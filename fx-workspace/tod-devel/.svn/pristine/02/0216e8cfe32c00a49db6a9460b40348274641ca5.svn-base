/*
 * Created on Jan 4, 2005
 */
package zz.csg.impl;

import java.awt.geom.Rectangle2D;

import zz.csg.api.GraphicObjectContext;
import zz.csg.api.IRectangularGraphicObject;
import zz.csg.api.constraints.IConstrainedRectangle;
import zz.csg.impl.constraints.ConstrainedRectangle;
import zz.utils.properties.IProperty;
import zz.utils.properties.IPropertyListener;
import zz.utils.properties.IRWProperty;


/**
 * Base for graphic objects that have bounds
 * @author gpothier
 */
public abstract class AbstractRectangularGraphicObject extends AbstractGraphicObject
implements IRectangularGraphicObject
{
	private IConstrainedRectangle pBounds = createBoundsProperty();

	public IConstrainedRectangle pBounds()
	{
		return pBounds;
	}
	
	private IConstrainedRectangle createBoundsProperty()
	{
		IConstrainedRectangle theRectangle = new GOConstrainedRectangle(this, BOUNDS);
		theRectangle.set(0, 0, 0, 0);
		return theRectangle;
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
	
}
