/*
 * Created on Apr 14, 2004
 */
package net.basekit.shapes;

import java.awt.Color;
import java.awt.Font;

import javax.vecmath.Color3f;
import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3f;

import net.basekit.shapes.utils.ShapeUtils;

import com.xith3d.scenegraph.Appearance;
import com.xith3d.scenegraph.Group;
import com.xith3d.scenegraph.Material;
import com.xith3d.scenegraph.Node;
import com.xith3d.scenegraph.PolygonAttributes;
import com.xith3d.scenegraph.Shape3D;

/**
 * A shape builder permits to build simple or more complex shapes by issuing
 * graphic "primitives" such as {@link #createBox ()} or {@link #pushTransform}.
 * All created nodes are added to a root node or its descendants.
 * <p>
 * This class is not thread safe.
 * @author gpothier
 */
public class SceneGraphBuilder
{
	private final float[] FLOAT_BUFFER = new float[4];

	private Group itsRootNode;
	
	/**
	 * The node in which we currently add the new shapes.
	 */
	private Group itsCurrentGroup;
	
	private Appearance itsCurrentAppearance;
	
	/**
	 * Whether the current appearance has been used. If so, it must be cloned
	 * the next time the client requests an appearance modification.
	 */
	private boolean itsAppearanceUsed;
	
	
	public SceneGraphBuilder (Group aRootNode)
	{
		itsCurrentGroup = itsRootNode = aRootNode;
		itsCurrentAppearance = new Appearance ();
		itsCurrentAppearance.setPolygonAttributes(
				new PolygonAttributes(
						PolygonAttributes.POLYGON_FILL,
						PolygonAttributes.CULL_BACK,
						0));
	}
	
	private void add (Node aNode)
	{
		itsCurrentGroup.addChild(aNode);
	}
	
	public void createBox (float aX, float aY, float aZ, float aW, float aH, float aD)
	{
		createBox (new Vector3f (aX, aY, aZ), new Vector3f (aW, aH, aD));
	}
	
	public void createBox (Tuple3f aPosition, Tuple3f aSize)
	{
		Box theBox = new Box (ShapeUtils.toVector(aPosition), ShapeUtils.toVector(aSize));
		applyAppearanceOn(theBox);
		add (theBox);
	}
	
	/**
	 * Creates a flat rectangle containing text in the xy plane at z = 0.
	 */
	public void createTextFace (Font aFont, String aText, int aX, int aY, int aW, int aH)
	{
		TextFace theTextFace = new TextFace (aText);
		theTextFace.setFont(aFont);
		theTextFace.setPosition(new Vector3f (aX, aY, 0));
		theTextFace.setSize(new Vector3f (aW, aH, 0));
		
		add (theTextFace);
	}
	
	// ****************************************************************************************************
	// Appearance-related
	// ****************************************************************************************************
	
	/**
	 * Applies the current appearance on the specified shape3d, and sets
	 * the {@link #itsAppearanceUsed} flag.
	 */
	private void applyAppearanceOn (Shape3D aNode)
	{
		aNode.setAppearance(itsCurrentAppearance);
		itsAppearanceUsed = true;
	}
	
	/**
	 * If the current appearance has been used, clones it.
	 */
	private void secureAppearance ()
	{
		if (itsAppearanceUsed) itsCurrentAppearance = (Appearance) itsCurrentAppearance.cloneNodeComponent(true);
	}
	
	public void setMaterial (Material aMaterial)
	{
		secureAppearance();
		itsCurrentAppearance.setMaterial(aMaterial);
	}
	
	public void setEmissiveColor (Color aColor)
	{
		aColor.getColorComponents(FLOAT_BUFFER);
		setEmissiveColor(new Color3f (FLOAT_BUFFER));
	}
	
	public void setEmissiveColor (Color3f aColor)
	{
		secureAppearance();
		itsCurrentAppearance.getMaterial().setEmissiveColor(aColor);
	}
}
