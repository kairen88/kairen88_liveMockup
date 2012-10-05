/*
 * Created on Apr 4, 2005
 */
package zz.snipsnap.storysnipper.ui.widgets.imagelist;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Models an image for the {@link zz.snipsnap.storysnipper.ui.widgets.imagelist.ImageListView}
 * @param <U> The type of the user object.
 * @author gpothier
 */
public interface IImageEntry<U>
{
    public U getUserObject();
    public BufferedImage getImage(int aImageSize);
    public String getName();

}
