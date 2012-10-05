/*
 * Created on Apr 5, 2005
 */
package zz.snipsnap.plugin.media;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.List;

import org.snipsnap.app.Application;
import org.snipsnap.container.Components;
import org.snipsnap.snip.Snip;
import org.snipsnap.snip.attachment.Attachment;
import org.snipsnap.snip.attachment.Attachments;
import org.snipsnap.snip.storage.UserStorage;
import org.snipsnap.user.User;

import zz.snipsnap.MIMEConstants;
import zz.snipsnap.utils.AttachmentLocator;

/**
 * This processor returns an html page that displays a particular media.
 * For an image, it permits to zoom it in/out.
 * <p>
 * This processor also supports a slideshow mode. It works in conjunction with {@link zz.snipsnap.macro.media.SequenceMediaMacro}
 * @author gpothier
 */
public class ShowMediaProcessor extends AbstractRequestProcessor
{
	public static final String REQUEST_NAME = "show-media";


	public static final Parameter SNIP = new Parameter("snip", "Name of the snip that contains the media", true);
	public static final Parameter FILE = new Parameter("file", "Name of the media within the snip", true);
	
	public static final Parameter QUALITY = new Parameter("q", "Quality of the media. Can be 'hi' of 'lo'", false);
	
	
	public static final Parameter SLIDESHOW_INDEX = new Parameter("index", "Index of the media within the slideshow", false);
	public static final Parameter HOME_URL = new Parameter("home", "URL for the home of the slideshow.", false);
	
	public static final Parameter[] PARAMETERS = {SNIP, FILE, SLIDESHOW_INDEX, HOME_URL, QUALITY};

	public Parameter[] getParameters()
	{
		return PARAMETERS;
	}

	protected void process() throws MediaPluginException, IOException
	{
		String theSnipName = getParamAsString(SNIP, null);
		String theFileName = getParamAsString(FILE, null);
		String theQuality = getParamAsString(QUALITY, "lo");
		
		AttachmentLocator theLocator = new AttachmentLocator(theSnipName, theFileName);
		
		String theHomeUrl = getParamAsString(HOME_URL, null);
		
		int theSequenceIndex = getParamAsInt(SLIDESHOW_INDEX, -1);

		AttachmentLocator thePreviousLocator = null;
		AttachmentLocator theNextLocator = null;
		
		if (theSequenceIndex >= 0)
		{
			List<AttachmentLocator> theSlideshow = 
				(List<AttachmentLocator>) getRequest().getSession().getAttribute(MediaPluginConstants.ATTR_SLIDESHOW);
			
			if (theSlideshow != null)
			{
				theSlideshow.get(theSequenceIndex);
				
				if (theSequenceIndex > 0) thePreviousLocator = theSlideshow.get(theSequenceIndex-1);
				if (theSequenceIndex < theSlideshow.size()-1) theNextLocator = theSlideshow.get(theSequenceIndex+1);
			}
		}
		
		String theBase = Application.get().getConfiguration().getUrl()+"/";
		
		// TODO: temp. We need proper JSP integration
		PrintWriter out = new PrintWriter(new OutputStreamWriter(getOutputStream()));
		String theBaseUrl = Application.get().getConfiguration().getUrl();
		String theMediaPluginUrl = "plugin/media";
		String thePageTitle = "View image - " + theFileName;
		
		setContentType(MIMEConstants.TEXT_HTML);

		ShowMediaParams theParams = new ShowMediaParams(
				thePageTitle,
				theBase,
				theLocator,
				thePreviousLocator,
				theNextLocator,
				theQuality,
				theSequenceIndex,
				theHomeUrl);
		
		jsp (out, theParams);

		out.flush();
		
		sendOk();
	}
	
