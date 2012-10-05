/*
 * Created on May 29, 2004
 */
package zz.csg.impl.edition;

import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.ImageIcon;

import EDU.Washington.grad.gjb.cassowary.ExCLError;
import EDU.Washington.grad.gjb.cassowary.ISimpleVariable;

import zz.csg.Resources;
import zz.csg.api.GraphicObjectContext;
import zz.csg.api.IGraphicObject;
import zz.csg.api.IRectangularGraphicObject;
import zz.csg.api.constraints.IConstrainedRectangle;
import zz.csg.api.edition.IGraphicMapper;
import zz.csg.api.edition.IGraphicObjectController;
import zz.csg.api.edition.IHandle;
import zz.utils.properties.IRWProperty;


/**
 * Helper class to create controllers for rectangular graphic objects 
 * (rectangles, ellipses...)
 * @author gpothier
 */
public class RectangularControllerUtils
{
	private static final ImageIcon ICON = Resources.ICON_WHITE_SELECTION_HANDLE;
	
//	public static IGraphicObjectController createTransformingController (
//			IGraphicObject aGraphicObject,
//			GraphicObjectContext aContext)
//	{
//		return RectangularControllerUtils.createRectangularController(
//				aContext, 
//				aGraphicObject, 
//				new TransformingBoundsProperty(aContext, aGraphicObject));
//	}
//
	
	/**
	 * Creates a default controller for the bounds of a rectangular graphic object
	 */
	public static DefaultGraphicObjectController createRectangularController (
			GraphicObjectContext aContext,
			IRectangularGraphicObject aGraphicObject)
	{
		return createRectangularController(
				aContext, 
				aGraphicObject, 
				aGraphicObject.pBounds());
	}

	/**
	 * Creates a default controller for the bounds of a graphic object.
	 */
	public static DefaultGraphicObjectController createRectangularController (
			GraphicObjectContext aContext,
			IGraphicObject aGraphicObject,
			IConstrainedRectangle aBoundsProperty)
	{
		IHandle thePositionHandle = new PositionHandle (aContext, aGraphicObject, aBoundsProperty);
		IHandle theSEHandle = new SE_Handle (aContext, aGraphicObject, aBoundsProperty);
		IHandle[] theControlPoints = new IHandle[]
		{
				new N_Handle (aContext, aGraphicObject, aBoundsProperty), 
				new S_Handle (aContext, aGraphicObject, aBoundsProperty), 
				new E_Handle (aContext, aGraphicObject, aBoundsProperty), 
				new W_Handle (aContext, aGraphicObject, aBoundsProperty),
				new NW_Handle (aContext, aGraphicObject, aBoundsProperty), 
				new NE_Handle (aContext, aGraphicObject, aBoundsProperty), 
				new SW_Handle (aContext, aGraphicObject, aBoundsProperty), theSEHandle
		};
		return new DefaultGraphicObjectController (
				thePositionHandle, 
				theControlPoints, 
				thePositionHandle, 
				theSEHandle,
				null);
	}
	
	
	private static class PositionHandle extends AbstractRectangularGraphicObjectHandle
	{
		public PositionHandle(
				GraphicObjectContext aContext,
				IGraphicObject aGraphicObject, 
				IConstrainedRectangle aBoundsProperty)
		{
			super(aContext, aGraphicObject, aBoundsProperty);
		}
		
		public PositionHandle(
				GraphicObjectContext aContext,
				IRectangularGraphicObject aGraphicObject)
		{
			super(aContext, aGraphicObject);

		}
		
		public boolean isInside(IGraphicMapper aMapper, Point2D aPoint)
		{
			return getGraphicObject ().isInside(getContext(), aPoint);
		}

		public Point2D getOrigin()
		{
			return new Point2D.Double (getMinX(), getMinY());
		}

		protected ISimpleVariable[] getEditedVariables()
		{
			return new ISimpleVariable[] {vX(), vY()};
		}
		
		protected void move0(Point2D aPoint) throws ExCLError
		{
			setX(aPoint.getX());
			setY(aPoint.getY());
		}

		public Cursor getCursor()
		{
			return Resources.MOVE_CURSOR;
		}

