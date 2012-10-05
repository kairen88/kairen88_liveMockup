/*
 * Created on Oct 11, 2005
 */
package zz.csg.api.layout;

import java.awt.geom.Rectangle2D;

import zz.csg.ZInsets;
import zz.csg.api.IGraphicContainer;
import zz.csg.api.IGraphicObject;
import zz.utils.ui.HorizontalAlignment;

/**
 * This layout manager stacks the container's children.
 * @author gpothier
 */
public class StackLayout extends AbstractSimpleLayout
{
	private double itsVGap; 
	private HorizontalAlignment itsAlignment;
	
	public StackLayout(double aGap, HorizontalAlignment aAlignment, ZInsets aInsets)
	{
		super(aInsets);
		itsVGap = aGap;
		itsAlignment = aAlignment;
	}

	public StackLayout()
	{
		this (0, HorizontalAlignment.LEFT, ZInsets.EMPTY);
	}
	
	@Override
	protected void layout(IGraphicContainer aContainer)
	{
//		System.out.println("StackLayout.layout()");
		double y = getInsets().top;
		double theMaxWidth = 0;
		
		// Compute max width
		for (IGraphicObject theGraphicObject : aContainer.pChildren())
		{
			Rectangle2D theBounds = theGraphicObject.getBounds(null);
			theBounds = theGraphicObject.getTransformedBounds(theBounds);
			
			theMaxWidth = Math.max(theMaxWidth, theBounds.getWidth());
		}

		// Perform layout
		for (IGraphicObject theGraphicObject : aContainer.pChildren())
		{
			Rectangle2D theBounds = theGraphicObject.getBounds(null);
			theBounds = theGraphicObject.getTransformedBounds(theBounds);
			
			double theDX = getInsets().left
					+ itsAlignment.getOffset(theBounds.getWidth(), theMaxWidth)
					- theBounds.getX();
			
			double theDY = theBounds.getY();
			
			theGraphicObject.translate(theDX, y-theDY);
			
			theMaxWidth = Math.max(theMaxWidth, theBounds.getWidth());
			y += theBounds.getHeight() + itsVGap;
		}
		
		resize(theMaxWidth+getInsets().left+getInsets().right, y+getInsets().bottom);
	}
}
