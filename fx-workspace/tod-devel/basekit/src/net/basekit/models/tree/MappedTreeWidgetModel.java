/*
 * Created on Apr 10, 2004
 */
package net.basekit.models.tree;

import java.util.HashMap;
import java.util.Map;

/**
 * This implementation of {@link net.basekit.models.tree.TreeWidgetModel} is useful
 * for cases where the whole tree is not known in advance and where nodes are not
 * instances of {@link net.basekit.models.tree.ExpandableTreeNode}.
 * This model keeps track of node information in a map.
 * @author gpothier
 */
public abstract class MappedTreeWidgetModel extends AbstractTreeWidgetModel
{
	private Map itsNodesMap = new HashMap ();

	/**
	 * Returns the {@link NodeInfo} corresponding to the specified node.
	 */
	protected NodeInfo getNodeInfo (Object aNode)
	{
		return (NodeInfo) itsNodesMap.get (aNode);
	}
	
	private void putNodeInfo (Object aNode, NodeInfo aNodeInfo)
	{
		itsNodesMap.put (aNode, aNodeInfo);
	}
	
	/**
	 * Lets the subclass instanciate an adequate NodeInfo.
	 * Default instanciates a {@link DefaultNodeInfo}.
	 */
	protected NodeInfo createNodeInfo (Object aNode, int aDepth, Object aParent)
	{
		return new DefaultNodeInfo (aDepth, aParent);
	}
	
	/**
	 * Corresponds to the {@link #getChild(Object, int)} method.
	 */
	protected abstract Object getChild0 (Object aParent, int aIndex);
	
	/**
	 * Corresponds to {@link #getRootNode()}
	 */
	protected abstract Object getRootNode0 ();
	
	/**
	 * This method is overridden so that it manages the node info
	 */
	public final Object getRootNode ()
	{
		Object theRootNode = getRootNode0();
		NodeInfo theNodeInfo = createNodeInfo(theRootNode, 0, null);
		putNodeInfo(theRootNode, theNodeInfo);
		return theRootNode;
	}
	
	/**
	 * This method is overridden so that it manages the node info
	 */
	public final Object getChild (Object aParent, int aIndex)
	{
		NodeInfo theNodeInfo = getNodeInfo(aParent);
		assert theNodeInfo != null;
		Object theChild = getChild0(aParent, aIndex);
		NodeInfo theChildNodeInfo = createNodeInfo(theChild, theNodeInfo.getDepth()+1, aParent);
		putNodeInfo(theChild, theChildNodeInfo);
		return theChild;
	}
	
	public int getDepth (Object aNode)
	{
		NodeInfo theNodeInfo = getNodeInfo(aNode);
		assert theNodeInfo != null;
		return theNodeInfo.getDepth();
	}
	
	public Object getParent (Object aNode)
	{
		NodeInfo theNodeInfo = getNodeInfo(aNode);
		assert theNodeInfo != null;
		return theNodeInfo.getParent();
	}
	
	public boolean isNodeExpanded (Object aNode)
	{
		NodeInfo theNodeInfo = getNodeInfo(aNode);
		assert theNodeInfo != null;
		return theNodeInfo.isNodeExpanded();
	}
	
	public void setNodeExpanded (Object aNode, boolean aExpanded)
	{
		NodeInfo theNodeInfo = getNodeInfo(aNode);
		assert theNodeInfo != null;
		theNodeInfo.setNodeExpanded(aExpanded);
	}

	
	/**
	 * Instances implementing this interface are stored in the nodes map.
	 */
	protected interface NodeInfo
	{
		public boolean isNodeExpanded ();
		public void setNodeExpanded (boolean aExpanded);
		
		public int getDepth ();
		public Object getParent ();
	}
	
	protected static class DefaultNodeInfo implements NodeInfo
	{
		private boolean itsNodeExpanded;
		private int itsDepth;
		private Object itsParent;
		
		public DefaultNodeInfo (int aDepth, Object aParent)
		{
			itsDepth = aDepth;
			itsParent = aParent;
		}
		
		public boolean isNodeExpanded ()
		{
			return itsNodeExpanded;
		}
		
		public void setNodeExpanded (boolean aNodeExpanded)
		{
			itsNodeExpanded = aNodeExpanded;
		}
		
		public int getDepth ()
		{
			return itsDepth;
		}
		
		public Object getParent ()
		{
			return itsParent;
		}
	}
}
