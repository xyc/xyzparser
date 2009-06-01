package ast;
/* Copyright (c) 2006, Sun Microsystems, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright notice,
 *       this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the Sun Microsystems, Inc. nor the names of its
 *       contributors may be used to endorse or promote products derived from
 *       this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

public class TestVisitor implements XYZParserVisitor
{
	private int statementCount = 0;
  private int indent = 0;
  private int totalWeight = 0;
  private StringBuffer parseMessage = new StringBuffer();

  public Object visit(SimpleNode node, Object data){
  	System.out.println(indentString() + node +
                   ": acceptor not unimplemented in subclass?");
    ++indent;
    data = node.childrenAccept(this, data);
    --indent;
    return data;
  }
  public Object visit(Root node, Object data){
	  parseMessage.append(indentString() + node+"\n");
  	 System.out.println(indentString() + node);
    ++indent;
    data = node.childrenAccept(this, data);
    --indent;
    return data;
  	}
  public Object visit(Program node, Object data)
  {
	 parseMessage.append(indentString() + node+"\n");
  	 System.out.println(indentString() + node);
    ++indent;
    data = node.childrenAccept(this, data);
    --indent;
    
    return data;
  }
  public Object visit(MainClass node, Object data)
    {
	  parseMessage.append(indentString() + node+":"+node.getText()+"\n");
  	 System.out.println(indentString() + node +":" + node.getText());
    ++indent;
    data = node.childrenAccept(this, data);
    
      //weight = childrenWeight
	    for(int i = 0; i < node.jjtGetNumChildren(); i++){
	    	SimpleNode childNode = (SimpleNode)node.jjtGetChild(i);
	    	node.setWeight(node.getWeight() + childNode.getWeight());
	    }
	    node.setWeight(node.getWeight());
	    totalWeight += node.getWeight();
    
    --indent;
    parseMessage.append(indentString()+ "Weight = "+node.getWeight()+"\n");
    System.out.println(indentString() + "Weight = "+node.getWeight());
    return data;
  }
  public Object visit(ClassDecl node, Object data)
    {
	  parseMessage.append(indentString() + node+":"+node.getText()+"\n");
  	 System.out.println(indentString() + node +":" + node.getText());
    ++indent;
    data = node.childrenAccept(this, data);
    
    //weight = childrenWeight
	    for(int i = 0; i < node.jjtGetNumChildren(); i++){
	    	SimpleNode childNode = (SimpleNode)node.jjtGetChild(i);
	    	node.setWeight(node.getWeight() + childNode.getWeight());
	    }
	    node.setWeight(node.getWeight());
	    totalWeight += node.getWeight();
	    
    
    --indent;
    parseMessage.append(indentString()+ "Weight = "+node.getWeight()+"\n");
    System.out.println(indentString() + "Weight = "+node.getWeight());
    return data;
  }
  
  public Object visit(VarDeclNode node, Object data){
	  parseMessage.append(indentString() + node+":"+node.getText()+"\n");
  		System.out.println(node.getText());
  		data = node.childrenAccept(this, data);
  		return data;
  }
  
    public Object visit(FormalListNode node, Object data){
    	 parseMessage.append(indentString() + node+":"+node.getText()+"\n");
    	System.out.println(node.getText());
  		data = node.childrenAccept(this, data);
  		return data;
    	}
  public Object visit(FormalRestNode node, Object data){
	  parseMessage.append(indentString() + node+":"+node.getText()+"\n");
  	System.out.println(node.getText());
  		data = node.childrenAccept(this, data);
  		return data;
  	}
  
  public Object visit(MethodNode node, Object data){
	  parseMessage.append(indentString() + node+":"+node.getText()+"\n");
  		 System.out.println(indentString() + node + ":" + node.getText());
    ++indent;
    data = node.childrenAccept(this, data);
    
    	//weight = childrenWeight
	    for(int i = 0; i < node.jjtGetNumChildren(); i++){
	    	SimpleNode childNode = (SimpleNode)node.jjtGetChild(i);
	    	node.setWeight(node.getWeight() + childNode.getWeight());
	    }
	    node.setWeight(node.getWeight());
    
    --indent;
    parseMessage.append(indentString()+ "Weight = "+node.getWeight()+"\n");
    System.out.println(indentString() + "Weight = "+node.getWeight());
    return data;
  	}
  
  public Object visit(StatementNode node, Object data){
	  parseMessage.append(indentString() + node+":"+node.getText()+"\n");
  	System.out.println(indentString() + node);
    ++indent;
    data = node.childrenAccept(this, data);
    --indent;
    return data;
  }

	  public Object visit(IfStatementNode node, Object data){
		  parseMessage.append(indentString() + node+":"+node.getText()+"\n");
	  	System.out.println(indentString() + node);
	    ++indent;
	    data = node.childrenAccept(this, data);
	    
	    //weight = 2 * childrenWeight
	    for(int i = 0; i < node.jjtGetNumChildren(); i++){
	    	SimpleNode childNode = (SimpleNode)node.jjtGetChild(i);
	    	node.setWeight(node.getWeight() + childNode.getWeight());
	    }
	    node.setWeight(2*node.getWeight());	    
	    
	    --indent;
	    parseMessage.append(indentString()+ "Weight = "+node.getWeight()+"\n");
	    System.out.println(indentString() + "Weight = "+node.getWeight());
	    return data;
	  }
	  
  public Object visit(WhileStatementNode node, Object data){
	  parseMessage.append(indentString() + node+":"+node.getText()+"\n");
	  	System.out.println(indentString() + node);
	    ++indent;
	    data = node.childrenAccept(this, data);
	    
	    //weight = 4 * childrenWeight
	    for(int i = 0; i < node.jjtGetNumChildren(); i++){
	    	SimpleNode childNode = (SimpleNode)node.jjtGetChild(i);
	    	node.setWeight(node.getWeight() + childNode.getWeight());
	    }
	    node.setWeight(4*node.getWeight());
	    
	    --indent;
	    parseMessage.append(indentString()+ "Weight = "+node.getWeight()+"\n");
	    System.out.println(indentString() + "Weight = "+node.getWeight());
	    return data;
  	}

	public Object visit(SingleStatementNode node, Object data){
			statementCount++;
			parseMessage.append(indentString() + node+":"+node.getText()+"\n");
	  	System.out.println(indentString() + node);
	    ++indent;
	    
	    //weight = 1
	    node.setWeight(1);
	    
	    data = node.childrenAccept(this, data);
	    --indent;
	    parseMessage.append(indentString()+ "Weight = "+node.getWeight()+"\n");
	    System.out.println(indentString() + "Weight = "+node.getWeight());
	    return data;
		}
		
		public Object visit(ReturnStatementNode node, Object data){
			statementCount++;
			parseMessage.append(indentString() + node+":"+node.getText()+"\n");
	  	System.out.println(indentString() + node);
	    ++indent;
	    
	    //weight = 1
	    node.setWeight(1);
	    
	    
	    data = node.childrenAccept(this, data);
	    --indent;
	    parseMessage.append(indentString()+ "Weight = "+node.getWeight()+"\n");
	    System.out.println(indentString() + "Weight = "+node.getWeight());
	    return data;
		}

  private String indentString() {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < indent; ++i) {
      sb.append(' ');
    }
    return sb.toString();
  }
 
 	public int getStatementCount(){
 			return statementCount;
 		}
 		
 	public int getTotalWeight(){
 			return totalWeight;
 		}
	
	public Object visit(JudgeExpNode node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Object visit(PrintStatementNode node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Object visit(AssignStatementNode node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Object visit(AssignLeftNode node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Object visit(AssignRightNode node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Object visit(PrimitiveTypeNode node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Object visit(ObjectTypeNode node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Object visit(ExpNode node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Object visit(NewExpNode node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Object visit(IntArrayNode node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Object visit(DoubleArrayNode node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Object visit(AndExpNode node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Object visit(NotExpNode node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Object visit(LessExpNode node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Object visit(AddExpNode node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Object visit(MinusExpNode node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Object visit(MultiExpNode node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Object visit(SignExpNode node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Object visit(LiteralConstantNode node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Object visit(IDExpNode node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Object visit(IndexNode node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Object visit(DotLengthNode node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Object visit(InvokeFunctionNode node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Object visit(IDNode node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Object visit(ThisNode node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Object visit(ExtendNode node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}
	public StringBuffer getParseMessage() {
		return parseMessage;
	}
	public void setParseMessage(StringBuffer parseMessage) {
		this.parseMessage = parseMessage;
	}
	public Object visit(CallExpNode node, Object data) {
		// TODO 自动生成方法存根
		return null;
	}
	public Object visit(MethodIDNode node, Object data) {
		// TODO 自动生成方法存根
		return null;
	}
	public Object visit(ExpListNode node, Object data) {
		// TODO 自动生成方法存根
		return null;
	}
	public Object visit(ExpRestNode node, Object data) {
		// TODO 自动生成方法存根
		return null;
	}
	public Object visit(ElseNode node, Object data) {
		// TODO 自动生成方法存根
		return null;
	}
	public Object visit(DoWhileStatement node, Object data) {
		// TODO 自动生成方法存根
		return null;
	}
	public Object visit(ArrayElementNode node, Object data) {
		// TODO 自动生成方法存根
		return null;
	}
	public Object visit(IDElementNode node, Object data) {
		// TODO 自动生成方法存根
		return null;
	}
	public Object visit(ConstantElementNode node, Object data) {
		// TODO 自动生成方法存根
		return null;
	}
	public Object visit(ChildExpNode node, Object data) {
		// TODO 自动生成方法存根
		return null;
	}
	public Object visit(Statement node, Object data) {
		// TODO 自动生成方法存根
		return null;
	}
}

/*end*/
