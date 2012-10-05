/*
 * Created on Jun 30, 2005
 */
package zz.csg.test;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import zz.csg.api.constraints.Constraint;
import zz.csg.api.constraints.ConstraintParser;
import zz.csg.api.constraints.IConstrainedDouble;
import zz.csg.api.constraints.IConstrainedRectangle;
import zz.csg.api.constraints.SymbolNotFoundException;
import zz.csg.api.constraints.ConstraintParser.CPException;
import zz.csg.api.constraints.ConstraintParser.Constant;
import zz.csg.api.constraints.parser.ParseException;
import zz.csg.impl.figures.SVGRectangle;
import EDU.Washington.grad.gjb.cassowary.ExCLNonlinearExpression;
import EDU.Washington.grad.gjb.cassowary.IVariable;

public class ConstraintSetTest extends TestCase
{

	private IConstrainedDouble x;
	private IConstrainedDouble y;
	private IConstrainedDouble w;
	private IConstrainedDouble h;
	private ConstraintParser itsSet;

	public void testParsing()
	{
		SVGRectangle theRectangle = new SVGRectangle();
		IConstrainedRectangle theBounds = theRectangle.pBounds();
		
		x = theBounds.pX();
		y = theBounds.pY();
		w = theBounds.pW();
		h = theBounds.pH();
		
		itsSet = new ConstraintParser();
		itsSet.addVar("x", x);
		itsSet.addVar("y", y);
		itsSet.addCnst("w", w);
		itsSet.addCnst("h", h);
		itsSet.addCnst("W", 10);
		itsSet.addCnst("H", 5);
		
		try
		{
			checkConstraint("2*x + w + 3*(x+y)*5 - (w-h)/2 + H = y*12 + 6 + W", 17, 3, 0.5, 0.5, -11);
			checkConstraintNonLinear("x * (2+x) = 2");
		}
		catch (ParseException e)
		{
			fail("parse exception");
		}
		catch (ExCLNonlinearExpression e)
		{
			fail("non linear");
		}
		catch (SymbolNotFoundException e)
		{
			fail("symbol not found"); 
		}
	}
	
	/**
	 * Checks that a constraint evaluation produces a correct linear expression.
	 */
	private void checkConstraint (String aConstraint, double aX, double aY, double aW, double aH, double aConst) 
	throws ParseException, ExCLNonlinearExpression, SymbolNotFoundException
	{
		Constraint theConstraint = itsSet.createConstraint(aConstraint);
		
		Map<IVariable, Double> theResult = new HashMap<IVariable, Double>();
		theResult.put(x, aX);
		theResult.put(y, aY);
		theResult.put(w, aW);
		theResult.put(h, aH);
		
		double[] k = theConstraint.get_k();
		IConstrainedDouble[] v = theConstraint.get_v();
		
		for (int i=0;i<k.length;i++)
		{
			double val = theResult.get(v[i]).doubleValue();
			if (val != k[i]) fail("Bad coefficient for constraint: "+aConstraint);
		}
		
		if (theConstraint.get_c() != aConst) fail("Bad constant for constraint: "+aConstraint);
	}
	
	/**
	 * Checks that the given constraint is non linear.
	 */
	private void checkConstraintNonLinear (String aConstraint) 
	{
		try
		{
			Constraint theConstraint = itsSet.createConstraint(aConstraint);
		}
		catch (CPException e)
		{
			return;
		}
		
		fail("Failed to detect non-linear constraint: "+aConstraint);
	}
}
