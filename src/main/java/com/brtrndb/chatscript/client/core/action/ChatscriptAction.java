package com.brtrndb.chatscript.client.core.action;

import com.brtrndb.chatscript.client.core.ChatscriptClient;
import com.brtrndb.chatscript.client.core.ChatscriptException;
import com.brtrndb.chatscript.client.core.command.ChatscriptCommandResult;

public abstract class ChatscriptAction
{
	public ChatscriptAction()
	{
	}

	public abstract ChatscriptCommandResult doAction(ChatscriptClient client, String[] action) throws ChatscriptException;
}
