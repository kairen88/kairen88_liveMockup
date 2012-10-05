/*
 * Created on Mar 6, 2004
 */
package net.basekit.widgets.list;

import net.basekit.models.list.messages.IntervalAddedMessage;
import net.basekit.models.list.messages.IntervalRemovedMessage;
import net.basekit.widgets.IndividualRenderer;

/**
 * Renderer for a {@link net.basekit.widgets.list.ListWidget}. This is not the
 * renderer for each individual element but for the list as a whole.
 * <p>
 * The list widget notifies the renderer of modifications to its model through the methods
 * of the {@link javax.swing.event.ListDataListener} interface. These methods are implemented in
 * this class but they do nothing.
 * @author gpothier
 */
public abstract class ListRenderer extends IndividualRenderer 
{
	public ListWidget getListWidget ()
	{
		return (ListWidget) getRenderedWidget();
	}

	/**
	 * Rebuilds all content.
	 * Called when the list widget changes its model or element renderer.
	 */
	public void reset ()
	{
	}
	
	public void intervalAdded (IntervalAddedMessage aMessage)
	{
	}
	
	public void intervalRemoved (IntervalRemovedMessage aMessage)
	{
	}
}
