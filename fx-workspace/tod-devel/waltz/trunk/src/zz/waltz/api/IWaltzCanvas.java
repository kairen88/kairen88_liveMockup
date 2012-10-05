/*
 * Created on Dec 21, 2004
 */
package zz.waltz.api;

import java.awt.LayoutManager;

import javax.swing.JComponent;

import zz.utils.properties.IProperty;
import zz.utils.properties.IRWProperty;
import zz.waltz.api.action.IActionStyle;
import zz.waltz.api.action.IActionModel;
import zz.waltz.api.property.IPropertyStyle;

/**
 * Object to which waltz components render their contents.
 * @author gpothier
 */
public interface IWaltzCanvas
{
	public void setLayout (LayoutManager aLayoutManager);

	/**
	 * Creates a new canvas and adds it to this canvas
	 * @param aLayoutManager The layout manager of the new canvas
	 * @param aLayoutConstraints The layout constraints for positioning 
	 * the new canvas within this canvas
	 * @return The created canvas
	 */
	public IWaltzCanvas createCanvas (LayoutManager aLayoutManager, Object aLayoutConstraints);
	
	/**
	 * Same as {@link #createCanvas(LayoutManager, Object)}, with no
	 * layout manager.
	 */
	public IWaltzCanvas createCanvas (Object aLayoutConstraints);
	
	/**
	 * Adds a swing comoponent to this canvas
	 * @param aComponent The component to add.
	 * @param aLayoutConstraints The layout constraints for positioning 
	 * the component within this canvas
	 */
	public void add (JComponent aComponent, Object aLayoutConstraints);
	
	/**
	 * Adds a waltz comoponent to this canvas
	 * @param aComponent The component to add.
	 * @param aLayoutConstraints The layout constraints for positioning 
	 * the component within this canvas
	 */
	public void add (WaltzComponent aComponent, Object aLayoutConstraints);
	
	/**
	 * Adds an action proxy to this container. An action proxy can be a button,
	 * or a hyperlink-style lable, etc. that can be activated, and has
	 * and enabled state.
	 * @param aStyle Defines the style of the proxy. 
	 * @param aModel Defines the behaviour of the action.
	 * @param aLayoutConstraints The layout constraints for positioning 
	 * the proxy within this canvas
	 */
	public void addAction (
			IActionStyle aStyle, 
			IActionModel aModel, 
			Object aLayoutConstraints);
	
	/**
	 * Adds an property editor to this container. 
	 * @param aStyle Defines the style of the editor. 
	 * @param aProperty The property to edit.
	 * @param aEnabled A property that indicates the enabled state of the editor.
	 * @param aLayoutConstraints The layout constraints for positioning 
	 * the editor within this canvas
	 */
	public <P extends IProperty> void addProperty (
			IPropertyStyle<? super P> aStyle, 
			P aProperty, 
			IProperty<Boolean> aEnabled,
			Object aLayoutConstraints);
	
	/**
	 * Adds an property editor to this container. 
	 * @param aStyle Defines the style of the editor. 
	 * @param aProperty The property to edit.
	 * @param aLayoutConstraints The layout constraints for positioning 
	 * the editor within this canvas
	 */
	public <P extends IProperty> void addProperty (
			IPropertyStyle<? super P> aStyle, 
			P aProperty, 
			Object aLayoutConstraints);
	
	/**
	 * Adds a small invisible separator, without layout constraints.
	 * This has a meaning only for some layouts, such as {@link java.awt.FlowLayout}
	 */
	public void addSeparator ();
	
	/**
	 * Adds a label to this canvas.
	 * @param aText A text for the label. This can be HTML text.
	 * @param aLayoutConstraints The layout constraints for positioning 
	 * the label within this canvas
	 */
	public void label (String aText, Object aLayoutConstraints);
}
