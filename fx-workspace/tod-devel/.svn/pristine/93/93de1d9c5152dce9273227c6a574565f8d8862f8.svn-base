/*
 * Created on Jul 2, 2004
 */
package zz.csg.display.menu;

import java.awt.geom.Point2D;

import javax.swing.Action;

import zz.csg.api.GraphicUtils;
import zz.csg.api.IGraphicObject;
import zz.utils.ItemAction;



/**
 * Base class for action handlers. They are capable of determining if a
 * particular action is available for a graphic object, and if so
 * it can generate an adequate action object.
 * @author gpothier
 */
public abstract class AbstractActionHandler
{
	private String itsName;
	
	public AbstractActionHandler(String aName)
	{
		itsName = aName;
	}
	/**
	 * Determines if the action represented by this handler is available
	 * on the specified graphic object.
	 * <p>
	 * There are helper methods for implementing this method.
	 * @param aRequestInfo TODO
	 * @see #hasParent(IGraphicObject)
	 * @see #isChild(IGraphicObject)
	 * @see #isReserved(IGraphicObject)
	 */
	public abstract boolean isAvailable (MenuRequestInfo aRequestInfo);
	
	/**
	 * Creates an action object that can perform the action represented
	 * by this handler on the specified graphic object.
	 * <p>
	 * See {@link SimpleMenuRequestHandler#createActions(Point2D, IGraphicObject, ISelectionProvider)}
	 * for details about parameters.
	 * @param aRequestInfo TODO
	 */
	public abstract Action createAction (MenuRequestInfo aRequestInfo);
	
	/**
	 * Devuelve el nombre del Handler. 
	 */
	public String getName ()
	{
		return itsName;
	}
	
	/**
	 * Indicates if the specified graphic object has a parent.
	 */
	protected static boolean hasParent (IGraphicObject aGraphicObject)
	{
		return aGraphicObject.getParent() != null;
	}
	
	/**
	 * Indicates if the specified graphic object is reserved
	 * (has a reserved name).
	 */
	protected static boolean isReserved (IGraphicObject aGraphicObject)
	{
		return GraphicUtils.isReserved(aGraphicObject);
	}
	
	/**
	 * Helps to implement graphic object actions.
	 * @author gpothier
	 */
	protected abstract static class GraphicObjectAction extends ItemAction
	{
		
		public GraphicObjectAction(String aTitle)
		{
			setTitle(aTitle);
		}
	}
}
