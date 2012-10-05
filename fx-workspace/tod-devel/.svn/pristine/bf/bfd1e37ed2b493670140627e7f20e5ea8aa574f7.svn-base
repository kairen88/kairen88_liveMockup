/*
 * Created on Mar 6, 2004
 */
package net.hddb.adapters;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * A message that is compound of other messages.
 * @author gpothier
 */
public class HDACompoundMessage extends HDAMessage
{
	private List itsMessages;

	public HDACompoundMessage()
	{
		this (new ArrayList ());
	}
	
	public HDACompoundMessage(List aMessages) 
	{
		itsMessages = aMessages;
	}
	
	public void append (HDAMessage aMessage)
	{
		itsMessages.add (aMessage);
	}
	
	public Iterator getMessagesIterator ()
	{
		return itsMessages.iterator();
	}
}