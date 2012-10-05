/*
 * Created on Mar 6, 2004
 */
package net.basekit.widgets;

/**
 * A widget that delegates its rendering to a {@link net.basekit.widgets.Renderer}.
 * TODO: implement as a trait/aspect when possible
 * @author gpothier
 */
public class RenderedWidget extends Widget
{
	private Renderer itsRenderer;
	
	public Renderer getRenderer ()
	{
		return itsRenderer;
	}
	
	public void setRenderer (Renderer aRenderer)
	{
		clearAdditionalContent();
		itsRenderer = aRenderer;
		render();
	}
	
	/**
	 * Invokes the render delegate and calls {@link Widget#redraw()}. 
	 * This is typically used to update the ui of a rendered widget.
	 */
	protected void render ()
	{	
		if (itsRenderer != null) 
		{
			itsRenderer.render (this);
			redraw();
		}
	}
}
