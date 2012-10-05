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

package net.jhid.core.listeners;

import net.jhid.core.devices.IButton;

/**
 * Listends for a button's state change.
 * @author gpothier
 */
public interface IButtonListener 
{
	/**
	 * Called when the button changes state.
	 * @param aButton The button that changed state.
	 * @param aPressed The new state of the button.
	 */
	public void stateChanged (IButton aButton, boolean aPressed);
}
