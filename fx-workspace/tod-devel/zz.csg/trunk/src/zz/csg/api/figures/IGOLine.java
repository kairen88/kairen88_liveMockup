/*
 * Created on 17-jun-2004
 */
package zz.csg.api.figures;

import java.awt.geom.Point2D;

import zz.csg.api.constraints.IConstrainedPoint;
import zz.utils.properties.PropertyId;

/**
 * A line defined by its two end point.
 * @author gpothier
 */
public interface IGOLine extends IShape
{
	public static final PropertyId<Point2D> POINT1 = 
		new PropertyId<Point2D> ("point1", false);

	public static final PropertyId<Point2D> POINT2 = 
		new PropertyId<Point2D> ("point2", false);
	
	/**
	 * First end of the line. 
	 */
	public IConstrainedPoint pPoint1 ();

	/**
	 * Second end of the line. 
	 */
	public IConstrainedPoint pPoint2 ();
	
}
