/*
 * Created on 20-oct-2004
 */
package zz.csg.impl.interactive;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.BoundedRangeModel;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import zz.csg.api.GraphicObjectContext;
import zz.csg.api.IDisplay;
import zz.csg.impl.AbstractRectangularGraphicObject;


/**
 * This graphic objects is a well-known scroll bar.
 * @author gpothier
 */
public abstract class SVGScrollBar extends AbstractRectangularGraphicObject 
implements ChangeListener
{
	private Zone[] itsZones =
	{
			new UpArrow(), new DownArrow()
	};
	
	private Zone itsHighlightedZone;
	private Zone itsPressedZone;
	
	/**
	 * Retrieves the model of this scrollbar for the given context.
	 */
	protected abstract BoundedRangeModel getModel (GraphicObjectContext aContext); 
	
	protected Object createContextualData(GraphicObjectContext aContext)
	{
		ContextualData theData = new ContextualData();
		theData.setTicker(new Ticker(aContext));
		return theData;
	}
	
	public void stateChanged(ChangeEvent aE)
	{
		repaintAllContexts();
	}
	
	@Override
	protected void paintTransformed(IDisplay aDisplay, GraphicObjectContext aContext, Graphics2D aGraphics, Area aVisibleArea)
	{
		for (Zone theZone : itsZones) theZone.paint(aContext, aGraphics);
	}
	
	public boolean isMouseAware(GraphicObjectContext aContext)
	{
		return true;
	}
	
	protected ContextualData getContextualData (GraphicObjectContext aContext) 
	{
		return (ContextualData) super.getContextualData(aContext);
	}
	
	public boolean mousePressed(GraphicObjectContext aContext, MouseEvent aEvent, Point2D aPoint)
	{
		itsPressedZone = getZone(aContext, aPoint);
		SVGScrollBar.Ticker theTicker = getContextualData(aContext).getTicker();
		theTicker.setZone(itsPressedZone);
		return false;
	}
	
	public boolean mouseReleased(GraphicObjectContext aContext, MouseEvent aEvent, Point2D aPoint)
	{
		itsPressedZone = null;
		SVGScrollBar.Ticker theTicker = getContextualData(aContext).getTicker();
		theTicker.setZone(null);
		return false;
	}
	
	public void mouseExited(GraphicObjectContext aContext, MouseEvent aEvent)
	{
		itsHighlightedZone = null;
		repaint(aContext);
	}
	
	public boolean mouseMoved(GraphicObjectContext aContext, MouseEvent aEvent, Point2D aPoint)
	{
		itsHighlightedZone = getZone(aContext, aPoint);
		repaint(aContext);
		return true;
	}
	
	private Zone getZone (GraphicObjectContext aContext, Point2D aPoint)
	{
		for (Zone theZone : itsZones) 
			if (theZone.isInside(aContext, aPoint)) return theZone;
		
		return null;
	}
	
	/**
	 * This class periodically sends click events to the zone
	 * on which the mouse is pressed.
	 * @author gpothier
	 */
	private static class Ticker extends Timer implements ActionListener
	{
		private GraphicObjectContext itsContext;
		private Zone itsZone;
		
		public Ticker(GraphicObjectContext aContext)
		{
			super (200, null);
			setInitialDelay(250);
			setDelay(100);
			
			itsContext = aContext;
			addActionListener(this);
			setRepeats(true);
		}

		public void actionPerformed(ActionEvent aE)
		{
			itsZone.mouseClicked(itsContext);
		}
		
		public void setZone(Zone aZone)
		{
			itsZone = aZone;
			if (itsZone != null) 
			{
				actionPerformed(null);
				restart();
			}
			else stop();
		}
	}
	
	private abstract class Zone
	{
		public abstract void paint(GraphicObjectContext aContext, Graphics2D aGraphics);
		public abstract boolean isInside (GraphicObjectContext aContext, Point2D aPoint2D);
		public abstract void mouseClicked(GraphicObjectContext aContext);
		
		protected boolean isHighlighted (GraphicObjectContext aContext)
		{
			return itsHighlightedZone == this;
		}
	}
	
	
	
	private class UpArrow extends Zone
	{
		private Rectangle2D getBounds(GraphicObjectContext aContext)
		{
			Rectangle2D theBounds = SVGScrollBar.this.getBounds(aContext);
			double theRadius = theBounds.getWidth();
			return new Rectangle2D.Double(
					theBounds.getX(), 
					theBounds.getY(), 
					theRadius, 
					theRadius);
		}
		
		public boolean isInside(GraphicObjectContext aContext, Point2D aPoint2D)
		{
			return getBounds(aContext).contains(aPoint2D);
		}
		
		public void mouseClicked(GraphicObjectContext aContext)
		{
			BoundedRangeModel theModel = getModel(aContext);
			theModel.setValue(theModel.getValue() - 1);
		}
		
		public void paint(GraphicObjectContext aContext, Graphics2D aGraphics)
		{
			Rectangle2D theBounds = getBounds(aContext);
			
			aGraphics.setColor(isHighlighted(aContext) ? Color.black : Color.red);
			aGraphics.draw(theBounds);
		}
	}
	
	
	private class DownArrow extends Zone
	{
		private Rectangle2D getBounds(GraphicObjectContext aContext)
		{
			Rectangle2D theBounds = SVGScrollBar.this.getBounds(aContext);
			double theRadius = theBounds.getWidth();
			return new Rectangle2D.Double(
					theBounds.getX(), 
					theBounds.getY() + theBounds.getHeight() - theRadius, 
					theRadius, 
					theRadius);
		}
		
		public boolean isInside(GraphicObjectContext aContext, Point2D aPoint2D)
		{
			return getBounds(aContext).contains(aPoint2D);
		}
		
		public void mouseClicked(GraphicObjectContext aContext)
		{
			BoundedRangeModel theModel = getModel(aContext);
			theModel.setValue(theModel.getValue() + 1);
		}
		
		public void paint(GraphicObjectContext aContext, Graphics2D aGraphics)
		{
			Rectangle2D theBounds = getBounds(aContext);
			
			aGraphics.setColor(isHighlighted(aContext) ? Color.black : Color.red);
			aGraphics.draw(theBounds);
		}
	}
	
	/**
	 * This is a bundle of data about the scrollbar that is stored in the
	 * {@link GraphicObjectContext context}.
	 * @author gpothier
	 */
	private static class ContextualData
	{
		private Ticker itsTicker;
		
		public Ticker getTicker()
		{
			return itsTicker;
		}
		
		public void setTicker(Ticker aTicker)
		{
			itsTicker = aTicker;
		}
	}
	
}
