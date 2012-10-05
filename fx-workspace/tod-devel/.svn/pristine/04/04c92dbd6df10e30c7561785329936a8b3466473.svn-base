/*
 * Created on Dec 22, 2004
 */
package zz.waltz.api.action.enable;

import java.util.List;

import zz.utils.Utils;
import zz.utils.properties.IProperty;

/**
 * This property is true iff the observed property's value, which must be
 * a list, is empty.
 * @author gpothier
 */
public class EmptyCondition extends AbstractPropertyWrapperCondition<List<?>>
{
	public EmptyCondition(IProperty<List<?>> aProperty)
	{
		super(aProperty);
		update();
	}
	
	protected boolean evaluate(List<?> aValue)
	{
		return aValue.size() == 0;
	}
}
