/*
 * Created on Mar 3, 2004
 */
package net.basekit.devices.mouse.messages;

import java.util.List;

import net.basekit.devices.mouse.Mouse;
import net.basekit.widgets.Widget;

/**
 * @author gpothier
 */
public class MouseReleased extends MouseMessage
{
	/**
	 * Index of the button that was released.
	 * @see Mouse.MAIN_BUTTON
	 */
	private int itsButton;
	
	
	public MouseReleased (Widget aSource, List aWidgets, Mouse aPointer2D, int aButton)
	{
		super(aSource, aWidgets, aPointer2D);
		itsButton = aButton;
	}
	
	public int getButton ()
	{
		return itsButton;
	}
	
}
