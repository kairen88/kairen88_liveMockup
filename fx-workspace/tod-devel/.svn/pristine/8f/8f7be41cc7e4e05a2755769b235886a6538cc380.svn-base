/*
 * Created on Mar 31, 2005
 */
package zz.snipsnap.storysnipper;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.JLabel;
import javax.swing.JPanel;

import zz.snipsnap.client.core.SnipSnapSpace;
import zz.snipsnap.storysnipper.model.Story;
import zz.snipsnap.storysnipper.ui.StorySnipperComponent;
import zz.snipsnap.storysnipper.ui.login.LoginComponent;
import zz.utils.ui.StackLayout;

public class AppletContent extends JPanel
{
	static
	{
		System.out.println("AppletContent loaded by "+AppletContent.class.getClassLoader());
	}
	
	private StorySnipperComponent itsStorySnipperComponent;

	public AppletContent() throws IOException, ClassNotFoundException
	{
		System.out.println(ClassLoader.getSystemClassLoader().loadClass("javax.imageio.ImageIO"));
		System.out.println(Class.forName("javax.imageio.ImageReader"));
		System.out.println(Class.forName("javax.imageio.ImageIO"));
		
		Story theStory = LoginComponent.login();

		if (theStory != null)
		{
			itsStorySnipperComponent = new StorySnipperComponent(theStory);
			setLayout(new StackLayout());
			add (itsStorySnipperComponent.getSwingComponent());
		}
		else
		{
			add (new JLabel("Could not login"));
		}
	}
	
	public void save()
	{
		itsStorySnipperComponent.save();
	}

}
