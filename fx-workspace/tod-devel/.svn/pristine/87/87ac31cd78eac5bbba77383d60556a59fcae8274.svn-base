/*
 * Created on Mar 3, 2004
 */
package net.basekit.world;

import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;

import javax.swing.JComponent;
import javax.vecmath.*;

import net.basekit.interaction.InteractionManager;
import net.basekit.layoutdelegates.DefaultLayoutDelegate;
import net.basekit.layoutdelegates.SharpLayoutDelegate;
import net.basekit.widgets.RootWidget;
import net.basekit.widgets.Widget;
import net.basekit.widgets.label.LabelWidget;
import net.basekit.widgets.list.LabelElementRenderer;
import net.basekit.world.toolglass.ToolGlass;

import com.xith3d.render.CanvasPeer;
import com.xith3d.render.RenderPeer;
import com.xith3d.render.jogl.RenderPeerImpl;
import com.xith3d.scenegraph.*;
import com.xith3d.scenegraph.Locale;

/**
 * Provides an entry point to the basekit. Permits to create a 3d canvas,
 * initialize devices, etc.
 * @author gpothier
 */
public class World
{
	private VirtualUniverse itsUniverse;
	private View itsView;
	private Locale itsLocale;
	private BranchGroup itsBranchGroup;
	
	private SwingMouse itsSwingMouse;
	
	private Component itsComponent;
	
	/**
	 * Whether our window is dirty.
	 */
	private boolean itsDirty = false;
	private Canvas3D itsCanvas;
	private Loop itsLoop;
	
	private LayoutManager itsLayoutManager = new LayoutManager (this);
	
	private InteractionManager itsInteractionManager;
	
	private ToolGlass itsToolGlass = new ToolGlass (this);
	
	private RootWidget itsRootWidget;

	/**
	 * This is the root of the world's content.
	 */
	private Widget itsContentRoot;
	
	/**
	 * This is the root for objects that are fixed to the camera.
	 */
	private Widget itsFixedRoot;

	public World (JComponent aContainer)
	{
		itsUniverse = new VirtualUniverse();
		itsView = new View();
		itsUniverse.addView(itsView);

		itsLocale = new Locale();
		itsUniverse.addLocale(itsLocale);

		itsBranchGroup = new BranchGroup();
		itsBranchGroup.setPickable(true);
		itsLocale.addBranchGraph(itsBranchGroup);

		itsRootWidget = new RootWidget (this);
		itsBranchGroup.addChild(itsRootWidget);

		// Init fixed root
		itsFixedRoot = new Widget ();
		itsFixedRoot.setPacked(true);
		itsFixedRoot.setRenderBounds(false);
		itsRootWidget.addWidget(itsFixedRoot);

		Transform3D theTransform = new Transform3D ();
		theTransform.rotZ((float)Math.PI);
		theTransform.setTranslation(new Vector3f (50, 50, 100));
		itsFixedRoot.setTransform(theTransform);
		
		itsFixedRoot.setLayoutDelegate(new DefaultLayoutDelegate ());
		itsFixedRoot.addWidget(itsToolGlass);
//		itsFixedRoot.addWidget(new LabelWidget ("toto"));

		// Init content root
		itsContentRoot = new Widget();
		itsContentRoot.setRenderBounds(false);
		itsRootWidget.addWidget(itsContentRoot);

		Transform3D theTransform1 = new Transform3D ();
		theTransform1.rotZ((float)Math.PI);
		theTransform1.setTranslation(new Vector3f (0, 0, 200));
		itsContentRoot.setTransform(theTransform1);
//		itsContentRoot.setLayoutDelegate(new SharpLayoutDelegate ());
		itsContentRoot.setLayoutDelegate(new DefaultLayoutDelegate ());
//		itsContentRoot.addWidget(new LabelWidget ("titi"));

		// turn the scene into a render friendly format
		//scene.compile();

		RenderPeer rp = new RenderPeerImpl();
		CanvasPeer cp = rp.makeCanvas(aContainer, 640, 480, 32, false);
		itsCanvas = new Canvas3D();
		itsCanvas.set3DPeer(cp);
		
		itsComponent = cp.getComponent();
		itsSwingMouse = new SwingMouse (itsCanvas, this);
		itsComponent.addMouseListener(itsSwingMouse);
		itsComponent.addMouseMotionListener(itsSwingMouse);
		itsComponent.addMouseWheelListener(itsSwingMouse);
		
		itsComponent.addKeyListener(new KeyAdapter ()
		{
			public void keyTyped (KeyEvent aE)
			{
				if (aE.getKeyChar() == 'C') itsInteractionManager.toggleInputEditor();
			}
		});
		
		itsInteractionManager = new InteractionManager (this);
		
		AmbientLight theAmbientLight = new AmbientLight (true, new Color3f (0.7f, 1f, 0.6f));
		itsBranchGroup.addChild(theAmbientLight);
		
		// modify our view so we can see the cube
		itsView.addCanvas3D(itsCanvas);
		itsView.setFrontClipDistance(0.5f);
		itsView.setBackClipDistance(2000f);
		Transform3D t = new Transform3D();
		t.lookAt(new Vector3f (0, 0, 0),    // location of eye
				new Vector3f (0, 0, 1),    // center of view
				new Vector3f (0, 1, 0));    // vector pointing up
		
//		setCameraPosition(new Vector3f (0, 0, -200));
		
		itsToolGlass.setPosition(10, 10);

		itsView.setTransform(t);

		itsLoop = new Loop ();
		itsLoop.start ();
	}

