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

public class TypeCheckingVistor implements XYZParserVisitor{

	private SymbolTableConstructVisitor symbolTableConstructor = new SymbolTableConstructVisitor();
	private ProgramDescriptor programDescriptor;
	private ClassDescriptor currentClassDescriptor;
	private MethodDescriptor currentMethodDescriptor;
	
	@Override
	public Object visit(SimpleNode node, Object data) {
		data = node.childrenAccept(this, data);
		return null;
	}

	@Override
	public Object visit(Root node, Object data) {
		node.jjtAccept(symbolTableConstructor, null);
		programDescriptor = symbolTableConstructor.getProgramDescriptor();
		
		data = node.childrenAccept(this, data);
		return null;
	}

	@Override
	public Object visit(Program node, Object data) {
		data = node.childrenAccept(this, data);
		return null;
	}

	@Override
	public Object visit(MainClass node, Object data) {
		IDNode n = (IDNode)node.jjtGetChild(0);
		currentClassDescriptor = programDescriptor.classSymbolTable.getDescriptor(Symbol.createSymbol(n.getText()));
		currentMethodDescriptor = currentClassDescriptor.methodSymbolTable.getDescriptor("main");
		
		data = node.childrenAccept(this, data);
		return null;
	}

	@Override
	public Object visit(ClassDecl node, Object data) {
		IDNode n = (IDNode)node.jjtGetChild(0);
		currentClassDescriptor = programDescriptor.classSymbolTable.getDescriptor(Symbol.createSymbol(n.getText()));
		
		data = node.childrenAccept(this, data);
		return null;
	}

	@Override
	public Object visit(ExtendNode node, Object data) {
		data = node.childrenAccept(this, data);
		return null;
	}

	@Override
	public Object visit(IfStatementNode node, Object data) {
		data = node.childrenAccept(this, data);
		return null;
	}

	@Override
	public Object visit(WhileStatementNode node, Object data) {
		data = node.childrenAccept(this, data);
		return null;
	}

	@Override
	public Object visit(JudgeExpNode node, Object data) {
		data = node.childrenAccept(this, data);
		
		Node exp = node.jjtGetChild(0);
		if(exp instanceof NewExpNode){
			System.err.println("New object type cannot convert to boolean");
		}
		if(!(exp instanceof AndExpNode) && !(exp instanceof NotExpNode)){
			System.err.println("integer/double type cannot convert to boolean");
		}
		//应为boolean表达式
		
		
		return null;
	}

	@Override
	public Object visit(SingleStatementNode node, Object data) {
		data = node.childrenAccept(this, data);
		return null;
	}

	@Override
	public Object visit(PrintStatementNode node, Object data) {
		data = node.childrenAccept(this, data);
		return null;
	}

	@Override
	public Object visit(AssignStatementNode node, Object data) {
		data = node.childrenAccept(this, data);
		return null;
	}

	@Override
	public Object visit(AssignLeftNode node, Object data) {
		SimpleNode idNode = (SimpleNode) node.jjtGetChild(0);
		String id = idNode.getText();
		Type type = findIdType(id);
		node.setType(type);
		
		data = node.childrenAccept(this, data);
		return null;
	}

	@Override
	public Object visit(AssignRightNode node, Object data) {
		data = node.childrenAccept(this, data);
		return null;
	}

	@Override
	public Object visit(VarDeclNode node, Object data) {
		data = node.childrenAccept(this, data);
		return null;
	}

	@Override
	public Object visit(MethodNode node, Object data) {
		SimpleNode idNode = (SimpleNode)node.jjtGetChild(1);
		currentMethodDescriptor = currentClassDescriptor.methodSymbolTable.getDescriptor(idNode.getText());
		
		data = node.childrenAccept(this, data);
		return null;
	}

	@Override
	public Object visit(FormalListNode node, Object data) {
		data = node.childrenAccept(this, data);
		return null;
	}

	@Override
	public Object visit(FormalRestNode node, Object data) {
		data = node.childrenAccept(this, data);
		return null;
	}

