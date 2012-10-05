/*
 * Created on Jun 30, 2005
 */
package zz.csg.api.constraints;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import zz.csg.api.constraints.Constraint.Operator;
import zz.csg.api.constraints.Constraint.Type;
import zz.csg.api.constraints.parser.ASTAdd;
import zz.csg.api.constraints.parser.ASTConst;
import zz.csg.api.constraints.parser.ASTDiv;
import zz.csg.api.constraints.parser.ASTIdentifier;
import zz.csg.api.constraints.parser.ASTMinus;
import zz.csg.api.constraints.parser.ASTMul;
import zz.csg.api.constraints.parser.ASTRel;
import zz.csg.api.constraints.parser.ASTStart;
import zz.csg.api.constraints.parser.ASTSub;
import zz.csg.api.constraints.parser.ArithmeticParser;
import zz.csg.api.constraints.parser.ArithmeticParserVisitor;
import zz.csg.api.constraints.parser.Node;
import zz.csg.api.constraints.parser.ParseException;
import zz.csg.api.constraints.parser.SimpleNode;
import EDU.Washington.grad.gjb.cassowary.CL;
import EDU.Washington.grad.gjb.cassowary.ClDouble;
import EDU.Washington.grad.gjb.cassowary.ClLinearExpression;
import EDU.Washington.grad.gjb.cassowary.ClStrength;
import EDU.Washington.grad.gjb.cassowary.ExCLNonlinearExpression;
import EDU.Washington.grad.gjb.cassowary.IVariable;

/**
 * Permits to create a group of constraints that
 * share the same sets of constants and variables.
 * 
 * @author gpothier
 */
public class ConstraintParser
{
	private Map<String, Symbol> itsSymbols = new HashMap<String, Symbol>();

	private Evaluator itsEvaluator = new Evaluator();

	private void addSymbol(Symbol aSymbol)
	{
		if (itsSymbols.put(aSymbol.getName(), aSymbol) != null) throw new CPException("Symbol already defined: "+aSymbol.getName());
	}

	/**
	 * Adds a variable to the symbols table
	 * @param aName Name of the variable
	 * @param aVar Actual variable
	 */
	public Symbol addVar(String aName, IConstrainedDouble aVar)
	{
		assert aName != null;
		assert aVar != null;
		ConstraintParser.Variable theSymbol = new ConstraintParser.Variable(aName, aVar);
		addSymbol (theSymbol);
		return theSymbol;
	}

	/**
	 * Adds a pseudo-constant to the symbols table.
	 * @param aName Name of the constant
	 * @param aVar Variable that contains the contant's value
	 */
	public Symbol addCnst(String aName, IConstrainedDouble aVar)
	{
		assert aName != null;
		assert aVar != null;
		ConstraintParser.ConstVar theSymbol = new ConstraintParser.ConstVar(aName, aVar);
		addSymbol (theSymbol);
		return theSymbol;
	}

	/**
	 * Adds a constant to the symbols table.
	 * @param aName Name of the constant
	 * @param aValue Constant's value.
	 */
	public Symbol addCnst(String aName, double aValue)
	{
		assert aName != null;
		ConstraintParser.Constant theSymbol = new ConstraintParser.Constant(aName, aValue);
		addSymbol (theSymbol);
		return theSymbol;
	}

	/**
	 * Creates a constraint with {@link ClStrength#required} strength.
	 * @see #createConstraint(String, ClStrength)
	 */
	public Constraint createConstraint(String aExpression)
	{
		return createConstraint(aExpression, ClStrength.required);
	}

	/**
	 * Creates a new constraint.
	 * 
	 * @param aExpression A linear equality or inequality using the symbols of this constraint set.
	 * @param aStrength Strength of the constraint.
	 * 
	 * @throws ParseException When a syntax error is detected in the expression
	 * @throws ExCLNonlinearExpression When the expression is not linear. Note that multiplication and division operators
	 * must have at least a constant operand. Expressions like "x/x = y", although linear, are not accepted.
	 * @throws SymbolNotFoundException When the expression references a symbol that was not declared.
	 */
	public Constraint createConstraint(String aExpression, ClStrength aStrength)
	{
		try
		{
			SimpleNode theNode = ArithmeticParser.parse(aExpression);
			return (Constraint) theNode.jjtAccept(itsEvaluator, aStrength);
		}
		catch (ParseException e)
		{
			throw new CPException(e);
		}
	}
	
