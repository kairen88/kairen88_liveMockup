/**
 * Created by IntelliJ IDEA.
 * User: g1
 * Date: 11 mars 2004
 * Time: 20:37:44
 * To change this template use File | Settings | File Templates.
 */
package net.basekit.utils;

import zz.utils.Formatter;

/**
 * Singleton formatter that simply returns the result of the invocation of
 * the target object's toString method.
 * @author gpothier
 */
public class ToStringFormatter implements Formatter
{
	private static final ToStringFormatter INSTANCE = new ToStringFormatter ();

	public static ToStringFormatter getInstance ()
	{
		return INSTANCE;
	}

	private ToStringFormatter ()
	{
	}

	public String getText (Object aObject)
	{
		return ""+aObject;
	}
}
