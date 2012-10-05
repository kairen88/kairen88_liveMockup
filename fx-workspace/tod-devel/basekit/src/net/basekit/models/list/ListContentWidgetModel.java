/**
 * Created by IntelliJ IDEA.
 * User: g1
 * Date: 11 mars 2004
 * Time: 22:03:00
 * To change this template use File | Settings | File Templates.
 */
package net.basekit.models.list;

import net.basekit.models.WidgetModel;
import net.basekit.models.selection.SelectableWidgetModel;

/**
 * Data model for a {@link net.basekit.widgets.list.ListWidget}.
 * We don't use the swing list model be cause:
 * <li>It uses a listener instead of observer (which is no big deal)
 * <li>Its subclass {@link javax.swing.ComboBoxModel} does not provide listener mechanism for
 * selected item, which IS a big problem. SO for consistency's sake we implement both list and
 * combop models.
 * @author gpothier
 */
public interface ListContentWidgetModel extends WidgetModel, SelectableWidgetModel
{
	/**
	 * @return The number of elements in the list.
	 */
	public int getSize ();

	/**
	 * Returns the element at the given position.
	 */
	public Object getElementAt (int aIndex);
}
