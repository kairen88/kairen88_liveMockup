/*
 * Created on Mar 22, 2004
 */
package net.basekit.widgets.table;

import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import net.basekit.layoutdelegates.LayoutDelegate;
import net.basekit.layoutdelegates.LineLayoutDelegate;
import net.basekit.models.table.TableColumnWidgetModel;
import net.basekit.models.table.TableWidgetModel;
import net.basekit.widgets.Widget;

/**
 * Disposes widgets in a grid. The positions and sizes of the columns
 * are obtained from an external source. 
 * @author gpothier
 */
public class TableLayoutDelegate extends LineLayoutDelegate
{
	private TableWidget itsTableWidget;

	public TableLayoutDelegate (TableWidget aTableWidget)
	{
		itsTableWidget = aTableWidget;
	}
	
	private TableWidgetModel getTableWidgetModel ()
	{
		return itsTableWidget.getModel();
	}

	protected Vector3f computeSize (int aSizeSelector)
	{
		if (getTableWidgetModel () != null) return super.computeSize(aSizeSelector);
		else return new Vector3f ();
	}
	
	public void layout ()
	{
		if (getTableWidgetModel () != null) super.layout();
	}

	protected void advanceLayout (Point3f aPosition, Widget aChildWidget, int aChildIndex, Vector3f aChildSize)
	{
		TableColumnWidgetModel theColumn = getTableWidgetModel ().getColumn(aChildIndex);
		float theWidth = theColumn.getWidth();
		aPosition.x += theWidth;
	}

	protected void growSize (Vector3f aSize, Widget aChild, int aChildIndex, Vector3f aChildSize)
	{
		TableColumnWidgetModel theColumn = getTableWidgetModel ().getColumn(aChildIndex);
		float theWidth = theColumn.getWidth();
		float theMinimumWidth = aChild.getMinimumSize().x;
		if (theWidth < theMinimumWidth) 
		{
			theColumn.setWidth(theMinimumWidth);
			getWidget().invalidateSize();
		}
		else
		{
			aSize.x += theWidth;
		}
		aSize.y = Math.max(aSize.y, aChildSize.y);
		aSize.z = Math.max(aSize.z, aChildSize.z);
	}

	protected void place (Widget aChildWidget, int aChildIndex, Point3f aPosition)
	{
		TableColumnWidgetModel theColumn = getTableWidgetModel ().getColumn(aChildIndex);
		float theWidth = theColumn.getWidth();

		Vector3f theSize = new Vector3f ();
		theSize.x = theWidth;
		theSize.y = getWidget().getSize().y;
		theSize.z = aChildWidget.getPreferredSize().z;
		aChildWidget.setPosition(aPosition);
		aChildWidget.setSize(theSize);
	}
	
	/**
	 * Returns the sum of all column widths
	 */
	private float getTotalWidth ()
	{
		float theWidth = 0;
		TableWidgetModel theModel = getTableWidgetModel();
		if (theModel != null)
		{
			int theCount = theModel.getColumnCount();
			for (int i=0;i<theCount;i++)
			{
				TableColumnWidgetModel theColumn = theModel.getColumn(i);
				theWidth += theColumn.getWidth();
			}
		}
		return theWidth;
	}
	
	public Vector3f computeMinimumSize ()
	{
		Vector3f theSize = super.computeMinimumSize();
		theSize.x = getTotalWidth();
		return theSize;
	}
	
	public Vector3f computePreferredSize ()
	{
		Vector3f theSize = super.computePreferredSize();
		theSize.x = getTotalWidth();
		return theSize;
	}
}
