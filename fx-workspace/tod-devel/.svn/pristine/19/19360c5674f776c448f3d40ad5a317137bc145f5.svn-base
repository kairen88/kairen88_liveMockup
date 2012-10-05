/**
 * Created by IntelliJ IDEA.
 * User: Guillaume
 * Date: 20 mars 2003
 * Time: 15:02:06
 */
package zz.csg.display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

import javax.swing.Action;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import zz.csg.api.GraphicNode;
import zz.csg.api.GraphicObjectContext;
import zz.csg.api.IGraphicContainer;
import zz.csg.api.IGraphicObject;
import zz.csg.api.IProxyGraphicObject;
import zz.csg.api.PickResult;
import zz.csg.api.edition.IGraphicMapper;
import zz.csg.display.menu.IMenuRequestHandler;
import zz.csg.display.menu.MenuRequestInfo;
import zz.csg.display.tool.EditorTool;
import zz.csg.display.tool.select.SelectionTool;
import zz.utils.Utils;
import zz.utils.properties.ISetProperty;
import zz.utils.ui.TransparentPanel;
import zz.utils.ui.UIUtils;


/**
 * Provides an edition layer that can be placed on top of a component that displays a 
 * {@link zz.csg.api.IGraphicContainer}.
 * @author gpothier
 */
