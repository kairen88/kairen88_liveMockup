package zz.snipsnap.storysnipper.ui.widgets.imagelist;

import java.awt.image.BufferedImage;

/**
 * Describes one image.
 * @author gpothier
 */
public abstract class AbstractImageEntry<U> implements IImageEntry<U>
{
	private U itsUserObject;
	
	public AbstractImageEntry(U aUserObject)
	{
		itsUserObject = aUserObject;
	}

	public U getUserObject()
	{
		return itsUserObject;
	}
	
	public boolean equals(Object aObj)
	{
		if (aObj instanceof AbstractImageEntry)
		{
			AbstractImageEntry theEntry = (AbstractImageEntry) aObj;
			return theEntry.itsUserObject == itsUserObject;
		}
		else return false;
	}
}