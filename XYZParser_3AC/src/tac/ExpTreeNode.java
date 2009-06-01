package tac;

import java.util.ArrayList;

import semantic.TypeCheckingVistor;

public class ExpTreeNode {
	public final static String END = "end";
	public final static String PLUS = "+";
	public final static String MINUS = "-";
	public final static String MULTIPLE = "*";
	public final static String SIGN = "(-)";
	
	public final static String SIMPLE = "simple";
	public final static String ARRAY = "array";
	public final static String CALL = "call";
	
	public static boolean showFinal = true;
	
	public String oper_type = END;
	public String value_type = SIMPLE;
	public ExpTreeNode left;
	public ExpTreeNode right;
	public String value;
	public String arrayID;
	public ExpTreeNode arrayOff;
	public ArrayList<String> params = new ArrayList<String>();
	public String method;
	
	public ExpTreeNode(){
		
	}
	
	public ExpTreeNode(String value){
		this.value = value;
	}
	
	public ExpTreeNode(String oper_type, ExpTreeNode left, ExpTreeNode right){
		this.oper_type = oper_type;
		this.left = left;
		this.right = right;
	}
	
	public ExpTreeNode(String oper_type, ExpTreeNode left){
		this.oper_type = oper_type;
		this.left = left;
	}
	
	public String translate(TacInfo tacInfo){
		String s;
		if(oper_type.equals(END)){
			if(value_type.equals(ARRAY)){
				String temp = "t" + Statement.a;
				Statement.a++;
				s = temp + " := " + arrayOff.translate(tacInfo) + " * W";
				if(showFinal){
					System.err.println(s);
					TypeCheckingVistor.addSpace(TypeCheckingVistor.level, tacInfo);
					tacInfo.content += s + "\n";
				}
				value = "t" + Statement.a;
				Statement.a++;
				s = value + " := " + arrayID + "[" + temp + "]";
				if(showFinal){
					System.err.println(s);
					TypeCheckingVistor.addSpace(TypeCheckingVistor.level, tacInfo);
					tacInfo.content += s + "\n";
				}
				return value;
			}
			else if(value_type.equals(CALL)){
				value = "t" + Statement.a;
				Statement.a++;
				for(int i=0;i<params.size();i++){
					s = "param " + params.get(i);
					if(showFinal){
						System.err.println(s);
						TypeCheckingVistor.addSpace(TypeCheckingVistor.level, tacInfo);
						tacInfo.content += s + "\n";
					}
				}
				s = "call " + method + ", " + params.size();
				if(showFinal){
					System.err.println(s);
					TypeCheckingVistor.addSpace(TypeCheckingVistor.level, tacInfo);
					tacInfo.content += s + "\n";
				}
				s = "return " + value;
				if(showFinal){
					System.err.println(s);
					TypeCheckingVistor.addSpace(TypeCheckingVistor.level, tacInfo);
					tacInfo.content += s + "\n";
				}
				return value;
			}
			else{
				return value;
			}
		}
		else{
			value = "t" + Statement.a;
			Statement.a++;
			if(oper_type.equals(SIGN))
				s = value + " := " + "-" + left.translate(tacInfo);
			else
				s = value + " := " + left.translate(tacInfo) + " " + oper_type + " " + right.translate(tacInfo);
			if(showFinal){
				System.err.println(s);
				TypeCheckingVistor.addSpace(TypeCheckingVistor.level, tacInfo);
				tacInfo.content += s + "\n";
			}
			return value;
		}
	}
	public static ExpTreeNode add(ExpTreeNode leftNode, ExpTreeNode rightNode, String oper_type){
		ExpTreeNode father = new ExpTreeNode();
		father.left = leftNode;
		father.right = rightNode;
		father.oper_type = oper_type;
		
		return father;
	}
}
