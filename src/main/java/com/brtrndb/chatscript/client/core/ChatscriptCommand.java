package com.brtrndb.chatscript.client.core;

import lombok.Getter;

public enum ChatscriptCommand
{
	CMD_QUIT(":quit", new ChatscriptActionQuit()),
	CMD_RESET(":reset", new ChatscriptActionReset()), 
	CMD_UNKNOWN("", new ChatscriptActionUnknown());

	@Getter
	private String				cmd;
	@Getter
	private ChatscriptAction	action;

	private ChatscriptCommand(String cmd, ChatscriptAction action)
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
}
