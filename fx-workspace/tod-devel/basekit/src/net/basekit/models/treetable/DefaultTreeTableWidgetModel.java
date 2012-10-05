/*
 * Created on Mar 22, 2004
 */
package net.basekit.models.treetable;

import net.basekit.models.table.TableColumnWidgetModel;
import net.basekit.models.tree.DefaultTreeWidgetModel;
import net.basekit.models.tree.ExpandableTreeNode;
import net.basekit.models.tree.TreeWidgetModel;

/**
 * Default implementation of {@link net.basekit.models.table.TableWidgetModel}.
 * It is based on an array of columns and a tree model so it is very flexible;
 * there should be no need to create another treetable model.
 * @author gpothier
 */
public class DefaultTreeTableWidgetModel extends AbstractTreeTableWidgetModel
{
	private TableColumnWidgetModel[] itsColumns;
	private TreeWidgetModel itsContent;
	
	public DefaultTreeTableWidgetModel ()
	{		
	}
	
	/**
	 * Creates a treetable model using a default tree model for the specified root.
	 */
	public DefaultTreeTableWidgetModel (TableColumnWidgetModel[] aColumns, ExpandableTreeNode aRoot)
	{
		this (aColumns, new DefaultTreeWidgetModel (aRoot));
	}
	
	public DefaultTreeTableWidgetModel (TableColumnWidgetModel[] aColumns, TreeWidgetModel aContent)
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
	
	public TreeWidgetModel getTreeModel ()
	{
		return itsContent;
	}
	
	public void setContent (TreeWidgetModel aContent)
	{
		itsContent = aContent;
		fireTableStructureChanged();
	}
}
