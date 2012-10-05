/*
 * Created on Sep 30, 2005
 */
package zz.csg.api;

import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;

import zz.utils.properties.PropertyId;
import zz.utils.properties.SimpleRWProperty;

/**
 * Special property for transforms. It caches its inverse transform.
 * @author gpothier
 */
public class TransformProperty extends SimpleRWProperty<AffineTransform>
{
	private AffineTransform itsInverse = null;

	public TransformProperty()
	{
		super();
	}

	public TransformProperty(Object aOwner, AffineTransform aValue)
	{
		super(aOwner, aValue);
	}

	public TransformProperty(Object aOwner, PropertyId<AffineTransform> aPropertyId, AffineTransform aValue)
	{
		super (aOwner, aPropertyId, aValue);
	}

	public TransformProperty(Object aOwner, PropertyId<AffineTransform> aPropertyId)
	{
		super (aOwner, aPropertyId);
	}

	public TransformProperty(Object aOwner)
	{
		super (aOwner);
	}

	/**
	 * Returns the inverse transform of the transform currently
	 * held by this property. IF the transform is non invertible, a runtime
	 * exception is thrown.
	 */
	public AffineTransform getInverse()
	{
		if (itsInverse == null)
		{
			try
			{
				itsInverse = get().createInverse();
			}
			catch (NoninvertibleTransformException e)
			{
				throw new RuntimeException("Cannot inverse transform");
			}
		}
		
		return itsInverse;
	}
	
	@Override
	public void set(AffineTransform aValue)
	{
		super.set(aValue);
		itsInverse = null;
	}
}
