package com.brtrndb.chatscript.client;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class ChatScriptOptions
{
	public static Options helpOptions()
	{
		final Options options = new Options();

		final Option optionHelp = Option.builder("h")
				.longOpt("help")
				.desc("Display help usage")
				.build();

		options.addOption(optionHelp);

		return (options);
	}

	public static Options cliOptions()
	{
		final Options options = new Options();

		final Option optionUrl = Option.builder("u")
				.longOpt("url")
				.desc("Server url")
				.argName("url")
				.hasArg(true)
				.required(false)
				.build();

		final Option optionPort = Option.builder("p")
				.longOpt("port")
				.desc("Server port")
				.argName("port")
				.hasArg(true)
				.required(false)
				.build();

		final Option optionUsername = Option.builder("n")
				.longOpt("name")
				.desc("Username")
				.argName("name")
				.hasArg(true)
				.required(false)
				.build();

		final Option optionBotname = Option.builder("b")
				.longOpt("bot")
				.desc("Botname")
				.argName("botname")
				.hasArg(true)
				.required(false)
				.build();

		options.addOption(optionUrl);
		options.addOption(optionPort);
		options.addOption(optionUsername);
		options.addOption(optionBotname);

		return (options);
	}

	public static Options merge(final Options... options)
	{
		final Options allOptions = new Options();

		for (final Options opts : options)
			for (final Option opt : opts.getOptions())
				allOptions.addOption(opt);

		return (allOptions);
	}
}
