/*
 * Created on Jul 13, 2005
 */
package zz.csg;

import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;

import zz.csg.api.GraphicNode;
import zz.csg.api.IGraphicObject;
import zz.csg.display.GraphicPanel;
import zz.utils.properties.IProperty;
import zz.utils.properties.IRWProperty;
import zz.utils.properties.PropertyId;
import zz.utils.properties.PropertyListener;

/**
 * Various utilities for the csg package
 * @author gpothier
 */
public class CSGUtils
{
	/**
	 * Creates and shows a frame that displays the specified object.
	 * @return The created frame.
	 */
	public static JFrame showFrame (String aTitle, IGraphicObject aGraphicObject)
	{
		JFrame theFrame = new JFrame (aTitle);
		theFrame.setContentPane(new GraphicPanel(new GraphicNode(aGraphicObject)));
		theFrame.pack();
		theFrame.setVisible(true);

		return theFrame;
	}
	
	public static <T> void  connect (
			IRWProperty<T> aSourceProperty,
			IRWProperty<T> aTargetProperty,
			boolean aSymmetric)
	{
		//Forward current value of the property.
		T theValue = aSourceProperty.get();
		aTargetProperty.set(theValue);
		
		aSourceProperty.addHardListener(new Connector<T>(aTargetProperty));
		if (aSymmetric) aTargetProperty.addHardListener(new Connector<T> (aSourceProperty));
		
	}

	/**
	 * Connects the sizes of two rectangle properties.
	 * This is a symetric connection.
	 */
	public static void  connectSizes (
			IRWProperty<Rectangle2D> aProperty1,
			IRWProperty<Rectangle2D> aProperty2)
	{
		forwardSize(aProperty1, aProperty2);
		aProperty1.addHardListener(new SizeConnector(aProperty2));
		aProperty2.addHardListener(new SizeConnector(aProperty1));
	}

	/**
	 * Forwards the size of a rectangle property from a source
	 * to a target, without modifying the target's
	 * position.
	 */
	public static void forwardSize (
			IProperty<Rectangle2D> aSource, 
			IRWProperty<Rectangle2D> aTarget)
	{
		Rectangle2D theSourceRectangle = aSource.get();
		Rectangle2D theTargetRectangle = aTarget.get();
		
		if (theSourceRectangle == null) 
		{
			aTarget.set(null);
			return;
		}
		
		double theX = theTargetRectangle != null ? theTargetRectangle.getX() : 0;
		double theY = theTargetRectangle != null ? theTargetRectangle.getY() : 0;
		double theW = theTargetRectangle != null ? theTargetRectangle.getWidth() : 0;
		double theH = theTargetRectangle != null ? theTargetRectangle.getHeight() : 0;
		
		double theNW = theSourceRectangle.getWidth();
		double theNH = theSourceRectangle.getHeight();
		
		if (theW != theNW || theH != theNH)
		{
			aTarget.set(new Rectangle2D.Double(theX, theY, theNW, theNH));
		}
	}
	
	/**
	 * A property listener that forwards property changes to a target property.
	 * @author gpothier
	 */
	public static class Connector<T> extends PropertyListener<T>
	{
		private IRWProperty<T> itsTargetProperty;
		
		
		public Connector(IRWProperty<T> aTargetProperty)
		{
			itsTargetProperty = aTargetProperty;
		}

		public void propertyChanged(IProperty<T> aProperty, T aOldValue, T aNewValue)
		{
			itsTargetProperty.set(aNewValue);
		}
	}
	
	/**
	 * This property listener forwards the listened property's size to a destination
	 * property
	 * @author gpothier
	 */
	public static class SizeConnector extends PropertyListener<Rectangle2D>
	{
		private IRWProperty<Rectangle2D> itsTargetProperty;
		
		
		public SizeConnector(IRWProperty<Rectangle2D> aTargetProperty)
		{
			itsTargetProperty = aTargetProperty;
		}

		public void propertyChanged(
				IProperty<Rectangle2D> aProperty, 
				Rectangle2D aOldValue, 
				Rectangle2D aNewValue)
		{
			forwardSize(aProperty, itsTargetProperty);
		}
		
	}


	
}
