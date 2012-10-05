/*
 * Created on Mar 15, 2004
 */
package net.basekit.models.tree;

/**
 * Visitor pattern for a tree node widget.
 * Subclasses can call the {@link #stop()} method if they
 * whish that the traversal stop.
 * @see net.basekit.models.tree.Traversal
 * @author gpothier
 */
public abstract class TreeVisitor
{
	private boolean itsStopped = false;
	
	/**
	 * This method is called for each node in the tree.
	 * @return Whether children should be visited.
	 */
	public abstract boolean visit (Object aNode);
	
	/**
	 * Tells the traversal that no more nodes should be visited.
	 */
	protected void stop ()
	{
		itsStopped = true;
	}
	
	public boolean isStopped ()
	{
		return itsStopped;
	}
}
