package com.brtrndb.chatscript.client.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.testng.annotations.Test;

import com.brtrndb.chatscript.client.FakeServer;
import com.brtrndb.chatscript.client.core.ChatscriptClient;
import com.brtrndb.chatscript.client.core.ChatscriptException;
import com.brtrndb.chatscript.client.core.message.ChatscriptMessage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClientTests // extends TestClassWithFakeServer
{
	private static final String				URL			= "localhost";
	private static final int				PORT		= 0;
	private static final String				USERNAME	= "User";
	private static final String				BOTNAME		= "Bot";

	private static final String				BODY		= "Msg";
	private static final ChatscriptMessage	MESSAGE		= new Message(USERNAME, BOTNAME, BODY);
	private static final String				RESPONSE	= "Ok";

	@Test
	public void testBuildSocket() throws UnknownHostException, IOException
	{
		FakeServer fs = new FakeServer("ClientTests.testBuildSocket", PORT, MESSAGE, RESPONSE);
		fs.start();

		ChatscriptClient client = new Client(URL, fs.getPort(), USERNAME, BOTNAME);

		try (Socket socket = client.buildSocket())
		{
			assertThat(socket.getPort()).isEqualTo(fs.getPort());
			assertThat(socket.getInetAddress().getHostName()).isEqualTo(URL);
		}

		fs.waitForEnding();
	}

	@Test
	public void testBuildMessage()
	{
		ChatscriptClient client = new Client(URL, PORT, USERNAME, BOTNAME);
		final ChatscriptMessage msg = client.buildMessage(BODY);
		assertThat(msg.getUsername()).isEqualTo(USERNAME);
		assertThat(msg.getBotname()).isEqualTo(BOTNAME);
		assertThat(msg.getBody()).isEqualTo(BODY);
	}

	@Test
	public void testSendAndReceive() throws ChatscriptException
	{
		FakeServer fs = new FakeServer("ClientTests.testSendAndReceive", PORT, MESSAGE, RESPONSE);
		fs.start();

		ChatscriptClient client = new Client(URL, fs.getPort(), USERNAME, BOTNAME);

		String res = client.sendAndReceive(BODY);
		assertThat(res).isEqualTo(RESPONSE);

		fs.waitForEnding();
	}
}
