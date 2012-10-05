/*
 * Created on Mar 30, 2005
 */
package zz.snipsnap.storysnipper;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;

import javax.swing.JApplet;
import javax.swing.JComponent;

public class StorySnipperApplet extends JApplet
{
//	private static String[] JARS = {
//		"storysnipper.jar",
//		"activation.jar",
//		"foxtrot.jar",
//		"gnu-regexp-1.1.4.jar",
//		"kafenio-config.jar",
//		"kafenio-icons.jar",
//		"kafenio.jar",
//		"metadata-extractor-2.2.2.jar",
//		"xmlrpc-1.2-b1.jar",
//		"jibx-run.jar",
//		"xpp3.jar",
//		"waltz.jar",
//		"zz.utils.jar",		
//	};
//	
//	/**
//	 * Reference to out {@link AppletContent}
//	 * We keep it as object because of ClassLoader issues.
//	 */
//	private Object itsAppletContent;
//	
	public void init()
	{
		try
		{
			System.out.println(Class.forName("javax.imageio.ImageReader"));
			System.out.println(Class.forName("javax.imageio.ImageIO"));
//
//			System.out.println("StorySnipper Applet initializing... " + getClass().getClassLoader());
//
//			URI theCodeBase = getCodeBase().toURI();
//			URL[] theUrls = new URL[JARS.length];
//			for (int i = 0; i < JARS.length; i++)
//			{
//				String theJar = JARS[i];
//				theUrls[i] = theCodeBase.resolve(theJar).toURL();
//			}
////			ClassLoader theLoader = new URLClassLoader(theUrls);
//			ClassLoader theLoader = getClass().getClassLoader();
//			
//			Class theClass = theLoader.loadClass("zz.snipsnap.storysnipper.AppletContent");
//			System.out.println(" Class AppletContent loaded");
//			itsAppletContent = theClass.newInstance();
//			System.out.println(" Class AppletContent instantiated");
//			
//			setContentPane((JComponent) itsAppletContent);
//			System.out.println("Done.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
//	public void stop()
//	{
//		try
//		{
//			Method theMethod = itsAppletContent.getClass().getMethod("save");
//			theMethod.invoke(itsAppletContent);
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
	
	private static class MyLoader extends URLClassLoader
	{
		public MyLoader(URL[] aUrls)
		{
			super (aUrls, ClassLoader.getSystemClassLoader());
		}
		
		@Override
		public URL getResource(String aName)
		{
			System.out.println("MyLoader.getResource(): "+aName);
			return super.getResource(aName);
		}
		
		@Override
		public InputStream getResourceAsStream(String aName)
		{
			System.out.println("MyLoader.getResourceAsStream(): "+aName);
			return super.getResourceAsStream(aName);
		}
		
		@Override
		public Enumeration<URL> getResources(String aName) throws IOException
		{
			System.out.println("MyLoader.getResources(): "+aName);
			return super.getResources(aName);
		}
		
		@Override
		public URL findResource(String aName)
		{
			System.out.println("MyLoader.findResource(): "+aName);
			return super.findResource(aName);
		}
		
		@Override
		public Enumeration<URL> findResources(String aName) throws IOException
		{
			System.out.println("MyLoader.findResources(): "+aName);
			return super.findResources(aName);
		}
		
	}

}

