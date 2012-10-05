/*
 * Created on Feb 25, 2004
 */
package net.hddb.views.map;

import net.hddb.models.map.HDMMap;
import net.hddb.views.HDViewClass;
import net.hddb.views.HDViewManager;

/**
 * @author gpothier
 */
public class HDVCMap extends HDViewClass
{
	private static final HDVCMap INSTANCE = new HDVCMap();
	public static HDVCMap getInstance()
	{
		return INSTANCE;
	}
	
	private static final Class[] HANDLED_CLASSES = {HDMMap.class};
	private static final String NAME = "Map";
	private static final String DESCRIPTION = "Map";
	
	private HDVCMap() 
	{
		super(HDVMap.class, NAME, DESCRIPTION);
	}

	public Class[] getHandledClasses()
	{
		return HANDLED_CLASSES;
	}
}