	private void jsp(PrintWriter out, ShowMediaParams params) throws MediaPluginException
	{
	      // HTML // begin [file="/showmedia.jsp";from=(0,50);to=(4,9)]
        out.write("\n<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\"> \n<html>\n\t<head>\n\t\t<title>");

      // end
      // begin [file="/showmedia.jsp";from=(4,12);to=(4,35)]
        out.print( params.getPageTitle() );
      // end
      // HTML // begin [file="/showmedia.jsp";from=(4,37);to=(6,14)]
        out.write("</title>\n\t\t<meta http-equiv=\"Content-Script-Type\" content=\"text/javascript\"> \n\t\t<base href=\"");

      // end
      // begin [file="/showmedia.jsp";from=(6,17);to=(6,35)]
        out.print( params.getBase() );
      // end
      // HTML // begin [file="/showmedia.jsp";from=(6,37);to=(10,40)]
        out.write("\"/>\n\t\t<script type=\"text/javascript\" src=\"plugin/media?query=get-resource&name=zoom.js\"></script>\n\t</head>\n\t\n\t<body onload=\"loadZoom(); fetchImages('");

      // end
      // begin [file="/showmedia.jsp";from=(10,43);to=(10,73)]
        out.print( params.getPreviousMediaUrl() );
      // end
      // HTML // begin [file="/showmedia.jsp";from=(10,75);to=(10,79)]
        out.write("', '");

      // end
      // begin [file="/showmedia.jsp";from=(10,82);to=(10,108)]
        out.print( params.getNextMediaUrl() );
      // end
      // HTML // begin [file="/showmedia.jsp";from=(10,110);to=(14,4)]
        out.write("')\">\n\t\n\t\t<table >\n\t\t\t<tr>\n\t\t\t\t");

      // end
      // begin [file="/showmedia.jsp";from=(14,6);to=(14,55)]
         if (params.hasPrevious() || params.hasNext()) { 
      // end
      // HTML // begin [file="/showmedia.jsp";from=(14,57);to=(16,6)]
        out.write("\n\t\t\t\t\t<td>\n\t\t\t\t\t\t");

      // end
      // begin [file="/showmedia.jsp";from=(16,8);to=(16,36)]
         if (params.hasPrevious()) {
      // end
      // HTML // begin [file="/showmedia.jsp";from=(16,38);to=(17,16)]
        out.write("\n\t\t\t\t\t\t\t<a href=\"");

      // end
      // begin [file="/showmedia.jsp";from=(17,19);to=(17,48)]
        out.print( params.getPreviousPageUrl() );
      // end
      // HTML // begin [file="/showmedia.jsp";from=(17,50);to=(21,6)]
        out.write("\">\n\t\t\t\t\t\t\t\t<img name=\"prevImage\" height=\"32\"/>\n\t\t\t\t\t\t\t\t<img src=\"plugin/media?query=get-resource&name=actions/previous.png\"/>\n\t\t\t\t\t\t\t</a>\n\t\t\t\t\t\t");

      // end
      // begin [file="/showmedia.jsp";from=(21,8);to=(21,11)]
         } 
      // end
      // HTML // begin [file="/showmedia.jsp";from=(21,13);to=(23,6)]
        out.write("\n\t\t\t\t\t\t\n\t\t\t\t\t\t");

      // end
      // begin [file="/showmedia.jsp";from=(23,8);to=(23,43)]
         if (params.getHomeUrl() != null) {
      // end
      // HTML // begin [file="/showmedia.jsp";from=(23,45);to=(24,16)]
        out.write("\n\t\t\t\t\t\t\t<a href=\"");

      // end
      // begin [file="/showmedia.jsp";from=(24,19);to=(24,40)]
        out.print( params.getHomeUrl() );
      // end
      // HTML // begin [file="/showmedia.jsp";from=(24,42);to=(27,6)]
        out.write("\">\n\t\t\t\t\t\t\t\t<img src=\"plugin/media?query=get-resource&name=actions/gohome.png\"/>\n\t\t\t\t\t\t\t</a>\n\t\t\t\t\t\t");

      // end
      // begin [file="/showmedia.jsp";from=(27,8);to=(27,11)]
         } 
      // end
      // HTML // begin [file="/showmedia.jsp";from=(27,13);to=(29,6)]
        out.write("\n\t\t\t\t\t\t\n\t\t\t\t\t\t");

      // end
      // begin [file="/showmedia.jsp";from=(29,8);to=(29,32)]
         if (params.hasNext()) {
      // end
      // HTML // begin [file="/showmedia.jsp";from=(29,34);to=(30,16)]
        out.write("\n\t\t\t\t\t\t\t<a href=\"");

      // end
      // begin [file="/showmedia.jsp";from=(30,19);to=(30,44)]
        out.print( params.getNextPageUrl() );
      // end
      // HTML // begin [file="/showmedia.jsp";from=(30,46);to=(34,6)]
        out.write("\">\n\t\t\t\t\t\t\t\t<img src=\"plugin/media?query=get-resource&name=actions/next.png\"/>\n\t\t\t\t\t\t\t\t<img name=\"nextImage\" height=\"32\"/>\n\t\t\t\t\t\t\t</a>\n\t\t\t\t\t\t");

      // end
      // begin [file="/showmedia.jsp";from=(34,8);to=(34,11)]
         } 
      // end
      // HTML // begin [file="/showmedia.jsp";from=(34,13);to=(36,4)]
        out.write("\n\t\t\t\t\t</td>\n\t\t\t\t");

      // end
      // begin [file="/showmedia.jsp";from=(36,6);to=(36,9)]
         } 
      // end
      // HTML // begin [file="/showmedia.jsp";from=(36,11);to=(50,5)]
        out.write("\n\t\t\t\t\n\t\t\t\t<td width=\"10\"/>\n\t\t\t\t<td bgcolor=\"black\" width=\"2\"/>\n\t\t\t\t<td width=\"10\"/>\n\t\t\t\t\n\t\t\t\t<td>\n\t\t\t\t\t<img name=\"zoomInIcon\" onclick=\"zoomIn()\" src=\"plugin/media?query=get-resource&name=actions/viewmag%2B.png\"/>\n\t\t\t\t\t<img name=\"zoomOutcon\" onclick=\"zoomOut()\" src=\"plugin/media?query=get-resource&name=actions/viewmag-.png\"/>\n\t\t\t\t\t<img name=\"fitIcon\" onclick=\"fit()\" src=\"plugin/media?query=get-resource&name=actions/viewmagfit.png\"/>\n\t\t\t\t\t<img name=\"fullSizeIcon\" onclick=\"fullSize()\" src=\"plugin/media?query=get-resource&name=actions/viewmag1.png\"/>\n\t\t\t\t</td>\n\t\t\t\t\n\t\t\t\t<td>\n\t\t\t\t\t");

      // end
      // begin [file="/showmedia.jsp";from=(50,7);to=(50,38)]
         if (params.isHighQuality()) { 
      // end
      // HTML // begin [file="/showmedia.jsp";from=(50,40);to=(51,15)]
        out.write("\n\t\t\t\t\t\t<a href=\"");

      // end
      // begin [file="/showmedia.jsp";from=(51,18);to=(51,50)]
        out.print( params.getCurrentPageUrl("lo") );
      // end
      // HTML // begin [file="/showmedia.jsp";from=(51,52);to=(52,5)]
        out.write("\">low quality</a>\n\t\t\t\t\t");

      // end
      // begin [file="/showmedia.jsp";from=(52,7);to=(52,17)]
         } else { 
      // end
      // HTML // begin [file="/showmedia.jsp";from=(52,19);to=(54,5)]
        out.write("\n\t\t\t\t\t\tlow quality\n\t\t\t\t\t");

      // end
      // begin [file="/showmedia.jsp";from=(54,7);to=(54,10)]
         } 
      // end
      // HTML // begin [file="/showmedia.jsp";from=(54,12);to=(58,5)]
        out.write("\n\t\t\t\t\t\n\t\t\t\t\t-\n\t\t\t\t\t\n\t\t\t\t\t");

      // end
      // begin [file="/showmedia.jsp";from=(58,7);to=(58,40)]
         if (! params.isHighQuality()) { 
      // end
      // HTML // begin [file="/showmedia.jsp";from=(58,42);to=(59,15)]
        out.write("\n\t\t\t\t\t\t<a href=\"");

      // end
      // begin [file="/showmedia.jsp";from=(59,18);to=(59,50)]
        out.print( params.getCurrentPageUrl("hi") );
      // end
      // HTML // begin [file="/showmedia.jsp";from=(59,52);to=(60,5)]
        out.write("\">high quality</a>\n\t\t\t\t\t");

      // end
      // begin [file="/showmedia.jsp";from=(60,7);to=(60,17)]
         } else { 
      // end
      // HTML // begin [file="/showmedia.jsp";from=(60,19);to=(62,5)]
        out.write("\n\t\t\t\t\t\thigh quality\n\t\t\t\t\t");

      // end
      // begin [file="/showmedia.jsp";from=(62,7);to=(62,10)]
         } 
      // end
      // HTML // begin [file="/showmedia.jsp";from=(62,12);to=(67,54)]
        out.write("\n\t\t\t\t</td>\n\t\t\t</tr>\n\t\t</table>\n\t\t\n\t\t<img name=\"mainImg\" style=\"visibility: hidden\" src=\"");

      // end
      // begin [file="/showmedia.jsp";from=(67,57);to=(67,86)]
        out.print( params.getCurrentMediaUrl() );
      // end
      // HTML // begin [file="/showmedia.jsp";from=(67,88);to=(71,0)]
        out.write("\"/>\n\t</body>\n</html>\n\n");

      // end
	}
	
