/*
 * Created on Feb 24, 2004
 */
package net.hddb.adapters.impl.utilsmap2map;

import java.util.Map;

import net.hddb.adapters.HDAdapterClass;
import net.hddb.adapters.HDAdapterManager;
import net.hddb.models.map.HDMMap;

/**
 * @author gpothier
 */
public class HDACUtilsMap2Map extends HDAdapterClass
{
	private static final HDACUtilsMap2Map INSTANCE = new HDACUtilsMap2Map();
	public static HDACUtilsMap2Map getInstance()
	{
		return INSTANCE;
	}

	private static final Class[] HANDLED_CLASSES = {Map.class};
	private static final String NAME = "Map";
	private static final String DESCRIPTION = "Standard java.utils.Map";

	private HDACUtilsMap2Map () 
	{
		super(HDAUtilsMap2Map.class, HDMMap.class, NAME, DESCRIPTION);
	}

	public Class[] getHandledClasses()
	{
		return HANDLED_CLASSES;
	}
}
