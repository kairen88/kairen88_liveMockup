/*
 * Created on Feb 24, 2004
 */
package net.hddb.adapters.impl.text2list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.basekit.Message;
import net.basekit.Observer;
import net.basekit.exceptions.UnexpectedMessageException;
import net.hddb.adapters.HDAMessage;
import net.hddb.adapters.HDAdapterClass;
import net.hddb.adapters.HDAutoAdapter;
import net.hddb.adapters.impl.string2text.HDAString2Text;
import net.hddb.models.list.HDMList;
import net.hddb.models.list.HDMListUtils;
import net.hddb.models.text.HDAMChanged;
import net.hddb.models.text.HDMText;
import zz.utils.BoundedIterator;

/**
 * Adapts a {@link net.hddb.adapters.text.HDAText} to a {@link net.hddb.models.base.AbstractHDMList}
 * @author gpothier
 */
public class HDAText2List extends HDAutoAdapter implements HDMList, Observer
{
	/**
	 * This is a list of 1-character strings that represents the text.
	 */
	private List itsCurrentValue;
	
	public HDAText2List (Object aElement) 
	{
		super(aElement);
		getHDMText().addObserver(this);
		setCurrentValue (getString());
	}
	
	private HDMText getHDMText ()
	{
		return (HDMText) getElement();
	}
	
	private String getString ()
	{
		return getHDMText().getText();
	}
	
	/**
	 * Accepts this string as the new content of the list, and fires appropriate
	 * messages.
	 */
	private void setCurrentValue (String aString)
	{
		int theLength = aString.length();

		List thePreviousValue = itsCurrentValue;
		if (itsCurrentValue == null) itsCurrentValue = new ArrayList (theLength);
		
		itsCurrentValue.clear();
		for (int i=0;i<theLength;i++)
		{
			String theChar = aString.substring(i, i+1);
			HDAString2Text theAdapter = new HDAString2Text (theChar);
			itsCurrentValue.add (theAdapter);
		}
		
		if (thePreviousValue != null)
		{
			HDAMessage theMessage = HDMListUtils.createMessage(this, thePreviousValue, itsCurrentValue);
			fire(theMessage);
		}
	}

	public int getCount()
	{
		return itsCurrentValue.size();
	}

	public Object getChild(int aIndex)
	{
		return itsCurrentValue.get (aIndex);
	}

	public List getOutboundReferences()
	{
		return null;
	}

	public void processMessage(Message aMessage)
	{
		if (aMessage instanceof HDAMChanged)
		{
			setCurrentValue(getString());
		}
		else throw new UnexpectedMessageException (aMessage);
	}

	public HDAdapterClass getAdapterClass()
	{
		return HDACText2List.getInstance(); 
	}
}
