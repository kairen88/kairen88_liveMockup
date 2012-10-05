/*
 * Created on Apr 3, 2004
 */
package net.hddb.models.list;

import java.util.Iterator;
import java.util.List;

import net.hddb.adapters.HDAMessage;
import zz.utils.Utils;

/**
 * 
 * @author gpothier
 */
public class HDMListUtils
{
	/**
	 * Creates a message that reflects the changes between two lists.
	 * This method is intended to be used by subclasses.
	 * @param aPreviousList The previous content of the list
	 * @param aNewList The new content of the list.
	 */
	public static HDAMessage createMessage (HDMList aHDMList, List aPreviousList, List aNewList)
	{
		//TODO: we must implement a true diff-like algorithm.
		//For now we only support insertion, deletion or replacement of 1 element
		
		int thePreviousSize = aPreviousList.size();
		int theNewSize = aNewList.size();
		
		if (theNewSize == thePreviousSize+1) //insertion
		{
			Iterator theIterator1 = aPreviousList.iterator();
			Iterator theIterator2 = aNewList.iterator();
			
			int theIndex = 0;
			while (theIterator1.hasNext())
			{
				Object theObject1 = theIterator1.next();
				Object theObject2 = theIterator2.next();
				
				if (! Utils.equalOrBothNull(theObject1, theObject2)) break;
				theIndex++;
			}

			theIterator2.next();
			while (theIterator1.hasNext())
			{
				Object theObject1 = theIterator1.next();
				Object theObject2 = theIterator2.next();
				
				if (! Utils.equalOrBothNull(theObject1, theObject2)) throw new RuntimeException ("Case not handled");
			}
			
			return new HDAMInserted (aHDMList, theIndex, 1);
		}
		else if (theNewSize == thePreviousSize-1) //deletion
		{			
			Iterator theIterator1 = aPreviousList.iterator();
			Iterator theIterator2 = aNewList.iterator();
			
			int theIndex = 0;
			while (theIterator2.hasNext())
			{
				Object theObject1 = theIterator1.next();
				Object theObject2 = theIterator2.next();
				
				if (! Utils.equalOrBothNull(theObject1, theObject2)) break;
				theIndex++;
			}

			theIterator1.next();
			while (theIterator2.hasNext())
			{
				Object theObject1 = theIterator1.next();
				Object theObject2 = theIterator2.next();
				
				if (! Utils.equalOrBothNull(theObject1, theObject2)) throw new RuntimeException ("Case not handled");
			}
			
			return new HDAMRemoved (aHDMList, theIndex, 1);

		}
		else if (theNewSize == thePreviousSize) //replacement
		{
			Iterator theIterator1 = aPreviousList.iterator();
			Iterator theIterator2 = aNewList.iterator();
			
			int theIndex = 0;
			while (theIterator2.hasNext())
			{
				Object theObject1 = theIterator1.next();
				Object theObject2 = theIterator2.next();
				
				if (! Utils.equalOrBothNull(theObject1, theObject2)) break;
				theIndex++;
			}

			while (theIterator2.hasNext())
			{
				Object theObject1 = theIterator1.next();
				Object theObject2 = theIterator2.next();
				
				if (! Utils.equalOrBothNull(theObject1, theObject2)) throw new RuntimeException ("Case not handled");
			}
			
			return new HDAMReplaced (aHDMList, theIndex, 1);			
		}
		else throw new RuntimeException ("Change is too complex");
	}

}
