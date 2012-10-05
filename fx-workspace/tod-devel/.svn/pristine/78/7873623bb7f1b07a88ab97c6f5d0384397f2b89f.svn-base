package net.basekit.widgets;

import java.util.*;

import javax.vecmath.*;

import net.basekit.Message;
import net.basekit.Observer;
import net.basekit.layoutdelegates.LayoutDelegate;
import net.basekit.messages.WidgetMessage;
import net.basekit.shapes.*;
import net.basekit.shapes.utils.ShapeUtils;
import net.basekit.utils.Logger;
import net.basekit.world.World;
import zz.utils.Utils;

import com.xith3d.scenegraph.*;

/*
 * Created on Jan 11, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */

/**
 * The base class of the widget hierarchy. A widget is comprised of:
 * <li>A set of children widgets.
 * See {@link #addWidget(Widget)}, {@link #removeWidget(Widget)}</li>
 * <li>Aptional additional content nodes.
 * See {@link #addAdditionalContent(Node)}</li>
 * See {@link #removeAdditionalContent(Node)}</li>
 * <p>
 * Widgets provide observer/observable mechanism: see
 * {@link #addObserver(Observer)}, {@link #removeObserver(Observer)} 
 * and {@link #fire(WidgetMessage)} methods.
 * Moreover, a widget always observe itself, ie it is useless to register
 * a widget as its own observer, as it already is. Just override
 * {@link #processMessage(Message)}
 * <p>
 * Widgets support layout delegates that disposes their children.
 * See {@link #setLayoutDelegate(LayoutDelegate)}, {@link net.basekit.layoutdelegates.LayoutDelegate}
 * The layout delegate lays out the children within the space defined by the size of this
 * widget minus its margins ({@link #getMargins()}.
 * <p>
 * Messages
 * <li>{@link net.basekit.devices.mouse.messages.MouseMessage} subclasses
 *  
 * @author gpothier
 */
public class Widget extends TransformGroup implements Observer
{
	public static final Logger.Category LOG_WIDGET_LAYOUT = new Logger.Category ("widget.layout");
	public static final Logger.Category LOG_WIDGET_STRUCTURE = new Logger.Category ("widget.structure");
	public static final Logger.Category LOG_WIDGET_RENDER = new Logger.Category ("widget.render");

	private LayoutDelegate itsLayoutDelegate;
	private boolean itsVisible;
	private Vector3f itsPreferredSize;
	private Vector3f itsMaximumSize;
	private Vector3f itsMinimumSize;
	
	/**
	 * Current size of the widget.
	 */
	private Vector3f itsSize;

	private Margins itsMargins = new Margins (0);
	
	/**
	 * This group contains all the children widgets of this widget.
	 */
	private Group itsWidgetsContainer;
	
	/**
	 * Permits to provide additional content apart from the widgets.
	 */
	private Group itsAdditionalContainer;

	/**
	 * An optional border to display around this widget.
	 */
	private Border itsBorder;

	private List itsObservers;
	
	/**
	 * A buffer used internally for some operations.
	 */
	private static Vector3f BUFFER = new Vector3f ();
	
	/**
	 * This shape is an invisible child of the widget that represents its bounds
	 * for picking purposes.
	 */
	private Box itsPickingBounds;
	
	/**
	 * Whether this widget should receive mouse (or pointer) events.
	 * A widget that doesn't receive mouse events doesn't prevent
	 * its children from receiving them.
	 */
	private boolean itsPickingAware = true;
	
	/**
	 * Whether the layout of this widget is valid or needs to be performed.
	 */
	private boolean itsLayoutValid = false;
	
	/**
	 * If true, this widget will always have its size equal to its preferred size.
	 */
	private boolean itsPacked;

