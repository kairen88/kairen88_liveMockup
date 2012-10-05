/*
 * Created on May 29, 2004
 */
package zz.csg.impl.edition;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import EDU.Washington.grad.gjb.cassowary.ExCLError;
import EDU.Washington.grad.gjb.cassowary.ISimpleVariable;

import zz.csg.api.GraphicObjectContext;
import zz.csg.api.IGraphicObject;
import zz.csg.api.IRectangularGraphicObject;
import zz.csg.api.constraints.IConstrainedRectangle;
import zz.utils.properties.IRWProperty;


/**
 * An implementation of handle that is suited for graphic objects
 * that have a bounds property.
 * @author gpothier
 */
public abstract class AbstractRectangularGraphicObjectHandle extends AbstractHandle
{

	/**
	 * The property that contains the bounds edited by this handle.
	 */
	private final IConstrainedRectangle itsBoundsProperty;

	/**
	 * Generic constructor.
	 * @param aGraphicObject The graphic object whose bounds are to be edited.
	 * @param aBoundsProperty The bounds property.
	 */
	public AbstractRectangularGraphicObjectHandle(
			GraphicObjectContext aContext,
			IGraphicObject aGraphicObject, 
			IConstrainedRectangle aBoundsProperty)
	{
		super (aContext, aGraphicObject);
		itsBoundsProperty = aBoundsProperty;
	}
	
	/**
	 * Constructor for a {@link IRectangularGraphicObject}. It takes the bounds
	 * property as defined by the object itself.
	 * @param aGraphicObject
	 */
	public AbstractRectangularGraphicObjectHandle(
			GraphicObjectContext aContext,
			IRectangularGraphicObject aGraphicObject)
	{
		this (aContext, aGraphicObject, aGraphicObject.pBounds());
	}
	
	public ISimpleVariable vX()
	{
		return itsBoundsProperty.pX();
	}

	public ISimpleVariable vY()
	{
		return itsBoundsProperty.pY();
	}
	
	public ISimpleVariable vW()
	{
		return itsBoundsProperty.pW();
	}
	
	public ISimpleVariable vH()
	{
		return itsBoundsProperty.pH();
	}

	
	/**
	 * Retrieves the bounds of the graphic object.
	 */
	protected Rectangle2D getBounds ()
	{
		return itsBoundsProperty.get();
	}

	/**
	 * Sets the bounds of the graphic object.
	 */
	protected void setBounds (Rectangle2D aBounds)
	{
		itsBoundsProperty.set(aBounds);
	}
	
	/**
	 * Returns true iff the specified point is within the bounds of the graphic object
	 */
	protected boolean isWithinBounds (Point2D aPoint)
	{
		return getBounds().contains(aPoint);
	}
	
	protected void setX (double aX) throws ExCLError
	{
		set(vX(), aX);
	}
	
	protected void setY (double aY) throws ExCLError
	{
		set(vY(), aY);
	}
	
	protected void setMinX (double aMinX) throws ExCLError
	{
		double theMaxX = getBounds().getMaxX();
		set(vX(), aMinX);
		set(vW(), theMaxX - aMinX);
	}
	
	protected void setMinY (double aMinY) throws ExCLError
	{
		double theMaxY = getBounds().getMaxY();
		set(vY(), aMinY);
		set(vH(), theMaxY - aMinY);
	}
	
	protected void setMaxX (double aMaxX) throws ExCLError
	{
		double theMinX = getBounds().getMinX();
		set(vW(), aMaxX-theMinX);
	}
	
	protected void setMaxY (double aMaxY) throws ExCLError
	{
		double theMinY = getBounds().getMinY();
		set(vH(), aMaxY-theMinY);
	}
	
	protected double getMinX ()
	{
		return getBounds().getMinX();
	}
	
	protected double getMinY ()
	{
		return getBounds().getMinY();
	}

	protected double getMaxX ()
	{
		return getBounds().getMaxX();
	}
	
	protected double getMaxY ()
	{
		return getBounds().getMaxY();
	}
	
	protected double getCenterX ()
	{
		return getBounds().getCenterX();
	}
	
	protected double getCenterY ()
	{
		return getBounds().getCenterY();
	}
}
