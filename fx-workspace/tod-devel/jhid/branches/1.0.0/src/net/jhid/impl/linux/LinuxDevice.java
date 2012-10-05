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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import net.jhid.core.devices.*;
import net.jhid.impl.generic.*;

/**
 * Linux implementation of {@link net.jhid.core.devices.IDevice}
 * @author gpothier
 */
public class LinuxDevice extends AbstractDevice implements Runnable
{
	private LinuxDeviceInfo fDeviceInfo;

	private IButton[] fButtonMap = new IButton[LinuxConstants.KEY_MAX];
	private AbsoluteAxis[] fAbsoluteAxisMap = new AbsoluteAxis[LinuxConstants.ABS_MAX];
	private RelativeAxis[] fRelativeAxisMap = new RelativeAxis[LinuxConstants.REL_MAX];
	
	private FileChannel fChannel;
	private ByteBuffer fBuffer;

	public LinuxDevice (LinuxDeviceInfo aDeviceInfo)
	{
		fDeviceInfo = aDeviceInfo;

		setupMappings();
		setupIO();
		
		List lButtons = new ArrayList ();
		for (int i = 0; i < fButtonMap.length; i++)
		{
			IButton lButton = fButtonMap[i];
			if (lButton != null) lButtons.add (lButton);
		}
		setButtons((IButton[]) lButtons.toArray(new IButton[0]));
		
		List lAxes = new ArrayList ();
		for (int i = 0; i < fAbsoluteAxisMap.length; i++)
		{
			IAxis lAxis = fAbsoluteAxisMap[i];
			if (lAxis != null) lAxes.add (lAxis);
		}
		for (int i = 0; i < fRelativeAxisMap.length; i++)
		{
			IAxis lAxis = fRelativeAxisMap[i];
			if (lAxis != null) lAxes.add (lAxis);
		}
		setAxes((IAxis[]) lAxes.toArray(new IAxis[0]));
	}
	
	public IDeviceInfo getDeviceInfo ()
	{
		return fDeviceInfo;
	}
	
	/**
	 * Sets up the evdev interface.
	 */
	private void setupIO () 
	{
		String lFileName = fDeviceInfo.getFileName();
		
		try
		{
			FileInputStream lStream = new FileInputStream (lFileName);
			fChannel = lStream.getChannel();
			fBuffer = ByteBuffer.allocate(16);
			fBuffer.order(ByteOrder.LITTLE_ENDIAN);
		} 
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		new Thread (this).start ();
	}

	/**
	 * Reads the linux evdev virtual file and calls {@link #processPacket(int, int, short, short, int)}
	 * for each received packet.
	 */
	public void run ()
	{
		while (true)
		{
			try
			{
				fBuffer.rewind();
				fChannel.read(fBuffer);
				fBuffer.rewind();
				int lSeconds = fBuffer.getInt();
				int lMS = fBuffer.getInt();
				short lType = fBuffer.getShort();
				short lCode = fBuffer.getShort();
				int lValue = fBuffer.getInt();
				
				processPacket(lSeconds, lMS, lType, lCode, lValue);
			} 
			catch (Exception e)
			{
				e.printStackTrace();
			}

		}
	}

