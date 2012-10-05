package zz.csg.impl.edition;

import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import javax.swing.ImageIcon;

import EDU.Washington.grad.gjb.cassowary.ExCLError;
import EDU.Washington.grad.gjb.cassowary.ISimpleVariable;

import zz.csg.Resources;
import zz.csg.api.GraphicObjectContext;
import zz.csg.api.IGraphicObject;
import zz.csg.api.constraints.IConstrainedPoint;
import zz.csg.api.edition.IGraphicMapper;
import zz.utils.properties.IRWProperty;


/**
 * This handle can controls any property of type {@link Point2D}
 * @author gpothier
 */
public class PointHandle extends AbstractHandle
{
	private IConstrainedPoint itsPoint;
	private final ImageIcon itsIcon;


	public PointHandle(
			GraphicObjectContext aContext,
			IGraphicObject aGraphicObject, 
			IConstrainedPoint aPointProperty,
			ImageIcon aIcon)
	{
		super(aContext, aGraphicObject);
		itsPoint = aPointProperty;
		itsIcon = aIcon;
	}
	
	public boolean isInside(IGraphicMapper aMapper, Point2D aPoint)
	{
		return isInsideCenteredImage(aMapper, aPoint, itsIcon);
	}

	public Point2D getOrigin()
	{
		return itsPoint.get();
	}

	protected ISimpleVariable[] getEditedVariables()
	{
		return new ISimpleVariable[] {itsPoint.pX(), itsPoint.pY()};
	}
	
	protected void move0(Point2D aPoint) throws ExCLError
	{
		set(itsPoint.pX(), aPoint.getX());
		set(itsPoint.pY(), aPoint.getY());
	}
	
	public Cursor getCursor()
	{
		return Resources.MOVE_CURSOR;
	}

	public void paint(IGraphicMapper aMapper, Graphics2D aGraphics)
	{
		paintCenteredImage(aMapper, aGraphics, itsIcon);
	}
	
}