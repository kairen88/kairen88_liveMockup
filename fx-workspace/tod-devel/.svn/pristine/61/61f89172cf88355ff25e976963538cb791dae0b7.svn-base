/*
 * Created on Mar 2, 2004
 */
package net.hddb.adapters;

import net.hddb.models.HDModel;

/**
 * This type of adapter chain dosn't perform any transformation.
 * @author gpothier
 */
public class HDIdentityAdapterPipe extends HDAdapterPipe
{
	private Class itsProducedClass;
	private Class[] itsHandledClasses;
	
	public HDIdentityAdapterPipe(Class aProducedClass) 
	{
		itsProducedClass = aProducedClass;
		itsHandledClasses = new Class[] {itsProducedClass};
	}
	
	public Class[] getHandledClasses()
	{
		return itsHandledClasses;
	}

	public Class getProducedClass ()
	{
		return itsProducedClass;
	}

	public HDModel adapt(Object aElement)
	{
		assert itsProducedClass.equals(aElement.getClass());
		return (HDModel) aElement;
	}
}
