/*
 * Created on Mar 23, 2005
 */
package zz.snipsnap.client.core;

public abstract class AbstractManager
{
	private SnipSnapSpace itsSpace;

	public AbstractManager(SnipSnapSpace aSpace)
	{
		itsSpace = aSpace;
	}

	public SnipSnapSpace getSpace()
	{
		return itsSpace;
	}
}
