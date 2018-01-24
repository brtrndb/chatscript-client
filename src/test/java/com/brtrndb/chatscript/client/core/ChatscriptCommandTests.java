package com.brtrndb.chatscript.client.core;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.brtrndb.chatscript.client.impl.Client;

public class ChatscriptCommandTests
{
	private ChatscriptClient client = new Client("localhost", 1024, "testname", "testbot");

	@Test
	public void getExistingCommandFromStringTest()
	{
		ChatscriptCommand cmd = ChatscriptCommand.fromString(":quit");
		assertThat(cmd).isEqualTo(ChatscriptCommand.CMD_QUIT);
	}

	@Test
	public void getResultForCmdQuit()
	{
		String[] cmdQuit = ":quit".split(" ");
		ChatscriptCommandResult result = ChatscriptCommand.CMD_QUIT.getAction().apply(this.client, cmdQuit);
		assertThat(result).isEqualTo(ChatscriptCommandResult.QUIT);
	}
}
