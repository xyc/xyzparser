package ast;
/* Generated By:JJTree: Do not edit this line. AssignStatementNode.java Version 4.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class AssignStatementNode extends SimpleNode {
  public AssignStatementNode(int id) {
    super(id);
  }

  public AssignStatementNode(XYZParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(XYZParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=76b62a4e67ba34d749ca12f287be62cd (do not edit this line) */
