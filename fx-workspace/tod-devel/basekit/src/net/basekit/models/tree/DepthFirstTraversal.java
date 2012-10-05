/*
 * Created on Mar 15, 2004
 */
package net.basekit.models.tree;

/**
 * Visits a tree in depth first order
 * @author gpothier
 */
public class DepthFirstTraversal implements Traversal
{
	private static DepthFirstTraversal INSTANCE = new DepthFirstTraversal();

	public static DepthFirstTraversal getInstance ()
	{
		return INSTANCE;
	}

	private DepthFirstTraversal ()
	{
	}
	
	public void traverse (TreeWidgetModel aTreeModel, TreeVisitor aVisitor)
	{
		traverse(aTreeModel, aTreeModel.getRootNode(), aVisitor);
	}
	
	private void traverse (TreeWidgetModel aTreeModel, Object aNode, TreeVisitor aVisitor)
	{
		boolean theVisitChildren = aVisitor.visit (aNode);
		if (!theVisitChildren || aVisitor.isStopped()) return;
		
		int theCount = aTreeModel.getChildrenCount(aNode);
		for (int i=0;i<theCount;i++)
		{
			Object theChild = aTreeModel.getChild(aNode, i);
			traverse (aTreeModel, theChild, aVisitor);
			if (aVisitor.isStopped()) break;
		}
	}
}
