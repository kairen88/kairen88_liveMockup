/*
 * Created on Mar 25, 2005
 */
package zz.snipsnap.storysnipper.ui.widgets.imagelist;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;

import zz.utils.list.IList;
import zz.utils.list.IListListener;
import zz.utils.list.SimpleListListener;
import zz.utils.notification.IEvent;
import zz.utils.notification.IEventListener;
import zz.utils.notification.SimpleEvent;
import zz.utils.properties.ArrayListProperty;
import zz.utils.properties.IListProperty;
import zz.utils.properties.IProperty;
import zz.utils.properties.IPropertyListener;
import zz.utils.properties.IRWProperty;
import zz.utils.properties.SimpleRWProperty;
import zz.utils.ui.text.TextPainter;
import zz.utils.ui.thumbnail.AsyncThumbnailCache;

/**
 * Presents a list of images.
 * @param <U> The type of user object associated with each image entry.
 * @author gpothier
 */
public class ImageListView<U> extends JPanel 
implements Scrollable
{
	public static final int H_GAP = 5;
	public static final int V_GAP = 5;
	public static final int V_NAME_GAP = 30;

	private final IList<? extends IImageEntry<U>> itsImages;
	
	private IListProperty<U> itsHighlightedObjects = new ArrayListProperty<U>(this);
	private SimpleEvent<U> itsObjectSelected = new SimpleEvent<U>();
	
    /**
     * This property contains the list of currently highlighted objects. Each object is 
     * the user object of the entries.
     */
	public final IListProperty<U> pHighlightedObjects = itsHighlightedObjects;
    
    /**
     * This event is fired whenever an entry is selected, ie. double clicked.
     * The parameter is the user object of the selected entry 
     */
	public final IEvent<U> eObjectSelected = itsObjectSelected;
	
	/**
	 * An optional thumbnail cache that can be listened to so as to
	 * repaint when new thumbnails are available.
	 */
	private final AsyncThumbnailCache<U> itsThumbnailCache;
	
	private IListListener<U> itsHighlightedObjectsListener = new SimpleListListener<U>()
	{
		protected void listChanged(IList aList)
		{
			repaint();
		}
	};
	
	/**
	 * This listener is called when the images list changes.
	 */
	private IListListener<IImageEntry<U>> itsEntriesListener = new SimpleListListener<IImageEntry<U>>()
	{
		protected void listChanged(IList aList)
		{
			revalidate();
			repaint();
		}
	};
	
	/**
	 * This listener is notified whenever a new thumbnail is loaded by the thumbnail cache
	 */
	private IEventListener<U> itsThumbnailCacheListener = new IEventListener<U>()
	{
		public void fired(IEvent<? extends U> aEvent, U aData)
		{
			repaint(100);			
		}
	};
	
	public final IRWProperty<Integer> pImageSize = new SimpleRWProperty<Integer>(this, 80)
	{
		protected void changed(Integer aOldValue, Integer aNewValue)
		{
			revalidate();
			repaint();
		}
	};

	public ImageListView(IList<IImageEntry<U>> aImages)
	{
		this (aImages, null);
	}
	
	public ImageListView(IList<? extends IImageEntry<U>> aImages, AsyncThumbnailCache<U> aThumbnailCache)
	{
		itsImages = aImages;
		itsThumbnailCache = aThumbnailCache;
		itsImages.addListener((IListListener) itsEntriesListener);
		itsHighlightedObjects.addListener(itsHighlightedObjectsListener);
		
		addMouseListener(new MouseAdapter()
				{
					public void mousePressed(MouseEvent aE)
					{
						IImageEntry<U> theEntry = getObjectAt(aE.getX(), aE.getY());
						
						int theCount = aE.getClickCount();
						if (theCount == 1) highlight(theEntry, aE.isControlDown());
						else if (theCount == 2 && theEntry != null)
						{
							itsObjectSelected.fire(theEntry.getUserObject());
							highlight(theEntry, false);
						}
					}
				});
		
	}
	
	public void addNotify()
	{
		super.addNotify();
		if (itsThumbnailCache != null) itsThumbnailCache.eThumbnailLoaded.addListener(itsThumbnailCacheListener);
	}
	
	public void removeNotify()
	{
		if (itsThumbnailCache != null) itsThumbnailCache.eThumbnailLoaded.removeListener(itsThumbnailCacheListener);
		super.removeNotify();
	}
	
	/**
	 * Adds the given entry to the current selection.
	 */
	private void highlight (IImageEntry<U> aEntry, boolean aMaintainSelection)
	{
		if (! aMaintainSelection) itsHighlightedObjects.clear();
		if (aEntry != null) itsHighlightedObjects.add(aEntry.getUserObject());
	}
	
	public Dimension getPreferredSize()
	{
		int theDimension = pImageSize.get();
		int theCellHeight = theDimension + V_GAP + V_NAME_GAP;
		int theCellWidth = theDimension + H_GAP;
		
		int theCurrentWidth = getWidth();
		if (theCurrentWidth < theCellWidth + H_GAP) theCurrentWidth = theCellWidth + H_GAP;
		
		int theCols = theCurrentWidth / theCellWidth;
		
		int theCollectionSize = itsImages.size();
		int theNRows = (theCollectionSize+theCols-1)/theCols;
		return new Dimension(
				theCols*(theDimension + H_GAP) + H_GAP, 
				theNRows*(theDimension + V_GAP + V_NAME_GAP) + V_GAP);
	}
	
	public Dimension getPreferredScrollableViewportSize()
	{
		return getPreferredSize();
	}
	
	public int getScrollableBlockIncrement(Rectangle aVisibleRect, int aOrientation, int aDirection)
	{
		int theDimension = pImageSize.get();
		int theCellHeight = theDimension + V_GAP + V_NAME_GAP;
		int theCellWidth = theDimension + H_GAP;
		
		if (aOrientation == SwingConstants.VERTICAL) return getHeight() - theCellHeight;
		else return 10; // We should not scroll horizontally
	}

	public int getScrollableUnitIncrement(Rectangle aVisibleRect, int aOrientation, int aDirection)
	{
		return 20;
	}

	public boolean getScrollableTracksViewportHeight()
	{
		return false;
	}

	public boolean getScrollableTracksViewportWidth()
	{
		return true;
	}

	protected void paintComponent(Graphics aG)
	{
		super.paintComponent(aG);
		
		Graphics2D theGraphics = (Graphics2D) aG;
		theGraphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		theGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		theGraphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

		Rectangle theClipBounds = aG.getClipBounds();
		
		int theSize = itsImages.size();
		int theDimension = pImageSize.get();
		
//		We round the requested dimension so as to avoid requesting too many different thumbnails
		int theRequestedDimension = 200 * ((theDimension+199)/200); 
		
		int theCellHeight = theDimension + V_GAP + V_NAME_GAP;
		int theCellWidth = theDimension + H_GAP;
		
		int theCurrentWidth = getWidth();
		int theCols = Math.max (theCurrentWidth / theCellWidth, 1);
		
		int theY = theCellHeight * (theClipBounds.y / theCellHeight);
		
		while (theY < theClipBounds.y + theClipBounds.height)
		{
			int theIndex = theCols * theY / theCellHeight;
			
			int theX = 0;
			for (int i=0;i<theCols;i++)
			{
				if (theIndex+i < theSize)
				{
					IImageEntry<U> theEntry = itsImages.get(theIndex+i);
					
					if (itsHighlightedObjects.contains(theEntry.getUserObject()))
					{
						aG.setColor(Color.BLUE);
						aG.fillRect(theX, theY, theCellWidth + H_GAP, theCellHeight + V_GAP);
					}
					
					BufferedImage theImage = theEntry.getImage(theRequestedDimension);
					
					if (theImage != null) paintImage(aG, theImage, theX + H_GAP, theY + V_GAP, theDimension);
					
					Rectangle2D theNameBounds = new Rectangle(
							theX + H_GAP, 
							theY + V_GAP + theDimension,
							theDimension,
							V_NAME_GAP - V_GAP);
					
					TextPainter.paint(
							(Graphics2D) aG, 
							TextPainter.SANS_SERIF_PLAIN_10, 
							false, 
							Color.BLACK, 
							theEntry.getName(), 
							theNameBounds,
							TextPainter.VerticalAlignment.CENTER,
							TextPainter.HorizontalAlignment.CENTER);
				}
				
				theX += theCellWidth;
			}
			
			theY += theCellHeight;
		}
	}
	
	private void paintImage(Graphics aGraphics, BufferedImage aImage, int aX, int aY, int aMaxSize)
	{
		// Determine the scale.
		double theScale = 1;
		int theOriginalW = aImage.getWidth();
		int theOriginalH = aImage.getHeight();
		if (theOriginalW > theOriginalH)
		{
			theScale = (double) aMaxSize / (double) theOriginalW;
		}
		else
		{
			theScale = (double) aMaxSize / (double) theOriginalH;
		}
		
		// Determine size of new image.
		// One of them should equal maxSize.
		int theW = (int) (theScale * theOriginalW);
		int theH = (int) (theScale * theOriginalH);


		aGraphics.drawImage(aImage, aX, aY, theW, theH, null);
	}
	
	private IImageEntry<U> getObjectAt (int aX, int aY)
	{
		int theDimension = pImageSize.get();
		int theCellHeight = theDimension + V_GAP + V_NAME_GAP;
		int theCellWidth = theDimension + H_GAP;
		
		int theCurrentWidth = getWidth();
		int theCols = Math.max (theCurrentWidth / theCellWidth, 1);

		
		int theIndex = theCols * (aY/theCellHeight) + (aX/theCellWidth);
		
		int theSize = itsImages.size();
		if (theIndex < 0 || theIndex >= theSize) return null;
		else return itsImages.get(theIndex);
	}
}