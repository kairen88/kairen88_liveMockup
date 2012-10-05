/*
 * Created on Mar 24, 2005
 */
package zz.snipsnap.client.plugin.media;

import java.util.Date;

import zz.snipsnap.client.core.AttachmentDescriptor;

public class ThumbnailDescriptor extends AttachmentDescriptor
{
	private int itsImageSize;

	public ThumbnailDescriptor()
	{
	}

	public ThumbnailDescriptor(String aName, String aContentType, long aSize, Date aModificationDate, String aLocation, int aImageSize)
	{
		super(aName, aContentType, aSize, aModificationDate, aLocation);
		itsImageSize = aImageSize;
	}

	public int getImageSize()
	{
		return itsImageSize;
	}
	
	public void setImageSize(int aImageSize)
	{
		itsImageSize = aImageSize;
	}
}
