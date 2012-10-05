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

/**
 * Receives the result of an ioctl EVIOCGID.
 * @author gpothier
 */
public class InputId
{
	private short fBusType;
	private short fVendor;
	private short fProduct;
	private short fVersion;
	
	
	public short getBusType ()
	{
		return fBusType;
	}
	
	public void setBusType (short pBusType)
	{
		fBusType = pBusType;
	}
	
	public short getProduct ()
	{
		return fProduct;
	}
	
	public void setProduct (short pProduct)
	{
		fProduct = pProduct;
	}
	
	public short getVendor ()
	{
		return fVendor;
	}
	
	public void setVendor (short pVendor)
	{
		fVendor = pVendor;
	}
	
	public short getVersion ()
	{
		return fVersion;
	}
	
	public void setVersion (short pVersion)
	{
		fVersion = pVersion;
	}
	
	
	public String toString ()
	{
		return "input [bus:"+getBusType()+" product: "+getProduct()+" vendor: "+getVendor()+" version: "+getVersion()+"]";
	}
}
