/*
 * Created on Feb 25, 2004
 */
package net.basekit.layoutdelegates;

import javax.vecmath.Vector3f;

import net.basekit.utils.SizeUtils;
import net.basekit.widgets.Margins;
import net.basekit.widgets.Widget;

/**
 * A layout delegate that place widgets in borders and corners of the main widget,
 * and gives the remaining center space to a widget.
 * <p>
 * The space is divided like a sharp sign (#). Zones are named as follows:
 * <pre>
 *  NW | N | NE
 * -------------
 *   W | C | E
 * -------------
 *  SW | S | SE
 * </pre>
 * <p>
 * The layout infos to use with this layout delegate are public static fields of
 * this class and their name describe which zone(s) a widget will occupy.
 * @author gpothier
 */
public class SharpLayoutDelegate extends LayoutDelegate
{
	private static final Vector3f EMPTY_SIZE = new Vector3f ();
	
	/**
	 * These are zone indices.
	 */
	private static final int ZONE_NW = 0;
	private static final int ZONE_N = 1;
	private static final int ZONE_NE = 2;
	private static final int ZONE_W = 3;
	private static final int ZONE_C = 4;
	private static final int ZONE_E = 5;
	private static final int ZONE_SW = 6;
	private static final int ZONE_S = 7;
	private static final int ZONE_SE = 8;
	
	/**
	 * Layout infos.
	 */
	public static final LayoutInfo N = new LayoutInfo (new int[] {1});
	public static final LayoutInfo NW_N = new LayoutInfo (new int[] {0, 1});
	public static final LayoutInfo N_NE = new LayoutInfo (new int[] {1, 2});
	public static final LayoutInfo NW_N_NE = new LayoutInfo (new int[] {0, 1, 2});
	public static final LayoutInfo NW = new LayoutInfo (new int[] {0});
	public static final LayoutInfo NE = new LayoutInfo (new int[] {2});
	
	public static final LayoutInfo W = new LayoutInfo (new int[] {3});
	public static final LayoutInfo NW_W = new LayoutInfo (new int[] {0, 3});
	public static final LayoutInfo W_SW = new LayoutInfo (new int[] {3, 6});
	public static final LayoutInfo NW_W_SW = new LayoutInfo (new int[] {0, 3, 6});
	
	public static final LayoutInfo S = new LayoutInfo (new int[] {7});
	public static final LayoutInfo SW_S = new LayoutInfo (new int[] {6, 7});
	public static final LayoutInfo S_SE = new LayoutInfo (new int[] {7, 8});
	public static final LayoutInfo SW_S_SE = new LayoutInfo (new int[] {6, 7, 8});
	public static final LayoutInfo SW = new LayoutInfo (new int[] {6});
	public static final LayoutInfo SE = new LayoutInfo (new int[] {8});
	
	public static final LayoutInfo E = new LayoutInfo (new int[] {5});
	public static final LayoutInfo NE_E = new LayoutInfo (new int[] {2, 5});
	public static final LayoutInfo E_SE = new LayoutInfo (new int[] {5, 8});
	public static final LayoutInfo NE_E_SE = new LayoutInfo (new int[] {2, 5, 8});
	
	public static final LayoutInfo C = new LayoutInfo (new int[] {4});
	public static final LayoutInfo N_C = new LayoutInfo (new int[] {1, 4});
	public static final LayoutInfo C_S = new LayoutInfo (new int[] {4, 7});
	public static final LayoutInfo N_C_S = new LayoutInfo (new int[] {1, 4, 7});
	public static final LayoutInfo W_C = new LayoutInfo (new int[] {3, 4});
	public static final LayoutInfo C_E = new LayoutInfo (new int[] {4, 5});
	public static final LayoutInfo W_C_E = new LayoutInfo (new int[] {3, 4, 5});
	
	
	/**
	 * Extension constants.
	 */
	private static final int N_EXTENSION = 1;
	private static final int S_EXTENSION = 2;
	private static final int W_EXTENSION = 4;
	private static final int E_EXTENSION = 8;
	private static final int NS_EXTENSION_MASK = N_EXTENSION | S_EXTENSION;
	private static final int EW_EXTENSION_MASK = W_EXTENSION | E_EXTENSION;
	
	private Widget[] itsWidgetsByZone = new Widget[9];
	
	
	
