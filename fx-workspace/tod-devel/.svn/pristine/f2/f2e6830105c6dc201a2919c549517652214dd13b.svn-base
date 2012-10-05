/*
 * Created on Jun 29, 2005
 */
package zz.csg.api.constraints;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import zz.csg.impl.constraints.ConstrainedDouble;
import zz.utils.ListMap;
import zz.utils.properties.IProperty;
import zz.utils.properties.IPropertyListener;
import zz.utils.properties.IPropertyVeto;
import EDU.Washington.grad.gjb.cassowary.ClConstraint;
import EDU.Washington.grad.gjb.cassowary.ClSimplexSolver;
import EDU.Washington.grad.gjb.cassowary.ExCLConstraintNotFound;
import EDU.Washington.grad.gjb.cassowary.ExCLError;
import EDU.Washington.grad.gjb.cassowary.ExCLInternalError;
import EDU.Washington.grad.gjb.cassowary.ExCLRequiredFailure;
import EDU.Washington.grad.gjb.cassowary.ExCLTooDifficult;
import EDU.Washington.grad.gjb.cassowary.IVariable;

public class ConstraintSystem 
{
	private ClSimplexSolver itsSolver = new ClSimplexSolver();

	/**
	 * The name of this system, used for debugging
	 */
	private String itsName;
	
	private ListMap<IConstrainedDouble, Constraint> itsVariablesMap = new ListMap<IConstrainedDouble, Constraint>();
	private ListMap<IConstrainedDouble, Constraint> itsConstantsMap = new ListMap<IConstrainedDouble, Constraint>();
	private Map<Constraint, ClConstraint> itsClConstraintsMap = new HashMap<Constraint, ClConstraint>();
	
	private ConstantsListener itsConstantsListener = new ConstantsListener();
	private VariablesListener itsVariablesListener = new VariablesListener();
	
	public ConstraintSystem()
	{
	}

	public ConstraintSystem(String aName)
	{
		itsName = aName;
	}
	
	public void addConstraint(Constraint aConstraint)
	{
		try
		{
			double[] k = aConstraint.get_k();
			IConstrainedDouble[] v = aConstraint.get_v();
			Constraint.Type[] t = aConstraint.get_t();
			
			
			for (int i=0;i<k.length;i++)
			{
				List<Constraint> theList;
				
				switch(t[i])
				{
				case CONST:
					if (itsVariablesMap.containsKey(v[i]))
					{
						throw new RuntimeException(String.format(
								"Variable %s already present in system as VAR",
								v[i]));
					}
					
					theList = itsConstantsMap.add(v[i], aConstraint);

					// Add a listener if this constant is added for the first time.
					if (theList.size()== 1) v[i].addListener(itsConstantsListener); 
					break;
					
				case VAR:
					if (itsConstantsMap.containsKey(v[i]))
					{
						throw new RuntimeException(String.format(
								"Variable %s already present in system as CONST",
								v[i]));
					}
					
					theList = itsVariablesMap.add(v[i], aConstraint);

					// Add a listener if this variable is added for the first time.
					if (theList.size()== 1) v[i].addListener(itsVariablesListener); 
					break;
					
				default: throw new RuntimeException();
				}
				
				if (theList.size() == 1) SystemsChecker.getInstance().checkInVariable(v[i], t[i], this);
			}
			
			
			ClConstraint theClConstraint = aConstraint.createClConstraint();
			itsClConstraintsMap.put(aConstraint, theClConstraint);
			itsSolver.addConstraint(theClConstraint);
		}
		catch (ExCLRequiredFailure e)
		{
			throw new RuntimeException(e);
		}
		catch (ExCLInternalError e)
		{
			throw new RuntimeException(e);
		}
	}
	
