/*
 * Created on Dec 21, 2004
 */
package zz.waltz.api.action;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPopupMenu;

import zz.utils.ui.popup.PopupUtils;

/**
 * An action that triggers a popup menu.
 * A useful utility for creating popup menus is {@link zz.waltz.api.menu.MenuBuilder}
 * @author gpothier
 */
public class PopupMenuAction extends DefaultActionModel
{
	private JPopupMenu itsPopupMenu;
	private int itsPreferredDirection;
	
	public PopupMenuAction(String aName, JPopupMenu aPopupMenu, int aPreferredDirection)
	{
		super(aName);
		itsPopupMenu = aPopupMenu;
		itsPreferredDirection = aPreferredDirection;
	}
	
	public void performed(JComponent aComponent)
	{
		PopupUtils.showPopupMenu(aComponent, itsPreferredDirection, itsPopupMenu);
	}

}
