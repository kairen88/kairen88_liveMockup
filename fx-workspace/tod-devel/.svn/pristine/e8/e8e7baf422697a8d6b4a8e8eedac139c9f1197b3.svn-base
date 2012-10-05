/*
 * Created on Mar 24, 2005
 */
package zz.snipsnap.plugin.media;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.snipsnap.snip.Snip;
import org.snipsnap.snip.attachment.Attachment;

import zz.snipsnap.MIMEConstants;

/**
 * This class processes the {@link zz.snipsnap.plugin.media.MediaPluginConstants#QUERY_LIST_MAIN} request.
 * It returns the name of each main attachment of a snip.
 * @author gpothier
 */
public class ListMediaProcessor extends AbstractRequestProcessor
{
	public static final String REQUEST_NAME = "list-media";
	public static final Parameter SNIP_NAME = new Parameter("snip", "Name of the snip of which to retrieve media attachments", true);
	public static final Parameter FORMAT = new Parameter("format", "Output format", false);
	
	public static final Parameter[] PARAMETERS = {SNIP_NAME, FORMAT};
	private String itsSnipName;

	
	public Parameter[] getParameters()
	{
		return PARAMETERS;
	}

	protected void process() throws MediaPluginException, IOException
	{
		itsSnipName = getParamAsString(SNIP_NAME, null);
		Snip theSnip = getSpace().load(itsSnipName);
		if (theSnip == null) throw new MediaPluginException("Snip not found: " + itsSnipName);

		List<Attachment> theAttachments = theSnip.getAttachments().getAll();

		setContentType(MIMEConstants.TEXT_PLAIN);
		ServletOutputStream theStream = getOutputStream();
		for (Attachment theAttachment : theAttachments)
		{
			String theName = theAttachment.getName();
			if (! theName.startsWith(MediaPluginConstants.THUMBNAIL_PREFIX)) theStream.println(theName);
		}
		
		sendOk();
	}
}
