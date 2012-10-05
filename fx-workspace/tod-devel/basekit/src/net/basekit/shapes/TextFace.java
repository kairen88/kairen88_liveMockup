package net.basekit.shapes;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

import net.basekit.shapes.utils.ShapeUtils;
import net.basekit.utils.SizeUtils;
import zz.utils.Utils;
import zz.utils.ui.Fonts;

import com.xith3d.scenegraph.Appearance;
import com.xith3d.scenegraph.Material;
import com.xith3d.scenegraph.PolygonAttributes;
import com.xith3d.scenegraph.QuadArray;
import com.xith3d.scenegraph.Shape3D;
import com.xith3d.scenegraph.TextureAttributes;
import com.xith3d.text.FontManager;
import com.xith3d.text.TextManager;

/**
 * This shape is a rectangle with a texture mapped text. 
 * @author gpothier
 */
public class TextFace extends Shape3D implements Shape
{
	public static final Aligner LEADING = new LeadingAligner ();
	public static final Aligner TRAILING = new TrailingAligner ();
	public static final Aligner CENTER = new CenterAligner ();
	
	/**
	 * Define the horizontal alignement of the text.
	 */
	private Aligner itsHorizontalAlign = LEADING; 
	
	/**
	 * Define the vertical alignement of the text.
	 */
	private Aligner itsVerticalAlign = CENTER; 
	
	private Font itsFont;
	private FontMetrics itsFontMetrics;
	private FontManager itsFontManager;  
	
	private String itsText;
	private int itsCurrentLength;
	private Vector2f itsTextSize = new Vector2f (); 
	
	private Vector3f itsSize;
	private Vector3f itsPosition;
	
	/**
	 * The font that was used in the previous update.
	 */
	private Font itsCurrentFont;

	public TextFace()
	{
		this ("");		
	}

	public TextFace(String aText)
	{
		itsText = aText;
		
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
		
		setAppearance(theAppearance);

	}

	public String getText ()
	{
		return itsText;
	}
	
	public void setText (String aText)
	{
		if (Utils.different(itsText, aText)) 
		{
			itsText = aText;
			update();
		}
	}

	public Font getFont ()
	{
		return itsFont;
	}

	public void setFont (Font aFont)
	{
		if (Utils.different(itsFont, aFont)) 
		{
			itsFont = aFont;
			update();
		}
	}
	
	public Vector3f getPosition ()
	{
		return itsPosition;
	}

	public void setPosition (Vector3f aPosition)
	{
		itsPosition = aPosition;
	}
	
	public Vector3f getSize ()
	{
		return itsSize;
	}
	
	public void setSize (Vector3f aSize)
	{
		if (Utils.different(itsSize, aSize))
		{
			itsSize = aSize;
			update ();
		}
	}

	/**
	 * @return The size occupied by the text according to current parameters.
	 * The returned object can be modified.
	 */
	public Vector2f getTextSize ()
	{
		return new Vector2f (itsTextSize);
	}

	
	/**
	 * Updates this widget's geometry, length, etc...
	 * Call this method whenever something changes (text, font, color...).
	 */
	private void update ()
	{
		updateFont();
		updateLength();
		updateSize();
		updateGeometry();
	}

	/**
	 * Checks if the length of the text has changed, and if so
	 * updates internal structures so that they support the new length
	 */
	private void updateLength ()
	{
		int theLength = getText().length()+4; //+4 for the borders.
		if (theLength != itsCurrentLength)
		{
			setGeometry(new QuadArray (theLength*4, 
					QuadArray.COORDINATES 
					| QuadArray.TEXTURE_COORDINATE_2));
			
			theLength = itsCurrentLength;
		}
	}

	/**
	 * Checks if the font changed, and if so updates internal structures.
	 */
	private void updateFont ()
	{
		Font theFont = getFont();
		if (Utils.different(theFont, itsCurrentFont))
		{
			itsFontManager = TextManager.getFontManager(theFont);
			itsFontMetrics = Fonts.getFontMetrics(theFont);

			getAppearance().setTexture(itsFontManager.getTexture());
			
			itsCurrentFont = theFont;
		}
	}

	/**
	 * Recalculates the text size.
	 */
	private void updateSize()
	{
		Dimension theDimension = Fonts.getTextSize(getText(), itsFontMetrics);
		itsTextSize.x = theDimension.width;
		itsTextSize.y = theDimension.height;
	}
	
	private QuadArray getQuadArray ()
	{
		return (QuadArray) getGeometry();
	}
	
