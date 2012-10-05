/*
 * Created on Feb 24, 2004
 */
package net.basekit;

/**
 * Permits to be notified of messages.
 * @author gpothier
 */
public interface Observer
{
	/**
	 * This method will be called whenever an observable notifies its observers
	 */
	public void processMessage (Message aMessage);
}