	/**
	 * This method is called whenever an evdev packet is received.
	 */
	private void processPacket (int aSeconds, int aMicroSeconds, short aType, short aCode, int aValue)
	{
		switch (aType)
		{
			case LinuxConstants.EV_ABS:
				AbsoluteAxis lAbsoluteAxis = fAbsoluteAxisMap[aCode];
				if (lAbsoluteAxis == null) System.out.println("Absolute code not handled: "+aCode);
				else lAbsoluteAxis.setValue(aValue);
				break;
			
			case LinuxConstants.EV_REL:
				RelativeAxis lRelativeAxis = fRelativeAxisMap[aCode];
				if (lRelativeAxis == null) System.out.println("Relative code not handled: "+aCode);
				else lRelativeAxis.move(aValue);
				break;
			
			case LinuxConstants.EV_KEY:
				Button lButton = (Button) fButtonMap[aCode];
				if (lButton == null) System.out.println("Key code not handled: "+aCode);
				else lButton.setPressed(aValue != 0);
				break;
		}
	}
	
	
	private void setupMappings ()
	{
		mapAbsoluteAxis(LinuxConstants.ABS_X, AxisId.ABS_X);
		mapAbsoluteAxis(LinuxConstants.ABS_Y, AxisId.ABS_Y);
		mapAbsoluteAxis(LinuxConstants.ABS_Z, AxisId.ABS_Z);
		mapAbsoluteAxis(LinuxConstants.ABS_RX, AxisId.ABS_RX);
		mapAbsoluteAxis(LinuxConstants.ABS_RY, AxisId.ABS_RY);
		mapAbsoluteAxis(LinuxConstants.ABS_RZ, AxisId.ABS_RZ);
		mapAbsoluteAxis(LinuxConstants.ABS_THROTTLE, AxisId.ABS_THROTTLE);
		mapAbsoluteAxis(LinuxConstants.ABS_RUDDER, AxisId.ABS_RUDDER);
		mapAbsoluteAxis(LinuxConstants.ABS_WHEEL, AxisId.ABS_WHEEL);
		mapAbsoluteAxis(LinuxConstants.ABS_GAS, AxisId.ABS_GAS);
		mapAbsoluteAxis(LinuxConstants.ABS_BRAKE, AxisId.ABS_BRAKE);
		mapAbsoluteAxis(LinuxConstants.ABS_HAT0X, AxisId.ABS_HAT0X);
		mapAbsoluteAxis(LinuxConstants.ABS_HAT0Y, AxisId.ABS_HAT0Y);
		mapAbsoluteAxis(LinuxConstants.ABS_HAT1X, AxisId.ABS_HAT1X);
		mapAbsoluteAxis(LinuxConstants.ABS_HAT1Y, AxisId.ABS_HAT1Y);
		mapAbsoluteAxis(LinuxConstants.ABS_HAT2X, AxisId.ABS_HAT2X);
		mapAbsoluteAxis(LinuxConstants.ABS_HAT2Y, AxisId.ABS_HAT2Y);
		mapAbsoluteAxis(LinuxConstants.ABS_HAT3X, AxisId.ABS_HAT3X);
		mapAbsoluteAxis(LinuxConstants.ABS_HAT3Y, AxisId.ABS_HAT3Y);
		mapAbsoluteAxis(LinuxConstants.ABS_PRESSURE, AxisId.ABS_PRESSURE);
		mapAbsoluteAxis(LinuxConstants.ABS_DISTANCE, AxisId.ABS_DISTANCE);
		mapAbsoluteAxis(LinuxConstants.ABS_TILT_X, AxisId.ABS_TILT_X);
		mapAbsoluteAxis(LinuxConstants.ABS_TILT_Y, AxisId.ABS_TILT_Y);
		mapAbsoluteAxis(LinuxConstants.ABS_TOOL_WIDTH, AxisId.ABS_TOOL_WIDTH);
		mapAbsoluteAxis(LinuxConstants.ABS_VOLUME, AxisId.ABS_VOLUME);
		
		mapRelativeAxis(LinuxConstants.REL_X, AxisId.REL_X);
		mapRelativeAxis(LinuxConstants.REL_Y, AxisId.REL_Y);
		mapRelativeAxis(LinuxConstants.REL_Z, AxisId.REL_Z);
		mapRelativeAxis(LinuxConstants.REL_HWHEEL, AxisId.REL_HWHEEL);
		mapRelativeAxis(LinuxConstants.REL_WHEEL, AxisId.REL_VWHEEL);

		
		mapButton(LinuxConstants.BTN_LEFT, ButtonId.MOUSE_LEFT);
		mapButton(LinuxConstants.BTN_RIGHT, ButtonId.MOUSE_RIGHT);
		mapButton(LinuxConstants.BTN_MIDDLE, ButtonId.MOUSE_MIDDLE);
	}

	private void mapAbsoluteAxis (int aCode, AxisId aAxisId)
	{
		if (fDeviceInfo.hasEventCode(LinuxConstants.EV_ABS, aCode))
		{
			fAbsoluteAxisMap[aCode] = new AbsoluteAxis (this, aAxisId);
		}
	}

	private void mapRelativeAxis (int aCode, AxisId aAxisId)
	{
		if (fDeviceInfo.hasEventCode(LinuxConstants.EV_REL, aCode))
		{
			fRelativeAxisMap[aCode] = new RelativeAxis (this, aAxisId);
		}
	}

	private void mapButton (int aCode, ButtonId aButtonId)
	{
		if (fDeviceInfo.hasEventCode(LinuxConstants.EV_KEY, aCode))
		{
			fButtonMap[aCode] = new Button (this, aButtonId);
		}
	}
}
