/*
 * Created on Apr 20, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package zz.csg.impl;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.WeakHashMap;

import javax.swing.JComponent;

import zz.csg.Log;
import zz.csg.api.GraphicObjectContext;
import zz.csg.api.GraphicUtils;
import zz.csg.api.IDisplay;
import zz.csg.api.IGraphicContainer;
import zz.csg.api.IGraphicObject;
import zz.csg.api.IGraphicObjectListener;
import zz.csg.api.IRectangularGraphicObject;
import zz.csg.api.PickResult;
import zz.csg.api.StreamAction;
import zz.csg.api.TransformProperty;
import zz.csg.api.constraints.IConstrainedDouble;
import zz.csg.api.edition.IGraphicObjectController;
import zz.csg.impl.constraints.ConstrainedDouble;
import zz.csg.impl.constraints.ConstrainedPoint;
import zz.csg.impl.constraints.ConstrainedRectangle;
import zz.utils.PublicCloneable;
import zz.utils.ReverseIteratorWrapper;
import zz.utils.notification.ObservationCenter;
import zz.utils.properties.AbstractProperty;
import zz.utils.properties.ArrayListProperty;
import zz.utils.properties.IRWProperty;
import zz.utils.properties.PropertyId;
import zz.utils.properties.PropertyUtils;
import zz.utils.properties.SimpleRWProperty;


/**
 * Provides base behaviour for implementing graphic objects.
 * <li>A graphic object can be incompletely defined and in this case inherits
 * missing properties from an inherited graphic object. 
 * A g.o. without parent must be completely defined.
 * An attempt to set an undefined property will cause its definition
 * (ie. the parent property will not be modified, but overidden).
 * @author gpothier
 */
