package net.hddb.ui;
import javax.vecmath.Vector3f;

import net.basekit.*;
import net.basekit.widgets.*;

import com.xith3d.spatial.bounds.Box;

/*
 * Created on Jan 11, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */

/**
 * Stores size preferences for a {@link Widget}.
 * @author gpothier
 */
public class SizeInfo
{
	private Vector3f itsPreferredSize;
	private Vector3f itsMaximumSize;
	private Vector3f itsMinimumSize;
	
	private Vector3f itsCurrentSize;
	
	public SizeInfo(Vector3f aPreferredSize, Vector3f aMaximumSize, Vector3f aMinimumSize)
	{
		itsPreferredSize = aPreferredSize;
		itsMaximumSize = aMaximumSize;
		itsMinimumSize = aMinimumSize;
	}

	public SizeInfo (Vector3f aPreferredSize)
	{
		this (aPreferredSize, null, null);
	}
	
	public Vector3f getMaximumSize()
	{
		return itsMaximumSize;
	}

	public void setMaximumSize(Vector3f aMaximumSize)
	{
		itsMaximumSize = aMaximumSize;
	}

	public Vector3f getMinimumSize()
	{
		return itsMinimumSize;
	}

	public void setMinimumSize(Vector3f aMinimumSize)
	{
		itsMinimumSize = aMinimumSize;
	}

	public Vector3f getPreferredSize()
	{
		return itsPreferredSize;
	}

	public void setPreferredSize(Vector3f aPreferredSize)
	{
		itsPreferredSize = aPreferredSize;
	}

	public Vector3f getCurrentSize()
	{
		return itsCurrentSize;
	}

	public void setCurrentSize(Vector3f aCurrentSize)
	{
		itsCurrentSize = aCurrentSize;
	}

}
