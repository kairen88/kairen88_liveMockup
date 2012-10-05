/*
 * Created on Dec 22, 2004
 */
package zz.waltz.api.action.enable;

import java.util.Collection;
import java.util.List;

import zz.utils.Utils;
import zz.utils.properties.IProperty;

/**
 * This property is true iff the observed property's value, which must be
 * a list, is not empty.
 * @author gpothier
 */
public class NotEmptyCondition extends AbstractPropertyWrapperCondition<Collection<?>>
{
	public NotEmptyCondition(IProperty<Collection<?>> aProperty)
	{
		super(aProperty);
		update();
	}
	
	protected boolean evaluate(Collection<?> aValue)
	{
		return aValue.size() > 0;
	}
}
