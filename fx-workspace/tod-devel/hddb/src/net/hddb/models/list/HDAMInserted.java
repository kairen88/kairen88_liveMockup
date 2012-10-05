/*
 * Created on Apr 3, 2004
 */
package net.hddb.models.list;



/**
 * Message that signals that elements have been inserted into the list.
 */
public class HDAMInserted extends AbstractHDAMRange
{
	public HDAMInserted (HDMList aList, int aFirstIndex, int aCount) 
	{
		super(aList, aFirstIndex, aCount);
	}
}