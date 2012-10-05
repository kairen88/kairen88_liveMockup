/*
 * Created on Mar 24, 2005
 */
package zz.snipsnap.client.plugin.media;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.jibx.runtime.JiBXException;

import zz.snipsnap.MIMEConstants;
import zz.snipsnap.client.core.SnipSnapSpace;
import zz.snipsnap.plugin.media.GetMediaInfoProcessor;
import zz.snipsnap.plugin.media.GetThumbnailProcessor;
import zz.snipsnap.plugin.media.ListMediaProcessor;
import zz.snipsnap.plugin.media.MediaInfo;
import zz.snipsnap.plugin.media.MediaPluginConstants;
import zz.snipsnap.plugin.media.MediaPluginUtils;
import zz.snipsnap.plugin.media.ShowMediaProcessor;
import zz.snipsnap.utils.jibx.Binder;
import zz.snipsnap.utils.jibx.JiBXListWrapper;


/**
 * Provides methods that permit to access the thumbnail plugin in a convenient way
 * @author gpothier
 */
public class MediaPluginClient
{
	private SnipSnapSpace itsSpace;
	
	public MediaPluginClient(SnipSnapSpace aSpace)
	{
		itsSpace = aSpace;
	}
	
	public URI getMediaViewerUri (String aSnipName, String aFileName)
	{
		return itsSpace.resolve(MediaPluginUtils.createShowMediaQuery(aSnipName, aFileName, false));
	}
	
	/**
	 * Returns the URL at which a given thumbnail can be retrieved
	 * @param aSnipName Name of the snip to which the original file is attached
	 * @param aFileName Name of the original file
	 * @param aMaxSize Maximum size of the thumbnail
	 */
	public URI getThumbnailURI (String aSnipName, String aFileName, int aMaxSize)
	{
		return itsSpace.resolve(MediaPluginUtils.createThumbnailQuery(aSnipName, aFileName, aMaxSize));
	}
	
	/**
	 * Returns a given thumbnail. Parameters are the same as for {@link #getThumbnailURL(String, String, int)}
	 */
	public BufferedImage getThumbnail (String aSnipname, String aFileName, int aMaxSize) throws IOException
	{
		URL theUrl = getThumbnailURI(aSnipname, aFileName, aMaxSize).toURL();
		InputStream theStream = theUrl.openConnection().getInputStream();
		
		BufferedImage theImage = ImageIO.read(theStream);
		return theImage;
	}
	
	/**
	 * Returns the list of the names of main attachments of a snip (omitting thumbnails)
	 */
	public List<String> getAttachmentsName (String aSnipName) throws IOException
	{
		URL theListUrl = itsSpace.resolve(MediaPluginUtils.createListMediaQuery(aSnipName)).toURL();
		
		URLConnection theConnection = theListUrl.openConnection();
		
		String theContentType = theConnection.getContentType();
		if (! MIMEConstants.TEXT_PLAIN.equals(theContentType)) return null;

		BufferedReader theReader = new BufferedReader(new InputStreamReader(theConnection.getInputStream()));
		
		List<String> theResult = new ArrayList<String>();
		while (true)
		{
			String theLine = theReader.readLine();
			if (theLine == null) break;
			
			theResult.add (theLine);
		}
		
		return theResult;
	}

	/**
	 * Returns the list of {@link zz.snipsnap.plugin.media.MediaInfo} of main attachments 
	 * of a snip (omitting thumbnails)
	 */
	public List<MediaInfo> getAttachmentsInfo (String aSnipName) throws IOException
	{
		URL theListUrl = itsSpace.resolve(MediaPluginUtils.createMediaInfoQuery(aSnipName)).toURL();
		
		URLConnection theConnection = theListUrl.openConnection();
		
		String theContentType = theConnection.getContentType();
		if (! MIMEConstants.TEXT_XML.equals(theContentType)) return null;

		BufferedReader theReader = new BufferedReader(new InputStreamReader(theConnection.getInputStream()));

		try
		{
			return ((JiBXListWrapper) Binder.getInstance().unmarshall(theReader)).getList();
		}
		catch (JiBXException e)
		{
			throw new RuntimeException("JiBX error", e);
		}
	}
}
