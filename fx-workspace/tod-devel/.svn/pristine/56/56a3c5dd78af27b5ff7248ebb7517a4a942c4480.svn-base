/*
 * Created on Dec 21, 2004
 */
package zz.waltz.impl;

import java.awt.Dimension;
import java.awt.LayoutManager;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import zz.utils.properties.IProperty;
import zz.utils.properties.SimpleProperty;
import zz.utils.ui.TransparentPanel;
import zz.waltz.api.IWaltzCanvas;
import zz.waltz.api.WaltzComponent;
import zz.waltz.api.action.IActionModel;
import zz.waltz.api.action.IActionStyle;
import zz.waltz.api.action.enable.ConstantCondition;
import zz.waltz.api.property.IPropertyStyle;

/**
 * A canvas that wraps around a swing component.
 * @author gpothier
 */
public class SwingWrapperCanvas implements IWaltzCanvas
{
	private JComponent itsComponent;
	
	public SwingWrapperCanvas(JComponent aComponent)
	{
		itsComponent = aComponent;
	}

	public void setLayout(LayoutManager aLayoutManager)
	{
		itsComponent.setLayout(aLayoutManager);
	}

	public IWaltzCanvas createCanvas(LayoutManager aLayoutManager, Object aLayoutConstraints)
	{
		JPanel thePanel = new JPanel(aLayoutManager);
		itsComponent.add (thePanel, aLayoutConstraints);
		return new SwingWrapperCanvas(thePanel);
	}

	public IWaltzCanvas createCanvas(Object aLayoutConstraints)
	{
		return createCanvas(null, aLayoutConstraints);
	}

	public void add(JComponent aComponent, Object aLayoutConstraints)
	{
		itsComponent.add (aComponent, aLayoutConstraints);
	}
	
	public void add(WaltzComponent aComponent, Object aLayoutConstraints)
	{
		itsComponent.add (aComponent.getSwingComponent(), aLayoutConstraints);
	}
	
	public void addAction(
			IActionStyle aStyle, 
			IActionModel aModel, 
			Object aLayoutConstraints)
	{
		JComponent theComponent = aStyle.createComponent(aModel);
		add (theComponent, aLayoutConstraints);
	}
	
	public <P extends IProperty> void addProperty (
			IPropertyStyle<? super P> aStyle, 
			P aProperty, 
			IProperty<Boolean> aEnabled,
			Object aLayoutConstraints)
	{
		JComponent theComponent = aStyle.createComponent(aProperty, aEnabled);
		add (theComponent, aLayoutConstraints);
	}
	
	public <P extends IProperty> void addProperty (
			IPropertyStyle<? super P> aStyle, 
			P aProperty, 
			Object aLayoutConstraints)
	{
		addProperty(aStyle, aProperty, new ConstantCondition(true), aLayoutConstraints);
	}
	
	public void addSeparator()
	{
		TransparentPanel theSeparator = new TransparentPanel();
		theSeparator.setPreferredSize(new Dimension(5, 5));
		add (theSeparator, null);
	}
	
	public void label(String aText, Object aLayoutConstraints)
	{
		add (new JLabel(aText), aLayoutConstraints);
	}
}
