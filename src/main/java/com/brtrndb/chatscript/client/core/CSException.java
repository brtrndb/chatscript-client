package com.brtrndb.chatscript.client.core;

public class CSException extends Exception
{
	private static final long serialVersionUID = 3111034869812210671L;

	public CSException()
	{
		super();
	}

	public CSException(final String message)
	{
		super(message);
	}

	public CSException(final String message, final Throwable cause)
	{
		super(message, cause);
	}
}
