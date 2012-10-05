/*
 * Created on Feb 24, 2004
 */
package net.hddb.views;

import net.hddb.*;
import net.hddb.adapters.*;
import net.hddb.models.HDModel;
import net.basekit.*;
import net.basekit.widgets.Widget;

/**
 * A view of an element. It uses an adapter to transform a raw element into
 * some known type (eg. list, tree node, image, ...)
 * WHen constructed the view registers itself as an observer of the adapter.
 * @author gpothier
 */
public abstract class HDView extends Widget implements Observer
{
	private HDModel itsModel;
	
	public HDView (HDModel aModel) 
	{
		itsModel = aModel;
	}

	protected void addNotify ()
	{
		super.addNotify ();
		itsModel.addObserver (this);
	}

	protected void removeNotify ()
	{
		super.removeNotify ();
		itsModel.removeObserver (this);
	}

	/**
	 * @return Returns the adapter.
	 */
	public HDModel getModel()
	{
		return itsModel;
	}

	/**
	 * Returns the view class that represents this view.
	 */
	public abstract HDViewClass getViewClass ();
}
