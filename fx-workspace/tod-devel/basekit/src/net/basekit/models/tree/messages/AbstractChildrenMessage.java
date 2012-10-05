/*
 * Created on Mar 15, 2004
 */
package net.basekit.models.tree.messages;

import net.basekit.messages.WidgetModelMessage;
import net.basekit.models.tree.TreeWidgetModel;

/**
 * Sent when children have been added to a {@link net.basekit.models.tree.TreeNodeWidgetModel}
 * @author gpothier
 */
public abstract class AbstractChildrenMessage extends WidgetModelMessage
{
	private int itsStartIndex;
	private int itsCount;
	private Object itsParent;

	public AbstractChildrenMessage (TreeWidgetModel aSource, Object aParent, int aStartIndex, int aCount)
	{
		super (aSource);
		itsParent = aParent;
		itsStartIndex = aStartIndex;
		itsCount = aCount;
	}
	
	public TreeWidgetModel getTreeNodeWidgetModel ()
	{
		return (TreeWidgetModel) getSource();
	}

	public Object getParent ()
	{
		return itsParent;
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
