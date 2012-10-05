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

package net.jhid.impl.linux;

import java.util.ArrayList;
import java.util.List;

import net.jhid.impl.spi.IDeviceProvider;

/**
 * Provides devices obtained from the linux evdev mechanism
 * @author gpothier
 */
public class EVDEVProvider implements IDeviceProvider
{
	private List fDevices = new ArrayList ();
	
	public EVDEVProvider () 
	{
		String lOS = System.getProperty("os.name");
		if ("linux".equalsIgnoreCase(lOS))
		{
			searchDevices("event");
			searchDevices("evdev");
		}
		else System.err.println("jhid: OS \""+lOS+"\"not supported - jhid will not be available");
	}
	
	private void searchDevices (String aPrefixDeviceName)
	{
		int i = 0;
		while (true)
		{
			LinuxDeviceInfo lDeviceInfo = getDeviceInfo(aPrefixDeviceName+i);
			if (lDeviceInfo == null) break;
			fDevices.add (new LinuxDevice (lDeviceInfo));
			i++;
		}
	}
	
	private LinuxDeviceInfo getDeviceInfo (String aDeviceName)
	{
		String lFileName = "/dev/input/"+aDeviceName;
		int lFD = LinuxNative.getFD(lFileName);
		if (lFD == -1) return null;
		else return new LinuxDeviceInfo (lFileName, lFD);
	}
	
	
	public List getDevices ()
	{
		return fDevices;
	}
}
