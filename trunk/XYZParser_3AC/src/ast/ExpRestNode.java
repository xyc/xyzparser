/* Generated By:JJTree: Do not edit this line. ExpRestNode.java Version 4.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package ast;

public
class ExpRestNode extends SimpleNode {
  public ExpRestNode(int id) {
    super(id);
  }

  public ExpRestNode(XYZParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(XYZParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=62f1a97ec5d05a7ca56901db59ad6fd1 (do not edit this line) */