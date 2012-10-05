/*
 * Created on Oct 12, 2005
 */
package zz.csg.api.layout;

import zz.csg.api.IGraphicContainer;

/**
 * Interface for layout managers. 
 * @see zz.csg.api.IGraphicContainer#setLayoutManager(ILayoutManager)
 * @author gpothier
 */
public interface ILayoutManager
{
	public void install(IGraphicContainer aContainer);
	public void uninstall();
	
	/**
	 * Ensures that this layout manager has been applied to its
	 * container; if not, layout is performed now.
	 */
	public void ensureLayout();
}
