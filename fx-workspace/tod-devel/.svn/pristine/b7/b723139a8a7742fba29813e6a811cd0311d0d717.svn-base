/*
 * Created on 31-may-2004
 */
package zz.csg.display.tool.create;

import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import zz.csg.Resources;
import zz.csg.api.GraphicObjectContext;
import zz.csg.api.IGraphicObject;
import zz.csg.api.edition.IGraphicObjectController;



/**
 * Abstract tool that serves to implement tools that
 * permit to create a graphic object by dragging the mouse
 * so that the point where the mouse is pressed is one extremity of
 * the object, and the other extremity is defined by the point where 
 * the mouse is released. 
 * <p>
 * When the mouse is only clicked, an object will be created with
 * a default size.
 * <p>
 * It delegates the creation of the object to subclasses
 * @author gpothier
 */
public abstract class AbstractDragCreationTool extends AbstractCreateTool
{
	private IGraphicObject itsCurrentObject;
	private IGraphicObjectController itsCurrentController;
	
	public AbstractDragCreationTool()
	{
	}
	
	public Cursor getCursor()
	{
		return Resources.CROSS_CURSOR;
	}

	public void paint(Graphics2D aGraphics)
	{
		// We don't paint anything for now.
	}
	
	/**
	 * Returns a controller for the given graphic object.
	 * Subclasses can override this method if they do not want
	 * to use the default controller provided by the graphic object.
	 */
	protected IGraphicObjectController getController (
			IGraphicObject aGraphicObject, 
			GraphicObjectContext aContext)
	{
		return aGraphicObject.getController(aContext);
	}
	
	public void mouseClicked(Point2D aPoint, MouseEvent aE)
	{
		IGraphicObject theGraphicObject = setupObject();
		if (theGraphicObject != null)
		{
			Point2D thePoint = getTargetNode().rootToLocal(aPoint);
			IGraphicObjectController theController = getController(theGraphicObject, null);
			theController.getExtremity1Handle().move(thePoint);
			
			Point2D thePoint2 = new Point2D.Double (thePoint.getX()+20, thePoint.getY()+20);
			theController.getExtremity2Handle().move(thePoint2);
			
			repaint();
		}
	}

	public void startDrag(Point2D aPoint, MouseEvent aE)
	{
		itsCurrentObject = setupObject();
		if (itsCurrentObject != null)
		{
			Point2D thePoint = getTargetNode().rootToLocal(aPoint);
			itsCurrentController = getController(itsCurrentObject, null);
			itsCurrentController.getExtremity1Handle().move(thePoint);
			
			repaint();
		}
	}

	public void mouseDragged(Point2D aPoint, MouseEvent aE)
	{
		if (itsCurrentObject != null)
		{
			Point2D thePoint = getTargetNode().rootToLocal(aPoint);
			itsCurrentController.getExtremity2Handle().move(thePoint);
			
			repaint();
		}		
	}

	public void endDrag(Point2D aPoint, MouseEvent aE)
	{
		itsCurrentObject = null;
		itsCurrentController = null;
		done();
	}

	public void cancelDrag()
	{
		if (itsCurrentObject != null) removeFromContainer(itsCurrentObject);
	}

}
