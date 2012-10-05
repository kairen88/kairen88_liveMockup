/*
 * Created on Jan 15, 2007
 */
package zz.csg.api.layout;

import java.awt.geom.Rectangle2D;

import zz.csg.api.IGraphicContainer;
import zz.csg.api.IRectangularGraphicObject;
import zz.utils.Utils;

/**
 * A layout similar to AWT's {@link java.awt.BorderLayout}.
 * Note that the components must be 
 * @author gpothier
 */
public class BorderLayout extends AbstractSimpleLayout
{
	private IRectangularGraphicObject itsNorthComponent;
	private IRectangularGraphicObject itsWestComponent;
	private IRectangularGraphicObject itsEastComponent;
	private IRectangularGraphicObject itsSouthComponent;
	private IRectangularGraphicObject itsCenterComponent;
	private IRectangularGraphicObject itsNEComponent;
	private IRectangularGraphicObject itsNWComponent;
	private IRectangularGraphicObject itsSEComponent;
	private IRectangularGraphicObject itsSWComponent;

	private double itsNorthBorderHeight;
	private double itsSouthBorderHeight;
	private double itsWestBorderWidth;
	private double itsEastBorderWidth;
	private double itsCenterWidth;
	private double itsCenterHeight;
	private double itsTotalVerticalGap;
	private double itsTotalHorizontalGap;

	private double itsHGap = 0;
	private double itsVGap = 0;

	public BorderLayout ()
	{
	}

	public BorderLayout (int aHGap, int aVGap)
	{
		itsHGap = aHGap;
		itsVGap = aVGap;
	}

	public IRectangularGraphicObject getCenterComponent()
	{
		return itsCenterComponent;
	}

	public void setCenterComponent(IRectangularGraphicObject aCenterComponent)
	{
		itsCenterComponent = aCenterComponent;
	}

	public IRectangularGraphicObject getEastComponent()
	{
		return itsEastComponent;
	}

	public void setEastComponent(IRectangularGraphicObject aEastComponent)
	{
		itsEastComponent = aEastComponent;
	}

	public IRectangularGraphicObject getNEComponent()
	{
		return itsNEComponent;
	}

	public void setNEComponent(IRectangularGraphicObject aComponent)
	{
		itsNEComponent = aComponent;
	}

	public IRectangularGraphicObject getNorthComponent()
	{
		return itsNorthComponent;
	}

	public void setNorthComponent(IRectangularGraphicObject aNorthComponent)
	{
		itsNorthComponent = aNorthComponent;
	}

	public IRectangularGraphicObject getNWComponent()
	{
		return itsNWComponent;
	}

	public void setNWComponent(IRectangularGraphicObject aComponent)
	{
		itsNWComponent = aComponent;
	}

	public IRectangularGraphicObject getSEComponent()
	{
		return itsSEComponent;
	}

	public void setSEComponent(IRectangularGraphicObject aComponent)
	{
		itsSEComponent = aComponent;
	}

	public IRectangularGraphicObject getSouthComponent()
	{
		return itsSouthComponent;
	}

	public void setSouthComponent(IRectangularGraphicObject aSouthComponent)
	{
		itsSouthComponent = aSouthComponent;
	}

	public IRectangularGraphicObject getSWComponent()
	{
		return itsSWComponent;
	}

	public void setSWComponent(IRectangularGraphicObject aComponent)
	{
		itsSWComponent = aComponent;
	}

	public IRectangularGraphicObject getWestComponent()
	{
		return itsWestComponent;
	}

	public void setWestComponent(IRectangularGraphicObject aWestComponent)
	{
		itsWestComponent = aWestComponent;
	}

	private Dimension getDimension(IRectangularGraphicObject aGraphicObject)
	{
		if (aGraphicObject == null) return empty;
		Rectangle2D theBounds = aGraphicObject.pBounds().get();
		return new Dimension(theBounds.getWidth(), theBounds.getHeight());
	}

