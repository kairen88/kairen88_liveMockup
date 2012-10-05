/*
 * Created on Feb 9, 2005
 */
package zz.csg.impl;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import zz.csg.api.GraphicObjectContext;
import zz.csg.api.IGraphicObject;
import zz.csg.api.IURIGraphicObject;
import zz.csg.api.IUseGraphicObject;
import zz.csg.api.PickResult;
import zz.csg.api.IURIGraphicObject.IProvider;
import zz.csg.api.constraints.IConstrainedRectangle;
import zz.csg.api.edition.IGraphicObjectController;
import zz.csg.impl.constraints.ConstrainedRectangle;
import zz.csg.impl.edition.RectangularControllerUtils;
import zz.utils.properties.IRWProperty;


/**
 * Implementation of {@link zz.csg.api.IUseGraphicObject}
 * @author gpothier
 */
public class SVGUse extends AbstractProxyGraphicObject
implements IUseGraphicObject
{
	private IRWProperty<IURIGraphicObject.IProvider> pProvider = createProperty(PROVIDER);
	
	private IConstrainedRectangle pBounds = createBoundsProperty();


	public GraphicObjectContext getChildContext(GraphicObjectContext aContext)
	{
		GraphicObjectContext theChildContext = aContext.getSubContext(this);
		if (theChildContext == null) 
		{
			theChildContext = GraphicObjectContext.createInheritedContext(
					aContext, 
					this, 
					getChildGraphicObject());
			
			aContext.putSubContext(this, theChildContext);
		}
		
		return theChildContext;
	}

	public IGraphicObject getChildGraphicObject()
	{
		IURIGraphicObject.IProvider theProvider = pProvider.get();
		return (IGraphicObject) theProvider.getData();
	}

	
	public PickResult pick(GraphicObjectContext aContext, Point2D aPoint)
	{
		if (isInside(aContext, aPoint)) return new PickResult(this, aContext);
		else return null;
	}
	
	protected IGraphicObjectController createController(GraphicObjectContext aContext)
	{
		return RectangularControllerUtils.createRectangularController(aContext, this);
	}
	
	public IRWProperty<IProvider> pProvider()
	{
		return pProvider;
	}

	public IConstrainedRectangle pBounds()
	{
		return pBounds;
	}
	
	@Override
	public Rectangle2D getBounds(GraphicObjectContext aContext)
	{
		return pBounds().get();
	}
	
	public void setSize(double aWidth, double aHeight)
	{
		Rectangle2D theBounds = pBounds.get();
		Rectangle2D theNewBounds = new Rectangle2D.Double(
				theBounds.getX(),
				theBounds.getY(),
				aWidth,
				aHeight);
		pBounds.set(theNewBounds);
	}
	
	
	private IConstrainedRectangle createBoundsProperty()
	{
		IConstrainedRectangle theRectangle = new GOConstrainedRectangle(this, BOUNDS);
		theRectangle.set(0, 0, 0, 0);
		return theRectangle;
	}

	
	protected void changed(IRWProperty aProperty)
	{
		if (aProperty == pBounds) updateTransform();
		super.changed(aProperty);
	}
	
	/**
	 * Sets up the transform of this object so that it reflects the position
	 * property.
	 */
	private void updateTransform ()
	{
		Rectangle2D theBounds = pBounds.get();
		
		double theX = theBounds != null ? theBounds.getX() : 0;
		double theY = theBounds != null ? theBounds.getY() : 0;
		
		pTransform().set(AffineTransform.getTranslateInstance(theX, theY));
	}
	
	public Object clone()
	{
		SVGUse theClone = (SVGUse) super.clone();
		
		theClone.pProvider.set((IProvider) pProvider.get().clone());
		
		return theClone;
	}
}
