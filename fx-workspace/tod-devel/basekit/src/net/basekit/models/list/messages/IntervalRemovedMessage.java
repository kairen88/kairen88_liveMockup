/**
 * Created by IntelliJ IDEA.
 * User: g1
 * Date: 11 mars 2004
 * Time: 22:34:22
 * To change this template use File | Settings | File Templates.
 */
package net.basekit.models.list.messages;

import net.basekit.models.list.ListContentWidgetModel;

/**
 * Sent when elements have been removed from a {@link net.basekit.models.list.ListContentWidgetModel}
 * @author gpothier
 */
public class IntervalRemovedMessage extends AbstractListIntervalMessage
{

	public IntervalRemovedMessage (ListContentWidgetModel aSource, int aStartIndex, int aCount)
	{
		super(aSource, aStartIndex, aCount);
	}
}
