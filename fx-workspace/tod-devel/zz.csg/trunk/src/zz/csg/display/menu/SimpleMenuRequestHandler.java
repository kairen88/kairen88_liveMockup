/*
 * Created on Jul 2, 2004
 */
package zz.csg.display.menu;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;


/**
 * Permits to create a context menu for a {@link zz.csg.display.menu.MenuRequestInfo}.
 * The available actions for a given request are obtained from a list of {@link zz.csg.display.menu.AbstractActionHandler}
 * @author gpothier
 */
public class SimpleMenuRequestHandler implements IMenuRequestHandler
{
	private List<AbstractActionHandler> itsHandlers;
	
	public SimpleMenuRequestHandler(List<AbstractActionHandler> aHandlers)
	{
		itsHandlers = aHandlers;
	}

	public List<AbstractActionHandler> getHandlers()
	{
		return itsHandlers;
	}

	public void setHandlers(List<AbstractActionHandler> aHandlers)
	{
		itsHandlers = aHandlers;
	}

	public List<Action> createActions (MenuRequestInfo aRequestInfo)
	{
		List<Action> theResult = new ArrayList<Action> ();
		
		for (AbstractActionHandler theHandler : itsHandlers)
		{
			if (theHandler.isAvailable(aRequestInfo))
			{
				Action theAction = theHandler.createAction(aRequestInfo);
				theResult.add (theAction);
			}
		}
		
		return theResult;
	}
}
