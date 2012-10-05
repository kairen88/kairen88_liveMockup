/*
 * Created on Mar 30, 2005
 */
package zz.waltz.api.action;

import javax.swing.JComponent;

import zz.utils.notification.IFireableEvent;

/**
 * This kind of action fires an event when performed.
 * @author gpothier
 */
public class FireEventAction<T> extends DefaultActionModel
{
	/**
	 * The event to fire when the action is performed
	 */
	private IFireableEvent<T> itsEvent;
	
	/**
	 * The data to pass to the listeners. 
	 */
	private T itsData;
	
	public FireEventAction(IFireableEvent<T> aEvent, T aData)
	{
		itsEvent = aEvent;
		itsData = aData;
	}
	
	public FireEventAction(String aName, IFireableEvent<T> aEvent, T aData)
	{
		super(aName);
		
		itsEvent = aEvent;
		itsData = aData;
	}



	public void performed(JComponent aComponent)
	{
		itsEvent.fire(itsData);
	}
	
}
