/*
 * Created on Apr 3, 2004
 */
package net.hddb.adapters.impl.debug.eclipse.stackframe;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IVariable;

import zz.utils.BoundedIterator;

import net.basekit.Observer;
import net.hddb.adapters.HDAdapterClass;
import net.hddb.adapters.HDObservable;
import net.hddb.models.map.HDMMap;
import net.hddb.utils.Constraints;

/**
 * Wraps the variables set from either a stackframe or a value.
 * @author gpothier
 */
public class HDAEclipseVariables extends HDObservable implements HDMMap
{
	private IVariable[] itsVariables;
	
	public HDAEclipseVariables (IVariable[] aVariables)
	{
		itsVariables = aVariables;
	}

	public IVariable[] getVariables () 
	{
		return itsVariables;
	}

	public Object getEntryValue (Object aEntry)
	{
		try
		{
			IVariable theVariable = (IVariable) aEntry;
			return theVariable.getValue ();
		} 
		catch (DebugException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public Object getEntryKey (Object aEntry)
	{
		try
		{
			IVariable theVariable = (IVariable) aEntry;
			return theVariable.getName ();
		} 
		catch (DebugException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public Object getValue (Object aKey)
	{
		try
		{
			String theVariableName = (String) aKey;
			IVariable[] theVariables = getVariables();
			for (int i = 0; i < theVariables.length; i++)
			{
				IVariable theVariable = theVariables[i];
				if (theVariable.getName ().equals(theVariableName)) return theVariable.getValue ();
			}
		} 
		catch (DebugException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public int getCount ()
	{
		return getVariables().length;
	}

	public Object getChild (int aIndex)
	{
		return getVariables()[aIndex];
	}

	public HDAdapterClass getAdapterClass ()
	{
		return null;
	}

	public List getOutboundReferences ()
	{
		return null;
	}

	public void addObserver (Observer aListener)
	{
	}

	public void removeObserver (Observer aListener)
	{
	}

	public Constraints getChildrenViewConstraints ()
	{
		return null;
	}
}
