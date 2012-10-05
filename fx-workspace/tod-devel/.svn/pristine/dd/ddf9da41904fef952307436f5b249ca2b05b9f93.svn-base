/*
 * Created on Jul 2, 2004
 */
package zz.csg.display.menu;

import java.awt.event.ActionEvent;

import javax.swing.Action;

import zz.csg.api.GraphicUtils;



/**
 * This handler represents the action that moves an object
 * towards the bottom of the rendering list of its parent
 * @author gpothier
 */
public class MoveBackwardHandler extends AbstractActionHandler
{
	private static MoveBackwardHandler INSTANCE = new MoveBackwardHandler();

	public static MoveBackwardHandler getInstance()
	{
		return INSTANCE;
	}

	private MoveBackwardHandler()
	{
		super("Enviar Atrás");
	}
	
	public boolean isAvailable(MenuRequestInfo aRequestInfo)
	{
		return GraphicUtils.canMoveBackward(aRequestInfo.getGraphicObject());
	}
	
	public Action createAction(final MenuRequestInfo aRequestInfo)
	{
		return new GraphicObjectAction (getName())
		{
			public void actionPerformed(ActionEvent aE)
			{
				GraphicUtils.moveBackward(aRequestInfo.getGraphicObject());
			}
		};
	}
}
