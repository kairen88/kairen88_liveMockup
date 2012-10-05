/*
 * Created on Apr 23, 2004
 */
package net.basekit.interaction.devices;

import net.basekit.world.World;
import fr.emn.reactiveinput.AbstractDevice;
import fr.emn.reactiveinput.In;

/**
 * Provides some base functionality for basekit devices.
 * @author gpothier
 */
public class AbstractBasekitDevice extends AbstractDevice
{
	private World itsWorld; 
	
	public AbstractBasekitDevice (String aName, World aWorld)
	{
		super(aName);
		itsWorld = aWorld;
	}
	
	public World getWorld ()
	{
		return itsWorld;
	}

	protected static float getFloatValue (In aSlot, float aDefaultValue)
	{
		return aSlot.isValid() ? (float) aSlot.getDoubleValue() : aDefaultValue;
	}
	
	protected static int getIntValue (In aSlot, int aDefaultValue)
	{
		return aSlot.isValid() ? aSlot.getIntValue() : aDefaultValue;
	}
	
	protected static boolean getBooleanValue (In aSlot, boolean aDefaultValue)
	{
		return aSlot.isValid() ? aSlot.getBooleanValue() : aDefaultValue;
	}
	
}
