/*
 * Created on 31-may-2004
 */
package zz.csg.display.tool.create;

import java.awt.Color;
import java.awt.Cursor;

import zz.csg.Resources;
import zz.csg.api.GraphicNode;
import zz.csg.api.IGraphicContainer;
import zz.csg.api.IGraphicObject;
import zz.csg.api.figures.IShape;
import zz.csg.display.tool.EditorTool;
import zz.utils.list.IList;
import zz.utils.properties.PropertyId;



/**
 * Abstract tool that serves to implement tools that
 * permit to create graphic objects
 * <p>
 * It delegates the creation of the object to subclasses
 * @author gpothier
 */
public abstract class AbstractCreateTool extends EditorTool
{
	public Cursor getCursor()
	{
		return Resources.CROSS_CURSOR;
	}

	/**
	 * Creates a graphic object and adds it
	 * to the target container.
	 * <p>
	 * The object is created using {@link #createObject()}.
	 * It is given default properties such as color, thickness... (if they
	 * are available).
	 */
	protected IGraphicObject setupObject ()
	{
		IGraphicObject theGraphicObject = createObject();
		
		// Affect default colors to the object.
		if (theGraphicObject instanceof IShape)
		{
			IShape theShape = (IShape) theGraphicObject;
			theShape.pStrokePaint().set(Color.BLACK);
			theShape.pFillPaint().set(Color.WHITE);
			theShape.pStrokeWidth().set(1.0);
		}
		
		// Add the object to the target container
		addToContainer(theGraphicObject);
		return theGraphicObject;
	}

	private IGraphicContainer getTargetContainer()
	{
		GraphicNode theTargetNode = getTargetNode();
		IGraphicContainer theContainer = 
			(IGraphicContainer) theTargetNode.getGraphicObject();

		return theContainer;
	}
	
	/**
	 * Adds the specified graphic object to this tool's target container.
	 */
	protected void addToContainer (IGraphicObject aGraphicObject)
	{
		IList<IGraphicObject> theChildren = getTargetContainer().pChildren();
		theChildren.add(aGraphicObject);
	}
	
	/**
	 * Removes the graphic object from the container. 
	 * This is useful for cancelling the creation if {@link #setupObject()}
	 * or {@link #addToContainer(IGraphicObject)} have been used.
	 */
	protected void removeFromContainer(IGraphicObject aGraphicObject)
	{
		IList<IGraphicObject> theChildren = getTargetContainer().pChildren();
		theChildren.remove(aGraphicObject);
	}
	
	/**
	 * Subclasses must implement this method to return the object
	 * that will be added to the container as a result of the use
	 * of this tool.
	 * Subclasses that override {@link #setupObject()} don't necesarilly have
	 * to implement this method.
	 */
	protected IGraphicObject createObject ()
	{
		return null;
	}
	
}
