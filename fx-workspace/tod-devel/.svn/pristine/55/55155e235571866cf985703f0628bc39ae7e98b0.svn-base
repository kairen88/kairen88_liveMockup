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
public class MousePressed extends MouseMessage
{
	/**
	 * Index of the button that was pressed.
	 * @see Mouse.MAIN_BUTTON
	 */
	private int itsButton;
	
	/**
	 * Numer of times this button has been pressed since the mouse pointer
	 * is in its current position (with some tolerance).
	 */
	private int itsClickCount;
	

	public MousePressed (Widget aSource, List aWidgets, Mouse aPointer2D, int aButton,
			int aClickCount)  
	{
		super(aSource, aWidgets, aPointer2D);
		itsButton = aButton;
		itsClickCount = aClickCount;
	}
	
	public int getButton ()
	{
		return itsButton;
	}
	
}
