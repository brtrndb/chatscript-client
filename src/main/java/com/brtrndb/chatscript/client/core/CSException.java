/**
 * 
 */
package com.brtrndb.chatscript.client.core;

/**
 * ChatScript exception.
 * 
 * @author bertrand
 *
 */
public class CSException extends Exception
{
	/**
	 * Serial UID.
	 */
	private static final long serialVersionUID = 3111034869812210671L;

	/**
	 * No arg. constructor.
	 */
	public CSException()
	{
		super();
	}

	/**
	 * 
	 * @param message
	 */
	public CSException(String message)
	{
		super(message);
	}

	/**
	 * 
	 * @param message
	 * @param cause
	 */
	public CSException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
