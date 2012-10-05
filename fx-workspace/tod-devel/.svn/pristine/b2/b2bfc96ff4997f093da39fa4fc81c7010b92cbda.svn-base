/*
 * Created on Dec 6, 2004
 */
package zz.csg.impl.figures;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;

import zz.csg.api.GraphicObjectContext;
import zz.csg.api.figures.IShape;


/**
 * Utilities for working with figures.
 * @author gpothier
 */
public class FigureUtils
{
	private static final Stroke DEFAULT_STROKE = new BasicStroke(1);
	
	/**
	 * Paints a shape in the given graphics using the {@link IShape}'s current
	 * stroke and fill paints.
	 */
	public static void paintShape (
			IShape aFigure, 
			Graphics2D aGraphics, 
			GraphicObjectContext aContext,
			Shape aShape)
	{
		int i = 0;
		Double theStrokeWidth = aFigure.pStrokeWidth().get();
		paintShape(
				aGraphics, 
				aShape, 
				aFigure.pStrokePaint().get(),
				aFigure.pFillPaint().get(),
				theStrokeWidth != null ? theStrokeWidth : 0.1);
	}

	/**
	 * Paints a shape in the given graphics using the specified
	 * attributes.
	 */
	public static void paintShape (
			Graphics2D aGraphics, 
			Shape aShape,
			Paint aStrokePaint,
			Paint aFillPaint,
			double aStrokeWidth)
	{
		if (aFillPaint != null)
		{
			aGraphics.setPaint(aFillPaint);
			aGraphics.fill(aShape);
		}

		if (aStrokePaint != null)
		{
			Stroke theStroke = aStrokeWidth == 1 
				? DEFAULT_STROKE 
				: new BasicStroke((float)aStrokeWidth);
			
			aGraphics.setStroke(theStroke);
			aGraphics.setPaint(aStrokePaint);
			aGraphics.draw(aShape);
		}
	}


}
