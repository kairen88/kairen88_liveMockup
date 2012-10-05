/*
 * Created on Mar 15, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package zz.csg.api;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

import zz.csg.api.layout.ILayoutManager;
import zz.utils.properties.IListProperty;
import zz.utils.properties.PropertyId;

/**
 * A graphic container is a graphic object that has children graphic objects.
 * Those children can have a distinct coordinate system
 * (see {@link #TRANSFORM}).
 * @author gpothier
 */
public interface IGraphicContainer extends IGraphicObject
{	
	public static final PropertyId<List<IGraphicObject>> CHILDREN = 
		new PropertyId<List<IGraphicObject>> ("children", true);

	/**
	 * Sets a layout manager that will handle the positioning of 
	 * this container's children
	 */
	public void setLayoutManager (ILayoutManager aLayoutManager);
	
	/**
	 * The list of children of this container.
	 * Elements of the list are instances of {@link IGraphicObject}
	 */
	public IListProperty<IGraphicObject> pChildren ();
	
	/**
	 * Returns the child of this container that has the specified name.
	 */
	public IGraphicObject getChildByName (String aName);
	
	/**
	 * Returns the bounds of the specified graphic object in the 
	 * coordinate system of this container.
	 * @param aGraphicObject A graphic object that pertains to the
	 * hierarchy of this container.
	 */
	public Rectangle2D getDescendantBounds(IGraphicObject aGraphicObject);
}