	protected static String getLowQualityUrl (AttachmentLocator aLocator)
	{
		return MediaPluginUtils.createThumbnailQuery(aLocator.getSnipName(), aLocator.getFileName(), 800);
	}
	
	protected static String getHighQualityUrl (AttachmentLocator aLocator) throws MediaPluginException
	{
		return "space/"+aLocator.getSnipName()+"/"+aLocator.getFileName();
	}
	
	protected static class ShowMediaParams
	{
		private String itsPageTitle;
		private String itsBase;
		
		private AttachmentLocator itsLocator;
		private AttachmentLocator itsPreviousLocator;
		private AttachmentLocator itsNextLocator;
		
		private String itsQuality;
		private int itsIndex;
		private String itsHomeUrl;

		
		public ShowMediaParams(String aPageTitle, String aBase, AttachmentLocator aLocator, AttachmentLocator aPreviousLocator, AttachmentLocator aNextLocator, String aQuality, int aIndex, String aHomeUrl)
		{
			itsPageTitle = aPageTitle;
			itsBase = aBase;
			itsLocator = aLocator;
			itsPreviousLocator = aPreviousLocator;
			itsNextLocator = aNextLocator;
			itsQuality = aQuality;
			itsIndex = aIndex;
			itsHomeUrl = aHomeUrl;
		}

