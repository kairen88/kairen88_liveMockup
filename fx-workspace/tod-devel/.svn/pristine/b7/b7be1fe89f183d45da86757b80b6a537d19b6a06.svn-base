/*
 * Created on Jan 3, 2005
 */
package zz.csg.display.tool.create;

import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import zz.csg.Resources;
import zz.csg.api.IGraphicObject;
import zz.csg.api.figures.IGOPath;


/**
 * This tool permits to create freeform curves.
 * @author gpothier
 */
public abstract class CurveCreateTool extends AbstractCreateTool
{
	private IGOPath itsPath;

	public Cursor getCursor()
	{
		return Resources.CROSS_CURSOR;
	}

	public void paint(Graphics2D aGraphics)
	{
	}

	public void startDrag(Point2D aPoint, MouseEvent aE)
	{
		itsPath = (IGOPath) setupObject();
		itsPath.pPath().get().moveTo(
				(float) aPoint.getX(), 
				(float) aPoint.getY());
	}
	
	/**
	 * Creates the path graphic object
	 */
	protected abstract IGOPath createPath();
	
	protected final IGraphicObject createObject()
	{
		return createPath();
	}
	
	public void endDrag(Point2D aPoint, MouseEvent aE)
	{
		itsPath = null;
		repaint();
		done();
	}
	
	public void mouseDragged(Point2D aPoint, MouseEvent aE)
	{
		itsPath.pPath().get().lineTo(
				(float) aPoint.getX(), 
				(float) aPoint.getY());	
		
		repaint();
	}
	
	public void cancelDrag()
	{
		removeFromContainer(itsPath);
		repaint();
	}
}
