/*
 * Created on Mar 15, 2004
 */
package net.basekit.models.tree;


/**
 * A default implementation of {@link net.basekit.models.tree.TreeWidgetModel}
 * It assumes that all nodes in the tree are instances of 
 * {@link net.basekit.models.tree.ExpandableTreeNode}
 * @author gpothier
 */
public class DefaultTreeWidgetModel extends AbstractTreeWidgetModel 
{
	private ExpandableTreeNode itsRootNode;
	
	public DefaultTreeWidgetModel (ExpandableTreeNode aRootNode)
	{
		itsRootNode = aRootNode;
	}

	public Object getRootNode ()
	{
		return itsRootNode;
	}
	
	public Object getParent (Object aNode)
	{
		ExpandableTreeNode theNode = (ExpandableTreeNode) aNode;
		return theNode.getParent();
	}
	
	public int getDepth (Object aNode)
	{
		int theDepth = -1;
		while (aNode != null)
		{
			theDepth++;
			aNode = getParent(aNode);
		}
		return theDepth;
	}

	
	public Object getChild (Object aParent, int aIndex)
	{
		ExpandableTreeNode theParent = (ExpandableTreeNode) aParent;
		return theParent.getChildAt(aIndex);
	}
	
	public int getChildrenCount (Object aParent)
	{
		ExpandableTreeNode theParent = (ExpandableTreeNode) aParent;
		return theParent.getChildrenCount();
	}

	
	public void setRootNode (ExpandableTreeNode aRootNode)
	{
		itsRootNode = aRootNode;
	}

	public boolean isNodeExpanded (Object aNode)
	{
		return ((ExpandableTreeNode) aNode).isExpanded();
	}
	
	public void setNodeExpanded (Object aNode, boolean aExpanded)
	{
		if (isNodeExpanded(aNode) != aExpanded)
		{
			((ExpandableTreeNode) aNode).setExpanded(aExpanded);
			fireNodeStateChanged(aNode, aExpanded);
		}
	}


	public Object getUserObject (Object aNode)
	{
		return ((ExpandableTreeNode)aNode).getUserObject();
	}

	
}
