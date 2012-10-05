/*
 * Created on Feb 24, 2004
 */
package net.hddb.adapters;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import net.hddb.HDClass;
import net.hddb.models.HDModel;
import net.hddb.utils.Constraints;

/**
 * Describes a class of adapters, and can instantiate an adapter of this class.
 * @author gpothier
 */
public abstract class HDAdapterClass extends HDClass
{
	private static final Class[] CONSTRUCTOR_ARGUMENTS = {Object.class};
	
	/**
	 * The class that the corresponding adapter is capable to produce.
	 */
	private Class itsProducedClass;
	
	protected HDAdapterClass(Class aAdapterClass, Class aProducedClass, String aName, String aDescription) 
	{
		super (aAdapterClass, aName, aDescription);
		itsProducedClass = aProducedClass;
	}

	/**
	 * Instantiates an adapter of this class on the given element.
	 */
	public HDModel adapt (Object aElement)
	{
		HDAdapter theAdapter = (HDAdapter) createInstance(new Object[] {aElement});
		return theAdapter.getModel();
	}

	protected Class[] getConstructorArgument()
	{
		return CONSTRUCTOR_ARGUMENTS;
	}

	/**
	 * If it makes sense for adapters of this class to have children, returns constraints
	 * that should be respected by the views that wrap the children.
	 * The constraints can be used to restrict the transformations that can be applied
	 * to the children.
	 * For instance, children of a {@link net.hddb.adapters.impl.text2list.HDAText2List}
	 * are strigs, which could in turn be transformed into {@link net.hddb.adapters.impl.text2list.HDAText2List}.
	 * Constraints permit to avoid this.
	 * @return Constraints that operate on {@link Class} objects, or null if
	 * not applicable or no constraints
	 */
	public Constraints getChildrenAdapdationConstraints ()
	{
		return null;
	}

	public Class getProducedClass ()
	{
		return itsProducedClass;
	}
}
