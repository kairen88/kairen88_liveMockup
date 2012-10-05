// $Id: ClAbstractVariable.java,v 1.13 1999/04/20 00:26:25 gjb Exp $
//
// Cassowary Incremental Constraint Solver
// Original Smalltalk Implementation by Alan Borning
// This Java Implementation by Greg J. Badros, <gjb@cs.washington.edu>
// http://www.cs.washington.edu/homes/gjb
// (C) 1998, 1999 Greg J. Badros and Alan Borning
// See ../LICENSE for legal details regarding this software
//
// ClAbstractVariable

package EDU.Washington.grad.gjb.cassowary;

import java.lang.*;

public interface IVariable
{
  public boolean isDummy();
  public boolean isExternal();
  public boolean isPivotable();
  public boolean isRestricted();
}
