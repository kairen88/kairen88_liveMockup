/*
 * Created on Mar 24, 2005
 */
package zz.snipsnap.storysnipper.model;

import java.io.IOException;

import zz.snipsnap.client.core.SnipSnapSpace;
import zz.snipsnap.client.core.SnipsManager;
import zz.utils.tree.ITree;
import zz.utils.tree.ITreeListener;
import zz.utils.tree.SimpleTree;
import zz.utils.tree.SimpleTreeNode;

/**
 * A snipsnap-based story.
 * @author gpothier
 */
public class Story implements ITreeListener<SimpleTreeNode<StoryPage>, StoryPage>
{
	
	/**
	 * The space in which this story resides.
	 */
	private SnipSnapSpace itsSpace;
	
	/**
	 * The name of this story's main snip
	 */
	private String itsRootSnipName;
	
	private SimpleTree<StoryPage> itsPages = new SimpleTree<StoryPage>();

	private SnipsManager itsSnipsManager;
	private MediaCollection itsMediaCollection;
	
	private boolean itsLoading = false;

	public Story(SnipSnapSpace aSpace, String aName) throws IOException
	{
		itsSpace = aSpace;
		itsRootSnipName = aName;

		itsPages.addListener(this);
		
		itsSnipsManager = itsSpace.getSnipsManager();
		
		load();
		itsMediaCollection = new MediaCollection(this);
	}
	
	public String resolve (String aSubSnipName)
	{
		return itsRootSnipName+"/"+aSubSnipName;
	}
	
	private void load()
	{
		try
		{
			itsLoading = true;
			// Check base structure
			if (! itsSnipsManager.hasSnip(itsRootSnipName)) itsSnipsManager.createSnip(null, itsRootSnipName);
			
			// load pages
			SimpleTreeNode<StoryPage> theRootNode = itsPages.getRoot();
			StoryPage theRootPage = new StoryPage(this, itsRootSnipName);
			theRootNode.pValue.set(theRootPage);
			loadChildren(theRootNode);
		}
		finally
		{
			itsLoading = false;
		}
	}
	
	private void loadChildren (SimpleTreeNode<StoryPage> aNode)
	{
		StoryPage thePage = aNode.pValue.get();
		Iterable<String> theChildren = itsSnipsManager.getSnipChildren(thePage.getSnipName());
		for (String theName : theChildren)
		{
			StoryPage theChildPage = new StoryPage(this, theName);
			SimpleTreeNode<StoryPage> theChildNode = itsPages.createNode(theChildPage);
			aNode.pChildren.add(theChildNode);
			
			loadChildren(theChildNode);
		}
	}

	public SnipSnapSpace getSpace()
	{
		return itsSpace;
	}

	public SnipsManager getSnipsManager()
	{
		return itsSnipsManager;
	}
	
	public String getRootSnipName()
	{
		return itsRootSnipName;
	}

	public SimpleTree<StoryPage> getPages()
	{
		return itsPages;
	}

	public MediaCollection getMediaCollection()
	{
		return itsMediaCollection;
	}
	
	/**
	 * Factory for new pages. The created page is not added to the tree.
	 */
	public StoryPage addPage(SimpleTreeNode<StoryPage> aParentNode, String aName)
	{
		StoryPage theParentPage = itsPages.getValue(aParentNode);
		String theParentSnipName = theParentPage.getSnipName();
		
		StoryPage thePage = new StoryPage(this, theParentSnipName+"/"+aName);
		SimpleTreeNode<StoryPage> theChildNode = itsPages.createNode(thePage);
		
		aParentNode.pChildren.add(theChildNode);
		
		return thePage;
	}

	public void childAdded(ITree<SimpleTreeNode<StoryPage>, StoryPage> aTree, SimpleTreeNode<StoryPage> aNode, int aIndex, SimpleTreeNode<StoryPage> aChild)
	{
		if (itsLoading) return;
		
		StoryPage theParentPage = aNode.pValue.get();
		StoryPage theNewPage = aChild.pValue.get();
		
		itsSnipsManager.createSnip(theParentPage.getSnipName(), theNewPage.getName());
	}

	public void childRemoved(ITree<SimpleTreeNode<StoryPage>, StoryPage> aTree, SimpleTreeNode<StoryPage> aNode, int aIndex, SimpleTreeNode<StoryPage> aChild)
	{
		if (itsLoading) return;

		StoryPage thePage = aChild.pValue.get();
		itsSnipsManager.removeSnip(thePage.getSnipName());
	}

	public void valueChanged(ITree<SimpleTreeNode<StoryPage>, StoryPage> aTree, SimpleTreeNode<StoryPage> aNode, StoryPage aNewValue)
	{
	}

	
	
}
