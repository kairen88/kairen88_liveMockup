/*
 * Created on Feb 25, 2004
 */
package net.hddb.views;

import net.hddb.adapters.HDAdapter;
import net.hddb.adapters.HDAdapterPipe;
import net.hddb.adapters.HDAdapterClass;
import net.hddb.models.HDModel;


/**
 * The chain that permit to produce a view. It is comprised of
 * an {@link net.hddb.adapters.HDAdapterPipe} and a
 * {@link net.hddb.views.HDViewClass}.
 * <p>
 * Two view chains are equal iff their view class are equal and
 * their adapter chains are equal. 
 * @author gpothier
 */
public class HDViewChain
{
	/**
	 * The adapter chain that permits to to transform the element into the adapter
	 * that is used by the view.
	 */
	private HDAdapterPipe itsAdapterPipe;
	private HDViewClass itsViewClass;

	public HDViewChain (HDAdapterPipe aAdapterChain, HDViewClass aViewClass) 
	{
		itsAdapterPipe = aAdapterChain;
		itsViewClass = aViewClass;
	}

	public HDAdapterPipe getAdapterPipe()
	{
		return itsAdapterPipe;
	}
	
	/**
	 * Returns the model class this view chain uses.
	 * This is the produced class of the adapter pipe.
	 */
	public Class getModelClass()
	{
		return getAdapterPipe().getProducedClass();
	}

	public HDViewClass getViewClass()
	{
		return itsViewClass;
	}
	
	/**
	 * Instanciates a view for the specified element, using this chain's adapter chain
	 * and view class.
	 */
	public HDView createView (Object aElement)
	{
		HDModel theModel = itsAdapterPipe.adapt(aElement);
		HDView theView = itsViewClass.createView(theModel);
		return theView;
	}

	public boolean equals(Object aObj)
	{
		if (aObj instanceof HDViewChain)
		{
			HDViewChain theViewChain = (HDViewChain) aObj;
			return getViewClass().equals(theViewChain.getViewClass())
				&& getAdapterPipe().equals(theViewChain.getAdapterPipe());
		}
		else return false;
	}

	public int hashCode()
	{
		return getViewClass().hashCode() * 27 + getAdapterPipe().hashCode();
	}
}