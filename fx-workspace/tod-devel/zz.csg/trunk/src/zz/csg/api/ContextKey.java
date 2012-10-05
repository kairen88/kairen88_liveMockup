/*
 * Created on Jan 26, 2005
 */
package zz.csg.api; 


/**
 * Keys for {@link zz.csg.api.GraphicObjectContext}
 * @author gpothier
 */
public class ContextKey<V> 
{
	private String itsId;
	
	public ContextKey(String aId)
	{
		itsId = aId;
	}
	
	/**
	 * Convenience method that can be used instead of 
	 * {@link GraphicObjectContext#getValue(ContextKey)}.
	 * It permits to avoid casting.
	 * <p>
	 * If the passed context is null, an exception is thrown.
	 */
	public V get (GraphicObjectContext aContext)
	{
		return (V) aContext.getValue(this);
	}
	
	/**
	 * Searches a value for this key in the given contextand its ancestors.
	 */
	public V getInherited(GraphicObjectContext aContext)
	{
		V theValue;
		while (aContext != null)
		{
			theValue = get(aContext);
			if (theValue != null) return theValue;
			
			aContext = aContext.getParentContext();
		}
		return null;
	}
	
	/**
	 * Similar to {@link #get(GraphicObjectContext)}, but if the passed context
	 * is null or if the value is null, the default value is returned.
	 */
	public V get (GraphicObjectContext aContext, V aDefaultValue)
	{
		V theValue = aContext != null ? get(aContext) : null;
		return theValue != null ? theValue : aDefaultValue;
	}
	
	/**
	 * Convenience method that can be used instead of 
	 * {@link GraphicObjectContext#putValue(ContextKey, Object)}.
	 * Provides type safety.
	 */
	public void set (GraphicObjectContext aContext, V aValue)
	{
		aContext.putValue(this, aValue);
	}

	public boolean equals(Object aObj)
	{
		if (aObj instanceof ContextKey)
		{
			ContextKey theKey = (ContextKey) aObj;
			return itsId.equals(theKey.itsId);
		}
		else return false;
	}

	public int hashCode()
	{
		return itsId.hashCode();
	}
	
	public String toString()
	{
		return itsId;
	}
}
