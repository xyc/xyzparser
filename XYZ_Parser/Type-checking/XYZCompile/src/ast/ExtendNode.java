package ast;

/* Generated By:JJTree: Do not edit this line. ExtendNode.java Version 4.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ExtendNode extends SimpleNode {
  public ExtendNode(int id) {
    super(id);
  }

  public ExtendNode(XYZParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(XYZParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=ebfbc4134cb6268890c26674be8f4dde (do not edit this line) */
