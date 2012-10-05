/**
 * Created by IntelliJ IDEA.
 * User: g1
 * Date: 11 mars 2004
 * Time: 22:54:20
 * To change this template use File | Settings | File Templates.
 */
package net.basekit.models.action;

import net.basekit.models.action.messages.ActionTriggeredMessage;
import net.basekit.models.label.DefaultLabelWidgetModel;

/**
 * Default implementation of {@link ActionWidgetModel}.
 * @author gpothier
 */
public class DefaultActionWidgetModel extends DefaultLabelWidgetModel implements ActionWidgetModel
{
	public DefaultActionWidgetModel ()
	{
		
	}
	
	public DefaultActionWidgetModel (String aTitle)
	{
		super(aTitle);
	}

	/**
	 * Sends a {@link ActionTriggeredMessage}.
	 */
	public void trigger ()
	{
		fire (new ActionTriggeredMessage (this));
	}
}
