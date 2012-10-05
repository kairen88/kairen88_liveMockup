/*
 * Created on Dec 23, 2004
 */
package zz.waltz.api.property;

import java.text.ParseException;

import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import zz.utils.properties.IProperty;
import zz.utils.properties.IPropertyListener;
import zz.utils.properties.IRWProperty;

/**
 * A property style suitable for editing integer properties.
 * It displays a spinner
 * @author gpothier
 */
public class SpinnerPropertyStyle<T> implements IPropertyStyle<IRWProperty<T>>
{
	private final SpinnerModel itsModel;

	public SpinnerPropertyStyle(SpinnerModel aModel)
	{
		itsModel = aModel;
	}

	public JComponent createComponent(
			IRWProperty<T> aProperty, 
			IProperty<Boolean> aEnabled)
	{
		return new MySpinner(aProperty, aEnabled);
	}

	private class MySpinner extends JSpinner
	implements IPropertyListener<Object>, ChangeListener, DocumentListener
	{
		private IRWProperty<T> itsProperty;
		private final IProperty<Boolean> itsEnabled;
		
		private boolean itsUpdating = false;
		
		public MySpinner(
				IRWProperty<T> aProperty, 
				IProperty<Boolean> aEnabled)
		{
			itsProperty = aProperty;
			itsEnabled = aEnabled;
			
			setModel(itsModel);
			
			// We want to be notified of each document change, as the property must be updated
			// in realtime.
			DefaultEditor theEditor = (DefaultEditor) getEditor();
			theEditor.getTextField().getDocument().addDocumentListener(this);
			
			itsProperty.addListener(this);
			update();
			
			itsEnabled.addListener(this);
			
			itsModel.addChangeListener(this);
		}

		private void update()
		{
			if (itsUpdating) return;
			
			itsUpdating = true;
			itsModel.setValue(itsProperty.get());			
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

		public void stateChanged(ChangeEvent aE)
		{
			itsProperty.set((T) itsModel.getValue());
		}

		private void edited()
		{
			if (itsUpdating) return;
			
			SwingUtilities.invokeLater(new Runnable()
					{

						public void run()
						{
							itsUpdating = true;
							try
							{
								// If we don't save/restore the caret position, it tends to randomly jump...
								DefaultEditor theEditor = (DefaultEditor) getEditor();
								JFormattedTextField theTextField = theEditor.getTextField();
								int theCaretPosition = theTextField.getCaretPosition();
								commitEdit();
								theTextField.setCaretPosition(theCaretPosition);
							}
							catch (ParseException e)
							{
							}
							itsUpdating = false;
						}
					});
		}
		
		public void changedUpdate(DocumentEvent aE)
		{
			edited();
		}

		public void insertUpdate(DocumentEvent aE)
		{
			edited();
		}

		public void removeUpdate(DocumentEvent aE)
		{
			edited();
		}
	}
}
