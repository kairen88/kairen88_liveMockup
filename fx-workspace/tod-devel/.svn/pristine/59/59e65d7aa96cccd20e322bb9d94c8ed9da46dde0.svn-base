/*
 * Created on Dec 22, 2004
 */
package zz.waltz.api.action.enable;

import java.util.ArrayList;
import java.util.List;

import zz.utils.properties.AbstractProperty;
import zz.utils.properties.IProperty;
import zz.utils.properties.IPropertyListener;
import zz.utils.properties.SimpleRWProperty;

/**
 * A special implementation of {@link zz.utils.properties.IProperty}
 * for enable condition of action models.
 * It is the logical AND of several boolean properties.
 * @author gpothier
 */
public class EnableProperty extends SimpleRWProperty<Boolean> 
implements IPropertyListener<Boolean>
{
	/**
	 * The list of conditions that must be verified for this 
	 * property to be true.
	 */
	private List<IProperty<Boolean>> itsConditions =
		new ArrayList<IProperty<Boolean>>();
	
	public EnableProperty(Object aContainer)
	{
		super(aContainer);
		update();
	}

	public void addCondition (IProperty<Boolean> aCondition)
	{
		itsConditions.add(aCondition);
		aCondition.addHardListener(this);
		update();
	}
	
	public void removeCondition (IProperty<Boolean> aCondition)
	{
		itsConditions.remove(aCondition);
		aCondition.removeListener(this);
		update();
	}
	
	public void propertyChanged(IProperty<Boolean> aProperty, Boolean aOldValue, Boolean aNewValue)
	{
		update();
	}
	
	public void propertyValueChanged(IProperty<Boolean> aProperty)
	{
		update();
	}
	
	/**
	 * Updates the state of this property according
	 * to all its conditions.
	 */
	private void update()
	{
		if (itsConditions.size() > 0)
		{
			boolean theResult = true;
			
			for (IProperty<Boolean> theCondition : itsConditions)
			{
				if (! theCondition.get())
				{
					theResult = false;
					break;
				}
			}
			
			set(theResult);
		}
		else if (get() == null) set(true);
	}
}
