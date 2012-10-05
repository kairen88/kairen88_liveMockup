/*/*
 * Created on 16-abr-2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package zz.snipsnap.utils.jibx;

import org.jibx.binding.Run;


/**
 * Provides a main method that permits to dynamically run the JiBX binder.
 * @author Rodrigo Rodriguez
 */
public class Launcher
{
	private static final String[] ARGS = 
	{
			"-b", "src/bindings.xml"
	};

	public static void main(String[] args)
	{
		System.out.println("Compiling bindings...");
		try
		{
			String[] theJiBXArgs = new String[ARGS.length+args.length];
			System.arraycopy(ARGS, 0, theJiBXArgs, 0, ARGS.length);
			System.arraycopy(args, 0, theJiBXArgs, ARGS.length, args.length);
			Run.main(theJiBXArgs);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}


