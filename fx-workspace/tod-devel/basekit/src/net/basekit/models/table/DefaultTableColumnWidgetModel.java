/*
 * Created on Mar 22, 2004
 */
package net.basekit.models.table;

import zz.utils.Formatter;
import net.basekit.models.label.DefaultLabelWidgetModel;
import net.basekit.models.label.LabelWidgetModel;
import net.basekit.widgets.list.LabelElementRenderer;
import net.basekit.widgets.list.ListElementRenderer;

/**
 * Default implementation of {@link net.basekit.models.table.TableColumnWidgetModel}
 * @author gpothier
 */
public class DefaultTableColumnWidgetModel extends AbstractTableColumnWidgetModel
{
	private float itsWidth;
	private LabelWidgetModel itsHeaderModel;
	private ListElementRenderer itsElementRenderer;

	/**
	 * Constructs the column model with a default header model representing the specified title.
	 */
	public DefaultTableColumnWidgetModel (float aWidth, String aTitle, ListElementRenderer aElementRenderer)
	{
		setWidth(aWidth);
		setHeaderModel(new DefaultLabelWidgetModel (aTitle));
		setElementRenderer(aElementRenderer);
	}

	/**
	 * Instantiates a new column with a label renderer using the specified formatter.
	 */
	public DefaultTableColumnWidgetModel (float aWidth, String aTitle, Formatter aFormatter)
	{
		this (aWidth, aTitle, new LabelElementRenderer (aFormatter));
	}

	public float getWidth ()
	{
		return itsWidth;
	}
	
	public void setWidth (float aWidth)
	{
		itsWidth = aWidth;
		fireColumnResized();
	}
	
	public ListElementRenderer getElementRenderer ()
	{
		return itsElementRenderer;
	}
	
	public void setElementRenderer (ListElementRenderer aElementRenderer)
	{
		itsElementRenderer = aElementRenderer;
	}
	
	public LabelWidgetModel getHeaderModel ()
	{
		return itsHeaderModel;
	}
	
	public void setHeaderModel (LabelWidgetModel aHeaderModel)
	{
		itsHeaderModel = aHeaderModel;
	}
}
