/*
 * Created on Jan 5, 2005
 */
package zz.csg.impl;

import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

/**
 * Utility class for computing the transform that corresponds to
 * a given view box, as defined by the SVG specs.
 * @author gpothier
 */
public class ViewBoxUtils
{
	public static AffineTransform createViewBoxTransform (
			Rectangle2D aBounds,
			Rectangle2D aViewBox)
	{
		double theX = aBounds.getX();
		double theY = aBounds.getY();
		double theW = aBounds.getWidth();
		double theH = aBounds.getHeight();
		
		double theVX = aViewBox.getX();
		double theVY = aViewBox.getY();
		double theVW = aViewBox.getWidth();
		double theVH = aViewBox.getHeight();
		
		double theAX = theW / theVW;
		double theBX = theX - theAX*theVX;
		
		double theAY = theH / theVH;
		double theBY = theY - theAY*theVY;

		return new AffineTransform(theAX, 0, 0, theAY, theBX, theBY);
	}
}
