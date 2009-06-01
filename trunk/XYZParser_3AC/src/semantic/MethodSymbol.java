package semantic;

import java.util.ArrayList;

public class MethodSymbol extends Symbol {
	//private String name;
	private ArrayList<String> parameterTypes;
	
	public MethodSymbol(String name, ArrayList<String> parameterTypes) {
		super();
		this.name = name;
		this.parameterTypes = parameterTypes;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<String> getParameterTypes() {
		return parameterTypes;
	}
	public void setParameterTypes(ArrayList<String> parameterTypes) {
		this.parameterTypes = parameterTypes;
	}
	
	public static Symbol createSymbol(String name,ArrayList<String>parameterTypes){
		return new MethodSymbol(name,parameterTypes);
	}
	@Override
	public int hashCode() {
		return parameterTypes.hashCode() + name.hashCode();
	}
	
	public boolean equals(Object obj) {
		if (!(obj instanceof Object))
			return false;
		MethodSymbol methodSymbol = (MethodSymbol)obj;
		if (name.equals(methodSymbol.getName()) && parameterTypes.equals(methodSymbol.getParameterTypes()))
			return true;
		return false;
	}
	
	

}
