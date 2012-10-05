/*
 * Created on Mar 6, 2004
 */
package net.basekit.widgets.list;


/**
 * Factory that creates a widget for an element in the 
 * {@link net.basekit.widgets.list.ListWidget}
 * @author gpothier
 */
public interface ListElementRenderer
{
	/**
	 * Creates a widget that represents an element from the list.
	 * @param aElement The element
	 * @param aIndex The index of the element.
	 */
	public ListElementWidget createWidget (Object aElement, int aIndex);
	
	/**
	 * Changes a widgets visual attributes according to flags that indicate its state within the list.
	 * @param aWidget A widget previously created by {@link #createWidget(Object,int)}
	 * @param aSelected Whether the element that corresponds to this widget is currently selected
	 * @param aFocused Whether the element that corresponds to this widget is currently focused
	 */
	public void updateWidget (ListElementWidget aWidget, boolean aSelected, boolean aFocused);
}
