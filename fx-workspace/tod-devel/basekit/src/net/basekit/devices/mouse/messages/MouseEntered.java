/*
 * Created on Mar 3, 2004
 */
package net.basekit.devices.mouse.messages;

import java.util.List;

import net.basekit.devices.mouse.Mouse;
import net.basekit.widgets.Widget;

/**
 * Sent when a mouse enters over a widget.
 * @author gpothier
 */
public class MouseEntered extends MouseMessage
{
	public MouseEntered (Widget aSource, List aWidgets, Mouse aPointer2D)
	{
		super(aSource, aWidgets, aPointer2D);
	}
}
