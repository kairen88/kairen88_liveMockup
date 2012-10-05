/*
 * Created on Oct 11, 2005
 */
package zz.csg.api.layout;

import java.awt.geom.Rectangle2D;

import zz.csg.ZInsets;
import zz.csg.api.IGraphicContainer;
import zz.csg.api.IGraphicObject;
import zz.csg.api.IGraphicObjectListener;
import zz.csg.api.IRectangularGraphicContainer;
import zz.csg.api.IRectangularGraphicObject;
import zz.utils.Cleaner;
import zz.utils.properties.IRWProperty;
import zz.utils.properties.PropertyUtils;

/**
 * Base class for simple layout managers that perform the layout
 * in a single method.
 * Subclasses must implement the {@link #layout(IRectangularGraphicContainer)} method,
 * which is called whenever children are added or removed, or when the bounds
 * of the container or of one of its children change.
 * @author gpothier
 */
public abstract class AbstractSimpleLayout 
implements ILayoutManager, IGraphicObjectListener
{
	private IGraphicContainer itsContainer;
	private ZInsets itsInsets;
	private boolean itsLayingOut = false;
	
	private Cleaner itsLayoutCleaner = new Cleaner()
	{
		@Override
		protected void clean()
		{
			itsLayingOut = true;
			if (itsContainer != null) layout(itsContainer);
			itsLayingOut = false;
		}
	}; 

	public AbstractSimpleLayout()
	{
	}

	public AbstractSimpleLayout(ZInsets aInsets)
	{
		itsInsets = aInsets;
	}

	public ZInsets getInsets()
	{
		return itsInsets;
	}

	public void setInsets(ZInsets aInsets)
	{
		itsInsets = aInsets;
	}

	public void install(IGraphicContainer aContainer)
	{
		if (itsContainer != null) throw new RuntimeException("Layout manager cannot be shared");
		
		itsContainer = aContainer;
		itsContainer.addGraphicObjectListener(this);
		relayout();
	}

	public void uninstall()
	{
		itsContainer.removeGraphicObjectListener(this);
		itsContainer = null;
	}

	public void ensureLayout()
	{
		if (itsContainer != null) itsLayoutCleaner.cleanNow();
	}
	
	public void changed(IGraphicObject aObject, IRWProperty aProperty)
	{
		if (aObject instanceof IRectangularGraphicObject)
		{
			IRectangularGraphicObject theGraphicObject = (IRectangularGraphicObject) aObject;
			
			if ((theGraphicObject == itsContainer || theGraphicObject.getParent() == itsContainer)
				&& aProperty == theGraphicObject.pBounds())
			{
				relayout();
			}
		}
	}

	public void childAdded(IGraphicContainer aContainer, IGraphicObject aChild)
	{
		relayout();
	}

	public void childRemoved(IGraphicContainer aContainer, IGraphicObject aChild)
	{
		relayout();
	}
	
	protected void relayout()
	{
		if (itsLayingOut) return;
		itsLayoutCleaner.markDirty();
	}
	
	/**
	 * Lays out the specified container. This method is called whenever
	 * a child is added or removed, or when the bounds of the container or 
	 * of one of its children change.
	 */
	protected abstract void layout(IGraphicContainer aContainer);
	
	/**
	 * Sets the size of the container.
	 */
	protected void resize(double aW, double aH)
	{
		if (itsContainer instanceof IRectangularGraphicObject)
		{
			IRectangularGraphicObject theGraphicObject = (IRectangularGraphicObject) itsContainer;
			theGraphicObject.setSize(aW, aH);
		}
	}
}
