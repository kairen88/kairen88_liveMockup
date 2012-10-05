/*
 * Created on Mar 24, 2005
 */
package zz.snipsnap.storysnipper.ui.html.gallery.media;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.net.URI;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.text.html.HTML;

import zz.snipsnap.plugin.media.MediaPlugin;
import zz.snipsnap.storysnipper.model.MediaCollection;
import zz.snipsnap.storysnipper.model.MediaObject;
import zz.snipsnap.storysnipper.model.Story;
import zz.snipsnap.storysnipper.ui.html.HtmlTemplate;
import zz.snipsnap.storysnipper.ui.html.gallery.AbstractGalleryComponent;
import zz.snipsnap.storysnipper.ui.widgets.imagelist.ImageListComponent;
import zz.snipsnap.storysnipper.ui.widgets.imagelist.ImageListView;
import zz.utils.notification.IEvent;
import zz.utils.notification.IEventListener;
import zz.utils.properties.IProperty;
import zz.utils.properties.IPropertyListener;
import zz.utils.properties.IRWProperty;
import zz.utils.properties.PropertyId;
import zz.utils.properties.SimplePropertyListener;
import zz.utils.properties.SimpleRWProperty;
import zz.utils.ui.GridStackLayout;
import zz.waltz.api.IWaltzCanvas;
import zz.waltz.api.Topic;

/**
 * This componenet displays all the media available within the story, and permits to
 * select and insert them.
 * @author gpothier
 */
public class StoryMediaGalleryComponent extends AbstractGalleryComponent
{
	private MediaCollection itsMediaCollection;
	private MediaCollectionListModel itsModel;
	private ImageListComponent<MediaObject> itsImageListComponent;
	
	private IEventListener<Object> itsSelectedListener = new IEventListener<Object>()
	{
		public void fired(IEvent< ? extends Object> aEvent, Object aData)
		{
			insertMedia((MediaObject) aData);
		}
	};
	
	private IPropertyListener<IMediaFilter> itsFilterListener = new SimplePropertyListener<IMediaFilter>()
	{
		protected void changed(IProperty<IMediaFilter> aProperty)
		{
			updateFilters();
		}
	};

	/**
	 * This property permits to indicate the size of the thumbnail to display in the page.
	 */
	private final IRWProperty<Integer> pThumbnailSize = new SimpleRWProperty<Integer>(this, 200);
	
	private static final PropertyId<Boolean> USE_THUMBNAIL = new PropertyId<Boolean>("Thumbnail");
	
	/**
	 * When this property is true, a thumbnail is inserted. Otherwise, the original image
	 * is inserted.
	 */
	private final IRWProperty<Boolean> pUseThumbnail = new SimpleRWProperty<Boolean>(this, USE_THUMBNAIL, true);
	private DateFilterComponent itsDateFilterComponent;

	
	
	public StoryMediaGalleryComponent(Story aStory)
	{
		super ("Media");
		
		itsMediaCollection = aStory.getMediaCollection();
		itsModel = new MediaCollectionListModel(itsMediaCollection);
		itsImageListComponent = new ImageListComponent<MediaObject>(itsModel.getEntries(), itsMediaCollection);
		
		itsImageListComponent.eObjectSelected().addListener(itsSelectedListener);
		
		itsDateFilterComponent = new DateFilterComponent(itsMediaCollection);
		itsDateFilterComponent.pFilter().addListener(itsFilterListener);
	}
	
	protected void insertMedia(MediaObject aMediaObject)
	{
		String theMediaPath = aMediaObject.getPath();
		HtmlTemplate theTemplate;
		
		if (pUseThumbnail.get()) 
		{
			int theSize = pThumbnailSize.get();
			URI theThumbnailUri = aMediaObject.getThumbnailUri(theSize);
			theThumbnailUri = itsMediaCollection.getSpace().getRelativeUri(theThumbnailUri);
			
			URI theMediaViewerUri = itsMediaCollection.getMediaPluginClient().getMediaViewerUri(
					itsMediaCollection.getMediaSnipName(),
					aMediaObject.getName());
			theMediaViewerUri = itsMediaCollection.getSpace().getRelativeUri(theMediaViewerUri);
			
			theTemplate = new HtmlTemplate(HTML.Tag.A, String.format(
					"<a href='%s'><img src='%s'></img></a>",
					theMediaViewerUri.toString(),
					theThumbnailUri.toString()));
		}	
		else
		{
			theTemplate = new HtmlTemplate(HTML.Tag.IMG, String.format(
					"<img src='%s'></img>",
					theMediaPath));			
		}
		eSelected.fire(theTemplate);
	}
	

	
	protected void render(IWaltzCanvas aCanvas)
	{
		aCanvas.setLayout(new BorderLayout());
		
		aCanvas.add(itsImageListComponent, BorderLayout.CENTER);
		
		renderNorth(aCanvas.createCanvas(BorderLayout.NORTH));
	}

	private void renderNorth (IWaltzCanvas aCanvas)
	{
		aCanvas.setLayout(new GridStackLayout(1));
		
		renderToolbar(aCanvas.createCanvas(null));
		renderFilters(aCanvas.createCanvas(null));
	}
	
	private void renderToolbar (IWaltzCanvas aCanvas)
	{
		aCanvas.setLayout(new FlowLayout());
		
		aCanvas.addProperty(CHECKBOX_PROPERTY(true), pUseThumbnail, null);
		aCanvas.addProperty(SPINNER_PROPERTY(20, MediaPlugin.MAX_THUMBNAIL_SIZE, 10), pThumbnailSize, pUseThumbnail, null);
	}
	
	private void renderFilters (IWaltzCanvas aCanvas)
	{
		aCanvas.setLayout(new GridStackLayout(1));
		aCanvas.add(new Separator(), null);
		aCanvas.add(itsDateFilterComponent, null);
		aCanvas.add(new Separator(), null);
	}
	
	protected void updateFilters()
	{
		itsModel.pFilter.set(itsDateFilterComponent.pFilter().get());
	}
	
	private static class Separator extends JPanel
	{
		public Separator()
		{
			setPreferredSize(new Dimension(1, 2));
			setBackground(Color.BLUE);
		}
	}

}
