/*
 * Created on Feb 25, 2004
 */
package net.hddb.views.list;

import java.util.Iterator;

import net.hddb.adapters.HDAMessage;
import net.hddb.adapters.HDAdapter;
import net.basekit.layoutdelegates.AxisLayoutDelegate;
import net.basekit.Message;
import net.hddb.models.HDModel;
import net.hddb.models.list.HDMList;
import net.hddb.ui.elementinstance.HDEIContainer;
import net.hddb.ui.elementinstance.HDEIUtils;
import net.hddb.ui.elementinstance.HDElementInstance;
import net.hddb.views.HDView;
import net.hddb.views.HDViewClass;
import net.hddb.utils.Constraints;

/**
 * @author gpothier
 */
public class HDVList extends HDView implements HDEIContainer
{
	public HDVList (HDModel aModel)
	{
		super (aModel);
		createUI ();
		createInitialList();
	}

	public HDViewClass getViewClass ()
	{
		return HDVCList.getInstance ();
	}

	/**
	 * Sets up static UI.
	 */
	private void createUI()
	{
		setLayoutDelegate(new AxisLayoutDelegate (AxisLayoutDelegate.X_POSITIVE));
	}
	
	private void createInitialList ()
	{
		int theCount = getHDMList().getCount();
		for (int i=0;i<theCount;i++)
		{
			Object aElement = getHDMList().getChild(i);
			HDElementInstance theElementInstance = new HDElementInstance (this, aElement);
			addWidget(theElementInstance);			
		}
	}

	private HDMList getHDMList ()
	{
		return (HDMList) getModel();
	}

	public void processMessage(Message aMessage)
	{
		clearWidgets();
		createInitialList();
		invalidateLayout ();
	}

	public Constraints getChildrenViewConstraints ()
	{
		return getModel ().getChildrenViewConstraints ();
	}
}