	/**
	 * Returns the swing component that displays the world.
	 */
	public Component getComponent ()
	{
		return itsComponent;
	}
	
	public LayoutManager getLayoutManager ()
	{
		return itsLayoutManager;
	}

	public InteractionManager getInteractionManager ()
	{
		return itsInteractionManager;
	}

	/**
	 * @return The toolglass of this world.
	 */
	public ToolGlass getToolGlass ()
	{
		return itsToolGlass;
	}

	/**
	 * The content root is the widget that contains the scene.
	 * To add content to the scene, add it to the content root.
	 */
	public Widget getContentRoot ()
	{
		return itsContentRoot;
	}

	/**
	 * The fixed root contains widgets that are attached to the camera.
	 */
	public Widget getFixedRoot ()
	{
		return itsFixedRoot;
	}
	
	/**
	 * Sets the transform of this world's view.
	 * See also {@link #viewLookAt(Vector3f, Vector3f, Vector3f)} for an easier way to
	 * set the view's transform.
	 */
	public void setViewTransform (Transform3D aTransform)
	{
		itsView.setTransform(aTransform);
	}

	
	public void setCameraPosition (Tuple3f aPosition)
	{
		Transform3D theTransform = itsContentRoot.getTransform();
		theTransform.setIdentity();
		theTransform.rotZ((float) (Math.PI));
		theTransform.setTranslation(new Vector3f (-aPosition.x, -aPosition.y, -aPosition.z));
		itsContentRoot.setTransform(theTransform);
		redraw();
	}

	/**
	 * Schedules a runnable for later execution by the rendering thread.
	 * @param aRunnable
	 */
	public void invokeLater (Runnable aRunnable)
	{
		itsLoop.invokeLater (aRunnable);
	}
	
	/**
	 * Indicates that the world needs to be redrawn
	 */
	public void redraw ()
	{
		itsDirty = true;
	}

	private class Loop extends Thread
	{
		private int itsFrame = 1;
		private List itsScheduledRunnables = new ArrayList ();

		public Loop ()
		{
			super ("Basekit world loop");
		}
		
		public synchronized void run ()
		{
			try
			{			
				while (true)
				{
					if (itsRootWidget.isRedrawNeeded()) itsDirty = true;
					if (itsFrame % 30 == 0) itsDirty = true;

					try
					{
						if (! itsScheduledRunnables.isEmpty ())
						{
							List theCurrentRunnables;
							synchronized (itsScheduledRunnables)
							{
								theCurrentRunnables = new ArrayList (itsScheduledRunnables);
								itsScheduledRunnables.clear ();
							}
							
							for (Iterator theIterator = theCurrentRunnables.iterator (); theIterator.hasNext ();)
							{
								Runnable theRunnable = (Runnable) theIterator.next ();
								try
								{
									theRunnable.run ();
								} 
								catch (Throwable e)
								{
									System.err.println("Catched exception while running scheduled runnables");
									e.printStackTrace();
								}
							}

						}

						if (itsDirty)
						{
							getLayoutManager().layout();
	//						System.out.println("Rendering frame #"+(itsFrame++));
							itsView.renderOnce();
	//						System.out.println("Rendered.");

							itsRootWidget.resetRedrawTime();
							itsDirty = false;
						}
					}
					catch (Exception e) 
					{
						e.printStackTrace();
					}
					wait (10);
					itsFrame++;
				}
			} 
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			
		}

		public void invokeLater (Runnable aRunnable)
		{
			synchronized (itsScheduledRunnables)
			{
				itsScheduledRunnables.add (aRunnable);
			}
			synchronized (this)
			{
				notify ();
			}
		}
	}
}
