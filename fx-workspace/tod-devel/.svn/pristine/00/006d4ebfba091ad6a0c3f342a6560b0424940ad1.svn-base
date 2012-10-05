/*
 * Created on Feb 26, 2004
 */
package net.basekit.test;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.vecmath.Vector3f;

import net.basekit.models.tree.ExpandableTreeNode;
import net.basekit.widgets.tree.TreeWidget;
import net.basekit.world.World;

/**
 * @author gpothier
 */
public class TestTree
{
	public static void main(String[] args)
	{
//		Logger.setActive (HDElementInstance.LOG_HDEI, true);

		JFrame the3DFrame = new JFrame ("TestTree");
		the3DFrame.setSize(640, 480);
		the3DFrame.setVisible(true);
		World theWorld = new World ((JPanel) the3DFrame.getContentPane());

		ExpandableTreeNode theRoot = new ExpandableTreeNode ("root");
		theRoot.add(new ExpandableTreeNode ("c1"));
		theRoot.add(new ExpandableTreeNode ("c2"));
		theRoot.add(new ExpandableTreeNode ("c2"));
		theRoot.add(new ExpandableTreeNode ("c4"));
		TreeWidget theTreeWidget = new TreeWidget (theRoot);
		
		theTreeWidget.setPreferredSize(new Vector3f (100, 100, 0));
		
		theWorld.getContentRoot().addWidget(theTreeWidget);
		
	}

}
