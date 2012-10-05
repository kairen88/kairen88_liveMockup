/*
 * Created on Mar 17, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package zz.csg.api;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

import zz.csg.api.constraints.IConstrainedDouble;
import zz.csg.api.edition.IGraphicObjectController;
import zz.utils.IPublicCloneable;
import zz.utils.properties.IRWProperty;
import zz.utils.properties.PropertyId;
import zz.utils.ui.IMouseAware;


/**
 * Generic graphic object. 
 * <li>A graphic object is capable of painting itself 
 * ({@link #paint(IGraphicObjectContext, Graphics2D, Area)}).
 * <li>Provides generic edition controller ({@link #getController(GraphicObjectContext)}).
 * @author gpothier
 */
public interface IGraphicObject extends IPublicCloneable
{
	public static final PropertyId<AffineTransform> TRANSFORM = 
		new PropertyId<AffineTransform> ("transform", true);
	
	public static final PropertyId<String> NAME = 
		new PropertyId<String> ("name", false);

	public static final PropertyId<Double> WEIGHT =
		new PropertyId<Double>("weight", false);
	
	public IGraphicContainer getParent ();

	/**
	 * A graphic object can keep references to any number of objects so that
	 * they are not garbage collected. It doesn't do anything with these object
	 * apart from keeping references to them.
	 * @param aObject A object to which to keep a reference.
	 */
	public void addReference(Object aObject);
	
	/**
	 * Removes a reference added by {@link #addReference(Object)}. 
	 */
	public void removeReference(Object aObject);
	
	/**
	 * The name of this graphic object.
	 */
	public IRWProperty<String> pName ();
	
	/**
	 * The wieght of this object, used to determine the z-order of
	 * objects in a container. Objects with greater weight "sink" to the bottom of
	 * the z axis, making them appear behind other objects.
	 */
	public IConstrainedDouble pWeight ();
	
	/**
	 * Sets the translation component of this object's transform.
	 */
	public void setPosition(double aX, double aY);
	
	/**
	 * Translates this graphic object's transform.
	 */
	public void translate(double aX, double aY);
	
	/**
	 * A transform to be applied to this object.
	 * The transforms creates a new coordinate system for this object.
	 * The bounds of the object ({@link #getBounds(GraphicObjectContext)},
	 * {@link IRectangularGraphicObject#pBounds()}) are in this new coordinate
	 * system.
	 * A null value means that no transform is used.
	 */
	public TransformProperty pTransform ();

	/**
	 * The given rectangle is transformed according this object's transform,
	 * and a rectangle entirely containing this transformed rectangle is returned.
	 */
	public Rectangle2D getTransformedBounds (Rectangle2D aBounds);

	/**
	 * Repaints this graphic object in all of its attached contexts.
	 */
	public void repaintAllContexts();
	
	/**
	 * Repaints this graphic object.
	 * This method simply calls {@link #repaint(Rectangle2D)} giving it
	 * this g.o. own bounds.
	 */
	public void repaint (GraphicObjectContext aContext);
	
	/**
	 * Repaints a rectangular region. 
	 * This method needs to find a concrete
	 * component that displays this graphic object. This is achieved by delegating
	 * the call to the parent.
	 * Two kind of subclasses should override this method:
	 * <li>Those that transform the coordinate system
	 * <li>Those that directly know a concrete component.
	 */
	public void repaint (GraphicObjectContext aContext, Rectangle2D aBounds);


	
	/**
	 * This method is called by the system when this graphic object 
	 * becomes attached, ie. it is in a subtree that is displayed.
	 * Overriders must call super.
	 */
	public void attached (GraphicObjectContext aContext);

	/**
	 * This method is called by the system when this graphic object 
	 * becomes not attached, ie. it is no more in a subtree that is displayed.
	 * Overriders must call super.
	 */
	public void detached (GraphicObjectContext aContext);

	/**
	 * Returns the contexts through which this graphic object is attached
	 * to a display.
	 */
	public Iterable<GraphicObjectContext> getAttachedContexts();

	
	/**
	 * Ensures that the content of this graphic object and its children is valid.
	 * This is useful for graphic objects that defer the creation of their content.
	 */
	public void checkValid();