	public Widget()
	{
		setPickable(true);
		
		itsWidgetsContainer = new Group ();
		setChildrenPickable(true);
		addChild(itsWidgetsContainer);

		itsAdditionalContainer = new Group ();
		setAdditionalContentPickable(false);
		addChild(itsAdditionalContainer);
		
		itsPickingBounds = new Box ();
		itsPickingBounds.setPickable(true);
		setRenderBounds(true);
		
		Appearance theAppearance = new Appearance ();
		theAppearance.setPolygonAttributes(
				new PolygonAttributes(
						PolygonAttributes.POLYGON_FILL,
						PolygonAttributes.CULL_NONE,
						0));
				
		TransparencyAttributes theTransparencyAttributes = new TransparencyAttributes ();
		theTransparencyAttributes.setMode(TransparencyAttributes.BLENDED);
		theTransparencyAttributes.setTransparency(0.8f);
		theAppearance.setTransparencyAttributes(theTransparencyAttributes);
		
		itsPickingBounds.setAppearance(theAppearance);
		
		addChild(itsPickingBounds);
	}
	
	public final Widget getParentWidget ()
	{
		Node theParent = getParent();
		if (theParent == null) return null;
		else
		{
			theParent = theParent.getParent();
			return theParent instanceof Widget 
				? (Widget) theParent
				: null;
		}
	}
	
	/**
	 * Returns the root widget this widget is a descendant of.
	 * @return The root widget, or null if there is none.
	 */
	public final RootWidget getRootWidget ()
	{
		Widget theWidget = this;
		while (theWidget != null)
		{
			if (theWidget instanceof RootWidget) return (RootWidget) theWidget;
			
			theWidget = theWidget.getParentWidget();
		}
		return null;
	}
	
	/**
	 * Returns the world this widget is part of.
	 * @return The world, or null if this widget is not within a world.
	 */
	public World getWorld ()
	{
		RootWidget theRootWidget = getRootWidget();
		return theRootWidget != null ? theRootWidget.getWorld() : null;
	}
	
	/**
	 * Returns the shape that represents this widget picking-wise.
	 */
	public PickableShape getPickingBounds ()
	{
		return itsPickingBounds;
	}
	
	/**
	 * Set to true to materialize the picking bounds of this widgets.
	 * By default, bounds are not rendered.
	 */
	public void setRenderBounds (boolean aRender)
	{
		((Shape3D) getPickingBounds ()).setRenderable (aRender);
	}
	
	/**
	 * Sets whether children widgets can be picked.
	 * If false, children widgets cannot be picked even if they declare themselves 
	 * pickables.
	 */
	public void setChildrenPickable (boolean aPickable) 
	{
		itsWidgetsContainer.setPickable(aPickable);
	}

	/**
	 * Sets whether nodes of additional content can be picked.
	 * If false, no additional node can be picked even if it declares itself 
	 * pickable.
	 */
	public void setAdditionalContentPickable (boolean aPickable) 
	{
		itsAdditionalContainer.setPickable(aPickable);
	}

	/**
	 * Called when this widget is added to a parent.
	 * Subclasses that override this method must call super.
	 */
	protected void addNotify ()
	{
		if (itsSize == null) setSize(getPreferredSize());
		invalidateChildrenLayout ();
	}
	
	/**
	 * Called when this widget is removed from a parent.
	 */
	protected void removeNotify ()
	{
	}
	
	
	/**
	 * Adds a child widget without layout info.
	 */
	public final void addWidget (Widget aWidget)
	{
		addWidget(aWidget, null);
	}

	/**
	 * Adds a child widget.
	 * @param aLayoutInfo An additional layout info that will be passed to the layout delegate.
	 */
	public final void addWidget (Widget aWidget, Object aLayoutInfo)
	{
		assert itsLayoutDelegate != null || aLayoutInfo == null; //It is an error if we have layout info and no layout delegate
		itsWidgetsContainer.addChild(aWidget);
		if (itsLayoutDelegate != null) itsLayoutDelegate.widgetAdded(aWidget, aLayoutInfo);
		aWidget.addNotify();
		
		invalidateLayout();
	}
	
	/**
	 * Removes a previously added widget.
	 */
	public final void removeWidget (Widget aWidget)
	{
		itsWidgetsContainer.removeChild(aWidget);
		if (itsLayoutDelegate != null) itsLayoutDelegate.widgetRemoved(aWidget);
		aWidget.removeNotify();

		invalidateLayout();
	}
	
	/**
	 * Removes all the widgets.
	 */
	public final void clearWidgets ()
	{
		for (Iterator theIterator = getWidgetsIterator(); theIterator.hasNext();)
		{
			Widget theWidget = (Widget) theIterator.next();
			if (itsLayoutDelegate != null) itsLayoutDelegate.widgetRemoved(theWidget);
			theWidget.removeNotify();
		}
		itsWidgetsContainer.removeAllChildren ();
		invalidateLayout ();
	}
	
