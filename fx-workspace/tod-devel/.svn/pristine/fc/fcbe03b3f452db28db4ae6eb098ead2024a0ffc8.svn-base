/*
 * Created on Dec 23, 2004
 */
package zz.waltz.api.property;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import zz.utils.properties.IProperty;
import zz.utils.properties.IPropertyListener;
import zz.utils.properties.IRWProperty;
import zz.utils.properties.PropertyId;

/**
 * A property style suitable for editing String properties.
 * It displays a JTextField
 * @author gpothier
 */
public class TextFieldPropertyStyle implements IPropertyStyle<IRWProperty<String>>
{
	private int itsColumnsCount;
	
	public TextFieldPropertyStyle(int aColumnsCount)
	{
		itsColumnsCount = aColumnsCount;
	}

	public JComponent createComponent(
			IRWProperty<String> aProperty, 
			IProperty<Boolean> aEnabled)
	{
		return new MyTextField(aProperty, aEnabled);
	}

	private class MyTextField extends JTextField
	implements IPropertyListener<Object>, DocumentListener
	{
		private IRWProperty<String> itsProperty;
		private final IProperty<Boolean> itsEnabled;
		
		private boolean itsUpdating = false;
		
		public MyTextField(
				IRWProperty<String> aProperty, 
				IProperty<Boolean> aEnabled)
		{
			super (itsColumnsCount);
			
			itsProperty = aProperty;
			itsEnabled = aEnabled;
			
			itsProperty.addListener(this);
			update();
			
			itsEnabled.addListener(this);
			
			getDocument().addDocumentListener(this);
		}

		private void update()
		{
			if (itsUpdating) return;
			
			itsUpdating = true;
			setText(itsProperty.get());
			setEnabled(itsEnabled.get());
			itsUpdating = false;
		}
		
		public void propertyChanged(IProperty<Object> aProperty, Object aOldValue, Object aNewValue)
		{
			update();
		}

		public void propertyValueChanged(IProperty<Object> aProperty)
		{
			assert false; // This should never happen (Boolean is immutable)
		}

		private void contentChanged()
		{
			if (itsUpdating) return;
			
			itsUpdating = true;
			itsProperty.set(getText());
			itsUpdating = false;
		}
		
		public void changedUpdate(DocumentEvent aE)
		{
			contentChanged();
		}

		public void insertUpdate(DocumentEvent aE)
		{
			contentChanged();
		}

		public void removeUpdate(DocumentEvent aE)
		{
			contentChanged();
		}
	}
	
}