	/**
	 * Invalidates the content of this graphic object; it will be revalidated
	 * during the next call to {@link #checkValid()}
	 */
	public void invalidate();
	
	/**
	 * Invalidates this object and its children.
	 */
	public void invalidateAll();
	
	/**
	 * Paints this graphic object on the specified graphics.
	 * @param aDisplay The display that is painting this object.
	 * @param aContext The context that permit to differentiate various
	 * representations of the same object.
	 * @param aVisibleArea The region of this graphic object that is potentially visible.
	 * It is different from the clipping region, which can be smaller than the visible
	 * region when repainting partially dirty zones. 
	 */
	public void paint (
			IDisplay aDisplay,
			GraphicObjectContext aContext,
			Graphics2D aGraphics, 
			Area aVisibleArea);
	
	/**
	 * Searches for a graphic object at the given position. If several children
	 * intersect at the given position, the frontmost child will be returned.
	 * If no child are found at this position, this method will return null.
	 * <p>
	 * The result of a pick is a {@link PickResult}, which contains a graphic
	 * object and its associated context.
	 */
	public PickResult pick (GraphicObjectContext aContext, Point2D aPoint);
	
	/**
	 * Returns a rectangle that entirely contains this g.o. 
	 */
	public Rectangle2D getBounds (GraphicObjectContext aContext);
	
	/**
	 * Transforms a point given in this object's root container's 
	 * coordinate system into this object's local coordinates.
	 * @param aPoint A point in the coordinate system of the root
	 * container
	 */
	public Point2D rootToLocal (GraphicObjectContext aContext, Point2D aPoint);
	
	/**
	 * Transforms a point given in this object's 
	 * coordinate system into a point in this object's
	 * root container's coordinates.
	 * @param aPoint A point in local coordinates
	 */
	public Point2D localToRoot (GraphicObjectContext aContext, Point2D aPoint);
	
	/**
	 * Transforms the given point into this object's coordinate system
	 * @param aPoint A point in the corrdinate system of the parent
	 * @param aDestination A point into which the result will be written.
	 * can be the same as the first parameter.
	 */
	public void localToChildren (Point2D aPoint, Point2D aDestination);
	
	/**
	 * The inverse of {@link #localToChildren(Point2D, Point2D)}.
	 * @param aDestination A point into which the result will be written.
	 * can be the same as the first parameter.
	 */
	public void childrenToLocal (Point2D aPoint, Point2D aDestination);
	
	/**
	 * Returns true iff the given point is inside this graphic object.
	 * The coordinates are given in this graphic object's coordinates system.
	 */
	public boolean isInside (GraphicObjectContext aContext, Point2D aPoint);
	
	/**
	 * Returns an object that permits to edit this graphic object,
	 * or null if not applicable.
	 */
	public IGraphicObjectController getController (GraphicObjectContext aContext);
	
	/**
	 * Plays, pauses or stops this graphic object and its descendants.
	 * This actually does something for time-aware objects (sounds, video...)
	 * @param aAction The action to perform.
	 */
	public void stream (GraphicObjectContext aContext, StreamAction aAction);
	
	/**
	 * Registers a listener that will be notified whenever the
	 * something changes within the subtree rooted at this
	 * graphic object
	 */
	public void addGraphicObjectListener (IGraphicObjectListener aListener);
	
	/**
	 * Removes a previously registered listener.
	 */
	public void removeGraphicObjectListener (IGraphicObjectListener aListener);
	
	/**
	 * Displays a Swing component on top of this graphic object.
	 */
	public void overlay (GraphicObjectContext aContext, JComponent aComponent);
	
	/**
	 * Removes a previously displayed component.
	 * @see #overlay(GraphicObjectContext, JComponent)
	 */
	public void remove (
			GraphicObjectContext aContext, 
			JComponent aComponent);


	/**
	 * We reproduce the methods of {@link IMouseAware}, but
	 * with a context argument.
	 * Indicates if this object is currently interested in mouse events.
	 * If not, the event will be passed to the closest mouse aware
	 * ancestor, exactly as if this object did not implement
	 * {@link IMouseAware}
	 */
	public boolean isMouseAware (GraphicObjectContext aContext);
	
