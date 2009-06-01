package parser2;

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
 
 import java.util.*;

public class InfoVisitor implements XYZParserVisitor
{
  private ArrayList<ClassInfo> classInfos = new ArrayList<ClassInfo>();
  private ClassInfo classInfo;
  private MethodInfo methodInfo;

  public Object visit(SimpleNode node, Object data){
    data = node.childrenAccept(this, data);
    return data;
  }
  public Object visit(Root node, Object data){
    data = node.childrenAccept(this, data);
    return data;
  	}
  public Object visit(Program node, Object data)
  {
    data = node.childrenAccept(this, data); 
    return data;
  }
  public Object visit(MainClass node, Object data)
    {
    
    classInfo = new ClassInfo();
    classInfo.setIsMainClass(true);
    String classText = node.getText();
    classInfo.setClassName(classText.substring(classText.lastIndexOf(' ')+1));
    
    data = node.childrenAccept(this, data);
    
    
    classInfos.add(classInfo);
    
    return data;
  }
  public Object visit(ClassDecl node, Object data)
    {
    
    classInfo = new ClassInfo();
    classInfo.setIsMainClass(false);
    String classText = node.getText();
    classInfo.setClassName(classText.substring(classText.lastIndexOf(' ')+1));
    
    data = node.childrenAccept(this, data);
    
    classInfos.add(classInfo);
	    
    
    return data;
  }
  
  public Object visit(VarDeclNode node, Object data){
  		
  		VarInfo varInfo = new VarInfo();
  		String varText = node.getText();
  		varInfo.setVarName(varText.substring(varText.lastIndexOf(' ')+1));
  		varInfo.setVarType(varText.substring(0, varText.indexOf(' ')));
  		classInfo.addVarInfo(varInfo);
  		
  		data = node.childrenAccept(this, data);
  		return data;
  }
  
    public Object visit(FormalListNode node, Object data){
    	
    	VarInfo varInfo = new VarInfo();
    	String varText = node.getText();
    	if(!"".equals(varText)){
    	varInfo.setVarName(varText.substring(varText.lastIndexOf(' ')+1));

    	varInfo.setVarType(varText.substring(0, varText.indexOf(' ')));
    	methodInfo.addFormalInfo(varInfo);
    	}
  		data = node.childrenAccept(this, data);
  		return data;
    	}
  public Object visit(FormalRestNode node, Object data){
  	
  	VarInfo varInfo = new VarInfo();
	String varText = node.getText();
	varInfo.setVarName(varText.substring(varText.lastIndexOf(' ')+1));
	varInfo.setVarType(varText.substring(0, varText.indexOf(' ')));
	methodInfo.addFormalInfo(varInfo);
  	
  		data = node.childrenAccept(this, data);
  		return data;
  	}
  
  public Object visit(MethodNode node, Object data){
    
    methodInfo = new MethodInfo();
    String methodText = node.getText();
    methodInfo.setMethodName(methodText.substring(methodText.lastIndexOf(' ')+1));
    methodInfo.setReturnType(methodText.substring(0, methodText.indexOf(' ')));
    
    data = node.childrenAccept(this, data);
    
    classInfo.addMethodInfo(methodInfo);

    return data;
  	}
  
  public Object visit(StatementNode node, Object data){
    data = node.childrenAccept(this, data);
    return data;
  }

	  public Object visit(IfStatementNode node, Object data){
	    data = node.childrenAccept(this, data);
	    
	    //weight = 2 * childrenWeight
	    for(int i = 0; i < node.jjtGetNumChildren(); i++){
	    	SimpleNode childNode = (SimpleNode)node.jjtGetChild(i);
	    	node.setWeight(node.getWeight() + childNode.getWeight());
	    }
	    node.setWeight(2*node.getWeight());	    
	    
	    return data;
	  }
	  
  public Object visit(WhileStatementNode node, Object data){

	    data = node.childrenAccept(this, data);

	    return data;
  	}

	public Object visit(SingleStatementNode node, Object data){
	       
	    data = node.childrenAccept(this, data);
	    return data;
		}
		
		public Object visit(ReturnStatementNode node, Object data){
	    
	    data = node.childrenAccept(this, data);
	    return data;
		}
 	
 	public ArrayList<ClassInfo> getClassInfos(){
 		return classInfos;	
 	}
}

/*end*/
