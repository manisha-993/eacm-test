package com.ibm.pprds.epimshw;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * Exception with nesting.  Sttructured so that when recasting an Exception and
 * rethrowing it the stck trace of the original exception is maintained.
 * 
 * Implementation Note: This class can be eliminated once the move to
 * jdk1.4 is solidified.  It has constructors for nested exceptions.
 * 
 * @author tim
 */
public abstract class NestedException extends Exception
{
	
	public static final String CAUSED = "Caused by: ";

	protected Throwable _cause = null;

	protected NestedException()
	{
		super();
	}
		
	public NestedException(String message)
	{
		super(message);
	}
	
	public NestedException(String message, Throwable cause)
	{
		super(message);
		_cause = cause;
	}
	
	public Throwable getCause()
	{
		return _cause;
	}
	
	public void setCause(Throwable t)
	{
		_cause = t;
	}
	
	public void printStackTrace() 
	{
		super.printStackTrace();
		if (null != getCause())
		{
			System.out.println(CAUSED);
			getCause().printStackTrace();
		}
	}
	
	public void printStackTrace(PrintStream s)
	{
		super.printStackTrace(s);
		if (null != getCause())
		{
			s.println("\n" + CAUSED);			
			getCause().printStackTrace(s);
		}
	}
	
	
	public void printStackTrace(PrintWriter s)
	{
		super.printStackTrace(s);
		if (null != getCause())
		{
			s.println("\n" + CAUSED);			
			getCause().printStackTrace(s);
		}
	}
	
	public String getMessage()
	{
		return getMessage(true);
	}
	
	/**
	 * Get message for exception, optionally include
	 * message from causing exception.
	 * 
	 * @param includeCause - if true, message from causing exception
	 * is included.  Otherwise it is omitted.
	 */
	public String getMessage(boolean includeCause)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(getSuperMessage());
		if (includeCause && (null != getCause()))
		{
			sb.append("  " + CAUSED + getCause().getMessage());
		}
		return sb.toString();
	}

	/**
	 * Get the message from the calling super class.
	 *
	 * @returns String exception message if defined or the String
	 * "null" if the exception message is not defined.
	 */	
	private String getSuperMessage()
	{
		String msg = super.getMessage();
		return msg;
	}
	
	public String toString()
	{
		if (null == getCause())
		{
			return super.toString();
		}
		return this.getClass().getName() + ": " + getMessage(false) + "  " + CAUSED + getCause().toString();
	}
	
	
	
}
