/*
 * Created on Mar 6, 2004
 */
package net.basekit.shapes.utils;

import javax.vecmath.Point3f;
import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3f;

import net.basekit.shapes.Shape;


/**
 * Represents a face of the cube; permits to compute the intersection between
 * this face and a ray.
 * It uses accessors to abstract concrete x, y, z coordinates
 * to i, j, p coordinates that are proper to this face.
 * A face is always orthogonal to one axis. The p coordinate is the coordinate along this axis.
 *  
 * @author gpothier
 */
public abstract class Face
{
	/**
	 * Index of this face within
	 */
	private int itsIndex;
	
	/**
	 * Orthogonal coordinate (Z for an XYFace)
	 */
	private float itsP;

	private CoordinateAccessor itsIAccessor;
	private CoordinateAccessor itsJAccessor;
	private CoordinateAccessor itsPAccessor;
	
	private Point3f itsIntersection = new Point3f ();

	private Shape itsShape;
	
	public Face (Shape aShape, int aIndex, CoordinateAccessor aIAccessor, CoordinateAccessor aJAccessor, CoordinateAccessor aPAccessor) 
	{
		itsShape = aShape;
		itsIndex = aIndex;
		itsIAccessor = aIAccessor;
		itsJAccessor = aJAccessor;
		itsPAccessor = aPAccessor;
	}
	
	public final Shape getShape ()
	{
		return itsShape;
	}

	private float getI (Tuple3f aTuple)
	{
		return itsIAccessor.get(aTuple);
	}
	
	private float getJ (Tuple3f aTuple)
	{
		return itsJAccessor.get(aTuple);
	}
	
	private float getP (Tuple3f aTuple)
	{
		return itsPAccessor.get(aTuple);
	}
	
	private void setI (Tuple3f aTuple, float aValue)
	{
		itsIAccessor.set(aTuple, aValue);
	}
	
	private void setJ (Tuple3f aTuple, float aValue)
	{
		itsJAccessor.set(aTuple, aValue);
	}
	
	private void setP (Tuple3f aTuple, float aValue)
	{
		itsPAccessor.set(aTuple, aValue);
	}
	
	public void setP (float aP)
	{
		itsP = aP;
	}
	
	public void computeIntersection (Point3f aOrigin, Vector3f aDirection)
	{
		float k = (getP (getShape ().getPosition()) - getP (aOrigin)) / getP (aDirection);
		setI(itsIntersection, getI(aOrigin) + k*getI (aDirection));
		setJ(itsIntersection, getJ(aOrigin) + k*getJ (aDirection));
		setP(itsIntersection, itsP);
	}
	
	/**
	 * Determines whether the current intersection is inside the face.
	 * @return
	 */
	public boolean isInside ()
	{
		float ip = getI (getShape ().getPosition());
		float ii = getI (itsIntersection);
		float is = getI (getShape ().getSize());
		float jp = getJ (getShape ().getPosition());
		float ji = getJ (itsIntersection);
		float js = getJ (getShape ().getSize());
		return ip <= ii && ii <= ip + is 
			&& jp <= ji && ji <= jp + js;
	}
	
	public Point3f getIntersection ()
	{
		return itsIntersection;
	}
}