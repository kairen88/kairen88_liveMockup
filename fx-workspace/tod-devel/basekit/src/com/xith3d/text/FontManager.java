/*
 * Created on Jan 13, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.xith3d.text;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.vecmath.Point3f;

import net.basekit.shapes.utils.ShapeUtils;

import zz.utils.Utils;

import com.xith3d.scenegraph.ImageComponent2D;
import com.xith3d.scenegraph.QuadArray;
import com.xith3d.scenegraph.Texture2D;

/**
 * Manages all the textures for a font.
 * @author gpothier
 */
public class FontManager
{
	private static char[] BUFFER = new char[1];
	
	private Font itsFont; 
	private FontMetrics itsFontMetrics;
	private int[] itsWidths;	

	private Texture2D itsTexture;
	
	/**
	 * Contains prepared 2D texture coordinates.
	 * Even indices contain x coordinates, odd indices contain y coordinates.
	 * Four points are defined for each character.
	 * The first character is n° 32.
	 */
	private float[] itsTextureCoordinates = new float[(256-32)*2*4];
	
	private int itsFontHeight;
	private int itsTextureHeight;
	private int itsTextureWidth;
	
	public FontManager(Font aFont)
	{
		itsFont = aFont;
		itsFontMetrics = TextManager.DEFAULT_GRAPHIC.getFontMetrics(itsFont);
		itsWidths = itsFontMetrics.getWidths();
		setup();
	}
	
	
	private int getWidth (char aChar)
	{
		return itsWidths[aChar];
	}
	
	/**
	 * Maintains location information for the {@link #render} method.
	 * @author gpothier
	 */
	public static class RenderLocation
	{
		private int itsVertexIndex;
		private float itsX;
		private float itsY;
		
		public RenderLocation()
		{
			this (0, 0, 0);
		}
		
		public RenderLocation(int aVertexIndex, float aX, float aY)
		{
			super();
			itsVertexIndex = aVertexIndex;
			itsX = aX;
			itsY = aY;
		}

		public int getVertexIndex()
		{
			return itsVertexIndex;
		}

		public float getX()
		{
			return itsX;
		}
		
		public float getY ()
		{
			return itsY;
		}

		void incrementVertexIndex (int aAmount)
		{
			itsVertexIndex += aAmount;
		}
		
		void incrementX (float aAmount)
		{
			itsX += aAmount;
		}
	}
	
	/**
	 * Appends the given array with quads that display the given string. When this method returns,
	 * the given {@link RenderLocation} is updated so that a subsequent call to this
	 * method will continue rendering at the proper locations both in coordinates and vertex index.
	 * @param aString
	 * @param aRenderLocation The location where to render.
	 * @param aQuadArray A quad array to be filled. it must have at least the 
	 * {@link QuadArray#COORDINATES} and {@link QuadArray.TEXTURE_COORDINATE_2}
	 * vertex format. It must also have enough space to store the new vertices (4 vertices
	 * per character).
	 */
	public void render (String aString, RenderLocation aRenderLocation, QuadArray aQuadArray)
	{
		int theLength = aString.length();
		
		Point3f theBuffer = new Point3f (); 
		float theY = aRenderLocation.getY();
		for (int i=0;i<theLength;i++)
		{
			char theChar = aString.charAt(i);
			int theWidth = getWidth(theChar);
			
			int theBaseTexCoordIndex = (theChar - 32) * 4;
			
			float theCurrentX = aRenderLocation.getX();
			int theVertexIndex = aRenderLocation.getVertexIndex();

			// Geometry
			ShapeUtils.renderRectangle(aQuadArray, theVertexIndex, theCurrentX, theY, theWidth, itsFontHeight, 0);
			
			// Texture
			aQuadArray.setTextureCoordinates(0, theVertexIndex, itsTextureCoordinates, theBaseTexCoordIndex, 4);
			
			aRenderLocation.incrementVertexIndex(4);
			aRenderLocation.incrementX(theWidth);
		}
	}
	
	public Texture2D getTexture ()
	{
		return itsTexture;
	}
		
	private void setup ()
	{
		int theMaxAscent = itsFontMetrics.getMaxAscent();
		int theMaxDescent = itsFontMetrics.getMaxDescent();
		itsFontHeight = theMaxAscent + theMaxDescent;
		itsTextureHeight = Utils.getFirstGreaterPow2(itsFontHeight);
		itsTextureWidth = (256-32) * (itsFontMetrics.getMaxAdvance()+2);
		itsTextureWidth = Utils.getFirstGreaterPow2(itsTextureWidth);
		
		itsTextureWidth = 2048;
		
		BufferedImage theImage = new BufferedImage (itsTextureWidth, itsTextureHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D theGraphics = theImage.createGraphics();
		theGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		theGraphics.setFont(itsFont);
		
//		theGraphics.setColor(Color.red);
//		theGraphics.fillRect(0, 0, itsTextureWidth, itsTextureHeight);
		theGraphics.setColor(Color.white);
//		theGraphics.fillRect(10, 10, 10, 10);
		
		int theCurrentX = 1;
		for (char theCurrentChar = (char)32;theCurrentChar < (char)256; theCurrentChar++)
		{
			BUFFER[0] = theCurrentChar;
			theGraphics.drawChars(BUFFER, 0, 1, theCurrentX, theMaxAscent);
			int theW = getWidth(theCurrentChar);
			storeTextureCoordinates(theCurrentChar, theCurrentX, theW);
			
			theCurrentX += theW+2;
		}
		
		ImageComponent2D theImageComponent = new ImageComponent2D (ImageComponent2D.FORMAT_RGBA, theImage, true, false);
		itsTexture = new Texture2D ();
		itsTexture.setImage(0, theImageComponent);
	}
	
	private void storeTextureCoordinates (char aChar, int aCharX, int aCharW)
	{
		int theBaseIndex = (aChar-32)*2*4;
		float theH = 1f * itsFontHeight / itsTextureHeight;
		itsTextureCoordinates[theBaseIndex] = 1f*aCharX/itsTextureWidth;
		itsTextureCoordinates[theBaseIndex+1] = 0;
		
		itsTextureCoordinates[theBaseIndex+2] = 1f*aCharX/itsTextureWidth;
		itsTextureCoordinates[theBaseIndex+3] = theH;
		
		itsTextureCoordinates[theBaseIndex+4] = 1f*(aCharX+aCharW)/itsTextureWidth;
		itsTextureCoordinates[theBaseIndex+5] = theH;
		
		itsTextureCoordinates[theBaseIndex+6] = 1f*(aCharX+aCharW)/itsTextureWidth;
		itsTextureCoordinates[theBaseIndex+7] = 0;
	}
	
}
