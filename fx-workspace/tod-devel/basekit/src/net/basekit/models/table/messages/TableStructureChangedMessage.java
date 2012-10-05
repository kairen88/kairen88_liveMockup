/*
 * Created on Mar 22, 2004
 */
package net.basekit.models.table.messages;

import net.basekit.messages.WidgetModelMessage;
import net.basekit.models.WidgetModel;
import net.basekit.models.table.TableWidgetModel;
import net.basekit.models.table.TabularWidgetModel;

/**
 * Sent when columns were added, removed or changed, or when the content model was changed
 * (ie. replaced; when the content of a content model changes, this message is not fired)
 * @author gpothier
 */
public class TableStructureChangedMessage extends WidgetModelMessage
{

	public TableStructureChangedMessage (TabularWidgetModel aSource)
	{
		super(aSource);
	}
	
	public TabularWidgetModel getTableWidgetModel ()
	{
		return (TabularWidgetModel) getSource();
	}

}
