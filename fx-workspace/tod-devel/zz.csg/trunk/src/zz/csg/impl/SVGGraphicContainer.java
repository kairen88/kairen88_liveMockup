/*
 * Created on 05-may-2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package zz.csg.impl;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import zz.csg.api.GraphicObjectContext;
import zz.csg.api.IDisplay;
import zz.csg.api.IRectangularGraphicContainer;
import zz.csg.api.PickResult;
import zz.csg.api.constraints.IConstrainedRectangle;


/**
 * Implementation of {@link zz.csg.api.IRectangularGraphicContainer} 
 *
 * @author gpothier
 */
public class SVGGraphicContainer extends AbstractGraphicContainer 
implements IRectangularGraphicContainer
{
	private IConstrainedRectangle pViewBox = new GOConstrainedRectangle(this, VIEWBOX)
	{
		@Override
		protected void changed(Rectangle2D aOldValue, Rectangle2D aNewValue)
		{
			super.changed(aOldValue, aNewValue);
			updateViewportTransform();
		}
	};
	
	private IConstrainedRectangle pBounds = createBoundsProperty();
	
	private AffineTransform itsViewportTransform = null;
	
	public IConstrainedRectangle pBounds()
	{
		return pBounds;
	}
	

	private IConstrainedRectangle createBoundsProperty()
	{
		IConstrainedRectangle theRectangle = new GOConstrainedRectangle(this, BOUNDS)
		{
			@Override
			protected void changed(Rectangle2D aOldValue, Rectangle2D aNewValue)
			{
				super.changed(aOldValue, aNewValue);
				updateViewportTransform();
			}
		};
		theRectangle.set(0, 0, 0, 0);
		return theRectangle;
	}

	public IConstrainedRectangle pViewBox()
	{
		return pViewBox;
	}
	
	public Rectangle2D getBounds(GraphicObjectContext aContext)
	{
		checkValid();
		return pBounds().get();
	}
	
	public void setSize(double aWidth, double aHeight)
	{
		Rectangle2D theBounds = pBounds.get();
		Rectangle2D theNewBounds = new Rectangle2D.Double(
				theBounds.getX(),
				theBounds.getY(),
				aWidth,
				aHeight);
		pBounds.set(theNewBounds);
	}
	
	/**
	 * This method updates the current viewport transform.
	 * This transform is applied before use-specified transform,
	 * and permits to map the viewbox to the viewport.
	 * If there is no view box, the transform is a translation
	 * of (bounds.x, bounds.y). 
	 */
	private void updateViewportTransform()
	{
		Rectangle2D theBounds = pBounds.get();
		if (theBounds == null)
		{
			itsViewportTransform = null;
		}
		else
		{
			Rectangle2D theViewBox = pViewBox.get();
			if (theViewBox == null)
			{
				itsViewportTransform = AffineTransform.getTranslateInstance(
						theBounds.getX(), 
						theBounds.getY());
			}
			else
			{
				itsViewportTransform = ViewBoxUtils.createViewBoxTransform(
						theBounds, 
						theViewBox);
			}
		}
	}
	
	@Override
	public void childrenToLocal(Point2D aPoint, Point2D aDestination)
	{
		super.childrenToLocal(aPoint, aDestination);
		if (itsViewportTransform != null)
		{
			itsViewportTransform.transform(aDestination, aDestination);
		}
	}
	
	@Override
	public void localToChildren(Point2D aPoint, Point2D aDestination)
	{
		if (itsViewportTransform != null)
		{
			try
			{
				itsViewportTransform.inverseTransform(aPoint, aDestination);
			}
			catch (NoninvertibleTransformException e)
			{
				e.printStackTrace();
			}
			super.localToChildren(aDestination, aDestination);
		}
		else super.localToChildren(aPoint, aDestination);
	}
		

	
	/**
	 * We override paint so as to perform clipping. 
	 * Overriding this method is discouraged, 
	 * see {@link AbstractGraphicContainer#paint(IDisplay, GraphicObjectContext, Graphics2D, Area)}
	 */
	@Override
	protected void paintTransformed(IDisplay aDisplay, GraphicObjectContext aContext, Graphics2D aGraphics, Area aVisibleArea)
	{
		Rectangle2D theBounds = getBounds(aContext);
		assert theBounds != null;
		
		if (theBounds.getWidth() == 0 || theBounds.getHeight() == 0) return;
		
		Area theVisibleArea = new Area (theBounds);
		theVisibleArea.intersect(aVisibleArea);
		
		if (theVisibleArea.isEmpty()) return;

		Rectangle2D theViewBox = pViewBox.get();
		if (theViewBox == null) theViewBox = new Rectangle2D.Double(0, 0, theBounds.getWidth(), theBounds.getHeight());
		AffineTransform theTransform = ViewBoxUtils.createViewBoxTransform(theBounds, theViewBox);
		
		try
		{
			theVisibleArea = theVisibleArea.createTransformedArea(theTransform.createInverse());
		}
		catch (NoninvertibleTransformException e)
		{
			throw new RuntimeException("Cannot inverse viewbox transform");
		}
		
		Shape thePreviousClip = aGraphics.getClip();
				
		aGraphics.clip(theBounds);
		
		AffineTransform thePreviousTransform = aGraphics.getTransform();
		aGraphics.transform(theTransform);
		super.paintTransformed(aDisplay, aContext, aGraphics, theVisibleArea);
		aGraphics.setTransform(thePreviousTransform);
		
		aGraphics.setClip(thePreviousClip);
	}
	
}
