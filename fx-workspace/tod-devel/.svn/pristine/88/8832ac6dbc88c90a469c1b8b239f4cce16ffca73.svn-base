/*
 * Created on Jul 2, 2004
 */
package zz.csg.display.menu;

import java.awt.event.ActionEvent;

import javax.swing.Action;

import zz.csg.api.GraphicUtils;



/**
 * This handler represents the action that moves an object
 * towards the top of the rendering list of its parent
 * @author gpothier
 */
public class MoveForwardHandler extends AbstractActionHandler
{
	private static MoveForwardHandler INSTANCE = new MoveForwardHandler();

	public static MoveForwardHandler getInstance()
	{
		return INSTANCE;
	}

	private MoveForwardHandler()
	{
		super ("Trae Adelante");
	}
	
	public boolean isAvailable(MenuRequestInfo aRequestInfo)
	{
		return GraphicUtils.canMoveForward(aRequestInfo.getGraphicObject());
	}
	
	public Action createAction(final MenuRequestInfo aRequestInfo)
	{
		return new GraphicObjectAction (getName())
		{
			public void actionPerformed(ActionEvent aE)
			{
				GraphicUtils.moveForward(aRequestInfo.getGraphicObject());
			}
			
		};
	}
}
