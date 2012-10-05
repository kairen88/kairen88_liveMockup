/*
 * Created on Feb 24, 2004
 */
package net.hddb.adapters.impl.string2text;

import java.util.List;

import net.hddb.adapters.HDAdapterClass;
import net.hddb.adapters.HDAutoAdapter;
import net.hddb.models.text.HDAMChanged;
import net.hddb.models.text.HDMText;
import zz.utils.Utils;

/**
 * A text adapter that takes its text from a string. As strings are immutable,
 * this adapter doesn't have a fixed element (or rather, it specifies that
 * its element is null), but it has an instance variable containing the current
 * string. It also has a {@link #setString(String)} method to change the value.
 * The element given in the constructor will be used as initial value for the string.
 * @author gpothier
 */
public class HDAString2Text extends HDAutoAdapter implements HDMText
{
	private String itsString;

	public HDAString2Text (Object aElement) 
	{
		super (null);
		assert aElement != null;
		itsString = (String) aElement;
	}

	public String getText()
	{
		return itsString;
	}
	
	/**
	 * Sets a new text value, and fires a {@link HDAMChanged} message.
	 */
	public void setString (String aString)
	{
		assert aString != null;
		if (Utils.different (itsString, aString))
		{
			itsString = aString;
			fire(new HDAMChanged ());
		}
	}

	public HDAdapterClass getAdapterClass()
	{
		return HDACString2Text.getInstance();
	}
	
	public List getOutboundReferences ()
	{
		return null;
	}
}
