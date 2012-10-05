/*
 * Created on Mar 18, 2004
 */
package net.basekit.shapes.utils;

import javax.vecmath.Point3f;
import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3f;

import com.xith3d.scenegraph.QuadArray;

/**
 * Utilities to deal with shapes and geometry.
 * @author gpothier
 */
public class ShapeUtils
{
	/**
	 * Sets the coordinates of the four vertices starting at the specified index so that
	 * they form an axis aligned rectangle in the x-y plane
	 */
	public static void renderRectangle (QuadArray aQuadArray, int aVertexIndex, float aX, float aY, float aW, float aH, float aZ)
	{
		Point3f theBuffer = new Point3f ();
		theBuffer.x = aX;
		theBuffer.y = aY;
		aQuadArray.setCoordinate(aVertexIndex, theBuffer);
		
		theBuffer.y = aY + aH; 
		aQuadArray.setCoordinate(aVertexIndex+1, theBuffer);
		
		theBuffer.x = aX + aW;
		aQuadArray.setCoordinate(aVertexIndex+2, theBuffer);
		
		theBuffer.y = aY;
		aQuadArray.setCoordinate(aVertexIndex+3, theBuffer);

	}

	/**
	 * Utility method to convert a generic tuple into a vector.
	 * If the given tuple is already a vector, it is immediately returned.
	 * Otherwise, a new vector object is returned
	 */
	public static Vector3f toVector (Tuple3f aTuple)
	{
		assert aTuple != null;
		if (aTuple instanceof Vector3f) return (Vector3f) aTuple;
		else
		{
			return new Vector3f (aTuple);
		}
	}
}
