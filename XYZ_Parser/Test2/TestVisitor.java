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

  public Object visit(SimpleNode node, Object data){
  	System.out.println(indentString() + node +
                   ": acceptor not unimplemented in subclass?");
    ++indent;
    data = node.childrenAccept(this, data);
    --indent;
    return data;
  }
  public Object visit(Root node, Object data){
  	 System.out.println(indentString() + node);
    ++indent;
    data = node.childrenAccept(this, data);
    --indent;
    return data;
  	}
  public Object visit(Program node, Object data)
  {
  	 System.out.println(indentString() + node);
    ++indent;
    data = node.childrenAccept(this, data);
    --indent;
    return data;
  }
  public Object visit(MainClass node, Object data)
    {
  	 System.out.println(indentString() + node);
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
    System.out.println(indentString() + "Weight = "+node.getWeight());
    return data;
  }
  public Object visit(ClassDecl node, Object data)
    {
  	 System.out.println(indentString() + node);
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
    System.out.println(indentString() + "Weight = "+node.getWeight());
    return data;
  }
  
  public Object visit(MethodNode node, Object data){
  		 System.out.println(indentString() + node);
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
    System.out.println(indentString() + "Weight = "+node.getWeight());
    return data;
  	}
  
  public Object visit(StatementNode node, Object data){
  	System.out.println(indentString() + node);
    ++indent;
    data = node.childrenAccept(this, data);
    --indent;
    return data;
  }

	  public Object visit(IfStatementNode node, Object data){
	  	System.out.println(indentString() + node);
	    ++indent;
	    data = node.childrenAccept(this, data);
	    
	    //weight = 2 * childrenWeight
	    for(int i = 0; i < node.jjtGetNumChildren(); i++){
	    	SimpleNode childNode = (SimpleNode)node.jjtGetChild(i);
	    	node.setWeight(node.getWeight() + childNode.getWeight());
	    }
	    node.setWeight(2*node.getWeight());
	    totalWeight += node.getWeight();
	    
	    
	    --indent;
	    System.out.println(indentString() + "Weight = "+node.getWeight());
	    return data;
	  }
	  
  public Object visit(WhileStatementNode node, Object data){
	  	System.out.println(indentString() + node);
	    ++indent;
	    data = node.childrenAccept(this, data);
	    
	    //weight = 4 * childrenWeight
	    for(int i = 0; i < node.jjtGetNumChildren(); i++){
	    	SimpleNode childNode = (SimpleNode)node.jjtGetChild(i);
	    	node.setWeight(node.getWeight() + childNode.getWeight());
	    }
	    node.setWeight(4*node.getWeight());
	    totalWeight += node.getWeight();
	    
	    
	    --indent;
	    System.out.println(indentString() + "Weight = "+node.getWeight());
	    return data;
  	}

	public Object visit(SingleStatementNode node, Object data){
			statementCount++;
	  	System.out.println(indentString() + node);
	    ++indent;
	    
	    //weight = 1
	    node.setWeight(1);
	    totalWeight += node.getWeight();
	    
	    
	    data = node.childrenAccept(this, data);
	    --indent;
	    System.out.println(indentString() + "Weight = "+node.getWeight());
	    return data;
		}
		
		public Object visit(ReturnStatementNode node, Object data){
			statementCount++;
	  	System.out.println(indentString() + node);
	    ++indent;
	    
	    //weight = 1
	    node.setWeight(1);
	    totalWeight += node.getWeight();
	    
	    
	    data = node.childrenAccept(this, data);
	    --indent;
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
}

/*end*/
