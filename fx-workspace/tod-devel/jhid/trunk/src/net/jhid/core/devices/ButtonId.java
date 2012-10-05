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
 * An enumeration of button identifiers.
 * 
 * @author gpothier
 */
public class ButtonId
{
	public static ButtonId MOUSE_LEFT = new ButtonId("button.left");
	public static ButtonId MOUSE_RIGHT = new ButtonId("button.right");
	public static ButtonId MOUSE_MIDDLE = new ButtonId("button.middle");
	
	private String itsName;

	private ButtonId (String aName)
	{
		itsName = aName;
	}

	/**
	 * Returns the name of the button
	 */
	public String getName ()
	{
		return itsName;
	}
}
