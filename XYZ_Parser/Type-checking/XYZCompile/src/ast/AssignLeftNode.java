package ast;
/* Generated By:JJTree: Do not edit this line. AssignLeftNode.java Version 4.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class AssignLeftNode extends SimpleNode {
  public AssignLeftNode(int id) {
    super(id);
  }

  public AssignLeftNode(XYZParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(XYZParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=c7c9092a5d3d440b735d054bd4f09816 (do not edit this line) */
