/*
 * Created on Mar 3, 2004
 */
package net.basekit.devices.mouse.messages;

import java.util.List;

import net.basekit.devices.mouse.Mouse;
import net.basekit.widgets.Widget;

/**
 * Message sent when the mouse wheel moves.
 * @author gpothier
 */
public class MouseWheelMoved extends MouseMessage
{
	private int itsAmount;

	public MouseWheelMoved (Widget aSource, List aWidgets, Mouse aPointer2D, int aAmount)
	{
		super (aSource, aWidgets, aPointer2D);
		itsAmount = aAmount;
	}

	public int getAmount ()
	{
		return itsAmount;
	}
}
