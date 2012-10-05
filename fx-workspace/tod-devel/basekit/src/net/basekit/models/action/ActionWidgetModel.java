/**
 * Created by IntelliJ IDEA.
 * User: g1
 * Date: 11 mars 2004
 * Time: 22:45:33
 * To change this template use File | Settings | File Templates.
 */
package net.basekit.models.action;

import net.basekit.models.label.LabelWidgetModel;

/**
 * Model for an action that can be triggered.
 * @author gpothier
 */
public interface ActionWidgetModel extends LabelWidgetModel
{
	/**
	 * Sends a {@link net.basekit.models.action.messages.ActionTriggeredMessage}.
	 */
	public void trigger ();
}
