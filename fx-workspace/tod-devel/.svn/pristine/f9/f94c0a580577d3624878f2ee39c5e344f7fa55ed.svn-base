/**
 * Created by IntelliJ IDEA.
 * User: g1
 * Date: 11 mars 2004
 * Time: 22:27:03
 * To change this template use File | Settings | File Templates.
 */
package net.basekit.models.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A simple implementation of {@link ListContentWidgetModel}.
 * Wraps a {@link List} and provides automatic message fireing (provided modifications to the underlying list
 * are performed through this object).
 * @author gpothier
 */
public class DefaultListContentWidgetModel extends AbstractListContentWidgetModel 
{
	private List itsList;

	public DefaultListContentWidgetModel ()
	{
		this (new ArrayList ());
	}

	public DefaultListContentWidgetModel (List aList)
	{
		itsList = aList;
	}

	public int getSize ()
	{
		return itsList.size ();
	}

	public Object getElementAt (int aIndex)
	{
		return itsList.get (aIndex);
	}

	/**
	 * Inserts an object into the list
	 * and sends appropriate notification.
	 */
	public void add (int aIndex, Object aObject)
	{
		itsList.add (aIndex, aObject);
		fireIntervalAdded(aIndex, 1);
	}

	/**
	 * Adds an object at the end of the list
	 * and sends appropriate notification.
	 */
	public void add (Object aObject)
	{
		int theIndex = itsList.size ();
		add (theIndex, aObject);
	}

	/**
	 * Adds all the elements of the collection to the list
	 * and sends appropriate notification.
	 */
	public void addAll (int aIndex, Collection aCollection)
	{
		int theCount = aCollection.size ();
		itsList.addAll (aIndex, aCollection);
		fireIntervalAdded (aIndex, theCount);
	}

	/**
	 * Adds all the elements of the collection at the end of the list
	 * and sends appropriate notification.
	 */
	public void addAll (Collection aCollection)
	{
		int theIndex = itsList.size ();
		addAll (theIndex, aCollection);
	}

	/**
	 * Removes the first occurence of the specified object
	 * and sends appropriate notification.
	 */
	public boolean remove (Object aObject)
	{
		int theIndex = itsList.indexOf (aObject);
		if (theIndex == -1) return false;

		itsList.remove (theIndex);
		fireIntervalRemoved(theIndex, 1);
		return true;
	}

	public Object remove (int aIndex)
	{
		Object theResult = itsList.remove (aIndex);
		fireIntervalRemoved(aIndex, 1);
		return theResult;
	}
}