public class GraphicObjectEditionLayer
		extends TransparentPanel
		implements MouseListener, MouseMotionListener, KeyListener
{
	/**
	 * The graphic panel that provides logical/pixel transformations.
	 */
	private IGraphicMapper itsMapper;
	
	/**
	 * An object that handles context menu requests
	 */
	private IMenuRequestHandler itsMenuRequestHandler;
	
	private GraphicNode itsRootNode;

	/**
	 * The container into which will be inserted the new elements.
	 */
	private GraphicNode itsTargetNode;

	/**
	 * The currently active tool.
	 */
	private EditorTool itsEditorTool;

	/**
	 * X coordinate of last mouse pressed with left button.
	 */
	private int itsPressX = -1;

	/**
	 * Y coordinate of last mouse pressed with left button.
	 */
	private int itsPressY = -1;
	
	/**
	 * Timestamp of last left mouse button press.
	 */
	private long itsPressTime; 
	
	/**
	 * Whether a drag operation has started.
	 */
	private boolean itsDragging = false;

	/**
	 * This is the selection tool of the graphic editor.
	 */
	private SelectionTool itsSelectionTool;


	public GraphicObjectEditionLayer (
			IGraphicMapper aGraphicMapper, 
			SelectionTool aSelectionTool)
	{
		this (aGraphicMapper, aSelectionTool, null);
	}
	
	public GraphicObjectEditionLayer (
			IGraphicMapper aGraphicPanel, 
			SelectionTool aSelectionTool, 
			GraphicNode aRootNode)
	{
		itsMapper = aGraphicPanel;
		itsSelectionTool = aSelectionTool;
		setRootNode(aRootNode);
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		setAutoscrolls(true);
	}

	public IGraphicMapper getMapper()
	{
		return itsMapper;
	}
	
	public IMenuRequestHandler getMenuRequestHandler()
	{
		return itsMenuRequestHandler;
	}

	public void setMenuRequestHandler(IMenuRequestHandler aMenuRequestHandler)
	{
		itsMenuRequestHandler = aMenuRequestHandler;
	}

	public void setRootNode(GraphicNode aRootNode)
	{
		if (itsRootNode != aRootNode)
		{
			itsRootNode = aRootNode;
			itsTargetNode = itsRootNode;
		}
	}

	public GraphicNode getRootNode ()
	{
		return itsRootNode;
	}

	public GraphicNode getTargetNode ()
	{
		return itsTargetNode;
	}

	public void setTargetNode (GraphicNode aTargetNode)
	{
		itsTargetNode = aTargetNode;
	}

	public EditorTool getEditorTool ()
	{
		return itsEditorTool;
	}

	public void setEditorTool (EditorTool aEditorTool)
	{
		if (itsEditorTool != null) itsEditorTool.deactivate();
		itsEditorTool = aEditorTool;
		if (itsEditorTool != null)
		{
			itsEditorTool.activate(this);
			setCursor(aEditorTool.getCursor());			
		}
		else setCursor(null);
		
		repaint();
	}

	private Point2D getTransformedPoint (MouseEvent e)
	{
		return itsMapper.pixelToRoot(e.getPoint());
	}

	/**
	 * Finds the object at the specified point.
	 */
	public PickResult pick (Point2D aPoint)
	{
		GraphicNode theRootNode = getRootNode();
		GraphicNode theTargetNode = getTargetNode();
		
//		System.out.println("Root:");
//		System.out.println(printGfx(theRootNode));
//		System.out.println("Target:");
//		System.out.println(printGfx(theTargetNode));
		
		if (theTargetNode != null)
		{
			IGraphicObject theTargetGraphicObject = theTargetNode.getGraphicObject();
			GraphicObjectContext theTargetContext = theTargetNode.getContext();
			
			// Transform point coordinates to target's coordinate system
			aPoint = theTargetGraphicObject.rootToLocal(theTargetContext, aPoint);
			
			PickResult theResult = theTargetGraphicObject.pick(theTargetContext, aPoint);
			if (theResult == null) return null;
			
			// Search the first-level child
			IGraphicObject theCurrentGraphicObject = theResult.getGraphicObject();
			GraphicObjectContext theCurrentContext = theResult.getContext();
			
//			System.out.println("Pick:");
//			System.out.println(printGfx(theCurrentGraphicObject, theCurrentContext));
			
			while (theCurrentGraphicObject != null)
			{
				IGraphicObject theParent = theCurrentGraphicObject.getParent();
				GraphicObjectContext theParentContext = theCurrentContext;
				if (theParent == null)
				{
					theParent = theCurrentContext.getProxyGraphicObject();
					theParentContext = theCurrentContext.getParentContext();
					if (theParentContext == null) break;
				}
				if (theParent == theTargetGraphicObject
						&& theParentContext == theTargetContext)
				{
					return new PickResult(theCurrentGraphicObject, theCurrentContext);
				}

				theCurrentContext = theParentContext;
				theCurrentGraphicObject = theParent;
			}
		}
		
		return null;
	}

	public static String printGfx(GraphicNode aGraphicNode)
	{
		return printGfx(aGraphicNode.getGraphicObject(), aGraphicNode.getContext());
	}
	
	public static String printGfx(IGraphicObject aGraphicObject, GraphicObjectContext aContext)
	{
		StringBuilder theBuilder = new StringBuilder();
		printGfx(theBuilder, aGraphicObject, aContext, 0);
		return theBuilder.toString();
	}

	private static void printGfx(
			StringBuilder aBuilder, 
			IGraphicObject aGraphicObject, 
			GraphicObjectContext aContext, 
			int aIndent)
	{
		Utils.indentln(aBuilder, aIndent);
		aBuilder.append("node: "+aGraphicObject+", ctx: "+aContext+", parent: "+aGraphicObject.getParent());
		if (aGraphicObject instanceof IGraphicContainer)
		{
			IGraphicContainer theContainer = (IGraphicContainer) aGraphicObject;
			for (IGraphicObject theChild : theContainer.pChildren())
			{
				printGfx(aBuilder, theChild, aContext, aIndent+1);
			}
		}
		else if (aGraphicObject instanceof IProxyGraphicObject)
		{
			aBuilder.append("PROXY ->");
			IProxyGraphicObject theProxyGraphicObject = (IProxyGraphicObject) aGraphicObject;
			
			printGfx(
					aBuilder, 
					theProxyGraphicObject.getChildGraphicObject(), 
					theProxyGraphicObject.getChildContext(aContext), 
					aIndent+1);
		}
	}
	
	/**
	 * This method is called whenever a tool has completed a drawing command
	 */
	public void graphicCommandPerformed ()
	{
		setEditorTool(itsSelectionTool);
	}

	public void mouseClicked (MouseEvent e)
	{
	}

	public void mousePressed (MouseEvent e)
	{
		grabFocus();
		
		Point2D theTransformedPoint = getTransformedPoint (e);
		getEditorTool ().mousePressed(theTransformedPoint, e);
		
		if (SwingUtilities.isLeftMouseButton(e))
		{
			itsPressX = e.getX();
			itsPressY = e.getY();
			itsPressTime = System.currentTimeMillis();
		}
		else if (SwingUtilities.isRightMouseButton(e))
		{
			showContextMenu (theTransformedPoint, e);
		}
	}

	public void mouseReleased (MouseEvent e)
	{
		Point2D theTransformedPoint = getTransformedPoint (e);
		getEditorTool ().mouseReleased(theTransformedPoint, e);
		
		if (SwingUtilities.isLeftMouseButton(e) && getEditorTool () != null)
		{
			if (itsDragging)
			{
				getEditorTool ().endDrag (theTransformedPoint, e);
				itsDragging = false;
			}
			else
			{
				getEditorTool ().mouseClicked (theTransformedPoint, e);
			}
		}
	}

	public void mouseDragged (MouseEvent e)
	{
		scrollRectToVisible(new Rectangle(e.getX(), e.getY(), 1, 1));
		if (SwingUtilities.isLeftMouseButton(e) && getEditorTool () != null)
		{
			if (itsDragging)
			{
				Point2D theTransformedPoint = getTransformedPoint (e);
				getEditorTool ().mouseDragged (theTransformedPoint, e);
			}
			else
			{
				int theDX = itsPressX - e.getX();
				int theDY = itsPressY - e.getY();
				int theDist = theDX * theDX + theDY * theDY;
				long theTime = System.currentTimeMillis() - itsPressTime;
				if (theDist > 4 || theTime > 300) 
				{
					itsDragging = true;
					Point2D theTransformedPoint = itsMapper.pixelToRoot(new Point (itsPressX, itsPressY));
					getEditorTool().startDrag(theTransformedPoint, e);
				}
			}
		}
	}

	public void mouseEntered (MouseEvent e)
	{
		if (getEditorTool () != null)
		{
			getEditorTool ().mouseEntered ();
		}
	}

	public void mouseExited (MouseEvent e)
	{
		if (getEditorTool () != null)
		{
			getEditorTool ().mouseExited ();
		}
	}

	public void mouseMoved (MouseEvent e)
	{
		if (getEditorTool () != null)
		{
			Point2D theTransformedPoint = getTransformedPoint (e);
			getEditorTool ().mouseMoved (theTransformedPoint, e);
			setCursor(getEditorTool().getCursor());
		}
	}

	public void keyTyped (KeyEvent e)
	{
		if (getEditorTool () != null) getEditorTool ().keyTyped(e);
	}

	public void keyPressed (KeyEvent e)
	{
		if (getEditorTool () != null) 
		{
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE && itsDragging)
			{
				getEditorTool ().cancelDrag();
				getEditorTool ().fireOperationCancelled();
				itsDragging = false;
				repaint();
			}
			else getEditorTool ().keyPressed(e);
		}
	}

	public void keyReleased (KeyEvent e)
	{
		if (getEditorTool () != null) getEditorTool ().keyReleased(e);
	}

	protected void paintComponent (Graphics g)
	{
		super.paintComponent(g);
		Graphics2D theGraphics = (Graphics2D) g;
		
		// Hightlight the target container, if any.
		GraphicNode theRootNode = getRootNode();
		GraphicNode theTargetNode = getTargetNode();
		if (theRootNode != null 
				&& theTargetNode != null 
				&& theRootNode != theTargetNode)
		{
			GraphicObjectContext theTargetContext = theTargetNode.getContext();
			IGraphicObject theTargetGraphicObject = theTargetNode.getGraphicObject();
			Rectangle2D theBounds = theTargetGraphicObject.getBounds(theTargetContext);
			Rectangle theTargetBounds = getMapper().localToPixel(theTargetContext, theTargetGraphicObject, theBounds);

			GraphicObjectContext theRootContext = theRootNode.getContext();
			IGraphicObject theRootGraphicObject = theRootContext.getRootGraphicObject();
			theBounds = theRootGraphicObject.getBounds(theRootContext);
			Rectangle theRootBounds = getMapper().rootToPixel(theBounds);
				
			Area theBoundsArea = new Area (theRootBounds);
			Area theTargetArea = new Area (theTargetBounds);
			theBoundsArea.subtract(theTargetArea);
				
			theGraphics.setComposite(UIUtils.ALPHA_06);
			theGraphics.setColor(Color.LIGHT_GRAY);
			theGraphics.fill(theBoundsArea);
			theGraphics.setComposite(UIUtils.ALPHA_OPAQUE);
			
		}
		
		if (getEditorTool() != null)
		{
			getEditorTool().paint(theGraphics);
		}
	}

	/**
	 * Shows a context menu for the graphic object that lays at the specified position.
	 */
	private void showContextMenu (Point2D aPoint, MouseEvent aE)
	{
		if (itsMenuRequestHandler == null) return;
		
		PickResult theResult = pick(aPoint);
		
		if (theResult != null)
		{
			ISetProperty<GraphicNode> theSelectionProperty = 
				itsSelectionTool.getSelectionProperty();
			
			theSelectionProperty.clear();
			theSelectionProperty.add(theResult);
			
			MenuRequestInfo theRequestInfo = new MenuRequestInfo (
					(IGraphicContainer) getRootNode().getGraphicObject(), 
					aPoint,
					theResult.getGraphicObject(),
					theSelectionProperty);
			
			List<Action> theActions = itsMenuRequestHandler.createActions(theRequestInfo);
			if (theActions.size() > 0)
			{
				JPopupMenu thePopupMenu = new JPopupMenu ();
				for (Action theAction : theActions)
				{
					JMenuItem theMenuItem = new JMenuItem (theAction);
					thePopupMenu.add(theMenuItem);
				}
				thePopupMenu.show(this, aE.getX(), aE.getY());
			}
			
		}
	}
}
