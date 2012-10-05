/*
 * Created on Mar 6, 2004
 */
package net.basekit.shapes.utils;

import javax.vecmath.Tuple3f;


public class XAccessor implements CoordinateAccessor
{
	private static final XAccessor INSTANCE = new XAccessor();

	public static XAccessor getInstance ()
	{
		return INSTANCE;
	}

	private XAccessor() 
	{
	}

	public float get (Tuple3f aTuple)
	{
		return aTuple.x;
	}

	public void set (Tuple3f aTuple, float aValue)
	{
		aTuple.x = aValue;
	}
}