	private void updateBorderSizes ()
	{
		Dimension dimCenter = getDimension (itsCenterComponent);
		Dimension dimNorth = getDimension (itsNorthComponent);
		Dimension dimSouth = getDimension (itsSouthComponent);
		Dimension dimEast = getDimension (itsEastComponent);
		Dimension dimWest = getDimension (itsWestComponent);
		Dimension dimNE = getDimension (itsNEComponent);
		Dimension dimNW = getDimension (itsNWComponent);
		Dimension dimSE = getDimension (itsSEComponent);
		Dimension dimSW = getDimension (itsSWComponent);

		itsNorthBorderHeight = Utils.max (dimNW.height, dimNorth.height, dimNE.height);
		itsSouthBorderHeight = Utils.max (dimSW.height, dimSouth.height, dimSE.height);
		itsWestBorderWidth = Utils.max (dimNW.width, dimWest.width, dimSW.width);
		itsEastBorderWidth = Utils.max (dimNE.width, dimEast.width, dimSE.width);
		itsCenterWidth = dimCenter.width;
		itsCenterHeight = dimCenter.height;

		int theNHorizontalBands = 
			(itsNorthBorderHeight > 0 ? 1 : 0) 
			+ (itsCenterHeight > 0 ? 1 : 0) 
			+ (itsSouthBorderHeight > 0 ? 1 : 0);
		
		itsTotalVerticalGap = Math.max (0, theNHorizontalBands - 1) * itsVGap;
		
		int theNVerticalBands = 
			(itsWestBorderWidth > 0 ? 1 : 0) 
			+ (itsCenterWidth > 0 ? 1 : 0) 
			+ (itsEastBorderWidth > 0 ? 1 : 0);
		
		itsTotalHorizontalGap = Math.max (0, theNVerticalBands - 1) * itsHGap;
	}

	@Override
	protected void layout(IGraphicContainer aContainer)
	{
		updateBorderSizes ();

		Rectangle2D theBounds;
		if (aContainer instanceof IRectangularGraphicObject)
		{
			IRectangularGraphicObject theGraphicObject = (IRectangularGraphicObject) aContainer;
			theBounds = theGraphicObject.pBounds().get();
		}
		else return;
		
		Insets insets = new Insets(); // Just in case we have insets later...
		double tw = theBounds.getWidth();
		double th = theBounds.getHeight();

		double width = tw - insets.left - insets.right;
		double height = th - insets.bottom - insets.top;
		double top = insets.top;
		double bottom = th - insets.bottom;
		double left = insets.left;
		double right = tw - insets.right;

		double w = width - itsWestBorderWidth - itsEastBorderWidth - itsTotalHorizontalGap;
		double h = height - itsNorthBorderHeight - itsSouthBorderHeight - itsTotalVerticalGap;

		if (itsNWComponent != null) itsNWComponent.pBounds().set(left, top, itsWestBorderWidth, itsNorthBorderHeight);
		left += itsWestBorderWidth;
		if (itsWestBorderWidth > 0) left += itsHGap;
		if (itsNorthComponent != null) itsNorthComponent.pBounds().set(left, top, w, itsNorthBorderHeight);
		left += w;
		if (w > 0) left += itsHGap;
		if (itsNEComponent != null) itsNEComponent.pBounds().set(left, top, itsEastBorderWidth, itsNorthBorderHeight);

		top += itsNorthBorderHeight;
		if (itsNorthBorderHeight > 0) top += itsVGap;
		left = insets.left;

		if (itsWestComponent != null) itsWestComponent.pBounds().set(left, top, itsWestBorderWidth, h);
		left += itsWestBorderWidth;
		if (itsWestBorderWidth > 0) left += itsHGap;
		if (itsCenterComponent != null) itsCenterComponent.pBounds().set(left, top, w, h);
		left += w;
		if (w > 0) left += itsHGap;
		if (itsEastComponent != null) itsEastComponent.pBounds().set(left, top, itsEastBorderWidth, h);

		top += h;
		if (h > 0) top += itsVGap;
		left = insets.left;

		if (itsSWComponent != null) itsSWComponent.pBounds().set(left, top, itsWestBorderWidth, itsSouthBorderHeight);
		left += itsWestBorderWidth;
		if (itsWestBorderWidth > 0) left += itsHGap;
		if (itsSouthComponent != null) itsSouthComponent.pBounds().set(left, top, w, itsSouthBorderHeight);
		left += w;
		if (w > 0) left += itsHGap;
		if (itsSEComponent != null) itsSEComponent.pBounds().set(left, top, itsEastBorderWidth, itsSouthBorderHeight);
	}

	private static final Dimension empty = new Dimension (0, 0);
	
	private static class Dimension
	{
		public double width;
		public double height;
		
		public Dimension(double aWidth, double aHeight)
		{
			width = aWidth;
			height = aHeight;
		}
	}
	
	private static class Insets
	{
	    public double top;
	    public double left;
	    public double bottom;
	    public double right;
	    
		public Insets()
		{
		}
	}
}
