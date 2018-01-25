package com.brtrndb.chatscript.client.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.testng.annotations.Test;

import com.brtrndb.chatscript.client.core.ChatscriptClient;
import com.brtrndb.chatscript.client.core.message.ChatscriptMessage;

public class ClientTests
{
	private final ChatscriptClient client = new Client("localhost", 1024, "testname", "testbot");

	@Test
	public void testBuildSocket() throws UnknownHostException, IOException
	{
		try (Socket socket = this.client.buildSocket())
		{
			assertThat(socket.getPort()).isEqualTo(1024);
			assertThat(socket.getInetAddress().getHostName()).isEqualTo("localhost");
		}
	}

	@Test
	public void testBuildMessage()
	{
		final ChatscriptMessage msg = this.client.buildMessage("message");
		assertThat(msg.getUsername()).isEqualTo("testname");
		assertThat(msg.getBotname()).isEqualTo("testbot");
		assertThat(msg.getBody()).isEqualTo("message");
	}
}
