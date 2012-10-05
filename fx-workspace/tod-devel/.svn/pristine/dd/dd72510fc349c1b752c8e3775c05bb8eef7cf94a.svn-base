/*
 * Created on Mar 24, 2005
 */
package zz.snipsnap.storysnipper.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import zz.snipsnap.storysnipper.model.Story;
import zz.snipsnap.storysnipper.model.StoryPage;
import zz.snipsnap.storysnipper.ui.html.HtmlLayoutComponent;
import zz.snipsnap.storysnipper.ui.structure.StructureEditorComponent;
import zz.snipsnap.storysnipper.ui.upload.UploadComponent;
import zz.utils.list.IList;
import zz.utils.list.IListListener;
import zz.waltz.api.IWaltzCanvas;
import zz.waltz.api.WaltzComponent;
import zz.waltz.api.action.DefaultActionModel;

/**
 * The main component of StorySnipper's UI.
 * @author gpothier
 */
public class StorySnipperComponent extends WaltzComponent
{
	private Story itsStory;
	private HtmlLayoutComponent itsHtmlLayoutComponent;
	private UploadComponent itsUploadComponent;
	private StructureEditorComponent itsStructureEditorComponent;
	private StoryPage itsOpenPage;
	
	private IListListener<StoryPage> itsSelectedPagesListener = new IListListener<StoryPage>()
	{

		public void elementAdded(IList<StoryPage> aList, int aIndex, StoryPage aElement)
		{
			changed();
		}

		public void elementRemoved(IList<StoryPage> aList, int aIndex, StoryPage aElement)
		{
			changed();
		}
		
		private void changed()
		{
			if (itsStructureEditorComponent.pSelectedPages.size() == 1)
			{
				openPage(itsStructureEditorComponent.pSelectedPages.get(0));
			}
		}
	};

	public StorySnipperComponent(Story aStory)
	{
		itsStory = aStory;
		
		itsHtmlLayoutComponent = new HtmlLayoutComponent(itsStory);
		itsUploadComponent = new UploadComponent(itsStory.getMediaCollection());
		itsStructureEditorComponent = new StructureEditorComponent(itsStory);
		itsStructureEditorComponent.pSelectedPages.addListener(itsSelectedPagesListener);
		
		itsStructureEditorComponent.pSelectedPages.clear();
		StoryPage theRootPage = itsStory.getPages().getRoot().pValue.get();
		itsStructureEditorComponent.pSelectedPages.add (theRootPage);		
	}
	
	/**
	 * Saves all editors
	 */
	public void save()
	{
		itsHtmlLayoutComponent.save();		
	}

	/**
	 * Restores all editors
	 */
	public void restore()
	{
		itsHtmlLayoutComponent.restore();		
	}

	protected void openPage(StoryPage aPage)
	{
		if (itsOpenPage != aPage)
		{
			itsHtmlLayoutComponent.save();
			itsHtmlLayoutComponent.load(aPage);
			
			itsOpenPage = aPage;
		}
	}
	
	protected void render(IWaltzCanvas aCanvas)
	{
		JSplitPane theSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		
		theSplitPane.setLeftComponent(itsStructureEditorComponent.getSwingComponent());
		theSplitPane.setRightComponent(createTabbedPane());
		theSplitPane.setOneTouchExpandable(true);
		
		aCanvas.setLayout(new BorderLayout());
		aCanvas.add(theSplitPane, BorderLayout.CENTER);
		
		renderNorth(aCanvas.createCanvas(BorderLayout.NORTH));
	}
	
	private void renderNorth (IWaltzCanvas aCanvas)
	{
		aCanvas.setLayout(new FlowLayout(FlowLayout.LEFT));

		aCanvas.addAction(BUTTON_ACTION, new SaveAction(), null);
		aCanvas.addAction(BUTTON_ACTION, new RestoreAction(), null);
		
		String theTitle = String.format(
				"Editing %s on %s",
				itsStory.getRootSnipName(),
				itsStory.getSpace().getUri());
		
		aCanvas.label(theTitle, null);
	}
	
	private JComponent createTabbedPane ()
	{
		JTabbedPane theTabbedPane = new JTabbedPane();
		
		theTabbedPane.addTab("Html layout", itsHtmlLayoutComponent.getSwingComponent());
		theTabbedPane.addTab("Manage media", itsUploadComponent.getSwingComponent());
		
		return theTabbedPane;
	}
	
	private class SaveAction extends DefaultActionModel
	{
		public SaveAction()
		{
			super ("Save");
		}
		
		public void performed(JComponent aComponent)
		{
			save();
		}
	}
	
	private class RestoreAction extends DefaultActionModel
	{
		public RestoreAction()
		{
			super ("Restore");
		}
		
		public void performed(JComponent aComponent)
		{
			int theResult = JOptionPane.showConfirmDialog(
					getSwingComponent(), 
					"Are you sure you want to discard your changes and reload the page from the site?", 
					"Restore", 
					JOptionPane.YES_NO_OPTION);
			
			if (theResult == JOptionPane.YES_OPTION) restore();
		}
	}
}
