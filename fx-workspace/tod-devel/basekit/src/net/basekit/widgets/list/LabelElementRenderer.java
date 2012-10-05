/*
 * Created on Mar 6, 2004
 */
package net.basekit.widgets.list;

import java.awt.Color;

import net.basekit.shapes.LineBorder;
import net.basekit.utils.AppearanceFactory;
import net.basekit.utils.Appearances;
import net.basekit.utils.ToStringFormatter;
import net.basekit.widgets.Formatted;
import net.basekit.widgets.label.LabelWidget;
import zz.utils.Formatter;

import com.xith3d.scenegraph.Appearance;
import com.xith3d.scenegraph.Shape3D;

/**
 * Simple renderer that display an element as a text.
 * The way the element is converted to text is defined by the {@link #itsFormatter formatter}.
 * By default it will be {@link ToStringFormatter}
 * @author gpothier
 */
public class LabelElementRenderer implements ListElementRenderer, Formatted
{

	/**
	 * The formatter used to transform the value of the element into a string.
	 */
	private Formatter itsFormatter;

	public LabelElementRenderer ()
	{
		this (ToStringFormatter.getInstance ());
	}

	public LabelElementRenderer (Formatter aFormatter)
	{
		itsFormatter = aFormatter;
	}

	public Formatter getFormatter ()
	{
		return itsFormatter;
	}

	public void setFormatter (Formatter aFormatter)
	{
		itsFormatter = aFormatter;
	}

	public final ListElementWidget createWidget (Object aElement, int aIndex)
	{
		return new ListElementLabelWidget (aElement, aIndex);
	}

	public final void updateWidget (ListElementWidget aWidget, boolean aSelected, boolean aFocused)
	{
		ListElementLabelWidget theWidget = (ListElementLabelWidget) aWidget;
		updateWidget(theWidget, aSelected, aFocused);
	}
	
	/**
	 * This method can be subclassed. It is similar to {@link #updateWidget(ListElementWidget, boolean, boolean)}
	 * it has the widget cast to the proper type.
	 */
	public void updateWidget (ListElementLabelWidget aWidget, boolean aSelected, boolean aFocused)
	{
		String theText = itsFormatter.getText(aWidget.getElement());
		aWidget.setTitle(theText);
		aWidget.setBorderAppearance (aSelected ? Appearances.RED : Appearances.BLUE);
	}
	
	/**
	 * A custom subclass of label widget that keeps track of the element it represents.
	 * @author gpothier
	 */
	private static class ListElementLabelWidget extends LabelWidget implements ListElementWidget
	{
		private Object itsElement;
		private int itsIndex;

		public ListElementLabelWidget (Object aElement, int aIndex)
		{
			itsElement = aElement;
			itsIndex = aIndex;
			setBorder (new LineBorder (1));
		}

		public Object getElement ()
		{
			return itsElement;
		}

		public int getIndex ()
		{
			return itsIndex;
		}

		public void setBorderAppearance (Appearance aAppearance)
		{
			((Shape3D) getBorder ()).setAppearance (aAppearance);
		}
	}
}
