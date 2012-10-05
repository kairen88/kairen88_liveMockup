/*
 * Created on Apr 20, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package zz.csg.api.figures;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import zz.csg.api.IRectangularGraphicObject;
import zz.utils.ui.text.TextPainter;
import zz.utils.ui.text.XFont;

/**
 * Represents a text laid out inside a rectangular frame.
 * @author gpothier
 */
public interface IGOFlowText extends IGOText, IRectangularGraphicObject
{
//	/**
//	 * Sets up this flow text object so that whenever it is attached its size
//	 * is constrained to the size of its text.
//	 * @param aSystem The system to which the constraints must be added.
//	 * @param aStrength The strength of the added constraints.
//	 */
//	public void setupAutoBounds (ConstraintSystem aSystem, ClStrength aStrength);
	
	/**
	 * Assigns a size computer to this flow text object.
	 * The size computer will be called whenever the text, font, or any property
	 * that might affect the rendered size of the text, is changed.
	 */
	public void setSizeComputer(SizeComputer aSizeComputer);
	
	/**
	 * Interface for objects that compute the size of a text.
	 * @see {@link IGOFlowText#setSizeComputer(SizeComputer)}
	 * @author gpothier
	 */
	public interface SizeComputer
	{
		/**
		 * Computes the size of the given text rendered with the given font.
		 */
		public Point2D computeSize (String aText, XFont aFont);
	}
	
	/**
	 * A default size computer based on {@link TextPainter#computeSize(Graphics2D, Font, boolean, String)}.
	 * @author gpothier
	 */
	public static class DefaultSizeComputer implements SizeComputer
	{
		private static DefaultSizeComputer INSTANCE = new DefaultSizeComputer();

		public static DefaultSizeComputer getInstance()
		{
			return INSTANCE;
		}

		private DefaultSizeComputer()
		{
		}

		public Point2D computeSize(String aText, XFont aFont)
		{
			return TextPainter.computeSize(
					TextPainter.getDefaultGraphics(), 
					aFont.getAWTFont(), 
					aFont.isUnderline(), 
					aText);
		}
	}
	
	/**
	 * This size computer computes the required height for a fixed width
	 * @author gpothier
	 */
	public static class FixedWidthComputer implements SizeComputer
	{
		private float itsWidth;

		public FixedWidthComputer(float aWidth)
		{
			itsWidth = aWidth;
		}

		public Point2D computeSize(String aText, XFont aFont)
		{
			float theHeight = TextPainter.computeHeight(
					TextPainter.getDefaultGraphics(), 
					itsWidth, 
					aFont.getAWTFont(),
					aFont.isUnderline(),
					aText);
			
			return new Point2D.Double(itsWidth, theHeight);
		}
		
		
	}
}
