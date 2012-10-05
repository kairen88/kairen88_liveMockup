/*
 * Created on 31-may-2004
 */
package zz.csg.impl.figures;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import zz.csg.api.GraphicObjectContext;
import zz.csg.api.IDisplay;
import zz.csg.api.figures.IShape;
import zz.utils.properties.IRWProperty;


/**
 * Base class for figures that are based on a single shape. This class
 * implements and declares final the {@link #paint(IGraphicObjectContext, Graphics2D, Area)}method, and
 * declares the abstract {@link #getShape()}method.
 * 
 * @author gpothier
 */
public abstract class AbstractShape extends AbstractFigure implements IShape
{
	private static final Stroke OUTLINE_INTERSECTION_STROKE = new BasicStroke(3f);

	private final IRWProperty<Paint> pStrokePaint = createProperty(STROKE_PAINT);
	private final IRWProperty<Paint> pFillPaint = createProperty(FILL_PAINT);
	private final IRWProperty<Double> pStrokeWidth = createProperty(STROKE_WIDTH);
	
	private Shape itsShape;
	private Shape itsShapeOutline;
	private Rectangle2D itsBounds;

	public IRWProperty<Paint> pFillPaint()
	{
		return pFillPaint;
	}

	public IRWProperty<Paint> pStrokePaint()
	{
		return pStrokePaint;
	}

	public IRWProperty<Double> pStrokeWidth()
	{
		return pStrokeWidth;
	}

	protected void changed(IRWProperty aProperty)
	{
		super.changed(aProperty);
		if (aProperty == pTransform()) updateShape();
	}
	
	/**
	 * Sets the shape of this object.
	 */
	protected void setShape (Shape aShape)
	{
		itsShape = aShape;
		updateShape();
	}

	/**
	 * Updates cached data relative to this object's shape.
	 */
	private void updateShape()
	{
		AffineTransform theTransform = pTransform().get();

		if (itsShape != null)
		{
			itsShapeOutline = OUTLINE_INTERSECTION_STROKE.createStrokedShape(itsShape);
			itsBounds = itsShape.getBounds2D();
		}
		else
		{
			itsShapeOutline = null;
			itsBounds = null;
		}
	}

	public boolean isInside(GraphicObjectContext aContext, Point2D aPoint)
	{
		boolean theTestInside = pFillPaint().get() != null;
		boolean theTestOutline = pStrokePaint().get() != null;

		if (theTestInside)
		{
			if (itsShape.contains(aPoint)) return true;
		}
		
		if (theTestOutline)
		{
			if ((itsShapeOutline != null && itsShapeOutline.contains(aPoint))) 
				return true;
		}
		
		return false;
	}

	@Override
	protected void paintTransformed(IDisplay aDisplay, GraphicObjectContext aContext, Graphics2D aGraphics, Area aVisibleArea)
	{
		checkValid();
		FigureUtils.paintShape(this, aGraphics, aContext, itsShape);
	}

	public Rectangle2D getBounds(GraphicObjectContext aContext)
	{
		checkValid();
		return itsBounds;
	}

}
