/*
 * Created on Dec 22, 2004
 */
package zz.waltz.api.action.enable;

import zz.utils.Utils;
import zz.utils.properties.IProperty;

/**
 * This property is true iff the observed property's value is
 * equal to a reference value
 * @author gpothier
 */
public class EqualCondition<T> extends AbstractPropertyWrapperCondition<T>
{
	private T itsReferenceValue;
	
	public EqualCondition(IProperty<T> aProperty, T aReferenceValue)
	{
		super(aProperty);
		itsReferenceValue = aReferenceValue;
		update();
	}
	
	protected boolean evaluate(T aValue)
	{
		return Utils.equalOrBothNull(aValue, itsReferenceValue);
	}
}
