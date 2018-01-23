package com.brtrndb.chatscript.client.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import com.brtrndb.chatscript.client.core.ChatscriptCommand;
import com.brtrndb.chatscript.client.core.ChatscriptCommandResult;
import com.brtrndb.chatscript.client.core.ChatscriptException;
import com.brtrndb.chatscript.client.core.ChatscriptMessage;
import com.brtrndb.chatscript.client.core.MessageService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Client
{
	private final String			url;
	private final int				port;
	private final String			username;
	private final String			botname;
	private final MessageService	messageService;

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
		ChatscriptCommandResult cmdResult = ChatscriptCommandResult.CONTINUE;
		String input = "";

		log.debug("Starting main conversation loop.");

		try (InputStreamReader isr = new InputStreamReader(System.in); BufferedReader br = new BufferedReader(isr))
		{
			while (cmdResult == ChatscriptCommandResult.CONTINUE)
				try
				{
					this.userPrompt();
					input = br.readLine().trim();
					cmdResult = this.processInput(input);
				}
				catch (final ChatscriptException e)
				{
					log.error("ChatScript error.", e);
					this.botPrompt(e.getLocalizedMessage());
				}
		}
		catch (final IOException e)
		{
			throw (new ChatscriptException("Cannot read standart input", e));
		}
	}

	private void userPrompt()
	{
		System.out.print(this.username + " : ");
	}

	private ChatscriptCommandResult processInput(String input) throws ChatscriptException
	{
		ChatscriptCommandResult result = ChatscriptCommandResult.CONTINUE;

		if (input.charAt(0) == ':')
		{
			String[] cmdLine = input.split(" ");
			ChatscriptCommand cmd = ChatscriptCommand.fromString(cmdLine[0]);
			result = cmd.getAction().apply(cmdLine);
		}

		if (result == ChatscriptCommandResult.CONTINUE && !input.isEmpty())
		{
			String response = this.sendAndReceive(input);
			this.botPrompt(response);
		}

		return (result);
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
		System.out.println("Good bye !");
	}
}
