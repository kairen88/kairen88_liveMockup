/**
 * Created by IntelliJ IDEA.
 * User: g1
 * Date: 11 mars 2004
 * Time: 23:23:18
 * To change this template use File | Settings | File Templates.
 */
package net.basekit.models.selection;

import net.basekit.models.AbstractWidgetModel;
import net.basekit.models.selection.messages.SelectionChangedMessage;

/**
 * @author gpothier
 */
public class SimpleSelectionWidgetModel extends AbstractWidgetModel implements SelectionWidgetModel
{
	private SelectableWidgetModel itsSelectableWidgetModel;
	private int itsSelectedIndex;

	public SimpleSelectionWidgetModel (SelectableWidgetModel aSelectableWidgetModel)
	{
		itsSelectableWidgetModel = aSelectableWidgetModel;
	}

	public SelectableWidgetModel getSelectableWidgetModel ()
	{
		return itsSelectableWidgetModel;
	}

	public int getSelectedIndex ()
	{
		return itsSelectedIndex;
	}

	public void setSelectedIndex (int aSelectedIndex)
	{
		boolean theChanged = itsSelectedIndex != aSelectedIndex;
		itsSelectedIndex = aSelectedIndex;
		if (theChanged) fire (new SelectionChangedMessage (this));
	}

	public void reset ()
	{
		setSelectedIndex (-1);
	}

	public void add (int aIndex)
	{
		setSelectedIndex (aIndex);
	}

	public void remove (int aIndex)
	{
		if (aIndex == itsSelectedIndex) reset ();
	}

	public void toggle (int aIndex)
	{
		if (aIndex == itsSelectedIndex) reset ();
		else setSelectedIndex (aIndex);
	}

	public int[] getSelectedIndices ()
	{
		return new int[] {itsSelectedIndex};
	}

	public void setSelectedIndices (int[] aSelectedIndices)
	{
		setSelectedIndex (aSelectedIndices.length > 0 ? aSelectedIndices[0] : -1);
	}

	public boolean isSelected (int aIndex)
	{
		return aIndex >= 0 && aIndex == itsSelectedIndex;
	}
}
