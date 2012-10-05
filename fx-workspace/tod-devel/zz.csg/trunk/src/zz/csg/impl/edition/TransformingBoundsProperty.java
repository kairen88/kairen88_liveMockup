package zz.csg.impl.edition;

import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import zz.csg.api.GraphicObjectContext;
import zz.csg.api.IGraphicContainer;
import zz.csg.impl.ViewBoxUtils;
import zz.utils.properties.SimpleRWProperty;


/**
 * This property simulates a bounds property on a graphic object
 * that doesn't have one, by acting on its transform.
 * @author gpothier
 */
public class TransformingBoundsProperty extends SimpleRWProperty<Rectangle2D>
{
	private final Rectangle2D itsOriginalBounds;
	private final AffineTransform itsOriginalTransform;
	private final IGraphicContainer itsGraphicObject;
	
	public TransformingBoundsProperty(
			GraphicObjectContext aContext,
			IGraphicContainer aGraphicObject)
	{
		itsGraphicObject = aGraphicObject;
		itsOriginalBounds = aGraphicObject.getBounds(aContext);
		itsOriginalTransform = aGraphicObject.pTransform().get();
		
		set(itsOriginalBounds);
	}
	
	protected void changed(Rectangle2D aOldValue, Rectangle2D aNewValue)
	{
		double theW = Math.max(aNewValue.getWidth(), 0.1);
		double theH = Math.max(aNewValue.getHeight(), 0.1);
		
		aNewValue.setRect(aNewValue.getX(), aNewValue.getY(), theW, theH);
		
		AffineTransform theTransform = ViewBoxUtils.createViewBoxTransform(
				aNewValue,
				itsOriginalBounds); 
		
		if (itsOriginalTransform != null)
			theTransform.concatenate(itsOriginalTransform);
		
		itsGraphicObject.pTransform().set(theTransform);
	}
}