/*
 * Created on Mar 17, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package zz.csg.traits;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

import zz.csg.api.GraphicObjectContext;
import zz.csg.api.IGraphicObjectListener;
import zz.csg.api.PickResult;
import zz.csg.api.StreamAction;
import zz.csg.api.constraints.IConstrainedDouble;
import zz.csg.api.edition.IGraphicObjectController;
import zz.utils.IPublicCloneable;
import zz.utils.properties.IRWProperty;
import zz.utils.properties.PropertyId;


/**
 * Generic graphic object. 
 * <li>A graphic object is capable of painting itself 
 * ({@link #paint(IGraphicObjectContext, Graphics2D, Area)}).
 * <li>Provides generic edition controller ({@link #getController(GraphicObjectContext)}).
 * @author gpothier
 */
interface IGraphicObject extends IPublicCloneable
{
	public static final PropertyId<AffineTransform> TRANSFORM = 
		new PropertyId<AffineTransform> ("transform", true);
	
	public static final PropertyId<String> NAME = 
		new PropertyId<String> ("name", false);

	public static final PropertyId<Double> WEIGHT =
		new PropertyId<Double>("weight", false);
	
	public IGraphicContainer getParent ();

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
	 * A transform to be applied to the children coordinates.
	 * A null value means that no transform is used.
	 */
	public IRWProperty<AffineTransform> pTransform ();

	/**
	 * This method is called by the system when this graphic object 
	 * becomes attached, ie. it is in a subtree that is displayed
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
	 * Paints this graphic object on the specified graphics.
	 * @param aContext The context that permit to differentiate various
	 * representations of the same object.
	 */
	public void paint (GraphicObjectContext aContext, Graphics2D aGraphics);
	
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
	 * @param aPoint The mouse coordinate in this object's coordinate system
	 */
	public void mouseEntered (GraphicObjectContext aContext);

	/**
	 * We reproduce the methods of {@link IMouseAware}, but
	 * with a context argument.
	 * Called when the mouse exits the bounds of this object.
	 * @param aPoint The mouse coordinate in this object's coordinate system
	 */
	public void mouseExited (GraphicObjectContext aContext);
	
	/**
	 * We reproduce the methods of {@link IMouseAware}, but
	 * with a context argument.
	 * Called when the mouse moves within the bounds of this object.
	 * @param aPoint The mouse coordinate in this object's coordinate system
	 * @return Whether to consume the event or not; if the event
	 * is not consumed it will be passed to another mouse aware object.
	 */
	public boolean mouseMoved (GraphicObjectContext aContext, Point2D aPoint);

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
