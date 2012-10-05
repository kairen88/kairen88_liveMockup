/*
 * Created on Feb 25, 2004
 */
package net.hddb.views.stackframe;

import net.hddb.models.stackframe.HDMStackFrame;
import net.hddb.views.HDViewClass;
import net.hddb.views.HDViewManager;

/**
 * @author gpothier
 */
public class HDVCStackFrame extends HDViewClass
{
	private static final HDVCStackFrame INSTANCE = new HDVCStackFrame();
	public static HDVCStackFrame getInstance()
	{
		return INSTANCE;
	}
	
	private static final Class[] HANDLED_CLASSES = {HDMStackFrame.class};
	private static final String NAME = "Stack frame";
	private static final String DESCRIPTION = "Debugger stack frame";
	
	private HDVCStackFrame() 
	{
		super(HDVStackFrame.class, NAME, DESCRIPTION);
	}

	public Class[] getHandledClasses()
	{
		return HANDLED_CLASSES;
	}
}
