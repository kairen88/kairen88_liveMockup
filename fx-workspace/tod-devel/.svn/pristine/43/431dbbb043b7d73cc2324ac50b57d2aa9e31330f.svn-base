/*
 * Created on Dec 23, 2004
 */
package zz.waltz.api.property;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;

import zz.utils.properties.IListProperty;
import zz.utils.properties.IProperty;
import zz.utils.properties.IPropertyListener;
import zz.utils.properties.IRWProperty;

/**
 * A property style suitable for editing String properties.
 * It displays an editable combo box.
 * @author gpothier
 */
public class TextComboPropertyStyle implements IPropertyStyle<IRWProperty<String>>
{
	private IListProperty<String> itsChoicesProperty;

	public TextComboPropertyStyle(IListProperty<String> aChoicesProperty)
	{
		itsChoicesProperty = aChoicesProperty;
	}

	public JComponent createComponent(
			IRWProperty<String> aProperty, 
			IProperty<Boolean> aEnabled)
	{
		return new MyCombo(aProperty, aEnabled);
	}

	private class MyCombo extends JComboBox
	implements IPropertyListener<Object>
	{
		private final IProperty<Boolean> itsEnabled;
		
		private boolean itsUpdating = false;
		
		public MyCombo(
				IRWProperty<String> aProperty, 
				IProperty<Boolean> aEnabled)
		{
			super (new MyComboModel(itsChoicesProperty, aProperty));
			
			itsEnabled = aEnabled;
			itsEnabled.addListener(this);
		}

		private void update()
		{
			if (itsUpdating) return;
			
			itsUpdating = true;
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
	}
	
	/**
	 * This is our combo box model, that backs all its state in properties.
	 * @author gpothier
	 */
	private static class MyComboModel extends AbstractListModel 
	implements ComboBoxModel, IPropertyListener<String>
	{
		private IListProperty<String> itsChoicesProperty;
		private IRWProperty<String> itsValueProperty;

		public MyComboModel(IListProperty<String> aChoicesProperty, IRWProperty<String> aValueProperty)
		{
			itsChoicesProperty = aChoicesProperty;
			itsValueProperty = aValueProperty;
			
			itsValueProperty.addListener(this);
		}

		public void setSelectedItem(Object aItem)
		{
			itsValueProperty.set((String) aItem);
		}

		public Object getSelectedItem()
		{
			return itsValueProperty.get();
		}

		public int getSize()
		{
			return itsChoicesProperty.size();
		}

		public Object getElementAt(int aIndex)
		{
			return itsChoicesProperty.get(aIndex);
		}

		public void propertyChanged(IProperty<String> aProperty, String aOldValue, String aNewValue)
		{
			fireContentsChanged(this, -1, -1);
		}

		public void propertyValueChanged(IProperty<String> aProperty)
		{
		}
		
		
	}
	
}
