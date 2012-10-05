/*
 * Created on 24-sep-2004
 */
package zz.csg.api;

import java.awt.GraphicsConfiguration;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

import zz.csg.api.edition.IGraphicMapper;

/**
 * This interface must be implemented by classes that are able to
 * display graphic objects.
 * It is used by graphic objects when they receive display messages,
 * which they forward to their display(s)
 * @author gpothier
 */
public interface IDisplay extends IGraphicMapper
{
	
	public void repaint(Rectangle2D aBounds);
	
	/**
	 * Returns the graphics configuration used by this display.
	 * It is useful to create images (see {@link GraphicsConfiguration#createCompatibleImage(int, int)}).
	 */
	public GraphicsConfiguration getGraphicsConfiguration();
	
	/**
	 * Displays the specified component on this display, using the given bounds.
	 */
	public void display(JComponent aComponent, Rectangle2D aBounds);
	
	/**
	 * Removes a previously displayed component.
	 */
	public void remove(JComponent aComponent);

}
