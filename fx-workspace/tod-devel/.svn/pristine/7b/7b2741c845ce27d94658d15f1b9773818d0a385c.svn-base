/**
 * Created by IntelliJ IDEA.
 * User: g1
 * Date: 9 mars 2004
 * Time: 10:46:29
 * To change this template use File | Settings | File Templates.
 */
package net.basekit.utils;

import java.util.HashSet;
import java.util.Set;

/**
 * Facility to log messages. It is possible to activate or desactivate logging for
 * categories of messages. Activated categories will have their messages printed to
 * System.out.
 * @author gpothier
 */
public class Logger
{
	private static Set itsActiveCategories = new HashSet ();

	/**
	 * Activates or desactivates a category.
	 */
	public static void setActive (Category aCategory, boolean aActive)
	{
		if (aActive) itsActiveCategories.add (aCategory);
		else itsActiveCategories.remove (aCategory);
	}

	private static boolean isActive (Category aCategory)
	{
		return itsActiveCategories.contains (aCategory);
	}

	/**
	 * Logs a message to the standard output if the category is activated.
	 * @return Always return true so that it can be used with assert:
	 * <code>assert Logger.log (...)</code>
	 */
	public static boolean log (Category aCategory, String aMessage)
	{
		if (isActive (aCategory)) System.out.println ("["+aCategory.getName ()+"] "+aMessage);
		return true;
	}

	/**
	 * Logging category. A category is specified for each logged message;
	 * categories can be enabled or disabled.
	 */
	public static class Category
	{
		private String itsName;

		public Category (String aName)
		{
			itsName = aName;
		}

		public String getName ()
		{
			return itsName;
		}
	}
}
