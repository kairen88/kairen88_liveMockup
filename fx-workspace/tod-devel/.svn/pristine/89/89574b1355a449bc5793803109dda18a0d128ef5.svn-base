/*
 * Created on Mar 5, 2004
 */
package net.basekit.utils;
import javax.vecmath.Matrix4f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import com.xith3d.scenegraph.Canvas3D;
import com.xith3d.scenegraph.Node;
import com.xith3d.scenegraph.Transform3D;
import com.xith3d.scenegraph.View;
/**
 * Implementation of gluUnproject taken from a Xith forum thread.
 * 
 * @author gpothier
 */
public class GLMath
{
	private static final Point3f POINT_BUFFER = new Point3f ();
	private static final Vector3f VECTOR_BUFFER = new Vector3f ();
	private static final Transform3D TRANSFORM_BUFFER = new Transform3D ();

	/**
	 * Multiplies the 4x4 matrix 'M' by the column vector 'in.' This is a
	 * helper function for the xithUnProject method
	 * 
	 * @param M
	 * @param in
	 * @return out a column vector with 4 elements
	 */
	private static float[] transform_point (Matrix4f M, float[] in)
	{
		float[] out = new float[4];
		out[0] = M.getElement(0, 0) * in[0] + M.getElement(0, 1) * in[1]
				+ M.getElement(0, 2) * in[2] + M.getElement(0, 3) * in[3];
		out[1] = M.getElement(1, 0) * in[0] + M.getElement(1, 1) * in[1]
				+ M.getElement(1, 2) * in[2] + M.getElement(1, 3) * in[3];
		out[2] = M.getElement(2, 0) * in[0] + M.getElement(2, 1) * in[1]
				+ M.getElement(2, 2) * in[2] + M.getElement(2, 3) * in[3];
		out[3] = M.getElement(3, 0) * in[0] + M.getElement(3, 1) * in[1]
				+ M.getElement(3, 2) * in[2] + M.getElement(3, 3) * in[3];
		return out;
	}

	/**
	 * Converts the given screen coordinates into object coordiantes based on
	 * the given projection and modelview matrix.
	 * 
	 * @param winX
	 *            screen coordinate x
	 * @param winY
	 *            screen coordinate y
	 * @param winZ
	 *            screen coordinate z
	 * @param model
	 *            modelview matrix
	 * @param projection
	 *            projection matrix
	 * @param viewPort
	 *            viewport
	 * @param results
	 *            puts the final object coordinates into the results array
	 *            where results[0] = x, results[1] = y results[2] = z
	 * @return boolean stating whether the unprojection was succesful or not
	 */
	public static boolean xithUnProject (float winX, float winY, float winZ,
			Matrix4f model, Matrix4f projection, int[] viewPort, float[] results)
	{
		float in[] = new float[4];
		float out[] = new float[4];
		Matrix4f A = new Matrix4f();
		in[0] = (winX - (float) viewPort[0]) * 2.0f / (float) viewPort[2]
				- 1.0f;
		in[1] = (winY - (float) viewPort[1]) * 2.0f / (float) viewPort[3]
				- 1.0f;
		in[2] = 2.0f * winZ - 1.0f;
		in[3] = 1.0f;
		A.mul(projection, model);
		A.invert();
		out = transform_point(A, in);
		if (out[3] == 0)
			return false;
		results[0] = out[0] / out[3];
		results[1] = out[1] / out[3];
		results[2] = out[2] / out[3];
		return true;
	}

	/**
	 * Utility method for picking. Given a coordinate in pixels in the canvas, calculates a ray
	 * in the virtual world that permits to calculate intersection with nodes.
	 * A possible scenario is to invoke {@link View#pick(Canvas3D, int, int, int, int)} and retrieve
	 * the nodes that are actually intersected by the ray, and then use this method to
	 * create an analitic ray that can be used to calculate the position of the intersection in
	 * the virtual world. 
	 * @param aX X coordinate within the canvas
	 * @param aY Y coordinate within the canvas
	 * @param aCanvas The canvas that displays the content that is being picked.
	 * @param aOriginSlot Will recieve the origin of the ray.
	 * @param aDirectionSlot Will receive the direction of the ray.
	 */
	public static void createRay (int aX, int aY, Canvas3D aCanvas,
			Point3f aOriginSlot, Vector3f aDirectionSlot)
	{
		int theVW = aCanvas.getWidth();
		int theVH = aCanvas.getHeight();
		int[] theViewport = {0, theVH, theVW, -theVH};
		float[] theBuffer = new float[3];
		View theView = aCanvas.getView();
		Matrix4f theProjection = theView.getProjection().getMatrix4f();
		Matrix4f theModel = theView.getTransform().getMatrix4f();
		GLMath.xithUnProject(aX, aY, theView.getFrontClipDistance(), theModel,
				theProjection, theViewport, theBuffer);
		aDirectionSlot.set(theBuffer);
		GLMath.xithUnProject(aX, aY, theView.getBackClipDistance(), theModel,
				theProjection, theViewport, theBuffer);
		aOriginSlot.set(theBuffer);
		aDirectionSlot.sub(aOriginSlot);
		theView.getTransform().getTranslation(VECTOR_BUFFER);
		aOriginSlot.set(VECTOR_BUFFER);
	}

	/**
	 * Transforms a ray in VWorld coordinates into the coordinate system of a given node. 
	 * @param aOrigin Origin of the ray in the virtusl world
	 * @param aDirection Direction of the ray in the virtusl world
	 * @param aNode The node in which coordinate system we want to tranform the ray
	 * @param aOriginSlot Origin of the tranformed ray
	 * @param aDirectionSlot Direction of the transformed ray.
	 */
	public static void tranformVWorldToLocal (Point3f aOrigin, Vector3f aDirection, Node aNode,
		Point3f aOriginSlot, Vector3f aDirectionSlot)
	{
		aNode.getLocalToVworld(TRANSFORM_BUFFER);
		TRANSFORM_BUFFER.invert();
		
		TRANSFORM_BUFFER.transform(aOrigin, aOriginSlot);
		aDirectionSlot.set (aDirection);
		TRANSFORM_BUFFER.transform(aDirectionSlot);		
	}
	
}


