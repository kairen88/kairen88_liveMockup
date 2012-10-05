/*
 * Created on May 30, 2004
 */
package zz.csg.display.tool.select;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import zz.csg.Log;
import zz.csg.Resources;
import zz.csg.api.GraphicNode;
import zz.csg.api.GraphicObjectContext;
import zz.csg.api.PickResult;
import zz.csg.api.edition.IGraphicObjectController;
import zz.csg.api.edition.IHandle;
import zz.utils.list.ICollection;
import zz.utils.list.ICollectionListener;
import zz.utils.properties.ISetProperty;


/**
 * This tool permits to select one or more graphic objects.
 * Selected graphic objects let the user manipulate them
 * through their {@link zz.csg.api.IGraphicObject#getController(GraphicObjectContext) controls}.
 * @author gpothier
 */
public class SelectionTool extends AbstractHandleTool 
implements ICollectionListener<GraphicNode>
{
	private ISetProperty<GraphicNode> itsSelectionProperty;
	
	/**
	 * Points to a controller that is in edition mode.
	 */
	private IGraphicObjectController itsEditingController;
	
	public SelectionTool(ISetProperty<GraphicNode> aSelectionProperty)
	{
		itsSelectionProperty = aSelectionProperty;
		itsSelectionProperty.addListener(this);
	}
	
	public ISetProperty<GraphicNode> getSelectionProperty()
	{
		return itsSelectionProperty;
	}
	
	protected Iterable<GraphicNode> getGraphicObjects()
	{
		return itsSelectionProperty;
	}
	
	/**
	 * Searches a selectable object at a given position
	 */
	private PickResult pick (Point2D aPoint)
	{
		return getEditionLayer().pick(aPoint);
	}

	protected Cursor getCursorFor(Point2D aPoint)
	{
		Cursor theCursor = super.getCursorFor(aPoint);
		if (theCursor == null)
		{
			PickResult theResult = pick(aPoint);
			if (theResult != null && theResult.getController() != null)
				theCursor = Resources.MOVE_CURSOR;
		}
		return theCursor;
	}
	
	public void mouseClicked(Point2D aPoint, MouseEvent aE)
	{
		PickResult theResult = pick(aPoint);
		
		switch (aE.getClickCount())
		{
			case 1:
				itsSelectionProperty.clear();
				if (theResult != null) itsSelectionProperty.add(theResult);
				break;
				
			case 2:
				if (theResult != null) 
				{
					itsEditingController = theResult.getController();
					if (itsEditingController != null) itsEditingController.startEditing();
				}
				break;
		}
	}
	
	public void mousePressed(Point2D aPoint, MouseEvent aE)
	{
		if (itsEditingController != null) 
		{
			itsEditingController.stopEditing();
			itsEditingController = null;
		}
		
		IHandle theHandle = findHandleAt(aPoint);
		if (theHandle == null)
		{
			PickResult theResult = pick(aPoint);
			if (theResult != null)
			{
				itsSelectionProperty.clear();
				itsSelectionProperty.add(theResult);
			}
		}
	}
	
	public void elementAdded(
			ICollection<GraphicNode> aCollection,
			GraphicNode aElement)
	{
		Log.GRAPHIC.debug("selection changed");
		repaint();
	}
	
	public void elementRemoved(
			ICollection<GraphicNode> aCollection,
			GraphicNode aElement)
	{
		Log.GRAPHIC.debug("selection changed");
		repaint();
	}
}
