/*
 * Created on Apr 20, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package zz.csg.api;

import java.awt.geom.Rectangle2D;

import zz.csg.api.constraints.IConstrainedRectangle;
import zz.utils.properties.PropertyId;

/**
 * A base interface for graphic objects that are characterized by
 * their rectangular bounds (rectangles, ellipses...).
 * @author gpothier
 */
public interface IRectangularGraphicObject extends IGraphicObject
{
	public static final PropertyId<Rectangle2D> BOUNDS = 
		new PropertyId<Rectangle2D> ("bounds", false);

	/**
	 * The bounds of this object in the parent's coordinate system.
	 */
	public IConstrainedRectangle pBounds ();	
	
	/**
	 * Sets the widths and height of this obejct's bounds
	 * without affecting the x and y components.
	 */
	public void setSize(double aWidth, double aHeight);
	
//	/**
//	 * Sets the bounds of this object to be a specified property.
//	 * This can be used to force two object to share the property.
//	 * TRAIT: implementation should be provided in a trait, as this method is non-trivial.
//	 * @param aProperty A property to use for this object,
//	 * or null to create a new property, make this object's property
//	 * independant again.
//	 */
//	public void setBoundsProperty(IConstrainedRectangle aProperty);
}
