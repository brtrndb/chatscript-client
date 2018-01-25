package com.brtrndb.chatscript.client.core.command;

import com.brtrndb.chatscript.client.core.action.ChatscriptAction;
import com.brtrndb.chatscript.client.core.action.ChatscriptActionQuit;
import com.brtrndb.chatscript.client.core.action.ChatscriptActionReset;
import com.brtrndb.chatscript.client.core.action.ChatscriptActionUnknown;

import lombok.Getter;

public enum ChatscriptCommand
{
	CMD_QUIT(":quit", new ChatscriptActionQuit()), CMD_RESET(":reset", new ChatscriptActionReset()), CMD_UNKNOWN("", new ChatscriptActionUnknown());

	@Getter
	private String				cmd;
	@Getter
	private ChatscriptAction	action;

	private ChatscriptCommand(final String cmd, final ChatscriptAction action)
	{
		this.cmd = cmd;
		this.action = action;
	}

	public static ChatscriptCommand fromString(final String str)
	{
		for (final ChatscriptCommand command : ChatscriptCommand.values())
			if (command.cmd.equals(str))
				return (command);

		return (ChatscriptCommand.CMD_UNKNOWN);
	}
}
