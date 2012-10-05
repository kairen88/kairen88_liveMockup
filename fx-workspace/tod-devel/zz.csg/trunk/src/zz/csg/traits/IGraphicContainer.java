/*
 * Created on Mar 15, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package zz.csg.traits;

import java.awt.geom.Point2D;
import java.util.List;

import zz.utils.properties.IListProperty;
import zz.utils.properties.PropertyId;

/**
 * A graphic container is a graphic object that has children graphic objects.
 * Those children can have a distinct coordinate system
 * (see {@link #TRANSFORM}).
 * @author gpothier
 */
interface IGraphicContainer extends IGraphicObject
{	
	public static final PropertyId<List<IGraphicObject>> CHILDREN = 
		new PropertyId<List<IGraphicObject>> ("children", true);

	/**
	 * The list of children of this container.
	 * Elements of the list are instances of {@link IGraphicObject}
	 */
	public IListProperty<IGraphicObject> pChildren ();
	
	
	/**
	 * Transforms the given point into the children's coordinate system
	 * @param aPoint A point in this container's coordinate system
	 */
	public Point2D localToChildren (Point2D aPoint);
	
	/**
	 * The inverse of {@link #localToChildren(Point2D)}.
	 */
	public Point2D childrenToLocal (Point2D aPoint);
	
	/**
	 * Returns the child of this container that has the specified name.
	 */
	public IGraphicObject getChildByName (String aName);
}
