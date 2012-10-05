/*
 * Created on Feb 25, 2004
 */
package net.hddb.views.text;

import net.hddb.models.text.HDMText;
import net.hddb.views.HDViewClass;
import net.hddb.views.HDViewManager;

/**
 * @author gpothier
 */
public class HDVCText extends HDViewClass
{
	private static final HDVCText INSTANCE = new HDVCText();
	public static HDVCText getInstance()
	{
		return INSTANCE;
	}

	private static final Class[] HANDLED_CLASSES = {HDMText.class};
	private static final String NAME = "Text";
	private static final String DESCRIPTION = "Plain text";
	
	private HDVCText() 
	{
		super(HDVText.class, NAME, DESCRIPTION);
	}

	public Class[] getHandledClasses()
	{
		return HANDLED_CLASSES;
	}

}
