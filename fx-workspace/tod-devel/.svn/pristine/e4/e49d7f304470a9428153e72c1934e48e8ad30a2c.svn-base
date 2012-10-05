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

import net.jhid.core.devices.IDeviceInfo;

/**
 * Permits to retrieve
 * 
 * @author gpothier
 */
public class LinuxDeviceInfo implements IDeviceInfo
{
	private int fFD;
	private String fFileName;
	
	private InputId fInputId;
	private String fName;
	
	private byte[] fSupportedEventTypes;
	private byte[][] fSupportedEventCodes = new byte[LinuxConstants.EV_MAX][];
	
	public LinuxDeviceInfo (String aFileName, int aFD)
	{
		fFileName = aFileName;
		fFD = aFD;
	}
	

	public int getFD ()
	{
		return fFD;
	}
	
	public String getFileName ()
	{
		return fFileName;
	}

	public InputId getInputId ()
	{
		if (fInputId == null) fInputId = LinuxNative.getInputId(fFD);
		return fInputId;
	}
	
	public byte[] getSupportedEventTypes ()
	{
		if (fSupportedEventTypes == null) fSupportedEventTypes = LinuxNative.getEventTypes(fFD);
		return fSupportedEventTypes;
	}
	
	public byte[] getSupportedEventCodes (int aEventType)
	{
		if (fSupportedEventCodes[aEventType] == null) 
			fSupportedEventCodes[aEventType] = LinuxNative.getEventCodes(fFD, aEventType);
		return fSupportedEventCodes[aEventType];
	}

	/**
	 * Determines if the device can report events of the specified type 
	 * @param aEventType One of the EV_* constants in {@link LinuxConstants}
	 */
	public boolean hasEventType (int aEventType)
	{
		return LinuxNative.testBit(getSupportedEventTypes (), LinuxConstants.EV_REL);
	}
	
	/**
	 * Determines if the device can report events of the specified type and code
	 * @param aEventType One of the EV_* constants in {@link LinuxConstants}
	 * @param aEventType One of the other constants in {@link LinuxConstants}, according 
	 * to the specified event type.
	 */
	public boolean hasEventCode (int aEventType, int aEventCode)
	{
		return LinuxNative.testBit(getSupportedEventCodes(aEventType), aEventCode);

	}

	public String getDeviceName ()
	{
		if (fName == null) fName = LinuxNative.getName(fFD);
		return fName;
	}
	
	public int getDeviceVendorId ()
	{
		return fInputId.getVendor();
	}


	public int getDeviceVersion ()
	{
		return fInputId.getVersion();
	}
}