	/**
	 * We reproduce the methods of {@link IMouseAware}, but
	 * with a context argument.
	 * Called when the mouse button is pressed on this object.
	 * @param aPoint The mouse coordinate in this object's coordinate system
	 * @return Whether to consume the event or not; if the event
	 * is not consumed it will be passed to another mouse aware object.
	 */
	public boolean mousePressed (GraphicObjectContext aContext, MouseEvent aEvent, Point2D aPoint);
	
	/**
	 * We reproduce the methods of {@link IMouseAware}, but
	 * with a context argument.
	 * Called when the mouse button is released on this object.
	 * @param aPoint The mouse coordinate in this object's coordinate system
	 * @return Whether to consume the event or not; if the event
	 * is not consumed it will be passed to another mouse aware object.
	 */
	public boolean mouseReleased (GraphicObjectContext aContext, MouseEvent aEvent, Point2D aPoint);
	
	/**
	 * We reproduce the methods of {@link IMouseAware}, but
	 * with a context argument.
	 * Called when the mouse button is pressed & released on this object,
	 * but only if no dragging occured.
	 * @param aPoint The mouse coordinate in this object's coordinate system
	 * @return Whether to consume the event or not; if the event
	 * is not consumed it will be passed to another mouse aware object.
	 */
	public boolean mouseClicked (GraphicObjectContext aContext, MouseEvent aEvent, Point2D aPoint);
	
	
	/**
	 * We reproduce the methods of {@link IMouseAware}, but
	 * with a context argument.
	 * Called when the mouse enters the bounds of this object.
	 * @param aEvent TODO
	 * @param aPoint The mouse coordinate in this object's coordinate system
	 */
	public void mouseEntered (GraphicObjectContext aContext, MouseEvent aEvent);

	/**
	 * We reproduce the methods of {@link IMouseAware}, but
	 * with a context argument.
	 * Called when the mouse exits the bounds of this object.
	 * @param aEvent TODO
	 * @param aPoint The mouse coordinate in this object's coordinate system
	 */
	public void mouseExited (GraphicObjectContext aContext, MouseEvent aEvent);
	
	/**
	 * We reproduce the methods of {@link IMouseAware}, but
	 * with a context argument.
	 * Called when the mouse moves within the bounds of this object.
	 * @param aEvent TODO
	 * @param aPoint The mouse coordinate in this object's coordinate system
	 * @return Whether to consume the event or not; if the event
	 * is not consumed it will be passed to another mouse aware object.
	 */
	public boolean mouseMoved (GraphicObjectContext aContext, MouseEvent aEvent, Point2D aPoint);

	/**
	 * We reproduce the methods of {@link IMouseAware}, but with a context
	 * argument.
	 */
	public boolean mouseWheelMoved(GraphicObjectContext aContext, MouseWheelEvent aEvent, Point2D aPoint);
	
	/**
	 * We reproduce the methods of {@link IMouseAware}, but
	 * with a context argument.
	 * Called when a drag gesture begins.
	 * Note that this method is called in addition to {@link #mousePressed(Point2D)},
	 * after it is detected that a drag operation starts.
	 * @param aPoint The mouse coordinate in this object's coordinate system
	 * @return Whether to consume the event or not; if the event
	 * is not consumed it will be passed to another mouse aware object.
	 */
	public boolean startDrag (GraphicObjectContext aContext, MouseEvent aEvent, Point2D aPoint);
	
	/**
	 * We reproduce the methods of {@link IMouseAware}, but
	 * with a context argument.
	 * Once a drag gesture has started, this method is called whenever
	 * the mouse moves.
	 * @param aPoint The mouse coordinate in this object's coordinate system
	 */
	public void drag (GraphicObjectContext aContext, Point2D aPoint);

	/**
	 * We reproduce the methods of {@link IMouseAware}, but
	 * with a context argument.
	 * Called when a drag gesture ends.
	 * Note that this method is called in addition to {@link #mouseReleased(Point2D)}
	 * @param aPoint The mouse coordinate in this object's coordinate system
	 */
	public void commitDrag (GraphicObjectContext aContext, Point2D aPoint);
	
	/**
	 * We reproduce the methods of {@link IMouseAware}, but
	 * with a context argument.
	 * Called when the user cancels a drag gesture.
	 */
	public void cancelDrag(GraphicObjectContext aContext);

	
}
