/*
 * Created on Apr 21, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package zz.csg.impl;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import zz.csg.api.GraphicObjectContext;
import zz.csg.api.IDisplay;
import zz.csg.api.IGraphicContainer;
import zz.csg.api.IGraphicObject;
import zz.csg.api.PickResult;
import zz.csg.api.StreamAction;
import zz.csg.api.layout.ILayoutManager;
import zz.utils.Utils;
import zz.utils.properties.IListProperty;
import zz.utils.properties.IRWProperty;


/**
 * Base implementation of {@link zz.csg.api.IGraphicContainer} 
 * <li>Handles merging of children with inherited container.
 * <li>Propagates state changes to children.
 * <li>This class is based on {@link com.redcrea.ina.core.module.AbstractGenericModule}
 * <li>Does not handle the {@link zz.csg.api.IGraphicContainer#getGraphicObjectFactory()}
 * method, which must be handled by subclasses.
 * @author gpothier
 */
public abstract class AbstractGraphicContainer extends AbstractGraphicObject 
	implements IGraphicContainer
{
	private final ChildrenProperty pChildren = new ChildrenProperty();
	
	private ILayoutManager itsLayoutManager;
	
	public void setLayoutManager(ILayoutManager aLayoutManager)
	{
		if (itsLayoutManager != null) itsLayoutManager.uninstall();
		itsLayoutManager = aLayoutManager;
		invalidate();
		if (itsLayoutManager != null) itsLayoutManager.install(this);
	}

	protected boolean validate()
	{
		if (! isUpdateEnabled()) return false;
		
		for (Iterator<IGraphicObject> theIterator = getChildrenIterator();theIterator.hasNext();)
		{
			IGraphicObject theGraphicObject = theIterator.next();
			theGraphicObject.checkValid();
		}
		if (itsLayoutManager != null) itsLayoutManager.ensureLayout(); 
		return super.validate();
	}
	
	@Override
	public void checkValid()
	{
//		for (Iterator<IGraphicObject> theIterator = getChildrenIterator();theIterator.hasNext();)
//		{
//			IGraphicObject theGraphicObject = theIterator.next();
//			theGraphicObject.checkValid();
//		}
		super.checkValid();
	}
	
	@Override
	public void invalidateAll()
	{
		for (Iterator<IGraphicObject> theIterator = getChildrenIterator();theIterator.hasNext();)
		{
			IGraphicObject theGraphicObject = theIterator.next();
			theGraphicObject.invalidateAll();
		}

		super.invalidateAll();
	}
	
	public IListProperty<IGraphicObject> pChildren()
	{
		return pChildren;
	}
	
	/**
	 * Returns a safe iterator over this container's children
	 */
	public Iterator<IGraphicObject> getChildrenIterator ()
	{
		return pChildren().iterator();
	}
	
	/**
	 * Same content as {@link #getChildrenIterator()}, but reverse order.
	 */
	public Iterator<IGraphicObject> getReverseChildrenIterator ()
	{
		return pChildren().reverseIterator();
	}
	
	public IGraphicObject getChildByName(String aName)
	{
		for (Iterator theIterator = getChildrenIterator(); theIterator.hasNext();)
		{
			IGraphicObject theChild = (IGraphicObject) theIterator.next();
			if (Utils.equalOrBothNull(theChild.pName().get(), aName)) return theChild;
		}
		return null;
	}
	
	/**
	 * Indicates if a pick on this container should recurse into
	 * children. This method returns true by default but subclasses
	 * can override it.
	 */
	protected boolean pickChildrenAllowed ()
	{
		return true;
	}

	public PickResult pick(GraphicObjectContext aContext, Point2D aPoint)
	{
		// Compute transformed coordinates.
		Point2D theTransformedPoint = new Point2D.Double();
		localToChildren(aPoint, theTransformedPoint);
		
		if (pickChildrenAllowed())
		{
			// Search frontmost child.
			for (Iterator theIterator = getReverseChildrenIterator(); theIterator.hasNext();)
			{
				IGraphicObject theChild = (IGraphicObject) theIterator.next();
				PickResult theResult = theChild.pick(aContext, theTransformedPoint);
				if (theResult != null) return theResult;
			}

			// No child found
			return null;
		}
		else
		{
			if (isInside(aContext, aPoint)) return new PickResult(this, aContext);
			else return null;
		}
	}
	
	/**
	 * Paints the background of this graphic container. This method is called before 
	 * children are painted. By default it does nothing bu subclasses can override it.
	 */
	protected void paintBackground(
			IDisplay aDisplay, 
			GraphicObjectContext aContext, 
			Graphics2D aGraphics, 
			Area aVisibleArea)
	{
		
	}

	/**
	 * Paints a child of this graphic object. By default it simply calls 
	 * {@link IGraphicObject#paint(IDisplay, GraphicObjectContext, Graphics2D, Area)}
	 * but subclasses can override it.
	 */
	protected void paintChild(
			IDisplay aDisplay, 
			GraphicObjectContext aContext, 
			IGraphicObject aChild, 
			Graphics2D aGraphics, 
			Area aVisibleArea)
	{
		aChild.paint(aDisplay, aContext, aGraphics, aVisibleArea);
	}
	
	/**
	 * Paints the foreground of this graphic container. This method is called after 
	 * children are painted. By default it does nothing bu subclasses can override it.
	 */
	protected void paintForeground(
			IDisplay aDisplay, 
			GraphicObjectContext aContext, 
			Graphics2D aGraphics, 
			Area aVisibleArea)
	{
		
	}
	
	
	/**
	 * Overriding this method is discouraged. Instead, consider overriding one
	 * of {@link #paintBackground(GraphicObjectContext, Graphics2D, Area)},
	 * {@link #paintChild(GraphicObjectContext, IGraphicObject, Graphics2D, Area)}
	 * or {@link #paintForeground(GraphicObjectContext, Graphics2D, Area)}
	 */
	protected void paintTransformed(IDisplay aDisplay, GraphicObjectContext aContext, Graphics2D aGraphics, Area aVisibleArea)
	{
		checkValid();

		paintBackground(aDisplay, aContext, aGraphics, aVisibleArea);
		
		// Paint children
		// We make a copy of the children in order to avoid
		// concurrent modification.
		List<IGraphicObject> theTempChildren = new ArrayList<IGraphicObject>();
		Utils.fillCollection(theTempChildren, getChildrenIterator());

		for (IGraphicObject theChild : theTempChildren)
		{
			theChild.checkValid();
			paintChild(aDisplay, aContext, theChild, aGraphics, aVisibleArea);
		}

		
		paintForeground(aDisplay, aContext, aGraphics, aVisibleArea);
	}

	public Rectangle2D computeBounds (GraphicObjectContext aContext)
	{
		if (pChildren().isEmpty()) return new Rectangle2D.Double();
		
		double theMinX = Double.MAX_VALUE;
		double theMinY = Double.MAX_VALUE;
		double theMaxX = -Double.MAX_VALUE;
		double theMaxY = -Double.MAX_VALUE;
		
		for (Iterator theIterator = getChildrenIterator(); theIterator.hasNext();)
		{
			IGraphicObject theChild = (IGraphicObject) theIterator.next();
			Rectangle2D theBounds = theChild.getBounds(aContext);
			
			theMinX = Math.min(theMinX, theBounds.getMinX());
			theMinY = Math.min(theMinY, theBounds.getMinY());
			theMaxX = Math.max(theMaxX, theBounds.getMaxX());
			theMaxY = Math.max(theMaxY, theBounds.getMaxY());
		}
		
		return new Rectangle2D.Double (theMinX, theMinY, theMaxX - theMinX, theMaxY - theMinY);
	}
	
	/**
	 * returns {@link #computeBounds(GraphicObjectContext)}
	 */
	public Rectangle2D getBounds (GraphicObjectContext aContext)
	{
		checkValid();
		return computeBounds(aContext);
	}
	
	public Rectangle2D getDescendantBounds(IGraphicObject aGraphicObject)
	{
		Rectangle2D theBounds = aGraphicObject.getBounds(null);
		if (aGraphicObject == this) return theBounds;
		
		theBounds = aGraphicObject.getTransformedBounds(theBounds);
		IGraphicContainer theCurrentParent = aGraphicObject.getParent();
		
		while (theCurrentParent != null)
		{
			if (theCurrentParent == this) return theBounds;
			theBounds = theCurrentParent.getTransformedBounds(theBounds);
			theCurrentParent = theCurrentParent.getParent();
		}
		
		throw new RuntimeException("Child not amongst descendants");
	}

	/**
	 * Called whenever a descendant graphic object changes.
	 * Overriders should call super.
	 * @param aProperty The property that changed.
	 */
	protected void graphicObjectChanged (
			IGraphicObject aGraphicObject, 
			IRWProperty aProperty)
	{
		if (aProperty == aGraphicObject.pTransform()) repaintAllContexts();
		fireChanged(aGraphicObject, aProperty);
	}
	
	/**
	 * This method is called whenever a graphic object is added to this container
	 */
	protected void graphicObjectAdded (IGraphicObject aGraphicObject)
	{
		AbstractGraphicObject theGraphicObject = (AbstractGraphicObject) aGraphicObject;
		
		assert aGraphicObject.getParent() == null;
		theGraphicObject.setParent(this);
		theGraphicObject.added();
		fireGraphicObjectAdded(this, aGraphicObject);
		
		for (GraphicObjectContext theContext : getAttachedContexts())
			aGraphicObject.attached(theContext);
		
		invalidate();
		repaintAllContexts();
	}
	
	/**
	 * This method is called whenever a graphic object is removed from this container
	 */
	protected void graphicObjectRemoved (IGraphicObject aGraphicObject)
	{
		AbstractGraphicObject theGraphicObject = (AbstractGraphicObject) aGraphicObject;

		assert aGraphicObject.getParent() == this;
		theGraphicObject.setParent(null);
		theGraphicObject.removed();
		fireGraphicObjectRemoved(this, aGraphicObject);
		
		for (GraphicObjectContext theContext : getAttachedContexts())
			aGraphicObject.detached(theContext);
		
		invalidate();
		repaintAllContexts();
	}
	

	public void attached(GraphicObjectContext aContext)
	{
		super.attached(aContext);
		for (Iterator theIterator = getChildrenIterator(); theIterator.hasNext();)
		{
			AbstractGraphicObject theGraphicObject = (AbstractGraphicObject) theIterator.next();
			theGraphicObject.attached(aContext);
		}
	}
	
	
	public void detached(GraphicObjectContext aContext)
	{
		super.detached(aContext);
		for (Iterator theIterator = getChildrenIterator(); theIterator.hasNext();)
		{
			AbstractGraphicObject theGraphicObject = (AbstractGraphicObject) theIterator.next();
			theGraphicObject.detached(aContext);
		}
	}
	
	
	public Object clone()
	{
		return clone(true);
	}
	
	
	/**
	 * Internal version that permits to avoid cloning children.
	 * @param aCloneChildren Whether to clone children. If false, the clone will
	 * have no children.
	 */
	public AbstractGraphicContainer clone(boolean aCloneChildren)
	{
		AbstractGraphicContainer theClone = (AbstractGraphicContainer) super.clone();
		theClone.pChildren.grab(pChildren, aCloneChildren);

		return theClone;
	}

	/**
	 * Repaints this graphic object. Adjust the bounds so that they match the transform.
	 */
	public void repaint (GraphicObjectContext aContext)
	{
		Rectangle2D theBounds = getBounds(aContext);
		theBounds = theBounds != null
				? new Rectangle2D.Double (0, 0, theBounds.getWidth(), theBounds.getHeight())
				: null;
		repaint(aContext, theBounds);
	}

	/**
	 * We propagate to all children.
	 */
	public void stream(GraphicObjectContext aContext, StreamAction aAction)
	{
		for (Iterator theIterator = getChildrenIterator(); theIterator.hasNext();)
		{
			IGraphicObject theChild = (IGraphicObject) theIterator.next();
			theChild.stream(aContext, aAction);
		}
	}
	
	private class ChildrenProperty extends GOListProperty<IGraphicObject>
	{
		private boolean itsGrabbing = false;
		
		public ChildrenProperty()
		{
			super(AbstractGraphicContainer.this, CHILDREN);
		}

		/**
		 * Grabs and clones children from another children list.
		 * @see AbstractGraphicContainer#clone(boolean)
		 */
		public void grab(ChildrenProperty aChildren, boolean aCloneChildren)
		{
			itsGrabbing = true;
			
			reset();

			// We clone all the children
			if (aCloneChildren) for (IGraphicObject theChild : aChildren)
			{
				AbstractGraphicObject theChildClone = (AbstractGraphicObject) theChild.clone();
				theChildClone.setParent(AbstractGraphicContainer.this);
				add(theChildClone);
			}

			
			itsGrabbing = false;
		}
		
		/**
		 * Resets the children list, overriding normal setter.
		 */
		public void reset()
		{
			super.reset();
		}
		
		protected void elementAdded(int aIndex, IGraphicObject aElement)
		{
			if (! itsGrabbing) graphicObjectAdded(aElement);
		}

		protected void elementRemoved(int aIndex, IGraphicObject aElement)
		{
			if (! itsGrabbing) graphicObjectRemoved(aElement);
		}
	}
}

