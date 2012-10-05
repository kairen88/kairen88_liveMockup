/*
 * Created on Apr 5, 2005
 */
package zz.snipsnap.storysnipper.ui.widgets.imagelist;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JScrollPane;

import zz.snipsnap.storysnipper.Resources;
import zz.utils.list.IList;
import zz.utils.notification.IEvent;
import zz.utils.properties.IListProperty;
import zz.utils.ui.thumbnail.AsyncThumbnailCache;
import zz.waltz.api.IWaltzCanvas;
import zz.waltz.api.WaltzComponent;
import zz.waltz.api.action.DefaultActionModel;
import zz.waltz.api.action.enable.Comparison;
import zz.waltz.api.action.enable.ComparisonCondition;

public class ImageListComponent<U> extends WaltzComponent
{
	private ImageListView<U> itsImageListView;
	
	public ImageListComponent(IList<? extends IImageEntry<U>> aImages)
	{
		this (aImages, null);
	}
	
	public ImageListComponent(IList<? extends IImageEntry<U>> aImages, AsyncThumbnailCache<U> aThumbnailCache)
	{
		itsImageListView = new ImageListView<U>(aImages, aThumbnailCache);
	}
	
	public IListProperty<U> pHighlightedObjects()
	{
		return itsImageListView.pHighlightedObjects;
	}
	
	public IEvent<U> eObjectSelected()
	{
		return itsImageListView.eObjectSelected;
	}

	protected void render(IWaltzCanvas aCanvas)
	{
		aCanvas.setLayout(new BorderLayout());
		
		renderToolbar(aCanvas.createCanvas(BorderLayout.NORTH));
		
		JScrollPane theScrollPane = new JScrollPane(itsImageListView,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		theScrollPane.setPreferredSize(new Dimension(
				itsImageListView.getPreferredSize().width + 30,
				400));

		aCanvas.add (theScrollPane, BorderLayout.CENTER);
	}
	

	private void renderToolbar(IWaltzCanvas aCanvas)
	{
		aCanvas.setLayout(new FlowLayout());
		aCanvas.addAction(BUTTON_ACTION, new ZoomInAction(), null);
		aCanvas.addAction(BUTTON_ACTION, new ZoomOutAction(), null);
	}

	private class ZoomInAction extends DefaultActionModel
	{
		public ZoomInAction()
		{
			pIcon.set(new ImageIcon(Resources.ZOOMIN));
			pEnabled.addCondition(new ComparisonCondition<Integer> (
					itsImageListView.pImageSize, 
					Comparison.LT, 
					400));
		}
		
		public void performed(JComponent aComponent)
		{
			int theSize = itsImageListView.pImageSize.get();
			itsImageListView.pImageSize.set(theSize+20);
		}
	}

	private class ZoomOutAction extends DefaultActionModel
	{
		public ZoomOutAction()
		{
			pIcon.set(new ImageIcon(Resources.ZOOMOUT));
			pEnabled.addCondition(new ComparisonCondition<Integer> (
					itsImageListView.pImageSize, 
					Comparison.GT, 
					40));
		}
		
		public void performed(JComponent aComponent)
		{
			int theSize = itsImageListView.pImageSize.get();
			itsImageListView.pImageSize.set(theSize-20);
		}
	}
}
