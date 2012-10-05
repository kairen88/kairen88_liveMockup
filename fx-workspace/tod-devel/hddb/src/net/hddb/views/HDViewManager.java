/*
 * Created on Feb 25, 2004
 */
package net.hddb.views;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import net.hddb.HDManager;
import net.hddb.adapters.HDAdapterPipe;
import net.hddb.adapters.HDAdapterClass;
import net.hddb.adapters.HDAdapterManager;
import net.hddb.views.list.HDVCList;
import net.hddb.views.map.HDVCMap;
import net.hddb.views.stackframe.HDVCStackFrame;
import net.hddb.views.text.HDVCText;
import zz.utils.ListMap;

/**
 * Manages the available views.
 * @author gpothier
 */
public class HDViewManager extends HDManager
{
	private static final HDViewManager INSTANCE = new HDViewManager();
	
	public static HDViewManager getInstance()
	{
		return INSTANCE;
	}
	
	private HDViewManager() 
	{
		registerViews();
	}
	
	private void registerViews ()
	{
		registerViewClass(HDVCList.getInstance());
		registerViewClass(HDVCMap.getInstance());
		registerViewClass(HDVCStackFrame.getInstance());
		registerViewClass(HDVCText.getInstance());
	}

	/**
	 * Registers a view class so that it is available to this manager.
	 */
	public void registerViewClass (HDViewClass aClass)
	{
		registerClass(aClass);
	}
	
	/**
	 * Returns all avaiable view classes for the specified adapter class.
	 */
	public Collection getViewClassesFor (Class aClass)
	{
		return getHDClassesFor(aClass);
	}
	
	/**
	 * Determines all the view chains available for the specified element.
	 * @return A collection of {@link HDViewChain}.
	 */
	public Collection getViewChainsFor (Object aElement)
	{
		Collection theAdapterChains = HDAdapterManager.getInstance().getAdapterPipesFor(aElement);
		Set theViewChains = new HashSet ();
		
		for (Iterator theIterator = theAdapterChains.iterator(); theIterator.hasNext();)
		{
			HDAdapterPipe theAdapterPipe = (HDAdapterPipe) theIterator.next();
			Class theProducedClass = theAdapterPipe.getProducedClass();
		
			Collection theViewClasses = getViewClassesFor(theProducedClass);
			for (Iterator theIterator2 = theViewClasses.iterator(); theIterator2.hasNext();)
			{
				HDViewClass theViewClass = (HDViewClass) theIterator2.next();
				HDViewChain theViewChain = new HDViewChain (theAdapterPipe, theViewClass);
				theViewChains.add (theViewChain);
			}
		}
		
		return theViewChains;
	}
}
