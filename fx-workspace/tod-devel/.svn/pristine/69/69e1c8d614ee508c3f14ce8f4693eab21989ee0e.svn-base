/*
 * Created on 07-may-2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package zz.csg.api.figures;

import java.awt.Paint;


import zz.csg.api.GraphicObjectContext;
import zz.utils.properties.PropertyId;
import zz.utils.properties.IRWProperty;

/**
 * A hape is a graphic object that has:
 * <li>A primary and secondary fill color
 * <li>A primary and secondary stroke color
 * <li>A stroke width.
 *
 * @author gpothier
 */
public interface IShape extends IFigure
{
	public static final PropertyId<Paint> FILL_PAINT = 
		new PropertyId<Paint> ("fillPaint", false);
	
	public static final PropertyId<Paint> STROKE_PAINT = 
		new PropertyId<Paint> ("strokePaint", false);
	
	public static final PropertyId<Double> STROKE_WIDTH = 
		new PropertyId<Double> ("strokeWidth", false);
	
	/**
	 * Primary fill color of this object
	 */
	public IRWProperty<Paint> pFillPaint ();

	/**
	 * Primary stroke color of this object
	 */
	public IRWProperty<Paint> pStrokePaint ();

	/**
	 * Stroke width of this object.
	 */
	public IRWProperty<Double> pStrokeWidth ();
	
}
