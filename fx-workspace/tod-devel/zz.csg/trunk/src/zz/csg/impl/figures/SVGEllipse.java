/*
 * Created on Apr 22, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package zz.csg.impl.figures;

import java.awt.Paint;
import java.awt.geom.Ellipse2D;

import zz.csg.api.GraphicObjectContext;
import zz.csg.api.edition.IGraphicObjectController;
import zz.csg.api.figures.IGOEllipse;
import zz.csg.impl.edition.RectangularControllerUtils;
import zz.utils.properties.IRWProperty;


/**
 * @author gpothier
 */
public class SVGEllipse extends AbstractRectangularShape implements IGOEllipse
{
	
	protected void changed(IRWProperty aProperty)
	{
		super.changed(aProperty);
		if (aProperty == pBounds())
		{
			Ellipse2D.Double theEllipse = new Ellipse2D.Double();
			theEllipse.setFrame(pBounds().get());
			setShape(theEllipse);
		}
	}

	protected IGraphicObjectController createController(GraphicObjectContext aContext)
	{
		return RectangularControllerUtils.createRectangularController(aContext, this);
	}

	public static SVGEllipse create(
			double aX, 
			double aY, 
			double aRx, 
			double aRy, 
			Paint aStrokePaint,
			double aStrokeWidth,
			Paint aFillPaint)
	{
		SVGEllipse theEllipse = new SVGEllipse();
		theEllipse.pBounds().set(aX-aRx/2, aY-aRy/2, aRx, aRy);
		theEllipse.pStrokePaint().set(aStrokePaint);
		theEllipse.pStrokeWidth().set(aStrokeWidth);
		theEllipse.pFillPaint().set(aFillPaint);
		
		return theEllipse;
	}
	
	public static SVGEllipse create(
			double aX, 
			double aY, 
			double aR, 
			Paint aFillPaint)
	{
		return create(aX, aY, aR, aR, null, 0, aFillPaint);
	}
}
