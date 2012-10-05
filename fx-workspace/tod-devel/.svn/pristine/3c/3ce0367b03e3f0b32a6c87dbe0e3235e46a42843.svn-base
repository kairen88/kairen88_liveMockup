/*
 * Created on Jan 10, 2005
 */
package zz.csg.impl.edition;

/**
 * Interface for editors of graphic objects.
 * This refefers to standalone editors, that are not automatically
 * handled by editor tools.
 * @see zz.csg.api.edition.IGraphicObjectController#startEditing()
 * @author gpothier
 */
public interface IGraphicObjectEditor
{
	/**
	 * Activates a standalone edition mode for this controller.
	 * This method is called when the object is double-clicked. 
	 * It can be used for instance for text controllers to overlay
	 * a text editor to graphic object.
	 */
	public void startEditing();
	
	/**
	 * Exist the edition mode.
	 * Note that the edition mode can also exit on its own, it
	 * is not necessary to wait for this method to be called
	 * to exit edition mode.
	 */
	public void stopEditing();

}
