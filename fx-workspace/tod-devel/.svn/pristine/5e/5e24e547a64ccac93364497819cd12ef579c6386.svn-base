/*
 * Created on Mar 22, 2004
 */
package net.basekit.models.tree;

import net.basekit.models.AbstractWidgetModel;
import net.basekit.models.tree.messages.ChildrenAddedMessage;
import net.basekit.models.tree.messages.ChildrenRemovedMessage;
import net.basekit.models.tree.messages.NodeStateChangedMessage;

/**
 * Provides message firing methods.
 * @author gpothier
 */
public abstract class AbstractTreeWidgetModel extends AbstractWidgetModel implements TreeWidgetModel
{
	
	protected void fireNodeStateChanged (Object aNode, boolean aExpanded)
	{
		fire (new NodeStateChangedMessage (this, aNode, aExpanded));
	}
	
	protected void fireChildrenAdded (Object aParent, int aStartIndex, int aCount)
	{
		fire (new ChildrenAddedMessage (this, aParent, aStartIndex, aCount));
	}
	
	protected void fireChildrenRemoved (Object aParent, int aStartIndex, int aCount)
	{
		fire (new ChildrenRemovedMessage (this, aParent, aStartIndex, aCount));
	}

	public int getIndexOf (Object aNode)
	{
		IndexFinder theFinder = new IndexFinder (aNode);
		DepthFirstTraversal.getInstance().traverse(this, theFinder);
		return theFinder.getIndex();
	}

	public Object getNodeAt (int aIndex)
	{
		return new ElementFinder (this, aIndex)
		{
			public boolean visitChildren (Object aNode)
			{
				return isNodeExpanded(aNode);
			}
		}.getResult();	
	}
	
	public int getVisibleNodesCount ()
	{
		NodeCounter theCounter = new NodeCounter ();
		DepthFirstTraversal.getInstance().traverse(this, theCounter);
		return theCounter.getCount();
	}
	
	public void toggleNodeExpansion (Object aNode)
	{
		setNodeExpanded(aNode, ! isNodeExpanded(aNode));
	}

	
	private class NodeCounter extends TreeVisitor
	{
		private int itsCount = 0;
		
		public boolean visit (Object aNode)
		{
			itsCount++;
			return isNodeExpanded(aNode);
		}
		
		public int getCount ()
		{
			return itsCount;
		}
	}

	private class IndexFinder extends TreeVisitor
	{
		private int itsIndex = 0;
		private Object itsSearchedNode;
		
		public IndexFinder (Object aSearchedNode)
		{
			itsSearchedNode = aSearchedNode;
		}

		public boolean visit (Object aNode)
		{
			if (aNode == itsSearchedNode) stop ();
			itsIndex++;
			return isNodeExpanded(aNode);
		}
		
		public int getIndex ()
		{
			return itsIndex;
		}
	}

	

	/**
	 * Abstract class that permits to retrieve an element in the tree based on
	 * its depth-first traversal order.
	 */
	public static abstract class ElementFinder extends TreeVisitor
	{
		private int itsIndex;
		private Object itsResult = null;
		
		public ElementFinder (TreeWidgetModel aTreeModel, int aIndex)
		{
			assert aIndex >= 0;
			itsIndex = aIndex;
			DepthFirstTraversal.getInstance().traverse(aTreeModel, this);
		}

		public boolean visit (Object aNode)
		{
			if (itsIndex == 0) 
			{
				itsResult = aNode;
				stop ();
			}
			else itsIndex--;
			
			return visitChildren(aNode);
		}

		/**
		 * Indicates if the children of the specified node should be included in the
		 * traversal.
		 */
		public abstract boolean visitChildren (Object aNode);
		
		public Object getResult ()
		{
			return itsResult;
		}
	}
	

}
