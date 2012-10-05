/**
 * Created by IntelliJ IDEA.
 * User: g1
 * Date: 11 mars 2004
 * Time: 23:25:40
 * To change this template use File | Settings | File Templates.
 */
package net.basekit.models.selection.messages;

import net.basekit.messages.WidgetModelMessage;
import net.basekit.models.selection.SelectionWidgetModel;

/**
 * Base class for messages that indicate that selection changed.
 * For now we do not refine into subclasses.
 * @author gpothier
 */
public class SelectionChangedMessage extends WidgetModelMessage
{
	public SelectionChangedMessage (SelectionWidgetModel aSelectionWidgetModel)
	{
		super (aSelectionWidgetModel);
	}

	public SelectionWidgetModel getSelectionWidgetModel ()
	{
		return (SelectionWidgetModel) getSource ();
	}
}
