package com.brtrndb.chatscript.client.core.action;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.brtrndb.chatscript.client.core.ChatscriptException;
import com.brtrndb.chatscript.client.core.command.ChatscriptCommandResult;

public class ChatscriptActionQuitTests extends ChatscriptActionTests
{
	private static final String COMMAND = ":quit";

	public ChatscriptActionQuitTests()
	{
		super(COMMAND);
	}

	@Test
	public void getResultForActionQuit() throws ChatscriptException
	{
		final ChatscriptAction action = new ChatscriptActionQuit();
		final ChatscriptCommandResult result = action.doAction(this.getClient(), this.getCmdLine());
		assertThat(result).isEqualTo(ChatscriptCommandResult.QUIT);
	}
}
