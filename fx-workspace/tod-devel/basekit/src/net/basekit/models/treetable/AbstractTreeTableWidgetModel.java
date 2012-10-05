/*
 * Created on Mar 22, 2004
 */
package net.basekit.models.treetable;

import net.basekit.models.AbstractWidgetModel;
import net.basekit.models.table.messages.TableStructureChangedMessage;

/**
 * Provides Firing methods
 * @author gpothier
 */
public abstract class AbstractTreeTableWidgetModel extends AbstractWidgetModel implements TreeTableWidgetModel
{
	protected void fireTableStructureChanged ()
	{
		fire (new TableStructureChangedMessage (this));
	}
}
