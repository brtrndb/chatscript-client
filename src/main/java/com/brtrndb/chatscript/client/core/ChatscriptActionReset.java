package com.brtrndb.chatscript.client.core;

import java.io.IOException;
import java.net.Socket;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChatscriptActionReset extends ChatscriptAction
{
	public ChatscriptActionReset()
	{
	}

	@Override
	public ChatscriptCommandResult doAction(ChatscriptClient client, String[] action) throws ChatscriptException
	{
		log.debug("Reset command.");

		try (Socket socket = client.getNewSocket())
		{
			ChatscriptMessage msg = client.buildMessage("");
			client.getMessageService().sendMessage(socket, msg);
		}
		catch (final IOException e)
		{
			throw (new ChatscriptException("Cannot connect to ChatScript server", e));
		}

		return (ChatscriptCommandResult.CONTINUE);
	}
}
