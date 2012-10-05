/*
 * Created on Jan 13, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.xith3d.text;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.HashMap;
import java.util.Map;

import com.xith3d.scenegraph.Appearance;
import com.xith3d.scenegraph.Material;
import com.xith3d.scenegraph.PolygonAttributes;
import com.xith3d.scenegraph.QuadArray;
import com.xith3d.scenegraph.Shape3D;
import com.xith3d.scenegraph.TextureAttributes;

/**
 * Provides a factory method that creates a {@link com.xith3d.scenegraph.Shape3D}
 * for a text in a given font. See {@link #createShape(Font, String)}.
 * The idea is that geometry & texture coordinate generation is delegated
 * to a {@link com.xith3d.text.FontManager}; there is one such object per awt font.
 * Wrapping that geometry into a shape3d with the proper appearance is performed
 * in this class.
 * <p>
 * Having the FontManager only generate geometry leaves space for improvement in the
 * area of using attributed text instead of plain strings.
 * @author gpothier
 */
public class TextManager
{
	public static final Font DEFAULT_FONT_PLAIN = new Font("SansSerif", Font.PLAIN, 12);
	public static final Font DEFAULT_FONT_BOLD = new Font("SansSerif", Font.BOLD, 12);
	public static final Font DEFAULT_FONT_ITALIC = new Font("SansSerif", Font.ITALIC, 12);

	public static final Font SMALL_FONT_PLAIN = new Font("SansSerif", Font.PLAIN, 9);
	
	
	private static Map itsFontToManagerMap = new HashMap ();
	
	public static final Graphics2D DEFAULT_GRAPHIC = createDefaultGraphics();

	private static Graphics2D createDefaultGraphics ()
	{
		GraphicsDevice theScreenDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		GraphicsConfiguration theConfiguration = theScreenDevice.getDefaultConfiguration();
		return theConfiguration.createCompatibleImage(1, 1).createGraphics();
	}
	
	public static FontManager getFontManager (Font aFont)
	{
		FontManager theManager = (FontManager) itsFontToManagerMap.get (aFont);
		if (theManager == null)
		{
			theManager = new FontManager (aFont);
			itsFontToManagerMap.put (aFont, theManager);
		}
		return theManager;
	}
	
	public static Shape3D createShape (Font aFont, String aText)
	{
		FontManager theFontManager = getFontManager(aFont);
		return createShape(theFontManager, aText);
	}
	
	public static Shape3D createShape (FontManager aFontManager, String aString)
	{
		int theLength = aString.length();
		QuadArray theQuadArray = new QuadArray (theLength*4, 
				QuadArray.COORDINATES 
				| QuadArray.TEXTURE_COORDINATE_2 
		);

		FontManager.RenderLocation theLocation = new FontManager.RenderLocation ();
		aFontManager.render(aString, theLocation, theQuadArray);
		
		Appearance theAppearance = new Appearance ();
		theAppearance.setPolygonAttributes(
				new PolygonAttributes(
						PolygonAttributes.POLYGON_FILL,
						PolygonAttributes.CULL_BACK,
						0));
		
		TextureAttributes theTextureAttributes = new TextureAttributes ();
		theTextureAttributes.setTextureMode(TextureAttributes.BLEND);
		
//		theTextureAttributes.setTextureMode(TextureAttributes.COMBINE);
//		theTextureAttributes.setCombineRgbMode(TextureAttributes.COMBINE_INTERPOLATE);
//		
//		theTextureAttributes.setCombineRgbSource(0, TextureAttributes.COMBINE_CONSTANT_COLOR);
//		theTextureAttributes.setCombineRgbSource(1, TextureAttributes.COMBINE_OBJECT_COLOR);
//		theTextureAttributes.setCombineRgbSource(2, TextureAttributes.COMBINE_TEXTURE_COLOR);
//		
//		theTextureAttributes.setCombineRgbFunction(0, TextureAttributes.COMBINE_SRC_COLOR);
//		theTextureAttributes.setCombineRgbFunction(1, TextureAttributes.COMBINE_SRC_COLOR);
//		theTextureAttributes.setCombineRgbFunction(2, TextureAttributes.COMBINE_SRC_ALPHA);
//		
//		theTextureAttributes.setTextureBlendColor(new Color4f (0f, 0f, 1f, 1f));
		
		theTextureAttributes.setPerspectiveCorrectionMode(TextureAttributes.FASTEST);
		theAppearance.setTextureAttributes(theTextureAttributes);

		Material theMaterial = new Material ();
		theMaterial.setEmissiveColor(1f, 0.5f, 0);
		theAppearance.setMaterial(theMaterial);
		
		theAppearance.setTexture(aFontManager.getTexture());
		Shape3D theShape = new Shape3D (theQuadArray, theAppearance);
		
		return theShape;
	}
	
	
	
}
