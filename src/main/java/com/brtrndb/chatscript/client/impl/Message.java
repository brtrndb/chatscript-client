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

	public Message(String username, String botname, String body)
	{
		this.username = username;
		this.botname = botname;
		this.body = body;
	}

	public String getUsername()
	{
		return username;
	}

	public String getBotname()
	{
		return botname;
	}

	public String getBody()
	{
		return body;
	}

	/*
	 * (non-Javadoc)
	 * @see com.brtrndb.chatscript.client.CSMessage#toCSFormat()
	 */
	@Override
	public byte[] toCSFormat()
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		this.writeStringAsBytes(baos, username);
		this.writeStringAsBytes(baos, botname);
		this.writeStringAsBytes(baos, body);

		return baos.toByteArray();
	}

	private void writeStringAsBytes(ByteArrayOutputStream baos, String str)
	{
		byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
		baos.write(bytes, 0, bytes.length);
		baos.write(0);
	}
}
