/*
 * Created on Dec 21, 2004
 */
package zz.waltz.api.action;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import zz.utils.properties.IProperty;

/**
 * The model of an action: it specifies the action's name, enabled state and other
 * properties, and also provides the behaviour.
 * @author gpothier
 */
public interface IActionModel
{
	/**
	 * Name of this action
	 */
	public IProperty<String> pName();
	
	/**
	 * Description of this action.
	 * Optional.
	 */
	public IProperty<String> pDescription();
	
	/**
	 * Tooltip for this action.
	 */
	public IProperty<String> pTooltip();
	
	/**
	 * Icon for this action.
	 */
	public IProperty<ImageIcon> pIcon();
	
	/**
	 * Indicates if the action is currently enabled.
	 */
	public IProperty<Boolean> pEnabled();
	
	/**
	 * Called when the action is performed.
	 * @param aComponent The component that triggered the action.
	 */
	public void performed(JComponent aComponent);
}
