/*
 * Created on Apr 3, 2004
 */
package net.hddb.models.list;



/**
 * Message that signals that elements have been replaced in the list.
 */
public class HDAMReplaced extends AbstractHDAMRange
{
	public HDAMReplaced (HDMList aList, int aFirstIndex, int aCount) 
	{
		super(aList, aFirstIndex, aCount);
	}
}