/*
 * Created on Dec 21, 2004
 */
package zz.waltz.api.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;

import zz.utils.properties.IProperty;
import zz.utils.properties.IPropertyListener;


/**
 * A style for actions that are represented by a button.
 * @author gpothier
 */
public class ButtonActionStyle implements IActionStyle
{
	public JComponent createComponent(IActionModel aModel)
	{
		return new ButtonActionComponent(aModel);
	}
	
	
	private static class ButtonActionComponent extends JButton 
	implements IPropertyListener<Object>, ActionListener
	{
		private IActionModel itsModel;
		
		public ButtonActionComponent(IActionModel aModel)
		{
			itsModel = aModel;
			reload();
			addActionListener(this);
		}
		
		public void actionPerformed(ActionEvent aE)
		{
			itsModel.performed(this);
		}
		
		public void addNotify()
		{
			super.addNotify();
			itsModel.pName().addHardListener(this);
			itsModel.pTooltip().addHardListener(this);
			itsModel.pIcon().addHardListener(this);
			itsModel.pEnabled().addHardListener(this);
		}
		
		public void removeNotify()
		{
			super.removeNotify();
			itsModel.pName().addHardListener(this);
			itsModel.pTooltip().addHardListener(this);
			itsModel.pIcon().addHardListener(this);
			itsModel.pEnabled().addHardListener(this);
		}
		
		public void propertyChanged(IProperty<Object> aProperty, Object aOldValue, Object aNewValue)
		{
			reload();
		}
		
		public void propertyValueChanged(IProperty<Object> aProperty)
		{
			reload();
		}
		
		/**
		 * Reloads properties from the model.
		 */
		private void reload()
		{
			String theName = itsModel.pName().get();
			String theTooltip = itsModel.pTooltip().get();
			ImageIcon theIcon = itsModel.pIcon().get();
			Boolean theEnabled = itsModel.pEnabled().get();
			
			setText(theName != null ? theName : "");
			setToolTipText(theTooltip != null ? theTooltip : null);
			setIcon(theIcon);
			setEnabled(theEnabled);
		}
	}

}
