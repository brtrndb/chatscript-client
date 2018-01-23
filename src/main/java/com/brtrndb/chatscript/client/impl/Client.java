package com.brtrndb.chatscript.client.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import com.brtrndb.chatscript.client.core.ChatscriptException;
import com.brtrndb.chatscript.client.core.ChatscriptMessage;
import com.brtrndb.chatscript.client.core.MessageService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Client
{
	private static final String	CMD_QUIT	= ":quit";

	private final String		url;
	private final int			port;
	private final String		username;
	private final String		botname;
	private MessageService		messageService;

	public Client(final String url, final int port, final String username, final String botname)
	{
		this.url = url;
		this.port = port;
		this.username = username;
		this.botname = botname;
		this.messageService = new MessageDelivery();
	}

	public void start()
	{
		try
		{
			log.info("Starting ChatScript client.");
			log.info("Client configuration: server={}:{} | username={} | botname={}", this.url, this.port, this.username, this.botname);
			System.out.println("Welcome to the ChatScript Java client. Type ':quit' to exit the chat.");
			this.startConversation();
			this.chatLoop();
		}
		catch (final ChatscriptException e)
		{
			e.printStackTrace();
		}
		this.quit();
	}

	private void startConversation() throws ChatscriptException
	{
		try (Socket socket = new Socket(this.url, this.port))
		{
			log.debug("Starting new conversation.");
			ChatscriptMessage msg = new Message(this.username, this.botname, "");
			this.messageService.sendMessage(socket, msg);
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
			ChatscriptMessage msg = new Message(this.username, this.botname, message);
			this.messageService.sendMessage(socket, msg);
			response = this.messageService.receiveMessage(socket);
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
