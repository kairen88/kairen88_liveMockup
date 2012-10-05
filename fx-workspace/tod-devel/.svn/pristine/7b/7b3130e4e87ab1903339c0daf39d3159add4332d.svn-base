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

package net.jhid.core;

import java.util.*;

import net.jhid.impl.spi.IDeviceProvider;
import sun.misc.Service;

/**
 * A singleton that permits to retrieve all available devices in the system.
 * Usage:
 * <code>
 * Enumerator.getInstance().getDevices()
 * </code>
 * <p>
 * Implementation note: the enumerator uses the SPI to retrieve all
 * contributed device providers, which in turn provide devices.
 * See {@link net.jhid.impl.spi.IDeviceProvider}.
 * @author gpothier
 */
public class Enumerator
{
	private static Enumerator INSTANCE = new Enumerator ();
	
	private List fDevices = new ArrayList ();

	/**
	 * Returns the singleton instance of the enumerator, or null
	 * if jhid is not available on this system.
	 */
	public static Enumerator getInstance ()
	{
		return INSTANCE;
	}

	protected Enumerator ()
	{
		Iterator lIterator = Service.providers(IDeviceProvider.class);
		while (lIterator.hasNext())
		{
			IDeviceProvider lProvider = (IDeviceProvider) lIterator.next();
			fDevices.addAll(lProvider.getDevices());
		}
	}
	
	/**
	 * Returns all available {@link net.jhid.core.devices.IDevice devices}.
	 */
	public List getDevices ()
	{
		return fDevices;
	}
}
