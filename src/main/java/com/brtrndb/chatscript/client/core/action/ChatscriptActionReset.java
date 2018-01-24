package com.brtrndb.chatscript.client.core.action;

import java.io.IOException;
import java.net.Socket;

import com.brtrndb.chatscript.client.core.ChatscriptClient;
import com.brtrndb.chatscript.client.core.ChatscriptException;
import com.brtrndb.chatscript.client.core.command.ChatscriptCommandResult;
import com.brtrndb.chatscript.client.core.message.ChatscriptMessage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChatscriptActionReset extends ChatscriptAction
{
	public ChatscriptActionReset()
	{
	}

	@Override
	public ChatscriptCommandResult doAction(final ChatscriptClient client, final String[] action) throws ChatscriptException
	{
		log.debug("Reset command.");

		try (Socket socket = client.buildSocket())
		{
			final ChatscriptMessage msg = client.buildMessage("");
			client.getMessageService().sendMessage(socket, msg);
		}
		catch (final IOException e)
		{
			throw (new ChatscriptException("Cannot connect to ChatScript server", e));
		}

		return (ChatscriptCommandResult.CONTINUE);
	}
}
