/*
 * Created on Mar 25, 2005
 */
package zz.snipsnap.storysnipper.model;

import zz.snipsnap.client.core.SnipsManager;

/**
 * A particular page in the story
 * @author gpothier
 */
public class StoryPage
{
	private Story itsStory;
	
	/**
	 * The name of this page's snip.
	 */
	private String itsSnipName;

	public StoryPage(Story aStoryModel, String aSnipName)
	{
		itsStory = aStoryModel;
		itsSnipName = aSnipName;
	}

	/**
	 * The story that contains this page.
	 */
	public Story getStory()
	{
		return itsStory;
	}

	/**
	 * The name of the snip that contains this page.
	 */
	public String getSnipName()
	{
		return itsSnipName;
	}
	
	/**
	 * The name of this page.
	 */
	public String getName()
	{
		int theIndex = itsSnipName.lastIndexOf('/');
		return theIndex != -1 ? itsSnipName.substring(theIndex+1) : itsSnipName;
	}
	
	/**
	 * Sets the content of the snip that corresponds to this page
	 */
	public void setSnipContent(String aContent)
	{
		SnipsManager theSnipsManager = getStory().getSnipsManager();
		theSnipsManager.setSnipContent(getSnipName(), aContent);
	}
	
	/**
	 * Returns the content of the snip that corresponds to this page
	 */
	public String getSnipContent()
	{
		SnipsManager theSnipsManager = getStory().getSnipsManager();
		return theSnipsManager.getSnipContent(getSnipName());
	}
	
}
