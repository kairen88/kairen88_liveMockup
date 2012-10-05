/*
 * Created on Feb 24, 2004
 */
package net.hddb.adapters;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Collection;

import net.basekit.*;
import net.hddb.*;
import net.hddb.models.HDModel;
import net.hddb.utils.Constraints;


/**
 * Extracts information that is meaningful to a view from a raw element. 
 * @author gpothier
 */
public abstract class HDAdapter extends HDObservable
{
	
	/**
	 * The underlying raw element.
	 */
	private Object itsElement;
	
	/**
	 * Instantiates an adapter for a given element. 
	 */
	public HDAdapter (Object aElement) 
	{
		itsElement = aElement;
	}
	
	/**
	 * Returns the model produced by this adapter.
	 */
	public abstract HDModel getModel ();
	
	/**
	 * Returns the adapter class that corresponds to this adapter.
	 */
	public abstract HDAdapterClass getAdapterClass ();
	
	/**
	 * @return Returns the element.
	 */
	public Object getElement()
	{
		return itsElement;
	}
	
}
