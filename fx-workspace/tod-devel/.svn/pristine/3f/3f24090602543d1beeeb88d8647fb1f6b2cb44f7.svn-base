/*
 * Created on Mar 3, 2004
 */
package net.basekit.world;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import net.basekit.devices.mouse.Mouse;
import net.basekit.devices.mouse.messages.MouseMessage;
import net.basekit.devices.mouse.messages.MousePressed;
import net.basekit.devices.mouse.messages.MouseReleased;
import net.basekit.devices.mouse.messages.MouseWheelMoved;
import net.basekit.shapes.PickableShape;
import net.basekit.utils.GLMath;
import net.basekit.widgets.Widget;

import com.xith3d.picking.PickRenderResult;
import com.xith3d.scenegraph.Canvas3D;
import com.xith3d.scenegraph.Node;

/**
 * Implementation of the AWT/Swing mouse.
 * @author gpothier
 */
public class SwingMouse implements Mouse, MouseListener, MouseMotionListener, MouseWheelListener
{
	private static final Point3f POINT_BUFFER = new Point3f ();
	private static final Vector3f VECTOR_BUFFER = new Vector3f ();
	
	/**
	 * Maximum distance between two button press events so that
	 * they are considered as consecutive clicks (think of double clicks).
	 */
	private static final float CLICK_TOLERANCE = 2;

	private MousePressedDispatcher itsMousePressedDispatcher = new MousePressedDispatcher ();
	private MouseReleasedDispatcher itsMouseReleasedDispatcher = new MouseReleasedDispatcher ();
	private MouseWheelMovedDispatcher itsMouseWheelMovedDispatcher = new MouseWheelMovedDispatcher ();

	/**
	 * The canvas on which operates the mouse.
	 */
	private Canvas3D itsCanvas3D;

	/**
	 * The world upon which this mouse operates.
	 */
	private World itsWorld;

	private int[] itsWheelPosition = new int[1];
	private boolean[] itsButtonsPressed = new boolean[3];
	private Point itsPosition = new Point ();
	
	/**
	 * If not null, indicates in which widget a drag operation started.
	 */
	private Widget itsDraggingWidget = null;
	
	/**
	 * Last press position for each button.
	 */
	private Point[] itsLastPress = {new Point (), new Point (), new Point ()};
	
	/**
	 * Current number of consequtive clicks for each button. 
	 */
	private int[] itsClickCounts = new int[3];
	
	public SwingMouse (Canvas3D aCanvas3D, World aWorld)
	{
		itsCanvas3D = aCanvas3D;
		itsWorld = aWorld;
	}
	
	public int getNButtons ()
	{
		return 3;
	}

	public int getNWheels ()
	{
		return 1;
	}

	public boolean isButtonPressed (int aButton)
	{
		assert aButton < getNButtons();
		return itsButtonsPressed[aButton];
	}

	public int getWheelPosition (int aWheel)
	{
		assert aWheel < getNWheels();
		return itsWheelPosition[aWheel];
	}

	public Point getPosition ()
	{
		return new Point (itsPosition);
	}
	
	private PickRenderResult[] pick (int aX, int aY)
	{
		return itsCanvas3D.getView().pick(itsCanvas3D, aX, aY, 1, 1);
	}

	public void mouseClicked (MouseEvent aE)
	{
	}

	public void mouseEntered (MouseEvent aE)
	{
	}

	public void mouseExited (MouseEvent aE)
	{
	}

	private int getButtonIndex (MouseEvent aEvent)
	{
		if (SwingUtilities.isLeftMouseButton(aEvent)) return MAIN_BUTTON;
		else if (SwingUtilities.isRightMouseButton(aEvent)) return CONTEXT_BUTTON;
		else if (SwingUtilities.isMiddleMouseButton(aEvent)) return MIDDLE_BUTTON;
		else 
		{
			assert false;
			return -1;
		}
	}
	
	/**
	 * Returns a list of all the widgets that intersect a ray starting at the view origin
	 * and passing by the specified canvas point.
	 */
	private List findWidgetsInRay (int aX, int aY)
	{
		List theList = new  ArrayList ();
		PickRenderResult[] thePick = pick (aX, aY);
		System.out.println("Picked "+thePick.length);
		for (int i = 0; i < thePick.length; i++)
		{
			PickRenderResult theResult = thePick[i];
			Node theNode = theResult.getNodes()[0];
//			System.out.println("zMin="+theResult.getZMin()+" zMax="+theResult.getZMax());
			Widget theWidget = findNearestAncestorWidget(theNode);
			if (theList.contains(theWidget) || ! theWidget.isPickingAware()) continue;
				
			theList.add(theWidget);
		}
		return theList;
	}
	
	/**
	 * Orders a list of widgets so that the first are the deepest ones.
	 */
	private void orderWidgetByDepth (List aWidgets)
	{
		Collections.sort(aWidgets, DepthComparator.getInstance());
	}
	
	private static class DepthComparator implements Comparator
	{
		private static DepthComparator INSTANCE = new DepthComparator();

