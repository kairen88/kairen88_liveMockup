package net.basekit.layoutdelegates;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import net.basekit.utils.SizeUtils;
import net.basekit.widgets.Widget;

/**
 * Lays out nodes along an axis.
 * It is possible to specify alignment (left, right, centered, justified)
 * @author gpothier
 */
public class AxisLayoutDelegate extends LineLayoutDelegate
{
	public static final Aligner LEFT = new LeftAligner ();
	public static final Aligner RIGHT = new RightAligner ();
	public static final Aligner CENTER = new CenterAligner () ;
	public static final Aligner JUSTIFIED = new JustifiedAligner () ;

	public static final Direction X_POSITIVE = new Direction (1, 0, 0, 0, 1, 0);
	public static final Direction X_NEGATIVE = new Direction (-1, 0, 0, 0, -1, 0);
	public static final Direction Y_POSITIVE = new Direction (0, 1, 0, 1, 0, 0);
	public static final Direction Y_NEGATIVE = new Direction (0, -1, 0, -1, 0, 0);
	public static final Direction Z_POSITIVE = new Direction (0, 0, 1, 0, 0, 0);
	public static final Direction Z_NEGATIVE = new Direction (0, 0, -1, 0, 0, 0);
		
	private Direction itsDirection;
	private Aligner itsAligner;

	public AxisLayoutDelegate(Direction aDirection)
	{
		this (aDirection, LEFT);
	}

	public AxisLayoutDelegate (Direction aDirection, Aligner aAligner)
	{
		assert aDirection != null;
		assert aAligner != null;
		itsDirection = aDirection;
		itsAligner = aAligner;
	}

	public AxisLayoutDelegate(Direction aDirection, float aGap)
	{
		super(aGap);
		itsDirection = aDirection;
	}
	
	protected void advanceLayout(Point3f aPosition, Widget aChildWidget, int aChildIndex, Vector3f aChildSize)
	{
		itsDirection.advanceLayout(aPosition, aChildSize, getGap());
	}

	protected void growSize (Vector3f aSize, Widget aChild, int aChildIndex, Vector3f aChildSize)
	{
		itsDirection.growSize (aSize, aChildSize, getGap());
	}

	protected void place (Widget aChildWidget, int aChildIndex, Point3f aPosition)
	{
		Vector3f theAvailableSize = getWidget ().getSize ();
		Vector3f theAlignAxis = itsDirection.getAlignAxis ();

		float theAx = theAvailableSize.x * theAlignAxis.x;
		float theAy = theAvailableSize.y * theAlignAxis.y;
		float theAz = theAvailableSize.z * theAlignAxis.z;

		itsAligner.place (aChildWidget, aPosition, theAx, theAy, theAz);
	}

	/**
	 * Represents a layout direction
	 */
	private static class Direction
	{
		/**
		 * THe support vector of the layout. It must be axis aligned.
		 */
		private Vector3f itsVector;
		
		/**
		 * The vector that support widget alignment. It must be orthogonal to the 
		 * direction vector.
		 */
		private Vector3f itsAlignAxis;

		public Direction(float aX, float aY, float aZ, float aAx, float aAy, float aAz)
		{
			itsVector = new Vector3f (aX, aY, aZ);
			itsAlignAxis = new Vector3f (aAx, aAy, aAz);
		}

		public void advanceLayout (Point3f aPosition, Vector3f aSize, float aGap)
		{
			float theScale = itsVector.dot(aSize);
			aPosition.scaleAdd(theScale, itsVector, aPosition);
			if (itsVector.x != 0) aPosition.x += aGap;
			if (itsVector.y != 0) aPosition.y += aGap;
			if (itsVector.z != 0) aPosition.z += aGap;
		}
		
		public void growSize (Vector3f aSize, Vector3f aChildSize, float aGap)
		{
			aSize.x = grow (SizeUtils.X_SELECTOR, aSize, aChildSize, aGap);
			aSize.y = grow (SizeUtils.Y_SELECTOR, aSize, aChildSize, aGap);
			aSize.z = grow (SizeUtils.Z_SELECTOR, aSize, aChildSize, aGap);
		}
		
		private float grow (int aAxis, Vector3f aSize, Vector3f aChildSize, float aGap)
		{
			float theDirection = SizeUtils.getAxis(aAxis, itsVector);
			float theSize = SizeUtils.getAxis(aAxis, aSize);
			float theWidgetSize = SizeUtils.getAxis(aAxis, aChildSize);
			
			return theDirection == 0
				? Math.max (theSize, theWidgetSize)
				: theSize + theWidgetSize + aGap;
		}

		public Vector3f getAlignAxis ()
		{
			return itsAlignAxis;
		}
	}

	/**
	 * Abstracts children alignment strategies.
	 */
	private abstract static class Aligner
	{
		protected static final Vector3f VECTOR_BUFFER = new Vector3f ();
		protected static final Point3f POINT_BUFFER = new Point3f ();
		public abstract void place (Widget aWidget, Point3f aBasePosition, float aAx, float aAy, float aAz);
	}

	private static class LeftAligner extends Aligner
	{
		public void place (Widget aWidget, Point3f aBasePosition, float aAx, float aAy, float aAz)
		{
			Vector3f thePreferredSize = aWidget.getPreferredSize ();

			aWidget.setPosition (aBasePosition);
			aWidget.setSize (thePreferredSize);
		}
	}

	private static class RightAligner extends Aligner
	{
		public void place (Widget aWidget, Point3f aBasePosition, float aAx, float aAy, float aAz)
		{
			Vector3f thePreferredSize = aWidget.getPreferredSize ();

			POINT_BUFFER.x = aBasePosition.x + (aAx == 0 ? 0 : aAx - thePreferredSize.x);
			POINT_BUFFER.y = aBasePosition.y + (aAy == 0 ? 0 : aAy - thePreferredSize.y);
			POINT_BUFFER.z = aBasePosition.z + (aAz == 0 ? 0 : aAz - thePreferredSize.z);

			aWidget.setPosition (POINT_BUFFER);
			aWidget.setSize (thePreferredSize);
		}
	}

	private static class CenterAligner extends Aligner
	{
		public void place (Widget aWidget, Point3f aBasePosition, float aAx, float aAy, float aAz)
		{
			Vector3f thePreferredSize = aWidget.getPreferredSize ();

			POINT_BUFFER.x = aBasePosition.x + (aAx == 0 ? 0 : (aAx - thePreferredSize.x)/2);
			POINT_BUFFER.y = aBasePosition.y + (aAy == 0 ? 0 : (aAy - thePreferredSize.y)/2);
			POINT_BUFFER.z = aBasePosition.z + (aAz == 0 ? 0 : (aAz - thePreferredSize.z)/2);

			aWidget.setPosition (POINT_BUFFER);
			aWidget.setSize (thePreferredSize);
		}
	}

	private static class JustifiedAligner extends Aligner
	{
		public void place (Widget aWidget, Point3f aBasePosition, float aAx, float aAy, float aAz)
		{
			Vector3f thePreferredSize = aWidget.getPreferredSize ();

			VECTOR_BUFFER.x = aAx == 0 ? thePreferredSize.x : aAx;
			VECTOR_BUFFER.y = aAy == 0 ? thePreferredSize.y : aAy;
			VECTOR_BUFFER.z = aAz == 0 ? thePreferredSize.z : aAz;

			aWidget.setPosition (aBasePosition);
			aWidget.setSize (VECTOR_BUFFER);
		}
	}
}
