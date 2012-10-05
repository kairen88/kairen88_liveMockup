/*
 * Created on Mar 15, 2004
 */
package net.basekit.models.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A node in a tree structure. Can be used to build a tree model.
 * @author gpothier
 */
public class ExpandableTreeNode
{
	private Object itsUserObject;
	private ExpandableTreeNode itsParent;
	private List itsChildren;
	private boolean itsExpanded = true;

	public ExpandableTreeNode (Object aUserObject)
	{
		this (aUserObject, new ArrayList ());
	}

	public ExpandableTreeNode (Object aUserObject, List aChildren)
	{
		itsChildren = aChildren;
		itsUserObject = aUserObject;
	}

	


	/**
	 * An object that has a meaning for the user of the node.
	 */
	public Object getUserObject ()
	{
		return itsUserObject;
	}

	public void setUserObject (Object aUserObject)
	{
		itsUserObject = aUserObject;
	}
	
	/**
	 * Returns the parent of this node, or null if it has none.
	 */
	public ExpandableTreeNode getParent ()
	{
		return itsParent;
	}

	/**
	 * Sets the parent of this node.
	 * For internal use only (see {@link DefaultTreeNodeWidgetModel#add(int, Object)}.
	 */
	public void setParent (ExpandableTreeNode aParent)
	{
		itsParent = aParent;
	}
	
	/**
	 * @return The numbder of children of this node.
	 */
	public int getChildrenCount ()
	{
		return itsChildren.size ();
	}

	/**
	 * Returns the child at the given index.
	 */
	public ExpandableTreeNode getChildAt (int aIndex)
	{
		return (ExpandableTreeNode) itsChildren.get (aIndex);
	}

	/**
	 * Inserts an object into the list
	 * and sends appropriate notification.
	 */
	public void add (int aIndex, ExpandableTreeNode aNode)
	{
		itsChildren.add (aIndex, aNode);
		assert aNode.getParent() == null;
		aNode.setParent(this);
	}

	/**
	 * Adds an object at the end of the list
	 * and sends appropriate notification.
	 */
	public void add (ExpandableTreeNode aNode)
	{
		int theIndex = itsChildren.size ();
		add (theIndex, aNode);
	}

	/**
	 * Adds all the elements of the collection to the list
	 * and sends appropriate notification.
	 */
	public void addAll (int aIndex, Collection aCollection)
	{
		int theCount = aCollection.size ();
		itsChildren.addAll (aIndex, aCollection);
	}

	/**
	 * Adds all the elements of the collection at the end of the list
	 * and sends appropriate notification.
	 */
	public void addAll (Collection aCollection)
	{
		int theIndex = itsChildren.size ();
		addAll (theIndex, aCollection);
	}

	/**
	 * Removes the first occurence of the specified object
	 * and sends appropriate notification.
	 */
	public boolean remove (ExpandableTreeNode aNode)
	{
		int theIndex = itsChildren.indexOf (aNode);
		if (theIndex == -1) return false;

		itsChildren.remove (theIndex);
		aNode.setParent(null);
		return true;
	}

	public Object remove (int aIndex)
	{
		ExpandableTreeNode theNode = (ExpandableTreeNode) itsChildren.remove (aIndex);
		theNode.setParent(null);
		return theNode;
	}

	public boolean isExpanded ()
	{
		return itsExpanded;
	}
	
	public void setExpanded (boolean aExpanded)
	{
		itsExpanded = aExpanded;
	}

	
}
