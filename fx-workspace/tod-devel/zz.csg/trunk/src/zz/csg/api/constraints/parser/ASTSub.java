/* Generated By:JJTree: Do not edit this line. ASTSub.java */

package zz.csg.api.constraints.parser;

public class ASTSub extends SimpleNode {
  public ASTSub(int id) {
    super(id);
  }

  public ASTSub(ArithmeticParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(ArithmeticParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}