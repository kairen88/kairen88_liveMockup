/*
 * Created on Apr 3, 2004
 */
package net.hddb.adapters.impl.utilsmap2map;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import zz.utils.BoundedIterator;

import net.hddb.adapters.HDAdapterClass;
import net.hddb.adapters.HDAutoAdapter;
import net.hddb.models.map.HDMMap;

/**
 * Transforms a {@link java.util.Map} to a map adapter.
 * @author gpothier
 */
public class HDAUtilsMap2Map extends HDAutoAdapter implements HDMMap
{
	public HDAUtilsMap2Map (Object aElement)
	{
		super(aElement);
	}
	
	protected Map getMap ()
	{
		return (Map) getElement();
	}
	
	public Object getEntryValue (Object aEntry)
	{
		Map.Entry theEntry = (Entry) aEntry;
		return theEntry.getValue();
	}

	public Object getEntryKey (Object aEntry)
	{
		Map.Entry theEntry = (Entry) aEntry;
		return theEntry.getKey();
	}

	public Object getValue (Object aKey)
	{
		return getMap().get(aKey);
	}

	public int getCount ()
	{
		return getMap().size();
	}

	public Object getChild (int aIndex)
	{
		//TODO: see how we can implement that.
		return null;
	}

	public HDAdapterClass getAdapterClass ()
	{
		return HDACUtilsMap2Map.getInstance();
	}

	public List getOutboundReferences ()
	{
		return null;
	}
}
