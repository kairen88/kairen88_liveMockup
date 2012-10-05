/* Generated By:JJTree: Do not edit this line. ASTIdentifier.java */

package zz.csg.api.constraints.parser;

public class ASTIdentifier extends SimpleNode {
	private String itsName;
	
  public ASTIdentifier(int id) {
    super(id);
  }

  public ASTIdentifier(ArithmeticParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(ArithmeticParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }

public String getName()
{
	return itsName;
}

public void setName(String aName)
{
	itsName = aName;
}
  
@Override
public String toString()
{
	return "Id: "+itsName;
}
}
