/*
 * Created on Mar 6, 2004
 */
package net.basekit.widgets;

/**
 * Defines the margin of a {@link net.basekit.widgets.Widget}. During the layout process,
 * the widget will leave space around its children as indicated by this object.
 * @author gpothier
 */
public class Margins
{
	private float itsLeft;
	private float itsRight;
	private float itsTop;
	private float itsBottom;
	private float itsBack;
	private float itsFront;
	
	/**
	 * Initialize empty margins.
	 */
	public Margins ()
	{
	}
	
	public Margins (float aLeft, float aRight, float aTop, float aBottom,
			float aBack, float aFront) 
	{
		itsLeft = aLeft;
		itsRight = aRight;
		itsTop = aTop;
		itsBottom = aBottom;
		itsBack = aBack;
		itsFront = aFront;
	}
	
	/**
	 * Initializes a margin whose borders are all the same.
	 * @param aUniformValue
	 */
	public Margins (float aUniformValue)
	{
		setUniform(aUniformValue);
	}
	
	/**
	 * Initializes a margin where border thicknesses are set by pairs.
	 * @see #setPairs(float, float, float)
	 */
	public Margins (float aLeftRight, float aTopBottom, float aFrontBack)
	{
		setPairs(aLeftRight, aTopBottom, aFrontBack);
	}
	
	/**
	 * Sets the same margin for all borders.
	 */
	public void setUniform (float aValue)
	{
		setLeft(aValue);
		setRight(aValue);
		setTop(aValue);
		setBottom(aValue);
		setFront(aValue);
		setBack(aValue);
	}
	
	/**
	 * Sets the margins by pairs.
	 * @param aLeftRight Margin for left and right borders
	 * @param aTopBottom Margin for top and bottom borders
	 * @param aFrontBack Margin for front and back borders
	 */
	public void setPairs (float aLeftRight, float aTopBottom, float aFrontBack)
	{
		setLeft(aLeftRight);
		setRight(aLeftRight);
		setTop(aTopBottom);
		setBottom(aTopBottom);
		setFront(aFrontBack);
		setBack(aFrontBack);
	}

	public void setBack (float aBack)
	{
		itsBack = aBack;
	}
	
	public void setBottom (float aBottom)
	{
		itsBottom = aBottom;
	}
	
	public void setFront (float aFront)
	{
		itsFront = aFront;
	}
	
	public void setLeft (float aLeft)
	{
		itsLeft = aLeft;
	}
	
	public void setRight (float aRight)
	{
		itsRight = aRight;
	}
	
	public void setTop (float aTop)
	{
		itsTop = aTop;
	}
	
	public final float getBack ()
	{
		return itsBack;
	}
	
	public final float getBottom ()
	{
		return itsBottom;
	}
	
	public final float getFront ()
	{
		return itsFront;
	}
	
	public final float getLeft ()
	{
		return itsLeft;
	}
	
	public final float getRight ()
	{
		return itsRight;
	}
	
	public final float getTop ()
	{
		return itsTop;
	}
	
	/**
	 * Returns total horizontal margin
	*/
	public final float getHorizontal ()
	{
		return getLeft() + getRight();
	}

	/**
	 * Returns total horizontal margin
	*/
	public final float getVertical ()
	{
		return getTop() + getBottom();
	}
	
	/**
	 * Returns total horizontal margin
	*/
	public final float getDeep ()
	{
		return getFront() + getBack();
	}
}
