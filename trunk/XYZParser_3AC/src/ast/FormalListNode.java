/* Generated By:JJTree: Do not edit this line. FormalListNode.java Version 4.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package ast;

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
/* JavaCC - OriginalChecksum=350f111a6d78ea8d55c1053943a59747 (do not edit this line) */