/*
 * Created on Mar 21, 2004
 */
package net.basekit.widgets.tree;

import javax.vecmath.Vector3f;

import net.basekit.Message;
import net.basekit.layoutdelegates.AxisLayoutDelegate;
import net.basekit.models.action.ActionWidgetModel;
import net.basekit.models.action.DefaultActionWidgetModel;
import net.basekit.models.action.messages.ActionTriggeredMessage;
import net.basekit.models.tree.TreeWidgetModel;
import net.basekit.widgets.Widget;
import net.basekit.widgets.button.ButtonWidget;
import net.basekit.widgets.label.LabelWidget;
import net.basekit.widgets.list.ListElementRenderer;
import net.basekit.widgets.list.ListElementWidget;


/**
 * This element renderer assembles a widget that represents the tree structure with a custom
 * component that serves as node label. 
 * <p>
 * This object needs to know the element renderer and the tree model.
 * @author gpothier
 */
public class TreeListElementRenderer implements ListElementRenderer
{

	
	/**
	 * The state model of the represented tree.
	 */
	private TreeWidgetModel itsTreeModel;
	
	/**
	 * The client-provided renderer that renders tree node labels.
	 */
	private ListElementRenderer itsNodeElementRenderer;

	
	public ListElementRenderer getNodeElementRenderer ()
	{
		return itsNodeElementRenderer;
	}
	
	public void setNodeElementRenderer (ListElementRenderer aNodeElementRenderer)
	{
		itsNodeElementRenderer = aNodeElementRenderer;
	}
	
	public TreeWidgetModel getTreeModel ()
	{
		return itsTreeModel;
	}
	
	public void setTreeModel (TreeWidgetModel aTreeModel)
	{
		itsTreeModel = aTreeModel;
	}

	public ListElementWidget createWidget (Object aElement, int aIndex)
	{
		return new NodeElementWidget (aElement, aIndex);
	}

	public final void updateWidget (ListElementWidget aWidget, boolean aSelected, boolean aFocused)
	{
		NodeElementWidget theWidget = (NodeElementWidget) aWidget;
		updateWidget(theWidget, aSelected, aFocused);
	}
	
	/**
	 * This method can be subclassed. It is similar to {@link #updateWidget(ListElementWidget, boolean, boolean)}
	 * it has the widget cast to the proper type.
	 */
	public void updateWidget (NodeElementWidget aWidget, boolean aSelected, boolean aFocused)
	{
//		String theText = itsFormatter.getText(aWidget.getElement());
//		aWidget.setTitle(theText);
//		aWidget.setBorderAppearance (aSelected ? AppearanceFactory.RED : AppearanceFactory.BLUE);
		aWidget.updateWidget(aSelected, aFocused);
	}
	
	/**
	 * Represents tree nodes. Aggregates a padding widget, a collapse/expand button and
	 * the node's label proper.
	 * 
	 * @author gpothier
	 */
	private class NodeElementWidget extends Widget implements ListElementWidget
	{
		/**
		 * The widget created by the client-specified {@link ListElementRenderer}
		 * returned by {@link TreeWidget#getNodeElementRenderer()}
		 */
		private ListElementWidget itsClientWidget;
		
		/**
		 * The widget that permits to shift node labels according to their depth.
		 */
		private Widget itsPadWidget;
		
		private ButtonWidget itsExpandButton;
		private DefaultActionWidgetModel itsExpandAction;
		
		private Object itsNode;
		private int itsIndex;

		public NodeElementWidget (Object aNode, int aIndex)
		{
			itsNode = aNode;
			itsIndex = aIndex;
			
			createUI ();
		}
		
		private void createUI ()
		{
			setPickingAware(false);
			setLayoutDelegate(new AxisLayoutDelegate (AxisLayoutDelegate.X_POSITIVE));
			Object theElement = getTreeModel().getUserObject(itsNode);
			itsClientWidget = getNodeElementRenderer().createWidget(theElement, getIndex());
			
			itsPadWidget = new Widget ();
			itsExpandAction = new DefaultActionWidgetModel ();
			itsExpandAction.addObserver(this);
			itsExpandButton = new ButtonWidget (itsExpandAction);
			
			addWidget (itsPadWidget);
			addWidget (itsExpandButton);
			addWidget ((Widget) itsClientWidget);
		}

		public Object getElement ()
		{
			return itsNode;
		}

		public int getIndex ()
		{
			return itsIndex;
		}

		public void updateWidget (boolean aSelected, boolean aFocused)
		{
			getNodeElementRenderer().updateWidget(itsClientWidget, aSelected, aFocused);
			
			boolean theExpanded = getTreeModel ().isNodeExpanded(itsNode);
			int theDepth = getTreeModel().getDepth(itsNode);
			
			itsExpandAction.setTitle(theExpanded ? "-" : "+");
			itsPadWidget.setMinimumSize(new Vector3f (theDepth * 5, 1, 0));
		}

		public void processMessage (Message aMessage)
		{
			if (aMessage instanceof ActionTriggeredMessage)
			{
				// In response to triggering of expand action.
				ActionTriggeredMessage theMessage = (ActionTriggeredMessage) aMessage;
				getTreeModel ().toggleNodeExpansion(itsNode);
			}
			super.processMessage(aMessage);
		}
	}

}