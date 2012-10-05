/*
 * Created on 27-sep-2004
 */
package zz.csg.impl;

import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

import zz.csg.api.GraphicObjectContext;
import zz.csg.api.IDisplay;


/**
 * This is a wrapper that permits to include Swing components
 * in a G.O. hierarchy.
 * It doesn't support multiple instantiation through contexts.
 * @author gpothier
 */
public class SwingGraphicObject extends AbstractRectangularGraphicObject
{
	private JComponent itsComponent;

	public SwingGraphicObject(JComponent aComponent, Rectangle2D aBounds)
	{
		pBounds().set(aBounds);
		itsComponent = aComponent;
	}

	public Rectangle2D getBounds(GraphicObjectContext aContext)
	{
		return pBounds().get();
	}
	
	@Override
	protected void paintTransformed(IDisplay aDisplay, GraphicObjectContext aContext, Graphics2D aGraphics, Area aVisibleArea)
	{
	}

	/**
	 * When we get attached, we display the editor if there is one.
	 */
	public void attached(GraphicObjectContext aContext)
	{
		super.attached(aContext);
		if (itsComponent != null) display(aContext, itsComponent, getBounds(aContext));
	}
	
	/**
	 * Hides the editor when we get unattached.
	 */
	public void detached(GraphicObjectContext aContext)
	{
		super.detached(aContext);
		if (itsComponent != null) 
		{
			remove(aContext, itsComponent);
		}
	}



}
