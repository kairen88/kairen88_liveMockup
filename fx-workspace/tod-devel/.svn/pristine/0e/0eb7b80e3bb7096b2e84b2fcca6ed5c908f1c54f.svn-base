/*
 * Created on Mar 6, 2004
 */
package net.basekit.shapes.utils;

import javax.vecmath.Tuple3f;


public class YAccessor implements CoordinateAccessor
{
	private static final YAccessor INSTANCE = new YAccessor();

	public static YAccessor getInstance ()
	{
		return INSTANCE;
	}

	private YAccessor() 
	{
	}

	public float get (Tuple3f aTuple)
	{
		return aTuple.y;
	}

	public void set (Tuple3f aTuple, float aValue)
	{
		aTuple.y = aValue;
	}
}