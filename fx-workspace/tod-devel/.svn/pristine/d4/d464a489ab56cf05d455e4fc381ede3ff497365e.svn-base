/*
 * Created on Mar 22, 2004
 */
package net.basekit.models.table.messages;

import net.basekit.messages.WidgetModelMessage;
import net.basekit.models.WidgetModel;
import net.basekit.models.table.TableColumnWidgetModel;
import net.basekit.models.table.TableWidgetModel;

/**
 * Sent when a column has been resized.
 * @author gpothier
 */
public class TableColumnResizedMessage extends WidgetModelMessage
{
	public TableColumnResizedMessage (TableColumnWidgetModel aColumn)
	{
		super (aColumn);
	}
	
	public WidgetModel getTableColumnWidgetModel ()
	{
		return getSource();
	}
}
