/*
 * Created on Jul 2, 2004
 */
package zz.csg.display.menu;

import java.awt.event.ActionEvent;

import javax.swing.Action;

import zz.csg.api.GraphicUtils;



/**
 * This handler represents the action that moves an object
 * to the bottom of the rendering list of its parent
 * @author gpothier
 */
public class MoveToBackHandler extends AbstractActionHandler
{
	private static MoveToBackHandler INSTANCE = new MoveToBackHandler();

	public static MoveToBackHandler getInstance()
	{
		return INSTANCE;
	}

	private MoveToBackHandler()
	{
		super ("Llevar al Fondo");
	}
	
	public boolean isAvailable(MenuRequestInfo aRequestInfo)
	{
		return GraphicUtils.canMoveToBack(aRequestInfo.getGraphicObject());
	}
	
	public Action createAction(final MenuRequestInfo aRequestInfo)
	{
		return new GraphicObjectAction (getName())
		{
			public void actionPerformed(ActionEvent aE)
			{
				GraphicUtils.moveToBack(aRequestInfo.getGraphicObject());
			}
			
		};
	}
}
