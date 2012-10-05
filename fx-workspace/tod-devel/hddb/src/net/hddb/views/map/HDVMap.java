/*
 * Created on Apr 3, 2004
 */
package net.hddb.views.map;

import java.util.Collection;

import net.basekit.layoutdelegates.AxisLayoutDelegate;
import net.basekit.layoutdelegates.SharpLayoutDelegate;
import net.basekit.models.list.AbstractListContentWidgetModel;
import net.basekit.models.table.DefaultTableColumnWidgetModel;
import net.basekit.models.table.DefaultTableWidgetModel;
import net.basekit.models.table.TableColumnWidgetModel;
import net.basekit.models.table.TableWidgetModel;
import net.basekit.widgets.Widget;
import net.basekit.widgets.list.ListElementRenderer;
import net.basekit.widgets.list.ListElementWidget;
import net.basekit.widgets.table.TableWidget;
import net.hddb.adapters.HDAdapter;
import net.hddb.models.HDModel;
import net.hddb.models.map.HDMMap;
import net.hddb.ui.elementinstance.HDEIContainer;
import net.hddb.ui.elementinstance.HDElementInstance;
import net.hddb.utils.Constraints;
import net.hddb.views.HDView;
import net.hddb.views.HDViewClass;

/**
 * 
 * @author gpothier
 */
public class HDVMap extends HDView implements HDEIContainer
{
	private TableWidget itsTableWidget;
	
	public HDVMap (HDModel aModel)
	{
		super(aModel);
		createUI ();
	}

	protected HDMMap getHDMMap ()
	{
		return (HDMMap) getModel();
	}
	
	private void createUI ()
	{
		TableColumnWidgetModel theKeysColumn = new DefaultTableColumnWidgetModel (50, "keys", new KeysElementRenderer ());
		TableColumnWidgetModel theValuesColumn = new DefaultTableColumnWidgetModel (150, "values", new ValuesElementRenderer ());
		TableColumnWidgetModel[] theColumns = {theKeysColumn, theValuesColumn};
		TableWidgetModel theTableModel = new DefaultTableWidgetModel (theColumns, new MapTableContentModel ());
		itsTableWidget = new TableWidget (theTableModel);
		setLayoutDelegate(new SharpLayoutDelegate ());
		addWidget(itsTableWidget, SharpLayoutDelegate.C);
	}

	public HDViewClass getViewClass ()
	{
		return HDVCMap.getInstance();
	}

	public Constraints getChildrenViewConstraints ()
	{
		return null;
	}
	
	private class MapTableContentModel extends AbstractListContentWidgetModel
	{
		public int getSize ()
		{
			HDMMap theHDAMap = getHDMMap();
			return theHDAMap != null ? theHDAMap.getCount() : 0;
		}

		public Object getElementAt (int aIndex)
		{
			return getHDMMap().getChild(aIndex);
		}
	}

	private class KeysElementRenderer implements ListElementRenderer
	{
		public ListElementWidget createWidget (Object aElement, int aIndex)
		{
			Object theKey = getHDMMap().getEntryKey(aElement);
			return new HDEIListElementWidget (HDVMap.this, theKey, aIndex);
		}

		public void updateWidget (ListElementWidget aWidget, boolean aSelected, boolean aFocused)
		{
		}
		
	}
	
	private class ValuesElementRenderer implements ListElementRenderer
	{

		public ListElementWidget createWidget (Object aElement, int aIndex)
		{
			Object theValue = getHDMMap().getEntryValue(aElement);
			return new HDEIListElementWidget (HDVMap.this, theValue, aIndex);
		}

		public void updateWidget (ListElementWidget aWidget, boolean aSelected, boolean aFocused)
		{
		}
		
	}
	
	private class HDEIListElementWidget extends Widget implements ListElementWidget
	{
		private int itsIndex;
		private HDElementInstance itsElementInstance;
		
		public HDEIListElementWidget (HDEIContainer aContainer, Object aElement, int aIndex)
		{
			itsElementInstance = new HDElementInstance (aContainer, aElement);
			itsIndex = aIndex;
			createUI ();
		}
		
		private void createUI ()
		{
			setLayoutDelegate(new AxisLayoutDelegate (AxisLayoutDelegate.X_POSITIVE));
			addWidget(itsElementInstance);
		}
		
		public int getIndex ()
		{
			return itsIndex;
		}

		public Object getElement ()
		{
			return itsElementInstance.getElement();
		}
	}
}
