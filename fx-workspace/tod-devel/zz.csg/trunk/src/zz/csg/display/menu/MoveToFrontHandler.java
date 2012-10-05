/*
 * Created on Jul 2, 2004
 */
package zz.csg.display.menu;

import java.awt.event.ActionEvent;

import javax.swing.Action;

import zz.csg.api.GraphicUtils;



/**
 * This handler represents the action that moves an object
 * to the top of the rendering list of its parent
 * @author gpothier
 */
public class MoveToFrontHandler extends AbstractActionHandler
{
	private static MoveToFrontHandler INSTANCE = new MoveToFrontHandler();

	public static MoveToFrontHandler getInstance()
	{
		return INSTANCE;
	}

	private MoveToFrontHandler()
	{
		super ("Traer al Frente");
	}
	
	public boolean isAvailable(MenuRequestInfo aRequestInfo)
	{
		return GraphicUtils.canMoveToFront(aRequestInfo.getGraphicObject());
	}
	
	public Action createAction(final MenuRequestInfo aRequestInfo)
	{
		return new GraphicObjectAction (getName())
		{
			public void actionPerformed(ActionEvent aE)
			{
				GraphicUtils.moveToFront(aRequestInfo.getGraphicObject());
			}
		};
	}
}
