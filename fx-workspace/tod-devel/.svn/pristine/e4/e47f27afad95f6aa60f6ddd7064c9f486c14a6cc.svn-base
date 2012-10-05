/*
 * Created on Mar 22, 2004
 */
package net.basekit.widgets.table;

import net.basekit.layoutdelegates.AxisLayoutDelegate;
import net.basekit.layoutdelegates.SharpLayoutDelegate;
import net.basekit.models.table.TableColumnWidgetModel;
import net.basekit.models.table.TableWidgetModel;
import net.basekit.widgets.Widget;
import net.basekit.widgets.label.LabelWidget;
import net.basekit.widgets.list.ListWidget;

/**
 * A widget that display data orginized in rows and columns
 * @see net.basekit.models.table.TableWidgetModel
 * @author gpothier
 */
public class TableWidget extends Widget
{
	private TableWidgetModel itsModel;
	
	private ListWidget itsListWidget; 
	private TableListElementRenderer itsListElementRenderer = new TableListElementRenderer ();

	private Widget itsTitleWidget;
	
	public TableWidget (TableWidgetModel aModel)
	{
		createUI();
		setModel(aModel);
	}
	
	private void createUI ()
	{
		setLayoutDelegate(new SharpLayoutDelegate ());
		
		itsListWidget = new ListWidget ();
		itsListWidget.setElementRenderer(itsListElementRenderer);
		
		itsTitleWidget = new Widget ();
		itsTitleWidget.setLayoutDelegate(new TableLayoutDelegate (this));
		
		addWidget(itsListWidget, SharpLayoutDelegate.C);	
		addWidget(itsTitleWidget, SharpLayoutDelegate.N);
	}
	
	/**
	 * Extract titles from the model and updates the title widget.
	 */
	private void buildTitle ()
	{
		itsTitleWidget.clearWidgets();
		
		int theCount = getModel().getColumnCount();
		for (int i=0;i<theCount;i++)
		{
			TableColumnWidgetModel theColumn = getModel ().getColumn(i);
			LabelWidget theLabelWidget = new LabelWidget (theColumn.getHeaderModel());
			itsTitleWidget.addWidget(theLabelWidget);
		}
		redraw();
	}

	public TableWidgetModel getModel ()
	{
		return itsModel;
	}
	
	public void setModel (TableWidgetModel aModel)
	{
		if (itsModel != null) itsModel.removeObserver(this);
		itsModel = aModel;
		if (itsModel != null) 
		{
			itsModel.addObserver(this);
			itsListElementRenderer.setTableWidget(this);
			itsListWidget.setModel(itsModel.getContent());
			buildTitle();
		}
	}
	
}
