/*
 * Created on Mar 15, 2004
 */
package net.basekit.models.tree;

import net.basekit.models.WidgetModel;
 
/**
 * Permits to retrieve which nodes are expanded or collapsed
 * in a tree.
 * @author gpothier
 */
public interface TreeWidgetModel extends WidgetModel
{
	/**
	 * @return the root node of the tree.
	 */
	public Object getRootNode ();
	
	/**
	 * Returns the user object that correspond to a given node.
	 * In some cases it can be the object itself, in other cases
	 * it must be determined another way.
	 */
	public Object getUserObject (Object aNode);
	
	/**
	 * @return The parent of the specified node, if any.
	 */
	public Object getParent (Object aNode);
	
	/**
	 * Computes the depth of a node. The root node has depth 0.
	 */
	public int getDepth (Object aNode);

	/**
	 * @return The number of children of the specified node.
	 */
	public int getChildrenCount (Object aParent);
	
	/**
	 * @return A child of the specified parent node.
	 */
	public Object getChild (Object aParent, int aIndex);

	/**
	 * @return Whether the specified node is expanded or collapsed.
	 */
	public boolean isNodeExpanded (Object aNode);
	
	/**
	 * Changes the expanded state of a node.
	 */
	public void setNodeExpanded (Object aNode, boolean aExpanded);
	
	/**
	 * Convenience method to toggle the expanded state of a node
	 */
	public void toggleNodeExpansion (Object aNode);
	
	/**
	 * @return The index of the node within the tree, in a depth-first
	 * traversal of expanded nodes..
	 */
	public int getIndexOf (Object aNode);
	
	
	/**
	 * Returns the node at the given index, in a depth-first traversal
	 * of expanded nodes.
	 */
	public Object getNodeAt (int aIndex);
	
	/**
	 * Returns the number of nodes that can be displayed according to
	 * the expanded/collapsed state.
	 */
	public int getVisibleNodesCount ();
}
