/*
 * Created on Mar 28, 2005
 */
package zz.snipsnap.plugin.media;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.snipsnap.container.Components;
import org.snipsnap.snip.SnipSpace;
import org.snipsnap.snip.SnipSpaceFactory;
import org.snipsnap.snip.attachment.storage.AttachmentStorage;


/**
 * 
 * @author gpothier
 */
public abstract class AbstractRequestProcessor
{
	private static SnipSpace SPACE;
	private static AttachmentStorage ATTACHMENT_STORAGE;

	private Map<Parameter, String> itsParameterValues = 
		new HashMap<AbstractRequestProcessor.Parameter, String>();
	
	private HttpServletRequest itsRequest;
	private HttpServletResponse itsResponse;
	
	public AbstractRequestProcessor()
	{
	}
	
	protected static SnipSpace getSpace()
	{
		if (SPACE == null) SPACE = SnipSpaceFactory.getInstance();
		return SPACE;
	}
	
	protected static AttachmentStorage getAttachmentStorage()
	{
		if (ATTACHMENT_STORAGE == null) ATTACHMENT_STORAGE =
			(AttachmentStorage) Components.getComponent(AttachmentStorage.class);
		
		return ATTACHMENT_STORAGE;
	}
	
	/**
	 * Returns the set of parameters needed by this processor 
	 */
	public abstract Parameter[] getParameters();
	
	public void process(HttpServletRequest aRequest, HttpServletResponse aResponse) throws MediaPluginException, IOException
	{
		itsRequest = aRequest;
		itsResponse = aResponse;
		
		for (Parameter theParameter : getParameters())
		{
			String theName = theParameter.getName();
			String theValue = aRequest.getParameter(theName);
			if (theValue != null) itsParameterValues.put(theParameter, theValue);
			else if (theParameter.isMandatory()) throw new MediaPluginException("Mandatory parameter "+theName+" missing.");
		}
		
		process();
	}
	
	protected abstract void process() throws MediaPluginException, IOException;
	
	/**
	 * Sends the response of this request as an image.
	 */
	protected void processImageResult (ImageResult aImageResult) throws IOException 
	{
		itsResponse.setContentLength(aImageResult.getSize());
		itsResponse.setContentType(aImageResult.getContentType());
		aImageResult.output(itsResponse.getOutputStream());
		itsResponse.setStatus(HttpServletResponse.SC_OK);
		itsResponse.flushBuffer();		
	}
	

	
	protected String getParamAsString(Parameter aParameter, String aDefault)
	{
		String theString = itsParameterValues.get(aParameter);
		return theString != null ? theString : aDefault;
	}
	
	protected int getParamAsInt(Parameter aParameter, int aDefault)
	{
		String theString = itsParameterValues.get(aParameter);
		try
		{
			return Integer.parseInt(theString);
		}
		catch (NumberFormatException e)
		{
			return aDefault;
		}
	}
	
	protected ServletOutputStream getOutputStream() throws IOException
	{
		return itsResponse.getOutputStream();
	}
	
	/**
	 * Validates the http response with an ok code, and flushes the buffer.
	 */
	protected void sendOk() throws IOException
	{
		send(HttpServletResponse.SC_OK);
	}
	
	/**
	 * Flushes buffers and sends the given response code.
	 */
	protected void send(int aStatusCode) throws IOException
	{
		itsResponse.setStatus(aStatusCode);
		itsResponse.flushBuffer();		
	}
	
	/**
	 * Sends a plain text message as a response
	 */
	protected void sendMessage (String aMessage) throws IOException
	{
		itsResponse.setContentType("text/plain");

		ServletOutputStream theStream = getOutputStream();
		theStream.println(aMessage);		
	}

	protected HttpServletRequest getRequest()
	{
		return itsRequest;
	}

	protected HttpServletResponse getResponse()
	{
		return itsResponse;
	}
	
	protected void setContentType(String aContentType)
	{
		itsResponse.setContentType(aContentType);
	}
	
	protected void setContentLength(int aContentLength)
	{
		itsResponse.setContentLength(aContentLength);
	}
	
	public static class Parameter
	{
		private String itsName;
		private boolean itsMandatory;
		private final String itsDescription;

		public Parameter(String aName, String aDescription, boolean aMandatory)
		{
			itsName = aName;
			itsDescription = aDescription;
			itsMandatory = aMandatory;
		}

		public String getName()
		{
			return itsName;
		}

		public String getDescription()
		{
			return itsDescription;
		}
		
		public boolean isMandatory()
		{
			return itsMandatory;
		}
		
		public String toString()
		{
			return getName();
		}
	}
}
