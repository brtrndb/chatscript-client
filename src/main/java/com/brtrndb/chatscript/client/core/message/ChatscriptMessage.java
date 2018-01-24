package com.brtrndb.chatscript.client.core.message;

public interface ChatscriptMessage
{
	public String getUsername();

	public String getBotname();

	public String getBody();

	public byte[] toCSFormat();
}
