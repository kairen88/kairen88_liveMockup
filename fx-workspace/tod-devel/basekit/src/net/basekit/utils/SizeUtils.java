/*
 * Created on Mar 18, 2004
 */
package net.basekit.utils;

import javax.vecmath.Tuple2f;
import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3f;

import net.basekit.widgets.Margins;
import net.basekit.widgets.Widget;

/**
 *
 * A few utility methods that help with size manipulations. 
 * @author gpothier
 */
public class SizeUtils
{
	/**
	 * Size selectors.
	 */
	public static final int MINIMUM_SELECTOR = 0;
	public static final int PREFERRED_SELECTOR = 1;
	public static final int MAXIMUM_SELECTOR = 2;
	
	/**
	 * Margin selector
	 */
	public static final int MARGIN_LOW = 0;
	public static final int MARGIN_HIGH = 1;
	public static final int MARGIN_TOTAL = 2;
	
	/**
	 * Axis selectors
	 */
	public static final int X_SELECTOR = 0;
	public static final int Y_SELECTOR = 1;
	public static final int Z_SELECTOR = 2;
	
	/**
	 * Returns the minimum or maximum of two values, according to a flag.
	 * @param aUseMin Whether to return the minimum or the maximum
	 */
	public static float applyMinOrMax (boolean aUseMin, float aValue1, float aValue2)
	{
		return aUseMin ? Math.min (aValue1, aValue2) : Math.max (aValue1, aValue2);
	}

	/**
	 * Determines one size component of one of the sizes of a widget
	 * @param aSizeSelector The selector that indicates which size is required.
	 * @param aAxisSelector The axis selector
	 */
	public static float getSize (int aSizeSelector, int aAxisSelector, Widget aWidget)
	{
		Vector3f theSize = getSize(aSizeSelector, aWidget);
		return getAxis(aAxisSelector, theSize);
	}
	
	/**
	 * Returns the value of one of the axis of the tuple.
	 */
	public static float getAxis (int aAxisSelector, Tuple3f aTuple)
	{
		switch (aAxisSelector)
		{
			case X_SELECTOR: return aTuple.x;
			case Y_SELECTOR: return aTuple.y;
			case Z_SELECTOR: return aTuple.z;
			default: assert false;return Float.NaN;
		}
	}

	/**
	 * Returns the value of one of the axis of the tuple.
	 */
	public static float getAxis (int aAxisSelector, Tuple2f aTuple)
	{
		switch (aAxisSelector)
		{
			case X_SELECTOR: return aTuple.x;
			case Y_SELECTOR: return aTuple.y;
			default: assert false;return Float.NaN;
		}
	}


	/**
	 * Returns the value of one of the axis of the margin.
	 */
	public static float getAxis (int aAxisSelector, int aMarginSelector, Margins aMargins)
	{
		switch (aMarginSelector)
		{
			case MARGIN_LOW:
				switch (aAxisSelector)
				{
					case X_SELECTOR: return aMargins.getLeft();
					case Y_SELECTOR: return aMargins.getTop();
					case Z_SELECTOR: return aMargins.getFront();
					default: assert false;return Float.NaN;
				}

			case MARGIN_HIGH:
				switch (aAxisSelector)
				{
					case X_SELECTOR: return aMargins.getRight();
					case Y_SELECTOR: return aMargins.getBottom();
					case Z_SELECTOR: return aMargins.getBack();
					default: assert false;return Float.NaN;
				}

			case MARGIN_TOTAL:
				switch (aAxisSelector)
				{
					case X_SELECTOR: return aMargins.getHorizontal();
					case Y_SELECTOR: return aMargins.getVertical();
					case Z_SELECTOR: return aMargins.getDeep();
					default: assert false;return Float.NaN;
				}
			default:assert false;return Float.NaN;
		}
	}

	/**
	 * Determines one of the sizes of a widget.
	 * @param aSizeSelector The selector that indicates which size is required.
	 */
	public static Vector3f getSize (int aSizeSelector, Widget aWidget)
	{
		switch (aSizeSelector)
		{
			case MINIMUM_SELECTOR: return aWidget.getMinimumSize();
			case PREFERRED_SELECTOR: return aWidget.getPreferredSize();
			case MAXIMUM_SELECTOR: return aWidget.getMaximumSize();
			default: assert false;return null;
		}
	}
	

}
