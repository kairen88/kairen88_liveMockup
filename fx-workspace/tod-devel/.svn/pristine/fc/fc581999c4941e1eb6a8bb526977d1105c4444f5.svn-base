/*
 * Created on Mar 24, 2004
 */
package net.basekit.widgets.tree;

import net.basekit.Message;
import net.basekit.Observer;
import net.basekit.models.list.AbstractListContentWidgetModel;
import net.basekit.models.tree.TreeWidgetModel;
import net.basekit.models.tree.messages.NodeStateChangedMessage;


/**
 * Wraps a tree model into a list model.
 * 
 * @author gpothier
 */
public class TreeListWrapperModel extends AbstractListContentWidgetModel implements Observer
{
	private TreeWidgetModel itsModel;

	public TreeListWrapperModel ()
	{
		this (null);
	}
	
	public TreeListWrapperModel (TreeWidgetModel aModel)
	{
		itsModel = aModel;
	}

	public TreeWidgetModel getModel ()
	{
		return itsModel;
	}
	
	public void setModel (TreeWidgetModel aModel)
	{
		itsModel = aModel;
	}
	
	public int getSize ()
	{
		return getModel() != null ? getModel ().getVisibleNodesCount() : 0;
	}

	public Object getElementAt (int aIndex)
	{
		return getModel ().getNodeAt(aIndex);
	}
	
	/**
	 * Handles expanding/collapsing nodes.
	 */
	public void processMessage (Message aMessage)
	{
		if (aMessage instanceof NodeStateChangedMessage)
		{
			NodeStateChangedMessage theMessage = (NodeStateChangedMessage) aMessage;
			Object theNode = theMessage.getNode();
			
			int theCount = getModel().getChildrenCount(theNode);
			int theIndex = getModel ().getIndexOf(theNode);
			
			if (theMessage.isExpanded()) fireIntervalAdded(theIndex+1, theCount);
			else fireIntervalRemoved(theIndex+1, theCount);
		}
	}

}