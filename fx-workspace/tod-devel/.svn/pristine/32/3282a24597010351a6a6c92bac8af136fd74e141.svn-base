/*
 * Created on 23-sep-2004
 */
package zz.csg.api;

/**
 * Enumerates the actions that can be performed by a time-aware
 * graphic object.
 * @see IGraphicObject#stream(GraphicObjectContext, StreamAction)
 * @author gpothier
 */
public class StreamAction
{
	public static final StreamAction PLAY = new StreamAction ("play");
	public static final StreamAction PAUSE = new StreamAction ("pause");
	public static final StreamAction STOP = new StreamAction ("stop");

	private String itsName;

	private StreamAction (String aName)
	{
		itsName = aName;
	}

	public String getName ()
	{
		return itsName;
	}

	public String toString ()
	{
		return getName ();
	}
}
