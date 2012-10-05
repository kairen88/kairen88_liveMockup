/*
 * Created on Jul 2, 2004
 */
package zz.csg.display.menu;

import java.awt.event.ActionEvent;

import javax.swing.Action;

import zz.csg.api.IGraphicContainer;
import zz.csg.api.IGraphicObject;
import zz.utils.list.IList;



/**
 * This handler represents the action that removes an object
 * from its parent.
 * @author gpothier
 */
public class DeleteObjectHandler extends AbstractActionHandler
{
	private static DeleteObjectHandler INSTANCE = new DeleteObjectHandler();

	public static DeleteObjectHandler getInstance()
	{
		return INSTANCE;
	}

	private DeleteObjectHandler()
	{
		super("Eliminar");
	}
	
	/**
	 * Here we setup the conditions to allow or deny the deletion of 
	 * an object:
	 * <li>It must have a parent
	 * <li>It must not be a child graphic object, because the associated structure
	 * must also be deleted
	 * <li>It must not have a reserved name.
	 */
	public boolean isAvailable(MenuRequestInfo aRequestInfo)
	{
		IGraphicObject theGraphicObject = aRequestInfo.getGraphicObject();
		
		return hasParent(theGraphicObject)
			&& ! isReserved(theGraphicObject);
	}

	public Action createAction(final MenuRequestInfo aRequestInfo)
	{
		return new GraphicObjectAction (getName())
		{
			public void actionPerformed(ActionEvent aE)
			{
				IGraphicObject theGraphicObject = aRequestInfo.getGraphicObject();

				IGraphicContainer theParent = theGraphicObject.getParent();
				IList<IGraphicObject> theChildren = theParent.pChildren();
				theChildren.remove(theGraphicObject);
				aRequestInfo.getSelectionProperty().clear();
			}
		};
	}
	
}
