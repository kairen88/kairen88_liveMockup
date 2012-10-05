/*
 * Created on Jan 13, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */


package net.hddb.ui;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.vecmath.Color4f;
import javax.vecmath.Vector3f;

import com.xith3d.render.CanvasPeer;
import com.xith3d.render.RenderPeer;
import com.xith3d.render.jogl.RenderPeerImpl;
import com.xith3d.scenegraph.Appearance;
import com.xith3d.scenegraph.BranchGroup;
import com.xith3d.scenegraph.Canvas3D;
import com.xith3d.scenegraph.ImageComponent2D;
import com.xith3d.scenegraph.Locale;
import com.xith3d.scenegraph.Material;
import com.xith3d.scenegraph.Node;
import com.xith3d.scenegraph.PolygonAttributes;
import com.xith3d.scenegraph.QuadArray;
import com.xith3d.scenegraph.Shape3D;
import com.xith3d.scenegraph.Texture2D;
import com.xith3d.scenegraph.TextureAttributes;
import com.xith3d.scenegraph.Transform3D;
import com.xith3d.scenegraph.TransformGroup;
import com.xith3d.scenegraph.View;
import com.xith3d.scenegraph.VirtualUniverse;

public class CombineTest
{
	public static void main (String[] aArgs)
	{
		// create the virtual univers
		VirtualUniverse universe = new VirtualUniverse();

		// add a view to the universe
		View view = new View();
		universe.addView(view);

		// add a locale
		Locale locale = new Locale(5.0f, 0.0f, 10.0f);
		universe.addLocale(locale);

		// create a BranchGroup
		BranchGroup scene = new BranchGroup();
		locale.addBranchGraph(scene);

		// let objects along this path rotate
		Transform3D theTransform = new Transform3D();
		theTransform.rotXYZ((float)Math.PI, 0, 0);
		theTransform.setScale(1f);
		TransformGroup objRotate = new TransformGroup(theTransform);
		scene.addChild(objRotate);

		Node theScene = createShape();
		objRotate.addChild(theScene);

		// turn the scene into a render friendly format
		//scene.compile();

		// create a canvas for our graphics
		RenderPeer rp = new RenderPeerImpl();
		CanvasPeer cp = rp.makeCanvas(null, 640, 480, 32, false);
		Canvas3D canvas = new Canvas3D();
		canvas.set3DPeer(cp);

		// modify our view so we can see the cube
		view.addCanvas3D(canvas);
		view.setFrontClipDistance(0.5f);
		view.setBackClipDistance(2000f);
		Transform3D t = new Transform3D();
		t.lookAt(new Vector3f(0, 0,    100f),    // location of eye
				new Vector3f( 0, 0, 0),    // center of view
				new Vector3f( 0, 1, 1));    // vector pointing up

		view.setTransform(t);

		long time=System.currentTimeMillis();
		while (System.currentTimeMillis()-time<5000)
			view.renderOnce();
		System.out.println("done.");
		
	}

	private static Node createShape()
	{
		QuadArray theQuadArray = new QuadArray (4, 
				QuadArray.COORDINATES 
				| QuadArray.TEXTURE_COORDINATE_2 
		);

		theQuadArray.setCoordinate(0, 0, 0);
		theQuadArray.setCoordinate(0, 32, 0);
		theQuadArray.setCoordinate(32, 32, 0);
		theQuadArray.setCoordinate(32, 0, 0);
		
		theQuadArray.setTexCoord(0, 0f, 0f);
		theQuadArray.setTexCoord(0, 0f, 1f);
		theQuadArray.setTexCoord(0, 1f, 1f);
		theQuadArray.setTexCoord(0, 1f, 0f);
		
		Appearance theAppearance = new Appearance ();
		theAppearance.setPolygonAttributes(
				new PolygonAttributes(
						PolygonAttributes.POLYGON_FILL,
						PolygonAttributes.CULL_BACK,
						0));
		
		TextureAttributes theTextureAttributes = new TextureAttributes ();
		
		// Comment this block to avoid the exception.
		theTextureAttributes.setTextureMode(TextureAttributes.COMBINE);
		theTextureAttributes.setCombineRgbMode(TextureAttributes.COMBINE_INTERPOLATE);
		theTextureAttributes.setCombineRgbSource(0, TextureAttributes.COMBINE_CONSTANT_COLOR);
		theTextureAttributes.setCombineRgbSource(1, TextureAttributes.COMBINE_OBJECT_COLOR);
		theTextureAttributes.setCombineRgbSource(2, TextureAttributes.COMBINE_TEXTURE_COLOR);
		theTextureAttributes.setCombineRgbFunction(0, TextureAttributes.COMBINE_SRC_COLOR);
		theTextureAttributes.setCombineRgbFunction(1, TextureAttributes.COMBINE_SRC_COLOR);
		theTextureAttributes.setCombineRgbFunction(2, TextureAttributes.COMBINE_SRC_ALPHA);
		theTextureAttributes.setTextureBlendColor(new Color4f (0f, 0f, 1f, 1f));
		
		theTextureAttributes.setPerspectiveCorrectionMode(TextureAttributes.FASTEST);
		theAppearance.setTextureAttributes(theTextureAttributes);

		Material theMaterial = new Material ();
		theMaterial.setEmissiveColor(1f, 0.5f, 0);
		theAppearance.setMaterial(theMaterial);
		
		theAppearance.setTexture(getTexture());
		Shape3D theShape = new Shape3D (theQuadArray, theAppearance);
		
		return theShape;
		
	}
	
	
	private static Texture2D getTexture ()
	{
		BufferedImage theImage = new BufferedImage (32, 32, BufferedImage.TYPE_INT_ARGB);
		Graphics2D theGraphics = theImage.createGraphics();
		FontMetrics theMetrics = theGraphics.getFontMetrics();
		theGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		theGraphics.setColor(Color.white);
		
		theGraphics.drawString("A", 0, theMetrics.getAscent());
		
		ImageComponent2D theImageComponent = new ImageComponent2D (ImageComponent2D.FORMAT_RGBA, theImage, true, false);
		Texture2D theTexture = new Texture2D ();
		theTexture.setImage(0, theImageComponent);
		
		return theTexture;
	}
	
	
}
