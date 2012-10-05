/*
 * Created on Apr 9, 2005
 */
package zz.snipsnap.plugin.media;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Utilities for working with the Media Plugin
 * @author gpothier
 */
public class MediaPluginUtils
{
	/**
	 * Creates a query URI for the plugin.
	 * @param aQuery The query type
	 * @param aArgs Additional argument name/value pairs.
	 */
	private static String createQuery (String aQuery, String... aArgs) 
	{
		try
		{
			StringBuilder theBuilder = new StringBuilder("plugin/"+MediaPluginConstants.PLUGIN_PATH+"?");
			
			theBuilder.append(MediaPluginConstants.PARAM_QUERY+"="+aQuery);
			
			int theNArgs = aArgs.length;
			assert theNArgs % 2 != 0;
			
			for (int i=0;i<theNArgs;i += 2) 
			{
				String theParamName = URLEncoder.encode(aArgs[i], "UTF-8");
				String theParamValue = URLEncoder.encode(aArgs[i+1], "UTF-8");
				
				theBuilder.append("&"+theParamName+"="+theParamValue);
			}
		
			return theBuilder.toString();
		}
		catch (UnsupportedEncodingException e)
		{
			throw new RuntimeException(e);
		}		
	}

	/**
	 * Creates a show-media request
	 * @param aSource Source (or URI) of the media.
	 */
	public static String createShowMediaQuery (String aSnipName, String aFileName, boolean aHiQuality)
	{
		return createQuery(
				ShowMediaProcessor.REQUEST_NAME,
				ShowMediaProcessor.SNIP.getName(), aSnipName,
				ShowMediaProcessor.FILE.getName(), aFileName,
				ShowMediaProcessor.QUALITY.getName(), aHiQuality ? "hi" : "lo");
		
	}
	
	/**
	 * Creates a show-media request
	 * @param aSource Source (or URI) of the media.
	 */
	public static String createShowMediaQuery (String aSnipName, String aFileName, boolean aHiQuality, int aSlideshowIndex)
	{
		return createQuery(
				ShowMediaProcessor.REQUEST_NAME,
				ShowMediaProcessor.SNIP.getName(), aSnipName,
				ShowMediaProcessor.FILE.getName(), aFileName,
				ShowMediaProcessor.QUALITY.getName(), aHiQuality ? "hi" : "lo",
				ShowMediaProcessor.SLIDESHOW_INDEX.getName(), ""+aSlideshowIndex);
		
	}
	
	/**
	 * Returns the URL at which a given thumbnail can be retrieved
	 * @param aSnipName Name of the snip to which the original file is attached
	 * @param aFileName Name of the original file
	 * @param aMaxSize Maximum size of the thumbnail
	 */
	public static String createThumbnailQuery (String aSnipName, String aFileName, int aMaxSize)
	{
		return createQuery(
				GetThumbnailProcessor.REQUEST_NAME,
				GetThumbnailProcessor.SNIP_NAME.getName(), aSnipName,
				GetThumbnailProcessor.FILE_NAME.getName(), aFileName,
				GetThumbnailProcessor.MAX_SIZE.getName(), ""+aMaxSize);
	}
	
	public static String createListMediaQuery (String aSnipName)
	{
		return createQuery(
				ListMediaProcessor.REQUEST_NAME,
				ListMediaProcessor.SNIP_NAME.getName(), aSnipName);
	}
	
	public static String createMediaInfoQuery (String aSnipName) 
	{
		return createQuery(
				GetMediaInfoProcessor.REQUEST_NAME,
				ListMediaProcessor.SNIP_NAME.getName(), aSnipName);
	}

}
