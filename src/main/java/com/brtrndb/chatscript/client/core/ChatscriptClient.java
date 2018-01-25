package com.brtrndb.chatscript.client.core;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.brtrndb.chatscript.client.core.message.ChatscriptMessage;
import com.brtrndb.chatscript.client.core.message.ChatscriptMessageService;

public interface ChatscriptClient
{
	public String getUsername();

	public String getBotname();

	public String getUrl();

	public int getPort();

	public ChatscriptMessageService getMessageService();

	public Socket buildSocket() throws UnknownHostException, IOException;

	public ChatscriptMessage buildMessage(String message);

	public default String sendAndReceive(final String message) throws ChatscriptException
	{
		String response;

		try (Socket socket = this.buildSocket())
		{
			final ChatscriptMessage msg = this.buildMessage(message);
			this.getMessageService().sendMessage(socket, msg);
			response = this.getMessageService().receiveMessage(socket);
		}
		catch (final IOException e)
		{
			throw (new ChatscriptException("There was a problem with the connection to ChatScript server", e));
		}

		return (response);
	}

	public void userPrompt(String str);

	public void botPrompt(String str);

	public void clientPrompt(String str);
}
