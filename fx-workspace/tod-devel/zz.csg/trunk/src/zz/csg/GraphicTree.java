/*
 * Created on Jul 19, 2005
 */
package zz.csg;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;

import zz.csg.api.IGraphicContainer;
import zz.csg.api.IGraphicObject;
import zz.utils.tree.AbstractTree;
import zz.utils.tree.SwingTreeModel;
import zz.utils.ui.UniversalRenderer;

/**
 * Models a tree of graphic objects.
 * @author gpothier
 */
public class GraphicTree extends AbstractTree<IGraphicObject, IGraphicObject> 
{
	private IGraphicObject itsRoot;

	public GraphicTree(IGraphicObject aRoot)
	{
		itsRoot = aRoot;
	}

	public IGraphicObject getChild(IGraphicObject aParent, int aIndex)
	{
		if (aParent instanceof IGraphicContainer)
		{
			IGraphicContainer theContainer = (IGraphicContainer) aParent;
			return theContainer.pChildren().get(aIndex);
		}
		else throw new IllegalArgumentException();
	}

	public int getChildCount(IGraphicObject aParent)
	{
		if (aParent instanceof IGraphicContainer)
		{
			IGraphicContainer theContainer = (IGraphicContainer) aParent;
			return theContainer.pChildren().size();
		}
		else return 0;
	}

	public int getIndexOfChild(IGraphicObject aParent, IGraphicObject aChild)
	{
		if (aParent instanceof IGraphicContainer)
		{
			IGraphicContainer theContainer = (IGraphicContainer) aParent;
			return theContainer.pChildren().indexOf(aChild);
		}
		else throw new IllegalArgumentException();
	}

	public IGraphicObject getParent(IGraphicObject aNode)
	{
		return aNode.getParent();
	}

	public IGraphicObject getRoot()
	{
		return itsRoot;
	}

	public IGraphicObject getValue(IGraphicObject aNode)
	{
		return aNode;
	}

	public IGraphicObject setValue(IGraphicObject aNode, IGraphicObject aValue)
	{
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Displays a frame containing a JTree representing the given scenegraph.
	 * @param aGraphicObject
	 */
	public static void showTree (IGraphicObject aGraphicObject)
	{
		JFrame theFrame = new JFrame("GraphicObject tree");
		theFrame.setContentPane(new JScrollPane(createJTree(aGraphicObject)));
		theFrame.pack();
		theFrame.setVisible(true);
	}
	
	/**
	 * Creates a JTree that displays the given scene graph.
	 */
	public static JTree createJTree (IGraphicObject aGraphicObject)
	{
		GraphicTree theTree = new GraphicTree(aGraphicObject);
		SwingTreeModel<IGraphicObject, IGraphicObject> theModel = new SwingTreeModel<IGraphicObject, IGraphicObject>(theTree);
		JTree theJTree = new JTree (theModel);
		theJTree.setCellRenderer(new Renderer());
		return theJTree;
	}
	
	
	public static class Renderer extends UniversalRenderer<IGraphicObject>
	{
		@Override
		protected String getName(IGraphicObject aObject)
		{
			return aObject.toString();
		}
		
	}
}
