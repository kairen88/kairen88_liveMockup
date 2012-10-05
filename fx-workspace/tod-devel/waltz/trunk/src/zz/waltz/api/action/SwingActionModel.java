/*
 * Created on Dec 22, 2004
 */
package zz.waltz.api.action;

import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JComponent;


/**
 * Simplifies the construction of a Waltz action for
 * legacy code that uses swing {@link javax.swing.Action actions}.
 * @author gpothier
 */
public class SwingActionModel extends DefaultActionModel
{
	private Action itsAction;
	
	public SwingActionModel(Action aAction)
	{
		itsAction = aAction;
		
		pName.set((String) itsAction.getValue(Action.NAME));
		pIcon.set((ImageIcon) itsAction.getValue(Action.SMALL_ICON));
	}
	
	public void performed(JComponent aComponent)
	{
		ActionEvent theEvent = new ActionEvent(aComponent, ActionEvent.ACTION_PERFORMED, null);
		itsAction.actionPerformed(theEvent);
	}
}
