/*
 * Created on Apr 10, 2004
 */
package net.hddb.views.stackframe;

import net.basekit.models.table.DefaultTableColumnWidgetModel;
import net.basekit.models.table.TableColumnWidgetModel;
import net.basekit.models.tree.MappedTreeWidgetModel;
import net.basekit.models.treetable.DefaultTreeTableWidgetModel;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;

import zz.utils.Formatter;

/**
 * 
 * @author gpothier
 */
public class StackFrameModel extends DefaultTreeTableWidgetModel
{
	private IStackFrame itsStackFrame;
	
	public StackFrameModel (IStackFrame aStackFrame)
	{
		itsStackFrame = aStackFrame;
		TableColumnWidgetModel theNameColumn = new DefaultTableColumnWidgetModel (50, "name", new NameFormatter ());
		TableColumnWidgetModel theTypeColumn = new DefaultTableColumnWidgetModel (50, "type", new TypeFormatter ());
		TableColumnWidgetModel theValueColumn = new DefaultTableColumnWidgetModel (150, "value", new ValueFormatter ());
		TableColumnWidgetModel[] theColumns = {theNameColumn, theTypeColumn, theValueColumn};
		setColumns(theColumns);

		setContent(new ContentModel ());
	}
	
	private IStackFrame getStackFrame ()
	{
		return itsStackFrame;
	}
	
	private static class NameFormatter implements Formatter
	{
		public String getText (Object aObject)
		{
			try
			{
				IVariable theVariable = (IVariable) aObject;
				return theVariable.getName();
			} 
			catch (DebugException e)
			{
				return "exeption";
			}
		}
	}
	
	private static class TypeFormatter implements Formatter
	{
		public String getText (Object aObject)
		{
			try
			{
				IVariable theVariable = (IVariable) aObject;
				return theVariable.getReferenceTypeName();
			} 
			catch (DebugException e)
			{
				return "exeption";
			}
		}
	}
	
	private static class ValueFormatter implements Formatter
	{
		public String getText (Object aObject)
		{
			try
			{
				IVariable theVariable = (IVariable) aObject;
				IValue theValue = theVariable.getValue();
				return theValue.getValueString();
			} 
			catch (DebugException e)
			{
				return "exeption";
			}
		}
	}
	
	private class ContentModel extends MappedTreeWidgetModel
	{
		protected Object getRootNode0 ()
		{
			return getStackFrame();
		}

		protected Object getChild0 (Object aParent, int aIndex)
		{
			try
			{
				if (aParent instanceof IStackFrame)
				{
					IStackFrame theStackFrame = (IStackFrame) aParent;
					return theStackFrame.getVariables()[aIndex];
				}
				else if (aParent instanceof IVariable)
				{
					IVariable theVariable = (IVariable) aParent;
					return theVariable.getValue().getVariables()[aIndex];
				}
				else
				{
					assert false;
					return null;
				}
			} 
			catch (DebugException e)
			{
				e.printStackTrace();
				return null;
			}
		}

		public int getChildrenCount (Object aParent)
		{
			try
			{
				if (aParent instanceof IStackFrame)
				{
					IStackFrame theStackFrame = (IStackFrame) aParent;
					return theStackFrame.getVariables().length;
				}
				else if (aParent instanceof IVariable)
				{
					IVariable theVariable = (IVariable) aParent;
					return theVariable.getValue().getVariables().length;
				}
				else
				{
					assert false;
					return -1;
				}
			} 
			catch (DebugException e)
			{
				e.printStackTrace();
				return 0;
			}
		}

		public Object getUserObject (Object aNode)
		{
			return aNode;
		}

	}
}
