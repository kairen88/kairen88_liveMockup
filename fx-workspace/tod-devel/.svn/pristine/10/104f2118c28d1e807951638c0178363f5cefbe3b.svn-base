/*
 * Created on Mar 6, 2004
 */
package net.basekit.shapes.utils;

import javax.vecmath.Tuple3f;


public class ZAccessor implements CoordinateAccessor
{
	private static final ZAccessor INSTANCE = new ZAccessor();

	public static ZAccessor getInstance ()
	{
		return INSTANCE;
	}

	private ZAccessor() 
	{
	}

	public float get (Tuple3f aTuple)
	{
		return aTuple.z;
	}

	public void set (Tuple3f aTuple, float aValue)
	{
		aTuple.z = aValue;
	}
}