/*
 * Created on Mar 28, 2005
 */
package zz.snipsnap.plugin.media;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import zz.snipsnap.storysnipper.Resources;

public class GetResourceProcessor extends AbstractRequestProcessor
{
	public static final String REQUEST_NAME = "get-resource";
	public static final Parameter FILE_NAME = new Parameter("name", "", false);
	
	public static final Parameter[] PARAMETERS = {FILE_NAME};

	public Parameter[] getParameters()
	{
		return PARAMETERS;
	}

	protected void process() throws MediaPluginException, IOException
	{
		String theFileName = getParamAsString(FILE_NAME, null);
		
		InputStream theStream = Resources.class.getResourceAsStream(theFileName);
		if (theStream == null) 
		{
			send(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		ServletOutputStream theOutputStream = getOutputStream();
		
		int n;
		byte[] theBuffer = new byte[4096];
		while ((n = theStream.read(theBuffer)) >= 0)
		{
			theOutputStream.write(theBuffer, 0, n);
		}
		
		sendOk();
	}
	
}
