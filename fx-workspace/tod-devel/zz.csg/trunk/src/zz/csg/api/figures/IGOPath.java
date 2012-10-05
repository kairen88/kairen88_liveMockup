/*
 * Created on Apr 20, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package zz.csg.api.figures;

import java.awt.geom.GeneralPath;

import zz.utils.properties.IRWProperty;
import zz.utils.properties.PropertyId;

/**
 * Represents an generic path
 * @author gpothier
 */
public interface IGOPath extends IShape
{
	public static final PropertyId<GeneralPath> PATH = 
		new PropertyId<GeneralPath> ("path", false);
	
	/**
	 * Path definition
	 */
	public IRWProperty<GeneralPath> pPath ();
}