	/**
	 * Removes all the additional content.
	 */
	public final void clearAdditionalContent ()
	{
		itsAdditionalContainer.removeAllChildren();
	}
	
	/**
	 * Returns an iterator over all the children widgets.
	 * @return
	 */
	public final Iterator getWidgetsIterator ()
	{
		return itsWidgetsContainer.getChildren().iterator();
	}
	
	/**
	 * Retrieves the child widget at the specified index.
	 */
	public final Widget getWidget (int aIndex)
	{
		return (Widget) itsWidgetsContainer.getChild(aIndex);
	}
	
	/**
	 * Notifies the system that the hierarchy containing this widget needs
	 * to be redrawn as soon as possible.
	 */
	public final void redraw ()
	{
		redraw(0);
	}
	
	/**
	 * Notifies the system that the hierarchy containing this widget needs
	 * to be redrawn. Redraw will not occur before the specified delay.
	 */
	public void redraw (long aMilliseconds)
	{
		RootWidget theRootWidget = getRootWidget();
		if (theRootWidget != null) theRootWidget.redraw (aMilliseconds);
	}
	
	/**
	 * Marks this widget's layout invalid.
	 * The layout will be recalculated just before the next redrawing.
	 */
	public final void invalidateLayout ()
	{
		itsLayoutValid = false;
		World theWorld = getWorld();
		if (theWorld != null)
		{
			theWorld.getLayoutManager().schedule(this);
			redraw();
		}
	}
	
	/**
	 * Invalidates the layout of thjis widget and all of its children.
	 */
	private void invalidateChildrenLayout ()
	{
		invalidateLayout();
		for (Iterator theIterator = getWidgetsIterator(); theIterator.hasNext();)
		{
			Widget theWidget = (Widget) theIterator.next();
			theWidget.invalidateChildrenLayout();
		}
	}
	
	/**
	 * Notifies the system that the size of this widget changed
	 * and thus its layout as well as its parent's layout might be invalid.
	 * The layout will be recalculated just before the next redrawing.
	 */
	public final void invalidateSize ()
	{
		invalidateLayout();
		Widget theParentWidget = getParentWidget();
		if (theParentWidget != null) theParentWidget.invalidateLayout();
	}
	
	/**
	 * Recalculates the layout of this widget if necessary.
	 * TODO: We could make this checking mechanism more generic: not limited to layout.
	 */
	public final void checkLayout ()
	{
		if (itsLayoutDelegate != null && ! itsLayoutValid)
		{
			Vector3f theCheckedSize = itsPacked ? getPreferredSize () : getSize();
			theCheckedSize = checkSize(theCheckedSize);
			if (!setSize(theCheckedSize)) layout (); // If the size changed we don't need to layout now
		}
	}
	
	/**
	 * Performs the layout of this widget. Checks that 
	 * its size is at least the minimum size.
	 */
	private void layout ()
	{
		assert Logger.log (LOG_WIDGET_LAYOUT, "Widget.layout()");

		if (itsLayoutDelegate != null) itsLayoutDelegate.layout();
		itsLayoutValid = true;
	}
	
	/**
	 * Ensures that the size of this widget is at least its minimal size.
	 * If so, it returns null; otherwise returns a valid size.
	 * @return Whether the size was correct.
	 */
	private Vector3f checkSize (Vector3f aSize)
	{
		if (aSize == null) return null;
		
		Vector3f theNewSize = new Vector3f (aSize);
		Vector3f theMinSize = getMinimumSize();
		
		if (theNewSize.x < theMinSize.x) theNewSize.x = theMinSize.x;
		if (theNewSize.y < theMinSize.y) theNewSize.y = theMinSize.y;
		if (theNewSize.z < theMinSize.z) theNewSize.z = theMinSize.z;
		
		return theNewSize;
	}

