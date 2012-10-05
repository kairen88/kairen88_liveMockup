/*
 * Created on Feb 26, 2004
 */
package net.basekit.test;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.vecmath.Vector3f;

import net.basekit.layoutdelegates.SharpLayoutDelegate;
import net.basekit.models.table.DefaultTableColumnWidgetModel;
import net.basekit.models.table.TableColumnWidgetModel;
import net.basekit.models.tree.ExpandableTreeNode;
import net.basekit.models.treetable.DefaultTreeTableWidgetModel;
import net.basekit.models.treetable.TreeTableWidgetModel;
import net.basekit.widgets.Widget;
import net.basekit.widgets.list.LabelElementRenderer;
import net.basekit.widgets.treetable.TreeTableWidget;
import net.basekit.world.World;
import zz.utils.Formatter;

/**
 * Test of layout of nested widget hierarchies.
 * @author gpothier
 */
public class TestDeepLayout
{
	public static void main(String[] args)
	{
//		Logger.setActive (HDElementInstance.LOG_HDEI, true);

		JFrame the3DFrame = new JFrame ("TestDeepLayout");
		the3DFrame.setSize(640, 480);
		the3DFrame.setVisible(true);
		final World theWorld = new World ((JPanel) the3DFrame.getContentPane());

		theWorld.invokeLater(new Runnable ()
		{
			public void run ()
			{
				ExpandableTreeNode theRoot = new ExpandableTreeNode ("root");
				theRoot.add(new ExpandableTreeNode ("c1"));
				theRoot.add(new ExpandableTreeNode ("c2"));
				theRoot.add(new ExpandableTreeNode ("c2"));
				theRoot.add(new ExpandableTreeNode ("c4"));
				
				TableColumnWidgetModel[] theColumns = 
					{
						new DefaultTableColumnWidgetModel (30, "Col1", new LabelElementRenderer ()),
						new DefaultTableColumnWidgetModel (20, "Col2", new LabelElementRenderer (new HashFormatter ())),
				};
				
				TreeTableWidgetModel theModel = new DefaultTreeTableWidgetModel (theColumns, theRoot);
				
				TreeTableWidget theTableWidget = new TreeTableWidget (theModel);
				theTableWidget.setPreferredSize(new Vector3f (100, 100, 0));
				
				Widget theWidget1 = new Widget ();
				theWidget1.setLayoutDelegate(new SharpLayoutDelegate ());
				theWidget1.addWidget(theTableWidget, SharpLayoutDelegate.C);
				theWorld.getContentRoot().addWidget(theWidget1);
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
