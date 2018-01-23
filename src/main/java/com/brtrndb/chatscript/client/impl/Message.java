package com.brtrndb.chatscript.client.impl;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

import com.brtrndb.chatscript.client.core.CSMessage;

public class Message implements CSMessage
{
	private final String	username;
	private final String	botname;
	private final String	body;

	public Message(final String username, final String botname, final String body)
	{
		this.username = username;
		this.botname = botname;
		this.body = body;
	}

	public String getUsername()
	{
		return this.username;
	}

	public String getBotname()
	{
		return this.botname;
	}

	public String getBody()
	{
		return this.body;
	}

	@Override
	public byte[] toCSFormat()
	{
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();

		this.writeStringAsBytes(baos, this.username);
		this.writeStringAsBytes(baos, this.botname);
		this.writeStringAsBytes(baos, this.body);

		return baos.toByteArray();
	}

	private void writeStringAsBytes(final ByteArrayOutputStream baos, final String str)
	{
		final byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
		baos.write(bytes, 0, bytes.length);
		baos.write(0);
	}

	@Override
	public String toString()
	{
		final StringBuilder builder = new StringBuilder();
		builder.append("Message [")
				.append("username=").append(this.username).append(", ")
				.append("botname=").append(this.botname).append(", ")
				.append("body=").append(this.body).append(']');
		return (builder.toString());
	}
}
