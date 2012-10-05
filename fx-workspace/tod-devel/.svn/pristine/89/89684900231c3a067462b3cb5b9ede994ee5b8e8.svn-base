/*
 * Created on Apr 3, 2004
 */
package net.hddb.models.list;

import net.hddb.adapters.HDAMessage;


/**
 * Message that signals that elements have been inserted into the list.
 */
public abstract class AbstractHDAMRange extends HDAMessage
{
	private HDMList itsList;
	private int itsFirstIndex;
	private int itsCount;
	
	public AbstractHDAMRange(HDMList aList, int aFirstIndex, int aCount) 
	{
		itsList = aList;
		itsFirstIndex = aFirstIndex;
		itsCount = aCount;
	}

	public int getCount()
	{
		return itsCount;
	}

	public int getFirstIndex()
	{
		return itsFirstIndex;
	}

	public HDMList getList()
	{
		return itsList;
	}
}