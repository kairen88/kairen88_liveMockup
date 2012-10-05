package zz.snipsnap.plugin.media;

import java.io.IOException;

import javax.servlet.ServletOutputStream;

/**
 * Abstract notion of an image response.
 * @author gpothier
 */
public abstract class ImageResult
{
	String itsContentType;
	int itsSize;

	public ImageResult(String aContentType, int aSize)
	{
		itsContentType = aContentType;
		itsSize = aSize;
	}

	/**
	 * Returns the MIME type of the image.
	 */
	public String getContentType()
	{
		return itsContentType;
	}
	
	/**
	 * Returns the size of the image data.
	 */
	public int getSize()
	{
		return itsSize;
	}

	/**
	 * Writes the image data to an output stream.
	 */
	public abstract void output (ServletOutputStream aStream) throws IOException;
}