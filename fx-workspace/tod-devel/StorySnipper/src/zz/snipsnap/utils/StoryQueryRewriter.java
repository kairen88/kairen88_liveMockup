/*
 * Created on Apr 12, 2005
 */
package zz.snipsnap.utils;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URLDecoder;
import java.util.Map;

import zz.snipsnap.plugin.media.ShowMediaProcessor;
import zz.snipsnap.plugin.media.rewriter.IQueryTransformer;
import zz.snipsnap.plugin.media.rewriter.SimpleRewriter;
import zz.snipsnap.plugin.media.rewriter.MediaQueryRewriter.MediaQuery;
import zz.snipsnap.storysnipper.model.Story;
import zz.snipsnap.storysnipper.model.StoryPage;
import zz.snipsnap.storysnipper.ui.login.LoginComponent;
import zz.utils.tree.ITreeVisitor;
import zz.utils.tree.SimpleTree;
import zz.utils.tree.SimpleTreeNode;
import zz.utils.tree.TreeUtils;

/**
 * Permits to rewrite all the snips of a given story.
 * @author gpothier
 */
public class StoryQueryRewriter implements ITreeVisitor<SimpleTreeNode<StoryPage>, StoryPage>
{
	private Story itsStory;
	private IQueryTransformer itsTransformer;
	
	public StoryQueryRewriter(Story aStory, IQueryTransformer aTransformer)
	{
		itsStory = aStory;
		itsTransformer = aTransformer;
	}
	
	public static void transform (Story aStory, IQueryTransformer aTransformer)
	{
		new StoryQueryRewriter(aStory, aTransformer).transform();
	}
	
	public void transform()
	{
		SimpleTree<StoryPage> thePages = itsStory.getPages();
		TreeUtils.visit(thePages, this);
	}

	public void visit(SimpleTreeNode<StoryPage> aNode, StoryPage aPage)
	{
		String theSnipContent = aPage.getSnipContent();
		StringReader theReader = new StringReader(theSnipContent);
		StringWriter theWriter = new StringWriter();
		
		try
		{
			System.out.println("Rewriting "+aPage.getName());
			SimpleRewriter.rewrite(itsTransformer, theReader, theWriter);
			System.out.println("Done.");
		}
		catch (IOException e)
		{
			throw new RuntimeException (e);
		}
		
		aPage.setSnipContent(theWriter.toString());
	}
	
	public static void main(String[] args) throws IOException
	{
		Story theStory = LoginComponent.login();
		transform(theStory, new IQueryTransformer()
				{
					public MediaQuery transform(MediaQuery aQuery)
					{
						if (ShowMediaProcessor.REQUEST_NAME.equals(aQuery.getQueryName()))
						{
							Map<String, String> theParameters = aQuery.getParameters();
							String theSource = theParameters.remove("source");
							
							if (theSource != null)
							{
								theSource = URLDecoder.decode(theSource);
								int theIndex1 = theSource.indexOf('/');
								int theIndex2 = theSource.lastIndexOf('/');
								
								String theSnipName = theSource.substring(theIndex1+1, theIndex2);
								String theFileName = theSource.substring(theIndex2+1);
								
								theParameters.put ("snip", theSnipName);
								theParameters.put ("file", theFileName);
							}
						}
						return aQuery;
					}
				});
	}
}
