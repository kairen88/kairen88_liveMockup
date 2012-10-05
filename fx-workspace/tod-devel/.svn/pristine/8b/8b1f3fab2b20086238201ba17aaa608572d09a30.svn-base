/*
 * Created on Dec 23, 2004
 */
package zz.waltz.api.property;

import javax.swing.JComponent;

import zz.utils.properties.IProperty;
import zz.utils.properties.IRWProperty;

/**
 * Defines the look & feel of a property editor.
 * @param <P> Thy type of property acceptable by this style.
 * @author gpothier
 */
public interface IPropertyStyle<P extends IProperty>
{
	/**
	 * Creates the component that permits to edit the property.
	 */
	public JComponent createComponent (P aProperty, IProperty<Boolean> aEnabled);
}
