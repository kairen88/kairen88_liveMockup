/*
 * Created on Jun 3, 2005
 */
package zz.csg.impl.constraints;

import EDU.Washington.grad.gjb.cassowary.ISimpleVariable;
import zz.csg.api.constraints.IConstrainedDouble;
import zz.csg.impl.AbstractGraphicObject;
import zz.utils.properties.PropertyId;
import zz.utils.properties.SimpleRWProperty;

/**
 * A property that can be constrained
 * @author gpothier
 */
public class ConstrainedDouble extends SimpleRWProperty<Double> 
implements IConstrainedDouble
{
	/**
	 * This flag indicates if the {@link zz.csg.api.constraints.ConstraintSystem}
	 * is currently editing this variable.
	 */
	private boolean itsEditing = false;
	
	public ConstrainedDouble()
	{
		this (null, null, 0.0);
	}
	
	public ConstrainedDouble(Double aInitialValue)
	{
		this (null, null, aInitialValue);
	}
	
	public ConstrainedDouble(
			Object aOwner, 
			PropertyId<Double> aPropertyId,
			Double aInitialValue)
	{
		super(aOwner, aPropertyId, aInitialValue);
	}

	public void setEditing(boolean aEditing)
	{
		itsEditing = aEditing;
	}

	public void change_value(double aValue)
	{
		if (aValue != get().doubleValue()) set (aValue);
	}

	@Override
	protected boolean canChangeProperty(Double aOldValue, Double aNewValue)
	{
		if (itsEditing) return true;
		else return super.canChangeProperty(aOldValue, aNewValue);
	}
	
	public double value()
	{
		return get();
	}

	public boolean isDummy()
	{
		return false;
	}

	public boolean isExternal()
	{
		return true;
	}

	public boolean isPivotable()
	{
		return false;
	}

	public boolean isRestricted()
	{
		return false;
	}

	
}
