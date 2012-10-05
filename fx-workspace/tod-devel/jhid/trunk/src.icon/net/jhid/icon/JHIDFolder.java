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

import java.util.Iterator;
import java.util.List;

import net.jhid.core.Enumerator;
import net.jhid.core.devices.IDevice;
import fr.emn.reactiveinput.Device;
import fr.emn.reactiveinput.SystemFolder;

/**
 * This folder contains jhid devices.
 * The application must add it to ICon.
 * @author gpothier
 */
public class JHIDFolder extends SystemFolder
{
	public JHIDFolder ()
	{
		super("jhid");
		if (isAvailable()) addDevices();
	}

	public static boolean isAvailable ()
	{
		return Enumerator.getInstance() != null;
	}
	
	private void addDevices ()
	{
		List lDevices = Enumerator.getInstance().getDevices();
		for (Iterator lIterator = lDevices.iterator(); lIterator.hasNext();)
		{
			IDevice	lDevice = (IDevice) lIterator.next();
			Device lJHIDDevice = new JHIDDevice (lDevice);
			add (lJHIDDevice);
		}
	}
}
