/*
 * Created on Dec 27, 2004
 */
package zz.csg.display.tool.select;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import zz.csg.api.GraphicNode;
import zz.csg.api.IGraphicObject;
import zz.csg.api.figures.IGORectangle;
import zz.csg.impl.figures.SVGRectangle;
import zz.utils.properties.IProperty;
import zz.utils.properties.IRWProperty;



/**
 * This tool permits to define a rectangular region
 * @author gpothier
 */
public class RectangularSelectionTool extends AbstractHandleTool 
{
	private IGORectangle itsRectangle = new SVGRectangle();
	private List<GraphicNode> itsNodes = new ArrayList<GraphicNode>();

	public RectangularSelectionTool()
	{
		itsNodes.add (new GraphicNode(itsRectangle));
		itsRectangle.pBounds().set (new Rectangle2D.Double(0, 0, 10, 10));
	}
	
	protected Iterable<GraphicNode> getGraphicObjects()
	{
		return itsNodes;
	}
	
	/**
	 * The property that contains the selected rectangle.
	 */
	public IRWProperty<Rectangle2D> pSelection()
	{
		return itsRectangle.pBounds();
	}
}
