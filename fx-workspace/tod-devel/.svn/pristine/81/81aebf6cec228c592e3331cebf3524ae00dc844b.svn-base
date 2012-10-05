/*
 * Created on Feb 25, 2004
 */
package net.basekit.layoutdelegates;

import java.util.Iterator;

import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3f;

import net.basekit.utils.SizeUtils;
import net.basekit.widgets.Margins;
import net.basekit.widgets.Widget;

/**
 * A layout delegate that just let the widget at their position; it only
 * ensures they have their preferred size.
 * @author gpothier
 */
public class DefaultLayoutDelegate extends LayoutDelegate
{
	private static final Vector3f EMPTY_SIZE = new Vector3f ();
	
	public void layout()
	{
		for (Iterator theIterator = getWidget().getWidgetsIterator(); theIterator.hasNext();)
		{
			Widget theChild = (Widget) theIterator.next();
			theChild.setPosition(theChild.getPosition()); //TODO: find why this is necessary.
			theChild.setSize(theChild.getPreferredSize());
		}
	}
	
	public Vector3f computeMinimumSize()
	{
		return computeSize(SizeUtils.MINIMUM_SELECTOR);
	}

	public Vector3f computeMaximumSize()
	{
		return computeSize(SizeUtils.MAXIMUM_SELECTOR);
	}

	public Vector3f computePreferredSize()
	{
		return computeSize(SizeUtils.PREFERRED_SELECTOR);
	}
	
	private Vector3f computeSize (int aSizeSelector)
	{
		Margins theMargins = getWidget().getMargins();
		float theW = 0;
		float theH = 0;
		float theD = 0;
		for (Iterator theIterator = getWidget().getWidgetsIterator(); theIterator.hasNext();)
		{
			Widget theChild = (Widget) theIterator.next();
			Tuple3f thePosition = theChild.getPosition();
			Vector3f theSize = SizeUtils.getSize(aSizeSelector, theChild);
			
			theW = Math.max (theW, thePosition.x + theSize.x);
			theH = Math.max (theH, thePosition.y + theSize.y);
			theD = Math.max (theD, thePosition.z + theSize.z);
		}
		return new Vector3f (theW, theH, theD);
	}
}
