/*
 * Created on Mar 3, 2004
 */
package net.basekit.devices.mouse;

import java.awt.Point;

/**
 * Represents a 2D pointer like a mouse.
 * @author gpothier
 */
public interface Mouse
{
	public static final int MAIN_BUTTON = 0;
	public static final int MIDDLE_BUTTON = 1;
	public static final int CONTEXT_BUTTON = 2;
	
	/**
	 * Returns the number of buttons.
	 */
	public int getNButtons ();
	
	/**
	 * Returns the number of wheels.
	 */
	public int getNWheels ();
	
	/**
	 * Whether the specified button is pressed
	 * @param aButton Button index
	 */
	public boolean isButtonPressed (int aButton);
	
	/**
	 * Returns the "absolute" position of the wheel. The absolute position is incremented
	 * for each wheel up and decremented for each wheel down.
	 * @param aWheel A wheel index.
	 */
	public int getWheelPosition (int aWheel);
	
	/**
	 * Returns the pointer's position.
	 */
	public Point getPosition ();
}
