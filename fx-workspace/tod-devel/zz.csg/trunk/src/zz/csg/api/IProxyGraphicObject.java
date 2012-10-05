/*
 * Created on Feb 1, 2005
 */
package zz.csg.api;


/**
 * This graphic object is a terminal node for its scenegraph,
 * but it proxies another graph, to which it delegates painting
 * and event handling.
 * @author gpothier
 */
public interface IProxyGraphicObject extends IGraphicObject
{
	/**
	 * Returns the context that describes the proxied graph.
	 */
	public GraphicObjectContext getChildContext(GraphicObjectContext aContext);
	
	/**
	 * Returns the child graphic object, without context.
	 * This method should return the same object as the graphic object
	 * of the context returned by {@link #getChildContext(GraphicObjectContext)}
	 */
	public abstract IGraphicObject getChildGraphicObject();
	
}

