/*
 * Created on Dec 21, 2004
 */
package zz.waltz.api.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import zz.utils.AbstractFormatter;
import zz.utils.Formatter;
import zz.utils.tree.ITree;
import zz.utils.tree.SimpleTree;
import zz.utils.tree.SimpleTreeBuilder;
import zz.utils.tree.SimpleTreeNode;
import zz.waltz.api.action.DefaultActionModel;
import zz.waltz.api.action.IActionModel;

/**
 * Builds a swing menu based on a {@link zz.utils.tree.ITree}.
 * This class provides some static methods that can be used to build
 * a menu based on an existing tree.
 * It can also be instantiated to help build the tree itself.
 * @author gpothier
 */
public class MenuBuilder extends SimpleTreeBuilder<IActionModel>
{
	public SimpleTreeNode<IActionModel> submenu (String aTitle, SimpleTreeNode<IActionModel>... aItems)
	{
		return node(new DefaultActionModel(aTitle), aItems);
	}
	
	public SimpleTreeNode<IActionModel> submenu (
			String aTitle, 
			String aDescription,
			ImageIcon aIcon, 
			SimpleTreeNode<IActionModel>... aItems)
	{
		return node(new DefaultActionModel(aTitle, aDescription, aDescription, aIcon, true), aItems);
	}
	
	public SimpleTreeNode<IActionModel> item(IActionModel aActionModel)
	{
		return leaf(aActionModel);
	}
	
	/**
	 * Builds a popup menu with the specified items.
	 */
	public JPopupMenu buildPopupMenu (SimpleTreeNode<IActionModel>... aItems)
	{
		root (aItems);
		return buildPopupMenu(getTree());
	}
	
	
	public static <N, V extends IActionModel> JPopupMenu buildPopupMenu(
			ITree<N, V> aTree)
	{
		return buildPopupMenu(
				aTree,
				new IMenuListener<V>()
				{
					public void menuItemSelected(JMenuItem aMenuItem, V aItem)
					{
						aItem.performed(aMenuItem);
					}
				},
				new AbstractFormatter<V>()
				{
					protected String getText(V aItem, boolean aHtml)
					{
						return aItem.pName().get();
					}
					
					public ImageIcon getIcon(V aItem)
					{
						return aItem.pIcon().get();
					}
				});
	}
	
	/**
	 * Builds a popup menu based on the structure of the given tree.
	 * The selection of a menu item calls {@link IMenuListener#menuItemSelected(V)} on
	 * the specified listener.
	 * <p>
	 * Only leaf nodes are selectable.
	 * 
	 * @param <N> Type of node in the tree
	 * @param <V> Type of values.
	 * @param aTree The tree whose structure is used to build the menu
	 * @param aListener The listener that is notified when a menu item is selected.
	 * @param aFormatter The formatter that provide texts for the menu items.
	 * @return The created menu.
	 */
	public static <N, V> JPopupMenu buildPopupMenu (
			ITree<N, V> aTree, 
			IMenuListener<V> aListener,
			Formatter<V> aFormatter)
	{
		JPopupMenu thePopupMenu = new JPopupMenu();
		N theRoot = aTree.getRoot();
		
		if (! aTree.isLeaf(theRoot)) for (N theChild : aTree.getChildren(theRoot))
			buildNode(aTree, thePopupMenu, theChild, aListener, aFormatter);
		
		return thePopupMenu;
	}
	
	/**
	 * Adds a new child menu item to the specified parent component,
	 * representing the specified child node.
	 */
	private static <N, V> void buildNode (
			ITree<N, V> aTree, 
			JComponent aParentComponent,
			N aChildNode,
			final IMenuListener<V> aListener,
			Formatter<V> aFormatter)
	{
		JMenuItem theMenuItem;
		final V theValue = aTree.getValue(aChildNode);
		
		if (aTree.isLeaf(aChildNode))
		{
			theMenuItem = new JMenuItem();
			final JMenuItem hack = theMenuItem;
			theMenuItem.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent aE)
						{
							aListener.menuItemSelected(hack, theValue);
						}
					});
		}
		else
		{
			theMenuItem = new JMenu();
			for (N theChild : aTree.getChildren(aChildNode))
				buildNode(aTree, theMenuItem, theChild, aListener, aFormatter);
		}
		
		theMenuItem.setText(aFormatter.getHtmlText(theValue));
		theMenuItem.setIcon(aFormatter.getIcon(theValue));
		
		aParentComponent.add (theMenuItem);
	}
	
	/**
	 * Interface for objects that listen for selection in menus.
	 * @author gpothier
	 */
	public interface IMenuListener<V> 
	{
		public void menuItemSelected (JMenuItem aMenuItem, V aItemValue);
	}
}
