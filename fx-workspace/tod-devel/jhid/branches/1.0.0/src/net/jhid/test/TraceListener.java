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

package net.jhid.test;

import net.jhid.core.devices.*;
import net.jhid.core.listeners.IDeviceListener;


/**
 * A listsner that prints every message it receives.
 * @author gpothier
 */
public class TraceListener implements IDeviceListener
{
	public void valueChanged (IRelativeAxis aAxis, int aDelta, int aValue)
	{
		String lDeviceName = aAxis.getDevice().getDeviceInfo().getDeviceName();
		String lAxisName = aAxis.getId().getName();
		System.out.println("Device: \""+lDeviceName+"\" "+lAxisName+": "+aValue);
	}

	public void valueChanged (IAbsoluteAxis aAxis, int aValue)
	{
		String lDeviceName = aAxis.getDevice().getDeviceInfo().getDeviceName();
		String lAxisName = aAxis.getId().getName();
		System.out.println("Device: \""+lDeviceName+"\" "+lAxisName+": "+aValue);
	}

	public void stateChanged (IButton aButton, boolean aPressed)
	{
		String lDeviceName = aButton.getDevice().getDeviceInfo().getDeviceName();
		String lButtonName = aButton.getId().getName();
		System.out.println("Device: \""+lDeviceName+"\" "+lButtonName+": "+aPressed);
	}

}
