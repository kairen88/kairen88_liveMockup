/**
 * Created by IntelliJ IDEA.
 * User: g1
 * Date: 11 mars 2004
 * Time: 23:16:58
 * To change this template use File | Settings | File Templates.
 */
package net.basekit.models.selection;

import net.basekit.models.WidgetModel;

/**
 * Selection model that defines the selection behaviour of widgets that
 * permit to select elements.
 * @author gpothier
 */
public interface SelectionWidgetModel extends WidgetModel
{
	/**
	 * @return The selectable model on which this selection operates.
	 */
	public SelectableWidgetModel getSelectableWidgetModel ();

	/**
	 * Resets the selection
	 */
	public void reset ();

	/**
	 * Marks the element at the specified index as selected
	 */
	public void add (int aIndex);

	/**
	 * Marks the element at the specified index as not selected
	 */
	public void remove (int aIndex);

	/**
	 * Changes the selection state of the element at the specified index.
	 */
	public void toggle (int aIndex);

	/**
	 * @return The indices of all selected elements.
	 */
	public int[] getSelectedIndices ();

	/**
	 * Sets all the selected indices.
	 */
	public void setSelectedIndices (int[] aSelectedIndices);

	/**
	 * returns whether the element at the specified index is selected.
	 */ 
	public boolean isSelected (int aIndex);
}
