package ast;
/* Generated By:JJTree: Do not edit this line. MinusExpNode.java Version 4.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class MinusExpNode extends SimpleNode {
  public MinusExpNode(int id) {
    super(id);
  }

  public MinusExpNode(XYZParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(XYZParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=ac7ba66e19d39152d762b975fbe73524 (do not edit this line) */