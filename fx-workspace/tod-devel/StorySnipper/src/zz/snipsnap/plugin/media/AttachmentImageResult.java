package zz.snipsnap.plugin.media;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletOutputStream;

import org.snipsnap.container.Components;
import org.snipsnap.snip.attachment.Attachment;
import org.snipsnap.snip.attachment.storage.AttachmentStorage;


/**
 * This image result fetches image data from an attachment.
 */
public class AttachmentImageResult extends ImageResult
{
	private final Attachment itsAttachment;

	public AttachmentImageResult(Attachment aAttachment)
	{
		super (aAttachment.getContentType(), (int) aAttachment.getSize());
		itsAttachment = aAttachment;
	}

	public void output(ServletOutputStream aStream) throws IOException
	{
		AttachmentStorage theStorage = (AttachmentStorage) Components.getComponent(AttachmentStorage.class);
		InputStream theStream = theStorage.getInputStream(itsAttachment);
		
		int n;
		
		byte[] theBuffer = new byte[1024];
		while ((n = theStream.read(theBuffer)) >= 0)
		{
			if (n > 0) aStream.write(theBuffer, 0, n);
		}
	}
}