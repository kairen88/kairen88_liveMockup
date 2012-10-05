/*
 * Created on Mar 24, 2004
 */
package net.basekit.widgets.treetable;

import net.basekit.Message;
import net.basekit.Observer;
import net.basekit.layoutdelegates.SharpLayoutDelegate;
import net.basekit.models.label.LabelWidgetModel;
import net.basekit.models.list.ListContentWidgetModel;
import net.basekit.models.selection.SelectionWidgetModel;
import net.basekit.models.table.AbstractTableColumnWidgetModel;
import net.basekit.models.table.AbstractTableWidgetModel;
import net.basekit.models.table.TableColumnWidgetModel;
import net.basekit.models.treetable.TreeTableWidgetModel;
import net.basekit.widgets.Widget;
import net.basekit.widgets.list.LabelElementRenderer;
import net.basekit.widgets.list.ListElementRenderer;
import net.basekit.widgets.table.TableWidget;
import net.basekit.widgets.tree.TreeListElementRenderer;
import net.basekit.widgets.tree.TreeListWrapperModel;

/**
 * A mix betwen a tree and a table: the first column represents a tree.
 * @author gpothier
 */
public class TreeTableWidget extends Widget
{
	private final TreeTableWrapperModel itsTreeTableWrapperModel = new TreeTableWrapperModel ();
	private TreeTableWidgetModel itsTreeTableModel;
	private SelectionWidgetModel itsSelectionModel;
	
	/**
	 * The renderer that creates tree node labels. It does not have
	 * to create the visual representation of the tree structure (ie it shouldn't bother
	 * about depth)
	 */
	private ListElementRenderer itsNodeElementRenderer;

	/**
	 * The underlying table widget. The treetable widget simply provides custom model and renderers
	 * to a table widget.
	 */
	private TableWidget itsTableWidget;
	private TreeListElementRenderer itsTreeListElementRenderer;
	
	public TreeTableWidget (TreeTableWidgetModel aModel)
	{
		setTreeTableModel(aModel);
		setNodeElementRenderer(new LabelElementRenderer ());
		createUI();
	}

	private void createUI ()
	{
		setLayoutDelegate(new SharpLayoutDelegate  ());
		itsTreeListElementRenderer = new TreeListElementRenderer ();
		itsTreeListElementRenderer.setTreeModel(getTreeTableModel().getTreeModel());
		itsTreeListElementRenderer.setNodeElementRenderer(getNodeElementRenderer());
		itsTableWidget = new TableWidget (itsTreeTableWrapperModel);
				
		addWidget(itsTableWidget, SharpLayoutDelegate.C);
	}
	
	public Object getRootNode ()
	{
		return getTreeTableModel().getTreeModel().getRootNode();
	}

	public TreeTableWidgetModel getTreeTableModel ()
	{
		return itsTreeTableModel;
	}
	
	public void setTreeTableModel (TreeTableWidgetModel aModel)
	{
		if (itsTreeTableModel != null) itsTreeTableModel.getTreeModel().removeObserver(itsTreeTableWrapperModel.itsListModel);
		itsTreeTableModel = aModel;
		if (itsTreeTableModel != null) 
		{
			itsTreeTableModel.getTreeModel().addObserver(itsTreeTableWrapperModel.itsListModel);
			if (itsTreeListElementRenderer != null) itsTreeListElementRenderer.setTreeModel(getTreeTableModel().getTreeModel());
			itsTreeTableWrapperModel.setTreeTableModel(itsTreeTableModel);
		}
		
	}

	public ListElementRenderer getNodeElementRenderer ()
	{
		return itsNodeElementRenderer;
	}
	
	public void setNodeElementRenderer (ListElementRenderer aNodeElementRenderer)
	{
		itsNodeElementRenderer = aNodeElementRenderer;
		if (itsTreeListElementRenderer != null) itsTreeListElementRenderer.setNodeElementRenderer(aNodeElementRenderer);
	}
	

	private class TreeTableWrapperModel extends AbstractTableWidgetModel implements Observer
	{
		private TreeTableWidgetModel itsTreeTableModel;
		private TreeListWrapperModel itsListModel = new TreeListWrapperModel ();
		private FirstColumnAdapter itsFirstColumnAdapter = new FirstColumnAdapter ();

		public TreeTableWidgetModel getTreeTableModel ()
		{
			return itsTreeTableModel;
		}
		
		public void setTreeTableModel (TreeTableWidgetModel aModel)
		{
			itsTreeTableModel = aModel;
			if (itsTreeTableModel != null) itsListModel.setModel(itsTreeTableModel.getTreeModel());
		}

		public void processMessage (Message aMessage)
		{
		}

		public ListContentWidgetModel getContent ()
		{
			return itsListModel;
		}

		public int getColumnCount ()
		{
			return getTreeTableModel().getColumnCount();
		}

		public TableColumnWidgetModel getColumn (int aIndex)
		{
			if (aIndex > 0)
			{
				return getTreeTableModel().getColumn(aIndex);
			}
			else
			{
				itsFirstColumnAdapter.setSourceColumn(getTreeTableModel().getColumn(aIndex));
				return itsFirstColumnAdapter;
			}
		}
		
	}
	
	private class FirstColumnAdapter extends AbstractTableColumnWidgetModel
	{
		private TableColumnWidgetModel itsSourceColumn;
		
		public void setSourceColumn (TableColumnWidgetModel aSourceColumn)
		{
			itsSourceColumn = aSourceColumn;
			itsTreeListElementRenderer.setNodeElementRenderer(itsSourceColumn.getElementRenderer());
		}

		public float getWidth ()
		{
			return itsSourceColumn.getWidth();
		}

		public void setWidth (float aWidth)
		{
			itsSourceColumn.setWidth(aWidth);
		}

		public LabelWidgetModel getHeaderModel ()
		{
			return itsSourceColumn.getHeaderModel();
		}

		public ListElementRenderer getElementRenderer ()
		{
			return itsTreeListElementRenderer;
		}
	}
}
