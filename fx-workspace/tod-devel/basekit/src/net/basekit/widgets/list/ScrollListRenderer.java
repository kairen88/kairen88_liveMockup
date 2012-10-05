/*
 * Created on Mar 6, 2004
 */
package net.basekit.widgets.list;

import java.util.Iterator;

import javax.swing.event.ListDataEvent;
import javax.vecmath.Vector3f;

import net.basekit.Message;
import net.basekit.Observer;
import net.basekit.devices.mouse.messages.MousePressed;
import net.basekit.devices.mouse.messages.MouseWheelMoved;
import net.basekit.layoutdelegates.AxisLayoutDelegate;
import net.basekit.models.list.ListContentWidgetModel;
import net.basekit.models.list.messages.IntervalAddedMessage;
import net.basekit.models.list.messages.IntervalRemovedMessage;
import net.basekit.models.selection.SelectionWidgetModel;
import net.basekit.widgets.Widget;

/**
 * A very basic list renderer.
 * @author gpothier
 */
public class ScrollListRenderer extends ListRenderer implements Observer
{
	/**
	 * Index of the first displayed item.
	 */
	private int itsInitialIndex = 0;

	public void initRenderer ()
	{
		getListWidget().setLayoutDelegate(new AxisLayoutDelegate (AxisLayoutDelegate.Y_POSITIVE, AxisLayoutDelegate.JUSTIFIED));
		getListWidget ().addObserver (this);
		reset ();
	}

	/**
	 * Here we need to update each element's state.
	 */
	public void updateRenderer ()
	{
		reset ();
	}
	
	public void intervalAdded (IntervalAddedMessage aMessage)
	{
		reset ();
		super.intervalAdded(aMessage);
	}
	
	public void intervalRemoved (IntervalRemovedMessage aMessage)
	{
		reset ();
		super.intervalRemoved(aMessage);
	}

	/**
	 * Updates the state of each element.
	 */
	private void updateState ()
	{
		ListElementRenderer theElementRenderer = getListWidget().getElementRenderer();
		SelectionWidgetModel theSelectionModel = getListWidget().getSelectionModel();

		for (Iterator theIterator = getListWidget ().getWidgetsIterator (); theIterator.hasNext ();)
		{
			ListElementWidget theWidget = (ListElementWidget) theIterator.next ();

			int theIndex = theWidget.getIndex ();
			boolean theSelected = theSelectionModel.isSelected (theIndex);
			theElementRenderer.updateWidget(theWidget, theSelected, false);
		}
	}

	/**
	 * Rebuilds the list from scratch.
	 */
	public void reset ()
	{
		getListWidget().clearWidgets();
		ListContentWidgetModel theModel = getListWidget().getModel();
		if (theModel == null) return;
		
		ListElementRenderer theElementRenderer = getListWidget().getElementRenderer();
		Vector3f theSize = getListWidget ().getSize ();
		if (theSize == null) return;
		float theHeight = theSize.y;

		int theCount = theModel.getSize();
		for (int i=itsInitialIndex;i<theCount;i++)
		{
			Object theElement = theModel.getElementAt(i);
			Widget theWidget = (Widget) theElementRenderer.createWidget(theElement, i);
			Vector3f theElementSize = theWidget.getPreferredSize ();
			theHeight -= theElementSize.y;
			if (theHeight < 0) break;

			getListWidget().addWidget(theWidget);
		}

		updateState ();
	}

	public void processMessage (Message aMessage)
	{
		if (aMessage instanceof MouseWheelMoved)
		{
			MouseWheelMoved theMessage = (MouseWheelMoved) aMessage;
			itsInitialIndex += theMessage.getAmount ();
			if (itsInitialIndex < 0) itsInitialIndex = 0;

			reset ();
			theMessage.consume ();
		}
		else if (aMessage instanceof MousePressed)
		{
			MousePressed theMessage = (MousePressed) aMessage;
			for (Iterator theIterator = theMessage.getWidgetsIterator (); theIterator.hasNext ();)
			{
				Widget theWidget = (Widget) theIterator.next ();
				if (theWidget instanceof ListElementWidget)
				{
					ListElementWidget theListElementWidget = (ListElementWidget) theWidget;
					int theIndex = theListElementWidget.getIndex ();
					getListWidget ().setSelectedIndex (theIndex);
					theMessage.consume ();
					break;
				}
			}
		}
	}
}
