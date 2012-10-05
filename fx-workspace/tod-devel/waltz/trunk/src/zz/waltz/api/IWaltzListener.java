/*
 * Created on Mar 24, 2005
 */
package zz.waltz.api;

/**
 * A listener that is notified when a componenet publishes data.
 * @author gpothier
 */
public interface IWaltzListener
{
	/**
	 * This method is called whenever a component to which this listsner is registered
	 * publishes some data.
	 */
	public void receive (Topic aTopic, Object aData);
}
