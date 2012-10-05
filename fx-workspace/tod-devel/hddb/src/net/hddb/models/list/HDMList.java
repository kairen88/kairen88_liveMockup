/*
 * Created on Apr 3, 2004
 */
package net.hddb.models.list;

import net.hddb.models.HDModel;

/**
 * Model for a list
 * @author gpothier
 */
public interface HDMList extends HDModel
{
	/**
	 * Returns the number of elements of the list.
	 */
	public int getCount ();
	
	/**
	 * Returns the element of the list at the specified index.
	 */
	public Object getChild (int aIndex);
	

}
