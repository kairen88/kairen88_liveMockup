/*
 * Created on Apr 3, 2004
 */
package net.hddb.adapters.impl.debug.eclipse.stackframe;

import org.eclipse.debug.core.model.IValue;

import net.hddb.adapters.HDAdapterClass;
import net.hddb.models.map.HDMMap;

/**
 * 
 * @author gpothier
 */
public class HDACValue2Variables extends HDAdapterClass
{

	private static HDACValue2Variables INSTANCE = new HDACValue2Variables();

	public static HDACValue2Variables getInstance ()
	{
		return INSTANCE;
	}
	
	private static final Class[] HANDLED_CLASSES = {IValue.class};
	
	private static final String NAME = "Variables";
	private static final String DESCRIPTION = "Variables extracted from a value";

	private HDACValue2Variables ()
	{
		super (HDAValue2Variables.class, HDMMap.class, NAME, DESCRIPTION);
	}

	public Class[] getHandledClasses ()
	{
		return HANDLED_CLASSES;
	}
}
