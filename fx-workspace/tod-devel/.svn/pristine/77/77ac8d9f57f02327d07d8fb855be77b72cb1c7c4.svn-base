/*
 * Created on Apr 12, 2005
 */
package zz.snipsnap.utils;

import java.io.IOException;

import zz.snipsnap.storysnipper.model.Story;
import zz.snipsnap.storysnipper.model.StoryPage;
import zz.snipsnap.storysnipper.ui.login.LoginComponent;
import zz.utils.tree.ITreeVisitor;
import zz.utils.tree.SimpleTree;
import zz.utils.tree.SimpleTreeNode;
import zz.utils.tree.TreeUtils;

public class StoryThumbnailPreloader
{
	public static void main(String[] args) throws IOException
	{
		Story theStory = LoginComponent.login();
		SimpleTree<StoryPage> thePages = theStory.getPages();
		
		TreeUtils.visit(thePages, new ITreeVisitor<SimpleTreeNode<StoryPage>, StoryPage>()
				{
					public void visit(SimpleTreeNode<StoryPage> aNode, StoryPage aValue)
					{
					}
				});

	}
}
