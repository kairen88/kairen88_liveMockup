/*
 * Created on Dec 23, 2004
 */
package zz.waltz.api.property;

import javax.swing.JComponent;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import zz.utils.properties.IProperty;
import zz.utils.properties.IPropertyListener;
import zz.utils.properties.IRWProperty;
import zz.utils.properties.PropertyId;

/**
 * A property style suitable for editing boolean properties.
 * It displays a toggle button
 * @author gpothier
 */
public class ButtonPropertyStyle implements IPropertyStyle<IRWProperty<Boolean>>
{

	public JComponent createComponent(
			IRWProperty<Boolean> aProperty, 
			IProperty<Boolean> aEnabled)
	{
		return new MyButton(aProperty, aEnabled);
	}

	private static class MyButton extends JToggleButton
	implements IPropertyListener<Boolean>, ChangeListener
	{
		private IRWProperty<Boolean> itsProperty;
		private final IProperty<Boolean> itsEnabled;
		
		public MyButton(
				IRWProperty<Boolean> aProperty, 
				IProperty<Boolean> aEnabled)
		{
			itsProperty = aProperty;
			itsEnabled = aEnabled;
			
			PropertyId<Boolean> thePropertyId = itsProperty.getId();
			setText(thePropertyId != null ? thePropertyId.getName() : "null");
			
			itsProperty.addListener(this);
			update();
			
			itsEnabled.addListener(this);
			
			addChangeListener(this);
			
		}

		private void update()
		{
			setSelected(itsProperty.get());			
			setEnabled(itsEnabled.get());
		}
		
		public void propertyChanged(IProperty<Boolean> aProperty, Boolean aOldValue, Boolean aNewValue)
		{
			update();
		}

		public void propertyValueChanged(IProperty<Boolean> aProperty)
		{
			assert false; // This should never happen (Boolean is immutable)
		}

		public void stateChanged(ChangeEvent aE)
		{
			itsProperty.set(isSelected());
		}
	}
	
}
