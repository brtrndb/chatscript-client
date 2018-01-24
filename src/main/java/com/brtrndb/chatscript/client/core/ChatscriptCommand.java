package com.brtrndb.chatscript.client.core;

import java.util.function.BiFunction;

import lombok.Getter;

public enum ChatscriptCommand
{
	CMD_QUIT(":quit", ChatscriptCommand::cmdQuit), 
	CMD_RESET(":reset", ChatscriptCommand::cmdReset), 
	CMD_UNKNOWN("", ChatscriptCommand::cmdUnknown);

	@Getter
	private String															cmd;
	@Getter
	private BiFunction<ChatscriptClient, String[], ChatscriptCommandResult>	action;

	private ChatscriptCommand(String cmd, BiFunction<ChatscriptClient, String[], ChatscriptCommandResult> action)
	{
		this.cmd = cmd;
		this.action = action;
	}

	public static ChatscriptCommand fromString(String str)
	{
		for (ChatscriptCommand command : ChatscriptCommand.values())
			if (command.cmd.equals(str))
				return (command);

		return (ChatscriptCommand.CMD_UNKNOWN);
	}

	private static ChatscriptCommandResult cmdQuit(ChatscriptClient client, String[] cmdLine)
	{
		return (ChatscriptCommandResult.QUIT);
	}

	private static ChatscriptCommandResult cmdReset(ChatscriptClient client, String[] cmdLine)
	{
		return (ChatscriptCommandResult.CONTINUE);
	}

	private static ChatscriptCommandResult cmdUnknown(ChatscriptClient client, String[] cmdLine)
	{
		return (ChatscriptCommandResult.IGNORE);
	}
}
