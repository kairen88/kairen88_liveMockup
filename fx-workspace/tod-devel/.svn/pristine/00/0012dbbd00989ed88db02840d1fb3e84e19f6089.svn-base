/*
 * Created on Mar 24, 2005
 */
package zz.snipsnap.storysnipper.ui.html;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.text.html.HTML;

import zz.snipsnap.storysnipper.model.Story;
import zz.snipsnap.storysnipper.model.StoryPage;
import zz.snipsnap.storysnipper.ui.IStoryPageEditor;
import zz.snipsnap.storysnipper.ui.html.gallery.AbstractGalleryComponent;
import zz.snipsnap.storysnipper.ui.html.gallery.links.LinksComponent;
import zz.snipsnap.storysnipper.ui.html.gallery.media.StoryMediaGalleryComponent;
import zz.snipsnap.storysnipper.ui.html.gallery.templates.HtmlTemplatesComponent;
import zz.utils.notification.IEvent;
import zz.utils.notification.IEventListener;
import zz.utils.ui.StackLayout;
import zz.waltz.api.IWaltzCanvas;
import zz.waltz.api.WaltzComponent;
import de.xeinfach.kafenio.KafenioPanel;
import de.xeinfach.kafenio.KafenioPanelConfiguration;

/**
 * This component permits to edit a page with an HTML layout.
 * It provides the HTML editor proper, a set of predefined templates,
 * and the media gallery component.
 * @author gpothier
 */
public class HtmlLayoutComponent extends WaltzComponent 
implements IStoryPageEditor
{
	private static final String HTML_PREFIX = "{sequence-media}{html}";
	private static final String HTML_SUFFIX = "{html}{sequence-media}";
	private StoryPage itsStoryPage;
	
	/**
	 * This is the HTML editor
	 */
	private KafenioPanel itsKafenioPanel;
	
	/**
	 * All the gallery components (media, html templates, links...)
	 */
	private List<AbstractGalleryComponent> itsGalleryComponents = new ArrayList<AbstractGalleryComponent>();
	
	private IEventListener<HtmlTemplate> itsGalleryListener = new IEventListener<HtmlTemplate>()
	{
		public void fired(IEvent< ? extends HtmlTemplate> aEvent, HtmlTemplate aData)
		{
			insertTemplate(aData);
		}
		
	};
	
	public HtmlLayoutComponent(Story aStory)
	{		
		// Init Kafenio
		KafenioPanelConfiguration theConfiguration = new KafenioPanelConfiguration();
		String theBaseUrl = aStory.getSpace().getUri().toString();
//		theConfiguration.setImageDir(theBaseUrl);
		theConfiguration.setCodeBase(theBaseUrl);
		itsKafenioPanel = new KafenioPanel(theConfiguration);
		
		addGalleryComponent(new StoryMediaGalleryComponent(aStory));
		addGalleryComponent(new HtmlTemplatesComponent());
		addGalleryComponent(new LinksComponent(aStory));
				
		loadPage();
	}
	
	private void addGalleryComponent (AbstractGalleryComponent aGalleryComponent)
	{
		itsGalleryComponents.add(aGalleryComponent);
		aGalleryComponent.eSelected().addListener(itsGalleryListener);
	}
	

	public StoryPage getCurrentPage()
	{
		return itsStoryPage;
	}

	public void load(StoryPage aStoryPage)
	{
		itsStoryPage = aStoryPage;
		loadPage();
	}

	public void save()
	{
		if (itsStoryPage != null)
		{
			System.out.println("Saving "+itsStoryPage.getName());
			String theDocumentBody = itsKafenioPanel.getDocumentBody();
			itsStoryPage.setSnipContent(HTML_PREFIX+theDocumentBody+HTML_SUFFIX);
		}
	}
	
	public void restore()
	{
		if (itsStoryPage != null) loadPage();
	}

	protected void render(IWaltzCanvas aCanvas)
	{
		aCanvas.setLayout(new StackLayout());
		
		JSplitPane theSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		theSplitPane.setLeftComponent(itsKafenioPanel);
		theSplitPane.setRightComponent(createGallery());

		aCanvas.add(theSplitPane, null);
	}
	
	private JComponent createGallery()
	{
		JTabbedPane theTabbedPane = new JTabbedPane();
		
		for (AbstractGalleryComponent theGalleryComponent : itsGalleryComponents)
		{
			theTabbedPane.add(theGalleryComponent.getName(), theGalleryComponent.getSwingComponent());
		}
		
		return theTabbedPane;
	}
	
	
	private void insertTemplate(HtmlTemplate aTemplate)
	{
		insert(aTemplate.getTag(), aTemplate.getHtmlFragment());
	}
	
	/**
	 * Inserts an html fragment into the document at the current caret location.
	 */
	private void insert (HTML.Tag aTag, String aHtml)
	{
		insert(aTag, aHtml, 0, 0);
	}
	
	/**
	 * Inserts an html fragment into the document at the current caret location.
	 */
	private void insert (HTML.Tag aTag, String aHtml, int aPop, int aPush)
	{
		int thePosition = itsKafenioPanel.getCaretPosition();
		try
		{
			itsKafenioPanel.getExtendedHtmlKit().insertHTML(
					itsKafenioPanel.getExtendedHtmlDoc(), 
					thePosition, 
					aHtml, 
					aPop,
					aPush, 
					aTag);
			itsKafenioPanel.refreshOnUpdate();
			itsKafenioPanel.setCaretPosition(thePosition+1);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}		
	}
	
	private void loadPage()
	{
		String theDocumentBody = itsStoryPage != null ? itsStoryPage.getSnipContent() : "";
		int theFirstIndex = theDocumentBody.indexOf(HTML_PREFIX);
		int theLastIndex = theDocumentBody.lastIndexOf(HTML_SUFFIX);
		
		if (theFirstIndex >= 0 && theLastIndex >= 0 && theFirstIndex < theLastIndex)
			theDocumentBody = theDocumentBody.substring(theFirstIndex+HTML_PREFIX.length(), theLastIndex);
		
		itsKafenioPanel.setDocumentText(theDocumentBody);
	}
	
}
