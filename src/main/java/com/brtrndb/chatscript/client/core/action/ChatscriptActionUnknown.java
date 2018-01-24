package com.brtrndb.chatscript.client.core.action;

import com.brtrndb.chatscript.client.core.ChatscriptClient;
import com.brtrndb.chatscript.client.core.ChatscriptException;
import com.brtrndb.chatscript.client.core.command.ChatscriptCommandResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChatscriptActionUnknown extends ChatscriptAction
{
	public ChatscriptActionUnknown()
	{
	}

	@Override
	public ChatscriptCommandResult doAction(ChatscriptClient client, String[] action) throws ChatscriptException
	{
		log.debug("Unknown command.");
		return (ChatscriptCommandResult.CONTINUE);
	}
}
