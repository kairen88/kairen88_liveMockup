/*
 * Created on Mar 4, 2004
 */
package net.basekit.shapes;

import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import net.basekit.shapes.utils.Face;
import net.basekit.shapes.utils.XYFace;
import net.basekit.shapes.utils.XZFace;
import net.basekit.shapes.utils.YZFace;
import net.basekit.utils.GLMath;

import com.xith3d.scenegraph.QuadArray;
import com.xith3d.scenegraph.Transform3D;

/**
 * A cube made of a quad array.
 * @author gpothier
 */
public class Box extends AbstractShape implements PickableShape
{
	private QuadArray itsQuadArray = new QuadArray (24, QuadArray.COORDINATES);
	
	private final Point3f POINT_BUFFER = new Point3f ();
	private final Vector3f VECTOR_BUFFER = new Vector3f ();
	

	private Face[] itsFaces = {new XZFace (this, 0), new XYFace (this, 1), new XZFace (this, 2), 
			new XYFace (this, 3), new YZFace (this, 4), new YZFace (this, 5)};   
	

	public Box (Vector3f aPosition, Vector3f aSize)
	{
		super (aPosition, aSize);
		init ();
	}
	
	public Box (Vector3f aSize)
	{
		super (new Vector3f (), aSize);
		init ();
	}
	
	public Box ()
	{
		init ();
	}
	
	private void init ()
	{
		setGeometry(itsQuadArray);		
	}
	
	protected void updateGeometry ()
	{
		float x0 = getPosition().x-1;
		float y0 = getPosition().y-1;
		float z0 = getPosition().z-1;
		float x1 = x0 + getSize().x+2;
		float y1 = y0 + getSize().y+2;
		float z1 = z0 + getSize().z+2;

		setCoordinate(0, x0, y0, z0);
		setCoordinate(1, x1, y0, z0);
		setCoordinate(2, x1, y0, z1);
		setCoordinate(3, x0, y0, z1);
		itsFaces[0].setP (y0);

		setCoordinate(4, x1, y0, z1);
		setCoordinate(5, x1, y1, z1);
		setCoordinate(6, x0, y1, z1);
		setCoordinate(7, x0, y0, z1);
		itsFaces[1].setP (z1);

		setCoordinate(8, x1, y1, z1);
		setCoordinate(9, x1, y1, z0);
		setCoordinate(10, x0, y1, z0);
		setCoordinate(11, x0, y1, z1);
		itsFaces[2].setP (y1);

		setCoordinate(12, x1, y1, z0);
		setCoordinate(13, x1, y0, z0);
		setCoordinate(14, x0, y0, z0);
		setCoordinate(15, x0, y1, z0);
		itsFaces[3].setP (z0);

		setCoordinate(16, x1, y1, z0);
		setCoordinate(17, x1, y1, z1);
		setCoordinate(18, x1, y0, z1);
		setCoordinate(19, x1, y0, z0);
		itsFaces[4].setP (x1);

		setCoordinate(20, x0, y1, z1);
		setCoordinate(21, x0, y1, z0);
		setCoordinate(22, x0, y0, z0);
		setCoordinate(23, x0, y0, z1);
		itsFaces[5].setP (x0);

	}
	
	private void setCoordinate (int aVertexIndex, float aX, float aY, float aZ)
	{
		POINT_BUFFER.x = aX;
		POINT_BUFFER.y = aY;
		POINT_BUFFER.z = aZ;
		itsQuadArray.setCoordinate(aVertexIndex, POINT_BUFFER);		
	}

	public Point3f computeIntersectionCoordinates (Point3f aOrigin, Vector3f aDirection)
	{
		GLMath.tranformVWorldToLocal(aOrigin, aDirection, this, POINT_BUFFER, VECTOR_BUFFER);
		
		Point3f theResult = null;
		float theResultDistance = Float.MAX_VALUE;
		for (int i = 0; i < itsFaces.length; i++)
		{
			Face theFace = itsFaces[i];
			theFace.computeIntersection(POINT_BUFFER, VECTOR_BUFFER);
			if (! theFace.isInside()) continue;
			
			Point3f theIntersection = theFace.getIntersection();
			float theDistance = POINT_BUFFER.distance(theIntersection);
			if (theDistance < theResultDistance)
			{
				theResultDistance = theDistance;
				theResult = theIntersection;
			}
		}
		
		return theResult != null ? new Point3f (theResult) : null;
	}
	
}
