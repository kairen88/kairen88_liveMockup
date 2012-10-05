/*
 * Created on Apr 3, 2004
 */
package net.hddb.views.stackframe;

import javax.vecmath.Vector3f;

import net.basekit.layoutdelegates.SharpLayoutDelegate;
import net.basekit.models.treetable.DefaultTreeTableWidgetModel;
import net.basekit.models.treetable.TreeTableWidgetModel;
import net.basekit.widgets.treetable.TreeTableWidget;
import net.hddb.adapters.HDAdapter;
import net.hddb.models.HDModel;
import net.hddb.models.stackframe.HDMStackFrame;
import net.hddb.views.HDView;
import net.hddb.views.HDViewClass;
import net.hddb.views.map.HDVMap;

/**
 * 
 * @author gpothier
 */
public class HDVStackFrame extends HDView
{
	private HDVMap itsVariablesView;

	public HDVStackFrame (HDModel aModel)
	{
		super(aModel);
		createUI ();
	}
	
	private void createUI ()
	{
		itsVariablesView = new HDVMap (getHDMStackFrame().getVariables());
		itsVariablesView.setPreferredSize(new Vector3f (100, 500, 0));
		setLayoutDelegate(new SharpLayoutDelegate ());
		addWidget(itsVariablesView, SharpLayoutDelegate.C);
	}

	protected HDMStackFrame getHDMStackFrame ()
	{
		return (HDMStackFrame) getModel();
	}

	public HDViewClass getViewClass ()
	{
		return HDVCStackFrame.getInstance();
	}
}