	/**
	 * Creates a constraint given a pair of linear expressions, an operator and a strength
	 * @param aLeftExpr The linear expression on the left of the operator
	 * @param aRightExpr The linear expression on the right of the operator
	 * @param aOperator The operator
	 * @param aStrength Strength of the constraint
	 */
	private Constraint createConstraint (
			ClLinearExpression aLeftExpr, 
			ClLinearExpression aRightExpr, 
			Operator aOperator,
			ClStrength aStrength)
	{
		ClLinearExpression theExpression = aLeftExpr.addExpression(aRightExpr, -1);
		
		Hashtable<Symbol, ClDouble> theTerms = theExpression.terms();
		
		double[] theK = new double[theTerms.size()];
		IConstrainedDouble[] theV = new IConstrainedDouble[theTerms.size()];
		Type[] theT = new Type[theTerms.size()];
		
		int i=0;
		for(Map.Entry<Symbol, ClDouble> theEntry : theTerms.entrySet())
		{
			Symbol theSymbol = theEntry.getKey();
			
			theK[i] = theEntry.getValue().doubleValue();
			theV[i] = theSymbol.getVariable();
			theT[i] = theSymbol.getType();
			
			i++;
		}
		
		return new Constraint(theK, theV, theT, theExpression.constant(), aOperator, aStrength);
	}
	
	/**
	 * Represents a symbol of the constraint set. It can be a variable, a constant variable, or
	 * a constant.
	 * It implements IVariable so as to trick the linear expressions arithmetic engine.
	 * @author gpothier
	 */
	private static abstract class Symbol implements IVariable
	{
		private String itsName;

		public Symbol(String aName)
		{
			itsName = aName;
		}

		public String getName()
		{
			return itsName;
		}
		
		public abstract ClLinearExpression getExpression();
		
		public abstract IConstrainedDouble getVariable();
		public abstract Type getType();

		public boolean isDummy()
		{
			throw new UnsupportedOperationException();
		}

		public boolean isExternal()
		{
			throw new UnsupportedOperationException();
		}

		public boolean isPivotable()
		{
			throw new UnsupportedOperationException();
		}

		public boolean isRestricted()
		{
			throw new UnsupportedOperationException();
		}
		
		
	}

	/**
	 * This kind of symbol represetns a variable of type {@link Constraint.Type#VAR}.
	 * @author gpothier
	 */
	private static class Variable extends Symbol
	{
		private IConstrainedDouble itsVariable;

		public Variable(String aName, IConstrainedDouble aVariable)
		{
			super(aName);
			itsVariable = aVariable;
		}

		@Override
		public ClLinearExpression getExpression()
		{
			return new ClLinearExpression(this);
		}

		@Override
		public IConstrainedDouble getVariable()
		{
			return itsVariable;
		}

		@Override
		public Type getType()
		{
			return Type.VAR;
		}
		
		
	}

	/**
	 * This kind of symbol represetns a variable of type {@link Constraint.Type#CONST}.
	 * @author gpothier
	 */
	private static class ConstVar extends Symbol
	{
		private IConstrainedDouble itsConstant;

		public ConstVar(String aName, IConstrainedDouble aConstant)
		{
			super(aName);
			itsConstant = aConstant;
		}
		
		@Override
		public ClLinearExpression getExpression()
		{
			return new ClLinearExpression(this);
		}

		@Override
		public IConstrainedDouble getVariable()
		{
			return itsConstant;
		}

		@Override
		public Type getType()
		{
			return Type.CONST;
		}
	}

	/**
	 * This symbol represents a numerical constant.
	 * @author gpothier
	 */
	public static class Constant extends Symbol
	{
		private double itsValue;

		public Constant(String aName, double aValue)
		{
			super(aName);
			itsValue = aValue;
		}

		public double getValue()
		{
			return itsValue;
		}

		@Override
		public ClLinearExpression getExpression()
		{
			return new ClLinearExpression(itsValue);
		}

		@Override
		public Type getType()
		{
			throw new UnsupportedOperationException();
		}

		@Override
		public IConstrainedDouble getVariable()
		{
			throw new UnsupportedOperationException();
		}
	}

	
	
	private class Evaluator implements ArithmeticParserVisitor
	{
		
