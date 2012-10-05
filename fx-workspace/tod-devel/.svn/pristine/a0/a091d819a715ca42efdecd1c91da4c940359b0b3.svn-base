/*
 * Created on May 30, 2004
 */
package zz.csg.api.edition;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import zz.csg.api.GraphicObjectContext;
import zz.csg.api.IGraphicObject;


/**
 * Interface for panels that displays a graphic object.
 * Permits to transform logical points to pixels and vice versa.
 * @author gpothier
 */
public interface IGraphicMapper
{
	/**
	 * Transforms a point given in logical coordinates into pixel 
	 * coordinates.
	 */
	public Point rootToPixel (Point2D aPoint);
	
	/**
	 * Transforms a rectangle in the root's logical coordinates
	 * to a rectangle in pixels.
	 */
	public Rectangle rootToPixel (Rectangle2D aRectangle);

	
	/**
	 * Converts a point given in pixel coordinates into
	 * logical coordinates.
	 */
	public Point2D pixelToRoot (Point aPoint);
	
	/**
	 * Computes the pixel coordinates of a point in the coordinate system
	 * of a given graphic object
	 */
	public Point localToPixel (GraphicObjectContext aContext, IGraphicObject aGraphicObject, Point2D aPoint);

	/**
	 * Computes the pixel coordinates of a rectangle in the coordinate system
	 * of a given graphic object
	 * @param aContext TODO
	 */
	public Rectangle localToPixel (GraphicObjectContext aContext, IGraphicObject aGraphicObject, Rectangle2D aRectangle);

	/**
	 * Returns the logical to pixel transform 
	 */
	public AffineTransform getTransform ();
}
