package ast;
/* Generated By:JJTree: Do not edit this line. LiteralConstantNode.java Version 4.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class LiteralConstantNode extends SimpleNode {
  public LiteralConstantNode(int id) {
    super(id);
  }

  public LiteralConstantNode(XYZParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(XYZParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=a0fbc23e925813c41960b64a815bed1f (do not edit this line) */
