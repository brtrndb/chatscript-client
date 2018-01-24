package com.brtrndb.chatscript.client.core.message;

import java.io.IOException;
import java.net.Socket;

public interface ChatscriptMessageService
{
	public void sendMessage(Socket socket, ChatscriptMessage message) throws IOException;

	public String receiveMessage(Socket socket) throws IOException;
}
