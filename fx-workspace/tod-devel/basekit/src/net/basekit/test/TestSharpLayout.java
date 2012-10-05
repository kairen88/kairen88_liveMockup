/*
 * Created on Feb 26, 2004
 */
package net.basekit.test;

import javax.swing.JFrame;
import javax.swing.JPanel;

import net.basekit.layoutdelegates.AxisLayoutDelegate;
import net.basekit.layoutdelegates.SharpLayoutDelegate;
import net.basekit.widgets.Widget;
import net.basekit.widgets.combo.ComboWidget;
import net.basekit.widgets.label.LabelWidget;
import net.basekit.world.World;

/**
 * @author gpothier
 */
public class TestSharpLayout
{
	public static void main(String[] args)
	{
//		Logger.setActive (HDElementInstance.LOG_HDEI, true);

		JFrame the3DFrame = new JFrame ("TestSharpLayout");
		the3DFrame.setSize(640, 480);
		the3DFrame.setVisible(true);
		World theWorld = new World ((JPanel) the3DFrame.getContentPane());

		Widget theWidget1 = new Widget ();
		theWidget1.setLayoutDelegate (new AxisLayoutDelegate (AxisLayoutDelegate.X_POSITIVE));

		theWidget1.addWidget (new DummyWidget ());
		theWidget1.addWidget (new DummyWidget ());
		theWidget1.addWidget (new DummyWidget ());
		theWidget1.setPacked (true);

		theWorld.getContentRoot().addWidget(theWidget1);
		
	}

	private static class DummyWidget extends Widget
	{
		public DummyWidget ()
		{
			setLayoutDelegate (new SharpLayoutDelegate ());

			addWidget (new LabelWidget ("North"), SharpLayoutDelegate.N);
			addWidget (new LabelWidget ("C"), SharpLayoutDelegate.C);
			addWidget (new ComboWidget (new String[] {"List", "Vladivostok"}), SharpLayoutDelegate.NE);
			setPacked (true);
		}
	}
}
