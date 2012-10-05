/*
 * Created on Apr 3, 2005
 */
package zz.snipsnap.utils;

import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import zz.snipsnap.plugin.media.MediaInfo;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifDirectory;
import com.drew.metadata.jpeg.JpegDirectory;

/**
 * Simplifies the retrieveal of media info from files or streams.
 * @author gpothier
 */
public class MetadataUtils
{
	public static MediaInfo getInfo (File aFile) throws FileNotFoundException
	{
		return getInfo(aFile.getName(), new FileInputStream(aFile), aFile.length());
	}
	
	/**
	 * Returns the media info of the content provided by the input stream.
	 * @param aSize Total size of the stream's content
	 * @return A MediaInfo object, of which as many fields as possible are
	 * initialized.
	 */
	public static MediaInfo getInfo(String aName, InputStream aInputStream, long aSize)
	{
		MediaInfo theMediaInfo = new MediaInfo();
		theMediaInfo.setName(aName);
		theMediaInfo.setSize(aSize);
		
		if (getExifInfo(theMediaInfo, aInputStream)) return theMediaInfo;
		// else if (...) return theMediaInfo
		else return theMediaInfo;
	}
	
	private static boolean getExifInfo(MediaInfo aMediaInfo, InputStream aInputStream) 
	{
		try
		{
			Metadata theMetadata = JpegMetadataReader.readMetadata(aInputStream);
			Directory theJpegDirectory = theMetadata.getDirectory(JpegDirectory.class);
			Directory theExifDirectory = theMetadata.getDirectory(ExifDirectory.class);

			int theX = -1;
			int theY = -1;
			if (theJpegDirectory.containsTag(JpegDirectory.TAG_JPEG_IMAGE_WIDTH))
				theX = theJpegDirectory.getInt(JpegDirectory.TAG_JPEG_IMAGE_WIDTH);
			
			if (theJpegDirectory.containsTag(JpegDirectory.TAG_JPEG_IMAGE_HEIGHT))
				theY = theJpegDirectory.getInt(JpegDirectory.TAG_JPEG_IMAGE_HEIGHT);
			
			if (theX >= 0 && theY >= 0)
				aMediaInfo.setResolution (new Dimension(theX, theY));

			if (theExifDirectory.containsTag(ExifDirectory.TAG_DATETIME))
				aMediaInfo.setDate (theExifDirectory.getDate(ExifDirectory.TAG_DATETIME));
			
			if (theExifDirectory.containsTag(ExifDirectory.TAG_EXPOSURE_TIME))
				aMediaInfo.setDuration (theExifDirectory.getDouble(ExifDirectory.TAG_EXPOSURE_TIME));
			
			return true;
		}
		catch (JpegProcessingException e)
		{
			System.out.println("Retrieving exif data for "+aMediaInfo.getName()+": "+e.getMessage());
		}
		catch (MetadataException e)
		{
			System.out.println("Retrieving exif data for "+aMediaInfo.getName()+": "+e.getMessage());
		}
		return false;
	}
}
