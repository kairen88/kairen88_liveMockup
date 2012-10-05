/**
 * Created by IntelliJ IDEA.
 * User: g1
 * Date: 9 mars 2004
 * Time: 20:28:06
 * To change this template use File | Settings | File Templates.
 */
package net.basekit.widgets.list;

/**
 * Interface for elements created by {@link ListElementRenderer#createWidget(java.lang.Object, int)}.
 * This interface should be implemented by subclasses of
 * {@link net.basekit.widgets.Widget} only.
 * TODO: implement as a trait.
 * @author gpothier
 */
public interface ListElementWidget
{
	/**
	 * Returns the element that is represented by this widget.
	 */
	public Object getElement ();

	/**
	 * Returns the index of the element in the list;
	 */ 
	public int getIndex ();
}
