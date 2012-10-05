/**
 * Created by IntelliJ IDEA.
 * User: g1
 * Date: 11 mars 2004
 * Time: 23:30:09
 * To change this template use File | Settings | File Templates.
 */
package net.basekit.models.selection;

import net.basekit.models.WidgetModel;

/**
 * To be implemented by widget models that support that their elements be selected.
 * Simply provides a method to retrieve an object at a given index,
 * as the selection model maintains a set of selected indices.
 * @author gpothier
 */
public interface SelectableWidgetModel extends WidgetModel
{
	public Object getElementAt (int aIndex);
}
