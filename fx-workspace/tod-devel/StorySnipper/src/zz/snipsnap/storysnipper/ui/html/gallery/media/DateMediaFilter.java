/*
 * Created on Apr 4, 2005
 */
package zz.snipsnap.storysnipper.ui.html.gallery.media;

import java.util.Calendar;
import java.util.Date;

import zz.snipsnap.plugin.media.MediaInfo;
import zz.snipsnap.storysnipper.model.MediaObject;

/**
 * Filters out media objects according to their date.
 * @author gpothier
 */
public class DateMediaFilter implements IMediaFilter
{
	public static final Calendar CALENDAR = Calendar.getInstance();
	
	public enum Granularity 
	{
		DAY (3),
		MONTH (2),
		YEAR (1);

		private static final int[] FIELDS = {Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH};
		
		/**
		 * Number of fields that should match
		 */
		private final int itsMatchingFieldsCount;

		Granularity(int aMatchingFieldsCount)
		{
			itsMatchingFieldsCount = aMatchingFieldsCount;
		}
		
		/**
		 * Indicates if the dates are within the same granularity unit.
		 */
		boolean accept(Date aDate1, Date aDate2)
		{
			if (aDate1 == null || aDate2 == null) return false;

			for (int i=0;i<itsMatchingFieldsCount;i++)
			{
				if (! accept(aDate1, aDate2, FIELDS[i])) return false;
			}
			return true;
		}

		boolean accept(Date aDate1, Date aDate2, int aField)
		{
			CALENDAR.setTime(aDate1);
			int theValue1 = CALENDAR.get(aField);
			
			CALENDAR.setTime(aDate2);
			int theValue2 = CALENDAR.get(aField);
			
			return theValue1 == theValue2;
		}
	}
	
	private Date itsDate;
	private Granularity itsGranularity;
	
	public DateMediaFilter(Date aDate, Granularity aGranularity)
	{
		assert itsDate != null;
		itsDate = aDate;
		itsGranularity = aGranularity;
	}

	public boolean accept(MediaObject aMediaObject)
	{
		MediaInfo theMediaInfo = aMediaObject.getInfo();
		if (theMediaInfo == null) return false;
		
		Date theDate = theMediaInfo.getDate();
		return itsGranularity.accept(theDate, itsDate);
	}
}
