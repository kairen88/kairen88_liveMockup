/**
 * Created by IntelliJ IDEA.
 * User: g1
 * Date: 12 mars 2004
 * Time: 13:11:25
 * To change this template use File | Settings | File Templates.
 */
package net.hddb.utils;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Defines rules for determining rejection or acceptation of certain objects
 * An object will be accepted iff:
 * <li> It is not in the rejected list ({@link #itsRejected})
 * <li>OR It is in the accepted list ({@link #itsAccepted}).
 *
 * <p>
 * Note that the rejected list can be defined so as to contain everything
 * (see {@link 3rejectAll ()}. It means that only objects in the accept list will be
 * accepted.
 */
public class Constraints
{
	private static final Collection ALL = Collections.EMPTY_LIST;

	private Collection itsAccepted;
	private Collection itsRejected;

	public Constraints ()
	{
		this (null);
	}

	/**
	 * Constructs a new constrinats object based on existing constraints.
	 */
	public Constraints (Constraints aParentConstraints)
	{
		if (aParentConstraints != null)
		{
			itsAccepted = aParentConstraints.itsAccepted;
			itsRejected = aParentConstraints.itsRejected;
		}
	}

	public void accept (Object aObject)
	{
		if (itsAccepted == null) itsAccepted = new ArrayList ();
		itsAccepted.add (aObject);
	}

	public void reject (Object aObject)
	{
		assert itsRejected != ALL;
		if (itsRejected == null) itsRejected = new ArrayList ();
		itsRejected.add (aObject);
	}

	/**
	 * Sets the rejected list so that it contains everything.
	 */
	public void rejectAll ()
	{
		itsRejected = ALL;
	}

	/**
	 * @return Whether the specified object is acceptable.
	 */
	public boolean isAccepted (Object aObject)
	{
		boolean theRejected = itsRejected != null && (itsRejected == ALL || itsRejected.contains (aObject));
		boolean theAllowed = itsAccepted != null && itsAccepted.contains (aObject);
		return (! theRejected) ||theAllowed;
	}
}
