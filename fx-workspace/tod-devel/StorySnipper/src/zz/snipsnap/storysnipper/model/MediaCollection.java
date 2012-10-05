/*
 * Created on Mar 25, 2005
 */
package zz.snipsnap.storysnipper.model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;
import javax.imageio.ImageIO;

import zz.snipsnap.MIMEConstants;
import zz.snipsnap.client.core.AttachmentsManager;
import zz.snipsnap.client.core.SnipSnapSpace;
import zz.snipsnap.client.core.SnipsManager;
import zz.snipsnap.client.plugin.media.MediaPluginClient;
import zz.snipsnap.plugin.media.MediaInfo;
import zz.snipsnap.storysnipper.Resources;
import zz.snipsnap.utils.MetadataUtils;
import zz.utils.ProgressModel;
import zz.utils.properties.IProperty;
import zz.utils.properties.IRWProperty;
import zz.utils.properties.SimpleRWProperty;
import zz.utils.ui.thumbnail.AsyncThumbnailCache;
import zz.utils.ui.thumbnail.ProcessingOrder;

/**
 * This class represents the set of media files in the story.
 * @author gpothier
 */
public class MediaCollection extends AsyncThumbnailCache<MediaObject>
{
	private static final String MEDIA_SNIP_NAME = "_zz_stsn_media";
	
	private static final MimetypesFileTypeMap MIME = new MimetypesFileTypeMap();
	
	static
	{
		MIME.addMimeTypes(MIMEConstants.IMAGE_PNG+" png PNG");
	}

	private Story itsStory;
	private AttachmentsManager itsAttachmentsManager;
	private MediaPluginClient itsMediaPluginClient;
	
	private List<MediaObject> itsMediaObjects = new ArrayList<MediaObject>();
	
	private IRWProperty<Integer> pCollectionSize = new SimpleRWProperty<Integer>(this);
	
	public MediaCollection(Story aStory) throws IOException
	{
		super (100, 10, ProcessingOrder.LIFO);
		itsStory = aStory;
		load();
	}

	public SnipSnapSpace getSpace()
	{
		return getStory().getSpace();
	}

	public AttachmentsManager getAttachmentsManager()
	{
		if (itsAttachmentsManager == null) 
		{
			itsAttachmentsManager = getSpace().getAttachmentsManager();
		}
		
		return itsAttachmentsManager;
	}

	public MediaPluginClient getMediaPluginClient()
	{
		if (itsMediaPluginClient == null) 
		{
			itsMediaPluginClient = new MediaPluginClient(getSpace());
		}
		
		return itsMediaPluginClient;
	}
	
	protected BufferedImage createThumbnail(MediaObject aId, int aMaxSize)
	{
		if (aId == null) return Resources.getInstance().getThumbnail(Resources.LOADING, aMaxSize);
		else 
		{
			BufferedImage theThumbnail = null;
			
			try
			{
				theThumbnail = getMediaPluginClient().getThumbnail(
						getMediaSnipName(), 
						aId.getName(), 
						aMaxSize);
			}
			catch (IOException e)
			{
				e.printStackTrace();
				theThumbnail = Resources.getInstance().getThumbnail(Resources.BROKEN, aMaxSize);
			}
			
			return theThumbnail;
		}
	}

	public Story getStory()
	{
		return itsStory;
	}
	
	/**
	 * Returns the name of the snip that contains the media files.
	 */
	public String getMediaSnipName()
	{
		return getStory().resolve(MEDIA_SNIP_NAME);
	}

	/**
	 * Loads the content of the collection from the snip.
	 */
	private void load() throws IOException
	{
		// Check base structure
		String theMediaSnipName = getMediaSnipName();
		SnipsManager theSnipsManager = getSpace().getSnipsManager();
		if (! theSnipsManager.hasSnip(theMediaSnipName)) theSnipsManager.createSnip(null, theMediaSnipName);
		
		itsMediaObjects.clear();
		
		List<MediaInfo> theAttachments = getMediaPluginClient().getAttachmentsInfo(getMediaSnipName());
		for (MediaInfo theMediaInfo : theAttachments)
		{
			MediaObject theMediaObject = new MediaObject(this, theMediaInfo);
			itsMediaObjects.add(theMediaObject);
		}
		
		pCollectionSize.set(itsMediaObjects.size());
	}
	
	/**
	 * Returns a list of all the media objects in this collection.
	 * This list should not be modified.
	 */
	public List<MediaObject> getMediaObjects()
	{
		return itsMediaObjects;
	}
	
	/**
	 * This read-only property reflects the collection size.
	 */
	public IProperty<Integer> pCollectionSize()
	{
		return pCollectionSize;
	}
	

	/**
	 * Uploads a media object to the snip
	 * @param aFileName The name under which the object will be saved
	 * @param aContentType MIME content type of the file.
	 * @param aInputStream The input stream that provides media content
	 * @return The newly added media object
	 */
	public MediaObject addMediaObject (
			String aFileName, 
			String aContentType, 
			long aSize,
			InputStream aInputStream, 
			ProgressModel<Long> aProgressModel) throws IOException
	{
		getAttachmentsManager().attach(getMediaSnipName(), aFileName, aContentType, aSize, aInputStream, aProgressModel);
		MediaInfo theMediaInfo = MetadataUtils.getInfo(aFileName, aInputStream, aSize);
		MediaObject theMediaObject = new MediaObject(this, theMediaInfo);
		
		itsMediaObjects.add(theMediaObject);
		
		pCollectionSize.set(itsMediaObjects.size());
		return theMediaObject;
	}
	
	/**
	 * Adds the given file's content, trying to determine its MIME type.
	 */
	public MediaObject addMedia (File aFile, ProgressModel<Long> aProgressModel) throws IOException
	{
		String theName = aFile.getName();
		String theMimeType = MIME.getContentType(aFile);
		
		getAttachmentsManager().attach(getMediaSnipName(), theName, theMimeType, aFile, aProgressModel);
		MediaInfo theMediaInfo = MetadataUtils.getInfo(aFile);
		MediaObject theMediaObject = new MediaObject(this, theMediaInfo);
		
		itsMediaObjects.add(theMediaObject);
		
		pCollectionSize.set(itsMediaObjects.size());
		return theMediaObject;
	}
	
	public void removeMediaObject (MediaObject aMediaObject)
	{
		getAttachmentsManager().detach(getMediaSnipName(), aMediaObject.getName());
		itsMediaObjects.remove(aMediaObject);

		pCollectionSize.set(itsMediaObjects.size());
	}
}
