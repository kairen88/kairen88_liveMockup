/*
 * Created on Dec 21, 2004
 */
package zz.waltz.api;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import zz.utils.ListMap;
import zz.utils.properties.IListProperty;
import zz.waltz.api.action.ButtonActionStyle;
import zz.waltz.api.property.ButtonPropertyStyle;
import zz.waltz.api.property.CheckBoxPropertyStyle;
import zz.waltz.api.property.SpinnerPropertyStyle;
import zz.waltz.api.property.TextComboPropertyStyle;
import zz.waltz.api.property.TextFieldPropertyStyle;
import zz.waltz.impl.SwingWrapperCanvas;

/**
 * Documentation pending. Subclass and override {@link #render(IWaltzCanvas)}
 * 
 * @author gpothier
 */
public class WaltzComponent
{
	/**
	 * Frequently used action style constant.
	 */
	protected static final ButtonActionStyle BUTTON_ACTION = new ButtonActionStyle();

	/**
	 * Frequently used property style constant.
	 */
	protected static final ButtonPropertyStyle BUTTON_PROPERTY = new ButtonPropertyStyle();


	/**
	 * Frequently used property style constant.
	 */
	protected static final CheckBoxPropertyStyle CHECKBOX_PROPERTY = CHECKBOX_PROPERTY(false);

	/**
	 * Frequently used property style pseudo-constant.
	 */
	protected static final CheckBoxPropertyStyle CHECKBOX_PROPERTY(boolean aShowText)
	{
		return new CheckBoxPropertyStyle(aShowText);
	}

	/**
	 * Frequently used property style pseudo-constant.
	 */
	protected static final SpinnerPropertyStyle<Integer> SPINNER_PROPERTY(int aMinimum, int aMaximum, int aStep)
	{
		SpinnerModel theModel = new SpinnerNumberModel(aMinimum, aMinimum, aMaximum, aStep);
		return new SpinnerPropertyStyle<Integer>(theModel);
	}
	
	/**
	 * Frequently used property style pseudo-constant.
	 */
	protected static final SpinnerPropertyStyle<Date> SPINNER_PROPERTY(Date aMinimum, Date aMaximum, int aCalendarField)
	{
		SpinnerModel theModel = new SpinnerDateModel(new Date(), aMinimum, aMaximum, aCalendarField)
		{
			public void setCalendarField(int calendarField)
			{
			}
		};
		return new SpinnerPropertyStyle<Date>(theModel);
	}
	
	/**
	 * Frequently used property style pseudo-constant.
	 */
	protected static final TextFieldPropertyStyle TEXTFIELD_PROPERTY(int aColumnsCount)
	{
		return new TextFieldPropertyStyle(aColumnsCount);
	}

	/**
	 * Frequently used property style pseudo-constant.
	 */
	protected static final TextComboPropertyStyle TEXTCOMBO_PROPERTY(IListProperty<String> aChoices)
	{
		return new TextComboPropertyStyle(aChoices);
	}


	private boolean itsValid = false;

	private JComponent itsComponent = new MyPanel();

	private List<IWaltzListener> itsGlobalListeners;

	private ListMap<Topic, IWaltzListener> itsTopicListeners;

	/**
	 * Publishes a data so that all registered listeners receive it.
	 */
	protected void publish(Topic aTopic, Object aData)
	{
		if (itsGlobalListeners != null) for (IWaltzListener theListener : itsGlobalListeners)
		{
			theListener.receive(aTopic, aData);
		}

		if (itsTopicListeners != null) for (IWaltzListener theListener : itsTopicListeners.iterable(aTopic))
		{
			theListener.receive(aTopic, aData);
		}
	}

	public void addListener(IWaltzListener aListener)
	{
		if (itsGlobalListeners == null) itsGlobalListeners = new ArrayList<IWaltzListener>();
		itsGlobalListeners.add(aListener);
	}

	public void removeListener(IWaltzListener aListener)
	{
		if (itsGlobalListeners != null) itsGlobalListeners.remove(aListener);
	}

	public void addListener(Topic aTopic, IWaltzListener aListener)
	{
		if (itsTopicListeners == null) itsTopicListeners = new ListMap<Topic, IWaltzListener>();
		itsTopicListeners.add(aTopic, aListener);
	}

	public void removeListener(Topic aTopic, IWaltzListener aListener)
	{
		if (itsTopicListeners != null) itsTopicListeners.remove(aTopic, aListener);
	}

	/**
	 * Returns the Swing component that represents this Waltz component.
	 */
	public JComponent getSwingComponent()
	{
		return itsComponent;
	}

	/**
	 * Renders this component onto the specified canvas.
	 */
	protected void render(IWaltzCanvas aCanvas)
	{
	}

	/**
	 * Marks this component as invalid. it will be rerendered during the next
	 * validation
	 */
	public void invalidate()
	{
		itsValid = false;
	}

	/**
	 * Ensures that this component is valid.
	 */
	private void validate()
	{
		if (itsValid == false)
		{
			itsComponent.removeAll();
			render(new SwingWrapperCanvas(itsComponent));
			itsComponent.revalidate();
			itsComponent.repaint();
			itsValid = true;
		}
	}

	/**
	 * Repaints the component.
	 */
	protected void repaint()
	{
		itsComponent.repaint();
	}

	/**
	 * Schedules repainting the component.
	 */
	protected void repaint(long aDelay)
	{
		itsComponent.repaint(aDelay);
	}

	private class MyPanel extends JPanel
	{
		public void paint(Graphics aG)
		{
			WaltzComponent.this.validate();
			super.paint(aG);
		}

		public Dimension getPreferredSize()
		{
			WaltzComponent.this.validate();
			return super.getPreferredSize();
		}

		public Dimension getMinimumSize()
		{
			WaltzComponent.this.validate();
			return super.getMinimumSize();
		}

		public Dimension getMaximumSize()
		{
			WaltzComponent.this.validate();
			return super.getMaximumSize();
		}
	}

}
