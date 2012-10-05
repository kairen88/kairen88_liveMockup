/*
 * Created on Mar 3, 2004
 */
package net.basekit.devices.mouse.messages;

import java.awt.Point;
import java.util.List;

import javax.vecmath.Point3f;

import net.basekit.devices.mouse.Mouse;
import net.basekit.widgets.Widget;

/**
 * Sent when the mouse moves over a widget with no button pressed.
 * @author gpothier
 */
public class MouseMoved extends MousePositionMessage
{
	public MouseMoved(Widget aSource, List aWidgets, Mouse aPointer2D, Point aIts2dPosition, Point3f aIts3dPosition)
	{
		super(aSource, aWidgets,  aPointer2D, aIts2dPosition, aIts3dPosition);
	}
}
