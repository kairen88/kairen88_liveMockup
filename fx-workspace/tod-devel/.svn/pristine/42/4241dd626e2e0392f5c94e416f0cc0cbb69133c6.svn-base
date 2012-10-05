/*
 * Created on Jan 26, 2005
 */
package zz.csg.api;

import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import zz.csg.api.edition.IGraphicObjectController;


/**
 * Agregates a {@link zz.csg.api.IGraphicObject}
 * and a {@link zz.csg.api.GraphicObjectContext}.
 * It privides a few utility methods for methods of {@link zz.csg.api.IGraphicObject}
 * that take a context as parameter: they call it giving this node's context.
 * @author gpothier
 */
public class GraphicNode<T extends IGraphicObject>
{
	private T itsGraphicObject;
	private GraphicObjectContext itsContext;
	
	
	/**
	 * Constructs a new graphic node with a default root context.
	 */
	public GraphicNode(T aGraphicObject)
	{
		itsGraphicObject = aGraphicObject;
		itsContext = new GraphicObjectContext(null, null, itsGraphicObject);
	}
	
	public GraphicNode(T aGraphicObject, GraphicObjectContext aContext)
	{
		itsGraphicObject = aGraphicObject;
		itsContext = aContext;
	}
	
	public GraphicObjectContext getContext()
	{
		return itsContext;
	}
	
	public T getGraphicObject()
	{
		return itsGraphicObject;
	}
	
	/**
	 * Convenience method for {@link IGraphicObject#paint(IDisplay, GraphicObjectContext, Graphics2D, Area)}
	 */
	public void paint(IDisplay aDisplay, Graphics2D aGraphics, Area aVisibleArea)
	{
		getGraphicObject().paint(aDisplay, getContext(), aGraphics, aVisibleArea);
	}
	
	/**
	 * Convenience method for {@link IGraphicObject#getBounds(GraphicObjectContext)}
	 */
	public Rectangle2D getBounds()
	{
		return getGraphicObject().getBounds(getContext());
	}
	
	/**
	 * Convenience method for {@link IGraphicObject#getController(GraphicObjectContext)}
	 */
	public IGraphicObjectController getController()
	{
		return getGraphicObject().getController(getContext());
	}
	
	/**
	 * Convenience method for {@link IGraphicObject#rootToLocal(GraphicObjectContext, Point2D)}
	 */
	public Point2D rootToLocal (Point2D aPoint)
	{
		return getGraphicObject().rootToLocal(getContext(), aPoint);
	}
	
	/**
	 * Convenience method for {@link IGraphicObject#localToRoot(GraphicObjectContext, Point2D)}
	 */
	public Point2D localToRoot (Point2D aPoint)
	{
		return getGraphicObject().localToRoot(getContext(), aPoint);
	}
	

	public boolean equals(Object aObj)
	{
		if (aObj instanceof GraphicNode)
		{
			GraphicNode theNode = (GraphicNode) aObj;
			return theNode.itsContext == itsContext && theNode.itsGraphicObject == itsGraphicObject;
		}
		else return false;
	}
	
	
}	
