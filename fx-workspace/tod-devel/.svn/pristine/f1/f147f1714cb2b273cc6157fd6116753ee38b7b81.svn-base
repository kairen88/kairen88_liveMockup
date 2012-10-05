/*
 * Created on Feb 24, 2004
 */
package net.hddb.adapters.impl.string2text;

import net.hddb.adapters.HDAdapterClass;
import net.hddb.models.text.HDMText;

/**
 * @author gpothier
 */
public class HDACString2Text extends HDAdapterClass
{
	private static final HDACString2Text INSTANCE = new HDACString2Text();
	public static HDACString2Text getInstance()
	{
		return INSTANCE;
	}

	private static final Class[] HANDLED_CLASSES = {String.class};
	
	private static final String NAME = "Text";
	private static final String DESCRIPTION = "Simple text";
	
	private HDACString2Text () 
	{
		super(HDAString2Text.class, HDMText.class, NAME, DESCRIPTION);
	}

	public Class[] getHandledClasses()
	{
		return HANDLED_CLASSES;
	}

}
