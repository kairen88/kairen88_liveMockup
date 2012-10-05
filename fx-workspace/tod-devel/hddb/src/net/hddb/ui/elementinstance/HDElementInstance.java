/*
 * Created on Feb 24, 2004
 */
package net.hddb.ui.elementinstance;

import java.util.Collection;
import java.util.List;

import net.basekit.layoutdelegates.SharpLayoutDelegate;
import net.basekit.utils.Logger;
import net.basekit.widgets.Widget;
import net.basekit.widgets.combo.ComboWidget;
import net.basekit.widgets.label.LabelWidget;
import net.hddb.views.HDView;
import net.hddb.views.HDViewChain;

/**
 * Instance of a particular element. The same element can have several instances,
 * ie. it can appear many times in a given configuration.
 * <p>
 * An instance knows which view of the object it should display. It also provides
 * means for changing the current view.
 *
 * @author gpothier
 */
public class HDElementInstance extends Widget
{
	public static final Logger.Category LOG_HDEI = new Logger.Category ("HDEI");

	private Object itsElement;
	private HDView itsCurrentView;
	private Collection itsAvailableViewChains;

	private ViewChainSelector itsViewChainSelector;

	public HDElementInstance(HDEIContainer aContainer, Object aElement)
	{
		assert aElement != null;
		itsAvailableViewChains = HDEIUtils.findAvailableViewChains(aContainer, aElement);
		if (itsAvailableViewChains.size() == 0) throw new RuntimeException ("No view chain for "+aElement);

		assert Logger.log (LOG_HDEI, "creating hdei ("+this+") for "+aElement);
		itsElement = aElement;

		createUI ();

		HDViewChain theInitialViewChain = (HDViewChain) itsAvailableViewChains.iterator ().next ();
		itsViewChainSelector.setSelectedViewChain(theInitialViewChain);
	}

	/**
	 * Returns the {@link HDEIContainer} that contains, possibly indirectly, this element instance.
	 */
	public HDEIContainer getHDEIContainer ()
	{
		Widget theCurrentWidget = getParentWidget ();
		while (theCurrentWidget != null)
		{
			if (theCurrentWidget instanceof HDEIContainer) return (HDEIContainer) theCurrentWidget;
			theCurrentWidget = theCurrentWidget.getParentWidget ();
		}
		return null;
	}

	private void createUI()
	{
		setPacked (true);
		setLayoutDelegate(new SharpLayoutDelegate ());

		itsViewChainSelector = new ViewChainSelector (this);
		addWidget (itsViewChainSelector, SharpLayoutDelegate.NE);

		addWidget (new LabelWidget ("EI"), SharpLayoutDelegate.N);
	}

	public Object getElement ()
	{
		return itsElement;
	}
	
	public void setView (HDView aView)
	{
		if (itsCurrentView != null) removeWidget(itsCurrentView);
		itsCurrentView = aView;
		if (itsCurrentView != null) addWidget(itsCurrentView, SharpLayoutDelegate.C);

		assert Logger.log (LOG_HDEI, "Setting view "+itsCurrentView+" for "+this);
	}

	private HDView getCurrentView ()
	{
		return itsCurrentView;
	}

	/**
	 * Delegates to the current view's adapter.
	 * @see net.hddb.adapters.HDAdapter#getOutboundReferences()}
	 */
	public List getOutboundReferences ()
	{
		return getCurrentView().getModel().getOutboundReferences();
	}

	public Collection getAvailableViewChains ()
	{
		return itsAvailableViewChains;
	}
}
