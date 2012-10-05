/**
 * Created by IntelliJ IDEA.
 * User: g1
 * Date: 11 mars 2004
 * Time: 22:34:22
 * To change this template use File | Settings | File Templates.
 */
package net.basekit.models.list.messages;

import net.basekit.messages.WidgetModelMessage;
import net.basekit.models.list.ListContentWidgetModel;

/**
 * Base message class for list messages that use intervals.
 * @author gpothier
 */
public class AbstractListIntervalMessage extends WidgetModelMessage
{
	private int itsStartIndex;
	private int itsCount;

	public AbstractListIntervalMessage (ListContentWidgetModel aSource, int aStartIndex, int aCount)
	{
		super (aSource);
		itsStartIndex = aStartIndex;
		itsCount = aCount;
	}

	public ListContentWidgetModel getListWidgetModel ()
	{
		return (ListContentWidgetModel) getSource ();
	}

	public int getStartIndex ()
	{
		return itsStartIndex;
	}

	public int getCount ()
	{
		return itsCount;
	}
}
