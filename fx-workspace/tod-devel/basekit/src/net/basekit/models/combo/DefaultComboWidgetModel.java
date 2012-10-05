/**
 * Created by IntelliJ IDEA.
 * User: g1
 * Date: 11 mars 2004
 * Time: 23:00:11
 * To change this template use File | Settings | File Templates.
 */
package net.basekit.models.combo;

import java.util.List;

import net.basekit.models.combo.messages.SelectedItemChangedMessage;
import net.basekit.models.list.DefaultListContentWidgetModel;
import zz.utils.Utils;

/**
 * Default implementation of {@link ComboWidgetModel} based on
 * {@link DefaultListContentWidgetModel}.
 * @author gpothier
 */
public class DefaultComboWidgetModel extends DefaultListContentWidgetModel implements ComboWidgetModel
{
	private Object itsSelectedItem;

	public DefaultComboWidgetModel ()
	{
	}

	public DefaultComboWidgetModel (List aList)
	{
		super (aList);
	}

	public DefaultComboWidgetModel (List aList, Object aSelectedItem)
	{
		super (aList);
		itsSelectedItem = aSelectedItem;
	}

	public Object getSelectedItem ()
	{
		return itsSelectedItem;
	}

	public void setSelectedItem (Object aSelectedItem)
	{
		boolean theChanged = Utils.different (itsSelectedItem, aSelectedItem);
		itsSelectedItem = aSelectedItem;
		if (theChanged) fireSelectedItemChanged ();
	}

	/**
	 * Fires a {@link SelectedItemChangedMessage}.
	 */
	protected void fireSelectedItemChanged ()
	{
		fire (new SelectedItemChangedMessage (this, getSelectedItem ()));
	}
}
