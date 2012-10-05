package net.basekit.layoutdelegates;
import javax.vecmath.Vector3f;

import net.basekit.widgets.Widget;

/*
 * Created on Jan 11, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */

/**
 * The delegate that handles the layout of a {@link Widget}.
 * A layout delegate cannot be shared between widgets, nor can it be reused
 * once it has been assigned to another widget.
 * <p>
 * This class also contains utility methods that can be used to implement layout delegates:
 * {@link #getSize(int, int, Widget)}, {@link #getSize(int, Widget)}, {@link #applyMinOrMax(boolean, float, float)}
 * @author gpothier
 */
public abstract class LayoutDelegate
{

	
	private Widget itsWidget;

	/**
	 * This method is called by {@link Widget#setLayoutDelegate(LayoutDelegate)}.
	 * It is not meant to be called by client code.
	 */
	public void setWidget(Widget aWidget)
	{
		if (itsWidget != null) throw new RuntimeException ("Layout delegate is already affected");
		itsWidget = aWidget;
	}

	protected Widget getWidget()
	{
		return itsWidget;
	}
	
	
	/**
	 * Lays out the children of the given group.
	 */
	public abstract void layout ();
	public abstract Vector3f computeMinimumSize ();
	public abstract Vector3f computeMaximumSize ();
	public abstract Vector3f computePreferredSize ();
	
	/**
	 * Called by the widget when a child widget is added.
	 * By default this method does nothing, it is intended to be overriden.
	 * @param aWidget The newly added child widget
	 * @param aLayoutInfo Optional layout info provided to the {@link Widget#addWidget(Widget, Object)} method
	 */
	public void widgetAdded (Widget aWidget, Object aLayoutInfo)
	{
	}

	/**
	 * Called by the widget when a child widget is removed.
	 * By default this method does nothing, it is intended to be overriden.
	 * @param aWidget The removed child widget
	 */
	public void widgetRemoved (Widget aWidget)
	{
	}

	
}
