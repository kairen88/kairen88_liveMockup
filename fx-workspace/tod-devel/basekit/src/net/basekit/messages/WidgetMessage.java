/*
 * Created on Mar 3, 2004
 */
package net.basekit.messages;

import net.basekit.Message;
import net.basekit.widgets.Widget;

/**
 * Base classe for widget-related messages.
 * @see Widget#fire(net.basekit.messages.WidgetMessage) 
 * @author gpothier
 */
public abstract class WidgetMessage extends Message
{
	/**
	 * The widget that generated the message.
	 */
	private Widget itsSource;
	
	public WidgetMessage (Widget aSource)
	{
		itsSource = aSource;
	}
	
	public Widget getSource ()
	{
		return itsSource;
	}
	
}
