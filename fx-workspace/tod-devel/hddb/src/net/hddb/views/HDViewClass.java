/*
 * Created on Feb 25, 2004
 */
package net.hddb.views;

import java.lang.reflect.Constructor;

import net.hddb.HDClass;
import net.hddb.adapters.HDAdapter;
import net.hddb.models.HDModel;

/**
 * @author gpothier
 */
public abstract class HDViewClass extends HDClass
{
	private static final Class[] CONSTRUCTOR_ARGUMENTS = {HDModel.class};
	
	protected HDViewClass(Class aViewClass, String aName, String aDescription) 
	{
		super (aViewClass, aName, aDescription);
	}

	protected final Class[] getConstructorArgument()
	{
		return CONSTRUCTOR_ARGUMENTS;
	}
	
	/**
	 * Instantiates an adapter of this class on the given element.
	 */
	public final HDView createView (HDModel aModel)
	{
		return (HDView) createInstance(new Object[] {aModel});
	}
}
