/*
 * Created on Feb 24, 2004
 */
package net.basekit.exceptions;

import net.basekit.Message;

/**
 * A standard exception that is thrown a an method implementing 
 * {@link net.basekit.Observer#processMessage(Message)} cannot handle a message.
 * @author gpothier
 */
public class UnexpectedMessageException extends RuntimeException
{
	private Message itsMessage;
	
	public UnexpectedMessageException (Message aMessage) 
	{
		super (""+aMessage);
		itsMessage = aMessage;
	}

	public Message getHDAMessage()
	{
		return itsMessage;
	}
}
