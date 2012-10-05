// $Id: ClVariable.java,v 1.12 1999/04/20 00:26:45 gjb Exp $
//
// Cassowary Incremental Constraint Solver
// Original Smalltalk Implementation by Alan Borning
// This Java Implementation by Greg J. Badros, <gjb@cs.washington.edu>
// http://www.cs.washington.edu/homes/gjb
// (C) 1998, 1999 Greg J. Badros and Alan Borning
// See ../LICENSE for legal details regarding this software
//
// ClVariable

package EDU.Washington.grad.gjb.cassowary;

import java.util.*;

public interface ISimpleVariable extends IVariable
{
  public double value();
  public void change_value(double value);
}
