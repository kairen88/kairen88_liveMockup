/*
 * Created on Apr 3, 2004
 */
package net.hddb.adapters.impl.debug.eclipse.stackframe;

import net.hddb.adapters.HDAdapterClass;
import net.hddb.models.text.HDMText;

import org.eclipse.debug.core.model.IValue;

/**
 * 
 * @author gpothier
 */
public class HDACValue2Text extends HDAdapterClass
{

	private static HDACValue2Text INSTANCE = new HDACValue2Text();

	public static HDACValue2Text getInstance ()
	{
		return INSTANCE;
	}
	
	private static final Class[] HANDLED_CLASSES = {IValue.class};
	
	private static final String NAME = "Text";
	private static final String DESCRIPTION = "Textual representation of a value";

	private HDACValue2Text ()
	{
		super (HDAValue2Text.class, HDMText.class, NAME, DESCRIPTION);
	}

	public Class[] getHandledClasses ()
	{
		return HANDLED_CLASSES;
	}
}
