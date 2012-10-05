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

package net.jhid.icon;

import net.jhid.core.devices.IAxis;
import net.jhid.core.devices.IButton;
import net.jhid.core.devices.IDevice;
import fr.emn.reactiveinput.AbstractDevice;
import fr.emn.reactiveinput.Device;
import fr.emn.reactiveinput.DeviceUtilities;
import fr.emn.reactiveinput.Out;
import fr.emn.reactiveinput.SlotType;

/**
 * Wraps an {@link net.jhid.core.devices.IDevice} in the ICon framework.
 * @author gpothier
 */
public class JHIDDevice extends AbstractDevice
{
	private IDevice fDevice;
	
	private Out[] fAxes;
	private Out[] fButtons;
	
	public JHIDDevice (IDevice aDevice)
	{
		super (aDevice.getDeviceInfo ().getDeviceName());
		fDevice = aDevice;
		configure();
	}
	
	/**
	 * Adds slots to this device according to the capabilities
	 * of the underlying jhid device.
	 */
	private void configure ()
	{
		fAxes = new Out[fDevice.getAxes().length];
		fButtons = new Out[fDevice.getButtons().length];
	
		for (int i = 0; i < fDevice.getAxes().length; i++)
		{
			IAxis lAxis = fDevice.getAxes()[i];
			String theName = lAxis.getId().getName();
			fAxes[i] = addOut(theName, SlotType.INT);
		}
		
		for (int i = 0; i < fDevice.getButtons().length; i++)
		{
			IButton lButton = fDevice.getButtons()[i];
			String theName = lButton.getId().getName();
			fButtons[i] = addOut(theName, SlotType.BOOLEAN);
		}
	}
	
	public boolean hasExternalInput() 
	{
		return true;
	}
	
	public Device copy() 
	{
		  JHIDDevice lDevice = new JHIDDevice (fDevice);
		  DeviceUtilities.setPrototype(lDevice, DeviceUtilities.getPrototype(this));
		  return lDevice;
		}

	
	public void update ()
	{
		for (int i = 0; i < fAxes.length; i++)
		{
			IAxis lAxis = fDevice.getAxes()[i];
			fAxes[i].setIntValue(lAxis.getValue(), false);
		}
		
		for (int i = 0; i < fButtons.length; i++)
		{
			IButton lButton = fDevice.getButtons()[i];
			fButtons[i].setBooleanValue(lButton.isPressed(), false);
		}
	}
}
