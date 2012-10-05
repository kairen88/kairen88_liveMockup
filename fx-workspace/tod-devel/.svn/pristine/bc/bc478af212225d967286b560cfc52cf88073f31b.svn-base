/*
 * Created on Mar 25, 2005
 */
package zz.snipsnap.storysnipper.ui.upload;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import zz.snipsnap.storysnipper.ui.widgets.imagelist.AbstractImageEntry;
import zz.snipsnap.storysnipper.ui.widgets.imagelist.IImageEntry;
import zz.utils.list.IList;
import zz.utils.list.ZArrayList;
import zz.utils.notification.IEvent;
import zz.utils.notification.SimpleEvent;
import zz.utils.properties.IProperty;
import zz.utils.properties.IPropertyListener;
import zz.utils.properties.IRWProperty;
import zz.utils.properties.SimpleRWProperty;

/**
 * A media list model of a filesystem folder.
 * @author gpothier
 */
public class FolderListModel implements IPropertyListener<File>
{
	public final IRWProperty<File> pFolder = new SimpleRWProperty<File>();
	private IList<FileEntry> itsEntries = new ZArrayList<FileEntry>();
	
	public FolderListModel()
	{
		pFolder.addListener(this);
	}
	
	/**
	 * Called when the folder changes.
	 */
	public void propertyChanged(IProperty<File> aProperty, File aOldValue, File aNewValue)
	{
		update();
	}

	public void propertyValueChanged(IProperty<File> aProperty)
	{
	}
	
	/**
	 * Reloads the folder's content.
	 */
	private void update()
	{
		FSThumbnailsCache.getInstance().clearQueue();
		
		List<FileEntry> theEntries = new ArrayList<FileEntry>();
		File theFolder = pFolder.get();
		for (File theFile : theFolder.listFiles()) 
		{
			if (! theFile.isHidden()) theEntries.add(new FileEntry(theFile));
		}
		Collections.sort(theEntries, COMPARATOR);
		
		itsEntries.clear();
		for (FileEntry theEntry : theEntries) itsEntries.add(theEntry);
		
		// TODO: preload thread
	}

	public IList<FileEntry> getEntries()
	{
		return itsEntries;
	}

	
	
	/**
	 * Base class for the entries of this model
	 * @author gpothier
	 */
	private class FileEntry extends AbstractImageEntry<File>
	{
		public FileEntry(File aFile)
		{
			super(aFile);
		}
		
		public BufferedImage getImage(int aImageSize)
		{
			return FSThumbnailsCache.getInstance().getThumbnail(getUserObject(), aImageSize);
		}
		
		public String getName()
		{
			return getUserObject().getName();
		}
	}
	
	private static EntryComparator COMPARATOR = new EntryComparator();
	
	/**
	 * Permits to sort entries.
	 * @author gpothier
	 */
	private static class EntryComparator implements Comparator<FileEntry>
	{
		public int compare(FileEntry aEntry1, FileEntry aEntry2)
		{
			boolean theFolder1 = aEntry1.getUserObject().isDirectory();
			boolean theFolder2 = aEntry2.getUserObject().isDirectory();
			
			if (theFolder1 && ! theFolder2) return -1;
			else if (theFolder2 && ! theFolder1) return 1;
			else
			{
				String theName1 = aEntry1.getName();
				String theName2 = aEntry2.getName();
				
				return theName1.compareToIgnoreCase(theName2);
			}
		}
	}

}
