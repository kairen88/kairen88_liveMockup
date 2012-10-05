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
 * Represents a numeric input from a {@link net.jhid.core.devices.IDevice}.
 * There are two types of axes: relative ({@link net.jhid.core.devices.IRelativeAxis})
 * and absolute ({@link net.jhid.core.devices.IAbsoluteAxis}). 
 * <p>
 * You cannot add a listener to an abstract axis, you have to use one of the
 * <code>addListener<code> methods in the subinterfaces: relative and absolute events 
 * are different.
 * @author gpothier
 */
public interface IAxis
{
	/**
	 * @return The device this axis belongs to.
	 */
	public IDevice getDevice ();
	
	/**
	 * @return The identifier of this axis.
	 */
	public AxisId getId ();
	
	
	/**
	 * Returns the current position of this axis.
	 */
	public int getValue ();
}
