package semantic;

import ast.AddExpNode;
import ast.AndExpNode;
import ast.ArrayElementNode;
import ast.AssignLeftNode;
import ast.AssignRightNode;
import ast.AssignStatementNode;
import ast.CallExpNode;
import ast.ChildExpNode;
import ast.ClassDecl;
import ast.ConstantElementNode;
import ast.DoWhileStatement;
import ast.DotLengthNode;
import ast.DoubleArrayNode;
import ast.ElseNode;
import ast.ExpListNode;
import ast.ExpNode;
import ast.ExpRestNode;
import ast.ExtendNode;
import ast.FormalListNode;
import ast.FormalRestNode;
import ast.IDElementNode;
import ast.IDExpNode;
import ast.IDNode;
import ast.IfStatementNode;
import ast.IndexNode;
import ast.IntArrayNode;
import ast.InvokeFunctionNode;
import ast.JudgeExpNode;
import ast.LessExpNode;
import ast.LiteralConstantNode;
import ast.MainClass;
import ast.MethodIDNode;
import ast.MethodNode;
import ast.MinusExpNode;
import ast.MultiExpNode;
import ast.NewExpNode;
import ast.Node;
import ast.NotExpNode;
import ast.ObjectTypeNode;
import ast.PrimitiveTypeNode;
import ast.PrintStatementNode;
import ast.Program;
import ast.ReturnStatementNode;
import ast.Root;
import ast.SignExpNode;
import ast.SimpleNode;
import ast.SingleStatementNode;
import ast.Statement;
import ast.StatementNode;
import ast.ThisNode;
import ast.VarDeclNode;
import ast.WhileStatementNode;
import ast.XYZParserVisitor;

public class SymbolTableConstructVisitor implements XYZParserVisitor{

	private ProgramDescriptor programDescriptor = new ProgramDescriptor();
	private ClassDescriptor currentClassDescriptor;
	private MethodDescriptor currentMethodDescriptor;
	
	private String redefinedInfo = "Redefined Info :\n"; 
	
	public ProgramDescriptor getProgramDescriptor(){
		return programDescriptor;
	}
	