		public void paint(IGraphicMapper aMapper, Graphics2D aGraphics)
		{
		}
		
	}
	
	
	private static abstract class AbstractResizeHandle extends AbstractRectangularGraphicObjectHandle
	{
		public AbstractResizeHandle(
				GraphicObjectContext aContext,
				IGraphicObject aGraphicObject, 
				IConstrainedRectangle aBoundsProperty)
		{
			super(aContext, aGraphicObject, aBoundsProperty);
		}
		
		public AbstractResizeHandle(
				GraphicObjectContext aContext,
				IRectangularGraphicObject aGraphicObject)
		{
			super(aContext, aGraphicObject);
		}
		
		public boolean isInside(IGraphicMapper aMapper, Point2D aPoint)
		{
			return isInsideCenteredImage(aMapper, aPoint, ICON);
		}
		
		public void paint(IGraphicMapper aMapper, Graphics2D aGraphics)
		{
			paintCenteredImage(aMapper, aGraphics, ICON);
		}
		
		protected ISimpleVariable[] getEditedVariables()
		{
			return new ISimpleVariable[] {vX(), vY(), vW(), vH()}; //not optimal, but simpler.
		}
		

	}
	
	private static class N_Handle extends AbstractResizeHandle
	{
		public N_Handle(
				GraphicObjectContext aContext,
				IGraphicObject aGraphicObject, 
				IConstrainedRectangle aBoundsProperty)
		{
			super(aContext, aGraphicObject, aBoundsProperty);
		}
		
		public N_Handle(
				GraphicObjectContext aContext,
				IRectangularGraphicObject aGraphicObject)
		{
			super(aContext, aGraphicObject);
		}
		
		public Cursor getCursor()
		{
			return Resources.N_RESIZE_CURSOR;
		}
		
		public Point2D getOrigin()
		{
			return new Point2D.Double (getCenterX(), getMinY());
		}
		
		protected void move0(Point2D aPoint) throws ExCLError
		{
			setMinY(Math.min (aPoint.getY(), getMaxY()));
		}
	}
	
	private static class S_Handle extends AbstractResizeHandle
	{
		public S_Handle(
				GraphicObjectContext aContext,
				IGraphicObject aGraphicObject, 
				IConstrainedRectangle aBoundsProperty)
		{
			super(aContext, aGraphicObject, aBoundsProperty);
		}
		
		public S_Handle(
				GraphicObjectContext aContext,
				IRectangularGraphicObject aGraphicObject)
		{
			super(aContext, aGraphicObject);
		}
		
		public Cursor getCursor()
		{
			return Resources.S_RESIZE_CURSOR;
		}
		
		public Point2D getOrigin()
		{
			return new Point2D.Double (getCenterX(), getMaxY());
		}
		
		protected void move0(Point2D aPoint) throws ExCLError
		{
			setMaxY(Math.max(aPoint.getY(), getMinY()));
		}
	}
	
	private static class E_Handle extends AbstractResizeHandle
	{
		public E_Handle(
				GraphicObjectContext aContext,
				IGraphicObject aGraphicObject, 
				IConstrainedRectangle aBoundsProperty)
		{
			super(aContext, aGraphicObject, aBoundsProperty);
		}
		
		public E_Handle(
				GraphicObjectContext aContext,
				IRectangularGraphicObject aGraphicObject)
		{
			super(aContext, aGraphicObject);
		}
		
		public Cursor getCursor()
		{
			return Resources.E_RESIZE_CURSOR;
		}
		
		public Point2D getOrigin()
		{
			return new Point2D.Double (getMaxX(), getCenterY());
		}
		
		protected void move0(Point2D aPoint) throws ExCLError
		{
			setMaxX(Math.max (aPoint.getX(), getMinX()));
		}
	}
	
	private static class W_Handle extends AbstractResizeHandle
	{
		
		public W_Handle(
				GraphicObjectContext aContext,
				IGraphicObject aGraphicObject, 
				IConstrainedRectangle aBoundsProperty)
		{
			super(aContext, aGraphicObject, aBoundsProperty);
		}
		
		public W_Handle(
				GraphicObjectContext aContext,
				IRectangularGraphicObject aGraphicObject)
		{
			super(aContext, aGraphicObject);
		}
		
