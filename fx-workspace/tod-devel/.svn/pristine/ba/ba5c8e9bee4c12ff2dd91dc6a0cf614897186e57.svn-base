/*
 * Created on Apr 23, 2004
 */
package net.basekit.interaction.devices;

import net.basekit.world.World;
import net.basekit.world.toolglass.ToolGlass;
import fr.emn.reactiveinput.*;
import fr.emn.reactiveinput.AbstractDevice;
import fr.emn.reactiveinput.In;

/**
 * A toolglass is a component that has its coordinates
 * in the same corrdinate system as the camera (ie moves with the camera)
 * and permits to execute actions by clicking through it.
 * <p>
 * A toolglass can have pages, that each contain a set of tools.
 * @author gpothier
 */
public class ToolGlassController extends AbstractBasekitDevice
{
	private In itsPositionX;
	private In itsPositionY;

	private In itsCurrentPage;
	
	public ToolGlassController (World aWorld)
	{
		super("basekitToolglass", aWorld);
		initSlots ();
	}

	private void initSlots ()
	{
		itsPositionX = addIn("position.x", SlotType.DOUBLE);
		itsPositionY = addIn("position.y", SlotType.DOUBLE);
		
		itsCurrentPage = addIn ("page", SlotType.INT);
	}
	
	public Device copy() 
	{
		Device theDevice = new ToolGlassController (getWorld());
		DeviceUtilities.setPrototype(theDevice, DeviceUtilities.getPrototype(this));
		return theDevice;
	}

	
	public void update ()
	{
		float thePositionX = getFloatValue(itsPositionX, 0);
		float thePositionY = getFloatValue(itsPositionY, 0);
		
		int theCurrentPage = getIntValue(itsCurrentPage, 0);
		
		ToolGlass theToolGlass = getWorld().getToolGlass();
		theToolGlass.setCurrentPage(theCurrentPage);
		theToolGlass.setPosition(thePositionX, thePositionY);
	}

}
