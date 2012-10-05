package zz.csg.api;

import java.awt.geom.Rectangle2D;

import zz.csg.api.constraints.IConstrainedRectangle;
import zz.utils.properties.PropertyId;

/**
 * A graphic container with rectangular bounds.
 * It clips its children so that they are entirely contained within its bounds. 
 *
 * @author gpothier
 */
public interface IRectangularGraphicContainer extends IGraphicContainer, IRectangularGraphicObject
{
	public static final PropertyId<Rectangle2D> VIEWBOX = 
		new PropertyId<Rectangle2D> ("viewBox", false);

	/**
	 * Defines the viewbox of this container.
	 * If present, the bounds of this container are mapped to the viewbox,
	 * ie. if a child has bounds equal to this container's viewbox,
	 * the child will occupy all the container's bounds.
	 */
	public IConstrainedRectangle pViewBox ();

	
	/**
	 * Computes the smallest rectangle containing all of this container's children.
	 */
	public Rectangle2D computeBounds (GraphicObjectContext aContext);
	
}
