/*
 * Created on Mar 6, 2004
 */
package net.basekit.widgets.button;

import javax.vecmath.Tuple3f;

import net.basekit.Message;
import net.basekit.devices.mouse.Mouse;
import net.basekit.devices.mouse.messages.MouseEntered;
import net.basekit.devices.mouse.messages.MouseExited;
import net.basekit.devices.mouse.messages.MousePressed;
import net.basekit.devices.mouse.messages.MouseReleased;
import net.basekit.models.action.ActionWidgetModel;
import net.basekit.models.action.DefaultActionWidgetModel;
import net.basekit.models.label.messages.LabelDataChanged;
import net.basekit.widgets.RenderedWidget;
import zz.utils.Utils;

/**
 * The classic button widget.
 * @see ActionWidgetModel
 * @author gpothier
 */
public class ButtonWidget extends RenderedWidget
{
	/**
	 * Is a mouse inside this button?
	 */
	private boolean itsMouseOver = false;
	
	/**
	 * Is the button is pressed?
	 */
	private boolean itsPressed = false;
	
	/**
	 * Has the button a graphical focus?
	 */
	private boolean itsFocused = false;

	/**
	 * The action that will be triggered when the button is pressed.
	 * It also provides the button's title, enabled state, etc.
	 */
	private ActionWidgetModel itsModel;

	public ButtonWidget()
	{
		this (new DefaultActionWidgetModel (""));
	}
	
	public ButtonWidget (ActionWidgetModel aModel)
	{
		setModel (aModel);
		setRenderer(new FlatRenderer ());
	}
	
	/**
	 * We process our own messages in order to react to user input.
	 */
	public void processMessage (Message aMessage)
	{
		if (aMessage instanceof MousePressed)
		{
			MousePressed theMousePressed = (MousePressed) aMessage;
			if (theMousePressed.getButton() == Mouse.MAIN_BUTTON) 
			{
				setPressed (true);
				theMousePressed.consume();
			}
		}
		else if (aMessage instanceof MouseReleased)
		{
			MouseReleased theMouseReleased= (MouseReleased) aMessage;
			if (isPressed () && theMouseReleased.getButton() == Mouse.MAIN_BUTTON)
			{
				setPressed (false);
				theMouseReleased.consume();
				getModel ().trigger ();
			}
		}
		else if (aMessage instanceof MouseEntered)
		{
			MouseEntered theMouseEntered = (MouseEntered) aMessage;
			setMouseOver(true);
			theMouseEntered.consume();
		}
		else if (aMessage instanceof MouseExited)
		{
			MouseExited theMouseExited = (MouseExited) aMessage;
			setMouseOver(true);
			theMouseExited.consume();
		}
		else if (aMessage instanceof LabelDataChanged)
		{
			render ();
			invalidateLayout ();
		}

		super.processMessage(aMessage);
	}
	
	public boolean isFocused ()
	{
		return itsFocused;
	}
	
	public void setFocused (boolean aFocused)
	{
		itsFocused = aFocused;
		render ();
	}
	
	public boolean isMouseOver ()
	{
		return itsMouseOver;
	}
	
	public void setMouseOver (boolean aMouseOver)
	{
		itsMouseOver = aMouseOver;
		render ();
	}
	
	public boolean isPressed ()
	{
		return itsPressed;
	}
	
	public void setPressed (boolean aPressed)
	{
		itsPressed = aPressed;
		render ();
	}

	public ActionWidgetModel getModel ()
	{
		return itsModel;
	}

	public void setModel (ActionWidgetModel aModel)
	{
		if (itsModel != null) itsModel.removeObserver (this);
		itsModel = aModel;
		if (itsModel != null) itsModel.addObserver (this);
		render ();
	}

	/**
	 * Overriden so as to update when size changes.
	 */
	public boolean setSize (Tuple3f aSize)
	{
		if (! super.setSize(aSize))
		{
			render();
			return true;
		}
		else return false;
	}
}
