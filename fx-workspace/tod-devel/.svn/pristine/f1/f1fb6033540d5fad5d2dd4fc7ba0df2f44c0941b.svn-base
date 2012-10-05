/*
 * Created on Mar 25, 2005
 */
package zz.snipsnap.storysnipper;

import java.io.IOException;
import java.net.MalformedURLException;

import zz.snipsnap.client.core.SnipSnapSpace;
import zz.snipsnap.storysnipper.model.Story;
import zz.snipsnap.storysnipper.ui.StorySnipperComponent;
import zz.snipsnap.storysnipper.ui.login.LoginComponent;
import zz.waltz.api.WaltzFrame;

/**
 * Main class for launching StorySnipper as an application.
 * @author gpothier
 */
public class StorySnipper
{
	public static void main(String[] args) throws IOException
	{
		Story theStory = LoginComponent.login();
		if(theStory != null) 
		{
			final StorySnipperComponent theStorySnipperComponent = new StorySnipperComponent(theStory);
			WaltzFrame.show(theStorySnipperComponent, WaltzFrame.EXIT_ON_CLOSE);
			
			Runtime.getRuntime().addShutdownHook(new Thread()
					{
						public void run()
						{
							theStorySnipperComponent.save();
						}
					});
		}
	}

}
