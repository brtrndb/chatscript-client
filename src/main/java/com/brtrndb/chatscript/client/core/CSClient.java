package com.brtrndb.chatscript.client.core;

import java.io.IOException;
import java.net.Socket;

public interface CSClient
{
	public void sendMessage(Socket socket, String username, String botname, String message) throws IOException;

	public String receiveMessage(Socket socket) throws IOException;
}
