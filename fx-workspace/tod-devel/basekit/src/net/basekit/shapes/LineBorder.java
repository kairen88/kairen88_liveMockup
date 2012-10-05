/*
 * Created on Mar 6, 2004
 */
package net.basekit.shapes;

import javax.vecmath.Point3f;

import net.basekit.widgets.Margins;

import com.xith3d.scenegraph.QuadArray;

/**
 * A simple shape that is constituted of a rectangular border.
 * Its thickness can be customized.
 * @author gpothier
 */
public class LineBorder extends AbstractShape implements Border
{
	private static final Point3f POINT_BUFFER = new Point3f ();
	private QuadArray itsQuadArray = new QuadArray (16, QuadArray.COORDINATES);

	private float itsThickness;
	private Margins itsMargins = new Margins ();

	public LineBorder()
	{
		this (1);
	}

	public LineBorder (float aThickness)
	{
		setGeometry(itsQuadArray);
		setThickness (aThickness);
	}

	protected void updateGeometry ()
	{
		float x0 = getPosition().x;
		float y0 = getPosition().y;
		float z0 = getPosition().z;
		float x1 = x0 + getSize().x;
		float y1 = y0 + getSize().y;
		float z1 = z0 + getSize().z;
		float t = itsThickness;

		setCoordinate(0, x0, y0, z0);
		setCoordinate(1, x0, y0+t, z0);
		setCoordinate(2, x1, y0+t, z0);
		setCoordinate(3, x1, y0, z0);

		setCoordinate(4, x0, y1-t, z0);
		setCoordinate(5, x0, y1, z0);
		setCoordinate(6, x1, y1, z0);
		setCoordinate(7, x1, y1-t, z0);

		setCoordinate(8, x0, y0+t, z0);
		setCoordinate(9, x0, y1-t, z0);
		setCoordinate(10, x0+t, y1-t, z0);
		setCoordinate(11, x0+t, y0+t, z0);

		setCoordinate(12, x1-t, y0+t, z0);
		setCoordinate(13, x1-t, y1-t, z0);
		setCoordinate(14, x1, y1-t, z0);
		setCoordinate(15, x1, y0-t, z0);
	}
	
	private void setCoordinate (int aVertexIndex, float aX, float aY, float aZ)
	{
		POINT_BUFFER.x = aX;
		POINT_BUFFER.y = aY;
		POINT_BUFFER.z = aZ;
		itsQuadArray.setCoordinate(aVertexIndex, POINT_BUFFER);		
	}

	public final float getThickness ()
	{
		return itsThickness;
	}

	public void setThickness (float aThickness)
	{
		itsThickness = aThickness;
		itsMargins.setUniform (itsThickness);
		updateGeometry();
	}

	public Margins getMargins ()
	{
		return itsMargins;
	}
}
