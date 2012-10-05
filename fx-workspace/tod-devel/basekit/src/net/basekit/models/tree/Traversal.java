/*
 * Created on Mar 15, 2004
 */
package net.basekit.models.tree;

/**
 * Defines a traversal strategy for classes that permit to visit a tree.
 * @see net.basekit.models.tree.TreeVisitor
 * @author gpothier
 */
public interface Traversal
{
	public void traverse (TreeWidgetModel aTreeModel, TreeVisitor aVisitor);
}
