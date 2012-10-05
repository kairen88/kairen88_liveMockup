/*
 * Created on Mar 15, 2004
 */
package net.basekit.models.tree.messages;

import net.basekit.models.tree.TreeWidgetModel;

/**
 * Sent when children have been removed from a {@link net.basekit.models.tree.TreeNodeWidgetModel}
 * @author gpothier
 */
public class ChildrenRemovedMessage extends AbstractChildrenMessage
{

	public ChildrenRemovedMessage (TreeWidgetModel aSource, Object aParent, int aStartIndex, int aCount)
	{
		super(aSource, aParent, aStartIndex, aCount);
	}
}
