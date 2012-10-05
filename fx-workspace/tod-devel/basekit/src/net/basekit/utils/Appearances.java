/*
 * Created on Mar 22, 2004
 */
package net.basekit.utils;

import java.awt.Color;

import com.xith3d.scenegraph.Appearance;

/**
 * Contains a few standard appearances.
 * @author gpothier
 */
public class Appearances
{
	public static final Appearance RED = AppearanceFactory.getEmissiveAppearance (Color.red);
	public static final Appearance BLUE = AppearanceFactory.getEmissiveAppearance (Color.blue);

}
