/*
 * Created on Mar 25, 2005
 */
package zz.snipsnap.storysnipper.ui.upload;

import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import zz.snipsnap.storysnipper.Resources;
import zz.utils.ui.thumbnail.AsyncThumbnailCache;
import zz.utils.ui.thumbnail.ProcessingOrder;
import zz.utils.ui.thumbnail.ThumbnailUtils;

/**
 * Manages thumbnails of local filesystem items
 * @author gpothier
 */
public class FSThumbnailsCache extends AsyncThumbnailCache<File>
{
	private static FSThumbnailsCache INSTANCE = new FSThumbnailsCache();

	public static FSThumbnailsCache getInstance()
	{
		return INSTANCE;
	}

	private FSThumbnailsCache()
	{
		super (100, 10, ProcessingOrder.LIFO); 
	}
	
	public BufferedImage getThumbnail(File aFile, int aMaxSize)
	{
		BufferedImage theDefaultImage = getDefaultImage(aFile, aMaxSize);
		return theDefaultImage != null ? theDefaultImage : super.getThumbnail(aFile, aMaxSize);
	}

	protected BufferedImage createThumbnail (File aFile, int aMaxSize)
	{
		System.out.println("Creating thumbnail for: "+aFile);
		
		BufferedImage theDefaultImage = null;
		try
		{
			if (aFile == null) theDefaultImage = Resources.LOADING;
			else if (aFile.isDirectory()) theDefaultImage = Resources.FOLDER;
			else 
			{
				FileInputStream theInputStream = new FileInputStream(aFile);
				
				BufferedImage theImage = ThumbnailUtils.createScaledImage(
						theInputStream, 
						aMaxSize, 
						true, 
						RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
				
				if (theImage != null) return theImage;
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
			theDefaultImage = Resources.BROKEN;
		}
		
		if (theDefaultImage == null) theDefaultImage = Resources.UNKNOWN;
		
		return Resources.getInstance().getThumbnail(theDefaultImage, aMaxSize);
	}
	
	/**
	 * If the specified file can be determined quickly to have a fixed thumbnail, return it 
	 * immediately. Otherwise returns null.
	 */
	private BufferedImage getDefaultImage (File aFile, int aMaxSize)
	{
		BufferedImage theDefaultImage = null;
		if (aFile.isDirectory()) theDefaultImage = Resources.FOLDER;
		
		if (theDefaultImage == null) return null;
		else return Resources.getInstance().getThumbnail(theDefaultImage, aMaxSize);
		
	}

}
