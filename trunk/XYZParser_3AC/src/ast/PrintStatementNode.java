/* Generated By:JJTree: Do not edit this line. PrintStatementNode.java Version 4.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package ast;

public
class PrintStatementNode extends SimpleNode {
  public PrintStatementNode(int id) {
    super(id);
  }

  public PrintStatementNode(XYZParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(XYZParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=ed33c054e5f0525516c3cd79a61bda6a (do not edit this line) */
