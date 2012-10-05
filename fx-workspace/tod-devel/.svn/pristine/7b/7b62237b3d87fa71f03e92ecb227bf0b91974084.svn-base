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

package net.jhid.core.devices;

/**
 * Enumeration class providing all possible axis ids.
 * Axes prefixed by <code>ABS_</code> are absolute axes 
 * (see {@link net.jhid.core.devices.IAbsoluteAxis});
 * Thoses prefixed by <code>REL_</code> are relative axes
 * (see {@link net.jhid.core.devices.IRelativeAxis}.
 * 
 * @author gpothier
 */
public class AxisId
{
	public static final AxisId ABS_X = new AxisId("position.x");
	public static final AxisId ABS_Y = new AxisId("position.y");
	public static final AxisId ABS_Z = new AxisId("position.z");
	
	public static final AxisId ABS_RX = new AxisId("rotation.x");
	public static final AxisId ABS_RY = new AxisId("rotation.y");
	public static final AxisId ABS_RZ = new AxisId("rotation.z");
	
	public static final AxisId ABS_THROTTLE = new AxisId ("throttle");
	public static final AxisId ABS_RUDDER = new AxisId ("rudder");
	public static final AxisId ABS_WHEEL = new AxisId ("absolute wheel");
	public static final AxisId ABS_GAS = new AxisId ("gas");
	public static final AxisId ABS_BRAKE = new AxisId ("brake");
	public static final AxisId ABS_HAT0X = new AxisId ("hat0.x");
	public static final AxisId ABS_HAT0Y = new AxisId ("hat0.y");
	public static final AxisId ABS_HAT1X = new AxisId ("hat1.x");
	public static final AxisId ABS_HAT1Y = new AxisId ("hat1.y");
	public static final AxisId ABS_HAT2X = new AxisId ("hat2.x");
	public static final AxisId ABS_HAT2Y = new AxisId ("hat2.y");
	public static final AxisId ABS_HAT3X = new AxisId ("hat3.x");
	public static final AxisId ABS_HAT3Y = new AxisId ("hat3.y");
	public static final AxisId ABS_PRESSURE = new AxisId ("pressure");
	public static final AxisId ABS_DISTANCE = new AxisId ("distance");
	public static final AxisId ABS_TILT_X = new AxisId ("tilt.x");
	public static final AxisId ABS_TILT_Y = new AxisId ("tilt.y");
	public static final AxisId ABS_TOOL_WIDTH = new AxisId ("tool width");
	public static final AxisId ABS_VOLUME = new AxisId ("volume");

	
	public static final AxisId REL_X = new AxisId("position.x");
	public static final AxisId REL_Y = new AxisId("position.y");
	public static final AxisId REL_Z = new AxisId("position.z");
	
	public static final AxisId REL_HWHEEL = new AxisId("wheel.horz");
	public static final AxisId REL_VWHEEL = new AxisId("wheel.vert");

	private String itsName;

	private AxisId (String aName)
	{
		itsName = aName;
	}

	/**
	 * @return The name of this axis
	 */
	public String getName ()
	{
		return itsName;
	}
	
}
