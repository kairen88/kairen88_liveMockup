/*
 * Created on May 30, 2004
 */
package zz.csg.display.tool.select;

import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.List;

import zz.csg.Log;
import zz.csg.Resources;
import zz.csg.api.GraphicNode;
import zz.csg.api.edition.IGraphicObjectController;
import zz.csg.api.edition.IHandle;
import zz.csg.display.tool.EditorTool;
import zz.utils.Utils;


/**
 * Abstract tool that manages the {@link IHandle handles} of
 * {@link IGraphicObjectController controllers}.
 * The set of objects whose handles should be considered is defined
 * by subclasses.
 * @author gpothier
 */
public abstract class AbstractHandleTool extends EditorTool 
{
	private Cursor itsCurrentCursor;
	
	/**
	 * Difference between logical mouse position and
	 * handle origin when dragging started.
	 */
	private Point2D itsDragDelta = new Point2D.Double ();
	
	/**
	 * The handle that is being dragged.
	 */
	private IHandle itsDraggedHandle;
	
	public AbstractHandleTool()
	{
	}
	
//	/**
//	 * Searches a selectable object at a given position
//	 */
//	private IGraphicObject findObjectAt (Point2D aPoint)
//	{
//		return getEditionLayer().findObjectAt(aPoint);
//	}
	
	/**
	 * Returns an iterable over the set of graphic objects
	 * whose handles should be considered.
	 */
	protected abstract Iterable<GraphicNode> getGraphicObjects();
	
	/**
	 * Finds a handle at the given position. Only handles of selected
	 * objects are considered.
	 * Priority is given to control point handles over position handles
	 * @param aPoint A point in the root container's coordinate system.
	 * @return A handle, or null if there is none.
	 */
	protected IHandle findHandleAt (Point2D aPoint)
	{
		for (GraphicNode theNode : getGraphicObjects())
		{
			if (theNode == null)
			{
				Log.GRAPHIC.warn("Null element in selection");
				continue;
			}
			
			IGraphicObjectController theController = theNode.getController();
			
			Point2D theLocalPoint = theNode.rootToLocal(aPoint);

			if (theController != null)
			{
				// First we check control points
				List theControlPoints = theController.getControlPoints();
				if (theControlPoints != null) for (Iterator theIterator2 = theControlPoints.iterator(); theIterator2.hasNext();)
				{
					IHandle theHandle = (IHandle) theIterator2.next();
					if (theHandle.isInside(getMapper(), theLocalPoint)) return theHandle;
				}
				
				// Then if no control point was found, we check position handle
				IHandle thePositionHandle = theController.getPositionHandle();
				if (thePositionHandle.isInside(getMapper(), theLocalPoint)) return thePositionHandle;
			}
		}
		
		return null;
	}
	
	public void paint(Graphics2D aGraphics)
	{
		for (GraphicNode theNode : getGraphicObjects())
		{
			if (theNode == null)
			{
				Log.GRAPHIC.warn("Null element in selection");
				continue;
			}
			
			IGraphicObjectController theController = theNode.getController();
			
			if (theController != null)
			{
				// First we paint the position handle
				IHandle thePositionHandle = theController.getPositionHandle();
				thePositionHandle.paint(getMapper(), aGraphics);
	
				// Then the control points
				List theControlPoints = theController.getControlPoints();
				if (theControlPoints != null) for (Iterator theIterator2 = theControlPoints.iterator(); theIterator2.hasNext();)
				{
					IHandle theHandle = (IHandle) theIterator2.next();
					theHandle.paint(getMapper(), aGraphics);
				}
			}
		}
	}
	
	/**
	 * Returns the cursor to display at the specified location, or
	 * null of no specific cursor should be displayed.
	 */
	protected Cursor getCursorFor (Point2D aPoint)
	{
		IHandle theHandle = findHandleAt(aPoint);
		if (theHandle != null) return theHandle.getCursor();
		else return null;
	}
	
	public void mouseMoved(Point2D aPoint, MouseEvent aE)
	{
		itsCurrentCursor = getCursorFor(aPoint);
		if (itsCurrentCursor == null) itsCurrentCursor = Resources.DEFAULT_CURSOR;
	}
	
	public void startDrag(Point2D aPoint, MouseEvent aE)
	{
		IHandle theHandle = findHandleAt(aPoint);

		if (theHandle != null)
		{
			itsDraggedHandle = theHandle;
			itsDraggedHandle.startMovement();
			Point2D theOrigin = theHandle.getOrigin();
			double theDeltaX = theOrigin.getX() - aPoint.getX();
			double theDeltaY = theOrigin.getY() - aPoint.getY();
			theDeltaX = Utils.round(theDeltaX, 1);
			theDeltaY = Utils.round(theDeltaY, 1);
			itsDragDelta.setLocation(theDeltaX, theDeltaY);
		}
	}
	
	public void mouseDragged(Point2D aPoint, MouseEvent aE)
	{
		if (itsDraggedHandle != null)
		{
			double theX = aPoint.getX() + itsDragDelta.getX();
			double theY = aPoint.getY() + itsDragDelta.getY();
			itsDraggedHandle.move(new Point2D.Double (theX, theY));
			repaint();
		}
	}
	
	public void endDrag(Point2D aPoint, MouseEvent aE)
	{
		if (itsDraggedHandle != null) itsDraggedHandle.endMovement();
		itsDraggedHandle = null;
	}
	
	public Cursor getCursor()
	{
		return itsCurrentCursor;
	}
	
}
