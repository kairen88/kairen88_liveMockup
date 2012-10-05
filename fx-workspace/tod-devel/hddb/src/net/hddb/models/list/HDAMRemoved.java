/*
 * Created on Apr 3, 2004
 */
package net.hddb.models.list;



/**
 * Message that signals that elements have been removed from the list.
 */
public class HDAMRemoved extends AbstractHDAMRange
{
	public HDAMRemoved (HDMList aList, int aFirstIndex, int aCount) 
	{
		super(aList, aFirstIndex, aCount);
	}
}