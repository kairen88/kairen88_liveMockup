/*
 * Created on Apr 3, 2004
 */
package net.hddb.adapters.impl.debug.eclipse.stackframe;

import java.util.List;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IStackFrame;

import net.hddb.adapters.HDAdapterClass;
import net.hddb.adapters.HDAutoAdapter;
import net.hddb.models.map.HDMMap;
import net.hddb.models.stackframe.HDMStackFrame;

/**
 * Wraps an eclipse stack frame
 * @author gpothier
 */
public class HDAEclipseStackFrame extends HDAutoAdapter implements HDMStackFrame
{
	private HDAEclipseVariables itsVariables;
	
	public HDAEclipseStackFrame (Object aElement)
	{
		super(aElement);
		try
		{
			itsVariables = new HDAEclipseVariables (getIStackFrame().getVariables());
		} 
		catch (DebugException e)
		{
			e.printStackTrace();
		}
	}
	
	protected IStackFrame getIStackFrame ()
	{
		return (IStackFrame) getElement();
	}

	public HDMMap getVariables ()
	{
		return itsVariables;
	}

	public HDAdapterClass getAdapterClass ()
	{
		return HDACEclipseStackFrame.getInstance();
	}

	public List getOutboundReferences ()
	{
		return null;
	}
}
