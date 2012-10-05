/*
 * Created on Apr 20, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package zz.csg.api.figures;

import java.awt.geom.Point2D;

import zz.csg.api.IRectangularGraphicObject;
import zz.csg.api.constraints.IConstrainedPoint;
import zz.utils.properties.PropertyId;


/**
 * A text baloon with a positionable arrow.
 * @author gpothier
 */
public interface IGOBalloon extends IShape, IRectangularGraphicObject, IGOText
{
	public static final PropertyId<Point2D> POINT = 
		new PropertyId<Point2D> ("point", false);
	
	/**
	 * Position of the arrow's tip.
	 */
	public IConstrainedPoint pPoint ();
}
