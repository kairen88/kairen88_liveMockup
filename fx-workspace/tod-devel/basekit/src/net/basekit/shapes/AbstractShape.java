/*
 * Created on Mar 6, 2004
 */
package net.basekit.shapes;

import javax.vecmath.Vector3f;

import zz.utils.Utils;

import com.xith3d.scenegraph.Shape3D;

/**
 * Abstract implementation of the shape interface.
 * Provides storage, getters and setters of size and position,
 * as well as an update method. 
 * @author gpothier
 */
public abstract class AbstractShape extends Shape3D implements Shape
{
	private Vector3f itsSize;
	private Vector3f itsPosition;
	
	public AbstractShape (Vector3f aSize, Vector3f aPosition)
	{
		itsSize = aSize;
		itsPosition = aPosition;
	}
	
	public AbstractShape ()
	{
		this (new Vector3f (), new Vector3f ());
	}

	public Vector3f getSize ()
	{
		return itsSize;
	}
	
	public void setSize (Vector3f aSize)
	{
		if (! Utils.equalOrBothNull(itsSize, aSize))
		{
			itsSize = aSize;
			updateGeometry ();
		}
	}
		
	public Vector3f getPosition ()
	{
		return itsPosition;
	}

	public void setPosition (Vector3f aPosition)
	{
		itsPosition = aPosition;
		updateGeometry();
	}
	
	/**
	 * Called whenever the size or position change.
	 * SUbclasses can also call it when any other parameter changes that affect the rendering.
	 */
	protected abstract void updateGeometry ();
}
