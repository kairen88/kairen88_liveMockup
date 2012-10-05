/*
 * Created on Mar 5, 2004
 */
package net.basekit.shapes;

import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

/**
 * Permits to compute the coordinate of intersection between this shape and a ray.
 * @author gpothier
 */
public interface PickableShape extends Shape
{
	/**
	 * Computes the coordinates of the intersection between this shape and
	 * the specified ray. The ray's coordinates are world coordinates, whereas
	 * the returned point's coordinates are in the coordinate system of this shape. 
	 */
	public Point3f computeIntersectionCoordinates (Point3f aOrigin, Vector3f aDirection);
}