	/**
	 * Indicates if a zone shares its widget with another zone.
	 * Each value is a boolean or of hte extension constants. 
	 */
	private int[] itsZoneExtensions = new int[9];
	
	/**
	 * Whether {@link #itsZoneExtensions} is up to date
	 * @see #checkExtensions()
	 * @see #invalidateExtensions()
	 */
	private boolean itsExtensionsUpToDate = false;
	
	private void checkExtensions ()
	{
		if (! itsExtensionsUpToDate) computeExtensions();
	}
	
	private void invalidateExtensions ()
	{
		itsExtensionsUpToDate = false;
	}
	
	/**
	 * Computes the zone extensions from scratch.
	 */
	private void computeExtensions ()
	{
		for (int i=0;i<9;i++)
		{
			int theExtension = 0;
			
			Widget theZoneWidget = itsWidgetsByZone[i];
			if (theZoneWidget == null) continue;
			
			if (theZoneWidget == getWidgetForZone(getExtensionIndex(i, N_EXTENSION))) theExtension |= N_EXTENSION;
			if (theZoneWidget == getWidgetForZone(getExtensionIndex(i, S_EXTENSION))) theExtension |= S_EXTENSION;
			if (theZoneWidget == getWidgetForZone(getExtensionIndex(i, W_EXTENSION))) theExtension |= W_EXTENSION;
			if (theZoneWidget == getWidgetForZone(getExtensionIndex(i, E_EXTENSION))) theExtension |= E_EXTENSION;
			
			assert (theExtension & NS_EXTENSION_MASK) == 0 || (theExtension & EW_EXTENSION_MASK) == 0;
			itsZoneExtensions[i] = theExtension;
		}
		itsExtensionsUpToDate = true;
	}
	
	/**
	 * Returns the widget in the specified zone, or null if the zone code is -1;
	 */
	private Widget getWidgetForZone (int aZoneIndex)
	{
		return aZoneIndex != -1 ? itsWidgetsByZone[aZoneIndex] : null;
	}
	
	/**
	 * Computes the index of the adjacent zone.
	 * @param aZoneIndex The original zone
	 * @param aExtension The direction in which to extend.
	 * @return The index of the adjacent zone, or -1 if there is none.
	 */
	private int getExtensionIndex (int aZoneIndex, int aExtension)
	{
		int theResult;
		switch (aExtension)
		{
			case N_EXTENSION: theResult = aZoneIndex-3;break;
			case S_EXTENSION: theResult = aZoneIndex+3;break;
			case E_EXTENSION: theResult = aZoneIndex+1;break;
			case W_EXTENSION: theResult = aZoneIndex-1;break;
			default: assert false;return -1;
		}
		return theResult >= 0 && theResult < 9 ? theResult : -1;
	}

