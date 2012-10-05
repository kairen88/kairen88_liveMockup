/*
 * Created on 17-jun-2004
 */
package zz.csg.api;

import zz.utils.IPublicCloneable;
import zz.utils.properties.IRWProperty;
import zz.utils.properties.PropertyId;

/**
 * A graphic object whose content is retrieved from an URI
 * @author gpothier
 */
public interface IURIGraphicObject extends IGraphicObject
{
	public static final PropertyId<IProvider> PROVIDER = 
		new PropertyId<IProvider> ("provider", true);

	/**
	 * The provider that is able to retrieve the data from the URI.
	 */
	public IRWProperty<IProvider> pProvider ();
	
	/**
	 * This interface is for data providers that are able to
	 * retrieve data from a URI.
	 * @author gpothier
	 */
	public interface IProvider extends IPublicCloneable
	{
		/**
		 * Returns the data referenced by the URI.
		 */
		public Object getData();
		
		/**
		 * Returns the URI of this provider.
		 */
		public String getURI();
	}
}
