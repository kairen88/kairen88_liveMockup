/*
 * Created on Feb 26, 2004
 */
package net.basekit.test;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.vecmath.Vector3f;

import net.basekit.layoutdelegates.AxisLayoutDelegate;
import net.basekit.widgets.Widget;
import net.basekit.widgets.label.LabelWidget;
import net.basekit.widgets.tree.TreeWidget;
import net.basekit.world.World;

/**
 * @author gpothier
 */
public class TestLabel
{
	public static void main(String[] args)
	{
//		Logger.setActive (HDElementInstance.LOG_HDEI, true);

		JFrame the3DFrame = new JFrame ("TestLabel");
		the3DFrame.setSize(640, 480);
		the3DFrame.setVisible(true);
		World theWorld = new World ((JPanel) the3DFrame.getContentPane());

		Widget theWidget = new Widget ();
		theWidget.setLayoutDelegate(new AxisLayoutDelegate (AxisLayoutDelegate.X_POSITIVE));

		LabelWidget theLabel;
		
		theLabel = new LabelWidget ("CC");
		theLabel.setPreferredSize(new Vector3f (100, 100, 0));
		theLabel.setAlign(LabelWidget.CENTER, LabelWidget.CENTER);
		theWidget.addWidget(theLabel);
		
//		theLabel = new LabelWidget ("CL");
//		theLabel.setPreferredSize(new Vector3f (100, 100, 0));
//		theLabel.setAlign(LabelWidget.CENTER, LabelWidget.LEADING);
//		theWidget.addWidget(theLabel);
//		
//		theLabel = new LabelWidget ("CT");
//		theLabel.setPreferredSize(new Vector3f (100, 100, 0));
//		theLabel.setAlign(LabelWidget.CENTER, LabelWidget.TRAILING);
//		theWidget.addWidget(theLabel);
//		
//		theLabel = new LabelWidget ("LC");
//		theLabel.setPreferredSize(new Vector3f (100, 100, 0));
//		theLabel.setAlign(LabelWidget.LEADING, LabelWidget.CENTER);
//		theWidget.addWidget(theLabel);
//		
//		theLabel = new LabelWidget ("LL");
//		theLabel.setPreferredSize(new Vector3f (100, 100, 0));
//		theLabel.setAlign(LabelWidget.LEADING, LabelWidget.LEADING);
//		theWidget.addWidget(theLabel);
//		
//		theLabel = new LabelWidget ("LT");
//		theLabel.setPreferredSize(new Vector3f (100, 100, 0));
//		theLabel.setAlign(LabelWidget.LEADING, LabelWidget.TRAILING);
//		theWidget.addWidget(theLabel);
//		
//		theLabel = new LabelWidget ("TC");
//		theLabel.setPreferredSize(new Vector3f (100, 100, 0));
//		theLabel.setAlign(LabelWidget.TRAILING, LabelWidget.CENTER);
//		theWidget.addWidget(theLabel);
//		
//		theLabel = new LabelWidget ("TL");
//		theLabel.setPreferredSize(new Vector3f (100, 100, 0));
//		theLabel.setAlign(LabelWidget.TRAILING, LabelWidget.LEADING);
//		theWidget.addWidget(theLabel);
//		
//		theLabel = new LabelWidget ("TT");
//		theLabel.setPreferredSize(new Vector3f (100, 100, 0));
//		theLabel.setAlign(LabelWidget.TRAILING, LabelWidget.TRAILING);
//		theWidget.addWidget(theLabel);
//		
		
		theWorld.getContentRoot().addWidget(theWidget);
		
	}

}
