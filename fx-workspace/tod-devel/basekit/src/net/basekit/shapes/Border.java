/**
 * Created by IntelliJ IDEA.
 * User: g1
 * Date: 9 mars 2004
 * Time: 11:40:18
 * To change this template use File | Settings | File Templates.
 */
package net.basekit.shapes;

import net.basekit.widgets.Margins;

/**
 * A shape that can be used as a border for a wiget.
 */
public interface Border extends Shape
{
	/**
	 * Returns the margins a widget should use is it is to use this border.
	 * @return
	 */
	public Margins getMargins ();
}
