/*
 * Created on Mar 28, 2005
 */
package zz.snipsnap.storysnipper.ui.html.gallery;

import zz.snipsnap.storysnipper.ui.html.HtmlTemplate;
import zz.utils.notification.IEvent;
import zz.utils.notification.SimpleEvent;
import zz.waltz.api.WaltzComponent;

/**
 * Abstract base class for all gallery components.
 * Gallery components permit to insert html templates into the document.
 */
public abstract class AbstractGalleryComponent extends WaltzComponent
{
	protected final SimpleEvent<HtmlTemplate> eSelected = new SimpleEvent<HtmlTemplate>();
	
	private String itsName;
	
	public AbstractGalleryComponent(String aName)
	{
		itsName = aName;
	}

	public String getName()
	{
		return itsName;
	}

	/**
	 * Provides an event that is fired whenever the user whishes to insert a template.
	 */
	public IEvent<HtmlTemplate> eSelected()
	{
		return eSelected;
	}	
	
	
}