		public String getHomeUrl()
		{
			return itsHomeUrl;
		}

		public String getPageTitle()
		{
			return itsPageTitle;
		}

		public String getBase()
		{
			return itsBase;
		}

		public boolean isHighQuality ()
		{
			return "hi".equals(itsQuality);
		}
		
		public boolean hasNext()
		{
			return itsNextLocator != null;
		}
		
		public boolean hasPrevious()
		{
			return itsPreviousLocator != null;
		}

		public String getCurrentMediaUrl () throws MediaPluginException
		{
			return isHighQuality() ? getHighQualityUrl(itsLocator) : getLowQualityUrl(itsLocator);
		}
		
		public String getPreviousMediaUrl () throws MediaPluginException
		{
			if (itsPreviousLocator == null) return "null";
			return isHighQuality() ? getHighQualityUrl(itsPreviousLocator) : getLowQualityUrl(itsPreviousLocator);
		}
		
		public String getNextMediaUrl () throws MediaPluginException
		{
			if (itsNextLocator == null) return "null";
			return isHighQuality() ? getHighQualityUrl(itsNextLocator) : getLowQualityUrl(itsNextLocator);
		}
		
		private String getPageUrl(AttachmentLocator aLocator, String aQuality, int aSlideshowIndex)
		{
			return "plugin/"+MediaPluginConstants.PLUGIN_PATH+"?"
				+ MediaPluginConstants.PARAM_QUERY + "=" + REQUEST_NAME + "&"
				+ SNIP.getName() + "=" +aLocator.getSnipName() + "&"
				+ FILE.getName() + "=" +aLocator.getFileName() + "&"
				+ QUALITY.getName() + "=" + aQuality + "&"
				+ HOME_URL.getName() + "=" + itsHomeUrl + "&"
				+ SLIDESHOW_INDEX.getName() + "=" +aSlideshowIndex;
		}
		
		public String getCurrentPageUrl (String aQuality)
		{
			return getPageUrl(itsLocator, aQuality, itsIndex);
		}
		
		public String getPreviousPageUrl ()
		{
			return getPageUrl(itsPreviousLocator, itsQuality, itsIndex-1);
		}
		
		public String getNextPageUrl ()
		{
			return getPageUrl(itsNextLocator, itsQuality, itsIndex+1);
		}
	}
}
