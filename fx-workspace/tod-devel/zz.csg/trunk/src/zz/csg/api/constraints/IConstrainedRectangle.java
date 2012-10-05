/*
 * Created on Jun 4, 2005
 */
package zz.csg.api.constraints;

import java.awt.geom.Rectangle2D;

import zz.utils.properties.IRWProperty;

public interface IConstrainedRectangle extends IRWProperty<Rectangle2D>
{
	/**
	 * Shortcut for the regular set method.
	 */
	public void set(double aX, double aY, double aW, double aH);
	
	/**
	 * Sets the position without changing the size.
	 */
	public void setPosition(double aX, double aY);
	
	/**
	 * Returns a variable that represents the X coordinate of this rectangle
	 */
	public IConstrainedDouble pX();

	/**
	 * Returns a variable that represents the Y coordinate of this rectangle
	 */
	public IConstrainedDouble pY();

	/**
	 * Returns a variable that represents the W coordinate of this rectangle
	 */
	public IConstrainedDouble pW();

	/**
	 * Returns a variable that represents the H coordinate of this rectangle
	 */
	public IConstrainedDouble pH();

}