package ast;
import java.util.*;

public class ClassInfo {
	private String className;
	private boolean isMainClass;
	
	private ArrayList<MethodInfo> methodInfos;
	private ArrayList<VarInfo> varInfos;
	
	public ClassInfo(){
		methodInfos = new ArrayList<MethodInfo>();
		varInfos = new ArrayList<VarInfo>();
	}
	
	public String getClassName(){
		return this.className;
	}
	
	public void setClassName(String className){
		this.className = className;
	}
	
	public boolean getIsMainClass(){
		return this.isMainClass;
	}
	
	public void setIsMainClass(boolean isMainClass){
		this.isMainClass = isMainClass;
	}
	
	public void addVarInfo(VarInfo varInfo){
		this.varInfos.add(varInfo);
	}
	
	public ArrayList<VarInfo> getVarInfos(){
		return varInfos;
	}
	
	public void addMethodInfo(MethodInfo methodInfo){
		this.methodInfos.add(methodInfo);
	}
	
	public ArrayList<MethodInfo> getMethodInfos(){
		return this.methodInfos;
	}
}
