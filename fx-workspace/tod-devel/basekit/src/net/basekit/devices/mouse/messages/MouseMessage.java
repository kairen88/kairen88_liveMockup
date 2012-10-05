/*
 * Created on Mar 3, 2004
 */
package net.basekit.devices.mouse.messages;

import java.util.Iterator;
import java.util.List;

import net.basekit.devices.mouse.Mouse;
import net.basekit.messages.WidgetMessage;
import net.basekit.widgets.Widget;

/**
 * Base classe for mouse related messages.
 * @author gpothier
 */
public abstract class MouseMessage extends WidgetMessage
{
	/**
	 * The mouse that caused this message to be fired.
	 */
	private Mouse itsMouse;

	/**
	 * List of all widgets that intersected the ray that
	 * triggerd this message.
	 */
	private List itsWidgets;

	/**
	 * Whether an observer has consumed the message, which means that it shouldn't
	 * be passed on to other observers.
	 */
	private boolean itsConsumed;



	public MouseMessage (Widget aSource, List aWidgets, Mouse aPointer2D)
	{
		super(aSource);
		itsMouse = aPointer2D;
		itsWidgets = aWidgets;
	}
	
	public Mouse getPointer2D ()
	{
		return itsMouse;
	}

	/**
	 * Returns the list of all the widgets that intersected the same ray as
	 * this message's source widget.
	 */
	public Iterator getWidgetsIterator ()
	{
		return itsWidgets.iterator ();
	}

	public boolean isConsumed ()
	{
		return itsConsumed;
	}

	public void consume ()
	{
		itsConsumed = true;
	}
	
}
