/* Generated By:JJTree: Do not edit this line. WhileStatementNode.java Version 4.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package ast;

public
class WhileStatementNode extends SimpleNode {
  public WhileStatementNode(int id) {
    super(id);
  }

  public WhileStatementNode(XYZParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(XYZParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=5a0215e3db5bed160b5f1e0c37c953c3 (do not edit this line) */
