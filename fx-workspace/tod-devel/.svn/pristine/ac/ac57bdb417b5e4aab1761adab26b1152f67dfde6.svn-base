/*
 * Created on Apr 20, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package zz.csg.impl.figures;

import zz.csg.api.IRectangularGraphicObject;
import zz.csg.api.figures.IFigure;
import zz.csg.impl.AbstractGraphicObject;
import zz.utils.properties.IRWProperty;


/**
 * Abstract base class for figures.
 * <li>This class provides public getters and setters for all the properties of
 * all the figures. If invoked on a concrete subclass that doesn't have the
 * property, they will fail.
 * <li>This class is based on {@link com.redcrea.ina.core.module.AbstractGenericModule}
 * @author gpothier
 */
public abstract class AbstractFigure extends AbstractGraphicObject 
implements IFigure
{
	
	protected void changed(IRWProperty aProperty)
	{
//		if (! IRectangularGraphicObject.BOUNDS.equals(aProperty.getId())) 
		if (aProperty != pTransform())
		{
			repaintAllContexts();
		}
		
		super.changed(aProperty);
	}
	
}
