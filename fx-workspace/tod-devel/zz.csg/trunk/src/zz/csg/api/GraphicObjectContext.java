/*
 * Created on Jan 26, 2005
 */
package zz.csg.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import EDU.Washington.grad.gjb.cassowary.ClSimplexSolver;


/**
 * This object maintains information that can be used to differentiate
 * various representations of the same graphic object graph.
 * <p>
 * All the graphic objects of the same subgraph must share the same context;
 * only proxied subgraphs have a new context.
 * The root of a proxied subgraph has no parent, it is only included in another subgraph
 * by a proxy element. The proxy element MUST provide a new context for its
 * content. 
 * @author gpothier
 */
public class GraphicObjectContext 
{
	/**
	 * Standard key.
	 * Whether this context currently has a rollever feedback
	 */
	public static final ContextKey<Boolean> ROLLOVER = new ContextKey<Boolean>("rollOver");
	
	/**
	 * Standard key.
	 * Whether this context currently has a pressed feedback.
	 */
	public static final ContextKey<Boolean> PRESSED = new ContextKey<Boolean>("pressed");
	
	/**
	 * Standard key.
	 * Constraint solver
	 */
	public static final ContextKey<ClSimplexSolver> SOLVER = new ContextKey<ClSimplexSolver>("solver");
	
	
	
	/**
	 * Standard key.
	 * The displays that are currently displaying (directly)
	 * the graphic object.
	 */
	public static final ContextKey<List<IDisplay>> DISPLAYS =
		new ContextKey<List<IDisplay>>("displays");


	
	private GraphicObjectContext itsParentContext;
	
	/**
	 * The root of the graph of this context
	 */
	private IGraphicObject itsRootGraphicObject;
	
	/**
	 * The proxy graphic object that created this context.
	 */
	private final IProxyGraphicObject itsProxyGraphicObject;
	
	private Map<Object, Object> itsValuesMap;
	
	private Map<Object, GraphicObjectContext> itsSubContextsMap;


	private GraphicObjectContext(
			GraphicObjectContext aParentContext, 
			IProxyGraphicObject aProxyGraphicObject, 
			IGraphicObject aRootGraphicObject,
			Map<Object, Object> aValuesMap,
			Map<Object, GraphicObjectContext> aSubContextsMap)
	{
		itsParentContext = aParentContext;
		itsRootGraphicObject = aRootGraphicObject;
		itsProxyGraphicObject = aProxyGraphicObject;
		itsValuesMap = aValuesMap;
		itsSubContextsMap = aSubContextsMap;
	}
	
	public GraphicObjectContext(
			GraphicObjectContext aParentContext,
			IProxyGraphicObject aProxyGraphicObject,
			IGraphicObject aRootGraphicObject)
	{
		this (
				aParentContext, 
				aProxyGraphicObject, 
				aRootGraphicObject, 
				new HashMap<Object, Object>(),
				new HashMap<Object, GraphicObjectContext>());
	}
	
	public GraphicObjectContext getParentContext()
	{
		return itsParentContext;
	}
	
	public IGraphicObject getRootGraphicObject()
	{
		return itsRootGraphicObject;
	}
	
	public IProxyGraphicObject getProxyGraphicObject()
	{
		return itsProxyGraphicObject;
	}
	
	/**
	 * Retrieves a value of this context.
	 * The preferred way to retrieve values is through 
	 * {@link ContextKey#get(GraphicObjectContext)}.
	 */
	public Object getValue(Object aKey)
	{
		return itsValuesMap.get(aKey);
	}
	
	/**
	 * Sets a value int this context.
	 * The preferred way to set values is through 
	 * {@link ContextKey#set(GraphicObjectContext, V)}.
	 */
	public void putValue (Object aKey, Object aValue)
	{
		itsValuesMap.put (aKey, aValue);
	}
	
	/**
	 * Removes a value from this context.
	 */
	public void removeValue (Object aKey)
	{
		itsValuesMap.remove(aKey);
	}

	public GraphicObjectContext getSubContext(Object aKey)
	{
		return itsSubContextsMap.get(aKey);
	}
	
	public void putSubContext (Object aKey, GraphicObjectContext aContext)
	{
		assert aContext.itsParentContext == this;
		itsSubContextsMap.put (aKey, aContext);
	}
	
	/**
	 * Creates a context that shares its values map with its parent context.
	 */
	public static GraphicObjectContext createInheritedContext(
			GraphicObjectContext aParentContext,
			IProxyGraphicObject aProxyGraphicObject,
			IGraphicObject aRootGraphicObject)
	{
		return new GraphicObjectContext(
				aParentContext, 
				aProxyGraphicObject, 
				aRootGraphicObject, 
				aParentContext.itsValuesMap,
				new HashMap<Object, GraphicObjectContext>());
	}

}
