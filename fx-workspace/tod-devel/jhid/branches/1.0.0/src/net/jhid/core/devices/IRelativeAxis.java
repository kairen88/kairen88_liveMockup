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

import net.jhid.core.listeners.IRelativeAxisListener;

/**
 * An axis for which the device provides a differential value in each message.
 * The axis will maintain a value that is computed by summing all received delta values.
 * @author gpothier
 */
public interface IRelativeAxis extends IAxis
{
	/**
	 * Sets the current value of this axis.
	 */
	public void setValue (int aValue);
	
	public void addListener (IRelativeAxisListener aListener);
	public void removeListener (IRelativeAxisListener aListener);

}
