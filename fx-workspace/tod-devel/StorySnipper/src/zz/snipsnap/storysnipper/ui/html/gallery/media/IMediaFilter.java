/*
 * Created on Apr 4, 2005
 */
package zz.snipsnap.storysnipper.ui.html.gallery.media;

import zz.snipsnap.storysnipper.model.MediaObject;

/**
 * A filter for media objects
 * @author gpothier
 */
public interface IMediaFilter
{
	public boolean accept(MediaObject aMediaObject);
}
