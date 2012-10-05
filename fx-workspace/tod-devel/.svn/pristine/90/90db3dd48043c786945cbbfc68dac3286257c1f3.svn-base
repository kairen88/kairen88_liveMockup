/*
 * Created on Mar 25, 2005
 */
package zz.snipsnap.client.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Permits to manage snips
 * @author gpothier
 */
public class SnipsManager extends AbstractManager
{

	public SnipsManager(SnipSnapSpace aSpace)
	{
		super(aSpace);
	}
	
	/**
	 * Retuns an iterable containing the names of the children snips of
	 * the specified snip.
	 */
	public Iterable<String> getSnipChildren (String aSnipName)
	{
		Vector theVector = (Vector) getSpace().executeRPC("snipSnap.getSnipChildren", aSnipName);
		List<String> theResult = new ArrayList<String>();
		theResult.addAll(theVector);
		
		return theResult;
	}
	
	public String getSnipContent (String aSnipName)
	{
		String theContent = 
			(String) getSpace().executeRPC("snipSnap.getSnip", aSnipName);
		
		return theContent;
	}
	
	public void setSnipContent (String aSnipName, String aContent)
	{
		getSpace().executeRPC("snipSnap.setSnipContent", aSnipName, aContent);
	}
	
	public boolean hasSnip (String aSnipName)
	{
		Boolean theResult = (Boolean) getSpace().executeRPC("snipSnap.hasSnip", aSnipName);
		return theResult;
	}
	
	public void removeSnip (String aSnipName)
	{
		getSpace().executeRPC("snipSnap.removeSnip", aSnipName);
	}
	
	public void createSnip (String aParentSnipName, String aSnipName)
	{
		if (aParentSnipName == null) aParentSnipName = "";
		getSpace().executeRPC("snipSnap.createSnip", aParentSnipName, aSnipName, "");	
	}

	public void restore (String aXMLSpace)
	{
		getSpace().executeRPC("snipSnap.restoreXml", aXMLSpace.getBytes());
	}
	
}
