package com.brtrndb.chatscript.client.core.action;

import com.brtrndb.chatscript.client.core.ChatscriptClient;
import com.brtrndb.chatscript.client.core.ChatscriptException;
import com.brtrndb.chatscript.client.core.command.ChatscriptCommandResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChatscriptActionQuit extends ChatscriptAction
{
	public ChatscriptActionQuit()
	{
	}

	@Override
	public ChatscriptCommandResult doAction(final ChatscriptClient client, final String[] action) throws ChatscriptException
	{
		log.debug("Exit command.");
		client.clientPrompt("Exiting.");
		return (ChatscriptCommandResult.QUIT);
	}
}
