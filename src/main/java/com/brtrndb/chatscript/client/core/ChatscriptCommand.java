package com.brtrndb.chatscript.client.core;

import java.util.function.Function;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum ChatscriptCommand
{
	CMD_QUIT(":quit", ChatscriptCommand::cmdQuit);

	@Getter
	private String										cmd;
	@Getter
	private Function<String[], ChatscriptCommandResult>	action;

	private ChatscriptCommand(String cmd, Function<String[], ChatscriptCommandResult> action)
	{
		this.cmd = cmd;
		this.action = action;
	}

	public static ChatscriptCommand fromString(String str)
	{
		for (ChatscriptCommand command : ChatscriptCommand.values())
			if (command.cmd.equals(str))
				return (command);

		throw (new IllegalArgumentException("The command does not exists: " + str));
	}

	private static ChatscriptCommandResult cmdQuit(String[] cmdLine)
	{
		return (ChatscriptCommandResult.QUIT);
	}
}
