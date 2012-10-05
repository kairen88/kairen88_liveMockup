/**
 * Created by IntelliJ IDEA.
 * User: g1
 * Date: 11 mars 2004
 * Time: 22:25:15
 * To change this template use File | Settings | File Templates.
 */
package net.basekit.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.basekit.Observer;
import net.basekit.messages.WidgetModelMessage;

/**
 * Base class for widget models.
 * Provides observer support.
 * TODO: implement observer support as a trait.
 * @author gpothier
 */
public class AbstractWidgetModel implements WidgetModel
{
	private List itsObservers;

	public final void addObserver (Observer aListener)
	{
		if (itsObservers == null) itsObservers = new ArrayList (2);
		itsObservers.add (aListener);
	}

	public final void removeObserver (Observer aListener)
	{
		itsObservers.remove (aListener);
	}

	/**
	 * Sends the specified message to all registered observers.
	 */
	public final void fire (WidgetModelMessage aMessage)
	{
		assert aMessage.getSource() == this;
		if (itsObservers != null) for (Iterator theIterator = itsObservers.iterator();theIterator.hasNext();)
		{
			Observer theObserver = (Observer) theIterator.next();
			theObserver.processMessage(aMessage);
		}
	}


}
