package com.brtrndb.chatscript.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

import com.brtrndb.chatscript.client.core.message.ChatscriptMessage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FakeServer extends Thread
{
	public static final String	FAIL	= "FAIL";

	private int					port;
	private ChatscriptMessage	expected;
	private String				response;

	public FakeServer(String name, int port, ChatscriptMessage expected, String response)
	{
		super(name);
		this.port = port;
		this.expected = expected;
		this.response = response;
	}

	public int getPort()
	{
		int p = -1;

		while (p == -1)
			synchronized (this)
			{
				if (this.port != 0)
				{
					p = this.port;
				}
			}
		return (p);
	}

	@Override
	public void run()
	{
		try (ServerSocket serverSocket = new ServerSocket(this.port))
		{
			synchronized (this)
			{
				this.port = serverSocket.getLocalPort();
			}

			log.debug("Waiting client connection on port {}.", this.port);
			try (Socket socket = serverSocket.accept())
			{
				log.debug("New connection from {} on port {}.", socket.getInetAddress(), socket.getPort());
				final byte[] expectedBytes = this.expected.toCSFormat();
				final byte[] buffer = new byte[expectedBytes.length];

				try(final InputStream is = socket.getInputStream())
				{
					is.read(buffer, 0, expectedBytes.length);

					if (Arrays.equals(buffer, expectedBytes))
						socket.getOutputStream().write(this.response.getBytes());
					else
						socket.getOutputStream().write(FAIL.getBytes());
				}
			}
			catch (IOException e)
			{
				log.error("Cannot open client socket.", e);
			}
		}
		catch (IOException e)
		{
			log.error("Cannot open server socket", e);
		}
		log.debug("FakeServer stop.");
	}

	public void waitForEnding()
	{
		while (this.isAlive())
			log.debug("Waiting the end of the fakeserver {}.", this.getName());
	}
}
