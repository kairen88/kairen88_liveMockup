/*
 * Created on Mar 25, 2005
 */
package zz.snipsnap.storysnipper.ui;

import zz.snipsnap.storysnipper.model.StoryPage;

/**
 * This interface must be implemented by the components that permit to edit pages.
 * @author gpothier
 */
public interface IStoryPageEditor
{
	/**
	 * Returns the page currently loaded in this editor.
	 */
	public StoryPage getCurrentPage ();
	
	/**
	 * Loads a new page, discarding the current one.
	 */
	public void load (StoryPage aStoryPage);
	
	/**
	 * Saves the current page.
	 */
	public void save();
	
	/**
	 * Reloads the current page from the site, discarding local changes.
	 */
	public void restore();
}