	public void layout()
	{
		Sizes theSizes = computeSizes(SizeUtils.PREFERRED_SELECTOR);
		Vector3f theWidgetSize = getWidget().getSize();
		Margins theMargins = getWidget().getMargins();
		
		theSizes.cw = theWidgetSize.x - theMargins.getHorizontal() - theSizes.ww - theSizes.ew;
		theSizes.ch = theWidgetSize.y - theMargins.getVertical() - theSizes.nh - theSizes.sh;
		checkExtensions();
		
		float theX, theY, theW, theH;
		final float theZ = 0;
		final float theD = theSizes.d;
		
		// First we layout the four corners, if they are individual widgets.
		
		if (hasWidget(ZONE_NW) && itsZoneExtensions[ZONE_NW] == 0) 
		{
			theX = theMargins.getLeft(); 
			theY = theMargins.getTop();
			theW = theSizes.ww;
			theH = theSizes.nh;
			setWidgetBounds(ZONE_NW, theX, theY, theZ, theW, theH, theD);
		}
		
		if (hasWidget(ZONE_NE) && itsZoneExtensions[ZONE_NE] == 0)
		{
			theX = theMargins.getLeft() + theSizes.ww + theSizes.cw;
			theY = theMargins.getTop();
			theW = theSizes.ew;
			theH = theSizes.nh;
			setWidgetBounds(ZONE_NE, theX, theY, theZ, theW, theH, theD);
		}
		
		if (hasWidget(ZONE_SW) && itsZoneExtensions[ZONE_SW] == 0)
		{
			theX = theMargins.getLeft();
			theY = theMargins.getTop() + theSizes.nh + theSizes.ch;
			theW = theSizes.ww;
			theH = theSizes.sh;
			setWidgetBounds(ZONE_SW, theX, theY, theZ, theW, theH, theD);
		}

		if (hasWidget(ZONE_SE) && itsZoneExtensions[ZONE_SE] == 0)
		{
			theX = theMargins.getLeft() + theSizes.ww + theSizes.cw;
			theY = theMargins.getTop() + theSizes.nh + theSizes.ch;
			theW = theSizes.ew;
			theH = theSizes.sh;
			setWidgetBounds(ZONE_SE, theX, theY, theZ, theW, theH, theD);
		}
		
		int theExtension;
	
		// North widget
		theExtension = itsZoneExtensions[ZONE_N];
		if (hasWidget(ZONE_N) && theExtension != S_EXTENSION)
		{
			theX = theMargins.getLeft() + ((theExtension & W_EXTENSION) != 0 ? 0 : theSizes.ww);
			theY = theMargins.getTop();
			theW = theSizes.cw;
			if ((theExtension & W_EXTENSION) != 0) theW += theSizes.ww;
			if ((theExtension & E_EXTENSION) != 0) theW += theSizes.ew;
			theH = theSizes.nh;
			setWidgetBounds(ZONE_N, theX, theY, theZ, theW, theH, theD);
		}
	
		// South widget
		theExtension = itsZoneExtensions[ZONE_S];
		if (hasWidget(ZONE_S) && theExtension != N_EXTENSION)
		{
			theX = theMargins.getLeft() + ((theExtension & W_EXTENSION) != 0 ? 0 : theSizes.ww);
			theY = theMargins.getTop() + theSizes.nh + theSizes.ch;
			theW = theSizes.cw;
			if ((theExtension & W_EXTENSION) != 0) theW += theSizes.ww;
			if ((theExtension & E_EXTENSION) != 0) theW += theSizes.ew;
			theH = theSizes.sh;
			setWidgetBounds(ZONE_S, theX, theY, theZ, theW, theH, theD);
		}
		
		// West widget
		theExtension = itsZoneExtensions[ZONE_W];
		if (hasWidget(ZONE_W) && theExtension != E_EXTENSION)
		{
			theX = theMargins.getLeft();
			theY = theMargins.getTop() + ((theExtension & N_EXTENSION) != 0 ? theMargins.getTop() : theSizes.nh);
			theW = theSizes.ww;
			theH = theSizes.ch;
			if ((theExtension & N_EXTENSION) != 0) theH += theSizes.nh;
			if ((theExtension & S_EXTENSION) != 0) theH += theSizes.sh;
			setWidgetBounds(ZONE_W, theX, theY, theZ, theW, theH, theD);
		}

		// East widget
		theExtension = itsZoneExtensions[ZONE_E];
		if (hasWidget(ZONE_E) && theExtension != W_EXTENSION)
		{
			theX = theMargins.getLeft() + theSizes.ww + theSizes.cw;
			theY = theMargins.getTop() + ((theExtension & N_EXTENSION) != 0 ? theMargins.getTop() : theSizes.nh);
			theW = theSizes.ew;
			theH = theSizes.ch;
			if ((theExtension & N_EXTENSION) != 0) theH += theSizes.nh;
			if ((theExtension & S_EXTENSION) != 0) theH += theSizes.sh;
			setWidgetBounds(ZONE_E, theX, theY, theZ, theW, theH, theD);
		}

		// Center widget
		if (hasWidget(ZONE_C))
		{
			theExtension = itsZoneExtensions[ZONE_C];

			// Note: it is supposed to be already checked that we do not have both
			// horizontal and vertical extension.
			theX = theMargins.getLeft() + ((theExtension & W_EXTENSION) != 0 ? 0 : theSizes.ww);
			theY = theMargins.getTop() + ((theExtension & N_EXTENSION) != 0 ? theMargins.getTop() : theSizes.nh);
			theW = theSizes.cw;
			if ((theExtension & W_EXTENSION) != 0) theW += theSizes.ww;
			if ((theExtension & E_EXTENSION) != 0) theW += theSizes.ew;
			theH = theSizes.ch;
			if ((theExtension & N_EXTENSION) != 0) theH += theSizes.nh;
			if ((theExtension & S_EXTENSION) != 0) theH += theSizes.sh;
			setWidgetBounds(ZONE_C, theX, theY, theZ, theW, theH, theD);
		}
	}
	
