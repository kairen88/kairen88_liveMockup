/*
 * Created on Feb 25, 2004
 */
package net.hddb.ui.elementinstance;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import net.hddb.adapters.HDAdapterPipe;
import net.hddb.adapters.HDAdapterClass;
import net.hddb.adapters.HDAdapterManager;
import net.hddb.views.*;
import net.hddb.views.HDViewClass;
import net.hddb.views.HDViewManager;
import net.hddb.utils.Constraints;

/**
 * @author gpothier
 */
public class HDEIUtils
{
	/**
	 * Determines all the view chains that can be found for a gieven element
	 * in the context of a container (that is used to determine view constraints).
	 */
	public static Collection findAvailableViewChains (HDEIContainer aContainer, Object aElement)
	{
		Collection theViewChains = HDViewManager.getInstance().getViewChainsFor(aElement);
		Constraints theViewConstraints = null;
		if (aContainer != null) theViewConstraints = aContainer.getChildrenViewConstraints ();

		// Filter out rejected views.
		if (theViewConstraints != null)
		{
			for (Iterator theIterator = theViewChains.iterator (); theIterator.hasNext ();)
			{
				HDViewChain theViewChain = (HDViewChain) theIterator.next ();
				Class theModelClass = theViewChain.getModelClass();
				boolean theAccepted = true;
				while (theAccepted && theModelClass != null)
				{
					if (! theViewConstraints.isAccepted (theModelClass)) theAccepted = false;
					theModelClass = theModelClass.getSuperclass ();
				}
				if (! theAccepted) theIterator.remove ();
			}
		}

		return theViewChains;
	}
}
