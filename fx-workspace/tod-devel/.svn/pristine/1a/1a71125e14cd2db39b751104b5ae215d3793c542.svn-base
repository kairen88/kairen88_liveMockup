/*
 * Created on Mar 25, 2005
 */
package zz.snipsnap.storysnipper.ui.structure;

import java.awt.Component;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import zz.snipsnap.storysnipper.model.Story;
import zz.snipsnap.storysnipper.model.StoryPage;
import zz.utils.notification.IEvent;
import zz.utils.notification.SimpleEvent;
import zz.utils.properties.ArrayListProperty;
import zz.utils.properties.IListProperty;
import zz.utils.tree.SimpleTree;
import zz.utils.tree.SimpleTreeNode;
import zz.utils.ui.StackLayout;
import zz.waltz.api.IWaltzCanvas;
import zz.waltz.api.Topic;
import zz.waltz.api.WaltzComponent;

/**
 * This component shows the whole structure of a story.
 * @author gpothier
 */
public class StructureViewComponent extends WaltzComponent implements TreeSelectionListener
{
	public final IListProperty<StoryPage> pSelectedPages = new ArrayListProperty<StoryPage>(this)
	{
		protected void elementAdded(int aIndex, StoryPage aPage)
		{
			updateTreeSelection();
		}
		
		protected void elementRemoved(int aIndex, StoryPage aPage)
		{
			updateTreeSelection();			
		}
	};
	
	private Story itsStory;
	
	private JTree itsTree;
	private StoryTreeModel itsStoryTreeModel;
	
	private boolean itsUpdating = false;
	
	public StructureViewComponent(Story aStory)
	{
		itsStory = aStory;
		
		itsStoryTreeModel = new StoryTreeModel(itsStory);
		itsTree = new JTree(itsStoryTreeModel);
		itsTree.getSelectionModel().addTreeSelectionListener(this);
		itsTree.setCellRenderer(new StoryPageRenderer());
	} 
	
	protected void render(IWaltzCanvas aCanvas)
	{
		aCanvas.setLayout(new StackLayout());
		
		aCanvas.add (new JScrollPane(itsTree), null);
	}
	
	public void valueChanged(TreeSelectionEvent aE)
	{
		if (itsUpdating) return;
		
		itsUpdating = true;
		StoryPage theSelectedPage = getSelectedPage();
		
		if (theSelectedPage == null) pSelectedPages.clear();
		else if (pSelectedPages.size() == 0) pSelectedPages.add(theSelectedPage);
		else if (pSelectedPages.size() == 1) pSelectedPages.set(0, theSelectedPage);
		else
		{
			pSelectedPages.clear();
			pSelectedPages.add(theSelectedPage);
		}
		itsUpdating = false;
	}
	
	protected void updateTreeSelection()
	{
		if (itsUpdating) return;
		
		itsUpdating = true;
		TreeSelectionModel theSelectionModel = itsTree.getSelectionModel();
		theSelectionModel.clearSelection();
		
		for (StoryPage thePage : pSelectedPages)
		{
			TreePath thePath = itsStoryTreeModel.getTreePathForValue(thePage);
			if (thePath != null) theSelectionModel.addSelectionPath(thePath);
		}
		itsUpdating = false;
	}

	/**
	 * Returns the page currently selected in the tree,
	 */
	public StoryPage getSelectedPage()
	{
		SimpleTreeNode<StoryPage> theNode = getSelectedNode();
		return theNode != null ? theNode.pValue.get() : null;
	}
	
	/**
	 * Returns the currently selected node in the tree.
	 */
	public SimpleTreeNode<StoryPage> getSelectedNode()
	{
		TreePath thePath = itsTree.getSelectionPath();
		if (thePath == null) return null;

		else return (SimpleTreeNode<StoryPage>) thePath.getLastPathComponent();
	}
	
	private class StoryPageRenderer extends DefaultTreeCellRenderer
	{
		public Component getTreeCellRendererComponent(JTree aTree, Object aValue, boolean aSel, boolean aExpanded, boolean aLeaf, int aRow, boolean aHasFocus)
		{
			super.getTreeCellRendererComponent(aTree, aValue, aSel, aExpanded, aLeaf, aRow, aHasFocus);
			
			String theName = getName(aValue);
			setText(theName);
			
			return this;
		}

		protected String getName(Object aObject)
		{
			SimpleTreeNode<StoryPage> theNode = (SimpleTreeNode<StoryPage>) aObject;
			SimpleTree<StoryPage> thePages = itsStory.getPages();
			StoryPage thePage = thePages.getValue(theNode);
			
			return thePage.getName();
		}
	}
}
