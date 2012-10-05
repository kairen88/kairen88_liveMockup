/**
 * Created by IntelliJ IDEA.
 * User: g1
 * Date: 11 mars 2004
 * Time: 22:45:33
 * To change this template use File | Settings | File Templates.
 */
package net.basekit.models.label;

import java.awt.Font;

import net.basekit.models.WidgetModel;

/**
 * Model for an action that can be triggered.
 * @author gpothier
 */
public interface LabelWidgetModel extends WidgetModel
{
	/**
	 * @return A title for this action
	 */
	public String getTitle ();
	
	/**
	 * Returns the font to use to display the title.
	 */
	public Font getTitleFont ();

	/**
	 * @return A description for this action.
	 */
	public String getDescription ();

	/**
	 * @return Whether it is currently possible to trigger this action.
	 */
	public boolean isEnabled ();

	//TODO: support icons, key shortcuts, ...
}
