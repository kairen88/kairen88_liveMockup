/*
 * Created on 10-sep-2004
 */
package zz.csg.display.menu;

import java.awt.geom.Point2D;

import zz.csg.api.IGraphicContainer;
import zz.csg.api.IGraphicObject;
import zz.utils.properties.ISetProperty;


/**
 * Aggregates all the information needed to setup a context
 * menu.
 * @author gpothier
 */
public class MenuRequestInfo
{
	/**
	 * The root graphic container of the editor that requests the menu.
	 */
	private IGraphicContainer itsRootContainer;
	
	/**
	 * Coordinates of the requested origin of the menu.
	 */
	private Point2D itsPoint;
	
	/**
	 * The graphic object at the requested origin.
	 */
	private IGraphicObject itsGraphicObject; 
	
	/**
	 * The selection provider of the editor that requests the menu.
	 */
	private ISetProperty itsSelectionProperty;
	
	
	public MenuRequestInfo(
			IGraphicContainer aRootContainer, 
			Point2D aPoint, 
			IGraphicObject aGraphicObject,
			ISetProperty aSelectionProperty)
	{
		itsRootContainer = aRootContainer;
		itsPoint = aPoint;
		itsGraphicObject = aGraphicObject;
		itsSelectionProperty = aSelectionProperty;
	}
	
	public IGraphicObject getGraphicObject()
	{
		return itsGraphicObject;
	}
	
	public Point2D getPoint()
	{
		return itsPoint;
	}
	
	public IGraphicContainer getRootContainer()
	{
		return itsRootContainer;
	}
	
	public ISetProperty getSelectionProperty()
	{
		return itsSelectionProperty;
	}
}
