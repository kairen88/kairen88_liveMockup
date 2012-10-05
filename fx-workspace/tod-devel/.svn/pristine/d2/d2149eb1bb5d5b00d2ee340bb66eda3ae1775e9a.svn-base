/*
 * Created on Apr 10, 2004
 */
package net.basekit.world;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.basekit.widgets.Widget;

/**
 * Manages the layout process in a {@link World}.
 * Widgets that need to be relaid out register themselves to world's
 * layout manager (that can be retrieved by {@link World#getLayoutManager()}).
 * This class is not intended to be used by clients. The {@link net.basekit.widgets.Widget}
 * class provides methods for managing layout.
 * @author gpothier
 */
public class LayoutManager
{
	private World itsWorld;
	
	/**
	 * The set of widgets that must be relaid out.
	 */
	private Set itsScheduledWigets = new HashSet ();
	
	public LayoutManager (World aWorld)
	{
		itsWorld = aWorld;
	}
	
	/**
	 * Notifies the system that the specified widget's layout is invalid
	 * and needs to be recomputed.
	 */
	public void schedule (Widget aWidget)
	{
		itsScheduledWigets.add (aWidget);
	}
	
	/**
	 * Lays out all registered widgets.
	 */
	public void layout ()
	{
		// Note about synchronization: as all widget manipulation is supposed to
		// be performed in the world's loop, we won't have synchronization issue
		// while copying the scheduled widgets set.
		// However, it is necessary to work on a copy of the set because the layout process
		// itself can cause more widgets to be registered.
		while (! itsScheduledWigets.isEmpty())
		{
			List theWorkingSet = new ArrayList (itsScheduledWigets);
			itsScheduledWigets.clear();
			
			for (Iterator theIterator = theWorkingSet.iterator(); theIterator.hasNext();)
			{
				Widget theWidget = (Widget) theIterator.next();
				if (itsScheduledWigets.contains(theWidget)) continue;
				theWidget.checkLayout ();
			}
		}
	}
}
