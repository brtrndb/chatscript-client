/**
 *
 */
package com.brtrndb.chatscript.client.core;

/**
 * @author bertrand
 * @see Full Chatscript documentation on implementation available at https://github.com/bwilcox-1234/ChatScript.
 */
public interface CSMessage
{
	/**
	 * Transform a message into the ChatScript message format.
	 *
	 * @return
	 */
	public byte[] toCSFormat();
}
