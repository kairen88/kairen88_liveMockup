/*
 * Created on Mar 22, 2004
 */
package net.basekit.models.table;

import zz.utils.FireableTreeModel;
import net.basekit.models.list.ListContentWidgetModel;

/**
 * Default implementation of {@link net.basekit.models.table.TableWidgetModel}
 * @author gpothier
 */
public class DefaultTableWidgetModel extends AbstractTableWidgetModel
{
	private TableColumnWidgetModel[] itsColumns;
	private ListContentWidgetModel itsContent;
	
	public DefaultTableWidgetModel (TableColumnWidgetModel[] aColumns,
			ListContentWidgetModel aContent)
	{
		setColumns(aColumns);
		setContent(aContent);
	}
	
	public int getColumnCount ()
	{
		return itsColumns.length;
	}

	public TableColumnWidgetModel getColumn (int aIndex)
	{
		return itsColumns[aIndex];
	}
	
	
	public void setColumns (TableColumnWidgetModel[] aColumns)
	{
		itsColumns = aColumns;
		fireTableStructureChanged();
	}
	
	public ListContentWidgetModel getContent ()
	{
		return itsContent;
	}
	
	public void setContent (ListContentWidgetModel aContent)
	{
		itsContent = aContent;
		fireTableStructureChanged();
	}
}
