/**
 *
 */
package com.brtrndb.chatscript.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.brtrndb.chatscript.client.impl.Client;

/**
 * @author bertrand
 *
 */
public class ChatScriptClient
{
	/** Logger for {@link ChatScriptClient}. */
	private static final Logger log = LoggerFactory.getLogger(ChatScriptClient.class);

	/**
	 * Main.
	 *
	 * @param args
	 *            Program arguments.
	 */
	public static void main(final String[] args)
	{
		try
		{
			verifyParams(args);
			final Client client = createClient(args);
			client.start();
		}
		catch (final Exception e)
		{
			log.error("Oups... Something went wrong: ", e.getLocalizedMessage());
		}
	}

	/**
	 * Verify correct number of parameters.
	 *
	 * @param args
	 *            Program arguments.
	 */
	private static void verifyParams(final String[] args)
	{
		if (args.length < 2)
		{
			log.error("Incorect number of parameters, found {} expected at least 2.", args.length);
			throw (new IllegalArgumentException("Wrong number of argument"));
		}
	}

	/**
	 * Build client from parameters.
	 *
	 * @param url
	 *            ChatScript server url.
	 * @param portStr
	 *            ChatScript server port.
	 * @return The ChatScript client.
	 */
	private static Client createClient(final String[] args)
	{
		final String url = args[0];
		final int port = Integer.parseInt(args[1]);
		final String username = args.length == 3 ? args[2] : "MacClane";
		final String botname = args.length == 4 ? args[3] : "harry";

		final Client client = new Client(url, port, username, botname);
		return (client);
	}
}
