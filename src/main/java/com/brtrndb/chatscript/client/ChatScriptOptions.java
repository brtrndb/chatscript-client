package com.brtrndb.chatscript.client;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class ChatScriptOptions
{
	public static final String	DEFAULT_USERNAME	= "user";
	public static final String	DEFAULT_BOTNAME		= "harry";
	public static final String	DEFAULT_SERVER_URL	= "localhost";
	public static final String	DEFAULT_SERVER_PORT	= "1024";

	public static final Options	OPTIONS_HELP		= helpOptions();
	public static final Options	OPTIONS_CLI			= cliOptions();
	public static final Options	OPTIONS_ALL			= merge(OPTIONS_HELP, OPTIONS_CLI);

	private static Options helpOptions()
	{
		final Options options = new Options();

		final Option optionHelp = Option.builder("h")
				.longOpt("help")
				.desc("Display help usage.")
				.hasArg(false)
				.required(false)
				.build();

		options.addOption(optionHelp);

		return (options);
	}

	private static Options cliOptions()
	{
		final Options options = new Options();

		final Option optionUrl = Option.builder("u")
				.longOpt("url")
				.desc("ChatScript server url. Default is '" + DEFAULT_SERVER_URL + "'.")
				.hasArg(true)
				.numberOfArgs(1)
				.argName("url")
				.type(String.class)
				.required(false)
				.build();

		final Option optionPort = Option.builder("p")
				.longOpt("port")
				.desc("Chatscript server port. Default is '" + DEFAULT_SERVER_PORT + "'.")
				.argName("port")
				.hasArg(true)
				.type(Integer.class)
				.required(false)
				.build();

		final Option optionUsername = Option.builder("n")
				.longOpt("name")
				.desc("Username. Default is '" + DEFAULT_USERNAME + "'.")
				.hasArg(true)
				.numberOfArgs(1)
				.argName("name")
				.type(String.class)
				.required(false)
				.build();

		final Option optionBotname = Option.builder("b")
				.longOpt("bot")
				.desc("Botname. Default is '" + DEFAULT_BOTNAME + "'.")
				.hasArg(true)
				.numberOfArgs(1)
				.argName("botname")
				.type(String.class)
				.required(false)
				.build();

		options.addOption(optionUrl);
		options.addOption(optionPort);
		options.addOption(optionUsername);
		options.addOption(optionBotname);

		return (options);
	}

	private static Options merge(final Options... options)
	{
		final Options allOptions = new Options();

		for (final Options opts : options)
			for (final Option opt : opts.getOptions())
				allOptions.addOption(opt);

		return (allOptions);
	}

	public static void printHelp()
	{
		final HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp(ChatScriptClient.class.getSimpleName(), ChatScriptOptions.OPTIONS_ALL, true);
	}
}
