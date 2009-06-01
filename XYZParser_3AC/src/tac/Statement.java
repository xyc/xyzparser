package tac;

import java.util.ArrayList;
import semantic.TypeCheckingVistor;

public class Statement {

	public static int a;
	public static final String ID = "id";
	public static final String ARRAY = "array";
	public ExpTreeNode assignRight;
	public String value;
	public String leftType = ID;
	public String arrayID;
	public ExpTreeNode arrayOff;
	
	public void translate(TacInfo tacInfo){
		String s;
		if(leftType.equals(ID)){
			s = value + " := " + assignRight.translate(tacInfo);
			System.err.println(s);
			TypeCheckingVistor.addSpace(TypeCheckingVistor.level, tacInfo);
			tacInfo.content += s + "\n";
		}
		else{
			String temp = "t" + Statement.a;
			Statement.a++;
			s = temp + " := " + arrayOff.translate(tacInfo) + " * W";
			System.err.println(s);
			TypeCheckingVistor.addSpace(TypeCheckingVistor.level, tacInfo);
			tacInfo.content += s + "\n";
			s = arrayID + "[" + temp + "]" + " := " + assignRight.translate(tacInfo);
			System.err.println(s);
			TypeCheckingVistor.addSpace(TypeCheckingVistor.level, tacInfo);
			tacInfo.content += s + "\n";
			Statement.clear();
		}
		
	}

	public static void clear(){
		Statement.a = 0;
	}
}
