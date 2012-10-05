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

import java.util.*;

import net.jhid.core.devices.*;
import net.jhid.core.listeners.IDeviceListener;

/**
 * Helper base implementation for devices.
 * <li>Handles listeners.
 * <li>Handles axes and buttons.
 * @author gpothier
 */
public abstract class AbstractDevice implements IDevice
{
	private List fListeners = new ArrayList ();
	private IAxis[] fAxes;
	private IButton[] fButtons;
	

	public String toString ()
	{
		IDeviceInfo theInfo = getDeviceInfo();
		return "Device: "+theInfo.getDeviceName()
				+ " (vendor: "+theInfo.getDeviceVendorId()
				+", product: "+theInfo.getDeviceProductId()
				+", version: "+theInfo.getDeviceVersion()+")";
	}
	
	public void addListener (IDeviceListener aListener)
	{
		fListeners.add (aListener);
	}

	public void removeListener (IDeviceListener aListener)
	{
		fListeners.remove (aListener);
	}
	 
	public Iterator getListenersIterator ()
	{
		return fListeners.iterator();
	}
	
	public IAxis[] getAxes ()
	{
		return fAxes;
	}

	public IButton[] getButtons ()
	{
		return fButtons;
	}

	public IAxis getAxis (AxisId aId)
	{
		for (int i = 0; i < fAxes.length; i++)
		{
			IAxis lAxis = fAxes[i];
			if (lAxis.getId() == aId) return lAxis;
		}
		return null;
	}
	
	public IButton getButton (ButtonId aId)
	{
		for (int i = 0; i < fButtons.length; i++)
		{
			IButton lButton = fButtons[i];
			if (lButton.getId() == aId) return lButton;
		}
		return null;
	}
	
	public void setAxes (IAxis[] aAxes)
	{
		fAxes = aAxes;
	}
	
	public void setButtons (IButton[] aButtons)
	{
		fButtons = aButtons;
	}
}
