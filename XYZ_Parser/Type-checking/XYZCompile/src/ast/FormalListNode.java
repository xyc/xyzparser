package ast;
/* Generated By:JJTree: Do not edit this line. FormalListNode.java Version 4.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class FormalListNode extends SimpleNode {
  public FormalListNode(int id) {
    super(id);
  }

  public FormalListNode(XYZParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(XYZParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=29c68353cbb76472b0e2b73f888b9a8f (do not edit this line) */
