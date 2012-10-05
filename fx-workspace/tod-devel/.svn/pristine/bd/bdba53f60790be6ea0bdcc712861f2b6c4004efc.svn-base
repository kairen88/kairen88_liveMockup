/*
 * Created on May 29, 2004
 */
package zz.csg.api.edition;

import java.util.List;


/**
 * Provides generic support for controlling (editing) graphic
 * objects. Each graphic object is capable of providing such
 * a controller ({@see com.redcrea.ina.core.graphic.IGraphicObject#getController()}).
 * <p>
 * The controller gives access to various handles that can be used to control
 * properties of the graphic object.
 * @author gpothier
 */
public interface IGraphicObjectController
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
	
	/**
	 * Returns the handle that controls the position of the graphic object.
	 */
	public IHandle getPositionHandle ();
	
	/**
	 * Returns an arbitrary number of handles that permit to control
	 * properties of the graphic object.
	 */
	public List<IHandle> getControlPoints ();
	
	/**
	 * Returns a handle that controls the first extremity of the
	 * graphic object. This is used when adding a graphic object to a container
	 * with a drag mouse gesture: this is the extremity that corresponds to
	 * the point where the mouse is pressed.
	 * <p>
	 * In the case of a rectangular object, this would be the north-west corner.
	 * @see #getExtremity2Handle() 
	 */
	public IHandle getExtremity1Handle ();

	/**
	 * Returns a handle that controls the second extremity of the
	 * graphic object. This is used when adding a graphic object to a container
	 * with a drag mouse gesture: this is the extremity that corresponds to
	 * the point where the mouse is released.
	 * <p>
	 * In the case of a rectangular object, this would be the south-east corner.
	 * @see #getExtremity1Handle() 
	 */
	public IHandle getExtremity2Handle ();
}
