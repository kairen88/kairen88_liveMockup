/*
 * Created on Feb 24, 2004
 */
package net.hddb.adapters.impl.text2list;

import net.hddb.adapters.HDAdapterClass;
import net.hddb.adapters.HDAdapterManager;
import net.hddb.models.list.HDMList;
import net.hddb.models.text.HDMText;
import net.hddb.utils.Constraints;

/**
 * @author gpothier
 */
public class HDACText2List extends HDAdapterClass
{
	private static final HDACText2List INSTANCE = new HDACText2List();
	public static HDACText2List getInstance()
	{
		return INSTANCE;
	}

	private static final String NAME = "List";
	private static final String DESCRIPTION = "transforms each character in a text ";
	
	private static final Class[] HANDLED_CLASSES = {HDMText.class};

	private HDACText2List () 
	{
		super(HDAText2List.class, HDMList.class, NAME, DESCRIPTION);
	}

	public Class[] getHandledClasses()
	{
		return HANDLED_CLASSES;
	}

	public Constraints getChildrenAdapdationConstraints ()
	{
		Constraints theConstraints = new Constraints (super.getChildrenAdapdationConstraints ());
		theConstraints.reject (HDMList.class);
		return theConstraints;
	}
}
