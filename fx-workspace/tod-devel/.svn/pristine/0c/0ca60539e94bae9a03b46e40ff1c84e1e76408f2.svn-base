/*
 * Created on 27-sep-2004
 */
package zz.csg.impl.uri;

import zz.csg.api.IURIGraphicObject;
import zz.utils.PublicCloneable;


/**
 * @author gpothier
 */
public abstract class AbstractProvider extends PublicCloneable
implements IURIGraphicObject.IProvider
{
	private String itsURI;
	
	public AbstractProvider()
	{
	}
	
	public AbstractProvider(String aURI)
	{
		itsURI = aURI;
	}
	
	public String getURI()
	{
		return itsURI;
	}
	
	protected void setURI(String aUri)
	{
		itsURI = aUri;
	}
}
