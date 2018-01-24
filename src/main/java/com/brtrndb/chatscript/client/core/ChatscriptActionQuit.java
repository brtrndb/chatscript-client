package com.brtrndb.chatscript.client.core;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChatscriptActionQuit extends ChatscriptAction
{
	public ChatscriptActionQuit()
	{
	}

	@Override
	public ChatscriptCommandResult doAction(ChatscriptClient client, String[] action) throws ChatscriptException
	{
		log.debug("Exit command.");
		return (ChatscriptCommandResult.QUIT);
	}
}
