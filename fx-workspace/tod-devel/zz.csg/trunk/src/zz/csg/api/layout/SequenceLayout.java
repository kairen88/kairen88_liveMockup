/*
 * Created on Oct 11, 2005
 */
package zz.csg.api.layout;

import java.awt.geom.Rectangle2D;

import zz.csg.api.IGraphicContainer;
import zz.csg.api.IGraphicObject;

/**
 * This layout manager places the children one after another in a single row.
 * @author gpothier
 */
public class SequenceLayout extends AbstractSimpleLayout
{
	private double itsHGap; 
	
	public SequenceLayout(double aGap)
	{
		itsHGap = aGap;
	}

	public SequenceLayout()
	{
		this (0);
	}
	
	@Override
	protected void layout(IGraphicContainer aContainer)
	{
		double x = 0;
		double theMaxHeight = 0;
		for (IGraphicObject theGraphicObject : aContainer.pChildren())
		{
			Rectangle2D theBounds = theGraphicObject.getBounds(null);
			theBounds = theGraphicObject.getTransformedBounds(theBounds);
			
			theGraphicObject.translate(x-theBounds.getX(), -theBounds.getY());
				
			theMaxHeight = Math.max(theMaxHeight, theBounds.getHeight());
				
			x += theBounds.getWidth() + itsHGap;
		}

		resize(x, theMaxHeight);
	}
}