	private boolean hasWidget (int aZone)
	{
		return itsWidgetsByZone[aZone] != null;
	}
	
	private void setWidgetBounds (int aZone, float aX, float aY, float aZ, float aW, float aH, float aD)
	{
		itsWidgetsByZone[aZone].setPosition(new Vector3f (aX, aY, aZ));
		itsWidgetsByZone[aZone].setSize(new Vector3f (aW, aH, aD));		
	}

	public Vector3f computeMinimumSize()
	{
		return computeSize(SizeUtils.MINIMUM_SELECTOR);
	}

	public Vector3f computeMaximumSize()
	{
		return computeSize(SizeUtils.MAXIMUM_SELECTOR);
	}

	public Vector3f computePreferredSize()
	{
		return computeSize(SizeUtils.PREFERRED_SELECTOR);
	}
	
	private Sizes computeSizes (int aSizeSelector)
	{
		checkExtensions();
		boolean theUseMin; //Whether we want the min or the max of all sizes.
		
		switch (aSizeSelector)
		{
			case SizeUtils.MINIMUM_SELECTOR: theUseMin = false;break;
			case SizeUtils.MAXIMUM_SELECTOR: theUseMin = true;break;
			case SizeUtils.PREFERRED_SELECTOR: theUseMin = false;break;
			default: assert false;return null;
		}
		
		Sizes theSizes = new Sizes (theUseMin);
		
		theSizes.nh = SizeUtils.applyMinOrMax(theUseMin, theSizes.nh, getSize(aSizeSelector, SizeUtils.Y_SELECTOR, EW_EXTENSION_MASK, ZONE_NW));
		theSizes.nh = SizeUtils.applyMinOrMax(theUseMin, theSizes.nh, getSize(aSizeSelector, SizeUtils.Y_SELECTOR, EW_EXTENSION_MASK, ZONE_N));
		theSizes.nh = SizeUtils.applyMinOrMax(theUseMin, theSizes.nh, getSize(aSizeSelector, SizeUtils.Y_SELECTOR, EW_EXTENSION_MASK, ZONE_NE));
		
		theSizes.sh = SizeUtils.applyMinOrMax(theUseMin, theSizes.sh, getSize(aSizeSelector, SizeUtils.Y_SELECTOR, EW_EXTENSION_MASK, ZONE_SW));
		theSizes.sh = SizeUtils.applyMinOrMax(theUseMin, theSizes.sh, getSize(aSizeSelector, SizeUtils.Y_SELECTOR, EW_EXTENSION_MASK, ZONE_S));
		theSizes.sh = SizeUtils.applyMinOrMax(theUseMin, theSizes.sh, getSize(aSizeSelector, SizeUtils.Y_SELECTOR, EW_EXTENSION_MASK, ZONE_SE));
		
		theSizes.ww = SizeUtils.applyMinOrMax(theUseMin, theSizes.ww, getSize(aSizeSelector, SizeUtils.X_SELECTOR, NS_EXTENSION_MASK, ZONE_NW));
		theSizes.ww = SizeUtils.applyMinOrMax(theUseMin, theSizes.ww, getSize(aSizeSelector, SizeUtils.X_SELECTOR, NS_EXTENSION_MASK, ZONE_W));
		theSizes.ww = SizeUtils.applyMinOrMax(theUseMin, theSizes.ww, getSize(aSizeSelector, SizeUtils.X_SELECTOR, NS_EXTENSION_MASK, ZONE_SW));
		
		theSizes.ew = SizeUtils.applyMinOrMax(theUseMin, theSizes.ew, getSize(aSizeSelector, SizeUtils.X_SELECTOR, NS_EXTENSION_MASK, ZONE_NE));
		theSizes.ew = SizeUtils.applyMinOrMax(theUseMin, theSizes.ew, getSize(aSizeSelector, SizeUtils.X_SELECTOR, NS_EXTENSION_MASK, ZONE_E));
		theSizes.ew = SizeUtils.applyMinOrMax(theUseMin, theSizes.ew, getSize(aSizeSelector, SizeUtils.X_SELECTOR, NS_EXTENSION_MASK, ZONE_SE));
		
		theSizes.ch = SizeUtils.applyMinOrMax(theUseMin, theSizes.ch, getSize(aSizeSelector, SizeUtils.Y_SELECTOR, EW_EXTENSION_MASK, ZONE_W));
		theSizes.ch = SizeUtils.applyMinOrMax(theUseMin, theSizes.ch, getSize(aSizeSelector, SizeUtils.Y_SELECTOR, EW_EXTENSION_MASK, ZONE_C));
		theSizes.ch = SizeUtils.applyMinOrMax(theUseMin, theSizes.ch, getSize(aSizeSelector, SizeUtils.Y_SELECTOR, EW_EXTENSION_MASK, ZONE_E));
		
		theSizes.cw = SizeUtils.applyMinOrMax(theUseMin, theSizes.cw, getSize(aSizeSelector, SizeUtils.X_SELECTOR, NS_EXTENSION_MASK, ZONE_N));
		theSizes.cw = SizeUtils.applyMinOrMax(theUseMin, theSizes.cw, getSize(aSizeSelector, SizeUtils.X_SELECTOR, NS_EXTENSION_MASK, ZONE_C));
		theSizes.cw = SizeUtils.applyMinOrMax(theUseMin, theSizes.cw, getSize(aSizeSelector, SizeUtils.X_SELECTOR, NS_EXTENSION_MASK, ZONE_S));
		
		for (int i=0;i<9;i++)
		{
			theSizes.d = SizeUtils.applyMinOrMax(theUseMin, theSizes.d, getSize(aSizeSelector, SizeUtils.Z_SELECTOR, -1, i));
		}
		
		return theSizes;
	}
	
