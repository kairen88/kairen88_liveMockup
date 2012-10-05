/*
 * Created on Apr 22, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package zz.csg.impl.figures;

import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import EDU.Washington.grad.gjb.cassowary.ExCLError;
import EDU.Washington.grad.gjb.cassowary.ISimpleVariable;

import zz.csg.Resources;
import zz.csg.api.GraphicObjectContext;
import zz.csg.api.constraints.IConstrainedPoint;
import zz.csg.api.edition.IGraphicMapper;
import zz.csg.api.edition.IGraphicObjectController;
import zz.csg.api.edition.IHandle;
import zz.csg.api.figures.IGOLine;
import zz.csg.impl.constraints.ConstrainedPoint;
import zz.csg.impl.edition.AbstractHandle;
import zz.csg.impl.edition.DefaultGraphicObjectController;
import zz.csg.impl.edition.PointHandle;
import zz.utils.properties.IRWProperty;
import zz.utils.properties.SimpleRWProperty;


/**
 * @author gpothier
 */
public class SVGLine extends AbstractShape implements IGOLine
{
	private final IConstrainedPoint pPoint1 = new GOConstrainedPoint(this, POINT1);
	private final IConstrainedPoint pPoint2 = new GOConstrainedPoint(this, POINT2);
	
	public IConstrainedPoint pPoint1()
	{
		return pPoint1;
	}
	
	public IConstrainedPoint pPoint2()
	{
		return pPoint2;
	}
	
	protected void changed(IRWProperty aProperty)
	{
		super.changed(aProperty);
		if (aProperty == pPoint1 || aProperty == pPoint2)
		{
			Point2D thePoint1 = pPoint1().get();
			Point2D thePoint2 = pPoint2().get();
			
			Line2D.Double theShape = thePoint1 != null && thePoint2 != null ?
					new Line2D.Double(thePoint1, thePoint2)
					: null;
					
			setShape(theShape);
		}
	}

	protected IGraphicObjectController createController(GraphicObjectContext aContext)
	{
		IHandle theHandle1 = new PointHandle(
				aContext,
				this, 
				pPoint1(), 
				Resources.ICON_BLACK_SELECTION_HANDLE);
		
		IHandle theHandle2 = new PointHandle(
				aContext,
				this, 
				pPoint2(), 
				Resources.ICON_BLACK_SELECTION_HANDLE);
		
		return new DefaultGraphicObjectController(
				new PositionHandle(aContext),
				new IHandle[] {theHandle1, theHandle2},
				theHandle1,
				theHandle2,
				null);
	}
	
//	private void updateBounds()
//	{
//		Point2D thePoint1 = pPoint1().get();
//		Point2D thePoint2 = pPoint2().get();
//
//		itsBounds = new Rectangle2D.Double();
//		if (thePoint1 != null) itsBounds.add(thePoint1);
//		if (thePoint2 != null) itsBounds.add(thePoint2);
//	}
//
//	public Rectangle2D getBounds()
//	{
//		if (itsBounds == null) updateBounds();
//		return itsBounds;
//	}
//
//	protected void changed(IRWProperty aProperty)
//	{
//		if (POINT1.equals(aProperty.getId()) || POINT2.equals(aProperty.getId()))
//		{
//			itsBounds = null;
//		}
//		super.changed(aProperty);
//	}

	private class PositionHandle extends AbstractHandle
	{
		/**
		 * Vector defined by the extremities of the line.
		 */
		private double itsDX;

		private double itsDY;

		public PositionHandle(GraphicObjectContext aContext)
		{
			super(aContext, SVGLine.this);
		}

		public boolean isInside(IGraphicMapper aMapper, Point2D aPoint)
		{
			return SVGLine.this.isInside(getContext(), aPoint);
		}

		public Point2D getOrigin()
		{
			return pPoint1().get();
		}
		
		protected ISimpleVariable[] getEditedVariables()
		{
			return new ISimpleVariable[] {
				pPoint1.pX(), pPoint1.pY(),
				pPoint2.pX(), pPoint2.pY(),
			};
		}
		
		public void startMovement()
		{
			super.startMovement();

			Point2D thePoint1 = pPoint1().get();
			Point2D thePoint2 = pPoint2().get();
			itsDX = thePoint2.getX() - thePoint1.getX();
			itsDY = thePoint2.getY() - thePoint1.getY();
		}

		protected void move0(Point2D aPoint) throws ExCLError
		{
			set(pPoint1.pX(), aPoint.getX());
			set(pPoint1.pY(), aPoint.getY());
			
			set(pPoint2.pX(), aPoint.getX() + itsDX);
			set(pPoint2.pY(), aPoint.getY() + itsDY);
		}

		public Cursor getCursor()
		{
			return Resources.MOVE_CURSOR;
		}

		public void paint(IGraphicMapper aMapper, Graphics2D aGraphics)
		{
		}
	}
}
