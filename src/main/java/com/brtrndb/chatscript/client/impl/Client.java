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

	private String				url;
	private int					port;
	private String				username;
	private String				botname;

	public Client(String url, int port, String username, String botname)
	{
		this.url = url;
		this.port = port;
		this.username = username;
		this.botname = botname;
	}

	@Override
	public void sendMessage(Socket socket, String username, String botname, String message) throws IOException
	{
		Message msg = new Message(username, botname, message);
		byte[] bytes = msg.toCSFormat();
		socket.getOutputStream().write(bytes);
		log.debug("Message sent: {} => [{}].", msg, bytes);
	}

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

	public void start()
	{
		try
		{
			log.info("Starting ChatScript client. Server: {}:{}.", this.url, this.port);
			initializeNewConversation();
			chatLoop();
		}
		catch (CSException e)
		{
			e.printStackTrace();
		}
		quit();
	}

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

	private void userPrompt()
	{
		log.debug("Waiting for user input.");
		System.out.print(username + " : ");
	}

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

	private void botPrompt(String response)
	{
		System.out.println(System.lineSeparator() + botname + " : " + response);
	}

	private void quit()
	{
		log.info("Exiting chat.");
	}
}
