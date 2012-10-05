/*
 * Created on 08-jun-2004
 */
package zz.csg.impl.figures;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

import zz.csg.Resources;
import zz.csg.api.GraphicObjectContext;
import zz.csg.api.IDisplay;
import zz.csg.api.IGraphicObject;
import zz.csg.api.constraints.IConstrainedRectangle;
import zz.csg.api.edition.IGraphicObjectController;
import zz.csg.api.figures.IGOImage;
import zz.csg.impl.ViewBoxUtils;
import zz.csg.impl.edition.RectangularControllerUtils;
import zz.utils.notification.ObservationCenter;
import zz.utils.notification.Observer;
import zz.utils.properties.IRWProperty;



/**
 * @author gpothier
 */
public class SVGImage extends AbstractFigure 
implements IGOImage, Observer
{
	private final IRWProperty<IProvider> pProvider = new GOSimpleProperty<IProvider>(this, PROVIDER)
	{
		protected void changed(IProvider aOldValue, IProvider aNewValue)
		{
			ObservationCenter.getInstance().unregisterListener(aOldValue, SVGImage.this);
			ObservationCenter.getInstance().registerListener(aNewValue, SVGImage.this);
		}
	};
	
	private IConstrainedRectangle pBounds = createBoundsProperty();
	
	public IConstrainedRectangle pBounds()
	{
		return pBounds;
	}
	
	private IConstrainedRectangle createBoundsProperty()
	{
		IConstrainedRectangle theRectangle = new GOConstrainedRectangle(this, BOUNDS);
		theRectangle.set(0, 0, 0, 0);
		return theRectangle;
	}

	public IRWProperty<IProvider> pProvider()
	{
		return pProvider;
	}
	
	public Rectangle2D getBounds(GraphicObjectContext aContext)
	{
		checkValid();
		return pBounds().get();
	}
	
	public void setSize(double aWidth, double aHeight)
	{
		Rectangle2D theBounds = pBounds.get();
		Rectangle2D theNewBounds = new Rectangle2D.Double(
				theBounds.getX(),
				theBounds.getY(),
				aWidth,
				aHeight);
		pBounds.set(theNewBounds);
	}
	
	public void observe(Object aObservable, Object aData)
	{
		if (aObservable == pProvider.get())
		{
			checkValid();
			repaintAllContexts();
		}
	}

	/**
	 * Returns the object that should be painted by this image.
	 * It can be an awt image or a graphic object
	 * @return
	 */
	protected Object getPaintable()
	{
		IProvider theProvider = pProvider().get();
		Object theData = theProvider != null ? theProvider.getData() : null;

		return getPaintable(theData);
	}
	
	/**
	 * This methods extracts a paintable object from the provided data.
	 * By default it simply returns the given data, but subclasses can
	 * change this behaviour.
	 * @param aData The data obtained from the provider
	 */
	protected Object getPaintable (Object aData)
	{
		return aData;
	}

	@Override
	protected void paintTransformed(IDisplay aDisplay, GraphicObjectContext aContext, Graphics2D aGraphics, Area aVisibleArea)
	{
		Rectangle2D theBounds = getBounds(aContext);
		Shape theOldClip = aGraphics.getClip();
		
		aGraphics.clip(theBounds);
	
		Object thePaintable = getPaintable();
		
		// Actual processing
		if (thePaintable instanceof IGraphicObject)
		{
			IGraphicObject theGraphicObject = (IGraphicObject) thePaintable;
			theGraphicObject.checkValid();
			Rectangle2D theGOBounds = theGraphicObject.getBounds(aContext);
			
			// We compute the viewbox as being the rectangle that 
			// contains the go's bounds and the origin.
			Rectangle2D theViewBox = new Rectangle2D.Double();
			theViewBox.setFrame(theGOBounds);
			theViewBox.add (0, 0);
			
			Area theVisibleArea = new Area (theViewBox);
			theVisibleArea.intersect(aVisibleArea);
			
			AffineTransform theOldTransform = aGraphics.getTransform();
			aGraphics.transform(ViewBoxUtils.createViewBoxTransform(theBounds, theViewBox));

			theGraphicObject.paint(aDisplay, aContext, aGraphics, theVisibleArea);
			aGraphics.setTransform(theOldTransform);
		}
		else if (thePaintable instanceof Image)
		{
			Image theImage = (Image) thePaintable;
			aGraphics.drawImage(
					theImage, 
					(int)theBounds.getX(), (int)theBounds.getY(), 
					(int)theBounds.getWidth(), (int)theBounds.getHeight(), 
					null);
		}
		else if (thePaintable != null)
		{
			aGraphics.drawImage(Resources.ICON_MISSING.getImage(), 0, 0, null);
		}

		aGraphics.setClip(theOldClip);
	}
	
	public void display(
			GraphicObjectContext aContext, 
			JComponent aComponent, 
			Rectangle2D aBounds)
	{
		Rectangle2D theImageBounds = getBounds(aContext);
		Rectangle2D theBounds = new Rectangle2D.Double(
				aBounds.getX() + theImageBounds.getX(),
				aBounds.getY() + theImageBounds.getY(),
				aBounds.getWidth(),
				aBounds.getHeight());
		super.display(aContext, aComponent, theBounds);
	}
	
	public boolean isInside(GraphicObjectContext aContext, Point2D aPoint)
	{
		return getPaintable() != null ? super.isInside(aContext, aPoint) : false;
	}
	
	protected IGraphicObjectController createController(GraphicObjectContext aContext)
	{
		return RectangularControllerUtils.createRectangularController(aContext, this);
	}
}
