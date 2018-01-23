package com.brtrndb.chatscript.client.core;

public class ChatscriptException extends Exception
{
	private static final long serialVersionUID = 3111034869812210671L;

	public ChatscriptException()
	{
		super();
	}

	public ChatscriptException(final String message)
	{
		super(message);
	}

	public ChatscriptException(final String message, final Throwable cause)
	{
		super(message, cause);
	}
}
