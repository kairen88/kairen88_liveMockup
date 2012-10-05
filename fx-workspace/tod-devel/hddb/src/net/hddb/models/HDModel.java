/*
 * Created on Apr 3, 2004
 */
package net.hddb.models;

import java.util.List;

import net.basekit.Observer;
import net.hddb.utils.Constraints;

/**
 * A model that can be viewed.
 * @author gpothier
 */
public interface HDModel
{
	/**
	 * Returns a list of elements that are referred by this adapter's element
	 */
	public List getOutboundReferences ();

	public void addObserver (Observer aListener);
	public void removeObserver (Observer aListener);
	
	/**
	 * If it makes sense for this adapter to have children, returns constraints
	 * that should respect the views that wrap around the children.
	 * <p>
	 * This default implementation simply delegates to the adapter class.
	 * @return Constraints that operate on {@link Class} objects, or null if
	 * not applicable or no constraints.
	 */
	public Constraints getChildrenViewConstraints ();


}
