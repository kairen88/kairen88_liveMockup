/*
 * Created on Jan 26, 2005
 */
package zz.csg.api;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Point2D;

import zz.utils.ui.IMouseAware;

/**
 * Result set for a picking operation
 * @see zz.csg.api.IGraphicContainer#pick(Point2D)
 * @author gpothier
 */
public class PickResult extends GraphicNode<IGraphicObject>
implements IMouseAware
{
	
	public PickResult(IGraphicObject aGraphicObject, GraphicObjectContext aContext)
	{
		super (aGraphicObject, aContext);
	}
	
	public boolean isMouseAware()
	{
		return getGraphicObject().isMouseAware(getContext());
	}

	public boolean mousePressed(MouseEvent aEvent, Point2D aPoint)
	{
		return getGraphicObject().mousePressed(getContext(), aEvent, aPoint);
	}

	public boolean mouseReleased(MouseEvent aEvent, Point2D aPoint)
	{
		return getGraphicObject().mouseReleased(getContext(), aEvent, aPoint);
	}

	public boolean mouseClicked(MouseEvent aEvent, Point2D aPoint)
	{
		return getGraphicObject().mouseClicked(getContext(), aEvent, aPoint);
	}

	public void mouseEntered(MouseEvent aEvent)
	{
		getGraphicObject().mouseEntered(getContext(), aEvent);
	}

	public void mouseExited(MouseEvent aEvent)
	{
		getGraphicObject().mouseExited(getContext(), aEvent);
	}

	public boolean mouseMoved(MouseEvent aEvent, Point2D aPoint)
	{
		return getGraphicObject().mouseMoved(getContext(), aEvent, aPoint);
	}

	public boolean mouseWheelMoved(MouseWheelEvent aEvent, Point2D aPoint)
	{
		return getGraphicObject().mouseWheelMoved(getContext(), aEvent, aPoint);
	}
	
	public boolean startDrag(MouseEvent aEvent, Point2D aPoint)
	{
		return getGraphicObject().startDrag(getContext(), aEvent, aPoint);
	}

	public void drag(Point2D aPoint)
	{
		getGraphicObject().drag(getContext(), aPoint);
	}

	public void commitDrag(Point2D aPoint)
	{
		getGraphicObject().commitDrag(getContext(), aPoint);
	}

	public void cancelDrag()
	{
		getGraphicObject().cancelDrag(getContext());
	}
}	
