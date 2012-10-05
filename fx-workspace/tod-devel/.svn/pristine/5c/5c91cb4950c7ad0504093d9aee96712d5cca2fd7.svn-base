/*
 * Created on Mar 25, 2005
 */
package zz.snipsnap.storysnipper.ui.html.gallery.links;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JComponent;
import javax.swing.text.html.HTML;

import zz.snipsnap.storysnipper.model.Story;
import zz.snipsnap.storysnipper.model.StoryPage;
import zz.snipsnap.storysnipper.ui.html.HtmlTemplate;
import zz.snipsnap.storysnipper.ui.html.gallery.AbstractGalleryComponent;
import zz.snipsnap.storysnipper.ui.structure.StructureViewComponent;
import zz.waltz.api.IWaltzCanvas;
import zz.waltz.api.action.DefaultActionModel;

/**
 * This component permits to insert SnipSNap style [link] to
 * arbitrary story pages.
 * @author gpothier
 */
public class LinksComponent extends AbstractGalleryComponent
{
	private Story itsStory;
	private StructureViewComponent itsStructureViewComponent;

	public LinksComponent(Story aStory)
	{
		super ("Links");
		itsStory = aStory;
		itsStructureViewComponent = new StructureViewComponent(aStory);
	}
	
	protected void render(IWaltzCanvas aCanvas)
	{
		aCanvas.setLayout(new BorderLayout());
		
		renderToolbar(aCanvas.createCanvas(BorderLayout.NORTH));
		aCanvas.add(itsStructureViewComponent, BorderLayout.CENTER);
	}
	
	private void renderToolbar (IWaltzCanvas aCanvas)
	{
		aCanvas.setLayout(new FlowLayout());
		aCanvas.addAction(BUTTON_ACTION, new InsertLinkAction(), null);
	}
	
	protected void insertLink(StoryPage aPage)
	{
		HtmlTemplate theTemplate = new HtmlTemplate(
				HTML.Tag.SPAN, 
				"<span>["+aPage.getSnipName()+"]</span>"); //TODO: we don't need the tag, how to avoid it?
		
		eSelected.fire(theTemplate);
	}
	
	private class InsertLinkAction extends DefaultActionModel
	{
		public InsertLinkAction()
		{
			super("Insert");
		}
		
		public void performed(JComponent aComponent)
		{
			StoryPage thePage = itsStructureViewComponent.getSelectedPage();
			if (thePage != null) insertLink(thePage);
		}
	}
}
