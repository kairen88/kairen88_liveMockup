/*
 * Created on Feb 24, 2004
 */
package net.hddb.ui.world;

import java.util.List;

import net.basekit.widgets.Widget;


/**
 * @author gpothier
 */
public class HDContainer extends Widget
{
	/**
	 * Returns a list of all the elements that appear in this container.
	 * Each element appears only once, even if it has several instances.
	 */
	public List getElements ()
	{
		return null;
	}
	
	/**
	 * Returns a list of all element instances in this container.
	 * @return
	 */
	public List getElementInstances ()
	{
		return null;
	}
	
	public List getInstancesOf (Object aElement) 
	{
		return null;
	}
}
