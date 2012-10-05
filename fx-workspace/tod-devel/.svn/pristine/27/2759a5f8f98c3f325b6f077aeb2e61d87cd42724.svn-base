/*
 * Created on Mar 24, 2005
 */
package zz.snipsnap.client.plugin.media;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import zz.snipsnap.client.core.SnipSnapSpace;

public class TestClient
{
	public static void main(String[] args) throws IOException
	{
		SnipSnapSpace theSpace = new SnipSnapSpace("http://localhost:8668/", "debug", "debug");
		MediaPluginClient theClient = new MediaPluginClient(theSpace);

		List<String> theAttachmentNames = theClient.getAttachmentsName("toto");
		for (String theString : theAttachmentNames)
		{
			System.out.println(theString);
		}
	}

}
