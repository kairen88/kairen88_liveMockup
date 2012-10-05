/*
 * Created on Mar 22, 2004
 */
package net.basekit.models.table;

import net.basekit.models.AbstractWidgetModel;
import net.basekit.models.table.messages.TableColumnResizedMessage;

/**
 * Provides firing methods.
 * @author gpothier
 */
public abstract class AbstractTableColumnWidgetModel extends AbstractWidgetModel implements TableColumnWidgetModel
{
	protected void fireColumnResized ()
	{
		fire (new TableColumnResizedMessage (this));
	}
}
