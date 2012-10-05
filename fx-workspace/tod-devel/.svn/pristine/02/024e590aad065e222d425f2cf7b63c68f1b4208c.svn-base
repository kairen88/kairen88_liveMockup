/*
 * Created on Apr 3, 2004
 */
package net.hddb.adapters.impl.debug.eclipse.stackframe;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;

import net.hddb.adapters.HDAdapter;
import net.hddb.adapters.HDAdapterClass;
import net.hddb.adapters.impl.string2text.HDAString2Text;
import net.hddb.adapters.impl.text2list.HDAText2List;
import net.hddb.models.HDModel;
import net.hddb.models.text.HDMText;

/**
 * 
 * @author gpothier
 */
public class HDAValue2Text extends HDAdapter
{
	public HDAValue2Text (Object aElement)
	{
		super(aElement);
	}
	
	protected IValue getIValue ()
	{
		return (IValue) getElement();
	}

	public HDModel getModel ()
	{
		try
		{
			String theText = getIValue().getValueString();
			return new HDAString2Text (theText);
		} 
		catch (DebugException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public HDAdapterClass getAdapterClass ()
	{
		return HDACValue2Text.getInstance();
	}
}
