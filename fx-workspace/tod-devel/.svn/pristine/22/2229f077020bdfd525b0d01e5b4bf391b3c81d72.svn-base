/*
 * Created on Feb 24, 2004
 */
package net.hddb.adapters.impl.debug.eclipse.stackframe;

import org.eclipse.debug.core.model.IStackFrame;

import net.hddb.adapters.HDAdapterClass;
import net.hddb.adapters.HDAdapterManager;
import net.hddb.models.stackframe.HDMStackFrame;

/**
 * @author gpothier
 */
public class HDACEclipseStackFrame extends HDAdapterClass
{
	private static final HDACEclipseStackFrame INSTANCE = new HDACEclipseStackFrame();
	public static HDACEclipseStackFrame getInstance()
	{
		return INSTANCE;
	}

	private static final Class[] HANDLED_CLASSES = {IStackFrame.class};
	
	private static final String NAME = "Stack frame";
	private static final String DESCRIPTION = "Eclipse stack frame ";

	
	private HDACEclipseStackFrame () 
	{
		super(HDAEclipseStackFrame.class, HDMStackFrame.class, NAME, DESCRIPTION);
	}

	public Class[] getHandledClasses()
	{
		return HANDLED_CLASSES;
	}

}
