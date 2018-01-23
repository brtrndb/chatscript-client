package com.brtrndb.chatscript.client.core;

import java.io.IOException;
import java.net.Socket;

public interface MessageService
{
	public void sendMessage(Socket socket, ChatscriptMessage message) throws IOException;

	public String receiveMessage(Socket socket) throws IOException;
}
