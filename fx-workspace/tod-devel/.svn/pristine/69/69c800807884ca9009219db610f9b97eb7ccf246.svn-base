/*
 * Created on Mar 30, 2005
 */
package zz.snipsnap.storysnipper.ui.login;

import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;

import zz.snipsnap.client.core.SnipSnapSpace;
import zz.snipsnap.storysnipper.model.Story;
import zz.snipsnap.storysnipper.ui.login.LoginHistory.Entry;
import zz.utils.properties.IRWProperty;
import zz.utils.properties.SimpleRWProperty;
import zz.utils.ui.GridStackLayout;
import zz.waltz.api.IWaltzCanvas;
import zz.waltz.api.WaltzComponent;

/**
 * This component permits to login and to enter the server's URL.
 * @author gpothier
 */
public class LoginComponent extends WaltzComponent
{
	public final IRWProperty<String> pSpaceUrl = new SimpleRWProperty<String>(this);
	public final IRWProperty<String> pStoryName = new SimpleRWProperty<String>(this);
	public final IRWProperty<String> pUsername = new SimpleRWProperty<String>(this);
	public final IRWProperty<String> pPassword = new SimpleRWProperty<String>(this);
	
	public LoginComponent()
	{
		List<Entry> theEntries = LoginHistory.getInstance().load();
		
		if (theEntries != null && theEntries.size() > 0) 
		{
			Entry theEntry = theEntries.get(theEntries.size()-1);
			
			pSpaceUrl.set(theEntry.getSpaceUrl());
			pStoryName.set(theEntry.getStoryName());
			pUsername.set(theEntry.getUserName());
			pPassword.set(theEntry.getPassword());
		}
		
	}
	
	protected void render(IWaltzCanvas aCanvas)
	{
		aCanvas.setLayout(new GridStackLayout(2));
		
		aCanvas.label("Space URL", null);
		aCanvas.addProperty(TEXTFIELD_PROPERTY(20), pSpaceUrl, aCanvas);
		
		aCanvas.label("Story name", null);
		aCanvas.addProperty(TEXTFIELD_PROPERTY(20), pStoryName, aCanvas);
		
		aCanvas.label("User name", null);
		aCanvas.addProperty(TEXTFIELD_PROPERTY(20), pUsername, aCanvas);
		
		aCanvas.label("Password", null);
		aCanvas.addProperty(TEXTFIELD_PROPERTY(20), pPassword, aCanvas);
	}
	
	public static Story login() throws IOException
	{
		LoginComponent theComponent = new LoginComponent();
		int theResult = JOptionPane.showOptionDialog(
				null, 
				theComponent.getSwingComponent(), 
				"Login into a SnipSnapStory", 
				JOptionPane.OK_CANCEL_OPTION, 
				JOptionPane.QUESTION_MESSAGE, 
				null, 
				null, 
				null);
		
		if (theResult == JOptionPane.OK_OPTION)
		{
			String theSpaceUrl = theComponent.pSpaceUrl.get();
			String theStoryName = theComponent.pStoryName.get();
			String theUsername = theComponent.pUsername.get();
			String thePassword = theComponent.pPassword.get();
			
			SnipSnapSpace theSpace = new SnipSnapSpace(theSpaceUrl, theUsername, thePassword);
			Story theStory = new Story(theSpace, theStoryName);
			
			LoginHistory.getInstance().addEntry(theSpaceUrl, theStoryName, theUsername, thePassword);
			LoginHistory.getInstance().save();
			
			return theStory;
		}
		else return null;
	}
}
