package parser2;

/* Generated By:JJTree: Do not edit this line. FormalRestNode.java Version 4.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class FormalRestNode extends SimpleNode {
  public FormalRestNode(int id) {
    super(id);
  }

  public FormalRestNode(XYZParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(XYZParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=df479bee49fc548f23f5fd9aa29f05f9 (do not edit this line) */
