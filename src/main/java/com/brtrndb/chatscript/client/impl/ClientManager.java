package com.brtrndb.chatscript.client.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import com.brtrndb.chatscript.client.core.ChatscriptClient;
import com.brtrndb.chatscript.client.core.ChatscriptCommand;
import com.brtrndb.chatscript.client.core.ChatscriptCommandResult;
import com.brtrndb.chatscript.client.core.ChatscriptException;
import com.brtrndb.chatscript.client.core.ChatscriptMessage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClientManager
{
	private ChatscriptClient client;

	public ClientManager(final String url, final int port, final String username, final String botname)
	{
		this.client = new Client(url, port, username, botname);
	}

	public void start()
	{
		try
		{
			log.info("Starting ChatScript client.");
			log.info("Client configuration: server={}:{} | username={} | botname={}", this.client.getUrl(), this.client.getPort(), this.client.getUsername(), this.client.getBotname());
			System.out.println("Welcome to the ChatScript Java client. Type ':quit' to exit the chat.");
			this.startConversation();
			this.chatLoop();
		}
		catch (final ChatscriptException e)
		{
			log.error("ChatScript error.", e);
			System.out.println("An unexpected error occurs: " + e.getLocalizedMessage());
		}

		this.quit();
	}

	private void startConversation() throws ChatscriptException
	{
		try (Socket socket = this.client.getNewSocket())
		{
			log.debug("Starting new conversation.");
			ChatscriptMessage msg = new Message(this.client.getUsername(), this.client.getBotname(), "");
			this.client.getMessageService().sendMessage(socket, msg);
		}
		catch (final IOException e)
		{
			throw (new ChatscriptException("Cannot connect to ChatScript server", e));
		}
	}

	private void chatLoop() throws ChatscriptException
	{
		ChatscriptCommandResult cmdResult = ChatscriptCommandResult.CONTINUE;
		String input;

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
		System.out.print(this.client.getUsername() + " : ");
	}

	private ChatscriptCommandResult processInput(String input) throws ChatscriptException
	{
		ChatscriptCommandResult result = ChatscriptCommandResult.CONTINUE;

		if (input.charAt(0) == ':')
		{
			String[] cmdLine = input.split(" ");
			ChatscriptCommand cmd = ChatscriptCommand.fromString(cmdLine[0]);
			result = cmd.getAction().apply(this.client, cmdLine);
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

		try (Socket socket = this.client.getNewSocket())
		{
			ChatscriptMessage msg = new Message(this.client.getUsername(), this.client.getBotname(), message);
			this.client.getMessageService().sendMessage(socket, msg);
			response = this.client.getMessageService().receiveMessage(socket);
		}
		catch (final IOException e)
		{
			throw (new ChatscriptException("Cannot connect to ChatScript server", e));
		}

		return (response);
	}

	private void botPrompt(final String response)
	{
		System.out.println(this.client.getBotname() + " : " + response);
	}

	private void quit()
	{
		log.info("Exiting chat.");
		System.out.println("Good bye !");
	}
}
