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

package net.jhid.impl.generic;

import java.util.*;

import net.jhid.core.devices.IDevice;

/**
 * Provides common behaviour for axes and buttons
 * @author gpothier
 */
public abstract class AbstractSlot
{
	private AbstractDevice fDevice;
	private List fListeners = new ArrayList ();
	
	public AbstractSlot (AbstractDevice aDevice)
	{
		fDevice = aDevice;
	}

	public IDevice getDevice ()
	{
		return getAbstractDevice();
	}
	
	public AbstractDevice getAbstractDevice ()
	{
		return fDevice;
	}

	protected void addListener (Object aListener)
	{
		fListeners.add (aListener);
	}

	protected void removeListener (Object aListener)
	{
		fListeners.remove (aListener);
	}
	
	protected Iterator getListenersIterator ()
	{
		return fListeners.iterator();
	}

}