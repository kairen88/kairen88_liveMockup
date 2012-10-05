/*
 * Created on Apr 4, 2005
 */
package zz.snipsnap.storysnipper.ui.html.gallery.media;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JPanel;

import zz.snipsnap.plugin.media.MediaInfo;
import zz.snipsnap.storysnipper.model.MediaCollection;
import zz.snipsnap.storysnipper.model.MediaObject;
import zz.utils.properties.IProperty;
import zz.utils.properties.IRWProperty;
import zz.utils.properties.SimpleRWProperty;
import zz.waltz.api.IWaltzCanvas;
import zz.waltz.api.WaltzComponent;

/**
 * This component permits to create/edit a date filter
 * @author gpothier
 */
public class DateFilterComponent extends WaltzComponent
{
	private IRWProperty<DateMediaFilter> pFilter = new SimpleRWProperty<DateMediaFilter>(this);  
	
	private IRWProperty<Boolean> pEnabled = new SimpleRWProperty<Boolean>(this, false)
	{
		protected void changed(Boolean aOldValue, Boolean aNewValue)
		{
			updateFilter();
			invalidate();
			repaint();			
		}
	};
	
	private IRWProperty<Date> pDate = new SimpleRWProperty<Date>(this)
	{
		protected void changed(Date aOldValue, Date aNewValue)
		{
			updateFilter();
		}
	};
	
	private MediaCollection itsMediaCollection;
	
	public DateFilterComponent(MediaCollection aCollection)
	{
		itsMediaCollection = aCollection;
	}

	public IProperty<DateMediaFilter> pFilter()
	{
		return pFilter;
	}
	
	private void updateFilter()
	{
		if (pEnabled.get())
		{
			Date theDate = pDate.get();
			if (theDate != null)
			{
				DateMediaFilter theFilter = new DateMediaFilter(theDate, DateMediaFilter.Granularity.DAY);
				pFilter.set(theFilter);
			}
		}
		else pFilter.set(null);
	}
	
	protected void render(IWaltzCanvas aCanvas)
	{
		aCanvas.setLayout(new BorderLayout());
		
		renderCaption(aCanvas.createCanvas(BorderLayout.NORTH));
		
		if (pEnabled.get()) renderCalendar(aCanvas.createCanvas(BorderLayout.CENTER));
	}
	
	private void renderCaption (IWaltzCanvas aCanvas)
	{
		aCanvas.setLayout(new BorderLayout());
		
		aCanvas.label("Date filter", BorderLayout.CENTER);
		aCanvas.addProperty(CHECKBOX_PROPERTY(false), pEnabled, BorderLayout.EAST);
	}
	
	private void renderCalendar (IWaltzCanvas aCanvas) 
	{
		aCanvas.setLayout(new FlowLayout());
		
		// Search maximum and minimum dates. 
		// TODO: update them when the collection changes
		
		Date theMinimum = new Date();
		Date theMaximum = new Date();
		
		for (MediaObject theMediaObject : itsMediaCollection.getMediaObjects())
		{
			MediaInfo theMediaInfo = theMediaObject.getInfo();
			Date theDate = theMediaInfo.getDate();
			
			if (theDate != null)
			{
				if (theDate.before(theMinimum)) theMinimum = theDate;
				if (theDate.after(theMaximum)) theMaximum = theDate;
			}
		}
		
		pDate.set(theMinimum);
		
		aCanvas.addProperty(SPINNER_PROPERTY(null, null, Calendar.DAY_OF_MONTH), pDate, aCanvas);
	}
	
}
