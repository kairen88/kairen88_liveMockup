/*
 * Created on Jan 4, 2005
 */
package zz.csg.impl.figures;

import java.awt.geom.Rectangle2D;

import zz.csg.api.GraphicObjectContext;
import zz.csg.api.IRectangularGraphicObject;
import zz.csg.api.constraints.IConstrainedRectangle;
import zz.csg.impl.constraints.ConstrainedRectangle;


/**
 * Base for shapes that have a rectanmgular bounds property.
 * @author gpothier
 */
public abstract class AbstractRectangularShape extends AbstractShape
implements IRectangularGraphicObject
{
	private IConstrainedRectangle pBounds = createBoundsProperty();

	public Rectangle2D getBounds(GraphicObjectContext aContext)
	{
		checkValid();
		return pBounds().get();
	}
	
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
