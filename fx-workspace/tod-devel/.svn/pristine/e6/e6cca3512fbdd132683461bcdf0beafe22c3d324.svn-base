/*
 * Created on Mar 6, 2004
 */
package net.basekit.widgets.button;

import java.awt.Color;

import javax.vecmath.Vector3f;

import net.basekit.layoutdelegates.SharpLayoutDelegate;
import net.basekit.shapes.LineBorder;
import net.basekit.utils.AppearanceFactory;
import net.basekit.utils.Appearances;
import net.basekit.widgets.label.LabelWidget;

import com.xith3d.scenegraph.Appearance;
import com.xith3d.scenegraph.Shape3D;

/**
 * A simple button renderer that displays the button's model's title.
 * @author gpothier
 */
public class FlatRenderer extends ButtonRenderer
{
	
	private LineBorder itsBorder;
	private LabelWidget itsLabelWidget;

	public void initRenderer ()
	{
		itsBorder = new LineBorder ();
		itsLabelWidget = new LabelWidget ();
		
		getButtonWidget().setLayoutDelegate(new SharpLayoutDelegate ());
		getButtonWidget().setBorder(itsBorder);
		getButtonWidget().addWidget(itsLabelWidget, SharpLayoutDelegate.C);
	}

	public void updateRenderer ()
	{
		Vector3f theSize = getButtonWidget().getSize();
		if (theSize != null)
		{
			itsBorder.setSize(theSize);
			itsBorder.setAppearance(getButtonWidget().isPressed() ? Appearances.RED : Appearances.BLUE);
			
			String theText = getButtonWidget().getModel ().getTitle ();
			itsLabelWidget.setTitle(theText);
		}
	}
}
