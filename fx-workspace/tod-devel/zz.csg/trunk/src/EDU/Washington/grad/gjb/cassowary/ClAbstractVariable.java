/*
 * Created on Jun 2, 2005
 */
package EDU.Washington.grad.gjb.cassowary;

public abstract class ClAbstractVariable implements IVariable
{
	 public ClAbstractVariable(String name)
	    {
	      //hash_code = iVariableNumber;
	      _name = name;
	      iVariableNumber++;
	    }

	  public ClAbstractVariable()
	    {
	      //hash_code = iVariableNumber;
	      _name = "v" + iVariableNumber;
	      iVariableNumber++;
	    }

	  public ClAbstractVariable(long varnumber, String prefix)
	    {
	      //hash_code = iVariableNumber;
	      _name = prefix + varnumber;
	      iVariableNumber++;
	    }

	  public String name()
	    { return _name; }
	  
	  public void setName(String name)
	    { _name = name; }
	  
	  public boolean isDummy()
	    { return false; }
	  

	  public static int numCreated() 
	    { return iVariableNumber; }

	  // for debugging
	  //  public final int hashCode() { return hash_code; }

	  private String _name;

	  // for debugging
	  // private int hash_code;

	  private static int iVariableNumber;

}
