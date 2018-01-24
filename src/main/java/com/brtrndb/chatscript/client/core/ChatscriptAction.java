package com.brtrndb.chatscript.client.core;

public abstract class ChatscriptAction
{
	public ChatscriptAction()
	{
	}

	public abstract ChatscriptCommandResult doAction(ChatscriptClient client, String[] action) throws ChatscriptException;
}
