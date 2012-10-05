/*
 * Created on Apr 22, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package zz.csg.impl.figures;

import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import zz.csg.api.GraphicObjectContext;
import zz.csg.api.edition.IGraphicObjectController;
import zz.csg.api.figures.IGORectangle;
import zz.csg.impl.edition.RectangularControllerUtils;
import zz.utils.properties.IRWProperty;


/**
 * @author gpothier
 */
public class SVGRectangle extends AbstractRectangularShape implements IGORectangle
{
	
	protected void changed(IRWProperty aProperty)
	{
		super.changed(aProperty);
		if (aProperty == pBounds())
		{
			setShape(pBounds().get());
		}
	}
	
	protected IGraphicObjectController createController(GraphicObjectContext aContext)
	{
		return RectangularControllerUtils.createRectangularController(aContext, this);
	}
	
	public static SVGRectangle create(double aX, double aY, double aW, double aH, Paint aFillPaint)
	{
		return create(aX, aY, aW, aH, aFillPaint, null, 0);
	}
	
	public static SVGRectangle create(double aX, double aY, double aW, double aH, Paint aFillPaint, Paint aStrokePaint, double aStrokeWidth)
	{
		SVGRectangle theRectangle = new SVGRectangle();
		theRectangle.pBounds().set(aX, aY, aW, aH);
		theRectangle.pFillPaint().set(aFillPaint);
		theRectangle.pStrokePaint().set(aStrokePaint);
		theRectangle.pStrokeWidth().set(aStrokeWidth);
		
		return theRectangle;
	}
	
}
