/* Generated By:JJTree: Do not edit this line. IndexNode.java Version 4.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package ast;

public
class IndexNode extends SimpleNode {
  public IndexNode(int id) {
    super(id);
  }

  public IndexNode(XYZParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(XYZParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=072ec48c3d5ee48901c708dea65aa8dc (do not edit this line) */
