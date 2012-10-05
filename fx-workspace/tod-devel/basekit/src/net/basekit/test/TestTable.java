/*
 * Created on Feb 26, 2004
 */
package net.basekit.test;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.vecmath.Vector3f;

import zz.utils.Formatter;

import net.basekit.models.list.DefaultListContentWidgetModel;
import net.basekit.models.table.DefaultTableColumnWidgetModel;
import net.basekit.models.table.DefaultTableWidgetModel;
import net.basekit.models.table.TableColumnWidgetModel;
import net.basekit.models.table.TableWidgetModel;
import net.basekit.widgets.list.LabelElementRenderer;
import net.basekit.widgets.table.TableWidget;
import net.basekit.widgets.tree.TreeWidget;
import net.basekit.world.World;

/**
 * Test of {@link net.basekit.widgets.table.TableWidget}
 * @author gpothier
 */
public class TestTable
{
	public static void main(String[] args)
	{
//		Logger.setActive (HDElementInstance.LOG_HDEI, true);

		JFrame the3DFrame = new JFrame ("TestTree");
		the3DFrame.setSize(640, 480);
		the3DFrame.setVisible(true);
		final World theWorld = new World ((JPanel) the3DFrame.getContentPane());

		theWorld.invokeLater(new Runnable ()
		{
			public void run ()
			{
				List theList = new ArrayList ();
				theList.add ("ABCDEF");
				theList.add ("toto");
				theList.add ("titiF");
				theList.add ("ADEF00");
				DefaultListContentWidgetModel theContent = new DefaultListContentWidgetModel (theList);
				
				TableColumnWidgetModel[] theColumns = 
					{
						new DefaultTableColumnWidgetModel (30, "Col1", new LabelElementRenderer ()),
						new DefaultTableColumnWidgetModel (20, "Col2", new LabelElementRenderer (new HashFormatter ())),
				};
				
				TableWidgetModel theModel = new DefaultTableWidgetModel (theColumns, theContent);
				
				TableWidget theTableWidget = new TableWidget (theModel);
				theTableWidget.setPreferredSize(new Vector3f (100, 100, 0));
				
				theWorld.getContentRoot().addWidget(theTableWidget);
			}
		});
		
	}
	
	private static class HashFormatter implements Formatter
	{

		public String getText (Object aObject)
		{
			return ""+aObject.hashCode();
		}
		
	}

}
