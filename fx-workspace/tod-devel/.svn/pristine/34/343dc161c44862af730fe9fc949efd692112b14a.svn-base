/*
 * Created on Mar 22, 2004
 */
package net.basekit.widgets.table;

import net.basekit.models.table.TableColumnWidgetModel;
import net.basekit.models.table.TableWidgetModel;
import net.basekit.widgets.Widget;
import net.basekit.widgets.list.ListElementRenderer;
import net.basekit.widgets.list.ListElementWidget;

/**
 * The table uses a list to display its content.
 * This element renderer displays client-created elements in a tabular fashion.
 * <p>
 * This object needs to know the model of the table.
 * Use {@link #setTableWidget(TableWidgetModel)}.  
 * @author gpothier
 */
public class TableListElementRenderer implements ListElementRenderer
{
	private TableWidget itsTableWidget;

	public TableWidgetModel getTableWidgetModel ()
	{
		return itsTableWidget.getModel();
	}
	
	public void setTableWidget (TableWidget aTableWidget)
	{
		itsTableWidget = aTableWidget;
	}

	public ListElementWidget createWidget (Object aElement, int aIndex)
	{
		return new LineElementWidget (aElement, aIndex);
	}

	public void updateWidget (ListElementWidget aWidget, boolean aSelected, boolean aFocused)
	{
		LineElementWidget theWidget = (LineElementWidget) aWidget;
		theWidget.updateWidget(aSelected, aFocused);
	}
	
	private class LineElementWidget extends Widget implements ListElementWidget 
	{
		private Object itsElement;
		private int itsIndex;
		
		public LineElementWidget (Object aElement, int aIndex)
		{
			itsElement = aElement;
			itsIndex = aIndex;
			createUI();
		}
		
		private void createUI ()
		{
			setPickingAware(false);
			setLayoutDelegate(new TableLayoutDelegate(itsTableWidget));
			int theCount = getTableWidgetModel ().getColumnCount();
			for (int i = 0; i < theCount; i++)
			{
				TableColumnWidgetModel theColumn = getTableWidgetModel().getColumn(i);
				ListElementRenderer theColumnRenderer = theColumn.getElementRenderer();
				ListElementWidget theWidget = theColumnRenderer.createWidget(itsElement, getIndex());
				addWidget((Widget) theWidget);
			}
		}

		public Object getElement ()
		{
			return itsElement;
		}
		
		public int getIndex ()
		{
			return itsIndex;
		}

		public void updateWidget (boolean aSelected, boolean aFocused)
		{
			int theCount = getTableWidgetModel ().getColumnCount();
			for (int i = 0; i < theCount; i++)
			{
				TableColumnWidgetModel theColumn = getTableWidgetModel().getColumn(i);
				ListElementRenderer theColumnRenderer = theColumn.getElementRenderer();
				ListElementWidget theWidget = (ListElementWidget) getWidget(i);
				theColumnRenderer.updateWidget(theWidget, aSelected, aFocused);
			}
		}

	}
}
