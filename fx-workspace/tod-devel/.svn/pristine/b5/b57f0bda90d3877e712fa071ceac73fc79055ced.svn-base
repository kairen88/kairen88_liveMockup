/*
 * Created on 07-oct-2004
 */
package zz.csg.api;

import zz.utils.properties.IRWProperty;

/**
 * @author gpothier
 */
public interface IGraphicObjectListener
{
	/**
	 * This method is called whenever a child is added to a node.
	 */
	public void childAdded (IGraphicContainer aContainer, IGraphicObject aChild);
	
	/**
	 * This method is called whenever a child is removed from a node
	 */
	public void childRemoved (IGraphicContainer aContainer, IGraphicObject aChild);
	
	/**
	 * Called whenever a property of a graphic object changed
	 * @param aProperty The property that changed.
	 */
	public void changed (IGraphicObject aObject, IRWProperty aProperty);

}
