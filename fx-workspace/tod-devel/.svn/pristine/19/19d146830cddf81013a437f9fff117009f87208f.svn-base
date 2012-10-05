/*
 * Created on Mar 25, 2005
 */
package zz.snipsnap.storysnipper;

import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import zz.utils.ui.thumbnail.SyncThumbnailCache;
import zz.utils.ui.thumbnail.ThumbnailUtils;

public class Resources extends SyncThumbnailCache<BufferedImage>
{
	private static Resources INSTANCE = new Resources();

	public static Resources getInstance()
	{
		return INSTANCE;
	}

	private Resources()
	{
	} 
	
	public static final BufferedImage FOLDER = readImage("filesystems/folder.png");
	public static final BufferedImage BROKEN = readImage("filesystems/file_broken.png");
	public static final BufferedImage LOADING = readImage("mimetypes/misc.png");
	public static final BufferedImage UNKNOWN = readImage("mimetypes/unknown.png");
	
	public static final BufferedImage ZOOMIN = readImage("actions/viewmag+.png");
	public static final BufferedImage ZOOMOUT = readImage("actions/viewmag-.png");
	
	private static BufferedImage readImage(String aName)
	{
		try
		{
			System.out.println("Loading resource: "+aName+" ("+Resources.class.getClassLoader()+")");
			InputStream theStream = Resources.class.getResourceAsStream (aName);
			return theStream != null ? ImageIO.read(theStream) : null;
		}
		catch (Exception e)
		{
			System.out.println("Exception while reading "+aName);
			e.printStackTrace();
			return null;
		}
	}

	protected BufferedImage createThumbnail(BufferedImage aImage, int aMaxSize)
	{
		return ThumbnailUtils.createScaledImage(
				aImage, 
				aMaxSize, 
				true,
				RenderingHints.VALUE_INTERPOLATION_BICUBIC);
	}
	
	
}
