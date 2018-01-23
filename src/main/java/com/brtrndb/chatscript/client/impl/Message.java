package com.brtrndb.chatscript.client.impl;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

import com.brtrndb.chatscript.client.core.ChatscriptMessage;

import lombok.Getter;
import lombok.ToString;

@ToString
public class Message implements ChatscriptMessage
{
	@Getter
	private final String	username;
	@Getter
	private final String	botname;
	@Getter
	private final String	body;

	public Message(final String username, final String botname, final String body)
	{
		this.username = username;
		this.botname = botname;
		this.body = body;
	}

	@Override
	public byte[] toCSFormat()
	{
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();

		this.writeStringAsBytes(baos, this.username);
		this.writeStringAsBytes(baos, this.botname);
		this.writeStringAsBytes(baos, this.body);

		return (baos.toByteArray());
	}

	private void writeStringAsBytes(final ByteArrayOutputStream baos, final String str)
	{
		final byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
		baos.write(bytes, 0, bytes.length);
		baos.write(0);
	}
}
