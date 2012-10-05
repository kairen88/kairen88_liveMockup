/*
 * Created on Mar 6, 2004
 */
package net.basekit.utils;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import javax.vecmath.Color3f;

import com.xith3d.scenegraph.Appearance;
import com.xith3d.scenegraph.Material;
import com.xith3d.scenegraph.PolygonAttributes;

/**
 * Factory for standard appearances.
 * Identical appearances are shared.
 * @author gpothier
 */
public class AppearanceFactory
{
	private static final float[] FLOAT_BUFFER = new float[4];
	private static Map itsEmissiveAppearanceMap = new HashMap ();


	/**
	 * Returns an appearance with a material with an emissive color.
	 */
	public static Appearance getEmissiveAppearance (Color aColor)
	{
		aColor.getColorComponents(FLOAT_BUFFER);
		return getEmissiveAppearance(new Color3f (FLOAT_BUFFER));
	}
	
	/**
	 * Returns an appearance with a material with an emissive color.
	 */
	public static Appearance getEmissiveAppearance (Color3f aColor)
	{
		Appearance theAppearance = (Appearance) itsEmissiveAppearanceMap.get (aColor);
		if (theAppearance == null)
		{
			theAppearance = createEmissiveAppearance(aColor);
			itsEmissiveAppearanceMap.put (aColor, theAppearance);
		}
		return theAppearance;
	}
	
	private static Appearance createEmissiveAppearance (Color3f aColor)
	{
		Appearance theAppearance = new Appearance ();
		theAppearance.setPolygonAttributes(
				new PolygonAttributes(
						PolygonAttributes.POLYGON_FILL,
						PolygonAttributes.CULL_NONE,	//TODO: should be CULL_BACK in production use.
						0));
		
		Material theMaterial = new Material ();
		theMaterial.setEmissiveColor(aColor);
		theAppearance.setMaterial(theMaterial);
		
		return theAppearance;
	}
}
