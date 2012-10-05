/*
 * Created on Apr 10, 2004
 */
package net.basekit.utils;

import java.util.Iterator;

import net.basekit.widgets.Widget;

/**
 * Prints the content of a widget tree.
 * @author gpothier
 */
public class WidgetPrinter
{
	public static String printWidget (Object aWidget)
	{
		StringBuffer theBuffer = new StringBuffer ();
		printWidget((Widget) aWidget, theBuffer, 0);
		return theBuffer.toString();
	}
	
	private static void printWidget (Widget aWidget, StringBuffer aBuffer, int aIndent)
	{
		printLine(aBuffer, aIndent, "Widget: "+aWidget);
		printLine(aBuffer, aIndent+1, "position: "+aWidget.getPosition());
		printLine(aBuffer, aIndent+1, "size: "+aWidget.getSize());
		printLine(aBuffer, aIndent+1, "children:");
		printLine(aBuffer, aIndent+1, "[");
		for (Iterator theIterator = aWidget.getWidgetsIterator(); theIterator.hasNext();)
		{
			Widget theWidget = (Widget) theIterator.next();
			printWidget(theWidget, aBuffer, aIndent+2);
		}
		printLine(aBuffer, aIndent+1, "]");
	}
	
	private static void printLine (StringBuffer aBuffer, int aIndent, String aText)
	{
		for (int i=0;i<aIndent;i++) aBuffer.append(' ');
		aBuffer.append(aText);
		aBuffer.append('\n');
	}
}
