package com.brtrndb.chatscript.client.core;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.brtrndb.chatscript.client.core.command.ChatscriptCommand;

public class ChatscriptCommandTests
{
	@Test
	public void getQuitCommandFromStringTest()
	{
		ChatscriptCommand cmd = ChatscriptCommand.fromString(":quit");
		assertThat(cmd).isEqualTo(ChatscriptCommand.CMD_QUIT);
	}

	@Test
	public void getResetCommandFromStringTest()
	{
		ChatscriptCommand cmd = ChatscriptCommand.fromString(":reset");
		assertThat(cmd).isEqualTo(ChatscriptCommand.CMD_RESET);
	}

	@Test
	public void getUnknownCommandFromStringTest()
	{
		ChatscriptCommand cmd = ChatscriptCommand.fromString(":unknown");
		assertThat(cmd).isEqualTo(ChatscriptCommand.CMD_UNKNOWN);
	}
}