public abstract class AbstractGraphicObject extends PublicCloneable
	implements IGraphicObject
{
	private final TransformProperty pTransform = new GOTransformProperty(this, TRANSFORM);
	private final IRWProperty<String> pName = createProperty(NAME);
	private final IConstrainedDouble pWeight = new GOConstrainedDouble(this, WEIGHT, 0.0);
	
	
	private IGraphicContainer itsParent;
	
	private List<IGraphicObjectListener> itsListeners;
	
	/**
	 * The list of contexts through which this graphic object
	 * is attached to a display.
	 */
	private List<GraphicObjectContext> itsAttachedContexts;

	/**
	 * Maps contexts to controllers.
	 */
	private WeakHashMap<GraphicObjectContext, IGraphicObjectController> itsControllers;
	
	private List<Object> itsReferences;
	
	private int itsUpdateEnabled = 0;
	
	public void enableUpdate()
	{
		itsUpdateEnabled++;
		if (itsUpdateEnabled > 0) itsUpdateEnabled = 0;
		else if (itsUpdateEnabled == 0) updateEnabled();
	}
	
	/**
	 * This method is called when update is enabled after having 
	 * been disabled.
	 */
	protected void updateEnabled()
	{
		repaintAllContexts();
	}
	
	public void disableUpdate()
	{
		itsUpdateEnabled--;
	}
	
	/**
	 * Indicates if update requests on this object should be honored.
	 */
	protected boolean isUpdateEnabled()
	{
		return itsUpdateEnabled == 0;
	}
	
	
	public void addReference(Object aObject)
	{
		if (itsReferences == null) itsReferences = new LinkedList<Object>();
		itsReferences.add(aObject);
	}

	public void removeReference(Object aObject)
	{
		itsReferences.remove(aObject);
		if (itsReferences.isEmpty()) itsReferences = null;
	}

	/**
	 * Creates a property for this graphic object
	 */
	protected <T> IRWProperty<T> createProperty (PropertyId<T> aId)
	{
		return createProperty(aId, null);
	}
	
	/**
	 * Creates a property for this graphic object
	 */
	protected <T> IRWProperty<T> createProperty (PropertyId<T> aId, T aValue)
	{
		return new GOSimpleProperty<T>(this, aId, aValue);
	}
	
	/**
	 * Indicates if the content of this graphic object is valid.
	 * See {@link #checkValid()}
	 */
	private boolean itsValid = false;	
	
	public void checkValid()
	{
		if (! itsValid) itsValid = validate();
	}
	
	public void invalidate()
	{
		itsValid = false;
	}
	
	public void invalidateAll()
	{
		invalidate();
	}
	
	public boolean isValid()
	{
		return itsValid;
	}
	
	/**
	 * Validates the content of this graphic object.
	 * Subclasses that defer content creation (lazy init)
	 * must override this method to create or update their content.
	 * Super should be called.
	 * @return Whether validation was successful.
	 */
	protected boolean validate()
	{
		return true;
	}
	
	public void addDisplay (GraphicObjectContext aContext, IDisplay aDisplay)
	{
		List<IDisplay> theDisplays = GraphicObjectContext.DISPLAYS.get(aContext, null);
		if (theDisplays == null)
		{
			theDisplays = new ArrayList<IDisplay>();
			GraphicObjectContext.DISPLAYS.set(aContext, theDisplays);
		}
		theDisplays.add (aDisplay);
		if (theDisplays.size() == 1) attached(aContext);
	}

	public void removeDisplay (GraphicObjectContext aContext, IDisplay aDisplay)
	{
		List<IDisplay> theDisplays = GraphicObjectContext.DISPLAYS.get(aContext, null);
		assert theDisplays != null;
		theDisplays.remove (aDisplay);
		if (theDisplays.size() == 0) detached(aContext);
	}

	public IGraphicContainer getParent ()
	{
		return itsParent;
	}
	
	void setParent(IGraphicContainer aParent)
	{
		itsParent = aParent;
	}
	
	public void setPosition(double aX, double aY)
	{
		AffineTransform theTransform = pTransform.get();
		
		AffineTransform theNewTransform;
		if (theTransform == null)
		{
			theNewTransform = AffineTransform.getTranslateInstance(aX, aY);
		}
		else
		{
			theNewTransform = new AffineTransform(
					theTransform.getScaleX(),
					theTransform.getShearY(),
					theTransform.getShearX(),
					theTransform.getScaleY(),
					aX,
					aY);
		}
		
		pTransform.set(theNewTransform);
	}
	
	public void translate(double aX, double aY)
	{
		AffineTransform theTransform = pTransform.get();
		
		AffineTransform theNewTransform;
		if (theTransform == null)
		{
			theNewTransform = AffineTransform.getTranslateInstance(aX, aY);
		}
		else
		{
			theNewTransform = new AffineTransform(
					theTransform.getScaleX(),
					theTransform.getShearY(),
					theTransform.getShearX(),
					theTransform.getScaleY(),
					theTransform.getTranslateX()+aX,
					theTransform.getTranslateY()+aY);
		}
		
		pTransform.set(theNewTransform);
	}
	
	public TransformProperty pTransform()
	{
		return pTransform;
	}
	
	public IRWProperty<String> pName ()
	{
		return pName;
	}
	
	public IConstrainedDouble pWeight()
	{
		return pWeight;
	}
	
	/**
	 * Provides a base implementation of the interface method that simply checks
	 * if the given point lies within our bounds.
	 * Subclasses that need a finer checking should override this method. 
	 */
	public boolean isInside(GraphicObjectContext aContext, Point2D aPoint)
	{
		return isInBounds(aContext, aPoint);
	}
	
	/**
	 * Utility method to determine if the specified point is within the
	 * bounds of this object.
	 */
	protected boolean isInBounds(GraphicObjectContext aContext, Point2D aPoint)
	{
		Point2D thePoint = new Point2D.Double();
		localToChildren(aPoint, thePoint);
		Rectangle2D theBounds = getBounds(aContext);
		return theBounds.contains(thePoint);		
	}
	
	public PickResult pick(GraphicObjectContext aContext, Point2D aPoint)
	{
		if (isInside(aContext, aPoint)) return new PickResult(this, aContext);
		else return null;
	}
	
	/**
	 * Paints this graphic object. The transform is already applied to the graphics
	 * and to the visible area.
	 */
	protected abstract void paintTransformed(
			IDisplay aDisplay,
			GraphicObjectContext aContext,
			Graphics2D aGraphics, 
			Area aVisibleArea);
	
	/**
	 * Subclasses should override {@link #paintTransformed(IDisplay, GraphicObjectContext, Graphics2D, Area)}
	 * instead of this method.
	 */
	public void paint(
			IDisplay aDisplay, 
			GraphicObjectContext aContext,
			Graphics2D aGraphics, 
			Area aVisibleArea)
	{
		AffineTransform thePreviousTransform = aGraphics.getTransform();
		AffineTransform theTransform = pTransform().get();
		if (theTransform != null) 
		{
			aGraphics.transform(theTransform);
			aVisibleArea = aVisibleArea.createTransformedArea(pTransform().getInverse());
		}
		
		paintTransformed(aDisplay, aContext, aGraphics, aVisibleArea);
		
		if (theTransform != null) aGraphics.setTransform(thePreviousTransform);
	}
	
	public Rectangle2D getTransformedBounds (Rectangle2D aBounds)
	{
		AffineTransform theTransform = pTransform().get();
		return theTransform != null
				? theTransform.createTransformedShape(aBounds).getBounds2D()
				: aBounds;
		
	}

	public void repaintAllContexts()
	{
		if (! isValid()) return;
		if (! isUpdateEnabled()) return;
		
		for (GraphicObjectContext theContext : getAttachedContexts())
		{
			repaint(theContext);
		}
	}
	
	public void repaint (GraphicObjectContext aContext)
	{
		if (! isValid()) return;
		if (! isUpdateEnabled()) return;

		Rectangle2D theBounds = getBounds(aContext);
		repaint(aContext, theBounds);
	}
	
	public void repaint (GraphicObjectContext aContext, Rectangle2D aBounds)
	{
		if (! isValid()) return;
		if (! isUpdateEnabled()) return;
		
		if (aBounds != null)
		{
			AbstractGraphicObject theParent = (AbstractGraphicObject) getParent();
			
			if (theParent != null) 
			{
				Rectangle2D theBounds = GraphicUtils.childrenToLocal(this, aBounds);
				theParent.repaint(aContext, theBounds);
			}
			else if (aContext != null)
			{
				// Pass to the refering proxy, if any
				AbstractGraphicObject theGraphicObject = 
					(AbstractGraphicObject) aContext.getProxyGraphicObject();

				if (theGraphicObject != null) 
				{
					Rectangle2D theBounds = GraphicUtils.childrenToLocal(this, aBounds);
					theGraphicObject.repaint(aContext.getParentContext(), theBounds);
				}

				// Pass to the context's displays
				List<IDisplay> theDisplays = GraphicObjectContext.DISPLAYS.get(aContext, null);
				if (theDisplays != null) for (IDisplay theDisplay : theDisplays)
				{
					theDisplay.repaint(aBounds);
				}
			}
			
		}
	}

	public void overlay (GraphicObjectContext aContext, JComponent aComponent)
	{
		display(aContext, aComponent, getBounds(aContext));
	}

	/**
	 * Displays a Swing component at specified bounds
	 * See {@link #repaint(Rectangle2D)} for details about the implementation.
	 * @param aComponent The component to display
	 * @param aBounds The bounds where to display the component,
	 * relative to this graphic object.
	 */
	public void display (
			GraphicObjectContext aContext, 
			JComponent aComponent, 
			Rectangle2D aBounds)
	{
		Rectangle2D theBounds = getTransformedBounds(aBounds);
		
		AbstractGraphicObject theContainer = (AbstractGraphicObject) getParent();
		if (theContainer != null) theContainer.display(aContext, aComponent, theBounds);
		else if (aContext != null)
		{
			AbstractGraphicObject theGraphicObject = 
				(AbstractGraphicObject) aContext.getProxyGraphicObject();
			
			if (theGraphicObject != null)
				theGraphicObject.display(aContext.getParentContext(), aComponent, theBounds);

			List<IDisplay> theDisplays = GraphicObjectContext.DISPLAYS.get(aContext, null);
			if (theDisplays != null) for (IDisplay theDisplay : theDisplays)
			{
				theDisplay.display(aComponent, theBounds);
			}
		}
	}
	
	/**
	 * Removes a previously displayed component.
	 */
	public void remove (
			GraphicObjectContext aContext, 
			JComponent aComponent)
	{
		AbstractGraphicObject theContainer = (AbstractGraphicObject) getParent();
		if (theContainer != null) theContainer.remove(aContext, aComponent);
		else if (aContext != null)
		{
			AbstractGraphicObject theGraphicObject = 
				(AbstractGraphicObject) aContext.getProxyGraphicObject();
			
			if (theGraphicObject != null)
				theGraphicObject.remove(aContext.getParentContext(), aComponent);

			List<IDisplay> theDisplays = GraphicObjectContext.DISPLAYS.get(aContext, null);
			if (theDisplays != null) for (IDisplay theDisplay : theDisplays)
			{
				theDisplay.remove(aComponent);
			}
		}
		
	}


	/**
	 * Resets the current controller of this object
	 */
	public void resetControllers()
	{
		itsControllers = null;
	}
	
	/**
	 * Subclasses that want to provide a controller must override
	 * {@link #createController(GraphicObjectContext)}.
	 */
	public final IGraphicObjectController getController(GraphicObjectContext aContext)
	{
		if (itsControllers == null) itsControllers = 
			new WeakHashMap<GraphicObjectContext, IGraphicObjectController>();
		
		IGraphicObjectController theController = itsControllers.get(aContext);
		if (theController == null)
		{
			theController = createController(aContext);
			itsControllers.put (aContext, theController);
		}
		return theController;
	}
	
	/**
	 * Instanciates a controller.
	 * Subclasses should override this method if they want to provide
	 * a controller. The {@link #getController(GraphicObjectContext)} provides lazy initialization.
	 * Returns null by default.
	 */
	protected IGraphicObjectController createController (GraphicObjectContext aContext)
	{
		return null;
	}
	
	public Point2D rootToLocal(GraphicObjectContext aContext, Point2D aPoint)
	{
		List<IGraphicObject> thePathToRoot = new ArrayList<IGraphicObject> ();
		Point2D theResult = new Point2D.Double ();
		theResult.setLocation(aPoint);
		
		// First we determine the chain of container that links us to the root
		IGraphicObject theCurrentObject = getParent();
		GraphicObjectContext theCurrentContext = aContext;
		do
		{
			while (theCurrentObject != null)
			{
				thePathToRoot.add (theCurrentObject);
				theCurrentObject = theCurrentObject.getParent();
			}

			if (theCurrentContext == null) break;
			theCurrentObject = theCurrentContext.getProxyGraphicObject();
			theCurrentContext = theCurrentContext.getParentContext();
		} 
		while (true);

		
		
		// Then we iterate from the root to transform the point
		for (Iterator<IGraphicObject> theIterator = new ReverseIteratorWrapper<IGraphicObject> (thePathToRoot); theIterator.hasNext();)
		{
			IGraphicObject theGraphicObject = theIterator.next();
			theGraphicObject.localToChildren(theResult, theResult);
		}
		return theResult;
	}
	
	public Point2D localToRoot(GraphicObjectContext aContext, Point2D aPoint)
	{
		Point2D theResult = new Point2D.Double ();
		theResult.setLocation(aPoint);
		
		IGraphicObject theCurrentObject = getParent();
		GraphicObjectContext theCurrentContext = aContext;
		do
		{
			while (theCurrentObject != null)
			{
				theCurrentObject.childrenToLocal(theResult, theResult);
				theCurrentObject = theCurrentObject.getParent();
			}

			if (theCurrentContext == null) break;
			theCurrentObject = theCurrentContext.getProxyGraphicObject();
			theCurrentContext = theCurrentContext.getParentContext();
		} 
		while (true);

		return theResult;
	}
	
	public void childrenToLocal(Point2D aPoint, Point2D aDestination)
	{
		AffineTransform theTransform = pTransform().get();
		
		if (theTransform == null)
		{
			if (aPoint == aDestination) return;
			else aDestination.setLocation(aPoint);
		}
		else
		{
			theTransform.transform(aPoint, aDestination);
		}
	}
	
	public void localToChildren(Point2D aPoint, Point2D aDestination)
	{
		AffineTransform theTransform = pTransform().get();
		
		if (theTransform == null)
		{
			if (aPoint == aDestination) return;
			else aDestination.setLocation(aPoint);
		}
		else
		{
			try
			{
				theTransform.inverseTransform(aPoint, aDestination);
			} 
			catch (NoninvertibleTransformException e)
			{
				Log.GRAPHIC.warn("Cannot inverse transform", e);
			}
		}
	}
	


	/**
	 * This method is called by the system when this graphic object is added to a container.
	 * Overriders must call super.
	 */
	protected void added ()
	{
	}

	/**
	 * This method is called by the system when this graphic object is removed from a container.
	 * Overriders must call super.
	 */
	protected void removed ()
	{
	}
	
	/**
	 * Returns the root of the graph that contains this object, whithout
	 * traversing proxies.
	 */
	private AbstractGraphicObject getLocalRoot()
	{
		IGraphicObject theCurrentObject = this;
		do
		{
			IGraphicContainer theParent = theCurrentObject.getParent();
			if (theParent == null) return (AbstractGraphicObject) theCurrentObject;
			
			theCurrentObject = theParent;
		} while(true);
	}
	
	/**
	 * Returns the number of contexts currently attached to this
	 * object's local root.
	 */
	protected int getAttachedContextsCount()
	{
		List<GraphicObjectContext> theContexts = getLocalRoot().itsAttachedContexts;
		return theContexts != null ? theContexts.size() : 0;
	}

	public void attached (GraphicObjectContext aContext)
	{
		// We maintain the attached context list only for proxied graph
		// roots.
		if (getParent() == null)
		{
			if (itsAttachedContexts == null) itsAttachedContexts = 
				new ArrayList<GraphicObjectContext>();
			
			itsAttachedContexts.add (aContext);
		}
		
		// Create and store contextual data.
		Object theData = createContextualData(aContext);
		if (theData != null) aContext.putValue(this, theData);
		
		CWAScope theScope = constrainedWhenAttached();
		switch (theScope)
		{
		case PER_CONTEXT:
			setupConstraints(aContext);
			break;
		case PER_OBJECT:
			if (getAttachedContextsCount() == 1)
				setupConstraints(null);
			break;
		}
	}

	public void detached (GraphicObjectContext aContext)
	{
		if (itsAttachedContexts != null) itsAttachedContexts.remove(aContext);
		
		// Remove contextual data.
		aContext.removeValue(this);

		CWAScope theScope = constrainedWhenAttached();
		switch (theScope)
		{
		case PER_CONTEXT:
			releaseConstraints(aContext);
			break;
		case PER_OBJECT:
			if (getAttachedContextsCount() == 0)
				releaseConstraints(null);
			break;
		}
	}
	
	public Iterable<GraphicObjectContext> getAttachedContexts()
	{
		AbstractGraphicObject theCurrentObject = this;
		
		do
		{
			IGraphicContainer theParent = theCurrentObject.getParent();
			if (theParent == null) 
			{
				return theCurrentObject.itsAttachedContexts != null 
					? theCurrentObject.itsAttachedContexts 
					: Collections.EMPTY_LIST;				
			}
			
			theCurrentObject = (AbstractGraphicObject) theParent;
			
		} while (true);
	}
	
	/**
	 * Creates the contextual data for this object in the specified context.
	 * This method should only create the contextual data, and not
	 * store it into the context. Subclasse that need contextual data
	 * should override this method.
	 * <p>
	 * Contextual data is a data bundle specific to an instance of graphic
	 * object; each context that uses this graphic object can potentially
	 * have its own contextual data for this object.
	 * <p>
	 * Contextual data is created whenever this graphic object is attached to
	 * a context, and removed from the context when this graphic object is detached.
	 * 
	 * @param aContext The context in which the contextual data will be
	 * used.
	 * @return The contextual data for the specified context, or null if
	 * no contextual data is needed.
	 */
	protected Object createContextualData (GraphicObjectContext aContext) 
	{
		return null;
	}
	
	/**
	 * Returns the contextual data associated to this graphic object in
	 * the given context.
	 */
	protected Object getContextualData (GraphicObjectContext aContext)
	{
		return aContext.getValue(this);
	}
	
	/**
	 * Scope for {@link #constrainedWhenAttached()}.
	 * See {@link #setupConstraints} for an explanation of the different possibilities.
	 */
	protected enum CWAScope
	{
		NO, PER_CONTEXT, PER_OBJECT;
	}
	/**
	 * Indicates if this graphic object should setup constraints when attached.
	 * If so, {@link #setupConstraints(GraphicObjectContext)} is called
	 * when this object is attached, and {@link #releaseConstraints(GraphicObjectContext)}
	 * is called when the object is detached.
	 * @return The scope of the constraints. By default returns {@link CWAScope#NO}
	 */
	protected CWAScope constrainedWhenAttached()
	{
		return CWAScope.NO;
	}
	
	/**
	 * Sets up the constraints. This method is called according to the result
	 * of {@link #constrainedWhenAttached()}:
	 * <li>{@link CWAScope#NO}: never
	 * <li>{@link CWAScope#PER_CONTEXT}: whenever this object is attached to a context
	 * <li>{@link CWAScope#PER_OBJECT}: when this object goes from detached state to attached state.
	 * In this case the context argument is null.
	 */
	public void setupConstraints(GraphicObjectContext aContext)
	{
	}

	/**
	 * Sets up the constraints. This method is called conversely to
	 * {@link #setupConstraints(GraphicObjectContext)}
	 */
	public void releaseConstraints(GraphicObjectContext aContext)
	{
	}
	
	
	
	public Object clone ()
	{
		AbstractGraphicObject theClone = (AbstractGraphicObject) super.clone();
		theClone.itsListeners = null;
		theClone.itsAttachedContexts = null;
		theClone.itsParent = null;
		theClone.itsControllers = null;
		theClone.itsValid = false;
		PropertyUtils.cloneProperties(theClone);
		
		return theClone;
	}
	

	/**
	 * By default we do nothing.
	 */
	public void stream(GraphicObjectContext aContext, StreamAction aAction)
	{
	}

	public void addGraphicObjectListener(IGraphicObjectListener aListener)
	{
		if (itsListeners == null) itsListeners = new ArrayList<IGraphicObjectListener> (3);
		itsListeners.add (aListener);
	}
	
	public void removeGraphicObjectListener(IGraphicObjectListener aListener)
	{
		itsListeners.remove (aListener);
		if (itsListeners.size() == 0) itsListeners = null;
	}
	
	protected void fireGraphicObjectAdded (IGraphicContainer aContainer, IGraphicObject aChild)
	{
		ObservationCenter.getInstance().requestObservation(this, null);
		if (itsListeners != null) for (IGraphicObjectListener theListener : itsListeners)
		{
			theListener.childAdded(aContainer, aChild);
		}
		
		AbstractGraphicObject theParent = (AbstractGraphicObject) getParent();
		if (theParent != null) theParent.fireGraphicObjectAdded(aContainer, aChild);
        else
        {
            for (GraphicObjectContext theContext : getAttachedContexts())
            {
                AbstractGraphicObject theProxy = 
                    (AbstractGraphicObject) theContext.getProxyGraphicObject();
                if (theProxy != null) theProxy.fireGraphicObjectAdded(aContainer, aChild);
            }
        }
	}
	
	protected void fireGraphicObjectRemoved (IGraphicContainer aContainer, IGraphicObject aChild)
	{
		ObservationCenter.getInstance().requestObservation(this, null);
		if (itsListeners != null) for (IGraphicObjectListener theListener : itsListeners)
		{
			theListener.childRemoved(aContainer, aChild);
		}
	
		AbstractGraphicObject theParent = (AbstractGraphicObject) getParent();
		if (theParent != null) theParent.fireGraphicObjectRemoved(aContainer, aChild);
        else
        {
            for (GraphicObjectContext theContext : getAttachedContexts())
            {
                AbstractGraphicObject theProxy = 
                    (AbstractGraphicObject) theContext.getProxyGraphicObject();
                if (theProxy != null) theProxy.fireGraphicObjectRemoved(aContainer, aChild);
            }
        }
	} 
	
	protected void fireChanged (IGraphicObject aObject, IRWProperty aProperty)
	{
		ObservationCenter.getInstance().requestObservation(this, null);
		if (itsListeners != null) for (IGraphicObjectListener theListener : itsListeners)
		{
			theListener.changed(aObject, aProperty);
		}

		AbstractGraphicContainer theParent = (AbstractGraphicContainer) getParent();
		if (theParent != null) theParent.graphicObjectChanged(aObject, aProperty);
        else
        {
            for (GraphicObjectContext theContext : getAttachedContexts())
            {
                AbstractGraphicObject theProxy = 
                    (AbstractGraphicObject) theContext.getProxyGraphicObject();
                if (theProxy != null) theProxy.fireChanged(aObject, aProperty);
            }
        }
	}
	
	/**
	 * Called whenever a property of this graphic object changes.
	 * Overriders should call super.
	 */
	protected void changed (IRWProperty aProperty)
	{
		fireChanged(this, aProperty);
	}
	
	/**
	 * We implement methods of {@link IMouseAwareGraphicObject} here
	 * in order not to have to implement unneeded ones in subclasses.
	 */
	public void cancelDrag(GraphicObjectContext aContext)
	{
	}

	/**
	 * We implement methods of {@link IMouseAwareGraphicObject} here
	 * in order not to have to implement unneeded ones in subclasses.
	 */
	public void commitDrag(GraphicObjectContext aContext, Point2D aPoint)
	{
	}

	/**
	 * We implement methods of {@link IMouseAwareGraphicObject} here
	 * in order not to have to implement unneeded ones in subclasses.
	 */
	public void drag(GraphicObjectContext aContext, Point2D aPoint)
	{
	}

	/**
	 * We implement methods of {@link IMouseAwareGraphicObject} here
	 * in order not to have to implement unneeded ones in subclasses.
	 */
	public boolean isMouseAware(GraphicObjectContext aContext)
	{
		return false;
	}

	/**
	 * We implement methods of {@link IMouseAwareGraphicObject} here
	 * in order not to have to implement unneeded ones in subclasses.
	 */
	public boolean mouseClicked(GraphicObjectContext aContext, MouseEvent aEvent, Point2D aPoint)
	{
		return false;
	}

	/**
	 * We implement methods of {@link IMouseAwareGraphicObject} here
	 * in order not to have to implement unneeded ones in subclasses.
	 */
	public void mouseEntered(GraphicObjectContext aContext, MouseEvent aEvent)
	{
	}

	/**
	 * We implement methods of {@link IMouseAwareGraphicObject} here
	 * in order not to have to implement unneeded ones in subclasses.
	 */
	public void mouseExited(GraphicObjectContext aContext, MouseEvent aEvent)
	{
	}

	/**
	 * We implement methods of {@link IMouseAwareGraphicObject} here
	 * in order not to have to implement unneeded ones in subclasses.
	 */
	public boolean mouseMoved(GraphicObjectContext aContext, MouseEvent aEvent, Point2D aPoint)
	{
		return false;
	}
	
	public boolean mouseWheelMoved(GraphicObjectContext aContext, MouseWheelEvent aEvent, Point2D aPoint)
	{
		return false;
	}

	/**
	 * We implement methods of {@link IMouseAwareGraphicObject} here
	 * in order not to have to implement unneeded ones in subclasses.
	 */
	public boolean mousePressed(GraphicObjectContext aContext, MouseEvent aEvent, Point2D aPoint)
	{
		return false;
	}

	/**
	 * We implement methods of {@link IMouseAwareGraphicObject} here
	 * in order not to have to implement unneeded ones in subclasses.
	 */
	public boolean mouseReleased(GraphicObjectContext aContext, MouseEvent aEvent, Point2D aPoint)
	{
		return false;
	}

	/**
	 * We implement methods of {@link IMouseAwareGraphicObject} here
	 * in order not to have to implement unneeded ones in subclasses.
	 */
	public boolean startDrag(GraphicObjectContext aContext, MouseEvent aEvent, Point2D aPoint)
	{
		return false;
	}
	
	private static final DecimalFormat FORMAT = new DecimalFormat("#.##"); 
	
	@Override
	public String toString()
	{
		StringBuilder theBuilder = new StringBuilder();
		theBuilder.append(getClass().getSimpleName());
		theBuilder.append(": ");
		
		AffineTransform theTransform = pTransform().get();
		if (theTransform != null)
		{
			theBuilder.append("tr: "+theTransform);
		}
		
		if (this instanceof IRectangularGraphicObject)
		{
			IRectangularGraphicObject theObject = (IRectangularGraphicObject) this;
			Rectangle2D theBounds = theObject.pBounds().get();
			if (theBounds != null) theBuilder.append(String.format(
					"bounds [%s, %s, %s, %s]", 
					FORMAT.format(theBounds.getX()),
					FORMAT.format(theBounds.getY()),
					FORMAT.format(theBounds.getWidth()),
					FORMAT.format(theBounds.getHeight())));
			else theBuilder.append("bounds [null]");
		}
		
		return theBuilder.toString();
	}
	
	public static abstract class GOAbstractProperty<T> extends AbstractProperty<T>
	implements IRWProperty<T>
	{
		public GOAbstractProperty(
				AbstractGraphicObject aModule, 
				PropertyId<T> aPropertyId)
		{
			super(aModule, aPropertyId);
//			aModule.itsPropertyManager.registerProperty(aPropertyId, this);
		}
		
		public AbstractGraphicObject getGraphicObject()
		{
			return (AbstractGraphicObject) getOwner();
		}

		protected void changed(T aOldValue, T aNewValue)
		{
			AbstractGraphicObject theGraphicObject = getGraphicObject();
			if (theGraphicObject != null) theGraphicObject.changed(this);
		}
	}
	
	/**
	 * Base class for properties that are used in graphic objects.
	 * It notifies the graphic object whenever its value changes.
	 * 
	 * @author gpothier
	 */
	public static class GOSimpleProperty<T> extends SimpleRWProperty<T>
	{
		public GOSimpleProperty(
				AbstractGraphicObject aGraphicObject, 
				PropertyId<T> aPropertyId)
		{
			super(aGraphicObject, aPropertyId, null);
		}
		
		public GOSimpleProperty(
				AbstractGraphicObject aGraphicObject, 
				PropertyId<T> aPropertyId, 
				T aInitialValue)
		{
			super(aGraphicObject, aPropertyId, aInitialValue);
//			aModule.itsPropertyManager.registerProperty(aPropertyId, this);
		}
		
		public AbstractGraphicObject getGraphicObject()
		{
			return (AbstractGraphicObject) getOwner();
		}
		
		protected void changed(T aOldValue, T aNewValue)
		{
			AbstractGraphicObject theGraphicObject = getGraphicObject();
			if (theGraphicObject != null) theGraphicObject.changed(this);
		}
	}
	
	public static class GOConstrainedDouble extends ConstrainedDouble
	{
		public GOConstrainedDouble(
				AbstractGraphicObject aGraphicObject, 
				PropertyId<Double> aPropertyId)
		{
			super(aGraphicObject, aPropertyId, null);
		}
		
		public GOConstrainedDouble(
				AbstractGraphicObject aGraphicObject, 
				PropertyId<Double> aPropertyId, 
				Double aInitialValue)
		{
			super(aGraphicObject, aPropertyId, aInitialValue);
		}
		
		public AbstractGraphicObject getGraphicObject()
		{
			return (AbstractGraphicObject) getOwner();
		}
		
		protected void changed(Double aOldValue, Double aNewValue)
		{
			AbstractGraphicObject theGraphicObject = getGraphicObject();
			if (theGraphicObject != null) theGraphicObject.changed(this);
		}		
	}
	
	public static class GOConstrainedPoint extends ConstrainedPoint
	{
		public GOConstrainedPoint(
				AbstractGraphicObject aGraphicObject, 
				PropertyId<Point2D> aPropertyId)
		{
			super(aGraphicObject, aPropertyId, null);
		}
		
		public GOConstrainedPoint(
				AbstractGraphicObject aGraphicObject, 
				PropertyId<Point2D> aPropertyId, 
				Point2D aInitialValue)
		{
			super(aGraphicObject, aPropertyId, aInitialValue);
		}
		
		public AbstractGraphicObject getGraphicObject()
		{
			return (AbstractGraphicObject) getOwner();
		}
		
		protected void changed(Point2D aOldValue, Point2D aNewValue)
		{
			AbstractGraphicObject theGraphicObject = getGraphicObject();
			if (theGraphicObject != null) theGraphicObject.changed(this);
		}		
	}
	
	public static class GOConstrainedRectangle extends ConstrainedRectangle
	{
		public GOConstrainedRectangle(
				AbstractGraphicObject aGraphicObject, 
				PropertyId<Rectangle2D> aPropertyId)
		{
			super(aGraphicObject, aPropertyId, null);
		}
		
		public GOConstrainedRectangle(
				AbstractGraphicObject aGraphicObject, 
				PropertyId<Rectangle2D> aPropertyId, 
				Rectangle2D aInitialValue)
		{
			super(aGraphicObject, aPropertyId, aInitialValue);
		}
		
		public AbstractGraphicObject getGraphicObject()
		{
			return (AbstractGraphicObject) getOwner();
		}
		
		protected void changed(Rectangle2D aOldValue, Rectangle2D aNewValue)
		{
			AbstractGraphicObject theGraphicObject = getGraphicObject();
			if (theGraphicObject != null) theGraphicObject.changed(this);
		}		
	}
	
	public static class GOTransformProperty extends TransformProperty
	{

		public GOTransformProperty(AbstractGraphicObject aOwner, PropertyId<AffineTransform> aPropertyId, AffineTransform aValue)
		{
			super (aOwner, aPropertyId, aValue);
		}

		public GOTransformProperty(AbstractGraphicObject aOwner, PropertyId<AffineTransform> aPropertyId)
		{
			super (aOwner, aPropertyId);
		}
		
		public AbstractGraphicObject getGraphicObject()
		{
			return (AbstractGraphicObject) getOwner();
		}
		
		@Override
		protected void changed(AffineTransform aOldValue, AffineTransform aNewValue)
		{
			AbstractGraphicObject theGraphicObject = getGraphicObject();
			if (theGraphicObject != null) theGraphicObject.changed(this);
		}
	}
	
	public static class GOListProperty<E> extends ArrayListProperty<E>
	{
		public GOListProperty(
				AbstractGraphicObject aModule, 
				PropertyId<List<E>> aPropertyId)
		{
			super(aModule, aPropertyId);
//			aModule.itsPropertyManager.registerProperty(aPropertyId, this);
		}
		
		/**
		 * Make this method public.
		 */
		public void set(List<E> aList)
		{
			super.set (aList);
		}
		
		public AbstractGraphicObject getGraphicObject()
		{
			return (AbstractGraphicObject) getOwner();
		}
	}

}