	@Override
	public Object visit(ReturnStatementNode node, Object data) {
		data = node.childrenAccept(this, data);
		return null;
	}

	@Override
	public Object visit(PrimitiveTypeNode node, Object data) {
		data = node.childrenAccept(this, data);
		return null;
	}

	@Override
	public Object visit(ObjectTypeNode node, Object data) {
		data = node.childrenAccept(this, data);
		return null;
	}

	@Override
	public Object visit(ExpNode node, Object data) {
		data = node.childrenAccept(this, data);
		return null;
	}

	@Override
	public Object visit(NewExpNode node, Object data) {
		data = node.childrenAccept(this, data);
		return null;
	}

	@Override
	public Object visit(IntArrayNode node, Object data) {
		data = node.childrenAccept(this, data);
		return null;
	}

	@Override
	public Object visit(DoubleArrayNode node, Object data) {
		data = node.childrenAccept(this, data);
		return null;
	}

	@Override
	public Object visit(AndExpNode node, Object data) {
		data = node.childrenAccept(this, data);
		return null;
	}

	@Override
	public Object visit(NotExpNode node, Object data) {
		data = node.childrenAccept(this, data);
		return null;
	}

	@Override
	public Object visit(LessExpNode node, Object data) {
		data = node.childrenAccept(this, data);
		return null;
	}

	@Override
	public Object visit(AddExpNode node, Object data) {
		data = node.childrenAccept(this, data);
		return null;
	}

	@Override
	public Object visit(MinusExpNode node, Object data) {
		data = node.childrenAccept(this, data);
		return null;
	}

	@Override
	public Object visit(MultiExpNode node, Object data) {
		data = node.childrenAccept(this, data);
		return null;
	}

	@Override
	public Object visit(SignExpNode node, Object data) {
		data = node.childrenAccept(this, data);
		
		return null;
	}

	@Override
	public Object visit(LiteralConstantNode node, Object data) {
		
		data = node.childrenAccept(this, data);
		return null;
	}

	@Override
	public Object visit(IDExpNode node, Object data) {
		Node child = node.jjtGetChild(0);
		if(child instanceof ThisNode){
			
		}
		else if(child instanceof IDNode){
			IDNode childNode = (IDNode)child;
			String id = childNode.getText();
			node.setType(findIdType(id));
		}
		else if(child instanceof NewExpNode){
			
		}
		
		data = node.childrenAccept(this, data);
		return null;
	}

	@Override
	public Object visit(IndexNode node, Object data) {
		data = node.childrenAccept(this, data);
		return null;
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
		//Node parent = node.jjtGetParent();
		//if(!(parent instanceof MainClass)&&!(parent instanceof ClassDecl)
		//		&&!(parent instanceof VarDeclNode) && !(parent instanceof MethodNode)){
		//	
		//}
		
		data = node.childrenAccept(this, data);
		return data;
	}

	@Override
	public Object visit(ThisNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}
	
	private Type findIdType(String id){
		if(currentMethodDescriptor!=null){
			//first refer to local symbol table
			LocalDescriptor localDesc = currentMethodDescriptor.localSymbolTable.getDescriptor(id);
			if(localDesc!=null){
				Type type = Type.findTypeByName(localDesc.typename);
				//if(type==null){
				//}
				return type;
				
			}
			
			//then refer to param symbol table
			else{
			ParamDescriptor paramDesc = currentMethodDescriptor.parameterSymbolTable.getDescriptor(id);
			if(paramDesc!=null){
				Type type = Type.findTypeByName(paramDesc.typename);
				return type;
			}
			
			//then refer to class field symbol table
				else{
				FieldDescriptor fieldDesc = currentClassDescriptor.fieldSymbolTable.getDescriptor(id);
				if(fieldDesc!=null){
					Type type = Type.findTypeByName(fieldDesc.typename);
					return type;
				}
				else{
					System.err.println("Symbol "+id+" undefined.");
				}
			}
			}
		}
		return null;
	}

}
