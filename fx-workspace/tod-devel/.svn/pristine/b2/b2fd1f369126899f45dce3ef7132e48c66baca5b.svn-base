/*
 * Created on Mar 6, 2004
 */
package net.basekit.widgets;

/**
 * A renderer that cannot be shared between several rendered widgets.
 * It dispatches calls to {@link net.basekit.widgets.Renderer#render(RenderedWidget)}
 * to an init and update method.
 * @author gpothier
 */
public abstract class IndividualRenderer implements Renderer
{
	private RenderedWidget itsRenderedWidget;

	public RenderedWidget getRenderedWidget ()
	{
		return itsRenderedWidget;
	}
	
	public void render (RenderedWidget aRenderedWidget)
	{
		if (itsRenderedWidget == null) 
		{
			itsRenderedWidget = aRenderedWidget;
			itsRenderedWidget.clearAdditionalContent();
			itsRenderedWidget.clearWidgets();
			initRenderer ();
		}
		else assert itsRenderedWidget == aRenderedWidget; //We do not accept sharing.
		updateRenderer();
	}
	
	/**
	 * Called once when the renderer is first invoked. 
	 * The widget emptid before this method is called.
	 */
	public abstract void initRenderer ();
	
	/**
	 * Called each time the renderer is invoked.
	 */
	public abstract void updateRenderer ();
}
