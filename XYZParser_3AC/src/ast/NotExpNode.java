/* Generated By:JJTree: Do not edit this line. NotExpNode.java Version 4.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package ast;

public
class NotExpNode extends SimpleNode {
  private boolean judge = true;
  
  public NotExpNode(int id) {
    super(id);
  }

  public NotExpNode(XYZParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(XYZParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }

	public boolean isJudge() {
		return judge;
	}
	
	public void setJudge(boolean judge) {
		this.judge = judge;
	}
}
/* JavaCC - OriginalChecksum=61b95b7f5662daa824953c07d1d1120f (do not edit this line) */
