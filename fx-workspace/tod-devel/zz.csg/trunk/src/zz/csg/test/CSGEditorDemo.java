/*
 * Created on Jun 3, 2005
 */
package zz.csg.test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.LayoutManager;

import javax.swing.JFrame;
import javax.swing.JPanel;

import zz.csg.api.GraphicNode;
import zz.csg.api.GraphicObjectContext;
import zz.csg.api.IGraphicObject;
import zz.csg.api.IRectangularGraphicContainer;
import zz.csg.api.figures.IGOLine;
import zz.csg.api.figures.IGORectangle;
import zz.csg.display.GraphicObjectEditionLayer;
import zz.csg.display.GraphicPanel;
import zz.csg.display.tool.select.SelectionTool;
import zz.csg.impl.SVGGraphicContainer;
import zz.csg.impl.figures.SVGLine;
import zz.csg.impl.figures.SVGRectangle;
import zz.utils.properties.HashSetProperty;
import zz.utils.properties.ISetProperty;
import zz.utils.ui.StackLayout;

import EDU.Washington.grad.gjb.cassowary.ClLinearEquation;
import EDU.Washington.grad.gjb.cassowary.ClLinearExpression;
import EDU.Washington.grad.gjb.cassowary.ClSimplexSolver;
import EDU.Washington.grad.gjb.cassowary.ExCLInternalError;
import EDU.Washington.grad.gjb.cassowary.ExCLNonlinearExpression;
import EDU.Washington.grad.gjb.cassowary.ExCLRequiredFailure;

public class CSGEditorDemo extends JPanel
{
	public static void main(String[] args)
	{
		JFrame theFrame = new JFrame("CSG Editor demo");
		theFrame.setContentPane(new CSGEditorDemo());
		theFrame.pack();
		theFrame.setVisible(true);
	}
	
	private ClSimplexSolver itsSolver = new ClSimplexSolver();
	private GraphicPanel itsGraphicPanel;
	private GraphicObjectEditionLayer itsEditionLayer;
	
	public CSGEditorDemo()
	{
		init();
	}

	private void init()
	{
		GraphicNode<IGraphicObject> theRootNode = createScenegraph();
		itsGraphicPanel = new GraphicPanel(theRootNode);
		
		ISetProperty<GraphicNode> theSelectionProperty = new HashSetProperty<GraphicNode>(this);
		SelectionTool theSelectionTool = new SelectionTool(theSelectionProperty);
		itsEditionLayer = new GraphicObjectEditionLayer(itsGraphicPanel, theSelectionTool, theRootNode);
		itsEditionLayer.setEditorTool(theSelectionTool);
		
		setLayout(new StackLayout());
		add (itsEditionLayer);
		add (itsGraphicPanel);
		
		setPreferredSize(new Dimension(400, 400));
	}
	
	private GraphicNode<IGraphicObject> createScenegraph()
	{
		IRectangularGraphicContainer theContainer = new SVGGraphicContainer();
		theContainer.pBounds().set(0, 0, 100, 100);
		
		IGORectangle theRectangle = new SVGRectangle();
		
		theRectangle.pStrokePaint().set(Color.BLACK);
		theRectangle.pStrokeWidth().set(1.0);
		theRectangle.pBounds().set(10, 10, 40, 30);
		
		IGOLine theLine = new SVGLine();
		theLine.pStrokePaint().set(Color.BLUE);
		theLine.pStrokeWidth().set(2.0);
		theLine.pPoint1().set(40, 10);
		theLine.pPoint2().set(60, 20);
		
		theContainer.pChildren().add(theRectangle);
		theContainer.pChildren().add(theLine);
		
		
		
		try
		{
			ClLinearExpression cle;
			ClLinearEquation cleq;

			itsSolver.addStay(theRectangle.pBounds().pX());
			itsSolver.addStay(theRectangle.pBounds().pY());
			itsSolver.addStay(theRectangle.pBounds().pW());
			itsSolver.addStay(theRectangle.pBounds().pH());

			itsSolver.addStay(theLine.pPoint1().pX());
			itsSolver.addStay(theLine.pPoint1().pY());
			itsSolver.addStay(theLine.pPoint2().pX());
			itsSolver.addStay(theLine.pPoint2().pY());

			cle = new ClLinearExpression(theRectangle.pBounds().pW()).divide(2).plus(theRectangle.pBounds().pX());
			cleq = new ClLinearEquation(theLine.pPoint1().pX(), cle);
			itsSolver.addConstraint(cleq);
			
			cle = new ClLinearExpression(theRectangle.pBounds().pH()).divide(2).plus(theRectangle.pBounds().pY());
			cleq = new ClLinearEquation(theLine.pPoint1().pY(), cle);
			itsSolver.addConstraint(cleq);
			
			itsSolver.resolve();
		}
		catch (ExCLNonlinearExpression e)
		{
			e.printStackTrace();
		}
		catch (ExCLRequiredFailure e)
		{
			e.printStackTrace();
		}
		catch (ExCLInternalError e)
		{
			e.printStackTrace();
		}
		
		GraphicNode<IGraphicObject> theNode = new GraphicNode<IGraphicObject>(theContainer);
		GraphicObjectContext.SOLVER.set(theNode.getContext(), itsSolver);
		
		return theNode;
	}
	
	
}
