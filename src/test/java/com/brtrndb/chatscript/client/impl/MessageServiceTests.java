package com.brtrndb.chatscript.client.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.testng.annotations.Test;

import com.brtrndb.chatscript.client.FakeServer;
import com.brtrndb.chatscript.client.core.message.ChatscriptMessage;
import com.brtrndb.chatscript.client.core.message.ChatscriptMessageService;

public class MessageServiceTests
{
	private static final String				URL			= "localhost";
	private static final int				PORT		= 0;

	private static final String				USERNAME	= "User";
	private static final String				BOTNAME		= "Bot";
	private static final String				BODY		= "Msg";
	private static final ChatscriptMessage	MESSAGE		= new Message(USERNAME, BOTNAME, BODY);

	private static final String				RESPONSE	= "Ok";

	private final ChatscriptMessageService	service		= new MessageService();

	@Test
	public void sendAndReceiveTest() throws UnknownHostException, IOException
	{
		FakeServer fs = new FakeServer("MessageServiceTests.sendAndReceiveTest", PORT, MESSAGE, RESPONSE);
		fs.start();

		try (Socket socket = new Socket(URL, fs.getPort()))
		{
			this.service.sendMessage(socket, MESSAGE);
			final String res = this.service.receiveMessage(socket);
			assertThat(res).isEqualTo(RESPONSE);
		}

		fs.waitForEnding();
	}
}
