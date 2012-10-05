/*
 * Created on Mar 22, 2004
 */
package net.basekit.models.tree.messages;

import net.basekit.messages.WidgetModelMessage;
import net.basekit.models.WidgetModel;
import net.basekit.models.tree.TreeWidgetModel;

/**
 * Fired when a node is expanded or collapsed.
 * @author gpothier
 */
public class NodeStateChangedMessage extends WidgetModelMessage
{
	private Object itsNode;
	
	/**
	 * If true, the node has just been expanded. Otherwise it has just been collapsed.
	 */
	private boolean itsExpanded;
	
	public NodeStateChangedMessage (TreeWidgetModel aSource, Object aNode, boolean aExpanded)
	{
		super(aSource);
		itsNode = aNode;
		itsExpanded = aExpanded;
	}
	
	public TreeWidgetModel getStateModel ()
	{
		return (TreeWidgetModel) getSource();
	}
	
	public Object getNode ()
	{
		return itsNode;
	}
	
	public boolean isExpanded ()
	{
		return itsExpanded;
	}
}
