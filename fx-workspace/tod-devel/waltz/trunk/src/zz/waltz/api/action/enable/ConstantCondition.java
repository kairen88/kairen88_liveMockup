/*
 * Created on Dec 27, 2004
 */
package zz.waltz.api.action.enable;

import zz.utils.properties.SimpleProperty;

/**
 * A constant condition, that is either always true, or always false
 * @author gpothier
 */
public class ConstantCondition extends SimpleProperty<Boolean>
{
	public ConstantCondition(Boolean aValue)
	{
		super(null, aValue);
	}
}
