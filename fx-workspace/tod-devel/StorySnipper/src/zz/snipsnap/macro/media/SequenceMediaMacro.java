/*
 * Created on Apr 9, 2005
 */
package zz.snipsnap.macro.media;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.snipsnap.render.context.SnipRenderContext;
import org.snipsnap.render.macro.SnipMacro;
import org.snipsnap.render.macro.parameter.SnipMacroParameter;

import zz.snipsnap.plugin.media.MediaPluginConstants;
import zz.snipsnap.plugin.media.ShowMediaProcessor;
import zz.snipsnap.plugin.media.rewriter.IQueryTransformer;
import zz.snipsnap.plugin.media.rewriter.SimpleRewriter;
import zz.snipsnap.plugin.media.rewriter.MediaQueryRewriter.MediaQuery;
import zz.snipsnap.utils.AttachmentLocator;

/**
 * This macro establishes previous/next links for media showed by the show-media query.
 * @author gpothier
 */
public class SequenceMediaMacro extends SnipMacro 
{
	public String getName()
	{
		return "sequence-media";
	}

	public void execute(Writer aWriter, SnipMacroParameter aParams) throws IllegalArgumentException, IOException
	{
		String theContent = aParams.getContent();

		HttpServletRequest theRequest = 
			(HttpServletRequest) aParams.getSnipRenderContext().getAttribute(SnipRenderContext.HTTP_REQUEST);
		
		String theHomeUrl = theRequest.getRequestURI();
		
		Transformer theTransformer = new Transformer(theHomeUrl);
		
		SimpleRewriter.rewrite(theTransformer, new StringReader (theContent), aWriter);
		
		theRequest.getSession().setAttribute(MediaPluginConstants.ATTR_SLIDESHOW, theTransformer.getSlideshow());
		
	}

	private static class Transformer implements IQueryTransformer
	{
		private List<AttachmentLocator> itsSlideshow = new ArrayList<AttachmentLocator>();
		private int itsIndex;
		private String itsHomeUrl;
		
		public Transformer(String aUrl)
		{
			itsHomeUrl = aUrl;
		}

		public MediaQuery transform(MediaQuery aQuery)
		{
			if (ShowMediaProcessor.REQUEST_NAME.equals(aQuery.getQueryName()))
			{
				Map<String, String> theParameters = aQuery.getParameters();
				
				theParameters.put(ShowMediaProcessor.SLIDESHOW_INDEX.getName(), ""+itsIndex);
				theParameters.put(ShowMediaProcessor.HOME_URL.getName(), itsHomeUrl);
				
				String theSnipName = theParameters.get(ShowMediaProcessor.SNIP.getName());
				String theFileName = theParameters.get(ShowMediaProcessor.FILE.getName());
				
				itsSlideshow.add (new AttachmentLocator(theSnipName, theFileName));
				itsIndex++;
			}
			
			return aQuery;
		}

		public List<AttachmentLocator> getSlideshow()
		{
			return itsSlideshow;
		}
	}		
}
