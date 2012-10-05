/*
 * Created on Jan 28, 2005
 */
package zz.csg.impl;

import java.util.Iterator;

import zz.csg.api.IGraphicObject;
import zz.csg.api.IProxyGraphicObject;


/**
 * Visitor pattern for graphic objects
 * @author gpothier
 */
public class GraphicVisitor<R>
{
	public static <R> R visit (GraphicVisitor<R> aVisitor, IGraphicObject aGraphicObject)
	{
		visit0(aVisitor, aGraphicObject);
		return aVisitor.getResult();
	}
	
	public static GraphicVisitorStatus visit0 (GraphicVisitor aVisitor, IGraphicObject aGraphicObject)
	{
		GraphicVisitorStatus theStatus = aVisitor.visit(aGraphicObject);
		if (theStatus != GraphicVisitorStatus.CONTINUE) return theStatus;
		
		if (aGraphicObject instanceof AbstractGraphicContainer)
		{
			AbstractGraphicContainer theContainer = (AbstractGraphicContainer) aGraphicObject;
			for (Iterator theIterator = theContainer.getChildrenIterator(); theIterator.hasNext();)
			{
				IGraphicObject theChild = (IGraphicObject) theIterator.next();
				theStatus = visit0(aVisitor, theChild);
				if (theStatus == GraphicVisitorStatus.QUIT) break;
			}
		}
		else if (aGraphicObject instanceof IProxyGraphicObject)
		{
			IProxyGraphicObject theProxy = (IProxyGraphicObject) aGraphicObject;
			IGraphicObject theChild = theProxy.getChildGraphicObject();
			theStatus = visit0(aVisitor, theChild);
		}
		
		return theStatus;
	}
	
	
	
	/**
	 * Returns the result of the visit.
	 */
	public R getResult()
	{
		return null;
	}
	
	/**
	 * A graphic object was encountered.
	 * @return Whether the visit should go on
	 */
	public GraphicVisitorStatus visit (IGraphicObject aGraphicObject)
	{
		return GraphicVisitorStatus.CONTINUE;
	}
	
}
