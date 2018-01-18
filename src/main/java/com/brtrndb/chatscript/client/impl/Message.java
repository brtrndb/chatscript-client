/**
 *
 */
package com.brtrndb.chatscript.client.impl;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

import com.brtrndb.chatscript.client.core.CSMessage;

/**
 * ChatScript message and metadata.
 *
 * @author bertrand
 *
 */
public class Message implements CSMessage
{
	/** Message author. */
	private final String	username;
	/** Message recipient. */
	private final String	botname;
	/** Message body. */
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

	/*
	 * (non-Javadoc)
	 * @see com.brtrndb.chatscript.client.CSMessage#toCSFormat()
	 */
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

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
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
