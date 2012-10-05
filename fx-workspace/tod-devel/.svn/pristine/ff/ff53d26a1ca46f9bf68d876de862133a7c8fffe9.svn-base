/**
 * Created by IntelliJ IDEA.
 * User: g1
 * Date: 9 mars 2004
 * Time: 21:36:23
 * To change this template use File | Settings | File Templates.
 */
package net.basekit.widgets.combo;

import java.util.Arrays;
import java.util.List;

import javax.vecmath.Vector3f;

import net.basekit.Message;
import net.basekit.Observer;
import net.basekit.messages.SelectionChanged;
import net.basekit.models.action.DefaultActionWidgetModel;
import net.basekit.models.action.messages.ActionTriggeredMessage;
import net.basekit.models.combo.ComboWidgetModel;
import net.basekit.models.combo.DefaultComboWidgetModel;
import net.basekit.models.combo.messages.SelectedItemChangedMessage;
import net.basekit.utils.ToStringFormatter;
import net.basekit.widgets.PopupWidget;
import net.basekit.widgets.button.ButtonRenderer;
import net.basekit.widgets.button.ButtonWidget;
import net.basekit.widgets.list.ListElementRenderer;
import net.basekit.widgets.list.ListRenderer;
import net.basekit.widgets.list.ListWidget;
import zz.utils.Formatter;

/**
 * The classic combo widget that displays a button that when clicked popups up a list of available choices.
 * <p>
 * Messages
 * <li>{@link SelectionChanged}
 * @author gpothier
 */
public class ComboWidget extends PopupWidget
{
	private Formatter itsButtonFormatter = ToStringFormatter.getInstance ();
	private DefaultActionWidgetModel itsAction;
	private ListWidget itsListWidget;
	private ComboWidgetModel itsModel;
	private ButtonWidget itsButtonWidget;

	/**
	 * Constructs a combo widget with no model.
	 */
	public ComboWidget ()
	{
		this ((ComboWidgetModel) null);
	}
	
	public ComboWidget (ComboWidgetModel aModel)
	{
		setPacked (true);
		itsAction = new DefaultActionWidgetModel ("");
		itsButtonWidget = new ButtonWidget (itsAction);
		itsAction.addObserver (this);
		itsListWidget = new ListWidget ();
		itsListWidget.setPreferredSize (new Vector3f (100, 150, 0));
		itsListWidget.getSelectionModel ().addObserver (new Observer ()
		{
			public void processMessage (Message aMessage)
			{
				listSelectionChanged ();
			}
		});
		setModel (aModel);

		setHook (itsButtonWidget);
		setPopup (itsListWidget);
	}

	/**
	 * Constructs a combo with a default model containing the elements of the
	 * specified list.
	 */
	public ComboWidget(List aList)
	{
		this (new DefaultComboWidgetModel (aList) );
	}

	/**
	 * Constructs a combo with a default model containing the elements of the
	 * specified array.
	 */
	public ComboWidget(Object[] aArray)
	{
		this (Arrays.asList(aArray));
	}


	/**
	 * Called when the list selection changes.
	 */
	private void listSelectionChanged ()
	{
		hidePopup ();
		itsModel.setSelectedItem (itsListWidget.getSelectedValue ());
	}

	private void selectedItemChanged ()
	{
		Object theSelectedItem = itsModel.getSelectedItem ();
		itsListWidget.setSelectedValue (theSelectedItem);
		itsAction.setTitle (itsButtonFormatter.getText (theSelectedItem));
	}

	public void setModel (ComboWidgetModel aModel)
	{
		if (itsModel != null) itsModel.removeObserver (this);
		itsModel = aModel;
		itsListWidget.setModel (aModel);
		if (itsModel != null)
		{
			itsModel.addObserver (this);
			selectedItemChanged ();
		}
	}
	
	public ComboWidgetModel getModel ()
	{
		return itsModel;
	}

	public void setButtonRenderer (ButtonRenderer aButtonRenderer)
	{
		itsButtonWidget.setRenderer (aButtonRenderer);
	}

	public void setListRenderer (ListRenderer aListRenderer)
	{
		itsListWidget.setRenderer (aListRenderer);
	}

	public void setListElementRenderer (ListElementRenderer aElementRenderer)
	{
		itsListWidget.setElementRenderer (aElementRenderer);
	}

	/**
	 * Returns the currently selected item in the combo.
	 */
	public Object getSelectedItem ()
	{
		return itsModel.getSelectedItem ();
	}

	/**
	 * Sets the formatter of both the list's element renderer ({@link ListWidget#itsElementRenderer}
	 * and the button's renderer,
	 * if they are instances of {@link net.basekit.widgets.Formatted}. Fails otherwise.
	 */
	public void setFormatter (Formatter aFormatter)
	{
		itsButtonFormatter = aFormatter;
		itsListWidget.setFormatter (aFormatter);
	}

	public void processMessage (Message aMessage)
	{
		if (aMessage instanceof ActionTriggeredMessage)
		{
			ActionTriggeredMessage theMessage = (ActionTriggeredMessage) aMessage;
			assert theMessage.getSource () == itsAction;
			togglePopup ();
		}
		else if (aMessage instanceof SelectedItemChangedMessage)
		{
			SelectedItemChangedMessage theMessage = (SelectedItemChangedMessage) aMessage;
			assert theMessage.getSource () == itsModel;
			selectedItemChanged ();
		}
		super.processMessage (aMessage);
	}

}
