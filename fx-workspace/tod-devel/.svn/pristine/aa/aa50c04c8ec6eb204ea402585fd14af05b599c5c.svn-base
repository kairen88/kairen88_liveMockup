/*
 * Created on Apr 11, 2005
 */
package zz.snipsnap.plugin.media.rewriter;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.snipsnap.render.context.SnipRenderContext;

import zz.snipsnap.plugin.media.MediaPlugin;
import zz.snipsnap.plugin.media.MediaPluginConstants;

/**
 * This class permits to parse a stream identifying requests to {@link zz.snipsnap.plugin.media.MediaPlugin}
 * and to rewrite them, possibly modifying their parameters.
 * @author gpothier
 */
public class MediaQueryRewriter
{
	private static final String MARKER = 
		"plugin/"
		+MediaPluginConstants.PLUGIN_PATH+"?"
		+MediaPluginConstants.PARAM_QUERY+"=";

	private static final Pattern TEXT_PATTERN = Pattern.compile(".*?"+Pattern.quote(MARKER), Pattern.DOTALL);
	private static final Pattern REMAINING_PATTERN = Pattern.compile(".*", Pattern.DOTALL);
	private static final Pattern QUERY_PATTERN = Pattern.compile(".*?\"");
	private static final Pattern PARAM_PATTERN = Pattern.compile("(.*?)=(.*?)&?");
	
	private Reader itsReader;
	private Writer itsWriter;

	private Scanner itsScanner;
	private boolean itsFinished = false;
	
	public MediaQueryRewriter(Reader aReader, Writer aWriter)
	{
		itsReader = aReader;
		itsWriter = aWriter;
		itsScanner = new Scanner(aReader);
	}

	public boolean hasNextChunk()
	{
		return ! itsFinished;
	}
	
	public Chunk getNextChunk()
	{
		if (itsFinished) throw new NoSuchElementException("No more chunks");
		
		String theText = itsScanner.findWithinHorizon(TEXT_PATTERN, 0);
		if (theText == null)
		{
			// Finishing
			String theRemaining = itsScanner.findWithinHorizon(REMAINING_PATTERN, 0);
			Chunk theChunk = new Chunk(theRemaining, null);
			itsFinished = true;
			return theChunk;
		}
		
		String theQueryString = itsScanner.findWithinHorizon(QUERY_PATTERN, 0);
		theQueryString = theQueryString.substring(0, theQueryString.length()-1);
		MediaQuery theQuery = parseQuery(theQueryString);
		Chunk theChunk = new Chunk(theText, theQuery);
		
		return theChunk;
	}
	
	public void writeChunk (Chunk aChunk) throws IOException
	{
		itsWriter.write(aChunk.getText());
		
		MediaQuery theQuery = aChunk.getQuery();
		if (theQuery != null)
		{
			itsWriter.write(theQuery.getQueryName());
			
			for (Map.Entry<String, String> theEntry : theQuery.getParameters().entrySet())
			{
				String theName = theEntry.getKey();
				String theValue = theEntry.getValue();
				
				itsWriter.write("&");
				itsWriter.write(theName);
				itsWriter.write("=");
				itsWriter.write(theValue);
			}
			
			itsWriter.write("\"");
		}
	}
	
	
	private static MediaQuery parseQuery (String aQuery)
	{
		Map<String, String> theParameters = new HashMap<String, String>();
		StringTokenizer theTokenizer = new StringTokenizer(aQuery);
		
		String theQueryName = theTokenizer.nextToken("&");
		
		while (theTokenizer.hasMoreTokens())
		{
			String theName = theTokenizer.nextToken("=").substring(1);
			String theValue = theTokenizer.nextToken("&").substring(1);
			
			theParameters.put(theName, theValue);
		}
		
		return new MediaQuery(theQueryName, theParameters);
	}
	
	/**
	 * A chunk contains a query and the text that precedes it.
	 */
	public static class Chunk
	{
		private String itsText;
		private MediaQuery itsQuery;
		
		public Chunk(String aText, MediaQuery aQuery)
		{
			itsText = aText;
			itsQuery = aQuery;
		}

		public MediaQuery getQuery()
		{
			return itsQuery;
		}

		public void setQuery(MediaQuery aQuery)
		{
			itsQuery = aQuery;
		}

		public String getText()
		{
			return itsText;
		}
	}

	
	/**
	 * Represents a query to the media plugin.
	 */
	public static class MediaQuery
	{
		private String itsQueryName;
		private Map<String, String> itsParameters;
		
		public MediaQuery(String aQuery, Map<String, String> aParameters)
		{
			itsQueryName = aQuery;
			itsParameters = aParameters;
		}

		public Map<String, String> getParameters()
		{
			return itsParameters;
		}

		public String getQueryName()
		{
			return itsQueryName;
		}
	}
	
	public static void main(String[] args) throws IOException
	{
		String theContent = "<p align=\"left\">\n" + 
				"      <a href=\"plugin/media?query=show-media&index=0&source=space%2Fperu2005%2F_zz_stsn_media%2FDSC00548.JPG\"><img src=\"plugin/media?query=get-thumbnail&snip=peru2005%2F_zz_stsn_media&file=DSC00548.JPG&size=400\">\n" + 
				"\n" + 
				"      </a><a href=\"plugin/media?query=show-media&index=1&source=space%2Fperu2005%2F_zz_stsn_media%2FDSC00549.JPG\"><img src=\"plugin/media?query=get-thumbnail&snip=peru2005%2F_zz_stsn_media&file=DSC00549.JPG&size=400\">\n" + 
				"      </a><a href=\"plugin/media?query=show-media&index=2&source=space%2Fperu2005%2F_zz_stsn_media%2FDSC00550.JPG\"><img src=\"plugin/media?query=get-thumbnail&snip=peru2005%2F_zz_stsn_media&file=DSC00550.JPG&size=400\">\n" + 
				"      </a>\n" + 
				"    </p>";
		
		StringReader theReader = new StringReader(theContent);
		StringWriter theWriter = new StringWriter();
		
		MediaQueryRewriter theRewriter = new MediaQueryRewriter(theReader, theWriter);
		
		while (theRewriter.hasNextChunk())
		{
			Chunk theChunk = theRewriter.getNextChunk();
			theRewriter.writeChunk(theChunk);
		}
		
		System.out.println(theWriter.toString());
		
	}
}
