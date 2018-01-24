package com.brtrndb.chatscript.client.core;

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
