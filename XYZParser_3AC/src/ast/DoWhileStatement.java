/* Generated By:JJTree: Do not edit this line. DoWhileStatement.java Version 4.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package ast;

public
class DoWhileStatement extends SimpleNode {
  public DoWhileStatement(int id) {
    super(id);
  }

  public DoWhileStatement(XYZParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(XYZParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=b71a170a0c4e547c1df90c9268a211d1 (do not edit this line) */