	private boolean insideMethod = false;
	
	
	public Object visit(SimpleNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	
	public Object visit(Root node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	
	public Object visit(Program node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	
	public Object visit(MainClass node, Object data) {
		IDNode n = (IDNode)node.jjtGetChild(0);
		IDNode args = (IDNode)node.jjtGetChild(1);
		//System.out.println(n.getText());
		String className = n.getText();
		//Declare main class here.
		//create type
		node.setType( Type.createType(className) );
		//add to symbol table
		ClassDescriptor classDesc = new ClassDescriptor();
		classDesc.id = className;
		
		//main method
		MethodDescriptor methodDesc = new MethodDescriptor();
		methodDesc.id = "main";
		methodDesc.returnType = "void";
		methodDesc.parameterTypes.add("String []");
		ParamDescriptor paramDesc = new ParamDescriptor();
		paramDesc.id = args.getText();
		paramDesc.typename = "String []";
		redefinedInfo += methodDesc.parameterSymbolTable.addSymbol(Symbol.createSymbol(args.getText()), paramDesc);
		redefinedInfo += classDesc.methodSymbolTable.addSymbol(Symbol.createSymbol("main"), methodDesc);
		
		
		//add to program
		programDescriptor.addClassDescriptor(classDesc);
		//set current
		currentClassDescriptor = classDesc;
		
		// TODO Auto-generated method stub
		data = node.childrenAccept(this, data);
		
		return data;
	}

	
	public Object visit(ClassDecl node, Object data) {
		IDNode n = (IDNode)node.jjtGetChild(0);
		String className = n.getText();
		//Declare class here.
		
		//create type
		node.setType( Type.createType(className) );
		//add to symbol table
		ClassDescriptor classDesc = new ClassDescriptor();
		classDesc.id = className;
		programDescriptor.addClassDescriptor(classDesc);
		//set current
		currentClassDescriptor = classDesc;
		
		if(node.jjtGetNumChildren()>2){
			Node sn = node.jjtGetChild(2);
			if(sn!=null&&sn instanceof ExtendNode){
				IDNode superClassNode = (IDNode)node.jjtGetChild(1);
				String superClassName = superClassNode.getText();
				classDesc.superClassName = superClassName;
			}
		}
		
		data = node.childrenAccept(this, data);
		return data;
	}

	
	public Object visit(IfStatementNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	
	public Object visit(WhileStatementNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	
	public Object visit(JudgeExpNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	
	public Object visit(SingleStatementNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	
	public Object visit(PrintStatementNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	
	public Object visit(AssignStatementNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	
	public Object visit(AssignLeftNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	
	public Object visit(AssignRightNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	
	public Object visit(VarDeclNode node, Object data) {
		if(insideMethod){
			SimpleNode typeNode = (SimpleNode)node.jjtGetChild(0);
			SimpleNode idNode = (SimpleNode)node.jjtGetChild(1);
			//construct descriptor
			LocalDescriptor localDesc = new LocalDescriptor();
			localDesc.typename = typeNode.getText();
			localDesc.id = idNode.getText();
			//add to local symbol table
			redefinedInfo += currentMethodDescriptor.localSymbolTable.addSymbol(Symbol.createSymbol(localDesc.id), localDesc);
			
			return null;
		}
		else{
		SimpleNode typeNode = (SimpleNode)node.jjtGetChild(0);
		SimpleNode idNode = (SimpleNode)node.jjtGetChild(1);
		//construct descriptor
		FieldDescriptor fieldDesc = new FieldDescriptor();
		fieldDesc.typename = typeNode.getText();
		fieldDesc.id = idNode.getText();
		//add to field symbol table
		redefinedInfo += currentClassDescriptor.fieldSymbolTable.addSymbol(Symbol.createSymbol(fieldDesc.id), fieldDesc);
		
		data = node.childrenAccept(this, data);
		return data;
		}
	}

	
	
	public Object visit(MethodNode node, Object data) {
		
		insideMethod = true;
		
		SimpleNode typeNode = (SimpleNode)node.jjtGetChild(0);
		SimpleNode idNode = (SimpleNode)node.jjtGetChild(1);
		//construct descriptor
		MethodDescriptor methodDesc = new MethodDescriptor();
		methodDesc.returnType = typeNode.getText();
		methodDesc.id = idNode.getText();
		
		//set current method
		currentMethodDescriptor = methodDesc;
		
		data = node.childrenAccept(this, data);
		MethodSymbol symbol = new MethodSymbol(methodDesc.id,currentMethodDescriptor.parameterTypes);
		redefinedInfo += currentClassDescriptor.methodSymbolTable.addSymbol(symbol, methodDesc);
		
		//Handler.printMethods(currentClassDescriptor);
		
		insideMethod = false;
		
		return data;
		
		
		/*insideMethod = true;
		
		SimpleNode typeNode = (SimpleNode)node.jjtGetChild(0);
		SimpleNode idNode = (SimpleNode)node.jjtGetChild(1);
		//construct descriptor
		MethodDescriptor methodDesc = new MethodDescriptor();
		methodDesc.returnType = typeNode.getText();
		methodDesc.id = idNode.getText();
		currentClassDescriptor.methodSymbolTable.addSymbol(Symbol.createSymbol(methodDesc.id), methodDesc);
		//set current method
		currentMethodDescriptor = methodDesc;
		
		data = node.childrenAccept(this, data);
		
		insideMethod = false;
		return data;*/
	}

	
	public Object visit(FormalListNode node, Object data) {
		SimpleNode typeNode = (SimpleNode)node.jjtGetChild(0);
		SimpleNode idNode = (SimpleNode)node.jjtGetChild(1);
		ParamDescriptor paramDesc = new ParamDescriptor();
		paramDesc.typename = typeNode.getText();
		paramDesc.id = idNode.getText();
		//add to method signature
		currentMethodDescriptor.parameterTypes.add(paramDesc.typename);
		//add to method symbol table
		redefinedInfo += currentMethodDescriptor.parameterSymbolTable.addSymbol(Symbol.createSymbol(paramDesc.id), paramDesc);
		
		data = node.childrenAccept(this, data);
		return data;
	}

	
	public Object visit(FormalRestNode node, Object data) {
		SimpleNode typeNode = (SimpleNode)node.jjtGetChild(0);
		SimpleNode idNode = (SimpleNode)node.jjtGetChild(1);
		ParamDescriptor paramDesc = new ParamDescriptor();
		
		//System.out.println("AAAAAAAAAAAA"+typeNode.getText());
		
		paramDesc.typename = typeNode.getText();
		paramDesc.id = idNode.getText();
		//add to method signature
		
		currentMethodDescriptor.parameterTypes.add(paramDesc.typename);
		//add to method symbol table
		redefinedInfo += currentMethodDescriptor.parameterSymbolTable.addSymbol(Symbol.createSymbol(paramDesc.id), paramDesc);
		
		data = node.childrenAccept(this, data);
		return data;
	}

	
	public Object visit(ReturnStatementNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	
	public Object visit(PrimitiveTypeNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	
	public Object visit(ObjectTypeNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	
	public Object visit(ExpNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	
	public Object visit(NewExpNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	
	public Object visit(IntArrayNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	
	public Object visit(DoubleArrayNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	
	public Object visit(AndExpNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	
	public Object visit(NotExpNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	
	public Object visit(LessExpNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	
	public Object visit(AddExpNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	
	public Object visit(MinusExpNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	
	public Object visit(MultiExpNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	
	public Object visit(SignExpNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	
	public Object visit(LiteralConstantNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	
	public Object visit(IDExpNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	
	public Object visit(IndexNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	
	public Object visit(DotLengthNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	
	public Object visit(InvokeFunctionNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	
	public Object visit(IDNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	
	public Object visit(ThisNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	
	public Object visit(ExtendNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
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
	
	public String getRedefinedInfo() {
		return redefinedInfo;
	}

	public void setRedefinedInfo(String redefinedInfo) {
		this.redefinedInfo = redefinedInfo;
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


	public Object visit(StatementNode node, Object data) {
		// TODO 自动生成方法存根
		return null;
	}

}
