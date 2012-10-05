/*
 * Created on Feb 26, 2004
 */
package net.hddb.test;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.vecmath.Vector3f;

import net.hddb.adapters.impl.string2text.HDAString2Text;
import net.basekit.widgets.Widget;
import net.basekit.widgets.combo.ComboWidget;
import net.basekit.widgets.label.LabelWidget;
import net.basekit.widgets.list.ListWidget;
import net.basekit.world.World;
import net.basekit.utils.Logger;
import net.basekit.layoutdelegates.SharpLayoutDelegate;
import net.hddb.ui.elementinstance.HDEIUtils;
import net.hddb.ui.elementinstance.HDElementInstance;
import net.hddb.ui.world.HDWorld;

/**
 * In this test the user types text in a text field, and an element instance based on this text
 * is displayed in the world.
 * First functional run 2004-3-12
 * @author gpothier
 */
public class Test1
{
	private static final String INITIAL_STRING = "ABC";
	private static JTextField TEXTFIELD;
	private static HDAString2Text ADAPTER = new HDAString2Text (INITIAL_STRING);

	public static void main(String[] args)
	{
		Logger.setActive (HDElementInstance.LOG_HDEI, true);

		JFrame the3DFrame = new JFrame ("Test1");
		the3DFrame.setSize(640, 480);
		the3DFrame.setVisible(true);
		World theWorld = new World ((JPanel) the3DFrame.getContentPane());

		HDElementInstance theElementInstance = new HDElementInstance (null, ADAPTER);

		theElementInstance.setSize(new Vector3f (100, 100, 0));

		theWorld.getRootWidget().addWidget(theElementInstance);
		
//		Widget theWidget1 = new Widget ();
//		theWidget1.setLayoutDelegate (new SharpLayoutDelegate ());
//
//		theWidget1.addWidget (new LabelWidget ("North"), SharpLayoutDelegate.N);
//		theWidget1.addWidget (new LabelWidget ("Center"), SharpLayoutDelegate.C);
//		theWidget1.addWidget (new ComboWidget (new String[] {"List", "Vladivostok"}), SharpLayoutDelegate.NE);
//		theWidget1.setPacked (true);
//
//		theWorld.getRootWidget().addWidget(theWidget1);
		
		JFrame theFrame = new JFrame ("Test1 - source");
		TEXTFIELD = new JTextField (INITIAL_STRING, 20);
		JPanel thePanel = new JPanel ();
		thePanel.add (TEXTFIELD);
		theFrame.getContentPane().add (thePanel);
		theFrame.pack();
		theFrame.setVisible(true);
		
		TEXTFIELD.getDocument().addDocumentListener(new DocumentListener ()
				{
			public void changedUpdate(DocumentEvent e)
			{
				textChanged();
			}

			public void	insertUpdate(DocumentEvent e)
			{
				textChanged();				
			}
			
			public void removeUpdate(DocumentEvent e)
			{
				textChanged();
			}
		});
	}
	
	private static void textChanged ()
	{
		String theNewText = TEXTFIELD.getText();
		ADAPTER.setString(theNewText);
	}
}