	/**
	 * Returns the difference between the origin and the furthest widget.
	 */
	private Vector3f computeTotalSize ()
	{
		Vector3f theResult = new Vector3f ();
		for (Iterator theIterator = getWidgetsIterator(); theIterator.hasNext();)
		{
			Widget theWidget = (Widget) theIterator.next();
			Point3f thePosition = theWidget.getPosition();
			Vector3f theSize = theWidget.getSize();
			
			theResult.x = Math.max (theResult.x, thePosition.x + theSize.x);
			theResult.y = Math.max (theResult.y, thePosition.y + theSize.y);
			theResult.z = Math.max (theResult.z, thePosition.z + theSize.z);
		}
		
		return theResult;
	}

	public boolean isPacked ()
	{
		return itsPacked;
	}

	public void setPacked (boolean aPacked)
	{
		itsPacked = aPacked;
	}

	/**
	 * Returns the maximum size. If a specific maximum size has been set by {@link #setMaximumSize(Vector3f)},
	 * it will be returned; otherwise, the call is delegated to the layout delegate.
	 * @return The maximum size of this widget. It is guaranteed to be greater than the minimum size, even
	 * if it was user-specified.
	 */
	public final Vector3f getMaximumSize()
	{
		Vector3f theSize = itsMaximumSize != null ? itsMaximumSize : computeMaximumSize();
		return checkSize(theSize);
	}
	
	protected Vector3f computeMaximumSize ()
	{
		if (itsLayoutDelegate != null) return itsLayoutDelegate.computeMaximumSize();
		else return computeTotalSize();
	}

	public void setMaximumSize(Vector3f aMaximumSize)
	{
		itsMaximumSize = aMaximumSize;
	}

	/**
	 * Returns the minimum size. If a specific minimum size has been set by {@link #setMinimumSize(Vector3f)},
	 * it will be returned; otherwise, the call is delegated to the layout delegate.
	 */
	public final Vector3f getMinimumSize()
	{
		if (itsMinimumSize != null) return itsMinimumSize;
		else return computeMinimumSize(); 
	}
	
	protected Vector3f computeMinimumSize ()
	{
		if (itsLayoutDelegate != null) return itsLayoutDelegate.computeMinimumSize();
		else return computeTotalSize();
	}

	/**
	 * Sets the minimum size. It is illegal to call this method if there
	 * is a layout delegate.
	 */
	public void setMinimumSize(Vector3f aMinimumSize)
	{
		assert itsLayoutDelegate == null;
		itsMinimumSize = aMinimumSize;
		invalidateLayout();
	}

	/**
	 * Returns the preferred size. If a specific preferred size has been set by {@link #setPreferredSize(Vector3f)},
	 * it will be returned; otherwise, the call is delegated to the layout delegate.
	 * @return The preferred size of this widget. It is guaranteed to be greater than the minimum size, even
	 * if it was user-specified.
	 */
	public final Vector3f getPreferredSize()
	{
		Vector3f theSize = itsPreferredSize != null ? itsPreferredSize : computePreferredSize();
		return checkSize(theSize);
	}
	
	protected Vector3f computePreferredSize ()
	{
		if (itsLayoutDelegate != null) return itsLayoutDelegate.computePreferredSize();
		else return computeTotalSize();
	}

	public void setPreferredSize(Vector3f aPreferredSize)
	{
		itsPreferredSize = aPreferredSize;
	}

	/**
	 * Returns the current size of the widget. Might be null.
	 */
	public final Vector3f getSize()
	{
		return itsSize;
	}

	/**
	 * Sets the current size of this widget. This method should be used 
	 * only in the following cases:
	 * <li>For internal use by the low level widget classes.
	 * <li>For layout delegates.
	 * <li>For widgets whose parent has no layout manager.
	 * <p>
	 * In general, clients should use the {@link #setPreferredSize(Vector3f)} method.
	 * @return Whether the new size is different from the previous size.
	 * TODO: implement the new size != size check with an around advice.
	 */
	public boolean setSize(Tuple3f aSize)
	{
		if (Utils.different(aSize, itsSize))
		{
			itsSize = ShapeUtils.toVector(aSize);
	
			itsPickingBounds.setSize(itsSize);
			if (itsBorder != null) itsBorder.setSize (itsSize);
	
			//When we changed our size, we need to relayout.
			invalidateSize();
			
			return true;
		}
		else return false;
	}

	
	
