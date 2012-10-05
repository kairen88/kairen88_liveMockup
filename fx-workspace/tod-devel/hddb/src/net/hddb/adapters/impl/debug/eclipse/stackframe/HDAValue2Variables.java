/*
 * Created on Apr 3, 2004
 */
package net.hddb.adapters.impl.debug.eclipse.stackframe;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;

import net.hddb.adapters.HDAdapter;
import net.hddb.adapters.HDAdapterClass;
import net.hddb.models.HDModel;

/**
 * 
 * @author gpothier
 */
public class HDAValue2Variables extends HDAdapter
{
	public HDAValue2Variables (Object aElement)
	{
		super(aElement);
	}
	
	protected IValue getIValue ()
	{
		return (IValue) getElement();
	}

	public HDModel getModel ()
	{
		try
		{
			IVariable[] tehVariables = getIValue().getVariables();
			return new HDAEclipseVariables (tehVariables);
		} 
		catch (DebugException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public HDAdapterClass getAdapterClass ()
	{
		return HDACValue2Variables.getInstance();
	}
}
