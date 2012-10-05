/*
 * Created on Jan 16, 2007
 */
package zz.csg.impl.interactive;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.Point2D;

import zz.csg.api.GraphicObjectContext;
import zz.csg.api.IDisplay;
import zz.csg.api.layout.BorderLayout;
import zz.csg.impl.SVGGraphicContainer;
import zz.csg.impl.figures.SVGFlowText;
import zz.csg.impl.figures.SVGImage;
import zz.csg.impl.uri.ImageProvider;
import zz.utils.notification.IEvent;
import zz.utils.notification.SimpleEvent;
import zz.utils.properties.IRWProperty;
import zz.utils.properties.SimpleRWProperty;

public class SVGButton extends SVGGraphicContainer
{
	private IEvent<Object> pClicked = new SimpleEvent<Object>();
	
	private IRWProperty<Image> pIcon = new SimpleRWProperty<Image>()
	{
		@Override
		protected void changed(Image aOldValue, Image aNewValue)
		{
			itsImage.pProvider().set(new ImageProvider(aNewValue));
		}
	};
	
	private boolean itsMouseOver = false;

	private SVGImage itsImage;

	private SVGFlowText itsText;

	public SVGButton(String aText)
	{
		this(aText, null);
	}
	
	public SVGButton(String aText, Image aIcon)
	{
		createUI();
		
		pText().set(aText);
		pIcon().set(aIcon);
		pBounds().set(0, 0, 10, 10);
	}
	
	private void createUI()
	{
		BorderLayout theLayout = new BorderLayout();
		setLayoutManager(theLayout);
		
		itsImage = new SVGImage();
		itsText = new SVGFlowText();
		
		pChildren().add(itsText);
		pChildren().add(itsImage);
		
		theLayout.setCenterComponent(itsText);
		theLayout.setWestComponent(itsImage);
	}
	
	@Override
	protected void paintBackground(IDisplay aDisplay, GraphicObjectContext aContext, Graphics2D aGraphics, Area aVisibleArea)
	{
		super.paintBackground(aDisplay, aContext, aGraphics, aVisibleArea);
		
		Color theColor = itsMouseOver ? Color.BLACK : Color.RED;
		
		aGraphics.setColor(theColor);
		aGraphics.draw(pBounds().get());
	}

	/**
	 * This event is fired when the button is clicked.
	 */
	public IEvent<Object> pClicked()
	{
		return pClicked;
	}

	/**
	 * This property contains the (optional) icon displayed by the button
	 */
	public IRWProperty<Image> pIcon()
	{
		return pIcon;
	}

	/**
	 * This property contains the (optional) text displayed by the button.
	 */
	public IRWProperty<String> pText()
	{
		return itsText.pText();
	}

	@Override
	public boolean mousePressed(GraphicObjectContext aContext, MouseEvent aEvent, Point2D aPoint)
	{
		return super.mousePressed(aContext, aEvent, aPoint);
	}
	
	@Override
	public boolean mouseReleased(GraphicObjectContext aContext, MouseEvent aEvent, Point2D aPoint)
	{
		return super.mouseReleased(aContext, aEvent, aPoint);
	}
	
	@Override
	public void mouseEntered(GraphicObjectContext aContext, MouseEvent aEvent)
	{
		itsMouseOver = true;
		repaintAllContexts();
	}
	
	@Override
	public void mouseExited(GraphicObjectContext aContext, MouseEvent aEvent)
	{
		itsMouseOver = false;
		repaintAllContexts();
	}
}
