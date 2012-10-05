/*
 * Created on Feb 24, 2004
 */
package net.hddb.adapters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import zz.utils.ListMap;

import net.hddb.HDManager;
import net.hddb.adapters.impl.debug.eclipse.stackframe.HDACEclipseStackFrame;
import net.hddb.adapters.impl.debug.eclipse.stackframe.HDACValue2Text;
import net.hddb.adapters.impl.debug.eclipse.stackframe.HDACValue2Variables;
import net.hddb.adapters.impl.string2text.HDACString2Text;
import net.hddb.adapters.impl.text2list.HDACText2List;
import net.hddb.adapters.impl.utilsmap2map.HDACUtilsMap2Map;
import net.hddb.models.HDModel;

/**
 * 
 * @author gpothier
 */
public class HDAdapterManager extends HDManager
{
	private static final HDAdapterManager INSTANCE = new HDAdapterManager();
	
	public static HDAdapterManager getInstance()
	{
		return INSTANCE;
	}
	
	/**
	 * Maps classes to lists of chains.
	 */
	private ListMap itsPipesMap = new ListMap ();

	private HDAdapterManager()
	{
		registerClasses();
	}
	
	private void registerClasses ()
	{
		//TODO: automatize that.
		registerClass(HDACString2Text.getInstance());
		registerClass(HDACText2List.getInstance());
		registerClass(HDACEclipseStackFrame.getInstance());
		registerClass(HDACUtilsMap2Map.getInstance());
		registerClass(HDACValue2Variables.getInstance());
		registerClass(HDACValue2Text.getInstance());
	}
	
	/**
	 * Registers an adapter class so that it can be used by the manager.
	 */
	public void registerAdapterClass (HDAdapterClass aClass)
	{
		registerClass(aClass);
	}
	
	/**
	 * Get all the adapter pipes that can be applied to the specified element.
	 * @return A list of {@link HDAdapterPipe}
	 */
	public Collection getAdapterPipesFor (Object aElement)
	{
		List thePipes = itsPipesMap.getList(aElement.getClass());
		if (thePipes == null)
		{
			thePipes = new ArrayList ();
			Set theTargetsSet = new HashSet ();

			// 1- We check if we can use an identity pipe.
			if (aElement instanceof HDModel)
			{
				HDModel theModel = (HDModel) aElement;
				HDIdentityAdapterPipe theAdapterPipe = new HDIdentityAdapterPipe (theModel.getClass());

				thePipes.add (theAdapterPipe);
//				theTargetsSet.add (theAdapterClass);
			}

			// 2- We build chain pipes.
			Collection theAdapterClasses = getHDClassesFor(aElement);
			fillChains(thePipes, theTargetsSet, theAdapterClasses, null);
			itsPipesMap.put (aElement.getClass(), thePipes);
		}
		return thePipes;
	}
	
	private void fillChains (List aPipes, Set aTargets, Collection aAdapterClasses, HDChainAdapterPipe aCurrentChain)
	{
		List theNewChains = new ArrayList ();
		for (Iterator theIterator = aAdapterClasses.iterator(); theIterator.hasNext();)
		{
			HDAdapterClass theAdapterClass = (HDAdapterClass) theIterator.next();
			
			if (aTargets.add (theAdapterClass))
			{
				HDAdapterPipe theAdapterPipe = new HDChainAdapterPipe (theAdapterClass, aCurrentChain);
				aPipes.add (theAdapterPipe);	
				theNewChains.add (theAdapterPipe);
			}
		}
		
		for (Iterator theIterator = theNewChains.iterator(); theIterator.hasNext();)
		{
			HDChainAdapterPipe theAdapterPipe = (HDChainAdapterPipe) theIterator.next();
			Class theProducedClass = theAdapterPipe.getProducedClass();
			Collection theAdapterClasses = getHDClassesFor(theProducedClass);
			fillChains(aPipes, aTargets, theAdapterClasses, theAdapterPipe);
		}
		
	}
}
