/*
 * Created on Feb 25, 2004
 */
package net.hddb.views.list;

import net.hddb.models.list.HDMList;
import net.hddb.views.HDViewClass;
import net.hddb.views.HDViewManager;

/**
 * @author gpothier
 */
public class HDVCList extends HDViewClass
{
	private static final HDVCList INSTANCE = new HDVCList();
	public static HDVCList getInstance()
	{
		return INSTANCE;
	}
	
	private static final Class[] HANDLED_CLASSES = {HDMList.class};
	private static final String NAME = "List";
	private static final String DESCRIPTION = "Animated list";
	
	private HDVCList() 
	{
		super(HDVList.class, NAME, DESCRIPTION);
	}

	public Class[] getHandledClasses()
	{
		return HANDLED_CLASSES;
	}
}
