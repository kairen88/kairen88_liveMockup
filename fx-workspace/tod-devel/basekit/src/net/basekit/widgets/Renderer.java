/*
 * Created on Mar 6, 2004
 */
package net.basekit.widgets;

/**
 * Interface used by {@link net.basekit.widgets.RenderedWidget}.
 * Whether or not a renderer can be shared between several widgets
 * depends on each particular renderer.
 * @author gpothier
 */
public interface Renderer
{
	/**
	 * Renders into the specified widget.
	 */
	public void render (RenderedWidget aRenderedWidget);
	
}
