/*
 * Created on Jan 27, 2005
 */
package zz.csg.impl;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import zz.csg.api.GraphicObjectContext;
import zz.csg.api.IDisplay;
import zz.csg.api.IGraphicObject;
import zz.csg.api.IProxyGraphicObject;
import zz.csg.api.PickResult;


/**
 * This graphic object is a terminal node for its scenegraph,
 * but it proxies another graph, to which it delegates painting
 * and event handling.
 * @author gpothier
 */
public abstract class AbstractProxyGraphicObject extends AbstractGraphicObject
implements IProxyGraphicObject
{
	public Rectangle2D getBounds(GraphicObjectContext aContext)
	{
		checkValid();
		return getChildGraphicObject().getBounds(getChildContext(aContext));
	}
	
	@Override
	protected void paintTransformed(IDisplay aDisplay, GraphicObjectContext aContext, Graphics2D aGraphics, Area aVisibleArea)
	{
		GraphicObjectContext theChildContext = getChildContext(aContext);
		
		if (theChildContext != null)
		{
			IGraphicObject theGraphicObject = theChildContext.getRootGraphicObject();
			
			theGraphicObject.checkValid();
			theGraphicObject.paint(aDisplay, theChildContext, aGraphics, aVisibleArea); //TODO: check visible region, probably wrong
		}
	}
	
	public PickResult pick(GraphicObjectContext aContext, Point2D aPoint)
	{
		if (! isInside(aContext, aPoint)) return null;
		
		GraphicObjectContext theChildContext = getChildContext(aContext);
		Point2D thePoint = new Point2D.Double();
		localToChildren(aPoint, thePoint);
		return theChildContext.getRootGraphicObject().pick(theChildContext, thePoint);
	}
	
	public void attached(GraphicObjectContext aContext)
	{
		super.attached(aContext);

		GraphicObjectContext theChildContext = getChildContext(aContext);
		
		if (theChildContext != null) 
			theChildContext.getRootGraphicObject().attached(theChildContext);
	}
	
	public void detached(GraphicObjectContext aContext)
	{
		super.detached(aContext);

		GraphicObjectContext theChildContext = getChildContext(aContext);
		
		if (theChildContext != null) 
			theChildContext.getRootGraphicObject().detached(theChildContext);
	}
	
}
