/*
 * Created on May 29, 2004
 */
package zz.csg.impl.edition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import zz.csg.api.edition.IGraphicObjectController;
import zz.csg.api.edition.IHandle;


/**
 * A default implementation of graphic object controller
 * @author gpothier
 */
public class DefaultGraphicObjectController implements IGraphicObjectController
{
	private IHandle itsPositionHandle; 
	private List<IHandle> itsControlPoints;
	private IHandle itsExtremity1Handle;
	private IHandle itsExtremity2Handle;
	private IGraphicObjectEditor itsEditor;
	
	public DefaultGraphicObjectController(
			IHandle aPositionHandle,
			IHandle[] aControlPoints, 
			IHandle aExtremity1Handle,
			IHandle aExtremity2Handle,
			IGraphicObjectEditor aEditor)
	{
		this (
				aPositionHandle, 
				new ArrayList<IHandle> (Arrays.asList(aControlPoints)),
				aExtremity1Handle, 
				aExtremity2Handle,
				aEditor);
	}
	
	public DefaultGraphicObjectController(
			IHandle aPositionHandle,
			List<IHandle> aControlPoints, 
			IHandle aExtremity1Handle,
			IHandle aExtremity2Handle,
			IGraphicObjectEditor aEditor)
	{
		itsPositionHandle = aPositionHandle;
		itsControlPoints = aControlPoints;
		itsExtremity1Handle = aExtremity1Handle;
		itsExtremity2Handle = aExtremity2Handle;
		itsEditor = aEditor;
	}
	
	public IHandle getPositionHandle()
	{
		return itsPositionHandle;
	}
	
	public List<IHandle> getControlPoints()
	{
		return itsControlPoints;
	}

	public IHandle getExtremity1Handle()
	{
		return itsExtremity1Handle;
	}
	
	public IHandle getExtremity2Handle()
	{
		return itsExtremity2Handle;
	}
	
	public void setControlPoints(List<IHandle> aControlPoints)
	{
		itsControlPoints = aControlPoints;
	}
	
	public void setExtremity1Handle(IHandle aExtremity1Handle)
	{
		itsExtremity1Handle = aExtremity1Handle;
	}
	
	public void setExtremity2Handle(IHandle aExtremity2Handle)
	{
		itsExtremity2Handle = aExtremity2Handle;
	}
	
	public void setPositionHandle(IHandle aPositionHandle)
	{
		itsPositionHandle = aPositionHandle;
	}
	
	public IGraphicObjectEditor getEditor()
	{
		return itsEditor;
	}
	
	public void setEditor(IGraphicObjectEditor aEditor)
	{
		itsEditor = aEditor;
	}
	
	/**
	 * Delegates to the editor.
	 * @see IGraphicObjectEditor
	 */
	public void startEditing()
	{
		if (itsEditor != null) itsEditor.startEditing();
	}
	
	/**
	 * Delegates to the editor.
	 * @see IGraphicObjectEditor
	 */
	public void stopEditing()
	{
		if (itsEditor != null) itsEditor.stopEditing();
	}
}
