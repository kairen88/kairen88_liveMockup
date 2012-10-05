/*
 * Created on Apr 3, 2004
 */
package net.hddb.adapters;

import net.hddb.models.HDModel;
import net.hddb.utils.Constraints;

/**
 * An adapter that is its own model.
 * @author gpothier
 */
public abstract class HDAutoAdapter extends HDAdapter implements HDModel
{
	public HDAutoAdapter (Object aElement)
	{
		super(aElement);
	}

	public Constraints getChildrenViewConstraints ()
	{
		return getAdapterClass ().getChildrenAdapdationConstraints ();
	}


	public HDModel getModel ()
	{
		return this;
	}
}
