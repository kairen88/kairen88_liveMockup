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

import net.jhid.core.listeners.*;

/**
 * Represents a physical device. A device comprises a number of axes and buttons,
 * which can be retrieved by the {@link #getAxes()} and {@link #getButtons()} methods.
 * Axes and buttons are identified by constants in the {@link net.jhid.core.devices.AxisId}
 * and {@link net.jhid.core.devices.ButtonId} classes. It is also possible to obtain
 * a particular axis or button of a device with the {@link #getAxis(AxisId)}
 * and {@link #getButton(ButtonId)} methods; these methods simply return <code>null</code>
 * if the requested axis or button is not available for this device.
 * <p>
 * Each particular axis or button  can be listened for events; it is also possible to listen
 * to the whole device. In the latter case, the listener will receive the events of all axes
 * and buttons of the device. See the following methods:
 * <li>{@link #addListener(IDeviceListener)}
 * <li>{@link #addListener(IAbsoluteAxisListener)}
 * <li>{@link IRelativeAxis#addListener(IRelativeAxisListener)}
 * <li>{@link IButton#addListener(IButtonListener)}
 * @author gpothier
 */
public interface IDevice
{
	/**
	 * @return An object that can provide information about this device, 
	 * such as its name, vendor id, etc.
	 */
	public IDeviceInfo getDeviceInfo ();
	
	/**
	 * Adds a listener that will be notified of all the events occuring on
	 * the buttons and axes of this device.
	 */
	public void addListener (IDeviceListener aListener);
	
	/**
	 * Removes a previously added listener.
	 */
	public void removeListener (IDeviceListener aListener);
	
	/**
	 * @return All the axes of this device.
	 */
	public IAxis[] getAxes ();
	
	/**
	 * @return All the buttons of this device.
	 */
	public IButton[] getButtons ();
	
	/**
	 * @return The axis with the specified identifier, or null if absent.
	 */
	public IAxis getAxis (AxisId aId);

	/**
	 * @return The button with the specified identifier, or null if absent.
	 */
	public IButton getButton (ButtonId aId);
}
