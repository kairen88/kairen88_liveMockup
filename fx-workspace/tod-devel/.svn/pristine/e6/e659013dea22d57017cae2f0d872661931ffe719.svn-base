/*
 * Created on Dec 22, 2004
 */
package zz.waltz.api.action.enable;

import zz.utils.Utils;
import zz.utils.properties.IProperty;

/**
 * This property is true iff the observed property's value has a specified relationwhip
 * with the reference value.
 * @author gpothier
 */
public class ComparisonCondition<T extends Comparable> extends AbstractPropertyWrapperCondition<T>
{
	private final T itsReferenceValue;
	private final Comparison itsComparison;
	
	public ComparisonCondition(IProperty<T> aProperty, Comparison aComparison, T aReferenceValue)
	{
		super(aProperty);
		itsComparison = aComparison;
		itsReferenceValue = aReferenceValue;
		update();
	}
	
	protected boolean evaluate(T aValue)
	{
		return itsComparison.accept(aValue.compareTo(itsReferenceValue));
	}
}
