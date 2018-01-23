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

import com.brtrndb.chatscript.client.core.MessageService;
import com.brtrndb.chatscript.client.core.ChatscriptException;

public class Client implements MessageService
{
	private static final Logger	log				= LoggerFactory.getLogger(Client.class);
	private static final String	CMD_QUIT		= ":quit";
	private static final int	RESPONSE_BUFFER	= 1024;

	private final String		url;
	private final int			port;
	private final String		username;
	private final String		botname;

	public Client(final String url, final int port, final String username, final String botname)
	{
		this.url = url;
		this.port = port;
		this.username = username;
		this.botname = botname;
	}

	@Override
	public void sendMessage(final Socket socket, final String username, final String botname, final String message) throws IOException
	{
		final Message msg = new Message(username, botname, message);
		final byte[] bytes = msg.toCSFormat();
		socket.getOutputStream().write(bytes);
		log.debug("Message sent: {} => [{}].", msg, bytes);
	}

	@Override
	public String receiveMessage(final Socket socket) throws IOException
	{
		int length;
		final byte[] buffer = new byte[RESPONSE_BUFFER];
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final InputStream is = socket.getInputStream();
		String response;

		while ((length = is.read(buffer)) != -1)
			baos.write(buffer, 0, length);

		response = baos.toString(StandardCharsets.UTF_8.name());
		log.debug("Response received: [{}].", response);
		return (response);
	}

	public void start()
	{
		try
		{
			log.info("Starting ChatScript client.");
			log.info("Client configuration: server={}:{} | username={} | botname={}", this.url, this.port, this.username, this.botname);
			System.out.println("Welcome to the ChatScript Java client. Type ':quit' to exit the chat.");
			this.initializeNewConversation();
			this.chatLoop();
		}
		catch (final ChatscriptException e)
		{
			e.printStackTrace();
		}
		this.quit();
	}

	private void initializeNewConversation() throws ChatscriptException
	{
		try (Socket socket = new Socket(this.url, this.port))
		{
			log.debug("Starting new conversation.");
			this.sendMessage(socket, this.username, this.botname, "");
		}
		catch (final IOException e)
		{
			throw (new ChatscriptException("Cannot connect to ChatScript server", e));
		}
	}

	private void chatLoop() throws ChatscriptException
	{
		boolean continueChatting = true;
		String message = "";
		String response = "";

		log.debug("Starting main conversation loop.");

		try (InputStreamReader isr = new InputStreamReader(System.in); BufferedReader br = new BufferedReader(isr))
		{
			while (continueChatting)
				try
				{
					this.userPrompt();
					message = br.readLine();
					if (CMD_QUIT.equals(message))
						continueChatting = false;
					else if (!message.isEmpty()) // As empty message means new conversation, empty message are ignored.
					{
						response = this.sendAndReceive(message);
						this.botPrompt(response);
					}
				}
				catch (final ChatscriptException e)
				{
					log.error("ChatScript error.", e);
					response = e.getLocalizedMessage();
					this.botPrompt(response);
				}
		}
		catch (final IOException e)
		{
			throw (new ChatscriptException("Cannot read standart input", e));
		}
	}

	private void userPrompt()
	{
		log.debug("Waiting for user input.");
		System.out.print(this.username + " : ");
	}

	private String sendAndReceive(final String message) throws ChatscriptException
	{
		String response;

		try (Socket socket = new Socket(this.url, this.port))
		{
			this.sendMessage(socket, this.username, this.botname, message);
			response = this.receiveMessage(socket);
		}
		catch (final IOException e)
		{
			throw (new ChatscriptException("Cannot connect to ChatScript server", e));
		}

		return (response);
	}

	private void botPrompt(final String response)
	{
		System.out.println(this.botname + " : " + response);
	}

	private void quit()
	{
		log.info("Exiting chat.");
	}
}
