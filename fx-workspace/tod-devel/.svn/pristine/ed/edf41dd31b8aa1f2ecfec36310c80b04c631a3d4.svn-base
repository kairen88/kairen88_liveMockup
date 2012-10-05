/*
 * Created on Jun 3, 2005
 */
package zz.csg.impl.constraints;

import java.awt.geom.Rectangle2D;

import zz.csg.api.constraints.IConstrainedDouble;
import zz.csg.api.constraints.IConstrainedRectangle;
import zz.csg.impl.AbstractGraphicObject;
import zz.utils.properties.PropertyId;
import zz.utils.properties.SimpleRWProperty;

/**
 * A {@link java.awt.geom.Rectangle2D} read-write property whoose coordinates can
 * be used with the constraints solver
 * @author gpothier
 */
public class ConstrainedRectangle extends SimpleRWProperty<Rectangle2D>
implements IConstrainedRectangle
{
	private MyRectangle itsRectangle = new MyRectangle();
	private Coord itsVX = new Coord("x");
	private Coord itsVY = new Coord("y");
	private Coord itsVW = new Coord("w");
	private Coord itsVH = new Coord("h");
	
	/**
	 * Indicates if the property is currently null;
	 */
	private boolean itsNull = true;
	
	private boolean itsUpdating = false;
	
	public ConstrainedRectangle()
	{
		this(null, null);
	}
	
	public ConstrainedRectangle(Object aOwner, PropertyId<Rectangle2D> aPropertyId)
	{
		super(aOwner, aPropertyId);
	}

	public ConstrainedRectangle(Object aOwner, PropertyId<Rectangle2D> aPropertyId, Rectangle2D aValue)
	{
		super (aOwner, aPropertyId, aValue);
	}

	public Rectangle2D get()
	{
		return itsNull ? null : itsRectangle;
	}
	
	public void set(double aX, double aY, double aW, double aH)
	{
		itsRectangle.setRect(aX, aY, aW, aH);
		itsNull = false;
	}

	public void setPosition(double aX, double aY)
	{
		if (itsNull) return;
		itsRectangle.setRect(aX, aY, itsRectangle.getWidth(), itsRectangle.getHeight());
	}

	public void set(Rectangle2D aValue)
	{
		if (aValue != null)
		{
			itsRectangle.setFrame(aValue);
			itsNull = false;
		}
		else 
		{
			if (! itsNull)
			{
				itsNull = true;
				firePropertyChanged(itsRectangle, null);
			}
		}
	}

	public IConstrainedDouble pX()
	{
		return itsVX;
	}

	public IConstrainedDouble pY()
	{
		return itsVY;
	}
	
	public IConstrainedDouble pW()
	{
		return itsVW;
	}
	
	public IConstrainedDouble pH()
	{
		return itsVH;
	}
	
	private class MyRectangle extends Rectangle2D
	{
		public void setRect(double aX, double aY, double aW, double aH)
		{
			itsUpdating = true;
			boolean itsChanged = false;
			itsChanged = set(itsVX, aX) | itsChanged;
			itsChanged = set(itsVY, aY) | itsChanged;
			itsChanged = set(itsVW, aW) | itsChanged;
			itsChanged = set(itsVH, aH) | itsChanged;
			itsUpdating = false;
			if (itsChanged) firePropertyChanged(this, this);
		}
		
		private boolean set(Coord aCoord, double aValue)
		{
			java.lang.Double theValue = aCoord.get();
			if (theValue == null || theValue.doubleValue() != aValue)
			{
				aCoord.set(aValue);
				return true;
			}
			else return false;
		}

		@Override
		public double getHeight()
		{
			return itsVH.value();
		}

		@Override
		public double getWidth()
		{
			return itsVW.value();
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

		/**
		 * Taken from {@link Rectangle2D.Double}
		 */
		public boolean isEmpty() {
		    return (getWidth() <= 0.0) || (getHeight() <= 0.0);
		}

		/**
		 * Taken from {@link Rectangle2D.Double}
		 */
		public int outcode(double x, double y) {
		    int out = 0;
		    double rx = getX();
		    double ry = getY();
		    double rwidth = getWidth();
		    double rheight = getHeight();
		    
		    if (rwidth <= 0) {
			out |= OUT_LEFT | OUT_RIGHT;
		    } else if (x < rx) {
			out |= OUT_LEFT;
		    } else if (x > rx + rwidth) {
			out |= OUT_RIGHT;
		    }
		    if (rheight <= 0) {
			out |= OUT_TOP | OUT_BOTTOM;
		    } else if (y < ry) {
			out |= OUT_TOP;
		    } else if (y > ry + rheight) {
			out |= OUT_BOTTOM;
		    }
		    return out;
		}

		/**
		 * Taken from {@link Rectangle2D.Double}
		 */
		public Rectangle2D createIntersection(Rectangle2D r) {
		    Rectangle2D dest = new Rectangle2D.Double();
		    Rectangle2D.intersect(this, r, dest);
		    return dest;
		}

		/**
		 * Taken from {@link Rectangle2D.Double}
		 */
		public Rectangle2D createUnion(Rectangle2D r) {
		    Rectangle2D dest = new Rectangle2D.Double();
		    Rectangle2D.union(this, r, dest);
		    return dest;
		}
		
		/**
		 * Taken from {@link Rectangle2D.Double}
		 */
		public String toString() {
		    double rx = getX();
		    double ry = getY();
		    double rwidth = getWidth();
		    double rheight = getHeight();

		    return getClass().getName()
			+ "[x=" + rx +
			",y=" + ry +
			",w=" + rwidth +
			",h=" + rheight + "]";
		}

	}
	
	private class Coord extends ConstrainedDouble
	{
		public Coord(String aName)
		{
			super(ConstrainedRectangle.this.getOwner(), new PropertyId<Double>(aName, false), 0.0);
		}

		public void change_value(double aValue)
		{
			super.change_value(aValue);
			if (! itsUpdating) ConstrainedRectangle.this.firePropertyChanged(itsRectangle, itsRectangle);				
		}
	}
}