	private Vector3f computeSize (int aSizeSelector)
	{
		Margins theMargins = getWidget().getMargins();
		Sizes theSizes = computeSizes(aSizeSelector);
		return new Vector3f (
				theMargins.getHorizontal() + theSizes.ww + theSizes.cw + theSizes.ew,
				theMargins.getVertical() + theSizes.nh + theSizes.ch + theSizes.sh,
				theMargins.getDeep() + theSizes.d);
	}
	
	/**
	 * Returns the size of the widget of a given zone. If there is no widget, or if 
	 * the zone has an extension that is not in the
	 * allowed mask, the method will return an empty size.
	 */
	private float getSize (int aSizeSelector, int aAxisSelector, int aAllowedExtensionMask, int aZone)
	{
		return (itsZoneExtensions[aZone] & aAllowedExtensionMask) == 0 && hasWidget(aZone)
			? SizeUtils.getSize(aSizeSelector, aAxisSelector, itsWidgetsByZone[aZone]) 
			: 0;		
	}
		
	/**
	 * Updates internal structures in response to widget addition.
	 */
	public void widgetAdded(Widget aWidget, Object aLayoutInfo)
	{
		assert aWidget != null && aLayoutInfo != null;
	
		LayoutInfo theLayoutInfo = (LayoutInfo) aLayoutInfo;
		int[] theZones = theLayoutInfo.getZones ();
		for (int i = 0; i < theZones.length; i++)
		{
			int theZone = theZones[i];
			assert getWidgetForZone(theZone) == null;
			itsWidgetsByZone[theZone] = aWidget;
		}
		
		invalidateExtensions();
	}

	/**
	 * Updates internal structures in response to widget removal.
	 */
	public void widgetRemoved(Widget aWidget)
	{
		assert aWidget != null;
		for (int i = 0; i < itsWidgetsByZone.length; i++)
		{
			Widget theWidget = itsWidgetsByZone[i];
			if (theWidget == aWidget) itsWidgetsByZone[i] = null;
		}
		
		invalidateExtensions();
	}

	/**
	 * Instances of this class are used as layout info.
	 * They describe which zones are occupied by a given widget.
	 */
	private static class LayoutInfo
	{
		private int[] itsZones;
		
		public LayoutInfo(int[] aZones) 
		{
			itsZones = aZones;
		}

		public int[] getZones()
		{
			return itsZones;
		}
	}
	
	/**
	 * Contains all the sizes that are needed to compute layout size or to perform layout.
	 */
	private static class Sizes
	{
		float nh; //north height
		float sh; //south height
		float ww; //west width
		float ew; //east width

		float ch; //center height
		float cw; //center width

		float d; //depth
		
		public Sizes (boolean aUseMin)
		{
			nh = sh = ww = ew = ch = cw = d = aUseMin ? Float.MAX_VALUE : 0;
		}
		
	}
	
}
