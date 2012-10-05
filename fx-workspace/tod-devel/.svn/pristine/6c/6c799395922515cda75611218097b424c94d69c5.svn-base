/*
 * Created on 06-may-2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package zz.csg.display;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.TexturePaint;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.RepaintManager;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;

import zz.csg.Log;
import zz.csg.Resources;
import zz.csg.api.GraphicNode;
import zz.csg.api.GraphicObjectContext;
import zz.csg.api.IDisplay;
import zz.csg.api.IGraphicContainer;
import zz.csg.api.IGraphicObject;
import zz.csg.api.IProxyGraphicObject;
import zz.csg.api.IRectangularGraphicObject;
import zz.csg.api.PickResult;
import zz.csg.api.edition.IGraphicMapper;
import zz.csg.impl.AbstractGraphicObject;
import zz.utils.properties.IProperty;
import zz.utils.properties.IPropertyListener;
import zz.utils.ui.HorizontalAlignment;
import zz.utils.ui.IMouseAware;
import zz.utils.ui.MouseHandler;
import zz.utils.ui.StackLayout;
import zz.utils.ui.VerticalAlignment;
import zz.utils.ui.text.TextPainter;
import zz.utils.ui.text.XFont;


/**
 * This component displays the content of a 
 * {@link zz.csg.api.IGraphicObject}.
 * It will display the g.o.'s content as big as possible, but maintaining the original
 * aspect ratio.
 *
 * @author gpothier
 */
