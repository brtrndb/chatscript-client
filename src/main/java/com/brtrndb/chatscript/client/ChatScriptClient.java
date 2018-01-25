package com.brtrndb.chatscript.client;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.brtrndb.chatscript.client.core.ChatscriptException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChatScriptClient
{
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
		catch (ChatscriptException e)
		{
			log.error("Chatscript client error", e);
			System.out.println("An error occurs: " + e.getLocalizedMessage() + ".");
		}
		catch (final Exception e)
		{
			log.error("Oups... Something unexpected went wrong: ", e);
			System.out.println("An unexpected error occurs: " + e.getLocalizedMessage() + ".");
		}
	}

	private static Entry<Options, CommandLine> verifyCli(final String[] args) throws ChatscriptException
	{
		try
		{
			final Options helpOptions = ChatScriptOptions.helpOptions();
			final Options cliOptions = ChatScriptOptions.cliOptions();
			final Options allOptions = ChatScriptOptions.merge(helpOptions, cliOptions);

			final CommandLineParser parser = new DefaultParser();
			final CommandLine cli = parser.parse(allOptions, args, true);

			final SimpleEntry<Options, CommandLine> entry = new SimpleEntry<>(allOptions, cli);
			return (entry);
		}
		catch (ParseException e)
		{
			throw (new ChatscriptException("Could not parse command line", e));
		}
	}

	private static ClientManager createClientManager(final CommandLine cli) throws ChatscriptException
	{
		try
		{
			final String url = cli.getOptionValue("u", ChatScriptOptions.DEFAULT_SERVER_URL);
			final int port = Integer.parseInt(cli.getOptionValue("p", ChatScriptOptions.DEFAULT_SERVER_PORT));
			final String username = cli.getOptionValue("n", ChatScriptOptions.DEFAULT_USERNAME);
			final String botname = cli.getOptionValue("b", ChatScriptOptions.DEFAULT_BOTNAME);

			final ClientManager clientManager = new ClientManager(url, port, username, botname);
			return (clientManager);
		}
		catch (NumberFormatException e)
		{
			throw (new ChatscriptException("Port parameter in not a number", e));
		}
	}
}
