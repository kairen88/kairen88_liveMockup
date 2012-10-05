/*
 * Created on Feb 25, 2004
 */
package net.hddb;

import java.lang.reflect.Constructor;

/**
 * Base class for objects that describe classes of objects...
 * 
 * @author gpothier
 */
public abstract class HDClass
{
	/**
	 * Java class this object instantiates.
	 */
	private Class itsInstanceClass;
	private String itsName;
	private String itsDescription;
	
	private Constructor itsConstructor; 
	
	protected HDClass(Class aInstanceClass, String aName, String aDescription) 
	{
		itsInstanceClass = aInstanceClass;
		itsName = aName;
		itsDescription = aDescription;
		
		try
		{
			itsConstructor = itsInstanceClass.getConstructor(getConstructorArgument());
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public Class getInstanceClass()
	{
		return itsInstanceClass;
	}

	public String getDescription()
	{
		return itsDescription;
	}

	public String getName()
	{
		return itsName;
	}

	/**
	 * Let the subclasses define the arguments to the constructor of the instance class.
	 */
	protected abstract Class[] getConstructorArgument ();
	
	/**
	 * Returns an array of classes that can be handled by this class.
	 */
	public abstract Class[] getHandledClasses ();
	
	protected Object createInstance (Object[] aArguments)
	{
		try
		{
			return itsConstructor.newInstance(aArguments);
		} 
		catch (Exception e)
		{
			throw new RuntimeException ("Unable to instantiate HDClass \""+this+"\"", e);
		}
	}
}
