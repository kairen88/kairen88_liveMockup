/*
 * Created on Mar 17, 2004
 */
package net.basekit.models.label;

import net.basekit.models.AbstractWidgetModel;
import net.basekit.models.label.messages.LabelDataChanged;

/**
 * Provides support for firing messages.
 * @author gpothier
 */
public abstract class AbstractLabelWidgetModel extends AbstractWidgetModel implements LabelWidgetModel
{
	protected void fireLabelDataChanged ()
	{
		fire (new LabelDataChanged (this));
	}

}
