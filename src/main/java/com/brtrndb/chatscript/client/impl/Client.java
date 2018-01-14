/**
 * 
 */
package com.brtrndb.chatscript.client.impl;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.brtrndb.chatscript.client.core.CSClient;
import com.brtrndb.chatscript.client.core.CSException;

/**
 * @author bertrand
 *
 */
public class Client implements CSClient
{
	private static final Logger	log				= LoggerFactory.getLogger(Client.class);
	private static final String	CMD_QUIT		= ":quit";
	private static final int	RESPONSE_BUFFER	= 1024;

	/** ChatScript server url. */
	private String				url;
	/** ChatScript server port. */
	private int					port;
	/** ChatScript client username. */
	private String				username;
	/** Botname on the ChatScript server. */
	private String				botname;

	/**
	 * ChatScript client constructor.
	 * 
	 * @param url
	 *            Server url.
	 * @param port
	 *            Server port.
	 * @param username
	 *            Client username.
	 * @param botname
	 *            Botname on the server.
	 */
	public Client(String url, int port, String username, String botname)
	{
		this.url = url;
		this.port = port;
		this.username = username;
		this.botname = botname;
	}

	/*
	 * (non-Javadoc)
	 * @see com.brtrndb.chatscript.client.CSClient#sendMessage(java.lang.String, java.lang.String)
	 */
	@Override
	public void sendMessage(Socket socket, String username, String botname, String message) throws IOException
	{
		Message msg = new Message(username, botname, message);
		byte[] bytes = msg.toCSFormat();
		socket.getOutputStream().write(bytes);
		log.debug("Message sent: {} => [{}].", msg, bytes);
	}

	/*
	 * (non-Javadoc)
	 * @see com.brtrndb.chatscript.client.CSClient#receiveMessage()
	 */
	@Override
	public String receiveMessage(Socket socket) throws IOException
	{
		int length;
		byte[] buffer = new byte[RESPONSE_BUFFER];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream is = socket.getInputStream();
		String response;

		while ((length = is.read(buffer)) != -1)
			baos.write(buffer, 0, length);

		response = baos.toString(StandardCharsets.UTF_8.name());
		log.debug("Response received: [{}].", response);
		return (response);
	}

	/**
	 * Start the ChatScript client.
	 */
	public void start()
	{
		try
		{
			log.info("Starting ChatScript client.");
			log.info("Client configuration: server={}:{} | username={} | botname={}", this.url, this.port, this.username, this.botname);
			initializeNewConversation();
			chatLoop();
		}
		catch (CSException e)
		{
			e.printStackTrace();
		}
		quit();
	}

	/**
	 * A new conversation start with an empty message.
	 * 
	 * @throws CSException
	 * @see: https://github.com/bwilcox-1234/ChatScript/blob/7aec5242cd74c033ede4e7801ecce7f848bc4e6e/WIKI/CLIENTS-AND-SERVERS/ChatScript-ClientServer-Manual.md#chatscript-protocol
	 */
	private void initializeNewConversation() throws CSException
	{
		try (Socket socket = new Socket(this.url, this.port))
		{
			log.debug("Starting new conversation.");
			sendMessage(socket, username, botname, "");
		}
		catch (IOException e)
		{
			throw (new CSException("Cannot connect to ChatScript server", e));
		}
	}

	/**
	 * Main chat loop:
	 * - Read standard input.
	 * - Send the message.
	 * - Receive the response.
	 * 
	 * @throws CSException
	 */
	private void chatLoop() throws CSException
	{
		boolean continueChatting = true;
		String message = "";
		String response = "";

		log.debug("Starting main conversation loop.");

		try (InputStreamReader isr = new InputStreamReader(System.in); BufferedReader br = new BufferedReader(isr))
		{
			while (continueChatting)
			{
				try
				{
					userPrompt();
					message = br.readLine();
					if (CMD_QUIT.equals(message))
						continueChatting = false;
					else if (!message.isEmpty()) // As empty message means new conversation, empty message are ignored.
					{
						response = sendAndReceive(message);
						botPrompt(response);
					}
				}
				catch (CSException e)
				{
					log.error("ChatScript error.", e);
					response = e.getLocalizedMessage();
					botPrompt(response);
				}
			}
		}
		catch (IOException e)
		{
			throw (new CSException("Cannot read standart input", e));
		}
	}

	/**
	 * Display user prompt.
	 */
	private void userPrompt()
	{
		log.debug("Waiting for user input.");
		System.out.print(username + " : ");
	}

	/**
	 * Send a message and receive the response.
	 * 
	 * @param message
	 *            The message to send.
	 * @return The response.
	 * @throws CSException
	 */
	private String sendAndReceive(String message) throws CSException
	{
		String response;

		try (Socket socket = new Socket(this.url, this.port))
		{
			sendMessage(socket, username, botname, message);
			response = receiveMessage(socket);
		}
		catch (IOException e)
		{
			throw (new CSException("Cannot connect to ChatScript server", e));
		}

		return (response);
	}

	/**
	 * Display bot prompt with its response.
	 * 
	 * @param response
	 *            The bot response.
	 */
	private void botPrompt(String response)
	{
		System.out.println(botname + " : " + response);
	}

	/**
	 * Quit the ChatScript client.
	 */
	private void quit()
	{
		log.info("Exiting chat.");
	}
}
