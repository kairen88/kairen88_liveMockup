/**
 * Created by IntelliJ IDEA.
 * User: g1
 * Date: 11 mars 2004
 * Time: 22:58:32
 * To change this template use File | Settings | File Templates.
 */
package net.basekit.models.combo.messages;

import net.basekit.messages.WidgetModelMessage;
import net.basekit.models.WidgetModel;
import net.basekit.models.combo.ComboWidgetModel;

/**
 * Sent when a combo's selected item changes.
 * @author gpothier
 */
public class SelectedItemChangedMessage extends WidgetModelMessage
{
	private Object itsSelectedItem;

	public SelectedItemChangedMessage (WidgetModel aSource, Object aSelectedItem)
	{
		super (aSource);
		itsSelectedItem = aSelectedItem;
	}

	public ComboWidgetModel getComboWidgetModel ()
	{
		return (ComboWidgetModel) getSource ();
	}

	public Object getSelectedItem ()
	{
		return itsSelectedItem;
	}
}
