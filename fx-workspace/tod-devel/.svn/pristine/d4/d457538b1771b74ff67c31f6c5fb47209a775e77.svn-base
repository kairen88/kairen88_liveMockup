/*
 * Created on Dec 23, 2004
 */
package zz.csg.impl.uri;

import java.awt.Image;

import zz.csg.api.IURIGraphicObject;
import zz.utils.PublicCloneable;


/**
 * Provider for a direct awt image.
 * This provider has no URI and thus cannot be saved
 * @author gpothier
 */
public class ImageProvider extends PublicCloneable
implements IURIGraphicObject.IProvider
{
	private Image itsImage;

	public ImageProvider()
	{
	}
	
	public ImageProvider(Image aImage)
	{
		itsImage = aImage;
	}
	
	public Image getImage()
	{
		return itsImage;
	}
	
	public void setImage(Image aImage)
	{
		itsImage = aImage;
	}
	
	public Object getData()
	{
		return itsImage;
	}

	public String getURI()
	{
		throw new UnsupportedOperationException();
	}

}
