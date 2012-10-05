/*
 * Created on Dec 22, 2004
 */
package zz.waltz.api.action.enable;

import zz.utils.properties.IProperty;
import zz.utils.properties.IPropertyListener;
import zz.utils.properties.SimpleProperty;
import zz.utils.properties.SimpleRWProperty;

/**
 * This abstract class can be used
 * to implement conditions for the enable property,
 * that depends on the value of another property.
 * For instance, the property can be true iff the observed property's value
 * is not null.
 * @author gpothier
 */
public abstract class AbstractPropertyWrapperCondition<T> extends SimpleProperty<Boolean>
implements IPropertyListener<T>
{
	/**
	 * The observed property.
	 */
	private IProperty<T> itsProperty;
	
	public AbstractPropertyWrapperCondition(IProperty<T> aProperty)
	{
		itsProperty = aProperty;
		itsProperty.addHardListener(this);
	}
	
	public void propertyChanged(IProperty<T> aProperty, T aOldValue, T aNewValue)
	{
		update();
	}
	
	public void propertyValueChanged(IProperty<T> aProperty)
	{
		update();
	}
	
	/**
	 * Recomputes the value of this property
	 *
	 */
	protected void update()
	{
		set0(evaluate(itsProperty.get()));		
	}
	
	/**
	 * Evaluates the value that this property should have,
	 * according to the value of the observed property.
	 */
	protected abstract boolean evaluate(T aValue);
}
