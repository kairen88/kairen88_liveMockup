/*
 * Created on Dec 21, 2004
 */
package zz.waltz.api.action;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import zz.utils.properties.IProperty;
import zz.utils.properties.IRWProperty;
import zz.utils.properties.SimpleRWProperty;
import zz.waltz.api.action.enable.EnableProperty;

/**
 * A simple implementation of {@link zz.waltz.api.action.IActionModel}
 * All the properties can be accessed through public final fields,
 * and all are read-write.
 * @author gpothier
 */
public class DefaultActionModel implements IActionModel
{

	public final IRWProperty<String> pName = new SimpleRWProperty<String>(this);
	public final IRWProperty<String> pDescription = new SimpleRWProperty<String>(this); 
	public final IRWProperty<String> pTooltip = new SimpleRWProperty<String>(this);
	public final IRWProperty<ImageIcon> pIcon = new SimpleRWProperty<ImageIcon>(this);
	public final EnableProperty pEnabled = new EnableProperty(this);
	
	
	public DefaultActionModel()
	{
	}
	
	public DefaultActionModel(String aName)
	{
		pName.set(aName);
	}
	
	public DefaultActionModel(String aName, String aDescription, String aTooltip, ImageIcon aIcon, Boolean aEnabled)
	{
		pName.set(aName);
		pDescription.set(aDescription);
		pTooltip.set(aTooltip);
		pIcon.set(aIcon);
		pEnabled.set(aEnabled);
	}
	
	public IRWProperty<String> pDescription()
	{
		return pDescription;
	}
	
	public IRWProperty<Boolean> pEnabled()
	{
		return pEnabled;
	}
	
	public IRWProperty<ImageIcon> pIcon()
	{
		return pIcon;
	}
	
	public IRWProperty<String> pName()
	{
		return pName;
	}
	
	public IRWProperty<String> pTooltip()
	{
		return pTooltip;
	}
	
	/**
	 * Does nothing. Should be overridden.
	 */
	public void performed(JComponent aComponent)
	{
	}
}
