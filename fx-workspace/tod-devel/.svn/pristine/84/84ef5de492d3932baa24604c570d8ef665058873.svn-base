package net.basekit.widgets.label;
import javax.vecmath.Tuple3f;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

import net.basekit.Message;
import net.basekit.models.label.DefaultLabelWidgetModel;
import net.basekit.models.label.LabelWidgetModel;
import net.basekit.models.label.messages.LabelDataChanged;
import net.basekit.shapes.TextFace;
import net.basekit.widgets.Margins;
import net.basekit.widgets.Widget;

/*
 * Created on Jan 11, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */

/**
 * Simple widget that displays a text and an icon (both optional).
 * @author gpothier
 */
public class LabelWidget extends Widget
{
	public static final TextFace.Aligner LEADING = TextFace.LEADING;
	public static final TextFace.Aligner TRAILING = TextFace.TRAILING;
	public static final TextFace.Aligner CENTER = TextFace.CENTER;
	
	private LabelWidgetModel itsModel;
	
	private TextFace itsTextFace = new TextFace ();
		
	public LabelWidget()
	{
		this ("");		
	}

	public LabelWidget(String aText)
	{
		this (new DefaultLabelWidgetModel (aText));		
	}

	public LabelWidget (LabelWidgetModel aModel)
	{
		setModel(aModel);
		addAdditionalContent(itsTextFace);
	}
	
	
	private void update ()
	{
		updateTextAttributes ();
		updateSize();
		updateGeometry();
	}


	private void updateTextAttributes ()
	{
		itsTextFace.setFont(getModel().getTitleFont());
		itsTextFace.setText(getModel().getTitle());
	}

	/**
	 * Recalculates the minimum abds preferred size of the widget.
	 */
	private void updateSize()
	{
		Vector2f theTextSize = itsTextFace.getTextSize();
		Vector3f theSize = new Vector3f (theTextSize.x, theTextSize.y, 0);
		
		Margins theMargins = getMargins();
		if (theMargins != null)
		{
			theSize.x += theMargins.getHorizontal();
			theSize.y += theMargins.getVertical();
		}
		
		
		setPreferredSize(theSize);
		setMinimumSize(theSize);
		invalidateLayout ();
	}

	/**
	 * Repositions the text face
	 */
	private void updateGeometry ()
	{
		Vector3f theSize = getSize();
		if (theSize != null)
		{
			Margins theMargins = getMargins();
			
			itsTextFace.setPosition(new Vector3f (theMargins.getLeft(), theMargins.getTop(), 0));
			
			itsTextFace.setSize(new Vector3f (theSize.x - theMargins.getHorizontal(),
					theSize.y - theMargins.getVertical(), 0));
		}
	}
	
	
	public LabelWidgetModel getModel ()
	{
		return itsModel;
	}
	
	/**
	 * Sets the model that governs the content of this label.
	 */
	public void setModel (LabelWidgetModel aModel)
	{
		if (itsModel != null) itsModel.removeObserver(this);
		itsModel = aModel;
		if (itsModel != null) itsModel.addObserver(this);
		update();
	}
	
	/**
	 * Convenience method that assumes that the model is a {@link DefaultLabelWidgetModel}
	 * and sets the title of this model.
	 */
	public void setTitle (String aTitle)
	{
		((DefaultLabelWidgetModel)getModel()).setTitle(aTitle);
	}
	
	public void processMessage (Message aMessage)
	{
		if (aMessage instanceof LabelDataChanged)
		{
			LabelDataChanged theMessage = (LabelDataChanged) aMessage;
			update();
		}
		super.processMessage(aMessage);
	}
	
	/**
	 * We override this method so as to update the geometry when the size changes.
	 */
	public boolean setSize (Tuple3f aSize)
	{
		if (super.setSize(aSize))
		{
			updateGeometry();
			return true;
		}
		else return false;
	}
	
	public TextFace.Aligner getHorizontalAlign ()
	{
		return itsTextFace.getHorizontalAlign();
	}
	
	public void setHorizontalAlign (TextFace.Aligner aHorizontalAlign)
	{
		itsTextFace.setHorizontalAlign(aHorizontalAlign);
	}
	
	public TextFace.Aligner getVerticalAlign ()
	{
		return itsTextFace.getVerticalAlign();
	}
	
	public void setVerticalAlign (TextFace.Aligner aVerticalAlign)
	{
		itsTextFace.setVerticalAlign(aVerticalAlign);
	}
	
	public void setAlign (TextFace.Aligner aHorizontal, TextFace.Aligner aVertical)
	{
		itsTextFace.setAlign(aHorizontal, aVertical);
	}
	
}
