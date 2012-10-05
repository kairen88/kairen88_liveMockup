package net.basekit.layoutdelegates;
import java.util.Iterator;

import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import net.basekit.utils.SizeUtils;
import net.basekit.widgets.Margins;
import net.basekit.widgets.Widget;

/**
 * Abstract support for laying out nodes along a line.
 * @author gpothier
 */
public abstract class LineLayoutDelegate extends LayoutDelegate
{
	private static Vector3f BUFFER = new Vector3f ();
	
	/**
	 * The gap between nodes.
	 */
	private float itsGap;
	
	
	public LineLayoutDelegate()
	{
		this (1);
	}
	
	public LineLayoutDelegate(float aGap)
	{
		itsGap = aGap;
	}

	public float getGap ()
	{
		return itsGap;
	}

	/**
	 * This is the method that defines the actual behaviour of this layout delegate.
	 * Giving it the position and size of a widget, it determines the position of the next widget,
	 * and updates the position argument accordingly.
	 */
	protected abstract void advanceLayout (Point3f aPosition, Widget aChildWidget, int aChildIndex, Vector3f aChildSize);
	
	/**
	 * Incrementally computes the size of the parent widget. After calling this method,
	 * the first parameter will be updated so that the size of the child widget,
	 * given as second parameter, is included in the total size. 
	 * @param aSize The buffer that contains the current size and that will be updated
	 * by this method so as to include the specified child widget size.
	 * @param aChild The widget that is being included in the layout.
	 * @param aChildIndex Index of the child widget
	 * @param aChildSize The size to take into account for the computation.
	 */
	protected abstract void growSize (Vector3f aSize, Widget aChild, int aChildIndex, Vector3f aChildSize);
	
	public Vector3f computeMaximumSize()
	{
		return computeSize(SizeUtils.MAXIMUM_SELECTOR);
	}

	public Vector3f computeMinimumSize()
	{
		return computeSize(SizeUtils.MINIMUM_SELECTOR);
	}

	public Vector3f computePreferredSize()
	{
		return computeSize(SizeUtils.PREFERRED_SELECTOR);
	}

	protected Vector3f computeSize(int aSizeSelector)
	{
		Vector3f theCurrentSize = new Vector3f ();
		
		Iterator theIterator = getWidget().getWidgetsIterator ();
		int theIndex = 0;
		while (theIterator.hasNext())
		{
			Widget theChildWidget = (Widget) theIterator.next ();
			Vector3f theChildSize = SizeUtils.getSize(aSizeSelector, theChildWidget);
			
			growSize (theCurrentSize, theChildWidget, theIndex, theChildSize);
			theIndex++;
		}

		Margins theMargins = getWidget().getMargins();
		theCurrentSize.x += theMargins.getHorizontal ();
		theCurrentSize.y += theMargins.getVertical ();
		theCurrentSize.z += theMargins.getDeep ();
		return theCurrentSize;
	}

	public void layout()
	{
		Margins theMargins = getWidget().getMargins();
		Point3f theCurrentPosition = new Point3f (theMargins.getLeft(), theMargins.getTop(), theMargins.getFront());
		Vector3f theWidgetSize = getWidget().getSize();
		
		Iterator theIterator = getWidget().getWidgetsIterator ();
		int theIndex = 0;
		while (theIterator.hasNext())
		{
			if (isOverflow(theCurrentPosition, theWidgetSize, theMargins)) break;
			
			Widget theChildWidget = (Widget) theIterator.next ();
			theChildWidget.setVisible (true);

			place (theChildWidget, theIndex, theCurrentPosition);
			advanceLayout (theCurrentPosition, theChildWidget, theIndex, theChildWidget.getSize ());
			theIndex++;
		}
		
		while (theIterator.hasNext())
		{
			Widget theChildWidget = (Widget) theIterator.next ();
			theChildWidget.setVisible (false);
		}
	}

	/**
	 * Actually places a widget at the specified position.
	 * Default behaviour is to affect it its preferred size.
	 * This lets subclasses override default behaviour.
	 */
	protected void place (Widget aChildWidget, int aChildIndex, Point3f aPosition)
	{
		Vector3f thePreferredChildSize = aChildWidget.getPreferredSize();
		aChildWidget.setPosition(aPosition);
		aChildWidget.setSize(thePreferredChildSize);
	}

	private boolean isOverflow (Point3f thePosition, Vector3f theSize, Margins aMargins)
	{
		return thePosition.x > theSize.x - aMargins.getRight()
		|| thePosition.y > theSize.y - aMargins.getBottom()
		|| thePosition.z > theSize.z - aMargins.getBack();
	}

}
