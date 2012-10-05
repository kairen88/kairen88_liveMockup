/**
 * Created by IntelliJ IDEA.
 * User: Guillaume
 * Date: 21 mars 2003
 * Time: 16:20:30
 */
package zz.csg.display.tool;

import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import zz.csg.api.GraphicNode;
import zz.csg.api.IGraphicContainer;
import zz.csg.api.edition.IGraphicMapper;
import zz.csg.display.GraphicObjectEditionLayer;
import zz.utils.notification.Message;


/**
 * Abstract base class for tools used in {@link GraphicObjectEditionLayer}.
 * These tools behave as a layer on top of the edition layer; they are
 * responsible of painting themselves ({@link #paint(Graphics2D)}) and
 * processing events (see {@link #startDrag(Point2D, MouseEvent)}
 * and related methods). 
 * <p>
 * The semantics of mouse events are a bit different of standard AWT events:
 * there are no pressed or released events, but rather drag start and drag stop
 * events, and click event. This permits to differentiate cases whre the mouse
 * button is pressed for dragging or for clicking.
 */
public abstract class EditorTool
{
	private GraphicObjectEditionLayer itsEditionLayer;
	private List itsEditorToolListeners = new ArrayList ();

	/**
	 * Called when this tool becomes the current tool in the
	 * specified edition layer
	 */
	public void activate (GraphicObjectEditionLayer aEditionLayer)
	{
		assert itsEditionLayer == null;
		itsEditionLayer = aEditionLayer;
	}
	
	/**
	 * Called when this tool ceases to be the current tool in its
	 * edition layer.
	 *
	 */
	public void deactivate ()
	{
		assert itsEditionLayer != null;
		itsEditionLayer = null;
	}
	
	public GraphicObjectEditionLayer getEditionLayer ()
	{
		return itsEditionLayer;
	}

	public GraphicNode getTargetNode()
	{
		return getEditionLayer ().getTargetNode();
	}
	
	public IGraphicMapper getMapper ()
	{
		return getEditionLayer().getMapper();
	}

	/**
	 * Retrieves the root container from the edition layer.
	 */ 
	public GraphicNode getRootNode()
	{
		return getEditionLayer().getRootNode();
	}

	public void addEditorToolListener (EditorToolListener aListener)
	{
		itsEditorToolListeners.add (aListener);
	}

	public void removeEditorToolListener (EditorToolListener aListener)
	{
		itsEditorToolListeners.remove (aListener);
	}

	protected void fireOperationPerformed ()
	{
		for (Iterator theIterator = itsEditorToolListeners.iterator (); theIterator.hasNext ();)
		{
			EditorToolListener theListener = (EditorToolListener) theIterator.next ();
			theListener.operationPerformed(this);
		}
	}

	public void fireOperationCancelled ()
	{
		for (Iterator theIterator = itsEditorToolListeners.iterator (); theIterator.hasNext ();)
		{
			EditorToolListener theListener = (EditorToolListener) theIterator.next ();
			theListener.operationCancelled(this);
		}
	}

	/**
	 * Returns the cursor to use when this tool is active.
	 */
	public abstract Cursor getCursor ();

	/**
	 * Paints this tool's layer.
	 */
	public abstract void paint (Graphics2D aGraphics);

	/**
	 * Repaints the component.
	 */
	protected void repaint ()
	{
		GraphicObjectEditionLayer theEditionLayer = getEditionLayer ();
		if (theEditionLayer != null) theEditionLayer.repaint ();
	}

	/**
	 * Subclasses should call this method when they completed an operation.
	 * It is useful if we want to go back to the selection tool after each
	 * object creation.
	 */
	protected void done()
	{
		itsEditionLayer.graphicCommandPerformed();
	}
	
	/**
	 * Called when the mouse enters the edition layer.
	 * @param aPoint The logical coordinates of the mouse.
	 * @param e The original MouseEvent
	 */
	public void mouseEntered ()
	{
	}

	/**
	 * Called when the mouse exits the edition layer.
	 * @param aPoint The logical coordinates of the mouse.
	 * @param e The original MouseEvent
	 */
	public void mouseExited ()
	{
	}

	/**
	 * Called when the mouse moves with no button pressed.
	 * @param aPoint The logical coordinates of the mouse.
	 * @param e The original MouseEvent
	 */
	public void mouseMoved (Point2D aPoint, MouseEvent e)
	{
	}

	/**
	 * Called when a drag operation starts.
	 * @param aPoint The logical coordinates of the mouse.
	 * @param e The original MouseEvent
	 */
	public void startDrag (Point2D aPoint, MouseEvent e)
	{
	}

	/**
	 * Called when a drag operation completes.
	 * @param aPoint The logical coordinates of the mouse.
	 * @param e The original MouseEvent
	 */
	public void endDrag (Point2D aPoint, MouseEvent e)
	{
	}

	/**
	 * Called when a drag operation is cancelled.
	 */
	public void cancelDrag ()
	{
	}

	/**
	 * @param aPoint The logical coordinates of the mouse.
	 * @param e The original MouseEvent
	 */
	public void mouseClicked (Point2D aPoint, MouseEvent e)
	{
	}

	/**
	 * @param aPoint The logical coordinates of the mouse.
	 * @param e The original MouseEvent
	 */
	public void mousePressed (Point2D aPoint, MouseEvent e)
	{
	}

	/**
	 * @param aPoint The logical coordinates of the mouse.
	 * @param e The original MouseEvent
	 */
	public void mouseReleased (Point2D aPoint, MouseEvent e)
	{
	}

	/**
	 * @param aPoint The logical coordinates of the mouse.
	 * @param e The original MouseEvent
	 */
	public void mouseDragged (Point2D aPoint, MouseEvent e)
	{
	}

	public void keyTyped (KeyEvent e)
	{
	}

	public void keyPressed (KeyEvent e)
	{
	}

	public void keyReleased (KeyEvent e)
	{
	}


}
