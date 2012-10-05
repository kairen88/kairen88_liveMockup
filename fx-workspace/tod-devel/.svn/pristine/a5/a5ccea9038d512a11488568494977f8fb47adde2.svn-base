/*
 * Created on Apr 3, 2004
 */
package net.hddb.models.map;

import net.hddb.models.list.*;

/**
 * Defines a mapping between a key set and values. 
 * It is a subclass of {@link HDMList}; elements of the list
 * are entries, that defines a key-value pair.
 * The key and value for an entry can be retrieved with
 * {@link #getEntryKey(Object)} and {@link #getEntryValue(Object)}.
 * @author gpothier
 */
public interface HDMMap extends HDMList
{
	/**
	 * Returns the value part of the entry.
	 */
	public Object getEntryValue (Object aEntry);
	
	/**
	 * Returns the key part of the entry.
	 */
	public Object getEntryKey (Object aEntry);
	
	/**
	 * Retrieves the value that corresponds to a specific key.
	 */
	public Object getValue (Object aKey);

}
