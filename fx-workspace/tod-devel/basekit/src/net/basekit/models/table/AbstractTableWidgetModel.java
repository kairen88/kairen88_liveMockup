/*
 * Created on Mar 22, 2004
 */
package net.basekit.models.table;

import net.basekit.models.AbstractWidgetModel;
import net.basekit.models.table.messages.TableStructureChangedMessage;

/**
 * Provides Firing methods
 * @author gpothier
 */
public abstract class AbstractTableWidgetModel extends AbstractWidgetModel implements TableWidgetModel
{
	protected void fireTableStructureChanged ()
	{
		fire (new TableStructureChangedMessage (this));
	}
}
