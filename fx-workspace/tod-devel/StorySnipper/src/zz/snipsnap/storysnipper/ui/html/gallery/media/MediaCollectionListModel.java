/*
 * Created on Mar 25, 2005
 */
package zz.snipsnap.storysnipper.ui.html.gallery.media;

import java.awt.image.BufferedImage;

import zz.snipsnap.plugin.media.MediaInfo;
import zz.snipsnap.storysnipper.model.MediaCollection;
import zz.snipsnap.storysnipper.model.MediaObject;
import zz.snipsnap.storysnipper.ui.widgets.imagelist.AbstractImageEntry;
import zz.utils.list.IList;
import zz.utils.list.ZArrayList;
import zz.utils.properties.IProperty;
import zz.utils.properties.IPropertyListener;
import zz.utils.properties.IRWProperty;
import zz.utils.properties.SimplePropertyListener;
import zz.utils.properties.SimpleRWProperty;

public class MediaCollectionListModel 
{
	private MediaCollection itsCollection;
	private int itsColumns = 2;
	
	public final IRWProperty<IMediaFilter> pFilter = new SimpleRWProperty<IMediaFilter>()
	{
		protected void changed(IMediaFilter aOldValue, IMediaFilter aNewValue)
		{
			update();
		}
	};
	
	private IList<MediaObjectEntry> itsEntries = new ZArrayList<MediaObjectEntry>();
	
	private IPropertyListener<Integer> itsCollectionListener = new SimplePropertyListener<Integer>()
	{
		protected void changed(IProperty aProperty)
		{
			update();
		}
	};

	public MediaCollectionListModel(MediaCollection aCollection)
	{
		itsCollection = aCollection;
		itsCollection.pCollectionSize().addListener(itsCollectionListener);
		update();
	}
	
	public IList<MediaObjectEntry> getEntries()
	{
		return itsEntries;
	}

	/**
	 * Rereuns the filter.
	 */
	protected void update()
	{
		itsEntries.clear();
		
		for (MediaObject theMediaObject : itsCollection.getMediaObjects())
		{
			IMediaFilter theFilter = pFilter.get();
			if (theFilter == null || theFilter.accept(theMediaObject))
			{
				itsEntries.add(new MediaObjectEntry(theMediaObject));
			}
		}
	}

	private class MediaObjectEntry extends AbstractImageEntry<MediaObject>
	{
		private MediaObject itsMediaObject;
		
		public MediaObjectEntry(MediaObject aMediaObject)
		{
			super(aMediaObject);
			itsMediaObject = aMediaObject;
		}

		public BufferedImage getImage(int aImageSize)
		{
			return itsMediaObject.getThumbnail(aImageSize);
		}
		
		public String getName()
		{
			MediaInfo theMediaInfo = itsMediaObject.getInfo();
			return itsMediaObject.getName();
			
		}
	}
}
