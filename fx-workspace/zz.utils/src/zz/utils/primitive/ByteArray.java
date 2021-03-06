/*
 * Created on Nov 19, 2007
 */
package zz.utils.primitive;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A primitive byte array that grows as needed.
 * @author gpothier
 */
public class ByteArray implements Serializable
{
	private static final long serialVersionUID = -1018286467494851056L;
	private byte[] itsData;
	private int itsSize;

	public ByteArray()
	{
		this(16);
	}
	
	public ByteArray(int aInitialSize)
	{
		itsData = new byte[aInitialSize];
	}
	
	public byte get(int aIndex)
	{
		return aIndex < itsSize ? itsData[aIndex] : 0;
	}
	
	public int size()
	{
		return itsSize;
	}
	
	public boolean isEmpty()
	{
		return itsSize == 0;
	}
	
	protected void setSize(int aSize)
	{
		itsSize = aSize;
	}
	
	public void set(int aIndex, byte aValue)
	{
		ensureSize(aIndex+1);
		itsData[aIndex] = aValue;
		itsSize = Math.max(itsSize, aIndex+1);
	}
	
	public void add(byte aValue)
	{
		set(size(), aValue);
	}
	
	public void clear()
	{
		itsSize = 0;
	}
	
	private void ensureSize(int aSize)
	{
		if (itsData.length >= aSize) return;
		
		int theNewSize = Math.max(aSize, itsData.length*2);
		byte[] theNewData = new byte[theNewSize];
		System.arraycopy(itsData, 0, theNewData, 0, itsData.length);
		itsData = theNewData;
	}

	public byte[] toArray()
	{
		byte[] theResult = new byte[size()];
		System.arraycopy(itsData, 0, theResult, 0, size());
		return theResult;
	}
	
	/**
	 * Transforms a collection of {@link Byte}s to an array of native bytes.
	 */
	public static byte[] toByteArray(Collection<Byte> aCollection)
	{
		byte[] theResult = new byte[aCollection.size()];
		int i=0;
		for(Byte v : aCollection) theResult[i++] = v;
		return theResult;
	}
	
	public static List<Byte> toList(byte[] aArray)
	{
		if (aArray == null) return null;
		List<Byte> theList = new ArrayList<Byte>();
		for(byte i : aArray) theList.add(i);
		return theList;
	}
}
