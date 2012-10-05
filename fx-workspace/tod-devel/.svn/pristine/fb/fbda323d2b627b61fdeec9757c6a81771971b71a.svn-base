/*
 * Created on Mar 25, 2005
 */
package zz.snipsnap.storysnipper.ui.structure;

import zz.snipsnap.storysnipper.model.Story;
import zz.snipsnap.storysnipper.model.StoryPage;
import zz.utils.tree.SimpleTreeNode;
import zz.utils.tree.SwingTreeModel;

/**
 * Wraps a {@link zz.snipsnap.storysnipper.model.Story} into a swing tree model.
 * @author gpothier
 */
public class StoryTreeModel extends SwingTreeModel<SimpleTreeNode<StoryPage>, StoryPage>
{
	private Story itsStory;

	public StoryTreeModel(Story aStory)
	{
		super(aStory.getPages());
		itsStory = aStory;
	}
}
