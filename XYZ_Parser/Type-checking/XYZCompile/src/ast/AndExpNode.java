package ast;
/* Generated By:JJTree: Do not edit this line. AndExpNode.java Version 4.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class AndExpNode extends SimpleNode {
  public AndExpNode(int id) {
    super(id);
  }

  public AndExpNode(XYZParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(XYZParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=1f7d3bc6a39ff51e9272f40a944bd60b (do not edit this line) */