	/**
	 * Returns the current position of this widget.
	 */
	public final Point3f getPosition ()
	{
		getTransform().getTranslation(BUFFER);
		return new Point3f (BUFFER);
	}
	
	/**
	 * Sets the position of this widget.
	 * @param aPosition A new position. The value of this argument will be copied; modifying
	 * its value after the call to this function has no effect on the widget.
	 */
	public void setPosition (Tuple3f aPosition)
	{
		Transform3D theTransform = getTransform();
		theTransform.setTranslation(ShapeUtils.toVector(aPosition));
		setTransform(theTransform);
	}
	
	/**
	 * Sets the scale of this widget.
	 * @param aScale A new scale. The value of this argument will be copied; modifying
	 * its value after the call to this function has no effect on the widget.
	 */
	public void setScale (Tuple3f aScale)
	{
		Transform3D theTransform = getTransform();
		theTransform.setScale(ShapeUtils.toVector(aScale));
		setTransform(theTransform);
	}
	
	public void setScale (float aScale)
	{
		Transform3D theTransform = getTransform();
		theTransform.setScale(aScale);
		setTransform(theTransform);
	}
	
	public final boolean isVisible()
	{
		return itsVisible;
	}

	public void setVisible(boolean aVisible)
	{
		itsVisible = aVisible;
	}
	
	/**
	 * Adds a node to the additional content of the widget. 
	 */
	public void addAdditionalContent (Node aAdditionalContent)
	{
		itsAdditionalContainer.addChild(aAdditionalContent);
	}
	
	/**
	 * Removes a node from the additional content of the widget. 
	 */
	public void removeAdditionalContent (Node aAdditionalContent)
	{
		itsAdditionalContainer.removeChild(aAdditionalContent);
	}

	public Border getBorder ()
	{
		return itsBorder;
	}

	public void setBorder (Border aBorder)
	{
		if (itsBorder != null) removeChild ((Node) itsBorder);
		itsBorder = aBorder;
		if (itsBorder != null)
		{
			addChild ((Node) itsBorder);
			setMargins (itsBorder.getMargins ());
		}
		redraw ();
	}

	/**
	 * @return Returns the layoutDelegate.
	 */
	public final LayoutDelegate getLayoutDelegate()
	{
		return itsLayoutDelegate;
	}

	/**
	 * @param aLayoutDelegate The layoutDelegate to set.
	 */
	public void setLayoutDelegate(LayoutDelegate aLayoutDelegate)
	{
		itsLayoutDelegate = aLayoutDelegate;
		itsLayoutDelegate.setWidget(this);
		invalidateLayout();
	}
	
	public final void addObserver (Observer aListener)
	{
		if (itsObservers == null) itsObservers = new ArrayList (2);
		itsObservers.add (aListener);
	}
	
	public final void removeObserver (Observer aListener)
	{
		itsObservers.remove (aListener);
	}

	/**
	 * Sends the specified message to all registered observers.
	 */
	public final void fire (WidgetMessage aMessage)
	{
		assert aMessage.getSource() == this;
		processMessage(aMessage);
		if (itsObservers != null) for (Iterator theIterator = itsObservers.iterator();theIterator.hasNext();)
		{
			Observer theObserver = (Observer) theIterator.next();
			theObserver.processMessage(aMessage);
		}
	}

	/**
	 * A widget always observes itself. This method will be called before any other observer
	 * by the {@link #fire(WidgetMessage)} method.
	 * Subclasses that override this method must call super.
	 * (Although it does nothing for now, it might do something in the future). 
	 */
	public void processMessage (Message aMessage)
	{
	}
	
	public final Margins getMargins ()
	{
		return itsMargins;
	}
	
	public void setMargins (Margins aWidgetMargin)
	{
		itsMargins = aWidgetMargin;
	}
	
	public boolean isPickingAware ()
	{
		return itsPickingAware;
	}
	public void setPickingAware (boolean aPickingAware)
	{
		itsPickingAware = aPickingAware;
	}
	
	/**
	 * Ensures the equality rule between widgets is identity
	 */
	public final boolean equals (Object aObj)
	{
		return aObj == this;
	}
	
	
	/**
	 * Ensures the hascode is consistent with equals.
	 */
	public final int hashCode ()
	{
		return super.hashCode();
	}
}
