/*
 * Created on Apr 20, 2004
 */
package net.basekit.interaction;

import javax.swing.JFrame;

import net.basekit.interaction.devices.CameraController;
import net.basekit.interaction.devices.ToolGlassController;
import net.basekit.world.World;
import net.jhid.icon.JHIDFolder;

import fr.emn.inputeditor.JInputEditor;
import fr.emn.inputeditor.devices.FEditor;
import fr.emn.reactiveinput.ClassFolder;
import fr.emn.reactiveinput.Configuration;
import fr.emn.reactiveinput.DeviceFolder;
import fr.emn.reactiveinput.DeviceUtilities;
import fr.emn.reactiveinput.OpenContext;
import fr.emn.reactiveinput.SystemFolder;
import fr.emn.reactiveinput.devices.FRoot;

/**
 * Central point of access to interaction subsystem.
 * There is one interaction manager per world.
 * @author gpothier
 */
public class InteractionManager
{
	/**
	 * Mapped to the world's swing component.
	 */
	public static final String KEY_COMPONENT = "component";
	
	private static final String CONFIG_FILE = "interaction.ic";
	
	private World itsWorld;

	private JInputEditor itsInputConfigurator;

	private FRoot itsRootFolder;
	private SystemFolder itsBasekitFolder;
	

	public InteractionManager (World aWorld)
	{
		itsWorld = aWorld;
		init ();
	}

	private void init ()
	{
		OpenContext theContext = new OpenContext();
		theContext.setValue(KEY_COMPONENT, itsWorld.getComponent());
		itsRootFolder = new FRoot(new FEditor());
		itsBasekitFolder = new SystemFolder ("basekit");
		itsRootFolder.add(itsBasekitFolder);
		itsRootFolder.add(new JHIDFolder ());
		initDevices();
		DeviceUtilities.register(itsBasekitFolder);
		Configuration theConfiguration = new Configuration(theContext, itsRootFolder);
		theConfiguration.load(new java.io.File(CONFIG_FILE));
		itsInputConfigurator = new JInputEditor(theConfiguration, false);
		theConfiguration.open();
		theConfiguration.start();
	}
	
	private void initDevices ()
	{
		itsBasekitFolder.add(new CameraController (itsWorld));
		itsBasekitFolder.add(new ToolGlassController (itsWorld));
	}

	/**
	 * Shows the input editor frame.
	 */
	public void showInputEditor ()
	{
		itsInputConfigurator.show ();
//		itsInputConfigurator.setExtendedState(JFrame.NORMAL);
	}

	/**
	 * Hides the input editor frame.
	 */
	public void hideInputEditor ()
	{
		itsInputConfigurator.setVisible(false);
	}
	
	/**
	 * Toggles visibility of the input editor frame.
	 */
	public void toggleInputEditor ()
	{
		itsInputConfigurator.setVisible(! itsInputConfigurator.isVisible());
	}
	
	/**
	 * Returns ICon's root device folder.
	 */
	public DeviceFolder getRootFolder ()
	{
		return itsRootFolder;
	}
}