	public void removeConstraint(Constraint aConstraint)
	{
		try
		{
			ClConstraint theClConstraint = itsClConstraintsMap.remove(aConstraint);
			if (theClConstraint == null) throw new IllegalArgumentException("The constraint is not part of this system");
			
			itsSolver.removeConstraint(theClConstraint);
			
			double[] k = aConstraint.get_k();
			IConstrainedDouble[] v = aConstraint.get_v();
			Constraint.Type[] t = aConstraint.get_t();
			
			
			for (int i=0;i<k.length;i++)
			{
				List<Constraint> theList;
				
				switch(t[i])
				{
				case CONST:
					theList = itsConstantsMap.getList(v[i]);
					theList.remove(aConstraint);

					if (theList.size() == 0) 
					{
						v[i].removeListener(itsConstantsListener);
						itsConstantsMap.remove(v[i]); // Remove the key so as to avoid stale references.
					}
					break;
					
				case VAR:
					theList = itsVariablesMap.getList(v[i]);
					theList.remove(aConstraint);
					
					if (theList.size() == 0) 
					{
						v[i].removeListener(itsVariablesListener);
						itsVariablesMap.remove(v[i]); // Remove the key so as to avoid stale references.
					}
					break;
					
				default: throw new RuntimeException();
				}
				
				if (theList.size() == 0) SystemsChecker.getInstance().checkOutVariable(v[i], t[i], this);
			}
		}
		catch (ExCLConstraintNotFound e)
		{
			throw new RuntimeException(e);
		}
		catch (ExCLInternalError e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	public String toString()
	{
		return itsName != null ? "ConstraintSystem '"+itsName+"'" : super.toString();
	}

	/**
	 * This listener is notified whenever the value of a constant changes.
	 * It updates all constraints in which the changed constant appears.
	 * @author gpothier
	 */
	private class ConstantsListener implements IPropertyListener<Double>
	{
		public void propertyChanged(IProperty<Double> aProperty, Double aOldValue, Double aNewValue)
		{
			IConstrainedDouble theVariable = (IConstrainedDouble) aProperty;
			List<Constraint> theConstraints = itsConstantsMap.get(theVariable);
			
			try
			{
				itsSolver.setAutosolve(false);
				for (Constraint theConstraint : theConstraints)
				{
					ClConstraint theClConstraint = itsClConstraintsMap.get(theConstraint);
					itsSolver.removeConstraint(theClConstraint);
					theClConstraint = theConstraint.createClConstraint();
					itsClConstraintsMap.put(theConstraint, theClConstraint);
					itsSolver.addConstraint(theClConstraint);
				}
				itsSolver.setAutosolve(true);
				
				itsSolver.solve();
			}
			catch (ExCLConstraintNotFound e)
			{
				throw new RuntimeException(e);
			}
			catch (ExCLInternalError e)
			{
				throw new RuntimeException(e);
			}
			catch (ExCLRequiredFailure e)
			{
				throw new RuntimeException(e);
			}
		}

		public void propertyValueChanged(IProperty<Double> aProperty)
		{
		}
	}
	
	/**
	 * This listener is called whenever the value of a variable changes.
	 * It tells the solver to 
	 * @author gpothier
	 */
	private class VariablesListener implements IPropertyVeto<Double>
	{
		public boolean canChangeProperty(IProperty<Double> aProperty, Double aOldValue, Double aNewValue)
		{
			ConstrainedDouble theVariable = (ConstrainedDouble) aProperty;
			try
			{
				theVariable.setEditing(true);
				
				itsSolver.addEditVar(theVariable);
				itsSolver.beginEdit();
				itsSolver.suggestValue(theVariable, aNewValue);
				itsSolver.resolve();
				itsSolver.endEdit();
				
				return true;
			}
			catch (ExCLRequiredFailure e)
			{
				return false;
			}
			catch (ExCLTooDifficult e)
			{
				return false;
			}
			catch (ExCLError e)
			{
				throw new RuntimeException("Problem encountered while editing variable", e);
			}
			finally
			{
				theVariable.setEditing(false);
			}
		}

		public void propertyChanged(IProperty<Double> aProperty, Double aOldValue, Double aNewValue)
		{
		}

		public void propertyValueChanged(IProperty<Double> aProperty)
		{
		}
		
	}
	
	/**
	 * This singleton class permits to check the consistency of the constraint systems.
	 * In particular it ensures that a variable cannot be used as variable in more than
	 * one system.
	 * @author gpothier
	 */
	private static class SystemsChecker
	{
		private static SystemsChecker INSTANCE = new SystemsChecker();

		public static SystemsChecker getInstance()
		{
			return INSTANCE;
		}

		private SystemsChecker()
		{
		}
		
		private Map<IConstrainedDouble, ConstraintSystem> itsVariablesMap =
			new HashMap<IConstrainedDouble, ConstraintSystem>();
		
		/**
		 * This method is called whenever a variable is checked into a system.
		 * A variable cannot be of type {@link Constraint.Type#VAR} in different systems.
		 * 
		 * @param aVariable The checked in variable
		 * @param aType The type of the variable
		 * @param aSystem The system into which the variable is checked in.
		 */
		public void checkInVariable (IConstrainedDouble aVariable, Constraint.Type aType, ConstraintSystem aSystem)
		{
			if (aType == Constraint.Type.VAR)
			{
				ConstraintSystem theSystem = itsVariablesMap.get(aVariable);
				if (theSystem != null)
				{
					throw new RuntimeException(String.format(
							"The variable %s cannot be checked into system %s because it is already used by system %s",
							aVariable,
							aSystem,
							theSystem));
				}
				
				itsVariablesMap.put(aVariable, aSystem);
				System.out.println(String.format(
							"Checked in variable %s into system %s",
							aVariable,
							aSystem));
			}
		}
		
		public void checkOutVariable (IConstrainedDouble aVariable, Constraint.Type aType, ConstraintSystem aSystem)
		{
			if (aType == Constraint.Type.VAR)
			{
				System.out.println(String.format(
						"Checking out variable %s from system %s...",
						aVariable,
						aSystem));
				
				ConstraintSystem theSystem = itsVariablesMap.remove(aVariable);
				if (theSystem != aSystem)
				{
					throw new RuntimeException(String.format(
							"Internal error: the variable %s cannot be checked out of system %s because it is checked in system %s",
							aVariable,
							aSystem,
							theSystem));
				}
			}
		}
	}
}
