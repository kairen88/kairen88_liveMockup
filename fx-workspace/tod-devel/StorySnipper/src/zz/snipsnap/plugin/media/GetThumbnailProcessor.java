/*
 * Created on Mar 24, 2005
 */
package zz.snipsnap.plugin.media;

import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.snipsnap.app.Application;
import org.snipsnap.config.Configuration;
import org.snipsnap.container.Components;
import org.snipsnap.snip.Snip;
import org.snipsnap.snip.SnipSpaceFactory;
import org.snipsnap.snip.attachment.Attachment;
import org.snipsnap.snip.attachment.Attachments;
import org.snipsnap.snip.storage.UserStorage;
import org.snipsnap.user.User;

import zz.snipsnap.MIMEConstants;
import zz.utils.ui.thumbnail.ThumbnailUtils;

/**
 * Represents a request to the thumbnail plugin.
 * 
 * @author gpothier
 */
public class GetThumbnailProcessor extends AbstractRequestProcessor
{
	/**
	 * Maps MIME types to icon names.
	 */
	private static final Map<String, String> MIME_ICON = new HashMap<String, String>();
	static
	{
		MIME_ICON.put(MIMEConstants.TEXT_HTML, "html-icon.gif");
		MIME_ICON.put(MIMEConstants.TEXT_PLAIN, "txt-icon.gif");
		MIME_ICON.put(MIMEConstants.TEXT_XML, "xml-icon.gif");
		MIME_ICON.put(MIMEConstants.TEXT_CSS, "css-icon.gif");
		MIME_ICON.put(MIMEConstants.IMAGE_JPEG, "image-icon.gif");
		MIME_ICON.put(MIMEConstants.IMAGE_GIF, "image-icon.gif");
		MIME_ICON.put(MIMEConstants.IMAGE_PNG, "image-icon.gif");
		MIME_ICON.put(MIMEConstants.APP_JAR, "java-icon.gif");
		MIME_ICON.put(MIMEConstants.APP_ZIP, "zip-icon.gif");
	}

	private static final String NO_ICON = "no-icon.gif";

	
	public static final String REQUEST_NAME = "get-thumbnail";
	public static final Parameter SNIP_NAME = new Parameter("snip", "Name of the snip where the file is searched", true);
	public static final Parameter FILE_NAME = new Parameter("file", "Name of the file for which to get a thumbnail", true);
	public static final Parameter MAX_SIZE = new Parameter("size", "Maximum size of the thumbnail", false);
	
	public static final Parameter[] PARAMETERS = {SNIP_NAME, FILE_NAME, MAX_SIZE};
	
	private String itsSnipName;
	private String itsFileName;
	private int itsMaxSize;

	private Snip itsSnip;
	private Attachment itsOriginalAttachment;

	public Parameter[] getParameters()
	{
		return PARAMETERS;
	}
	
	public void process() throws MediaPluginException, IOException
	{
		itsSnipName = getParamAsString(SNIP_NAME, null);
		itsFileName = getParamAsString(FILE_NAME, null);
		itsMaxSize = getParamAsInt(MAX_SIZE, 200);
		if (itsMaxSize > MediaPlugin.MAX_THUMBNAIL_SIZE) throw new MediaPluginException("Thumbnails can't be bigger that "+MediaPlugin.MAX_THUMBNAIL_SIZE);
		
		itsSnip = getSpace().load(itsSnipName);
		if (itsSnip == null) throw new MediaPluginException("Snip not found: " + itsSnipName);

		// We set the current user to the owner of the snip so as to be able to create
		// thumbnails if necessary
		String theUserName = itsSnip.getOUser();
		UserStorage theUserStorage = (UserStorage) Components.getComponent(UserStorage.class);
		User theUser = theUserStorage.storageLoad(theUserName);
		Application.get().setUser(theUser);
		
		Attachments theAttachments = itsSnip.getAttachments();
		itsOriginalAttachment = theAttachments.getAttachment(itsFileName);
		if (itsOriginalAttachment == null) throw new MediaPluginException("Attachment not found: " + itsFileName);

		processImageResult(getThumbnail());
	}

	private ImageResult getThumbnail() throws IOException, MediaPluginException
	{
		String theType = itsOriginalAttachment.getContentType();

		// We check if we have an image. If not, we will return an icon.
		if (theType.startsWith("image")) return getScaledImage();
		else
		{
			String theIconName = MIME_ICON.get(theType);
			if (theIconName == null) theIconName = NO_ICON;

			Configuration theConfiguration = Application.get().getConfiguration();
			Snip theThemeSnip = getSpace().load("SnipSnap/themes/" + theConfiguration.getTheme());
			Attachment theAttachment = theThemeSnip.getAttachments().getAttachment(theIconName);
			if (theAttachment == null) throw new MediaPluginException("Attachment not found");
			
			return new AttachmentImageResult(theAttachment);
		}

	}

	private ImageResult getScaledImage() throws IOException
	{
		String theOriginalName = itsOriginalAttachment.getName();
		String theScaledName = MediaPluginConstants.THUMBNAIL_PREFIX + itsMaxSize + "_" + theOriginalName;

		Attachment theScaledAttachment = itsSnip.getAttachments().getAttachment(theScaledName);
		if (theScaledAttachment == null
				|| itsOriginalAttachment.getDate().after(theScaledAttachment.getDate()))
		{
			theScaledAttachment = createScaledImage(theScaledName);
		}

		return new AttachmentImageResult(theScaledAttachment);
	}

	private Attachment createScaledImage(String aName) throws IOException
	{
		InputStream theInputStream = getAttachmentStorage().getInputStream(itsOriginalAttachment);
		BufferedImage theScaledImage = ThumbnailUtils.createScaledImage(
				theInputStream, 
				itsMaxSize, 
				false, // No alpha because of JPEG output bug
				RenderingHints.VALUE_INTERPOLATION_BICUBIC); 
		
		// If the original image is already smaller than required, then just return it.
		if (theScaledImage == null) return itsOriginalAttachment;
		
		// Otherwise we create a new Attachment
		Attachment theScaledAttachment = itsSnip.getAttachments().addAttachment(
				aName,
	            "image/jpeg", 
	            0, 
				itsSnip.getName()+"/"+aName);
		
		ByteArrayOutputStream theByteOutput = new ByteArrayOutputStream();
//		JPEGWriter.compressJpegFile(theRenderedImage, theByteOutput, 0.5f);
		ImageIO.write(theScaledImage, "jpeg", theByteOutput);
		
		OutputStream theOutput = null;
		try
		{
			theOutput = getAttachmentStorage().getOutputStream(theScaledAttachment);
			theByteOutput.writeTo(theOutput);
			theOutput.flush();
			theScaledAttachment.setSize(theByteOutput.size());
			itsSnip.getAttachments().addAttachment(theScaledAttachment);
			SnipSpaceFactory.getInstance().store(itsSnip);
		}
		finally
		{
			theOutput.close();
		}
		
		return theScaledAttachment;
	}

}
