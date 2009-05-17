package ast;
import java.util.*;

public class MethodInfo {
	private String methodName;
	private String returnType;
	private ArrayList<VarInfo> formalList;
	
	public MethodInfo(){
		formalList = new ArrayList<VarInfo>();
	}
	
	public String getMethodName(){
		return this.methodName;
	}
	
	public void setMethodName(String methodName){
		this.methodName = methodName;
	}
	
	public String getReturnType(){
		return this.returnType;
	}
	
	public void setReturnType(String returnType){
		this.returnType = returnType;
	}
	
	public void addFormalInfo(VarInfo formalInfo){
		this.formalList.add(formalInfo);
	}
	
	public ArrayList<VarInfo> getFormalList(){
		return this.getFormalList();
	}
}
