/**
 * Created by IntelliJ IDEA.
 * User: g1
 * Date: 11 mars 2004
 * Time: 20:59:37
 * To change this template use File | Settings | File Templates.
 */
package net.basekit.widgets;

import zz.utils.Formatter;

/**
 * Interface for objects that use a {@link Formatter} and provide getters and setters for it.
 * @author gpothier
 */
public interface Formatted
{
	public Formatter getFormatter ();
	public void setFormatter (Formatter aFormatter);
}
