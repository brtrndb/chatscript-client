package com.brtrndb.chatscript.client.core.action;

import com.brtrndb.chatscript.client.core.ChatscriptClient;
import com.brtrndb.chatscript.client.impl.Client;

import lombok.Getter;

public abstract class ChatscriptActionTests
{
	@Getter
	private ChatscriptClient	client	= new Client("localhost", 1024, "testname", "testbot");
	@Getter
	private String[]			cmdLine;

	public ChatscriptActionTests(String command)
	{
		this.cmdLine = command.split(" ");
	}
}