		public static DepthComparator getInstance ()
		{
			return INSTANCE;
		}

		private DepthComparator ()
		{
		}
		
		public int compare (Object aO1, Object aO2)
		{
			Widget theWidget1 = (Widget) aO1;
			Widget theWidget2 = (Widget) aO2;
			return getWidgetDepth(theWidget2) - getWidgetDepth(theWidget1);
		}
		
		private int getWidgetDepth (Widget aWidget)
		{
			int theDepth = 0;
			while (aWidget != null)
			{
				theDepth++;
				aWidget = aWidget.getParentWidget();
			}
			return theDepth;
		}
		
	}
	
	private Widget findNearestAncestorWidget (Node aNode)
	{
		while (aNode != null)
		{
			if (aNode instanceof Widget) return (Widget) aNode;
			aNode = aNode.getParent();
		}
		return null;
	}
	
	public void mousePressed (MouseEvent aE)
	{
		int theButtonIndex = getButtonIndex(aE);
		int theX = aE.getX();
		int theY = aE.getY();

		if (itsDraggingWidget == null)
		{
			itsMousePressedDispatcher.setButton (theButtonIndex);
			dispatchMouseEvent(aE, itsMousePressedDispatcher);

			Point theLastPress = itsLastPress[theButtonIndex];
			int theDx = theLastPress.x - theX;
			int theDy = theLastPress.y - theY;
			if (theDx*theDx + theDy+theDy < CLICK_TOLERANCE*CLICK_TOLERANCE) itsClickCounts[theButtonIndex]++;
			else itsClickCounts[theButtonIndex] = 0;
			theLastPress.setLocation(theX, theY);
		}
		else
		{
			//TODO: handle dragging.
		}
		
	}

	private void dispatchMouseEvent (final MouseEvent aE, final EventDispatcher aDispatcher)
	{
		itsWorld.invokeLater (new Runnable ()
		{
			public void run ()
			{
				int theX = aE.getX();
				int theY = aE.getY();

				GLMath.createRay(theX, theY, itsCanvas3D, POINT_BUFFER, VECTOR_BUFFER);

				List theWidgets = findWidgetsInRay(theX, theY);
				orderWidgetByDepth(theWidgets);
				for (Iterator theIterator = theWidgets.iterator(); theIterator.hasNext();)
				{
					Widget theWidget = (Widget) theIterator.next();
					PickableShape thePickableShape = theWidget.getPickingBounds();
					Point3f theIntersection = thePickableShape.computeIntersectionCoordinates(POINT_BUFFER, VECTOR_BUFFER);
					System.out.println(theIntersection);

					MouseMessage theMessage = aDispatcher.createMessage (theWidget, theWidgets);
					theWidget.fire(theMessage);
					if (theMessage.isConsumed()) break;
				}
			}
		});
	}

	public void mouseReleased (MouseEvent aE)
	{
		int theButtonIndex = getButtonIndex(aE);

		if (itsDraggingWidget == null)
		{
			itsMouseReleasedDispatcher.setButton (theButtonIndex);
			dispatchMouseEvent(aE, itsMouseReleasedDispatcher);
		}
		else
		{
			//TODO: handle dragging.
		}
		
	}

	public void mouseDragged (MouseEvent aE)
	{
	}

	public void mouseMoved (MouseEvent aE)
	{
	}

	public void mouseWheelMoved (MouseWheelEvent aE)
	{
		itsMouseWheelMovedDispatcher.setAmount (aE.getWheelRotation ());
		dispatchMouseEvent (aE, itsMouseWheelMovedDispatcher);
	}

	/**
	 * Permits to abstract actual event creation in method
	 * {@link SwingMouse#dispatchMouseEvent(java.awt.event.MouseEvent, net.basekit.world.SwingMouse.EventDispatcher)}
	 */
	private abstract class EventDispatcher
	{
		public abstract MouseMessage createMessage (Widget aWidget, List aWidgets);
	}

	private class MousePressedDispatcher extends EventDispatcher
	{
		private int itsButton;

		public void setButton (int aButton)
		{
			itsButton = aButton;
		}

		public MouseMessage createMessage (Widget aWidget, List aWidgets)
		{
			return new MousePressed (aWidget, aWidgets, SwingMouse.this, itsButton, itsClickCounts[itsButton]);
		}
	}

	private class MouseReleasedDispatcher extends EventDispatcher
	{
		private int itsButton;

		public void setButton (int aButton)
		{
			itsButton = aButton;
		}

		public MouseMessage createMessage (Widget aWidget, List aWidgets)
		{
			return new MouseReleased (aWidget, aWidgets, SwingMouse.this, itsButton);
		}
	}

	private class MouseWheelMovedDispatcher extends EventDispatcher
	{
		private int itsAmount;

		public void setAmount (int aAmount)
		{
			itsAmount = aAmount;
		}

		public MouseMessage createMessage (Widget aWidget, List aWidgets)
		{
			return new MouseWheelMoved (aWidget, aWidgets, SwingMouse.this, itsAmount);
		}
	}

}
