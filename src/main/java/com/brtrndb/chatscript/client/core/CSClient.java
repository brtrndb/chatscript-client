/**
 * 
 */
package com.brtrndb.chatscript.client.core;

import java.io.IOException;
import java.net.Socket;

/**
 * @author bertrand
 * @see Full Chatscript documentation on implementation available at https://github.com/bwilcox-1234/ChatScript.
 */
public interface CSClient
{
	/**
	 * Send a message to the ChatScript server.
	 * 
	 * @param socket
	 *            The socket to write on.
	 * @param username
	 *            The client username.
	 * @param botname
	 *            The bot name.
	 * @param message
	 *            The message.
	 * @throws IOException
	 */
	public void sendMessage(Socket socket, String username, String botname, String message) throws IOException;

	/**
	 * Receive the ChatScript response message.
	 * 
	 * @param socket
	 *            The socket to listen for the response.
	 * @return The response.
	 * @throws IOException
	 */
	public String receiveMessage(Socket socket) throws IOException;
}
