/*
 * Created on Apr 22, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package zz.csg.impl.figures;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import zz.csg.api.GraphicObjectContext;
import zz.csg.api.IDisplay;
import zz.csg.api.edition.IGraphicObjectController;
import zz.csg.api.figures.IGOFlowText;
import zz.csg.impl.edition.DefaultGraphicObjectController;
import zz.csg.impl.edition.RectangularControllerUtils;
import zz.utils.properties.IRWProperty;
import zz.utils.ui.HorizontalAlignment;
import zz.utils.ui.VerticalAlignment;
import zz.utils.ui.text.TextPainter;
import zz.utils.ui.text.XFont;


/**
 * Displays a text within a shape.
 * @author gpothier
 */
public class SVGFlowText extends AbstractRectangularShape 
implements IGOFlowText
{
	private final IRWProperty<XFont> pFont = createProperty(FONT);
	private final IRWProperty<String> pText = createProperty(TEXT);
	
	private SizeComputer itsSizeComputer;
	
	public IRWProperty<XFont> pFont()
	{
		return pFont;
	}
	
	public IRWProperty<String> pText()
	{
		return pText;
	}

	@Override
	protected void paintTransformed(IDisplay aDisplay, GraphicObjectContext aContext, Graphics2D aGraphics, Area aVisibleArea)
	{
		XFont theFont = pFont().get();
		
		Paint theStrokePaint = pStrokePaint().get();
		if (theStrokePaint != null)
		{
			aGraphics.setPaint(theStrokePaint);
			Rectangle2D theBounds = getBounds (aContext);
			TextPainter.paint(
					aGraphics, 
					theFont.getAWTFont(), 
					theFont.isUnderline(),
					theStrokePaint, 
					pText().get(), 
					theBounds,
					VerticalAlignment.TOP,
					HorizontalAlignment.LEFT);
		}
	}
	
	protected IGraphicObjectController createController(GraphicObjectContext aContext)
	{
		DefaultGraphicObjectController theController = 
			RectangularControllerUtils.createRectangularController(aContext, this);
		
		return theController;
	}
	
	public boolean isInside(GraphicObjectContext aContext, Point2D aPoint)
	{
		return isInBounds(aContext, aPoint);
	}
	

	public void setSizeComputer(SizeComputer aSizeComputer)
	{
		itsSizeComputer = aSizeComputer;
		if (itsSizeComputer != null) updateSize();
	}

	@Override
	protected void changed(IRWProperty aProperty)
	{
		super.changed(aProperty);
		if (itsSizeComputer != null && (
				aProperty == pFont() 
				|| aProperty == pText()
				|| aProperty == pBounds()))
			updateSize();
	}
	
	/**
	 * Uses the size computer to recalculate the size of this object.
	 */
	protected void updateSize()
	{
		if (! isUpdateEnabled()) return;
		
		Point2D theSize = itsSizeComputer.computeSize(pText().get(), pFont().get());
		Rectangle2D theBounds = pBounds().get();
		
		// Avoid infinite loop by temporarily disabling updates
		SizeComputer theSizeComputer = itsSizeComputer;
		itsSizeComputer = null;
		
		pBounds().set(new Rectangle2D.Double(
				theBounds.getX(),
				theBounds.getY(),
				theSize.getX(),
				theSize.getY()));
		
		itsSizeComputer = theSizeComputer;
	}
	
	@Override
	protected void updateEnabled()
	{
		updateSize();
		super.updateEnabled();
	}
	
	/**
	 * Creates a new flow text with default size computer.
	 */
	public static SVGFlowText create(String aText, XFont aFont, Color aColor)
	{
		return create(aText, aFont, aColor, DefaultSizeComputer.getInstance());
	}
	
	public static SVGFlowText create(
			String aText, 
			XFont aFont, 
			Color aColor,
			SizeComputer aSizeComputer)
	{
		SVGFlowText theFlowText = new SVGFlowText();
		theFlowText.pText().set(aText);
		theFlowText.pStrokePaint().set(aColor);
		theFlowText.pFont().set(aFont);
		theFlowText.setSizeComputer(aSizeComputer);
		
		return theFlowText;
	} 
	
	/**
	 * Creates a new flow text with default size computer and font.
	 */
	public static SVGFlowText create(String aText, Color aColor)
	{
		return create(aText, XFont.DEFAULT_XPLAIN, aColor);
	}

	/**
	 * Creates a new flow text with default size computer and default font
	 * of the given size.
	 */
	public static SVGFlowText create(String aText, float aFontSize, Color aColor)
	{
		return create(aText, XFont.DEFAULT_XPLAIN.deriveFont(aFontSize), aColor);
	}
	
}
