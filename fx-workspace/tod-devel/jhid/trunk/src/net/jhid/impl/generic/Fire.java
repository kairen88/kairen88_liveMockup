/*
    jhid - Java API for Human Interaction Devices
    (c) Copyright 2004 Guillaume Pothier
    Project home page: jhid.sourceforge.net

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/

package net.jhid.impl.generic;

import java.util.Iterator;

import net.jhid.core.listeners.*;

/**
 * Provides methods to fire an event to all
 * the listeners of a slot provided
 * they implement the proper listener interface.
 * These methods also fire to the device.
 * @author gpothier
 */
public class Fire
{
	public static void fireValueChanged (RelativeAxis aAxis, int aDelta, int aValue)
	{
		fireValueChanged(aAxis.getListenersIterator(), aAxis, aDelta, aValue);
		fireValueChanged(aAxis.getAbstractDevice ().getListenersIterator(), aAxis, aDelta, aValue);
	}
	
	private static void fireValueChanged (Iterator aListenersIterator, RelativeAxis aAxis, int aDelta, int aValue)
	{
		while (aListenersIterator.hasNext())
		{
			Object lListener = (Object) aListenersIterator.next();
			if (lListener instanceof IRelativeAxisListener)
			{
				IRelativeAxisListener lRelativeListener = (IRelativeAxisListener) lListener;
				lRelativeListener.valueChanged(aAxis, aDelta, aValue);
			}
		}		
	}
	
	public static void fireValueChanged (AbsoluteAxis aAxis, int aValue)
	{
		fireValueChanged(aAxis.getListenersIterator(), aAxis, aValue);
		fireValueChanged(aAxis.getAbstractDevice ().getListenersIterator(), aAxis, aValue);
	}
	
	private static void fireValueChanged (Iterator aListenersIterator, AbsoluteAxis aAxis, int aValue)
	{
		while (aListenersIterator.hasNext())
		{
			Object lListener = (Object) aListenersIterator.next();
			if (lListener instanceof IAbsoluteAxisListener)
			{
				IAbsoluteAxisListener lAbsoluteListener = (IAbsoluteAxisListener) lListener;
				lAbsoluteListener.valueChanged(aAxis, aValue);
			}
		}		
	}
	
	public static void fireStateChanged (Button aButton, boolean aPressed)
	{
		fireStateChanged(aButton.getListenersIterator(), aButton, aPressed);
		fireStateChanged(aButton.getAbstractDevice ().getListenersIterator(), aButton, aPressed);
	}
	
	private static void fireStateChanged (Iterator aListenersIterator, Button aButton, boolean aPressed)
	{
		while (aListenersIterator.hasNext())
		{
			Object lListener = (Object) aListenersIterator.next();
			if (lListener instanceof IButtonListener)
			{
				IButtonListener lButtonListener = (IButtonListener) lListener;
				lButtonListener.stateChanged(aButton, aPressed);
			}
		}		
	}
	
}
