/*
 * Created on Apr 23, 2004
 */
package net.basekit.world.toolglass;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Vector3f;

import net.basekit.layoutdelegates.SharpLayoutDelegate;
import net.basekit.widgets.Widget;
import net.basekit.widgets.label.LabelWidget;
import net.basekit.world.World;

/**
 * A toolglass is a widget whose position is relative to the camera
 * and that contains a number of tool pages that can be clicked through.
 * @author gpothier
 */
public class ToolGlass extends Widget
{
	private World itsWorld;
	
	private List itsPages = new ArrayList ();
	private int itsCurrentPage;
	
	public ToolGlass (World aWorld)
	{
		itsWorld = aWorld;
		createUI ();
	}

	private void createUI ()
	{
		setLayoutDelegate(new SharpLayoutDelegate ());
		addWidget(new LabelWidget ("Toolglass"), SharpLayoutDelegate.N);
	}

	/**
	 * Sets the position of the toolglass in the camera plane.
	 */
	public void setPosition (float aX, float aY)
	{
		setPosition(new Vector3f (aX, aY, 0));
		redraw();
	}
	
	public int getCurrentPage ()
	{
		return itsCurrentPage;
	}
	
	public void setCurrentPage (int aCurrentPage)
	{
		itsCurrentPage = aCurrentPage;
	}
}