		public Object visit(ASTRel aNode, Object aData)
		{
			Node theLeftNode = aNode.jjtGetChild(0);
			ClLinearExpression theLeftExpression = (ClLinearExpression) theLeftNode.jjtAccept(this, aData);
			
			Node theRightNode = aNode.jjtGetChild(1);
			ClLinearExpression theRightExpression = (ClLinearExpression) theRightNode.jjtAccept(this, aData);
			
			ClStrength theStrength = (ClStrength) aData;
			
			return createConstraint(theLeftExpression, theRightExpression, aNode.getOp(), theStrength);
		}

		public Object visit(ASTConst aNode, Object aData)
		{
			return new ClLinearExpression(aNode.getValue());
		}
		
		public Object visit(ASTIdentifier aNode, Object aData)
		{
			String theName = aNode.getName();
			Symbol theSymbol = itsSymbols.get(theName);
			if (theSymbol == null) throw new CPException (new SymbolNotFoundException(theName));
			
			return theSymbol.getExpression();
		}

		public Object visit(ASTAdd aNode, Object aData)
		{
			return op(aNode, ADD);
		}
		
		public Object visit(ASTSub aNode, Object aData)
		{
			return op(aNode, SUB);
		}

		public Object visit(ASTDiv aNode, Object aData)
		{
			return op(aNode, DIV);
		}

		public Object visit(ASTMul aNode, Object aData)
		{
			return op(aNode, MUL);
		}

		public Object visit(ASTMinus aNode, Object aData)
		{
			Node theChild = aNode.jjtGetChild(0);
			ClLinearExpression theExpression = (ClLinearExpression) theChild.jjtAccept(this, aData);
			theExpression.multiplyMe(-1);
			return theExpression;
		}

		public Object visit(ASTStart aNode, Object aData)
		{
			Node theChild = aNode.jjtGetChild(0);
			return theChild.jjtAccept(this, aData);
		}

		private ClLinearExpression op(Node aNode, Op aOperation)
		{
			try
			{
				int numChildren = aNode.jjtGetNumChildren();
				ClLinearExpression theLastExpression = null;
				for (int i = 0; i < numChildren; i++)
				{
					Node theChild = aNode.jjtGetChild(i);
					ClLinearExpression theExpression = (ClLinearExpression) theChild.jjtAccept(this, null);
					
					if (theLastExpression != null)
					{
						theLastExpression = aOperation.perform(theLastExpression, theExpression);
					}
					else theLastExpression = theExpression;
				}
				
				return theLastExpression;
			}
			catch (ExCLNonlinearExpression e)
			{
				throw new CPException(e);
			}
		}

		public Object visit(SimpleNode aNode, Object aData)
		{
			return null;
		}

	}
	
	/**
	 * Constraint parser exception.
	 * This exception is often used to wrap non-runtime exceptions that are thrown during expression
	 * evaluation.
	 * @author gpothier
	 */
	public static class CPException extends RuntimeException
	{
		private Exception itsException;

		public CPException(String aMessage)
		{
			super (aMessage);
		}

		public CPException(Exception aException)
		{
			itsException = aException;
		}

		public Exception getException()
		{
			return itsException;
		}
		
		
	}
	
	private abstract static class Op
	{
		public abstract ClLinearExpression perform(ClLinearExpression aExpression1, ClLinearExpression aExpression2) throws ExCLNonlinearExpression;
	}
	
	private static Op ADD  = new Op()
	{
		@Override
		public ClLinearExpression perform(ClLinearExpression aExpression1, ClLinearExpression aExpression2)
		{
			return CL.Plus(aExpression1, aExpression2);
		}
	};
	
	private static Op SUB = new Op()
	{
		@Override
		public ClLinearExpression perform(ClLinearExpression aExpression1, ClLinearExpression aExpression2)
		{
			return CL.Minus(aExpression1, aExpression2);
		}
	};
	
	private static Op MUL = new Op()
	{
		@Override
		public ClLinearExpression perform(ClLinearExpression aExpression1, ClLinearExpression aExpression2) throws ExCLNonlinearExpression
		{
			return CL.Times(aExpression1, aExpression2);
		}
	};
	
	private static Op DIV = new Op()
	{
		@Override
		public ClLinearExpression perform(ClLinearExpression aExpression1, ClLinearExpression aExpression2) throws ExCLNonlinearExpression
		{
			return CL.Divide(aExpression1, aExpression2);
		}
	};
}
