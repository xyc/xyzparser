package semantic;

import ast.AddExpNode;
import ast.AndExpNode;
import ast.AssignLeftNode;
import ast.AssignRightNode;
import ast.AssignStatementNode;
import ast.ClassDecl;
import ast.DotLengthNode;
import ast.DoubleArrayNode;
import ast.ExpNode;
import ast.ExtendNode;
import ast.FormalListNode;
import ast.FormalRestNode;
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
import ast.ThisNode;
import ast.VarDeclNode;
import ast.WhileStatementNode;
import ast.XYZParserVisitor;

public class SymbolTableConstructVisitor implements XYZParserVisitor{

	private ProgramDescriptor programDescriptor = new ProgramDescriptor();
	private ClassDescriptor currentClassDescriptor;
	private MethodDescriptor currentMethodDescriptor;
	
	public ProgramDescriptor getProgramDescriptor(){
		return programDescriptor;
	}
	
	private boolean insideMethod = false;
	
	@Override
	public Object visit(SimpleNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	@Override
	public Object visit(Root node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	@Override
	public Object visit(Program node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	@Override
	public Object visit(MainClass node, Object data) {
		IDNode n = (IDNode)node.jjtGetChild(0);
		IDNode args = (IDNode)node.jjtGetChild(0);
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
		methodDesc.parameterSymbolTable.addSymbol(Symbol.createSymbol(args.getText()), paramDesc);		
		classDesc.methodSymbolTable.addSymbol(Symbol.createSymbol("main"), methodDesc);
		
		//add to program
		programDescriptor.addClassDescriptor(classDesc);
		//set current
		currentClassDescriptor = classDesc;
		
		// TODO Auto-generated method stub
		data = node.childrenAccept(this, data);
		
		return data;
	}

	@Override
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

	@Override
	public Object visit(IfStatementNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	@Override
	public Object visit(WhileStatementNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	@Override
	public Object visit(JudgeExpNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	@Override
	public Object visit(SingleStatementNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	@Override
	public Object visit(PrintStatementNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	@Override
	public Object visit(AssignStatementNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	@Override
	public Object visit(AssignLeftNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	@Override
	public Object visit(AssignRightNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	@Override
	public Object visit(VarDeclNode node, Object data) {
		if(insideMethod){
			SimpleNode typeNode = (SimpleNode)node.jjtGetChild(0);
			SimpleNode idNode = (SimpleNode)node.jjtGetChild(1);
			//construct descriptor
			LocalDescriptor localDesc = new LocalDescriptor();
			localDesc.typename = typeNode.getText();
			localDesc.id = idNode.getText();
			//add to local symbol table
			currentMethodDescriptor.localSymbolTable.addSymbol(Symbol.createSymbol(localDesc.id), localDesc);
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
		currentClassDescriptor.fieldSymbolTable.addSymbol(Symbol.createSymbol(fieldDesc.id), fieldDesc);
		
		data = node.childrenAccept(this, data);
		return data;
		}
	}

	@Override
	public Object visit(MethodNode node, Object data) {
		insideMethod = true;
		
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
		return data;
	}

	@Override
	public Object visit(FormalListNode node, Object data) {
		SimpleNode typeNode = (SimpleNode)node.jjtGetChild(0);
		SimpleNode idNode = (SimpleNode)node.jjtGetChild(1);
		ParamDescriptor paramDesc = new ParamDescriptor();
		paramDesc.typename = typeNode.getText();
		paramDesc.id = idNode.getText();
		//add to method signature
		currentMethodDescriptor.parameterTypes.add(paramDesc.typename);
		//add to method symbol table
		currentMethodDescriptor.parameterSymbolTable.addSymbol(Symbol.createSymbol(paramDesc.id), paramDesc);
		
		data = node.childrenAccept(this, data);
		return data;
	}

	@Override
	public Object visit(FormalRestNode node, Object data) {
		SimpleNode typeNode = (SimpleNode)node.jjtGetChild(0);
		SimpleNode idNode = (SimpleNode)node.jjtGetChild(1);
		ParamDescriptor paramDesc = new ParamDescriptor();
		paramDesc.typename = typeNode.getText();
		paramDesc.id = idNode.getText();
		//add to method signature
		currentMethodDescriptor.parameterTypes.add(paramDesc.typename);
		//add to method symbol table
		currentMethodDescriptor.parameterSymbolTable.addSymbol(Symbol.createSymbol(paramDesc.id), paramDesc);
		
		data = node.childrenAccept(this, data);
		return data;
	}

	@Override
	public Object visit(ReturnStatementNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	@Override
	public Object visit(PrimitiveTypeNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	@Override
	public Object visit(ObjectTypeNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	@Override
	public Object visit(ExpNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	@Override
	public Object visit(NewExpNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	@Override
	public Object visit(IntArrayNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	@Override
	public Object visit(DoubleArrayNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	@Override
	public Object visit(AndExpNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	@Override
	public Object visit(NotExpNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	@Override
	public Object visit(LessExpNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	@Override
	public Object visit(AddExpNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	@Override
	public Object visit(MinusExpNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	@Override
	public Object visit(MultiExpNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	@Override
	public Object visit(SignExpNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	@Override
	public Object visit(LiteralConstantNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	@Override
	public Object visit(IDExpNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	@Override
	public Object visit(IndexNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	@Override
	public Object visit(DotLengthNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	@Override
	public Object visit(InvokeFunctionNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	@Override
	public Object visit(IDNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	@Override
	public Object visit(ThisNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	@Override
	public Object visit(ExtendNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

}
