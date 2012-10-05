/*
 * Created on Dec 23, 2004
 */
package zz.waltz.api.property;

import javax.swing.JCheckBox;
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
 * It displays a check box
 * @author gpothier
 */
public class CheckBoxPropertyStyle implements IPropertyStyle<IRWProperty<Boolean>>
{
	private boolean itsShowTitle = false;

	public CheckBoxPropertyStyle(boolean aShowTitle)
	{
		itsShowTitle = aShowTitle;
	}
	
	public JComponent createComponent(
			IRWProperty<Boolean> aProperty, 
			IProperty<Boolean> aEnabled)
	{
		return new MyCheckBox(aProperty, aEnabled);
	}

	private class MyCheckBox extends JCheckBox
	implements IPropertyListener<Boolean>, ChangeListener
	{
		private IRWProperty<Boolean> itsProperty;
		private final IProperty<Boolean> itsEnabled;
		
		public MyCheckBox(
				IRWProperty<Boolean> aProperty, 
				IProperty<Boolean> aEnabled)
		{
			itsProperty = aProperty;
			itsEnabled = aEnabled;
			
			PropertyId<Boolean> thePropertyId = itsProperty.getId();
			if (itsShowTitle) 
			{
				setText(thePropertyId != null ? thePropertyId.getName() : "null");
			}
			
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
