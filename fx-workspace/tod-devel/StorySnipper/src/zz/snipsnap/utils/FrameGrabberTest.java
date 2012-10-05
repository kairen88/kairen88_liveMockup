/*
 * Created on Apr 7, 2005
 */
package zz.snipsnap.utils;

import java.io.File;
import java.io.IOException;

import javax.media.ConfigureCompleteEvent;
import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.EndOfMediaEvent;
import javax.media.Format;
import javax.media.Manager;
import javax.media.MediaException;
import javax.media.MediaLocator;
import javax.media.PrefetchCompleteEvent;
import javax.media.Processor;
import javax.media.ProcessorModel;
import javax.media.RealizeCompleteEvent;
import javax.media.ResourceUnavailableEvent;
import javax.media.format.RGBFormat;
import javax.media.protocol.FileTypeDescriptor;
import javax.media.protocol.PushBufferDataSource;

public class FrameGrabberTest implements ControllerListener
{
	private Object waitSync = new Object();

	private boolean stateTransitionOK = true;

	private Processor itsProcessor;

	public static void main(String[] args) throws MediaException, IOException, InterruptedException
	{
		new FrameGrabberTest().test();
	}

	public void test() throws MediaException, IOException
	{
		Manager.setHint(Manager.PLUGIN_PLAYER, true);
		File theFile = new File("/home/gpothier/Documents/photos/peru2005/MOV03029.MPG");

		MediaLocator theLocator = new MediaLocator(theFile.toURL());
		ProcessorModel theModel = new ProcessorModel(
				theLocator, 
				new Format[] {new RGBFormat()},
				new FileTypeDescriptor(FileTypeDescriptor.MPEG));
		
		itsProcessor = Manager.createRealizedProcessor(theModel);
		PushBufferDataSource theBufferDataSource = (PushBufferDataSource) itsProcessor.getDataOutput();

	}

	/**
	 * Block until the processor has transitioned to the given state. Return
	 * false if the transition failed.
	 */
	private void waitForState(int state)
	{
		synchronized (waitSync)
		{
			try
			{
				while (itsProcessor.getState() != state && stateTransitionOK)
					waitSync.wait();
			}
			catch (Exception e)
			{
			}
		}
		
		if (! stateTransitionOK) throw new RuntimeException();
	}

	/**
	 * Controller Listener.
	 */
	public void controllerUpdate(ControllerEvent evt)
	{

		if (evt instanceof ConfigureCompleteEvent || evt instanceof RealizeCompleteEvent
				|| evt instanceof PrefetchCompleteEvent)
		{
			synchronized (waitSync)
			{
				stateTransitionOK = true;
				waitSync.notifyAll();
			}
		}
		else if (evt instanceof ResourceUnavailableEvent)
		{
			synchronized (waitSync)
			{
				stateTransitionOK = false;
				waitSync.notifyAll();
			}
		}
		else if (evt instanceof EndOfMediaEvent)
		{
			itsProcessor.close();
			System.exit(0);
		}
	}

}
