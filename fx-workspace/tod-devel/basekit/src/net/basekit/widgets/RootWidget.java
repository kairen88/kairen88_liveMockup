package net.basekit.widgets;

import javax.vecmath.Vector3f;

import net.basekit.layoutdelegates.DefaultLayoutDelegate;
import net.basekit.world.World;

import com.xith3d.scenegraph.Shape3D;

/*
 * Created on Jan 16, 2004
 */
/**
 * This is the primary parent of all widgets. To be displayd,
 * widget needs to have a RootWidget ancestor.
 * @author gpothier
 */
public class RootWidget extends Widget
{
	/**
	 * The time, in milliseconds, after which a redraw should occur.
	 * If it is smaller than {@link System#currentTimeMillis()}, a redraw
	 * should occur as soon as possible.
	 */
	private long itsRedrawTime;	
	
	/**
	 * The world that owns this root widget.
	 */
	private World itsWorld;
	
	public RootWidget (World aWorld)
	{
		itsWorld = aWorld;
		setLayoutDelegate(new DefaultLayoutDelegate ());
		setSize(new Vector3f (1000, 1000, 1000));
		resetRedrawTime();
		
		setRenderBounds(false);
	}

	public World getWorld ()
	{
		return itsWorld;
	}

	/**
	 * Whether this hierarchy needs to be redrawn now.
	 */
	public boolean isRedrawNeeded ()
	{
		return System.currentTimeMillis() >= itsRedrawTime;
	}
	
	/**
	 * Resets the redraw time so that a call to {@link #isRedrawNeeded()} cannot
	 * return true until {@link #redraw(long)} is called.
	 *
	 */
	public void resetRedrawTime ()
	{
		itsRedrawTime = Long.MAX_VALUE;
	}
	
	public void redraw (long aMilliseconds)
	{
		long theRequestedRedrawTime = System.currentTimeMillis() + aMilliseconds;
		itsRedrawTime = Math.min(itsRedrawTime, theRequestedRedrawTime);
	}

}
