/*
 * Created on Mar 15, 2004
 */
package net.basekit.models.list;

import net.basekit.models.AbstractWidgetModel;
import net.basekit.models.list.messages.IntervalAddedMessage;
import net.basekit.models.list.messages.IntervalRemovedMessage;

/**
 * An abstract utility classes for classes that need to implement
 * {@link net.basekit.models.list.ListContentWidgetModel}.
 * Provides needed fireXxx methods.
 * @author gpothier
 */
public abstract class AbstractListContentWidgetModel extends AbstractWidgetModel implements ListContentWidgetModel
{

	protected void fireIntervalAdded (int aIndex, int aCount)
	{
		fire (new IntervalAddedMessage (this, aIndex, aCount));
	}

	protected void fireIntervalRemoved (int aIndex, int aCount)
	{
		fire (new IntervalRemovedMessage (this, aIndex, aCount));
	}
}
