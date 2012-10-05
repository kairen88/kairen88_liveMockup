/*
 * Created on Mar 3, 2004
 */
package net.basekit.widgets;

import javax.vecmath.Vector3f;

import net.basekit.layoutdelegates.LayoutDelegate;

/**
 * A widget that displays a popup when requested. The popup will always appear
 * next to this widget. This widget also has a fixed content called the hook.
 * Subclasses can veto request to show and hide the popup by overriding
 * {@link #canHidePopup()} and {@link #canShowPopup()}
 * <p>
 * Warning (pickable popup).
 * The pickable state of a popup must be definitively set before any call to showPopup or hidePopup.
 * While the popup is not displayed, it is not pickable either.
 * @author gpothier
 */
public abstract class PopupWidget extends Widget
{
	private Widget itsHook;
	private Widget itsPopup;

	private boolean itsPopupShown = false;
	private boolean itsPopupPickable;

	protected PopupWidget ()
	{
		this (null, null);
	}

	protected PopupWidget (Widget aHook, Widget aPopup)
	{
		setHook (aHook);
		setPopup (aPopup);
		setLayoutDelegate (new PopupLayoutDelegate ());
	}

	public final Widget getPopup ()
	{
		return itsPopup;
	}
	
	public void setPopup (Widget aPopup)
	{
		if (itsPopup != null) removeWidget(itsPopup);
		itsPopup = aPopup;
		if (itsPopup != null)
		{
			itsPopup.setRenderable (itsPopupShown);
			itsPopupPickable = itsPopup.getPickable ();
			if (! itsPopupShown) itsPopup.setPickable (false);
			addWidget(itsPopup);
		}
	}

	public Widget getHook ()
	{
		return itsHook;
	}

	public void setHook (Widget aHook)
	{
		if (itsHook != null) removeWidget (itsHook);
		itsHook = aHook;
		if (itsHook != null) addWidget (itsHook);
	}

	/**
	 * Displays the popup widget next to this widget.
	 */
	public final void showPopup ()
	{
		if (itsPopup != null && ! itsPopupShown && canShowPopup())
		{
			itsPopup.setPosition(new Vector3f (0, getSize().y, 0));
			itsPopup.setSize (itsPopup.getPreferredSize ());
			itsPopup.setRenderable (true);
			itsPopup.setPickable (itsPopupPickable);
			itsPopupShown = true;
		}
	}

	/**
	 * Hides the popup widget.
	 */
	public final void hidePopup ()
	{
		if (itsPopup != null && itsPopupShown && canHidePopup())
		{
			itsPopup.setRenderable (false);
			itsPopup.setPickable (false);
			itsPopupShown = false;
		}
	}
	
	/**
	 * If the popup is shown, hide it; if it is hidden, show it.
	 *
	 */
	public final void togglePopup ()
	{
		if (itsPopupShown) hidePopup();
		else showPopup();
	}
	
	/**
	 * Called when the popup has been shown.
	 * Pseudo abstract.
	 */
	protected void popupShown ()
	{
	}
	
	/**
	 * Indicates whether a request to show the popup should be fulffilled.
	 * @return By default returns true;
	 */
	protected boolean canShowPopup ()
	{
		return true;
	}
	
	/**
	 * Called when the popup has been hidden.
	 * Pseudo abstract.
	 */
	protected void popupHidden ()
	{
	}
	
	/**
	 * Indicates whether a request to hide the popup should be fulffilled.
	 * @return By default returns true;
	 */
	protected boolean canHidePopup ()
	{
		return true;
	}

	private class PopupLayoutDelegate extends LayoutDelegate
	{
		public void layout ()
		{
			itsHook.setPosition (new Vector3f (0, 0, 0));
			itsHook.setSize (getWidget ().getSize ());
		}

		public Vector3f computeMinimumSize ()
		{
			return itsHook.getMinimumSize ();
		}

		public Vector3f computeMaximumSize ()
		{
			return itsHook.getMaximumSize ();
		}

		public Vector3f computePreferredSize ()
		{
			return itsHook.getPreferredSize ();
		}

	}
}