		public Cursor getCursor()
		{
			return Resources.W_RESIZE_CURSOR;
		}
		
		public Point2D getOrigin()
		{
			return new Point2D.Double (getMinX(), getCenterY());
		}
		
		protected void move0(Point2D aPoint) throws ExCLError
		{
			setMinX(Math.min(aPoint.getX(), getMaxX()));
		}
	}
	
	private static class NW_Handle extends AbstractResizeHandle
	{
		public NW_Handle(
				GraphicObjectContext aContext,
				IGraphicObject aGraphicObject, 
				IConstrainedRectangle aBoundsProperty)
		{
			super(aContext, aGraphicObject, aBoundsProperty);
		}
		
		public NW_Handle(
				GraphicObjectContext aContext,
				IRectangularGraphicObject aGraphicObject)
		{
			super(aContext, aGraphicObject);
		}
		
		public Cursor getCursor()
		{
			return Resources.NW_RESIZE_CURSOR;
		}
		
		public Point2D getOrigin()
		{
			return new Point2D.Double (getMinX(), getMinY());
		}
		
		protected void move0(Point2D aPoint) throws ExCLError
		{
			setMinX(Math.min(aPoint.getX(), getMaxX()));
			setMinY(Math.min (aPoint.getY(), getMaxY()));
		}
	}
	
	private static class NE_Handle extends AbstractResizeHandle
	{
		public NE_Handle(
				GraphicObjectContext aContext,
				IGraphicObject aGraphicObject, 
				IConstrainedRectangle aBoundsProperty)
		{
			super(aContext, aGraphicObject, aBoundsProperty);
		}
		
		public NE_Handle(
				GraphicObjectContext aContext,
				IRectangularGraphicObject aGraphicObject)
		{
			super(aContext, aGraphicObject);
		}
		
		public Cursor getCursor()
		{
			return Resources.NE_RESIZE_CURSOR;
		}
		
		public Point2D getOrigin()
		{
			return new Point2D.Double (getMaxX(), getMinY());
		}
		
		protected void move0(Point2D aPoint) throws ExCLError
		{
			setMaxX(Math.max (aPoint.getX(), getMinX()));
			setMinY(Math.min (aPoint.getY(), getMaxY()));
		}
	}
	
	private static class SW_Handle extends AbstractResizeHandle
	{
		public SW_Handle(
				GraphicObjectContext aContext,
				IGraphicObject aGraphicObject, 
				IConstrainedRectangle aBoundsProperty)
		{
			super(aContext, aGraphicObject, aBoundsProperty);
		}
		
		public SW_Handle(
				GraphicObjectContext aContext,
				IRectangularGraphicObject aGraphicObject)
		{
			super(aContext, aGraphicObject);
		}
		
		public Cursor getCursor()
		{
			return Resources.SW_RESIZE_CURSOR;
		}
		
		public Point2D getOrigin()
		{
			return new Point2D.Double (getMinX(), getMaxY());
		}
		
		protected void move0(Point2D aPoint) throws ExCLError
		{
			setMinX(Math.min(aPoint.getX(), getMaxX()));
			setMaxY(Math.max(aPoint.getY(), getMinY()));
		}
	}
	
	private static class SE_Handle extends AbstractResizeHandle
	{
		public SE_Handle(
				GraphicObjectContext aContext,
				IGraphicObject aGraphicObject, 
				IConstrainedRectangle aBoundsProperty)
		{
			super(aContext, aGraphicObject, aBoundsProperty);
		}
		
		public SE_Handle(
				GraphicObjectContext aContext,
				IRectangularGraphicObject aGraphicObject)
		{
			super(aContext, aGraphicObject);
		}
		
		public Cursor getCursor()
		{
			return Resources.SE_RESIZE_CURSOR;
		}
		
		public Point2D getOrigin()
		{
			return new Point2D.Double (getMaxX(), getMaxY());
		}
		
		protected void move0(Point2D aPoint) throws ExCLError
		{
			setMaxX(Math.max (aPoint.getX(), getMinX()));
			setMaxY(Math.max(aPoint.getY(), getMinY()));
		}
	}
	
}
