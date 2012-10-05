/*
 * Created on Apr 12, 2005
 */
package zz.snipsnap.plugin.media.rewriter;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import zz.snipsnap.plugin.media.rewriter.MediaQueryRewriter.Chunk;
import zz.snipsnap.plugin.media.rewriter.MediaQueryRewriter.MediaQuery;

/**
 * This abstract class simplifies the implementation of rewriters that don't depend
 * on other queries to rewrite a given query.
 * @author gpothier
 */
public class SimpleRewriter
{
	private MediaQueryRewriter itsRewriter;
	private final IQueryTransformer itsTransformer;
	
	public SimpleRewriter(IQueryTransformer aTransformer, Reader aReader, Writer aWriter)
	{
		itsTransformer = aTransformer;
		itsRewriter = new MediaQueryRewriter(aReader, aWriter);
	}
	
	public static void rewrite(IQueryTransformer aTransformer, Reader aReader, Writer aWriter) throws IOException
	{
		new SimpleRewriter(aTransformer, aReader, aWriter).rewrite();
	}
	
	/**
	 * Starts the rewriting process, transforming all queries found in the source stream.
	 */
	public void rewrite() throws IOException
	{
		while (itsRewriter.hasNextChunk())
		{
			Chunk theChunk = itsRewriter.getNextChunk();
			MediaQuery theQuery = theChunk.getQuery();
			if (theQuery != null) 
			{
				theQuery = itsTransformer.transform(theQuery);
				theChunk.setQuery(theQuery);
			}
			
			itsRewriter.writeChunk(theChunk);
		}
	}

}
