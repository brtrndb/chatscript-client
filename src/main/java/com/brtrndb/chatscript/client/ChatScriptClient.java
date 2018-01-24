package com.brtrndb.chatscript.client;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChatScriptClient
{
	private static final String	DEFAULT_USERNAME	= "user";
	private static final String	DEFAULT_BOTNAME		= "harry";
	private static final String	DEFAULT_SERVER_URL	= "localhost";
	private static final String	DEFAULT_SERVER_PORT	= "1024";

	public static void main(final String[] args)
	{
		try
		{
			final Entry<Options, CommandLine> cli = verifyCli(args);

			if (cli.getValue().hasOption("h"))
			{
				final HelpFormatter formatter = new HelpFormatter();
				formatter.printHelp(ChatScriptClient.class.getSimpleName(), cli.getKey(), true);
			}
			else
			{
				final ClientManager clientManager = createClientManager(cli.getValue());
				clientManager.start();
			}
		}
		catch (final Exception e)
		{
			log.error("Oups... Something went wrong: ", e.getLocalizedMessage());
		}
	}

	private static Entry<Options, CommandLine> verifyCli(final String[] args) throws ParseException
	{
		final Options helpOptions = ChatScriptOptions.helpOptions();
		final Options cliOptions = ChatScriptOptions.cliOptions();
		final Options allOptions = ChatScriptOptions.merge(helpOptions, cliOptions);

		final CommandLineParser parser = new DefaultParser();
		final CommandLine cli = parser.parse(allOptions, args, true);

		final SimpleEntry<Options, CommandLine> entry = new SimpleEntry<>(allOptions, cli);
		return (entry);
	}

	private static ClientManager createClientManager(final CommandLine cli)
	{
		final String url = cli.getOptionValue("u", DEFAULT_SERVER_URL);
		final int port = Integer.parseInt(cli.getOptionValue("p", DEFAULT_SERVER_PORT));
		final String username = cli.getOptionValue("n", DEFAULT_USERNAME);
		final String botname = cli.getOptionValue("b", DEFAULT_BOTNAME);

		final ClientManager clientManager = new ClientManager(url, port, username, botname);
		return (clientManager);
	}
}
