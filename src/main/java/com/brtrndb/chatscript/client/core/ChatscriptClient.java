package com.brtrndb.chatscript.client.core;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public interface ChatscriptClient
{
	public String getUsername();

	public String getBotname();

	public String getUrl();

	public int getPort();

	public MessageService getMessageService();

	public Socket getNewSocket() throws UnknownHostException, IOException;
}
