/*
 * Created on Feb 24, 2004
 */
package net.hddb;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import zz.utils.ListMap;

/**
 * Base classes for entities that manage a set of {@link net.hddb.HDClass}.
 * @author gpothier
 */
public abstract class HDManager
{
	/**
	 * Maps classes to hdclasses.
	 */
	private ListMap itsClassesMap = new ListMap ();
	
	/**
	 * Registers a class so that it can be indirectly retrieved
	 * through {@link #getHDClassesFor(HDClass)} and {@link #getHDClassesFor(Object)}.
	 */
	protected void registerClass (HDClass aClass)
	{
		Class[] theClasses = aClass.getHandledClasses();
		for (int i = 0; i < theClasses.length; i++)
		{
			Class theClass = theClasses[i];
			itsClassesMap.add(theClass, aClass);
		}
	}
	
	/**
	 * Returns a set of all classes that can handle instances of the specified class.
	 */
	protected Collection getHDClassesFor (Class aClass)
	{
		Set theResult = new HashSet ();
		Class theClass = aClass;
		while (theClass != null)
		{
			List theList = itsClassesMap.getList(theClass);
			if (theList != null) theResult.addAll(theList);
			
			theClass = theClass.getSuperclass();
		}
		
		Class[] theInterfaces = aClass.getInterfaces();
		for (int i = 0; i < theInterfaces.length; i++)
		{
			theClass = theInterfaces[i];
			
			Collection theClasses = getHDClassesFor(theClass);
			theResult.addAll(theClasses);
		}
		
		return theResult;
	}
	
	/**
	 * Returns a list of all classes that can handle the specified element.
	 */
	protected Collection getHDClassesFor (Object aElement)
	{
		return getHDClassesFor(aElement.getClass());
	}
	
	/**
	 * Returns a list of all classes that can handle object of the instance class
	 * of the specified {@link HDClass}.
	 */
	protected Collection getHDClassesFor (HDClass aClass)
	{
		return getHDClassesFor(aClass.getInstanceClass());
	}
	
}
