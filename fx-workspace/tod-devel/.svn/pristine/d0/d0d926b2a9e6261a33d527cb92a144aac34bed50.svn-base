/*
 * Created on Dec 18, 2004
 */
package zz.csg;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Appender;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.XMLLayout;

/**
 * @author gpothier
 */
public class Log
{
	/**
	 * Logger for events related to the graphic subsystem.
	 */
	public static final Logger GRAPHIC = Logger.getLogger("graphic");
	
	/**
	 * Base logger for editors.
	 */
	public static final Logger EDITOR = Logger.getLogger("editor");
	
	/**
	 * Logger for tools.
	 */
	public static final Logger TOOLS = Logger.getLogger("tools");
	
	/**
	 * Logger for the tests
	 */
	public static final Logger TEST = Logger.getLogger("test");
	
	/**
	 * Logger for UI 
	 */
	public static final Logger UI = Logger.getLogger("ui");
	
	
	static
	{
		if ("true".equals(System.getProperty("deploy"))) configDeployment();
		else configDevelopment();

		// Setup Swing exception handler.
		System.setProperty("sun.awt.exception.handler", Log.class.getName());
	}
	
	/**
	 * Sets up Log4j for a development environment. Level of root logger is set to DEBUG.
	 */
	public static void configDevelopment()
	{
		System.out.println("logging in development mode");
		// Send all output to the console 
		BasicConfigurator.configure();
		
//		try
//		{
//			// Send all output to a file, in xml format.
//			String theFilename = createFilename();
//			Layout theLayout = new XMLLayout();
//			Appender theAppender = new FileAppender(theLayout, theFilename);
//			Logger.getRootLogger().addAppender(theAppender);
//		}
//		catch (IOException e)
//		{
//			throw new RuntimeException(e);
//		}
	}
	
	/**
	 * Sets up Log4j for a deployed environment. Level of root logger is set to WARN.
	 */
	public static void configDeployment()
	{
		System.out.println("logging in deployment mode");
		try
		{
			// Send all output to a file, in xml format.
			String theFilename = createFilename();
			Layout theLayout = new XMLLayout();
			Appender theAppender = new FileAppender(theLayout, theFilename);
			Logger.getRootLogger().addAppender(theAppender);
			Logger.getRootLogger().setLevel(Level.WARN);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Creates a filename for the log file with the date/time stamps.
	 */
	private static String createFilename()
	{
		return "zz.csg.log."
			+new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date())
			+".xml";
	}
	
}
