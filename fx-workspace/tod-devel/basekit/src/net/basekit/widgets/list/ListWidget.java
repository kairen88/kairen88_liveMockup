/*
 * Created on Mar 6, 2004
 */
package net.basekit.widgets.list;

import java.util.Arrays;
import java.util.List;

import javax.vecmath.Tuple3f;

import net.basekit.Message;
import net.basekit.messages.SelectionChanged;
import net.basekit.models.list.DefaultListContentWidgetModel;
import net.basekit.models.list.ListContentWidgetModel;
import net.basekit.models.list.messages.IntervalAddedMessage;
import net.basekit.models.list.messages.IntervalRemovedMessage;
import net.basekit.models.selection.SelectionWidgetModel;
import net.basekit.models.selection.SimpleSelectionWidgetModel;
import net.basekit.models.selection.messages.SelectionChangedMessage;
import net.basekit.widgets.Formatted;
import net.basekit.widgets.RenderedWidget;
import zz.utils.Formatter;
import zz.utils.Utils;

/**
 * Displays a list of elements. The elements are taken from a 
 * {@link javax.swing.ListModel}.
 * Individual elements are renderer by a {@link net.basekit.widgets.list.ListElementRenderer}.
 * Selection is managed by a {@link javax.swing.ListSelectionModel}
 * <p>
 * Messages
 * <li>{@link net.basekit.messages.SelectionChanged}
 * @author gpothier
 */
public class ListWidget extends RenderedWidget
{
	private ListContentWidgetModel itsModel;
	private SelectionWidgetModel itsSelectionModel;
	private ListElementRenderer itsElementRenderer;
	
	public ListWidget ()
	{
		this ((ListContentWidgetModel) null);
	}

	public ListWidget(ListContentWidgetModel aModel)
	{
		setSelectionModel (new SimpleSelectionWidgetModel (aModel));
		setElementRenderer (new LabelElementRenderer ());
		setRenderer(new ScrollListRenderer ());
		setModel(aModel);
	}

	/**
	 * Constructs a list with a default model containing the elements of the
	 * specified list.
	 */
	public ListWidget(List aList)
	{
		this (new DefaultListContentWidgetModel (aList));
	}
	
	/**
	 * Constructs a list with a default model containing the elements of the
	 * specified array.
	 */
	public ListWidget(Object[] aArray)
	{
		this (Arrays.asList(aArray));
	}
	
	public ListContentWidgetModel getModel ()
	{
		return itsModel;
	}
	
	public void setModel (ListContentWidgetModel aModel)
	{
		if (itsModel != null) itsModel.removeObserver(this);
		itsModel = aModel;
		if (itsModel != null) itsModel.addObserver(this);
		getListRenderer().reset();
	}
	
	public SelectionWidgetModel getSelectionModel ()
	{
		return itsSelectionModel;
	}
	
	public void setSelectionModel (SelectionWidgetModel aSelectionModel)
	{
		if (itsSelectionModel != null) itsSelectionModel.removeObserver (this);
		itsSelectionModel = aSelectionModel;
		if (itsSelectionModel != null) itsSelectionModel.addObserver (this);
	}

	/**
	 * Casts our renderer to a list renderer.
	 */
	private ListRenderer getListRenderer ()
	{
		return (ListRenderer) getRenderer();
	}
	
	public ListElementRenderer getElementRenderer ()
	{
		return itsElementRenderer;
	}
	
	public void setElementRenderer (ListElementRenderer aElementRenderer)
	{
		itsElementRenderer = aElementRenderer;
		if (getListRenderer () != null) getListRenderer().reset();		
	}

	public boolean setSize (Tuple3f aSize)
	{
		if (super.setSize (aSize))
		{
			render ();
			return true;
		}
		else return false;
	}

	/**
	 * Convenience method that selects a single index in the list
	 */
	public void setSelectedIndex (int aIndex)
	{
		getSelectionModel ().setSelectedIndices (new int[] {aIndex});
	}

	/**
	 * Convenience method to select the first object in the list that is equal to
	 * the specified object.
	 * @return Whether the object was found.
	 */
	public boolean setSelectedValue (Object aValue)
	{
		if (itsModel != null)
		{
			int theCount = itsModel.getSize ();
			for (int i=0;i<theCount;i++)
			{
				Object theObject = itsModel.getElementAt (i);
				if (Utils.equalOrBothNull (aValue, theObject))
				{
					setSelectedIndex (i);
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Returns the index of the first selected element, or -1 if there is none.
	 * This is a convenience method that delegates to the selection model.
	 */
	public int getSelectedIndex ()
	{
		int[] theSelectedIndices = getSelectionModel ().getSelectedIndices ();
		return theSelectedIndices.length > 0 ? theSelectedIndices[0] : -1;
	}

	/**
	 * Returns the first selected value, or null if there is none.
	 */
	public Object getSelectedValue ()
	{
		int theIndex = getSelectedIndex ();
		if (theIndex == -1) return null;
		else return getModel ().getElementAt (theIndex);
	}

	/**
	 * Fires a {@link SelectionChanged} message.
	 */
	protected void fireSelectionChanged ()
	{
		fire (new SelectionChanged (this));
	}

	public void processMessage (Message aMessage)
	{
		if (aMessage instanceof SelectionChangedMessage)
		{
			SelectionChangedMessage theMessage = (SelectionChangedMessage) aMessage;
			render ();
		}
		else if (aMessage instanceof IntervalAddedMessage)
		{
			IntervalAddedMessage theMessage = (IntervalAddedMessage) aMessage;
			getListRenderer().intervalAdded(theMessage);
		}
		else if (aMessage instanceof IntervalRemovedMessage)
		{
			IntervalRemovedMessage theMessage = (IntervalRemovedMessage) aMessage;
			getListRenderer().intervalRemoved(theMessage);
		}
		super.processMessage (aMessage);
	}

	/**
	 * Sets the formatter of the element renderer ({@link #itsElementRenderer}, if it
	 * is an instance of {@link net.basekit.widgets.Formatted}. Fails otherwise.
	 * @param aFormatter
	 */
	public void setFormatter (Formatter aFormatter)
	{
		assert itsElementRenderer instanceof Formatted;
		((Formatted)itsElementRenderer).setFormatter (aFormatter);
	}
}
