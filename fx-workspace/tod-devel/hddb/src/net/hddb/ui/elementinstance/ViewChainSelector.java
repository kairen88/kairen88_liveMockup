/*
 * Created on Apr 17, 2004
 */
package net.hddb.ui.elementinstance;

import java.util.ArrayList;

import javax.vecmath.Vector3f;

import net.basekit.Message;
import net.basekit.models.combo.ComboWidgetModel;
import net.basekit.models.combo.DefaultComboWidgetModel;
import net.basekit.models.combo.messages.SelectedItemChangedMessage;
import net.basekit.widgets.combo.ComboWidget;
import net.hddb.views.HDView;
import net.hddb.views.HDViewChain;
import net.hddb.views.ViewFormatter;

/**
 * Permits to select the current view of an
 * {@link net.hddb.ui.world.HDElementInstance}
 * @author gpothier
 */
public class ViewChainSelector extends ComboWidget
{
	private HDElementInstance itsElementInstance;

	public ViewChainSelector (HDElementInstance aElementInstance)
	{
		itsElementInstance = aElementInstance;

		setModel(new DefaultComboWidgetModel (new ArrayList (getElementInstance().getAvailableViewChains())));

		setPreferredSize (new Vector3f (10, 10, 0));
		setFormatter (ViewFormatter.getInstance ());
	}


	public HDElementInstance getElementInstance ()
	{
		return itsElementInstance;
	}

	public void processMessage (Message aMessage)
	{
		if (aMessage instanceof SelectedItemChangedMessage)
		{
			SelectedItemChangedMessage theMessage = (SelectedItemChangedMessage) aMessage;
			if (theMessage.getSource() == getModel())
			{
				HDView theview = getSelectedViewChain().createView(getElementInstance ().getElement());
				getElementInstance().setView(theview);
			}
		}
		super.processMessage(aMessage);
	}

	public HDViewChain getSelectedViewChain ()
	{
		return (HDViewChain) getModel().getSelectedItem();
	}
	
	public void setSelectedViewChain (HDViewChain aViewChain)
	{
		getModel().setSelectedItem(aViewChain);
	}


}
