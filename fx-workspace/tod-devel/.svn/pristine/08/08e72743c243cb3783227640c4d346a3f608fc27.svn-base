/*
 * Created on Apr 20, 2004
 */
package net.basekit.interaction.devices;

import javax.vecmath.Vector3f;

import net.basekit.world.World;
import fr.emn.reactiveinput.AbstractDevice;
import fr.emn.reactiveinput.Device;
import fr.emn.reactiveinput.DeviceUtilities;
import fr.emn.reactiveinput.In;
import fr.emn.reactiveinput.SlotType;

/**
 * A device that represents a camera in the virtual world.
 * Provides slots that permits to control its position, orientation, zoom, etc.
 * @author gpothier
 */
public class CameraController extends AbstractBasekitDevice
{
	private In itsPositionX;
	private In itsPositionY;
	private In itsPositionZ;
	
	private In itsDirectionX;
	private In itsDirectionY;
	private In itsDirectionZ;
	
	private In itsZoom;
	
	public CameraController (World aWorld)
	{
		super("basekitCamera", aWorld);
		initSlots ();
	}

	private void initSlots ()
	{
		itsPositionX = addIn("position.x", SlotType.DOUBLE);
		itsPositionY = addIn("position.y", SlotType.DOUBLE);
		itsPositionZ = addIn("position.z", SlotType.DOUBLE);

		itsDirectionX = addIn("direction.x", SlotType.DOUBLE);
		itsDirectionY = addIn("direction.y", SlotType.DOUBLE);
		itsDirectionZ = addIn("direction.z", SlotType.DOUBLE);

		itsZoom = addIn("zoom", SlotType.DOUBLE);
	}
	
	public Device copy() 
	{
		Device theDevice = new CameraController (getWorld());
		DeviceUtilities.setPrototype(theDevice, DeviceUtilities.getPrototype(this));
		return theDevice;
	}

	
	public void update ()
	{
		float thePositionX = getFloatValue(itsPositionX, 0);
		float thePositionY = getFloatValue(itsPositionY, 0);
		float thePositionZ = getFloatValue(itsPositionZ, -200);

		float theDirectionX = getFloatValue(itsDirectionX, 0);
		float theDirectionY = getFloatValue(itsDirectionY, 0);
		float theDirectionZ = getFloatValue(itsDirectionZ, 0);

		float theZoom = getFloatValue(itsZoom, 1);
		
		Vector3f thePosition = new Vector3f (thePositionX, thePositionY, thePositionZ);
		Vector3f theDirection = new Vector3f (theDirectionX, theDirectionY, theDirectionZ);
		
		Vector3f theCenter = new Vector3f ();
		theCenter.add (thePosition, theDirection);
		
		System.out.println("set camera position: "+thePosition+", center: "+theCenter);
		getWorld().setCameraPosition(thePosition);
	}
}
