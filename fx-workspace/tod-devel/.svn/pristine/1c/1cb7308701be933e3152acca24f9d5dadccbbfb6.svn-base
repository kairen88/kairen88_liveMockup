/*
 * Created on Mar 5, 2004
 */
package net.basekit.devices.mouse.messages;

import java.awt.Point;
import java.util.List;

import javax.vecmath.Point3f;

import net.basekit.devices.mouse.Mouse;
import net.basekit.widgets.Widget;

/**
 * Base comodity class that maintains a pointer position.
 * @author gpothier
 */
public abstract class MousePositionMessage extends MouseMessage
{
	/**
	 * Position of the pointer in screen space.
	 */
	private Point its2dPosition;
	
	/**
	 * Position of the intersection of a ray passing through the pointer
	 * and the source widget.
	 */
	private Point3f its3dPosition;

	public MousePositionMessage (Widget aSource, List aWidgets, Mouse aPointer2D, Point aIts2dPosition,
			Point3f aIts3dPosition) 
	{
		super(aSource, aWidgets, aPointer2D);
		its2dPosition = aIts2dPosition;
		its3dPosition = aIts3dPosition;
	}

	public Point get2dPosition ()
	{
		return its2dPosition;
	}
	
	public Point3f get3dPosition ()
	{
		return its3dPosition;
	}
}
