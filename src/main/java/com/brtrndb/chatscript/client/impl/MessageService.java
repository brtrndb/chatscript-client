package com.brtrndb.chatscript.client.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import com.brtrndb.chatscript.client.core.ChatscriptMessage;
import com.brtrndb.chatscript.client.core.ChatscriptMessageService;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public class MessageService implements ChatscriptMessageService
{
	private static final int RESPONSE_BUFFER = 1024;

	@Override
	public void sendMessage(final Socket socket, ChatscriptMessage message) throws IOException
	{
		final byte[] bytes = message.toCSFormat();
		socket.getOutputStream().write(bytes);
		log.debug("Message sent: {} => [{}].", message, bytes);
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
}
