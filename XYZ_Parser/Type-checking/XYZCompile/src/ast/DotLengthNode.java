package ast;
/* Generated By:JJTree: Do not edit this line. DotLengthNode.java Version 4.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class DotLengthNode extends SimpleNode {
  public DotLengthNode(int id) {
    super(id);
  }

  public DotLengthNode(XYZParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(XYZParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=ccaa07b67944ad5f4e0092486c8e267b (do not edit this line) */
