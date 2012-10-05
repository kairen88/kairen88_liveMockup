/*
 * Created on 09-sep-2004
 */
package zz.csg.api;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

import zz.utils.list.IList;

/**
 * Utilities for working with graphic objects
 * @author gpothier
 */
public class GraphicUtils
{
	/**
	 * Prefix for reserved standard objects.
	 */
	private static final String RESEVED_GO_PREFIX = "{*}";

	public static boolean canMoveToBack (IGraphicObject aGraphicObject)
	{
		return canMoveBackward(aGraphicObject);
	}
	
	public static void moveToBack (IGraphicObject aGraphicObject)
	{
		IGraphicContainer theParent = aGraphicObject.getParent();
		Iterable<GraphicObjectContext> theContexts = 
			aGraphicObject.getAttachedContexts();
		
		System.out.println("Attached contexts: "+((List)theContexts).size());
		
		IList<IGraphicObject> theChildren = theParent.pChildren();
		
		theChildren.remove(aGraphicObject);
		theChildren.add(0, aGraphicObject);
	}
	
	public static boolean canMoveToFront (IGraphicObject aGraphicObject)
	{
		return canMoveForward(aGraphicObject);
	}

	public static void moveToFront (IGraphicObject aGraphicObject)
	{
		IGraphicContainer theParent = aGraphicObject.getParent();
		
		IList<IGraphicObject> theChildren = theParent.pChildren();
		
		theChildren.remove(aGraphicObject);
		theChildren.add(aGraphicObject);

	}
	
	public static boolean canMoveBackward (IGraphicObject aGraphicObject)
	{
		IGraphicContainer theParent = aGraphicObject.getParent();
		IList<IGraphicObject> theChildren = theParent.pChildren();
		
		int theIndex = theChildren.indexOf(aGraphicObject);

		return 
			aGraphicObject.getParent() != null
			&& theIndex > 0; 
	
	}

	public static void moveBackward (IGraphicObject aGraphicObject)
	{
		IGraphicContainer theParent = aGraphicObject.getParent();
		
		IList<IGraphicObject> theChildren = theParent.pChildren();
		
		int theIndex = theChildren.indexOf(aGraphicObject);
		theChildren.remove(aGraphicObject);
		theChildren.add(theIndex-1, aGraphicObject);
	}
	
	public static boolean canMoveForward (IGraphicObject aGraphicObject)
	{
		IGraphicContainer theParent = aGraphicObject.getParent();
		IList<IGraphicObject> theChildren = theParent.pChildren();
		
		int theIndex = theChildren.indexOf(aGraphicObject);

		return 
			aGraphicObject.getParent() != null
			&& theIndex < theChildren.size() - 1; 

	}

	public static void moveForward (IGraphicObject aGraphicObject)
	{
		IGraphicContainer theParent = aGraphicObject.getParent();
		
		IList<IGraphicObject> theChildren = theParent.pChildren();
		
		int theIndex = theChildren.indexOf(aGraphicObject);
		theChildren.remove(aGraphicObject);
		theChildren.add(theIndex+1, aGraphicObject);

	}
	
	/**
	 * Indicates if the specified graphic object is reserved
	 * (has a reserved name).
	 */
	public static boolean isReserved (IGraphicObject aGraphicObject)
	{
		String theName = aGraphicObject.pName().get();
		return theName != null && theName.startsWith(GraphicUtils.RESEVED_GO_PREFIX);
	}
	
	/**
	 * Converts the given name into a reserved name.
	 */
	public static String makeReservedName (String aName)
	{
		return GraphicUtils.RESEVED_GO_PREFIX+aName;
	}

	/**
	 * Walk up the hierarchy until we find the root node or the given stopper.
	 * We transform the coordinates along the path.
	 * @param aStopper The graphic object that stops the traversal. If not null,
	 * traversal stops at this object, and this object is returned. If null,
	 * traversal continues until the root is found.
	 */
	public static IGraphicContainer findRoot (
			IGraphicObject aStart,
			GraphicObjectContext aContext,
			AffineTransform aTransform,
			IGraphicContainer aStopper)
	{
		GraphicObjectContext theRootContext = null;
		
		IGraphicObject theCurrentObject = aStart;
		GraphicObjectContext theCurrentContext = aContext;
		do
		{
			while (theCurrentObject != null)
			{				
				AffineTransform theTransform = theCurrentObject.pTransform().get();
				if (theTransform != null) aTransform.preConcatenate(theTransform);
				
				if (aStopper != null && aStopper == theCurrentObject)
					return aStopper;
				
				theCurrentObject = theCurrentObject.getParent();
			}

			if (theCurrentContext == null) break;
			
			theRootContext = theCurrentContext;
			theCurrentObject = theCurrentContext.getProxyGraphicObject();
			theCurrentContext = theCurrentContext.getParentContext();
		} 
		while (true);

		return (IGraphicContainer) theRootContext.getRootGraphicObject();
	}
	
	/**
	 * Transforms a rectangle from the children coordinate system to local coordinate
	 * system for a given graphic object.
	 * @see IGraphicObject#childrenToLocal(Point2D, Point2D)
	 */
	public static Rectangle2D childrenToLocal (IGraphicObject aGraphicObject, Rectangle2D aBounds)
	{
		Point2D thePoint1 = new Point2D.Double(aBounds.getMinX(), aBounds.getMinY());
		Point2D thePoint2 = new Point2D.Double(aBounds.getMaxX(), aBounds.getMaxY());
		aGraphicObject.childrenToLocal(thePoint1, thePoint1);
		aGraphicObject.childrenToLocal(thePoint2, thePoint2);
		
		double theX1 = thePoint1.getX();
		double theY1 = thePoint1.getY();
		
		Rectangle2D.Double theBounds = new Rectangle2D.Double(theX1, theY1, 0, 0);
		theBounds.add(thePoint2);
		return theBounds;
	}

	/**
	 * Transforms a rectangle from the local coordinate system to children coordinate
	 * system for a given graphic object.
	 * @see IGraphicObject#localToChildren(Point2D, Point2D)
	 */
	public static Rectangle2D localToChildren (IGraphicObject aGraphicObject, Rectangle2D aBounds)
	{
		Point2D thePoint1 = new Point2D.Double(aBounds.getMinX(), aBounds.getMinY());
		Point2D thePoint2 = new Point2D.Double(aBounds.getMaxX(), aBounds.getMaxY());
		aGraphicObject.localToChildren(thePoint1, thePoint1);
		aGraphicObject.localToChildren(thePoint2, thePoint2);
		
		double theX1 = thePoint1.getX();
		double theY1 = thePoint1.getY();
		double theX2 = thePoint2.getX();
		double theY2 = thePoint2.getY();
		
		return new Rectangle2D.Double(theX1, theY1, theX2-theX1, theY2-theY1);
	}
	
	
}

