/**
 * Created by IntelliJ IDEA.
 * User: g1
 * Date: 11 mars 2004
 * Time: 22:52:54
 * To change this template use File | Settings | File Templates.
 */
package net.basekit.models.action.messages;

import net.basekit.messages.WidgetModelMessage;
import net.basekit.models.WidgetModel;
import net.basekit.models.action.ActionWidgetModel;

/**
 * @author gpothier
 */
public abstract class AbstractActionMessage extends WidgetModelMessage
{
	public AbstractActionMessage (WidgetModel aSource)
	{
		super (aSource);
	}

	public ActionWidgetModel getActionWidgetModel ()
	{
		return (ActionWidgetModel) getSource ();
	}
}
