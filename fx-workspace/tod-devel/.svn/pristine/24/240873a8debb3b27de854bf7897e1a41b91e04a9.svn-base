/*
 * Created on Mar 28, 2005
 */
package zz.snipsnap.plugin.media;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.jibx.runtime.JiBXException;
import org.snipsnap.snip.Snip;
import org.snipsnap.snip.attachment.Attachment;
import org.snipsnap.snip.attachment.Attachments;
import org.snipsnap.snip.attachment.storage.AttachmentStorage;

import zz.snipsnap.MIMEConstants;
import zz.snipsnap.utils.MetadataUtils;
import zz.snipsnap.utils.jibx.Binder;
import zz.snipsnap.utils.jibx.JiBXListWrapper;

public class GetMediaInfoProcessor extends AbstractRequestProcessor
{
	public static final String REQUEST_NAME = "get-media-info";
	public static final Parameter SNIP_NAME = new Parameter("snip", "Name of the snip where the file is searched", true);
	public static final Parameter FILE_NAME = new Parameter("file", "Name of the file for which to retrieve information. If not specified, information is retrieved for all files in the snip", false);
	
	public static final Parameter[] PARAMETERS = {SNIP_NAME, FILE_NAME};

	public Parameter[] getParameters()
	{
		return PARAMETERS;
	}

	protected void process() throws MediaPluginException, IOException
	{
		String theSnipName = getParamAsString(SNIP_NAME, null);
		String theFileName = getParamAsString(FILE_NAME, null);
		
		Snip theSnip = getSpace().load(theSnipName);
		if (theSnip == null) throw new MediaPluginException("Snip not found: " + theSnipName);

		Attachments theAttachments = theSnip.getAttachments();

		AttachmentStorage theAttachmentStorage = getAttachmentStorage();
		
		ServletOutputStream theResponseStream = getOutputStream();
		
		Object theResult;
		
		if (theFileName != null)
		{
			Attachment theAttachment = theAttachments.getAttachment(theFileName);
			InputStream theInputStream = theAttachmentStorage.getInputStream(theAttachment);
			MediaInfo theInfo = MetadataUtils.getInfo(theAttachment.getName(), theInputStream, theAttachment.getSize());
			
			theResult = theInfo;
		}
		else
		{
			List<Attachment> theList = theAttachments.getAll();
			List<MediaInfo> theInfosList = new ArrayList<MediaInfo>(theList.size());
			
			for (Attachment theAttachment : theList)
			{
				String theName = theAttachment.getName();
				if (theName.startsWith(MediaPluginConstants.THUMBNAIL_PREFIX)) continue;
				
				InputStream theInputStream = theAttachmentStorage.getInputStream(theAttachment);
				MediaInfo theInfo = MetadataUtils.getInfo(theAttachment.getName(), theInputStream, theAttachment.getSize());
				theInfosList.add(theInfo);
			}

			theResult = new JiBXListWrapper(theInfosList);
		}
		
		try
		{
			setContentType(MIMEConstants.TEXT_XML);
			Binder.getInstance().marshall(theResult, new OutputStreamWriter(theResponseStream));
			sendOk();
		}
		catch (JiBXException e)
		{
			e.printStackTrace();
			setContentType(MIMEConstants.TEXT_PLAIN);
			e.printStackTrace(new PrintStream(theResponseStream));
			send(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
	
}
