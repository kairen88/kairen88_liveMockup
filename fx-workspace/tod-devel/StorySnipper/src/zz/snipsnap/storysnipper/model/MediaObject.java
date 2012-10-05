/*
 * Created on Mar 25, 2005
 */
package zz.snipsnap.storysnipper.model;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import zz.snipsnap.plugin.media.MediaInfo;


/**
 * Represents a particular media in the story
 * @author gpothier
 */
public class MediaObject
{
	private MediaCollection itsMediaCollection;
	private MediaInfo itsInfo;
	
	public MediaObject(MediaCollection aMediaCollection, MediaInfo aInfo)
	{
		itsMediaCollection = aMediaCollection;
		itsInfo = aInfo;
	}

	public String getName()
	{
		return itsInfo.getName();
	}

	public MediaCollection getMediaCollection()
	{
		return itsMediaCollection;
	}
	
	public MediaInfo getInfo()
	{
		return itsInfo;
	}

	/**
	 * Returns the Url of a thumbnail or icon of this media object
	 * @param aMaxSize Maximum size of the image, in pixels
	 */
	public URI getThumbnailUri (int aMaxSize) 
	{
		return itsMediaCollection.getMediaPluginClient().getThumbnailURI(
				itsMediaCollection.getMediaSnipName(), 
				getName(), 
				aMaxSize);
	}
	
	/**
	 * Convenience method for {@link MediaCollection}'s {@link zz.utils.ui.thumbnail.ThumbnailCache} mechanism
	 */
	public BufferedImage getThumbnail (int aMaxSize)
	{
		return getMediaCollection().getThumbnail(this, aMaxSize);
	}
	
	
	/**
	 * Returns an input stream that provides the content of this media object
	 */
	public InputStream getContent() throws IOException
	{
		return itsMediaCollection.getAttachmentsManager().getAttachmentInputStream(
				itsMediaCollection.getMediaSnipName(), 
				getName());
	}
	
	/**
	 * Returns the path of this object relative to the SnipSnap instance
	 */
	public String getPath()
	{
		return "space/"+itsMediaCollection.getMediaSnipName()+"/"+getName();
	}
}
