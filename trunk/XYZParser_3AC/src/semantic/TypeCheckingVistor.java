package semantic;

import tac.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;

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
import ast.StatementNode;
import ast.ThisNode;
import ast.VarDeclNode;
import ast.WhileStatementNode;
import ast.XYZParserVisitor;

public class TypeCheckingVistor implements XYZParserVisitor {

	private SymbolTableConstructVisitor symbolTableConstructor = new SymbolTableConstructVisitor();
	private ProgramDescriptor programDescriptor;
	private ClassDescriptor currentClassDescriptor;
	private MethodDescriptor currentMethodDescriptor;
	
	private HashMap<String,String>duplicateMethodMessage = new HashMap<String, String>();

	private String undefinedInfo = "undefinedInfo :\n";
	private String duplicateMethodInfo = "Duplicate Method Messages:\n";
	private String mismatchInfo = "Mismatch Messages:\n";
	private TacInfo tacInfo = new TacInfo();
	
	private String curMethodName = "";

	private ArrayList<String> paraTypes = new ArrayList<String>();
	private ArrayList<String> fomalTypes = new ArrayList<String>();
	
	private int ifFlagIndex = 0;
	private int doNum = 1;
	private int whileNum = 1;
	private String[] ifFlags = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
			 "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X"};
	private int[] ifNums = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1
			, 1, 1, 1, 1, 1, 1};
	
	public static int level = 0;
	
	private boolean showFinal = false;

	public void setDuplicate() {
		String s = "";
		Iterator<Symbol> ci = programDescriptor.classSymbolTable.symbols
				.keySet().iterator();
		ClassDescriptor cd;
		MethodDescriptor md;
		FieldDescriptor fd;
		LocalDescriptor ld;
		ParamDescriptor pd;
		Iterator<Symbol> mi;
		Iterator<Symbol> pi;
		Iterator<Symbol> li;
		Iterator<Symbol> fi;

		while (ci.hasNext()) {
			cd = (ClassDescriptor) programDescriptor.classSymbolTable.symbols
					.get((Symbol) ci.next());
			s += "\n";
			s += cd.toString() + "\n";

			fi = cd.fieldSymbolTable.symbols.keySet().iterator();
			while (fi.hasNext()) {
				fd = (FieldDescriptor) cd.fieldSymbolTable.symbols
						.get((Symbol) fi.next());
				s += "\t" + fd.toString() + "\n";
			}
			mi = cd.methodSymbolTable.symbols.keySet().iterator();
			while (mi.hasNext()) {
				
				String id = mi.next().name;
				String info = getDuplicateMessage(id,cd);
				duplicateMethodInfo += info+"\t";
				
				md = (MethodDescriptor) cd.methodSymbolTable.symbols
						.get((Symbol) mi.next());

				s += "\t" + md.toString() + "\n";
				pi = md.parameterSymbolTable.symbols.keySet().iterator();
				while (pi.hasNext()) {
					pd = (ParamDescriptor) md.parameterSymbolTable.symbols
							.get((Symbol) pi.next());
					s += "\t\t" + pd.toString() + "\n";
				}
				li = md.localSymbolTable.symbols.keySet().iterator();
				while (li.hasNext()) {
					ld = (LocalDescriptor) md.localSymbolTable.symbols
							.get((Symbol) li.next());

					s += "\t\t" + ld.toString() + "\n";

				}
			}
		}
	}

	public String getDuplicateMessage(String id,ClassDescriptor currentClassDescriptor) {
		SymbolTable<MethodDescriptor> methodDescriptors = currentClassDescriptor.methodSymbolTable;
		HashMap<Symbol, MethodDescriptor> symbols = methodDescriptors
				.getSymbols();
		Iterator<Symbol> methodSymbols = symbols.keySet().iterator();
		MethodSymbol methodSymbol = null;
		ArrayList<MethodSymbol> methodWithSameName = new ArrayList<MethodSymbol>();
		while (methodSymbols.hasNext()) {
			methodSymbol = (MethodSymbol) methodSymbols.next();
			if (methodSymbol.getName().equals(id)) {
				methodWithSameName.add(methodSymbol);
			}
		}

		int count = 0;
		int size = methodWithSameName.size();
		ArrayList<MethodSymbol> methodDuplicated = new ArrayList<MethodSymbol>();
		if (size > 1) {

			for (int i = 0; i < size - 1; i++) {
				for (int j = i + 1; j < size; j++) {
					if (Handler.isDuplicate(methodWithSameName.get(i),
							methodWithSameName.get(j))) {
						count++;
						methodDuplicated.add(methodWithSameName.get(i));
						methodDuplicated.add(methodWithSameName.get(j));
					}
				}
			}
		}
		StringBuffer message = new StringBuffer("");
		if (count > 0) {

			message.append("Duplicate method: " + id + "\n");
			for (int i = 0; i < methodDuplicated.size(); i++) {
				message
						.append("\t\t" + methodDuplicated.get(i).getName()
								+ "(");
				ArrayList<String> types = methodDuplicated.get(i)
						.getParameterTypes();
				if (types.size() == 0)
					continue;
				message.append(types.get(0));
				for (int j = 1; j < types.size(); j++) {
					message.append(", " + types.get(j));
				}
				message.append(")\n");
			}
			// duplicateMethodMessage.put(id,message.toString());

		}
		return message.toString();
	}

	
	public Object visit(SimpleNode node, Object data) {
		data = node.childrenAccept(this, data);
		return null;
	}

	
	public Object visit(Root node, Object data) {
		node.jjtAccept(symbolTableConstructor, null);
		programDescriptor = symbolTableConstructor.getProgramDescriptor();

		data = node.childrenAccept(this, data);
		return null;
	}

	
	public Object visit(Program node, Object data) {
		data = node.childrenAccept(this, data);
		return null;
	}

	
	public Object visit(MainClass node, Object data) {
		IDNode n = (IDNode) node.jjtGetChild(0);
		currentClassDescriptor = programDescriptor.classSymbolTable
				.getDescriptor(Symbol.createSymbol(n.getText()));
		currentMethodDescriptor = currentClassDescriptor.methodSymbolTable
				.getDescriptor("main");
		
		tacInfo.content += "\nMain Class " + n.getText() + ": \n\n";
		tacInfo.content += "Main Method: \n\n";
		

		tacInfo.content += "Push Stack\n";

		data = node.childrenAccept(this, data);
		

		tacInfo.content += "Pop Stack\n";
		return null;
	}

	
	public Object visit(ClassDecl node, Object data) {
		IDNode n = (IDNode) node.jjtGetChild(0);
		currentClassDescriptor = programDescriptor.classSymbolTable
				.getDescriptor(Symbol.createSymbol(n.getText()));

		tacInfo.content += "\nClass " + n.getText() + ": \n\n";
		data = node.childrenAccept(this, data);
		return null;
	}

	
	public Object visit(ExtendNode node, Object data) {
		data = node.childrenAccept(this, data);
		return null;
	}

	
	public Object visit(IfStatementNode node, Object data) {
		JudgeExpNode jen = (JudgeExpNode)node.jjtGetChild(0);
		for(int i=0;i<jen.jjtGetNumChildren();i++){
			if(jen.jjtGetChild(i).toString().equals("LessExpNode")){
				LessExpNode len = (LessExpNode)jen.jjtGetChild(i);
				String judgeLeft = "";
				String judgeRight = "";
				
				if(len.jjtGetChild(0).toString().equals("ConstantElementNode")){
					judgeLeft = ((SimpleNode)len.jjtGetChild(0)).getText();
				}
				else if(len.jjtGetChild(0).toString().equals("ArrayElementNode")){
					ArrayElementNode aen = (ArrayElementNode)len.jjtGetChild(0);
					ExpNode arrayChildExpNode = (ExpNode)aen.jjtGetChild(0);
					
					ExpTreeNode arrayTreeNode = new ExpTreeNode();
					arrayTreeNode.arrayID = aen.getText();
					arrayTreeNode.arrayOff = arrayChildExpNode.translate(programDescriptor, currentClassDescriptor, currentMethodDescriptor, tacInfo);
					arrayTreeNode.value_type = ExpTreeNode.ARRAY;
					judgeLeft = arrayTreeNode.translate(tacInfo);
				}
				else if(len.jjtGetChild(0).toString().equals("IDElementNode")){
					judgeLeft = ((SimpleNode)len.jjtGetChild(0)).getText();
				}
				else if(len.jjtGetChild(0).toString().equals("ChildExpNode")){
					ExpNode childExpNode = (ExpNode)len.jjtGetChild(0).jjtGetChild(0);
					ExpTreeNode childTreeNode = childExpNode.translate(programDescriptor, currentClassDescriptor, currentMethodDescriptor, tacInfo);
					judgeLeft = childTreeNode.translate(tacInfo);
				}
				

				if(len.jjtGetChild(1).toString().equals("ConstantElementNode")){
					judgeRight = ((SimpleNode)len.jjtGetChild(1)).getText();
				}
				else if(len.jjtGetChild(1).toString().equals("ArrayElementNode")){
					ArrayElementNode aen = (ArrayElementNode)len.jjtGetChild(1);
					ExpNode arrayChildExpNode = (ExpNode)aen.jjtGetChild(0);
					
					ExpTreeNode arrayTreeNode = new ExpTreeNode();
					arrayTreeNode.arrayID = aen.getText();
					arrayTreeNode.arrayOff = arrayChildExpNode.translate(programDescriptor, currentClassDescriptor, currentMethodDescriptor, tacInfo);
					arrayTreeNode.value_type = ExpTreeNode.ARRAY;
					judgeRight = arrayTreeNode.translate(tacInfo);
				}
				else if(len.jjtGetChild(1).toString().equals("IDElementNode")){
					judgeRight = ((SimpleNode)len.jjtGetChild(1)).getText();
				}
				else if(len.jjtGetChild(1).toString().equals("ChildExpNode")){
					ExpNode childExpNode = (ExpNode)len.jjtGetChild(1).jjtGetChild(0);
					ExpTreeNode childTreeNode = childExpNode.translate(programDescriptor, currentClassDescriptor, currentMethodDescriptor, tacInfo);
					judgeRight = childTreeNode.translate(tacInfo);
				}
				
				if(showFinal){
					System.err.println("iffalse " + judgeLeft + " < " + judgeRight + " goto " + ifFlags[ifFlagIndex] + ifNums[ifFlagIndex]);
					addSpace(level, tacInfo);
					tacInfo.content += "iffalse " + judgeLeft + " < " + judgeRight + " goto " + ifFlags[ifFlagIndex] + ifNums[ifFlagIndex] + "\n";
				}
				else{
					System.err.println("iffalse judgeLeft < judgeRight goto " + ifFlags[ifFlagIndex] + ifNums[ifFlagIndex]);
					addSpace(level, tacInfo);
					tacInfo.content += "iffalse judgeLeft < judgeRight goto " + ifFlags[ifFlagIndex] + ifNums[ifFlagIndex] + "\n";
				}
				Statement.clear();
			}
			else if(jen.jjtGetChild(i).toString().equals("NotExpNode")){
				NotExpNode nen = (NotExpNode)jen.jjtGetChild(i);
				if(nen.isJudge()){
					if(showFinal){
						System.err.println("iffalse " + nen.getText() + " goto " + ifFlags[ifFlagIndex] + ifNums[ifFlagIndex]);
						addSpace(level, tacInfo);
						tacInfo.content += "iffalse " + nen.getText() + " goto " + ifFlags[ifFlagIndex] + ifNums[ifFlagIndex] + "\n";
					}
					else{
						System.err.println("iffalse judge boolean goto " + ifFlags[ifFlagIndex] + ifNums[ifFlagIndex]);
						addSpace(level, tacInfo);
						tacInfo.content += "iffalse judge boolean goto " + ifFlags[ifFlagIndex] + ifNums[ifFlagIndex] + "\n";
					}
				}
				else{
					if(showFinal){
						System.err.println("if " + nen.getText() + " goto " + ifFlags[ifFlagIndex] + ifNums[ifFlagIndex]);
						addSpace(level, tacInfo);
						tacInfo.content += "if " + nen.getText() + " goto " + ifFlags[ifFlagIndex] + ifNums[ifFlagIndex] + "\n";
					}
					else{
						System.err.println("if judge boolean goto " + ifFlags[ifFlagIndex] + ifNums[ifFlagIndex]);
						addSpace(level, tacInfo);
						tacInfo.content += "if judge boolean goto " + ifFlags[ifFlagIndex] + ifNums[ifFlagIndex] + "\n";
					}
				}
			}
		}
		
		ifFlagIndex++;
		level++;
			
		data = node.childrenAccept(this, data);
		ifFlagIndex--;
		level--;
		System.err.print(ifFlags[ifFlagIndex] + (ifNums[ifFlagIndex]+1) + ": ");
		addSpace(level, tacInfo);
		tacInfo.content += ifFlags[ifFlagIndex] + (ifNums[ifFlagIndex]+1) + ": ";
		ifNums[ifFlagIndex] += 2;
		return null;
	}

	
	public Object visit(WhileStatementNode node, Object data) {
		System.err.print("Y" + whileNum + ": ");
		addSpace(level, tacInfo);
		tacInfo.content += "Y" + whileNum + ": ";
		JudgeExpNode jen = (JudgeExpNode)node.jjtGetChild(0);
		for(int i=0;i<jen.jjtGetNumChildren();i++){
			if(jen.jjtGetChild(i).toString().equals("LessExpNode")){
				LessExpNode len = (LessExpNode)jen.jjtGetChild(i);
				String judgeLeft = "";
				String judgeRight = "";
				
				if(len.jjtGetChild(0).toString().equals("ConstantElementNode")){
					judgeLeft = ((SimpleNode)len.jjtGetChild(0)).getText();
				}
				else if(len.jjtGetChild(0).toString().equals("ArrayElementNode")){
					ArrayElementNode aen = (ArrayElementNode)len.jjtGetChild(0);
					ExpNode arrayChildExpNode = (ExpNode)aen.jjtGetChild(0);
					
					ExpTreeNode arrayTreeNode = new ExpTreeNode();
					arrayTreeNode.arrayID = aen.getText();
					arrayTreeNode.arrayOff = arrayChildExpNode.translate(programDescriptor, currentClassDescriptor, currentMethodDescriptor, tacInfo);
					arrayTreeNode.value_type = ExpTreeNode.ARRAY;
					judgeLeft = arrayTreeNode.translate(tacInfo);
				}
				else if(len.jjtGetChild(0).toString().equals("IDElementNode")){
					judgeLeft = ((SimpleNode)len.jjtGetChild(0)).getText();
				}
				else if(len.jjtGetChild(0).toString().equals("ChildExpNode")){
					ExpNode childExpNode = (ExpNode)len.jjtGetChild(0).jjtGetChild(0);
					ExpTreeNode childTreeNode = childExpNode.translate(programDescriptor, currentClassDescriptor, currentMethodDescriptor, tacInfo);
					judgeLeft = childTreeNode.translate(tacInfo);
				}
				

				if(len.jjtGetChild(1).toString().equals("ConstantElementNode")){
					judgeRight = ((SimpleNode)len.jjtGetChild(1)).getText();
				}
				else if(len.jjtGetChild(1).toString().equals("ArrayElementNode")){
					ArrayElementNode aen = (ArrayElementNode)len.jjtGetChild(1);
					ExpNode arrayChildExpNode = (ExpNode)aen.jjtGetChild(0);
					
					ExpTreeNode arrayTreeNode = new ExpTreeNode();
					arrayTreeNode.arrayID = aen.getText();
					arrayTreeNode.arrayOff = arrayChildExpNode.translate(programDescriptor, currentClassDescriptor, currentMethodDescriptor, tacInfo);
					arrayTreeNode.value_type = ExpTreeNode.ARRAY;
					judgeRight = arrayTreeNode.translate(tacInfo);
				}
				else if(len.jjtGetChild(1).toString().equals("IDElementNode")){
					judgeRight = ((SimpleNode)len.jjtGetChild(1)).getText();
				}
				else if(len.jjtGetChild(1).toString().equals("ChildExpNode")){
					ExpNode childExpNode = (ExpNode)len.jjtGetChild(1).jjtGetChild(0);
					ExpTreeNode childTreeNode = childExpNode.translate(programDescriptor, currentClassDescriptor, currentMethodDescriptor, tacInfo);
					judgeRight = childTreeNode.translate(tacInfo);
				}
				if(showFinal){
					System.err.println("iffalse " + judgeLeft + " < " + judgeRight + " goto Y" + (whileNum+1));
					addSpace(level, tacInfo);
					tacInfo.content += "iffalse " + judgeLeft + " < " + judgeRight + " goto Y" + (whileNum+1) + "\n";
				}
				else{
					System.err.println("iffalse judgeLeft < judgeRight goto Y" + (whileNum+1));
					addSpace(level, tacInfo);
					tacInfo.content += "iffalse judgeLeft < judgeRight goto Y" + (whileNum+1) + "\n";
				}

				Statement.clear();
			}
			else if(jen.jjtGetChild(i).toString().equals("NotExpNode")){
				NotExpNode nen = (NotExpNode)jen.jjtGetChild(i);
				if(nen.isJudge()){
					if(showFinal){
						System.err.println("iffalse " + nen.getText() + " goto Y" + (whileNum+1));
						addSpace(level, tacInfo);
						tacInfo.content += "iffalse " + nen.getText() + " goto Y" + (whileNum+1) + "\n";
					}
					else{
						System.err.println("iffalse judge boolean goto Y" + (whileNum+1));
						addSpace(level, tacInfo);
						tacInfo.content += "iffalse judge boolean goto Y" + (whileNum+1) + "\n";
					}				
				}
				else{
					if(showFinal){
						System.err.println("if " + nen.getText() + " goto Y" + (whileNum+1));
						addSpace(level, tacInfo);
						tacInfo.content += "if " + nen.getText() + " goto Y" + (whileNum+1) + "\n";
					}
					else{
						System.err.println("if judgeLeft < judgeRight goto Y" + (whileNum+1));
						addSpace(level, tacInfo);
						tacInfo.content += "if judgeLeft < judgeRight goto Y" + (whileNum+1) + "\n";
					}
				}
			}
		}
		
		whileNum += 2;
		level++;
		
		data = node.childrenAccept(this, data);
		whileNum -= 2;
		level--;
		System.err.println("goto Y" + (whileNum));
		addSpace(level, tacInfo);
		tacInfo.content += "goto Y" + (whileNum) + "\n";
		System.err.print("Y" + (whileNum+1) + ": ");
		addSpace(level, tacInfo);
		tacInfo.content += "Y" + (whileNum+1) + ": ";
		return null;
	}

	
	public Object visit(JudgeExpNode node, Object data) {
		data = node.childrenAccept(this, data);

		return null;
	}

	
	public Object visit(SingleStatementNode node, Object data) {
		data = node.childrenAccept(this, data);
		return null;
	}

	
	public Object visit(PrintStatementNode node, Object data) {
		data = node.childrenAccept(this, data);
		return null;
	}

	
	public Object visit(AssignStatementNode node, Object data) {
		SimpleNode assignLeft = (SimpleNode)node.jjtGetChild(0);
		SimpleNode assignRight = (SimpleNode)node.jjtGetChild(1);
		
		if(assignRight.jjtGetChild(0).toString().equals("ExpNode")){

			Statement statement = new Statement();
			if(assignLeft.jjtGetChild(0).toString().equals("IDElementNode")){
				IDElementNode ien = (IDElementNode)assignLeft.jjtGetChild(0);
				statement.value = ien.getText();
			}
			else if(assignLeft.jjtGetChild(0).toString().equals("ArrayElementNode")){
				ArrayElementNode aen = (ArrayElementNode)assignLeft.jjtGetChild(0);
				ExpNode arrayChildExpNode = (ExpNode)aen.jjtGetChild(0);
				statement.arrayOff = arrayChildExpNode.translate(programDescriptor, currentClassDescriptor, currentMethodDescriptor, tacInfo);
				statement.arrayID = aen.getText();
				statement.leftType = Statement.ARRAY;
			}
			
			ExpNode expNode = (ExpNode)assignRight.jjtGetChild(0);
			
			statement.assignRight = expNode.translate(programDescriptor, currentClassDescriptor, currentMethodDescriptor, tacInfo);
			if(showFinal){
				statement.translate(tacInfo);
			}
			else{
				addSpace(level, tacInfo);
				tacInfo.content += "one statement\n";
				System.err.println("one statement");
			}
			
			Statement.clear();
		}
		else{
		}
		
		/*SimpleNode idNode0 = (SimpleNode) node.jjtGetChild(0);
		SimpleNode idNode1 = (SimpleNode) node.jjtGetChild(1).jjtGetChild(0).jjtGetChild(0);
		
		if(findIdType(idNode0.getText())==null){
			return null;
		}
		if (findIdType(idNode0.getText()).getName().equals("int")&&
				idNode1.getType().getName().equals("double")) {
			String type = idNode1.getType().getName();
			if(type==null)
				type = "void";
			mismatchInfo += "assign left: " + findIdType(idNode0.getText()).getName()
					+ " assign right: " + type + " mismatch!\n";
		}*/

		data = node.childrenAccept(this, data);
		return null;
	}

	
	public Object visit(AssignLeftNode node, Object data) {
		SimpleNode idNode = (SimpleNode) node.jjtGetChild(0);
		String id = idNode.getText();
		Type t = findIdType(id);
		if(t==null){
			System.err.println("findIdType failed!");
			return null;
		}
		node.setType(t);

		data = node.childrenAccept(this, data);
		return null;
	}

	
	public Object visit(AssignRightNode node, Object data) {
		data = node.childrenAccept(this, data);
		return null;
	}

	
	public Object visit(VarDeclNode node, Object data) {
		data = node.childrenAccept(this, data);
		return null;
	}

	
	public Object visit(MethodNode node, Object data) {
		SimpleNode idNode = (SimpleNode)node.jjtGetChild(1);
		String id = idNode.getText();
		
		SymbolTable<MethodDescriptor> methodDescriptors = currentClassDescriptor.methodSymbolTable;
		HashMap<Symbol, MethodDescriptor>  symbols  = methodDescriptors.getSymbols();
		Iterator<Symbol> methodSymbols = symbols.keySet().iterator();
		MethodSymbol methodSymbol = null;
		ArrayList<MethodSymbol> methodWithSameName = new ArrayList<MethodSymbol>();
		while (methodSymbols.hasNext()) {
			methodSymbol = (MethodSymbol)methodSymbols.next();
			if (methodSymbol.getName().equals(id)) {
				methodWithSameName.add(methodSymbol);
			}
		}
		
		int count = 0;
		int size = methodWithSameName.size();
		ArrayList<MethodSymbol> methodDuplicated = new ArrayList<MethodSymbol>();
		if (size > 1) {
			
			for (int i = 0; i < size-1; i++) {
				for (int j = i+1; j < size; j++) {
					if (Handler.isDuplicate(methodWithSameName.get(i),methodWithSameName.get(j))) {
						count ++;
						methodDuplicated.add(methodWithSameName.get(i));
						methodDuplicated.add(methodWithSameName.get(j));
					}
				}
			}				
		}
		if (count > 0) {
			StringBuffer message = new StringBuffer("");
			message.append("Duplicate method: "+id+"\n");
			for (int i = 0; i < methodDuplicated.size(); i++) {
				message.append("\t\t"+methodDuplicated.get(i).getName()+"(");
				ArrayList<String> types = methodDuplicated.get(i).getParameterTypes();
				if (types.size() == 0)
					continue;
				message.append(types.get(0));
				for (int j = 1; j < types.size(); j++) {
					message.append(", "+types.get(j));
				}
				message.append(")\n");
			}
			String className = currentClassDescriptor.id;
			duplicateMethodMessage.put(id,"in class "+className+": "+message.toString());
			//System.out.println(message.toString());
		}
		this.curMethodName = id;
		
		tacInfo.content += "\nMethod " + id + " : \n\n";
		tacInfo.content += "Push Stack\n";
		
		data = node.childrenAccept(this, data);
		
		tacInfo.content += "Pop Stack\n";
		
		curMethodName = "";
		fomalTypes.clear();
		
		
		return data;
		//currentMethodDescriptor = currentClassDescriptor.methodSymbolTable.getDescriptor(idNode.getText());
		
		//System.out.println("Visit mehtod"+currentMethodDescriptor.id+currentMethodDescriptor.parameterTypes.get(0));
		
		/*String currentMethodName = currentMethodDescriptor.id;
		HashMap<Symbol, MethodDescriptor> methodSymbolTable = currentClassDescriptor.methodSymbolTable.getSymbols();
		Iterator<MethodDescriptor> methodIter = methodSymbolTable.values().iterator();
		ArrayList<MethodDescriptor> methodWithSameName = new ArrayList<MethodDescriptor>();
		while (methodIter.hasNext()) {
			MethodDescriptor method = methodIter.next();
			if (currentMethodName .equals(method.id)) {
				methodWithSameName.add(method);
			}
		}*/
		/*
		Iterator mi = currentClassDescriptor.methodSymbolTable.symbols.keySet().iterator();
		boolean find = false;
		MethodDescriptor md = null;
		while(mi.hasNext()){
			MethodSymbol s = (MethodSymbol)mi.next();
			if(s.name.equals(idNode.getText())){
				currentMethodDescriptor = currentClassDescriptor.methodSymbolTable.symbols.get(s);
				data = node.childrenAccept(this, data);
				return data;
			}
		}
		return null;*/
		/*SimpleNode idNode = (SimpleNode) node.jjtGetChild(1);
		currentMethodDescriptor = currentClassDescriptor.methodSymbolTable
				.getDescriptor(idNode.getText());
		data = node.childrenAccept(this, data);
		return null;*/
	}
	
	public Object visit(FormalListNode node, Object data) {
		SimpleNode typeNode = (SimpleNode)node.jjtGetChild(0);
		SimpleNode idNode = (SimpleNode)node.jjtGetChild(1);
		//add to method signature
		this.fomalTypes.add(typeNode.getText());
		
		MethodSymbol symbol = new MethodSymbol(this.curMethodName,this.fomalTypes);
		if(currentClassDescriptor.methodSymbolTable.getSymbols().get(symbol)!=null)
			currentMethodDescriptor =  currentClassDescriptor.methodSymbolTable.getSymbols().get(symbol);
		
		data = node.childrenAccept(this, data);
		return data;
	}

	
	public Object visit(FormalRestNode node, Object data) {
		SimpleNode typeNode = (SimpleNode)node.jjtGetChild(0);
		SimpleNode idNode = (SimpleNode)node.jjtGetChild(1);
		
		this.fomalTypes.add(typeNode.getText());
		
		MethodSymbol symbol = new MethodSymbol(this.curMethodName,this.fomalTypes);
		if(currentClassDescriptor.methodSymbolTable.getSymbols().get(symbol)!=null)
			currentMethodDescriptor =  currentClassDescriptor.methodSymbolTable.getSymbols().get(symbol);
		
		
		data = node.childrenAccept(this, data);
		return data;
	}

	
	public Object visit(ReturnStatementNode node, Object data) {
		data = node.childrenAccept(this, data);
		return null;
	}

	
	public Object visit(PrimitiveTypeNode node, Object data) {
		data = node.childrenAccept(this, data);
		return null;
	}

	
	public Object visit(ObjectTypeNode node, Object data) {
		data = node.childrenAccept(this, data);
		return null;
	}

	
	public Object visit(ExpNode node, Object data) {
		data = node.childrenAccept(this, data);
		return null;
	}

	
	public Object visit(NewExpNode node, Object data) {
		data = node.childrenAccept(this, data);
		return null;
	}

	
	public Object visit(IntArrayNode node, Object data) {
		data = node.childrenAccept(this, data);
		return null;
	}

	
	public Object visit(DoubleArrayNode node, Object data) {
		data = node.childrenAccept(this, data);
		return null;
	}

	
	public Object visit(AndExpNode node, Object data) {
		data = node.childrenAccept(this, data);
		return null;
	}

	
	public Object visit(NotExpNode node, Object data) {
		data = node.childrenAccept(this, data);
		return null;
	}

	
	public Object visit(LessExpNode node, Object data) {
		data = node.childrenAccept(this, data);
		return null;
	}

	
	public Object visit(AddExpNode node, Object data) {
		data = node.childrenAccept(this, data);
		return null;
	}

	
	public Object visit(MinusExpNode node, Object data) {
		data = node.childrenAccept(this, data);
		return null;
	}

	
	public Object visit(MultiExpNode node, Object data) {
		data = node.childrenAccept(this, data);
		return null;
	}

	
	public Object visit(SignExpNode node, Object data) {
		data = node.childrenAccept(this, data);

		return null;
	}

	
	public Object visit(LiteralConstantNode node, Object data) {

		data = node.childrenAccept(this, data);
		return null;
	}

	
	public Object visit(IDExpNode node, Object data) {
		Node child = node.jjtGetChild(0);
		if (child instanceof ThisNode) {

		} else if (child instanceof IDNode) {
			IDNode childNode = (IDNode)child;
			String id = childNode.getText();

			Type t = findIdType(id);
			if(t==null){
				System.err.println("findIdType failed!");
				return null;
			}
			node.setType(t);
		} else if (child instanceof NewExpNode) {

		}

		data = node.childrenAccept(this, data);
		return null;
	}

	
	public Object visit(IndexNode node, Object data) {
		data = node.childrenAccept(this, data);
		return null;
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

	private Type findIdType(String id) {
		if (currentMethodDescriptor != null) {
			// first refer to local symbol table
			LocalDescriptor localDesc = currentMethodDescriptor.localSymbolTable
					.getDescriptor(id);
			if (localDesc != null) {
				Type type = Type.findTypeByName(localDesc.typename);
				// if(type==null){
				// }
				return type;

			}

			// then refer to param symbol table
			else {
				ParamDescriptor paramDesc = currentMethodDescriptor.parameterSymbolTable
						.getDescriptor(id);
				if (paramDesc != null) {
					Type type = Type.findTypeByName(paramDesc.typename);
					return type;
				}

				// then refer to class field symbol table
				else {
					FieldDescriptor fieldDesc = currentClassDescriptor.fieldSymbolTable
							.getDescriptor(id);
					if (fieldDesc != null) {
						Type type = Type.findTypeByName(fieldDesc.typename);
						return type;
					} else {
						undefinedInfo += "Undefined symbol " + id + " in method "+
										currentMethodDescriptor.id+ " class " + 
										currentClassDescriptor.id+";\n";
						System.err.println("Undefined symbol " + id + ";\n");
					}
				}
			}
		}
		return null;
	}

	public String toString() {
		String s = "";
		Iterator<Symbol> ci = programDescriptor.classSymbolTable.symbols
				.keySet().iterator();
		ClassDescriptor cd;
		MethodDescriptor md;
		FieldDescriptor fd;
		LocalDescriptor ld;
		ParamDescriptor pd;
		Iterator<Symbol> mi;
		Iterator<Symbol> pi;
		Iterator<Symbol> li;
		Iterator<Symbol> fi;

		while (ci.hasNext()) {
			cd = (ClassDescriptor) programDescriptor.classSymbolTable.symbols
					.get((Symbol) ci.next());
			s += "\n";
			s += cd.toString() + "\n";

			fi = cd.fieldSymbolTable.symbols.keySet().iterator();
			while (fi.hasNext()) {
				fd = (FieldDescriptor) cd.fieldSymbolTable.symbols
						.get((Symbol) fi.next());
				s += "\t" + fd.toString() + "\n";
			}
			mi = cd.methodSymbolTable.symbols.keySet().iterator();
			while (mi.hasNext()) {
				md = (MethodDescriptor) cd.methodSymbolTable.symbols
						.get((Symbol) mi.next());

				s += "\t" + md.toString() + "\n";
				pi = md.parameterSymbolTable.symbols.keySet().iterator();
				while (pi.hasNext()) {
					pd = (ParamDescriptor) md.parameterSymbolTable.symbols
							.get((Symbol) pi.next());
					s += "\t\t" + pd.toString() + "\n";
				}
				li = md.localSymbolTable.symbols.keySet().iterator();
				while (li.hasNext()) {
					ld = (LocalDescriptor) md.localSymbolTable.symbols
							.get((Symbol) li.next());

					s += "\t\t" + ld.toString() + "\n";

				}
			}
		}
		return s;
	}

	public ProgramDescriptor getProgramDescriptor() {
		return programDescriptor;
	}

	public void setProgramDescriptor(ProgramDescriptor programDescriptor) {
		this.programDescriptor = programDescriptor;
	}

	public String getUndefinedInfo() {
		return undefinedInfo;
	}

	public void setUndefinedInfo(String undefinedInfo) {
		this.undefinedInfo = undefinedInfo;
	}
	
	public String getRedefinedInfo() {
		return symbolTableConstructor.getRedefinedInfo();
	}

	public String getDuplicateMethodInfo() {
		return duplicateMethodInfo;
	}

	public void setDuplicateMethodInfo(String duplicateMethodInfo) {
		this.duplicateMethodInfo = duplicateMethodInfo;
	}
	
	public String getMismatchInfo(){
		return mismatchInfo;
	}
	
	public void setMismatchInfo(String mismatchInfo){
		this.mismatchInfo = mismatchInfo;
	}

	public HashMap<String, String> getDuplicateMethodMessage() {
		return duplicateMethodMessage;
	}

	public void setDuplicateMethodMessage(
			HashMap<String, String> duplicateMethodMessage) {
		this.duplicateMethodMessage = duplicateMethodMessage;
	}

	public Object visit(CallExpNode node, Object data) {
		/*String object_class = "";
		Node object = node.jjtGetChild(0);
		if(object instanceof ThisNode){
			node.setType(new Type(currentClassDescriptor.id));
			object_class = currentClassDescriptor.id;
		}
		else if(object instanceof IDNode){
			IDNode childNode = (IDNode)object;
			String id = childNode.getText();
			Type t = findIdType(id);
			if(t==null){
				System.err.println("findIdType failed");
				return null;
			}
			node.setType(t);
			object_class = node.getType().getName();
		}
		else if(object instanceof NewExpNode){
			data = node.childrenAccept(this, data);
			return data;
		}
		IDNode call = (IDNode)node.jjtGetChild(1);
		String method_name = call.getText();
		ClassDescriptor cd =  programDescriptor.classSymbolTable.symbols.get(new Symbol(object_class));
		if(cd == null){
			mismatchInfo += "there is no class " + object_class + "\n";
			System.err.println("there is no class " + object_class);
			return null;
		}
		Iterator mi = cd.methodSymbolTable.symbols.keySet().iterator();
		boolean find = false;
		MethodDescriptor md = null;
		while(mi.hasNext()){
			MethodSymbol s = (MethodSymbol)mi.next();
			if(s.name.equals(method_name)){
				find = true;
				md = cd.methodSymbolTable.symbols.get(s);
			}
		}
		//MethodDescriptor md = cd.methodSymbolTable.symbols.get(new Symbol(method_name));
		if(find==false){
			mismatchInfo += "class " + object_class + " is no method " + method_name + "\n";
			System.err.println("class " + object_class + " is no method " + method_name);
			return null;
		}*/
		//String id = childNode.getText();
		
		data = node.childrenAccept(this, data);
		
		/*boolean match = false;
		MethodSymbol symbol = new MethodSymbol(md.id,paraTypes);
		if(cd.methodSymbolTable.getSymbols().get(symbol)!=null){
			match = true;
			paraTypes.clear();
			return data;
		}
		String paraStr = paraTypes.get(0);
		for(int i=0;i<paraTypes.size();i++){
			if(i!=0)
				paraStr += ", " + paraTypes.get(i);
			if(paraTypes.get(i).equals("int")){
				paraTypes.set(i, "double");
				if(cd.methodSymbolTable.getSymbols().get(symbol)!=null){
					match = true;
					paraTypes.clear();
					return data;
				}
			}
			else if(paraTypes.get(i).equals("int []")){
				paraTypes.set(i, "double []");
				if(cd.methodSymbolTable.getSymbols().get(symbol)!=null){
					match = true;
					paraTypes.clear();
					return data;
				}
			}
		}
		mismatchInfo += "method " + md.id + "(" + paraStr + ")" + " parameters mismatch!\n";
		System.err.println("method " + md.id + " parameters mismatch!");

		paraTypes.clear();*/
		return null;
	}

	public Object visit(MethodIDNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}

	public Object visit(ExpListNode node, Object data) {
		/*if(node.getText().equals("int") || node.getText().equals("double") || node.getText().equals("boolean"))
			paraTypes.add(node.getText());
		else{
			Type t = findIdType(node.getText());
			if(t==null){
				System.err.println("findIdType failed!");
				return null;
			}
			paraTypes.add(t.getName());
			
		}*/
		data = node.childrenAccept(this, data);
		return data;
	}

	public Object visit(ExpRestNode node, Object data) {
		/*if(node.getText().equals("int") || node.getText().equals("double") || node.getText().equals("boolean"))
			paraTypes.add(node.getText());
		else{
			Type t = findIdType(node.getText());
			if(t==null){
				System.err.println("findIdType failed!");
				return null;
			}
			paraTypes.add(t.getName());
		}*/
		data = node.childrenAccept(this, data);
		return data;
	}

	public Object visit(ElseNode node, Object data) {
		System.err.println("goto " + ifFlags[ifFlagIndex-1] + (ifNums[ifFlagIndex-1]+1));
		addSpace(level, tacInfo);
		tacInfo.content += "goto " + ifFlags[ifFlagIndex-1] + (ifNums[ifFlagIndex-1]+1) + "\n";
		System.err.print(ifFlags[ifFlagIndex-1] + ifNums[ifFlagIndex-1] + ": ");
		addSpace(level, tacInfo);
		tacInfo.content += ifFlags[ifFlagIndex-1] + ifNums[ifFlagIndex-1] + ": ";
		data = node.childrenAccept(this, data);
		return null;
	}

	public Object visit(DoWhileStatement node, Object data) {
		System.err.print("Z" + doNum + ": ");
		addSpace(level, tacInfo);
		tacInfo.content += "Z" + doNum + ": ";
		doNum+=2;
		level++;
		data = node.childrenAccept(this, data);
		doNum-=2;
		level--;
		JudgeExpNode jen = (JudgeExpNode)node.jjtGetChild(1);
		for(int i=0;i<jen.jjtGetNumChildren();i++){
			if(jen.jjtGetChild(i).toString().equals("LessExpNode")){
				LessExpNode len = (LessExpNode)jen.jjtGetChild(i);
				String judgeLeft = "";
				String judgeRight = "";
				
				if(len.jjtGetChild(0).toString().equals("ConstantElementNode")){
					judgeLeft = ((SimpleNode)len.jjtGetChild(0)).getText();
				}
				else if(len.jjtGetChild(0).toString().equals("ArrayElementNode")){
					ArrayElementNode aen = (ArrayElementNode)len.jjtGetChild(0);
					ExpNode arrayChildExpNode = (ExpNode)aen.jjtGetChild(0);
					
					ExpTreeNode arrayTreeNode = new ExpTreeNode();
					arrayTreeNode.arrayID = aen.getText();
					arrayTreeNode.arrayOff = arrayChildExpNode.translate(programDescriptor, currentClassDescriptor, currentMethodDescriptor, tacInfo);
					arrayTreeNode.value_type = ExpTreeNode.ARRAY;
					judgeLeft = arrayTreeNode.translate(tacInfo);
				}
				else if(len.jjtGetChild(0).toString().equals("IDElementNode")){
					judgeLeft = ((SimpleNode)len.jjtGetChild(0)).getText();
				}
				else if(len.jjtGetChild(0).toString().equals("ChildExpNode")){
					ExpNode childExpNode = (ExpNode)len.jjtGetChild(0).jjtGetChild(0);
					ExpTreeNode childTreeNode = childExpNode.translate(programDescriptor, currentClassDescriptor, currentMethodDescriptor, tacInfo);
					judgeLeft = childTreeNode.translate(tacInfo);
				}
				

				if(len.jjtGetChild(1).toString().equals("ConstantElementNode")){
					judgeRight = ((SimpleNode)len.jjtGetChild(1)).getText();
				}
				else if(len.jjtGetChild(1).toString().equals("ArrayElementNode")){
					ArrayElementNode aen = (ArrayElementNode)len.jjtGetChild(1);
					ExpNode arrayChildExpNode = (ExpNode)aen.jjtGetChild(0);
					
					ExpTreeNode arrayTreeNode = new ExpTreeNode();
					arrayTreeNode.arrayID = aen.getText();
					arrayTreeNode.arrayOff = arrayChildExpNode.translate(programDescriptor, currentClassDescriptor, currentMethodDescriptor, tacInfo);
					arrayTreeNode.value_type = ExpTreeNode.ARRAY;
					judgeRight = arrayTreeNode.translate(tacInfo);
				}
				else if(len.jjtGetChild(1).toString().equals("IDElementNode")){
					judgeRight = ((SimpleNode)len.jjtGetChild(1)).getText();
				}
				else if(len.jjtGetChild(1).toString().equals("ChildExpNode")){
					ExpNode childExpNode = (ExpNode)len.jjtGetChild(1).jjtGetChild(0);
					ExpTreeNode childTreeNode = childExpNode.translate(programDescriptor, currentClassDescriptor, currentMethodDescriptor, tacInfo);
					judgeRight = childTreeNode.translate(tacInfo);
				}
				
				if(showFinal){
					System.err.println("iffalse " + judgeLeft + " < " + judgeRight + " goto Z" + (doNum+1));
					addSpace(level, tacInfo);
					tacInfo.content += "iffalse " + judgeLeft + " < " + judgeRight + " goto Z" + (doNum+1) + "\n";
				}
				else{
					System.err.println("iffalse judgeLeft < judgeRight goto Z" + (doNum+1));
					addSpace(level, tacInfo);
					tacInfo.content += "iffalse judgeLeft < judgeRight goto Z" + (doNum+1) + "\n";
				}

				Statement.clear();
			}
			else if(jen.jjtGetChild(i).toString().equals("NotExpNode")){
				NotExpNode nen = (NotExpNode)jen.jjtGetChild(i);
				if(nen.isJudge()){
					if(showFinal){
						System.err.println("iffalse " + nen.getText() + " goto Z" + (doNum+1));
						addSpace(level, tacInfo);
						tacInfo.content += "iffalse " + nen.getText() + " goto Z" + (doNum+1) + "\n";
					}
					else{
						System.err.println("iffalse judge boolean goto Z" + (doNum+1));
						addSpace(level, tacInfo);
						tacInfo.content += "iffalse judge boolean goto Z" + (doNum+1) + "\n";
					}
				}
				else{
					if(showFinal){
						System.err.println("if " + nen.getText() + " goto Z" + (doNum+1));
						addSpace(level, tacInfo);
						tacInfo.content += "if " + nen.getText() + " goto Z" + (doNum+1) + "\n";
					}
					else{
						System.err.println("if judge boolean goto Z" + (doNum+1));
						addSpace(level, tacInfo);
						tacInfo.content += "if judge boolean goto Z" + (doNum+1) + "\n";
					}
				}
			}
		}
		System.err.println("goto Z" + (doNum));
		addSpace(level, tacInfo);
		tacInfo.content += "goto Z" + (doNum) + "\n";
		System.err.print("Z" + (doNum+1) + ": ");
		addSpace(level, tacInfo);
		tacInfo.content += "Z" + (doNum+1) + ": ";
		return null;
	}

	public Object visit(ArrayElementNode node, Object data) {
		data = node.childrenAccept(this, data);
		return null;
	}

	public Object visit(IDElementNode node, Object data) {
		data = node.childrenAccept(this, data);
		return null;
	}

	public Object visit(ConstantElementNode node, Object data) {
		data = node.childrenAccept(this, data);
		return null;
	}

	public Object visit(ChildExpNode node, Object data) {
		data = node.childrenAccept(this, data);
		return null;
	}

	public Object visit(StatementNode node, Object data) {
		data = node.childrenAccept(this, data);
		return null;
	}

	public boolean isShowFinal() {
		return showFinal;
	}

	public void setShowFinal(boolean showFinal) {
		this.showFinal = showFinal;
		ExpTreeNode.showFinal = showFinal;
	}

	public TacInfo getTacInfo() {
		return tacInfo;
	}

	public void setTacInfo(TacInfo tacInfo) {
		this.tacInfo = tacInfo;
	}
	
	public static void addSpace(int level, TacInfo tacInfo){
		for(int l=0;l<level;l++)
			tacInfo.content += " ";
	}

}
