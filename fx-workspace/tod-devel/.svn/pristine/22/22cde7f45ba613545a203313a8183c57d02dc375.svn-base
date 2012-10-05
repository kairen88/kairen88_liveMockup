/*
 * Created on Mar 23, 2005
 */
package zz.snipsnap.plugin.media;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.snipsnap.net.ServletPlugin;

public class MediaPlugin implements ServletPlugin
{
	/**
	 * The maximum allowed thumbnail size. This limit permits to prevent an attacker from 
	 * filling the hard disk with huge thumbnails.
	 */
	public static final int MAX_THUMBNAIL_SIZE = 800;

	public String getPath()
	{
		return MediaPluginConstants.PLUGIN_PATH;
	}

	public void service(HttpServletRequest aRequest, HttpServletResponse aResponse) 
	throws IOException, ServletException
	{
		try
		{
			String theQueryName = aRequest.getParameter(MediaPluginConstants.PARAM_QUERY);
			
			AbstractRequestProcessor theProcessor = 
				RequestProcessorFactory.getInstance().createProcessor(theQueryName);
			
			theProcessor.process(aRequest, aResponse);
		}
		catch (MediaPluginException e)
		{
			aResponse.setContentType("text/plain");

			ServletOutputStream theStream = aResponse.getOutputStream();
			theStream.println(e.getMessage());
			
			aResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
			aResponse.flushBuffer();
		}
	}
}
