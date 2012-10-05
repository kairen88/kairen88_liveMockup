/*
 * Created on Feb 25, 2004
 */
package net.hddb.adapters;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.hddb.models.HDModel;


/**
 * A chain of adapters that permit to transform an element to an adapter,
 * optionally through a chain of tranformations.
 * <p>
 * Two adapter classes are equal iff their first adapter classes are 
 * equal and their target adapter classes are equal.
 * @author gpothier
 */
public class HDChainAdapterPipe extends HDAdapterPipe
{
	/**
	 * The chain of adapters. The last element is the target adapter class.
	 */
	private List itsAdapterClasses = new ArrayList ();
	
	public HDChainAdapterPipe (HDAdapterClass aAdapterClass)
	{
		this (aAdapterClass, null);
	}
	
	public HDChainAdapterPipe (HDAdapterClass aAdapterClass, HDChainAdapterPipe aSourceChain)
	{
		if (aSourceChain != null) itsAdapterClasses.addAll (aSourceChain.itsAdapterClasses);
		if (aAdapterClass != null) itsAdapterClasses.add (aAdapterClass);
	}

	/**
	 * Returns the class of adapter that this chain is able to produce. 
	 * @return
	 */
	private HDAdapterClass getTargetAdapterClass ()
	{
		return (HDAdapterClass) itsAdapterClasses.get (itsAdapterClasses.size()-1);
	}
	
	public final Class getProducedClass ()
	{
		return getTargetAdapterClass().getProducedClass();
	}

	
	private HDAdapterClass getFirstAdapterClass ()
	{
		return (HDAdapterClass) itsAdapterClasses.get (0);
	}
	
	public Class[] getHandledClasses()
	{
		return getFirstAdapterClass().getHandledClasses();
	}

	/**
	 * Passes the given element through the chain of adapters, and returns 
	 * an adapter of the target class of this chain.
	 */
	public HDModel adapt (Object aElement)
	{
		Object theResult = aElement;
		for (Iterator theIterator = itsAdapterClasses.iterator(); theIterator.hasNext();)
		{
			HDAdapterClass theAdapterClass = (HDAdapterClass) theIterator.next();
			theResult = theAdapterClass.adapt(theResult);
		}
		
		return (HDModel) theResult; 
	}
}