public class GraphicPanel extends JPanel 
implements IGraphicMapper, IDisplay, IPropertyListener<Rectangle2D>, Scrollable
{
	/**
	 * Transform set by the user.
	 * If null, the object is adapted so that it fits in the panel
	 */
	private AffineTransform itsTransform;
	
	/**
	 * The transform before centering.
	 */
	private AffineTransform itsPreCenterTransform = new AffineTransform();
	
	private AffineTransform itsRealTransform;
	
	private GraphicNode itsRootNode;
	
	/**
	 * We use this field to keep track of the currently acquired node.
	 */
	private GraphicNode itsAcquiredNode;
	
	/**
	 * This flag is set when this component is actually displayed.
	 */
	private boolean itsDisplayed = false;
	
	/**
	 * When this field is not null, a visual indicator is shown that represents
	 * the rectangle
	 */
	private Rectangle2D itsShownBounds = null;
	
	/**
	 * When this flag is set, the root graphic object is centered in
	 * the rectangle contained in {@link #itsShownBounds}
	 */
	private boolean itsCentered = false;
	
	/**
	 * This flag is set when an exception occured while painting the panel.
	 * While the flag is set, no more painting will occur.
	 */
	private boolean itsExceptionInPaint = false;
	
	private static final boolean DISABLE_DOUBLE_BUFFER =
		Boolean.parseBoolean(System.getProperty("disable-double-buffer"));
	
	public GraphicPanel ()
	{
		this (null);
	}
	
	public GraphicPanel (GraphicNode aRootNode)
	{
		if (DISABLE_DOUBLE_BUFFER)
		{
			RepaintManager.currentManager(this).setDoubleBufferingEnabled(false);
		}
		setRootNode(aRootNode);
		setOpaque(false);
		setLayout(null);
		addComponentListener(new ComponentAdapter ()
		{
			public void componentResized(ComponentEvent aE)
			{
				updateTransform ();
				repaint();
			}
		});
		
		// Register mouse listener
		MyMouseListener theMouseListener = new MyMouseListener ();
		addMouseListener(theMouseListener);
		addMouseMotionListener(theMouseListener);
		addMouseWheelListener(theMouseListener);
		addKeyListener(theMouseListener);
	}
	
	public void addNotify()
	{
		super.addNotify();
		assert itsDisplayed == false;
		itsDisplayed = true;
		if (itsRootNode != null) acquireNode(itsRootNode);
	}
	
	public void removeNotify()
	{
		super.removeNotify();
		assert itsDisplayed == true;
		itsDisplayed = false;
		if (itsAcquiredNode != null) releaseNode();
	}
	
	public AffineTransform getTransform()
	{
		return itsTransform;
	}
	
	public void setTransform(AffineTransform aTransform)
	{
		itsTransform = aTransform;
		updateTransform();
		updateSize();
	}
	
	public void setShownBounds(Rectangle2D aShownBounds)
	{
		itsShownBounds = aShownBounds;
		updateSize();
		repaint();
	}
	
	public Rectangle2D getShownBounds()
	{
		return itsShownBounds;
	}
	
	public void setCentered(boolean aCentered)
	{
		itsCentered = aCentered;
		updateTransform();
		repaint();
	}
	
	public GraphicNode getRootNode()
	{
		return itsRootNode;
	}
	
	/**
	 * Sets up listeners and other elements on the given node.
	 */
	private void acquireNode (GraphicNode aNode)
	{
		assert itsAcquiredNode == null;
		
		AbstractGraphicObject theGraphicObject = 
			(AbstractGraphicObject) itsRootNode.getGraphicObject();
		
		theGraphicObject.addDisplay(itsRootNode.getContext(), this);
		
		if (theGraphicObject instanceof IRectangularGraphicObject)
		{
			IRectangularGraphicObject theRectangularGraphicObject = 
				(IRectangularGraphicObject) theGraphicObject;

			theRectangularGraphicObject.pBounds().addListener(this);
		}
		
		itsAcquiredNode = aNode;
	}

	/**
	 * Performs the opposite of {@link #acquireNode(GraphicNode)}
	 */
	private void releaseNode ()
	{
		AbstractGraphicObject theGraphicObject = 
			(AbstractGraphicObject) itsAcquiredNode.getGraphicObject();
		
		theGraphicObject.removeDisplay(itsAcquiredNode.getContext(), this);
		
		if (theGraphicObject instanceof IRectangularGraphicObject)
		{
			IRectangularGraphicObject theRectangularGraphicObject = 
				(IRectangularGraphicObject) theGraphicObject;

			theRectangularGraphicObject.pBounds().removeListener(this);
		}

		itsAcquiredNode = null;
	}
	
	public void setRootNode(GraphicNode aRootNode)
	{
		if (itsAcquiredNode != null) releaseNode();
		itsRootNode = aRootNode;
		
		if (itsDisplayed && itsRootNode != null) acquireNode(itsRootNode);
		
//		itsMainWindow.setRootGraphicObject(aRootGraphicObject);
		updateSize();
		repaint();
	}
	
	/**
	 * Sets the root node of this graphic panel.
	 * @see #setRootNode(GraphicNode)
	 */
	public void setRootNode (IGraphicObject aGraphicObject)
	{
		setRootNode(new GraphicNode<IGraphicObject>(aGraphicObject));
	}
	

	public void propertyChanged(
			IProperty<Rectangle2D> aProperty,
			Rectangle2D aOldValue, 
			Rectangle2D aNewValue)
	{
		if (getParent() != null) updateSize();
	}
	
	public void propertyValueChanged(IProperty<Rectangle2D> aProperty)
	{
	}
	
	/**
	 * Updates the preferred size of this component according to the bounds
	 * of the root graphic object.
	 */
	private void updateSize()
	{
		if (itsRootNode != null)
		{
			Rectangle2D theBounds = itsRootNode.getBounds();
			
			if (itsShownBounds != null)
			{
				theBounds = (Rectangle2D) theBounds.clone();
				theBounds.add(itsShownBounds);
			}
			
			Rectangle theTransformedBounds = rootToPixel(theBounds);
			setPreferredSize(new Dimension (
					(int) theTransformedBounds.getMaxX(),
					(int) theTransformedBounds.getMaxY()));
			
			revalidate();
			repaint();
		}
		
		updateTransform();
	}
	
	/**
	 * Updates the root transform, for instance in response to a size change,
	 * so that the main window is displayed as big as possible, 
	 * but keeping its original ratio.
	 */
	private void updateTransform()
	{
		if (getParent() == null) return;
		
		AffineTransform theRealTransform;
		
		if (itsRootNode == null)
		{
			theRealTransform = null;
		}
		else
		{
			theRealTransform = new AffineTransform ();

			Rectangle2D theBounds = itsRootNode.getBounds();

			if (itsTransform == null)
			{
				// If there is no user transform, we fit the root to the panel's size
				Dimension theDimension = getSize();
				double thePanelWidth = theDimension.getWidth();
				double thePanelHeight = theDimension.getHeight();
				
				if (thePanelWidth == 0 || thePanelHeight == 0) return;
				
				Rectangle2D theTotalBounds;
				
				if (itsShownBounds != null)
				{
					theTotalBounds = (Rectangle2D) theBounds.clone();
					theTotalBounds.add(itsShownBounds);
				}
				else theTotalBounds = theBounds;
				
				double theLogicalWidth = theTotalBounds.getMaxX();
				double theLogicalHeight = theTotalBounds.getMaxY();
				
				double theHorzRatio = thePanelWidth/theLogicalWidth;
				double theVertRatio = thePanelHeight/theLogicalHeight;
				
				double theRatio = Math.min(theHorzRatio, theVertRatio);
				
				double theWindowWidth = theLogicalWidth * theRatio;
				double theWindowHeight = theLogicalHeight * theRatio;
				
				theRealTransform.translate(
						(thePanelWidth - theWindowWidth)/2, 
						(thePanelHeight - theWindowHeight)/2);
				
				theRealTransform.scale(theRatio, theRatio);
			}
			else theRealTransform.setTransform(itsTransform);

			itsPreCenterTransform.setTransform(theRealTransform);
			
			if (itsCentered)
			{
				double theTX = itsShownBounds.getX()
					+ (itsShownBounds.getWidth() - theBounds.getWidth())/2;
				
				double theTY = itsShownBounds.getY()
					+ (itsShownBounds.getHeight() - theBounds.getHeight())/2;
				
				theRealTransform.translate(theTX, theTY);
			}

		}
		
		
		setRealTransform(theRealTransform);
		updateHolders();
	}
	
	/**
	 * Updates the bounds of all holders.
	 */
	protected void updateHolders()
	{
		for (Component theComponent : getComponents()) 
		{
			if (theComponent instanceof ComponentHolder)
			{
				ComponentHolder theHolder = (ComponentHolder) theComponent;
				theHolder.updateBounds();
			}
		}
	}
	
	/**
	 * Returns the concatenation of user's transform and additional transformations
	 * handled by this class.
	 */
	private AffineTransform getRealTransform()
	{
		return itsRealTransform;
	}
	
	private void setRealTransform(AffineTransform aTransform)
	{
		itsRealTransform = aTransform;
	}
	
	/**
	 * Repaints a rectangular region of this screen.
	 * @param aBounds Region to repaint, in logical coordinates.  
	 */
	protected void repaintLogicalRegion (Rectangle2D aRegion)
	{
//		Log.GRAPHIC.debug("GraphicPanel.repaintLogicalRegion("+aRegion+")");
		if (getRealTransform() != null)
		{
			Shape theShape = getRealTransform().createTransformedShape(aRegion);
			Rectangle theBounds = theShape.getBounds();
			repaint(theBounds);
		}
	}
	
	void displayComponent (JComponent aComponent, Rectangle2D aBounds)
	{
		ComponentHolder theHolder = new ComponentHolder(aComponent, aBounds);
		add (theHolder);
		aComponent.revalidate();
		theHolder.repaint();
	}
	
//	private static final Paint BACKGROUND_PAINT = createBackgroundTexture();
	public static final Color BACKGROUND_PAINT = Color.WHITE;

	private static TexturePaint createBackgroundTexture ()
	{
		BufferedImage theImage = Resources.IMAGE_GRAHICEDITOR_BACKGROUND;
		int theW = theImage.getWidth(null);
		int theH = theImage.getHeight(null);
		return new TexturePaint(theImage, new Rectangle2D.Double (0, 0, theW, theH));
	}
	
	private BufferedImage itsImage;

	protected void paintComponent(Graphics aG)
	{
//		System.out.println("GraphicPanel.paintComponent()");
		Graphics2D theGraphics = (Graphics2D) aG;
		
		if (itsExceptionInPaint)
		{
			theGraphics.setColor(Color.WHITE);
			theGraphics.fillRect(getX(), getY(), getWidth(), getHeight());
			
			TextPainter.paint(
					theGraphics, 
					XFont.DEFAULT_PLAIN,
					false,
					Color.RED, 
					"Error - painting disabled",
					getBounds(),
					VerticalAlignment.TOP,
					HorizontalAlignment.LEFT);
			
			return;
		}
		
		try
		{
			if (itsRootNode != null)
			{
				
				// Optionally paint screen bounds
				Rectangle theBackgroundBounds;
				if (itsShownBounds != null)
				{
					theBackgroundBounds =  toPixel(itsShownBounds, itsPreCenterTransform);
				}
				else 
				{
					// If we don't have to show screen bounds, we materialize
					// the root's bounds
					Rectangle2D theBounds = itsRootNode.getBounds();

					theBackgroundBounds = rootToPixel(theBounds);
				}
				
				theGraphics.setPaint(BACKGROUND_PAINT);
				theGraphics.fill(theBackgroundBounds);
				
				// Setup transform
				AffineTransform theOriginalTransform = theGraphics.getTransform();
				if (getRealTransform() != null) theGraphics.transform(getRealTransform());
				theGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				
				// Compute visible region
				Rectangle theVisibleRect = getVisibleRect();
				Point2D theOrigin = pixelToRoot(new Point(
						theVisibleRect.x, 
						theVisibleRect.y));
				
				Point2D theBLCorner = pixelToRoot(new Point (
						theVisibleRect.x + theVisibleRect.width, 
						theVisibleRect.y + theVisibleRect.height));
				
				Rectangle2D.Double theVisibleRegion = new Rectangle2D.Double(
						theOrigin.getX(),
						theOrigin.getY(),
						0,
						0);
				theVisibleRegion.add(theBLCorner);
				Area theVisibleArea = new Area (theVisibleRegion);
				
				// Paint the root graphic object
				itsRootNode.getGraphicObject().checkValid();
				itsRootNode.paint(this, theGraphics, theVisibleArea);
				
				theGraphics.setTransform(theOriginalTransform);
			}
		}
		catch (Exception e)
		{
			itsExceptionInPaint = true;
			e.printStackTrace();
		}
	}
	

	public Point rootToPixel(Point2D aPoint)
	{
		AffineTransform theTransform = getRealTransform();
		if (theTransform != null) 
		{
			Point theResult = new Point ();
			theTransform.transform(aPoint, theResult);
			return theResult;
		}
		else return new Point ((int) aPoint.getX(), (int) aPoint.getY());
	}
	
	public Rectangle rootToPixel (Rectangle2D aRectangle)
	{
		return toPixel(aRectangle, getRealTransform());
	}
	
	private Rectangle toPixel(Rectangle2D aRectangle, AffineTransform aTransform)
	{
		return aTransform != null 
		? aTransform.createTransformedShape(aRectangle).getBounds()
		: aRectangle.getBounds();
		
	}
	
	public Point2D pixelToRoot(Point aPoint)
	{
		try
		{
			if (getRealTransform() != null) 
			{
				Point2D theResult = new Point2D.Double ();
				getRealTransform().inverseTransform(aPoint, theResult);
				return theResult;
			}
			else return aPoint;
		} 
		catch (NoninvertibleTransformException e)
		{
			Log.GRAPHIC.warn("Warning: non invertible transform (GraphicPanel.pixelToRoot)");
			return new Point2D.Double();
		}
	}

	public Point localToPixel (GraphicObjectContext aContext, IGraphicObject aGraphicObject, Point2D aPoint)
	{
		Point2D theResult = aGraphicObject.localToRoot(aContext, aPoint);
		return rootToPixel(theResult);
	}
	
	public Rectangle localToPixel(GraphicObjectContext aContext, IGraphicObject aGraphicObject, Rectangle2D aRectangle)
	{
		Point2D theCorner1 = new Point2D.Double (
				aRectangle.getMinX(),
				aRectangle.getMinY());
		
		Point2D theCorner2 = new Point2D.Double (
				aRectangle.getMaxX(),
				aRectangle.getMaxY());

		Rectangle theRectangle = new Rectangle(localToPixel(aContext, aGraphicObject, theCorner1));
		theRectangle.add (localToPixel(aContext, aGraphicObject, theCorner2));
		
		return theRectangle;
	}
	
	public void repaint(Rectangle2D aBounds)
	{
		repaintLogicalRegion(aBounds);
	}
	
	
	public void display(JComponent aComponent, Rectangle2D aBounds)
	{
		displayComponent (aComponent, aBounds);
	}
	
	public void remove(JComponent aComponent)
	{
		ComponentHolder theHolder = (ComponentHolder) aComponent.getParent();
		theHolder.getParent().remove(theHolder);
	}


	public Dimension getPreferredScrollableViewportSize()
	{
		return getPreferredSize();
	}

	public int getScrollableBlockIncrement(Rectangle aVisibleRect, int aOrientation, int aDirection)
	{
		switch (aOrientation)
		{
		case SwingConstants.HORIZONTAL:
			return 80 * aVisibleRect.width / 100;
			
		case SwingConstants.VERTICAL:
			return 80 * aVisibleRect.height / 100;
			
		default:
			throw new RuntimeException();
		}
	}

	public int getScrollableUnitIncrement(Rectangle aVisibleRect, int aOrientation, int aDirection)
	{
		switch (aOrientation)
		{
		case SwingConstants.HORIZONTAL:
			return 10 * aVisibleRect.width / 100;
			
		case SwingConstants.VERTICAL:
			return 10 * aVisibleRect.height / 100;
			
		default:
			throw new RuntimeException();
		}
	}
	
	public boolean getScrollableTracksViewportHeight()
	{
		return false;
	}

	public boolean getScrollableTracksViewportWidth()
	{
		return false;
	}

	/**
	 * This class contains all the mouse handling.
	 */
	private class MyMouseListener extends MouseHandler<PickResult>
	{
		public MyMouseListener()
		{
			super(GraphicPanel.this, true);
		}
		
		protected Point2D pixelToRoot(Point aPoint)
		{
			return GraphicPanel.this.pixelToRoot(aPoint);
		}
		
		protected IMouseAware getMouseAware(PickResult aElement)
		{
			return aElement;
		}

		protected Point2D rootToLocal(PickResult aElement, Point2D aPoint)
		{
			IGraphicObject theGraphicObject = aElement.getGraphicObject();
			GraphicObjectContext theContext = aElement.getContext();
			return theGraphicObject.rootToLocal(theContext, aPoint);
		}

		protected PickResult getElementAt(Point2D aPoint)
		{
			if (itsRootNode != null)
			{
				return itsRootNode.getGraphicObject().pick(
						itsRootNode.getContext(), 
						aPoint);
			}
			else return null;
		}

		protected PickResult getParent(PickResult aElement)
		{
			IGraphicObject theGraphicObject = aElement.getGraphicObject();
			GraphicObjectContext theContext = aElement.getContext();
			
			IGraphicContainer theParent = theGraphicObject.getParent();
			if (theParent != null)
			{
				return new PickResult(theParent, theContext);
			}
			else if (theContext != null)
			{
				IProxyGraphicObject theProxyGraphicObject = theContext.getProxyGraphicObject();
				GraphicObjectContext theParentContext = theContext.getParentContext();
				
				if (theProxyGraphicObject == null) return null;
				else return new PickResult(
						theProxyGraphicObject, 
						theParentContext);
			}
			else return null;
		}
		
	}
	
	
	/**
	 * This panel holds components displayed with {@link GraphicPanel#display(JComponent, Rectangle2D)}.
	 * It adjusts its bounds according to the current transform of the graphic panel.
	 * @author gpothier
	 */
	private class ComponentHolder extends JPanel
	{
		private final Rectangle2D itsBounds;

		private ComponentHolder(JComponent aComponent, Rectangle2D aBounds)
		{
			super (new StackLayout());
			add (aComponent);
			
			itsBounds = aBounds;
			updateBounds();
		}
		
		/**
		 * Updates the bounds of this holder according to 
		 * the current transform.
		 */
		public void updateBounds()
		{
			AffineTransform theTransform = getRealTransform();
			Shape theShape = theTransform != null 
				? theTransform.createTransformedShape(itsBounds)
				: itsBounds;
				
			setBounds(theShape.getBounds());
			revalidate();
		}
		
	}

}