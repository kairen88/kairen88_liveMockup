/*
 * Created on Mar 21, 2005
 */
package zz.waltz.api;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * This frame displays a single {@link zz.waltz.api.WaltzComponent}
 * @author gpothier
 */
public class WaltzFrame extends JFrame
{
	private WaltzComponent itsComponent;

	public WaltzFrame()
	{
	}

	public WaltzFrame(WaltzComponent aComponent)
	{
		setComponent(aComponent);
	}

	public WaltzComponent getComponent()
	{
		return itsComponent;
	}

	public void setComponent(WaltzComponent aComponent)
	{
		itsComponent = aComponent;
		setContentPane(itsComponent != null ? itsComponent.getSwingComponent() : new JPanel());
	}
	
	/**
	 * Shows a waltz frame with the specified component.
	 */
	public static WaltzFrame show (WaltzComponent aComponent)
	{
		return show(aComponent, JFrame.DISPOSE_ON_CLOSE);
	}
	
	public static WaltzFrame show (WaltzComponent aComponent, int aDefaultCloseOperation) 
	{
		WaltzFrame theFrame = new WaltzFrame(aComponent);
		theFrame.setDefaultCloseOperation(aDefaultCloseOperation);
		theFrame.pack();
		theFrame.setVisible(true);
		return theFrame;
	}
	
}
