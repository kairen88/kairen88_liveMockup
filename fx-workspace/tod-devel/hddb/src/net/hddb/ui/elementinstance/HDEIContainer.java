/**
 * Created by IntelliJ IDEA.
 * User: g1
 * Date: 12 mars 2004
 * Time: 20:47:16
 * To change this template use File | Settings | File Templates.
 */
package net.hddb.ui.elementinstance;

import net.hddb.utils.Constraints;

/**
 * Interface for widgets that can contain {@link HDElementInstance}s.
 * It manages constraint that should be respected by its children
 * element instances.
 * @author gpothier
 */
public interface HDEIContainer
{
	/**
	 * Returns constraints that define which views should be allowed or not
	 * for the children of this container.
	 * @return A constraint object that operates on {@link net.hddb.adapters.HDAdapterClass}
	 * objects. They refer to the last adapter class in the chain.
	 */
	public Constraints getChildrenViewConstraints ();
}
