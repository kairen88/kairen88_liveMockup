/*
 * Created on Jun 2, 2005
 */
package zz.csg.display.menu;

import java.util.List;

import javax.swing.Action;

/**
 * Specifies the interface to implement to serve context menu requests
 * @author gpothier
 */
public interface IMenuRequestHandler
{
	/**
	 * Returns all the actions that are available for the given request info.
	 */
	public List<Action> createActions (MenuRequestInfo aRequestInfo);

}
