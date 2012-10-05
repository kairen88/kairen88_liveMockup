/*
 * Created on Jun 3, 2005
 */
package zz.csg.impl.constraints;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import zz.csg.api.constraints.IConstrainedDouble;
import zz.csg.api.constraints.IConstrainedPoint;
import zz.csg.impl.AbstractGraphicObject;
import zz.utils.Utils;
import zz.utils.properties.PropertyId;
import zz.utils.properties.SimpleRWProperty;
import EDU.Washington.grad.gjb.cassowary.ClAbstractSimpleVariable;
import EDU.Washington.grad.gjb.cassowary.ISimpleVariable;

/**
 * A {@link Point2D} read-write property whoose coordinates can be used by the constraints solver.
 * @author gpothier
 */
public class ConstrainedPoint extends SimpleRWProperty<Point2D>
implements IConstrainedPoint
{
	private MyPoint itsPoint = new MyPoint();
	private IConstrainedDouble itsVX = new Coord();
	private IConstrainedDouble itsVY = new Coord();
	
	private boolean itsUpdating = false;
	
	public ConstrainedPoint(Object aOwner, PropertyId<Point2D> aPropertyId)
	{
		super(aOwner, aPropertyId);
	}

	public ConstrainedPoint(Object aContainer, PropertyId<Point2D> aPropertyId, Point2D aValue)
	{
		super (aContainer, aPropertyId, aValue);
	}

	public Point2D get()
	{
		return itsPoint;
	}
	
	public void set(double aX, double aY)
	{
		itsPoint.setLocation(aX, aY);
	}

	public void set(Point2D aValue)
	{
		itsPoint.setLocation(aValue);
	}

	public IConstrainedDouble pX()
	{
		return itsVX;
	}

	public IConstrainedDouble pY()
	{
		return itsVY;
	}
	
	private class MyPoint extends Point2D
	{
		
		@Override
		public void setLocation(double aX, double aY)
		{
			itsUpdating = true;
			itsVX.set(aX);
			itsVY.set(aY);
			itsUpdating = false;
			firePropertyValueChanged();
		}

		@Override
		public double getX()
		{
			return itsVX.value();
		}

		@Override
		public double getY()
		{
			return itsVY.value();
		}
	}
	
	public class Coord extends ConstrainedDouble
	{
		public Coord()
		{
			super(ConstrainedPoint.this.getOwner(), null, 0.0);
		}

		public void change_value(double aValue)
		{
			super.change_value(aValue);
			if (! itsUpdating) ConstrainedPoint.this.firePropertyChanged(itsPoint, itsPoint);				
		}
	}
}
