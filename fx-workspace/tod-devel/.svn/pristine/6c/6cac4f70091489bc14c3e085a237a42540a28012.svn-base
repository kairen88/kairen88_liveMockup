/*
 * Created on Dec 28, 2004
 */
package zz.csg.impl.figures.path;

import java.awt.Shape;
import java.awt.geom.GeneralPath;

import zz.csg.api.GraphicObjectContext;
import zz.csg.api.edition.IGraphicObjectController;
import zz.csg.api.figures.IGOPath;
import zz.csg.impl.edition.RectangularControllerUtils;
import zz.csg.impl.figures.AbstractShape;
import zz.utils.properties.IRWProperty;
import zz.utils.properties.SimpleRWProperty;


/**
 * A shape defined by a path.
 * @author gpothier
 */
public class SVGPath extends AbstractShape implements IGOPath
{
	private final IRWProperty<GeneralPath> pPath = createProperty(PATH, new GeneralPath());
	
	public IRWProperty<GeneralPath> pPath()
	{
		return pPath;
	}
	
//	protected IGraphicObjectController createController(GraphicObjectContext aContext)
//	{
//		return RectangularControllerUtils.createTransformingController(this, aContext);
//	}

	protected void changed(IRWProperty aProperty)
	{
		super.changed(aProperty);
		if (PATH.equals(aProperty.getId())) setShape(pPath.get());
	}
}
