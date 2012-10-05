/*
 * Created on Mar 22, 2004
 */
package net.basekit.models.table;

import net.basekit.models.WidgetModel;
import net.basekit.models.label.LabelWidgetModel;
import net.basekit.widgets.list.ListElementRenderer;

/**
 * The model that describes a column in a table. 
 * @author gpothier
 */
public interface TableColumnWidgetModel extends WidgetModel
{
	/**
	 * @return The width of this column
	 */
	public float getWidth ();
	
	/**
	 * Sets the width of this column.
	 */
	public void setWidth (float aWidth);
	
	/**
	 * Returns a label model that will be used to represent the header of the column. 
	 */
	public LabelWidgetModel getHeaderModel ();
	
	/**
	 * Returns a renderer that will create widgets for table data in this column.
	 */
	public ListElementRenderer getElementRenderer ();
}
