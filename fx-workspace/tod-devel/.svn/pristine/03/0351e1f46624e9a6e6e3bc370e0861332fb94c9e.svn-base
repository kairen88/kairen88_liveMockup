/*
 * Created on Mar 23, 2005
 */
package zz.snipsnap.client.core;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class Test
{
	private final SnipSnapSpace itsSpace;

	public static void main(String[] args) throws IOException
	{
		SnipSnapSpace theSpace = new SnipSnapSpace("http://localhost:8668/", "debug", "debug");
		Test theTest = new Test(theSpace);
		theTest.testAttachments();
		theTest.testChildren();
	}
	
	public Test(SnipSnapSpace aSpace)
	{
		itsSpace = aSpace;
	}
	
	public void testChildren()
	{
		SnipsManager theSnipsManager = itsSpace.getSnipsManager();
		
		Iterable<String> theChildren = theSnipsManager.getSnipChildren("tesst");
		for (String theString : theChildren)
		{
			System.out.println(theString);
		}
	}
	
	public void testAttachments() throws IOException
	{
		AttachmentsManager theAttachmentsManager = itsSpace.getAttachmentsManager();
		
		printAttachments(theAttachmentsManager, "tesst");
		
		AttachmentDescriptor theAttachment = theAttachmentsManager.attach("tesst", ".project", "text/plain", new File(".project"), null);
		
		printAttachments(theAttachmentsManager, "tesst");
		
		theAttachmentsManager.detach("tesst", theAttachment);

		printAttachments(theAttachmentsManager, "tesst");
		
	}
	
	private static void printAttachments (AttachmentsManager aManager, String aSnipName)
	{
		Iterable<AttachmentDescriptor> theAttachments = aManager.getAttachments(aSnipName);
		
		for (AttachmentDescriptor theAttachment : theAttachments)
		{
			System.out.println(theAttachment.getName());
		}
	}
	
	

}
