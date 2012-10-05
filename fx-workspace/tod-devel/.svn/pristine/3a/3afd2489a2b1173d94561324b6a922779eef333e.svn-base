/**
 * Created by IntelliJ IDEA.
 * User: g1
 * Date: 11 mars 2004
 * Time: 20:52:58
 * To change this template use File | Settings | File Templates.
 */
package net.hddb.views;

import zz.utils.Formatter;

/**
 * Formatter for views, view chains and view classes.
 * @author gpothier
 */
public class ViewFormatter implements Formatter
{
	private static final ViewFormatter INSTANCE = new ViewFormatter ();

	public static ViewFormatter getInstance ()
	{
		return INSTANCE;
	}

	private ViewFormatter ()
	{
	}
	
	public String getText (Object aObject)
	{
		if (aObject instanceof HDView)
		{
			HDView theView = (HDView) aObject;
			return theView.getViewClass ().getName ();
		}
		else if (aObject instanceof HDViewChain)
		{
			HDViewChain theViewChain = (HDViewChain) aObject;
			return theViewChain.getViewClass ().getName ();
		}
		else if (aObject instanceof HDViewClass)
		{
			HDViewClass theViewClass = (HDViewClass) aObject;
			return theViewClass.getName ();
		}
		else return ""+aObject;
	}
}
