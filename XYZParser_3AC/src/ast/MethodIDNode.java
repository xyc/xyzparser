/* Generated By:JJTree: Do not edit this line. MethodIDNode.java Version 4.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package ast;

public
class MethodIDNode extends SimpleNode {
  public MethodIDNode(int id) {
    super(id);
  }

  public MethodIDNode(XYZParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(XYZParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=7b81fef3995ed8e8be0a084b9fc2cbc4 (do not edit this line) */
