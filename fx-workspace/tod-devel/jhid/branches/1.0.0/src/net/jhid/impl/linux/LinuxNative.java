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

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Handles native calls.
 * @author gpothier
 */
public class LinuxNative
{
	private static final ByteBuffer BUFFER = ByteBuffer.allocate (256);


	/**
	 * Returns the file descriptor for the specified file name, or -1
	 * if not available.
	 */
	public static native int getFD (String aFileName);
	
	/**
	 * Retrieves the name of a device.
	 */
	private static native int ioctl_EVIOCGNAME (int aFD, byte[] aBuffer);

	/**
	 * Retrieves info of a device
	 */
	private static native int ioctl_EVIOCGID (int aFD, byte[] aBuffer);
	
	private static native int ioctl_EVIOCGBIT (int aFD, int aType, byte[] aBuffer);
	
	static 
	{
        System.loadLibrary("linuxNative");
        BUFFER.order (ByteOrder.LITTLE_ENDIAN);
    }
    
	public static String getName (int aFD)
	{
		byte[] theBytes = BUFFER.array();
		int theResult = ioctl_EVIOCGNAME(aFD, theBytes);
		if (theResult < 0) throw new RuntimeException ("Error in getName ("+theResult+")");
		
		int theLength = 0;
		for (int i = 0; i < theBytes.length; i++)
		{
			if (theBytes[i] == 0)
			{
				theLength = i;
				break;
			}
		}
		return new String (theBytes, 0, theLength);
	}


	public static InputId getInputId (int aFD)
	{
		InputId lInputId = new InputId ();
		int theResult = ioctl_EVIOCGID(aFD, BUFFER.array());
		if (theResult < 0) throw new RuntimeException ("Error in getInputId ("+theResult+")");
		
		lInputId.setBusType(BUFFER.getShort());
		lInputId.setProduct(BUFFER.getShort());
		lInputId.setVendor(BUFFER.getShort());
		lInputId.setVersion(BUFFER.getShort());
		return lInputId;
	}	
	
	/**
	 * @return A bit array, each bit corresponds to an EV_XXX constant.
	 */
	public static byte[] getEventTypes (int aFD)
	{
		return getEventCodes(aFD, 0);
	}
	
	public static byte[] getEventCodes (int aFD, int aEventType)
	{
		int lResult = ioctl_EVIOCGBIT(aFD, aEventType, BUFFER.array());
		if (lResult < 0) throw new RuntimeException ("Error in getEventCodes ("+lResult+")");
		
		byte[] lBits = new byte[lResult];
		BUFFER.rewind();
		BUFFER.get(lBits, 0, lResult);
		
		return lBits;
	}
	
	/**
	 * Tests if the bit at the given index is 1 or 0 in the bit array.
	 */
	public static boolean testBit (byte[] aBits, int aBit)
	{
		byte lByte = aBits[aBit / 8];
		return (lByte & (1<<(aBit % 8))) != 0;
	}
}