	/**
	 * Updates the geometry's coordinates.
	 */
	private void updateGeometry ()
	{
		if (getSize() == null) return;
		
		float[] theHLayers = new float[4];
		float[] theVLayers = new float[4];
		
		itsHorizontalAlign.computeLayers(SizeUtils.X_SELECTOR, this, itsTextSize, theHLayers);
		itsVerticalAlign.computeLayers(SizeUtils.Y_SELECTOR, this, itsTextSize, theVLayers);
		
		FontManager.RenderLocation theLocation = new FontManager.RenderLocation (0, 
				theHLayers[Aligner.INDEX_BOTTOM1],
				theVLayers[Aligner.INDEX_BOTTOM1]);
		
		// Render the characters
		itsFontManager.render(getText(), theLocation, getQuadArray());
		
		// Render the padding around the characters		
		int theVertexIndex = theLocation.getVertexIndex();
		ShapeUtils.renderRectangle(getQuadArray(), theVertexIndex, 
			theHLayers[Aligner.INDEX_TOP1], 
			theVLayers[Aligner.INDEX_TOP1], 
			theHLayers[Aligner.INDEX_BOTTOM1] - theHLayers[Aligner.INDEX_TOP1], 
			theVLayers[Aligner.INDEX_BOTTOM2] - theVLayers[Aligner.INDEX_TOP1], 0);		
		theVertexIndex += 4;
		
		ShapeUtils.renderRectangle(getQuadArray(), theVertexIndex, 
			theHLayers[Aligner.INDEX_TOP2], 
			theVLayers[Aligner.INDEX_TOP1], 
			theHLayers[Aligner.INDEX_BOTTOM2] - theHLayers[Aligner.INDEX_TOP2], 
			theVLayers[Aligner.INDEX_BOTTOM2] - theVLayers[Aligner.INDEX_TOP1], 0);
		theVertexIndex += 4;
		
		ShapeUtils.renderRectangle(getQuadArray(), theVertexIndex, 
			theHLayers[Aligner.INDEX_BOTTOM1], 
			theVLayers[Aligner.INDEX_TOP1], 
			theHLayers[Aligner.INDEX_TOP2] - theHLayers[Aligner.INDEX_BOTTOM1], 
			theVLayers[Aligner.INDEX_BOTTOM1] - theVLayers[Aligner.INDEX_TOP1], 0);
		theVertexIndex += 4;
		
		ShapeUtils.renderRectangle(getQuadArray(), theVertexIndex, 
			theHLayers[Aligner.INDEX_BOTTOM1], 
			theVLayers[Aligner.INDEX_TOP2], 
			theHLayers[Aligner.INDEX_TOP2] - theHLayers[Aligner.INDEX_BOTTOM1], 
			theVLayers[Aligner.INDEX_BOTTOM2] - theVLayers[Aligner.INDEX_TOP2], 0);
		
	}

	
	public Aligner getHorizontalAlign ()
	{
		return itsHorizontalAlign;
	}
	
	public void setHorizontalAlign (Aligner aHorizontalAlign)
	{
		itsHorizontalAlign = aHorizontalAlign;
		updateGeometry();
	}
	
	public Aligner getVerticalAlign ()
	{
		return itsVerticalAlign;
	}
	
	public void setVerticalAlign (Aligner aVerticalAlign)
	{
		itsVerticalAlign = aVerticalAlign;
		updateGeometry();
	}
	
	public void setAlign (Aligner aHorizontal, Aligner aVertical)
	{
		itsHorizontalAlign = aHorizontal;
		itsVerticalAlign = aVertical;
		updateGeometry();
	}
	
	/**
	 * A strategy that handles the positioning of the face's text within the available space.
	 * 
	 * @author gpothier
	 */
	public static abstract class Aligner
	{
		public static final int INDEX_TOP1 = 0;
		public static final int INDEX_BOTTOM1 = 1;
		public static final int INDEX_TOP2 = 2;
		public static final int INDEX_BOTTOM2 = 3;
		
		/**
		 * Computes the height of the layers of the shape's geometry.
		 * For an y axis aligner, layers are:
		 * <li> 0 ({@link #INDEX_TOP1}): top of the first border
		 * <li> 1 ({@link #INDEX_BOTTOM1}): bottom of the first border
		 * <li> 2 ({@link #INDEX_TOP2}): top of the second border
		 * <li> 3 ({@link #INDEX_BOTTOM2}): bottom of the second border
		 * @param aBuffer A float[4] buffer that will contain the result. 
		 */
		public final void computeLayers (int aAxis, TextFace aTextFace, Vector2f aTextSize, float[] aBuffer)
		{
			float theTextFaceSize = SizeUtils.getAxis(aAxis, aTextFace.getSize());
			float theTextSize = SizeUtils.getAxis(aAxis, aTextSize);
			float theTop = SizeUtils.getAxis(aAxis, aTextFace.getPosition());
			
			comp (theTop, theTextFaceSize, theTextSize, aBuffer);
			
		}

		/**
		 * Computes layer coordinates using various sizes projected on this aligner's axis.
		 * @param aWidgetSize The size of the label widget
		 * @param aLowMargin The size of the lower margin (left or top)
		 * @param aHighMargin The size of the higher margin (right or bottom)
		 * @param aTextSize The size of the text
		 * @param aBuffer The buffer that receives the results  
		 */
		protected abstract void comp (float aTop, float aTextFaceSize, float aTextSize, float[] aBuffer);
	}
	
	private static class LeadingAligner extends Aligner
	{
		protected void comp (float aTop, float aTextFaceSize, float aTextSize, float[] aBuffer)
		{
			aBuffer[INDEX_TOP1] = aTop;
			aBuffer[INDEX_BOTTOM1] = aTop;
			aBuffer[INDEX_TOP2] = aTop + aTextSize;
			aBuffer[INDEX_BOTTOM2] = aTop + aTextFaceSize;
		}
	}
	
	private static class TrailingAligner extends Aligner
	{
		protected void comp (float aTop, float aTextFaceSize, float aTextSize, float[] aBuffer)
		{
			aBuffer[INDEX_TOP1] = aTop;
			aBuffer[INDEX_BOTTOM1] = aTop + aTextFaceSize - aTextSize;
			aBuffer[INDEX_TOP2] = aTop + aTextFaceSize;
			aBuffer[INDEX_BOTTOM2] = aTop + aTextFaceSize;
		}
	}
	
	private static class CenterAligner extends Aligner
	{
		protected void comp (float aTop, float aTextFaceSize, float aTextSize, float[] aBuffer)
		{
			float theFreeSpace = aTextFaceSize - aTextSize;
			aBuffer[INDEX_TOP1] = aTop;
			aBuffer[INDEX_BOTTOM1] = aTop + theFreeSpace/2;
			aBuffer[INDEX_TOP2] = aTop + aTextFaceSize - theFreeSpace/2;
			aBuffer[INDEX_BOTTOM2] = aTop + aTextFaceSize;
		}
	}
}
