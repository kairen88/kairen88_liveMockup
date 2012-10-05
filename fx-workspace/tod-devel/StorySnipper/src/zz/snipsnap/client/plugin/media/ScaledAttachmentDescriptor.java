/*
 * Created on Mar 24, 2005
 */
package zz.snipsnap.client.plugin.media;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import zz.snipsnap.client.core.AttachmentDescriptor;

/**
 * Descriptor for an attachment and its associated thumbnails
 * @author gpothier
 */
public class ScaledAttachmentDescriptor extends AttachmentDescriptor
{
	private List<ThumbnailDescriptor> itsThumbnails = new ArrayList<ThumbnailDescriptor>();

	public ScaledAttachmentDescriptor()
	{
	}

	public ScaledAttachmentDescriptor(String aName, String aContentType, long aSize, Date aModificationDate, String aLocation)
	{
		super(aName, aContentType, aSize, aModificationDate, aLocation);
	}

	public List<ThumbnailDescriptor> getThumbnails()
	{
		return itsThumbnails;
	}
	
	
	
}
