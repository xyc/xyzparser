/* Generated By:JJTree: Do not edit this line. IfStatementNode.java Version 4.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package ast;

public
class IfStatementNode extends SimpleNode {
  public IfStatementNode(int id) {
    super(id);
  }

  public IfStatementNode(XYZParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(XYZParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=7df493da3aa4f2a95e981b039b73fc5f (do not edit this line) */
