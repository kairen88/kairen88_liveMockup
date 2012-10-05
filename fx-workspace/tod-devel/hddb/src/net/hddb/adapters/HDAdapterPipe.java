/*
 * Created on Feb 25, 2004
 */
package net.hddb.adapters;

import net.hddb.models.HDModel;


/**
 * Permit to transform an element to an adapter,
 * optionally through a chain of tranformations.
 * <p>
 * Two adapter classes are equal iff their source classes are 
 * equal and their target adapter classes are equal.
 * @author gpothier
 */
public abstract class HDAdapterPipe
{
	/**
	 * Returns the class of objects ultimately produced by this pipe.
	 */
	public abstract Class getProducedClass ();

	/**
	 * Returns the classes of objects that can be transformed by this chain.
	 */
	public abstract Class[] getHandledClasses();
	
	/**
	 * Passes the given element through the chain of adapters, and returns 
	 * an adapter of the target class of this chain.
	 */
	public abstract HDModel adapt (Object aElement);
	
	public final boolean equals(Object aObj)
	{
		if (aObj instanceof HDAdapterPipe)
		{
			HDAdapterPipe theAdapterChain = (HDAdapterPipe) aObj;
			return getHandledClasses().equals(theAdapterChain.getHandledClasses())
				&& getProducedClass().equals(theAdapterChain.getProducedClass());
		}
		else return false;
	}

	public final int hashCode()
	{
		return getHandledClasses().hashCode() * 27 + getProducedClass().hashCode();
	}

}