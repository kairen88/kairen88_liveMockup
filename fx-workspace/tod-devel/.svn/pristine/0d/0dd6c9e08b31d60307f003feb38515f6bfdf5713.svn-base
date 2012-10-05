/*
 * Created on May 29, 2004
 */
package zz.csg.impl.edition;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;

import javax.swing.ImageIcon;

import EDU.Washington.grad.gjb.cassowary.ClSimplexSolver;
import EDU.Washington.grad.gjb.cassowary.ExCLError;
import EDU.Washington.grad.gjb.cassowary.ExCLInternalError;
import EDU.Washington.grad.gjb.cassowary.ISimpleVariable;

import zz.csg.api.GraphicObjectContext;
import zz.csg.api.IGraphicObject;
import zz.csg.api.edition.IGraphicMapper;
import zz.csg.api.edition.IHandle;


/**
 * Abstract implementation of {@link zz.csg.api.edition.IHandle}.
 * Provides a few utilities that deal with pixel-aware handles, which
 * is the typical case of control points.
 * @author gpothier
 */
public abstract class AbstractHandle implements IHandle
{
	private GraphicObjectContext itsContext;
	private IGraphicObject itsGraphicObject;
	
	/**
	 * The solver used to resolve constraints.
	 */
	private ClSimplexSolver itsSolver;
	
	
	public AbstractHandle(GraphicObjectContext aContext, IGraphicObject aGraphicObject)
	{
		itsContext = aContext;
		itsGraphicObject = aGraphicObject;
	}
	
	public GraphicObjectContext getContext()
	{
		return itsContext;
	}
	
	public IGraphicObject getGraphicObject()
	{
		return itsGraphicObject;
	}

	/**
	 * Returns an array of the variables that this handle modifies while
	 * moving.
	 */
	protected abstract ISimpleVariable[] getEditedVariables();
	
	protected abstract void move0 (Point2D aPoint) throws ExCLError;
	
	protected void set(ISimpleVariable aVariable, double aValue) throws ExCLError
	{
		if (itsSolver != null) itsSolver.suggestValue(aVariable, aValue);
		else aVariable.change_value(aValue);
	}
	
	public void startMovement()
	{
		itsSolver = GraphicObjectContext.SOLVER.getInherited(getContext());
		if (itsSolver != null)
		{
			try
			{
				ISimpleVariable[] theVariables = getEditedVariables();
				for (ISimpleVariable theVariable : theVariables)
					itsSolver.addEditVar(theVariable);
				itsSolver.beginEdit();
			}
			catch (ExCLInternalError e)
			{
				throw new RuntimeException(e);
			}
		}
	}
	
	public final void move(Point2D aPoint)
	{
		try
		{
			move0(aPoint);
			if (itsSolver != null) itsSolver.resolve();
		}
		catch (ExCLError e)
		{
			e.printStackTrace();
		}
	}
	
	public void endMovement()
	{
		if (itsSolver != null)
		{
			try
			{
				itsSolver.endEdit();
			}
			catch (ExCLInternalError e)
			{
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * Returns true if the specified logical point is in the bounds of the given
	 * image when displayed at its natural scale
	 * @param aMapper The mapper that permits to transform coordinates.
	 * @param aPoint A point in this handle's logical coordinate system
	 * @param aIcon The image
	 */
	protected boolean isInsideCenteredImage (IGraphicMapper aMapper, Point2D aPoint, ImageIcon aIcon)
	{
		Point thePixelOrigin = aMapper.localToPixel(getContext(), getGraphicObject(), getOrigin());
		Point thePixelPoint = aMapper.localToPixel(getContext(), getGraphicObject(), aPoint);
		
		return Math.abs (thePixelOrigin.x - thePixelPoint.x) <= aIcon.getIconWidth() / 2
			&& Math.abs (thePixelOrigin.y - thePixelPoint.y) <= aIcon.getIconHeight() / 2;
	}
	
	/**
	 * Paints the specified image at its natural scale, centered around the 
	 * given point.
	 * @param aMapper The mapper that permits to transform coordinates.
	 * @param aPoint A point in this handle's logical coordinate system
	 * @param aIcon The image to draw
	 */
	protected void paintCenteredImage(IGraphicMapper aMapper, Graphics2D aGraphics, ImageIcon aIcon)
	{
		Point thePixelOrigin = aMapper.localToPixel(getContext(), getGraphicObject(), getOrigin());
		int theX = thePixelOrigin.x - aIcon.getIconWidth() / 2;
		int theY = thePixelOrigin.y - aIcon.getIconHeight() / 2;
		aGraphics.drawImage(aIcon.getImage(), theX, theY, null);
	}


}
