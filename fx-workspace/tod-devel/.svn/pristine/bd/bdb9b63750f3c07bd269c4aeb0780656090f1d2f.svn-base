/*
 * Created on May 29, 2004
 */
package zz.csg.api.edition;

import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import zz.csg.api.IGraphicObject;


/**
 * A handle that can be used to edit one or more positional properties of
 * a graphic object. 
 * <li>It can have a graphic representation (see {@link #paint(IGraphicMapper, Graphics2D)}).
 * <li>All coordinates in a handle are in the same coordinate system
 * as the corresponding graphic object.
 * <li>The method that might need to work with pixels are provided
 * a {@link zz.csg.api.edition.IGraphicMapper}.
 * @author gpothier
 */
public interface IHandle
{
	/**
	 * Returns the graphic object to which pertains this handle.
	 */
	public IGraphicObject getGraphicObject ();
	
	/**
	 * Returns true if the given point is inside this handle.
	 */
	public boolean isInside (IGraphicMapper aMapper, Point2D aPoint);
	
	/**
	 * Returns the origin of this handle. This point does not 
	 * necessarily reflects a visible element, but is used as
	 * a reference for mouse operations.
	 */
	public Point2D getOrigin ();
	
	/**
	 * This method is called when this handle is about to be moved
	 */
	public void startMovement();
	
	/**
	 * Moves the origin of this handle to the specified position.
	 * This method is called between calls to {@link #startMovement()}
	 * and {@link #endMovement()}
	 */
	public void move (Point2D aPoint);
	
	/**
	 * This method is called after this handle has moved.
	 */
	public void endMovement();
	
	/**
	 * Returns the cursor that must be used when the mouse is inside
	 * this handle; 
	 */
	public Cursor getCursor ();

	/**
	 * Paints this handle. The provided graphics is NOT in the coordinate 
	 * system of the corresponding graphic object, but in its root
	 * container's coordinate system: we don't want to scale handles.
	 */
	public void paint (IGraphicMapper aMapper, Graphics2D aGraphics);
}
