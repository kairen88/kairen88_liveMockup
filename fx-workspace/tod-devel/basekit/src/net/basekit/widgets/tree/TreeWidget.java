/*
 * Created on Mar 15, 2004
 */
package net.basekit.widgets.tree;

import net.basekit.Message;
import net.basekit.Observer;
import net.basekit.layoutdelegates.SharpLayoutDelegate;
import net.basekit.models.list.AbstractListContentWidgetModel;
import net.basekit.models.selection.SelectionWidgetModel;
import net.basekit.models.tree.DefaultTreeWidgetModel;
import net.basekit.models.tree.ExpandableTreeNode;
import net.basekit.models.tree.TreeWidgetModel;
import net.basekit.models.tree.messages.NodeStateChangedMessage;
import net.basekit.widgets.Widget;
import net.basekit.widgets.list.LabelElementRenderer;
import net.basekit.widgets.list.ListElementRenderer;
import net.basekit.widgets.list.ListWidget;

/**
 * A widget that displays a tree.
 * The tree's content is specified by a {@link net.basekit.models.tree.TreeNodeWidgetModel},
 * the collapsed/expanded state of nodes by a {@link net.basekit.models.tree.TreeWidgetModel}
 * and the selection by a {@link net.basekit.models.selection.SelectionWidgetModel}
 * @author gpothier
 */
public class TreeWidget extends Widget
{
	private final TreeListWrapperModel itsTreeListWrapperModel = new TreeListWrapperModel ();
	private TreeWidgetModel itsModel;
	private SelectionWidgetModel itsSelectionModel;
	
	/**
	 * The renderer that creates tree node labels. It does not have
	 * to create the visual representation of the tree structure (ie it shouldn't bother
	 * about depth)
	 */
	private ListElementRenderer itsNodeElementRenderer;

	/**
	 * The underlying list widget. The tree widget simply provides custom model and renderers
	 * to a list widget.
	 */
	private ListWidget itsListWidget;
	private TreeListElementRenderer itsTreeListElementRenderer;
	
	/**
	 * Constructs a tree widget with a default node model based on the 
	 * specified root node.
	 */
	public TreeWidget (ExpandableTreeNode aRootNode)
	{
		this (new DefaultTreeWidgetModel (aRootNode));
	}
	
	public TreeWidget (TreeWidgetModel aModel)
	{
		setModel(aModel);
		setNodeElementRenderer(new LabelElementRenderer ());
		createUI();
	}

	private void createUI ()
	{
		setLayoutDelegate(new SharpLayoutDelegate ());
		itsListWidget = new ListWidget (itsTreeListWrapperModel);
		itsTreeListElementRenderer = new TreeListElementRenderer ();
		itsTreeListElementRenderer.setTreeModel(getModel());
		itsTreeListElementRenderer.setNodeElementRenderer(getNodeElementRenderer());
		itsListWidget.setElementRenderer(itsTreeListElementRenderer);
		
		
		addWidget(itsListWidget, SharpLayoutDelegate.C);
	}
	
	public Object getRootNode ()
	{
		return itsModel.getRootNode();
	}

	public TreeWidgetModel getModel ()
	{
		return itsModel;
	}
	
	public void setModel (TreeWidgetModel aStateModel)
	{
		if (itsModel != null) itsModel.removeObserver(itsTreeListWrapperModel);
		itsModel = aStateModel;
		if (itsModel != null) 
		{
			itsModel.addObserver(itsTreeListWrapperModel);
			if (itsTreeListElementRenderer != null) itsTreeListElementRenderer.setTreeModel(aStateModel);
			itsTreeListWrapperModel.setModel(itsModel);
		}
		
	}

	public ListElementRenderer getNodeElementRenderer ()
	{
		return itsNodeElementRenderer;
	}
	
	public void setNodeElementRenderer (ListElementRenderer aNodeElementRenderer)
	{
		itsNodeElementRenderer = aNodeElementRenderer;
		if (itsTreeListElementRenderer != null) itsTreeListElementRenderer.setNodeElementRenderer(aNodeElementRenderer);
	}
	


	
}
