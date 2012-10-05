/*
 * Created on Feb 24, 2004
 */
package net.hddb.adapters;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Collection;

import net.basekit.*;
import net.hddb.*;
import net.hddb.models.HDModel;
import net.hddb.utils.Constraints;


/**
 * Provides observable support
 * @author gpothier
 */
public abstract class HDObservable
{
	private List itsObservers = new ArrayList (1);
	
	public void addObserver (Observer aListener)
	{
		itsObservers.add (aListener);
	}
	
	public void removeObserver (Observer aListener)
	{
		itsObservers.remove (aListener);
	}

	/**
	 * Sends the specified message to all observers.
	 */
	protected void fire (HDAMessage aMessage)
	{
		for (Iterator theIterator = itsObservers.iterator(); theIterator.hasNext();)
		{
			Observer theObserver = (Observer) theIterator.next();
			theObserver.processMessage(aMessage);
		}
	}
	
	/**
	 * Recursively desagregates compound messages. If the given message is a 
	 * {@link HDACompoundMessage}, calls the observer's {@link Observer#processMessage(Message)}
	 * method on all of its children messages.
	 */
	public static void splitMessage (HDAMessage aMessage, Observer aObserver)
	{
		if (aMessage instanceof HDACompoundMessage)
		{
			HDACompoundMessage theCompoundMessage = (HDACompoundMessage) aMessage;
			for (Iterator theIterator = theCompoundMessage.getMessagesIterator();theIterator.hasNext();)
			{
				HDAMessage theMessage = (HDAMessage) theIterator.next();
				splitMessage(theMessage, aObserver);
			}
		}
		else aObserver.processMessage(aMessage);
	}

}
