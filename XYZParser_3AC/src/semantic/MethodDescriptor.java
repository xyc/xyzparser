package semantic;

import java.util.ArrayList;

public class MethodDescriptor extends Descriptor{
	String returnType;
	//String id;
	ArrayList<String> parameterTypes = new ArrayList<String>();
	
	public SymbolTable<LocalDescriptor> localSymbolTable = new SymbolTable<LocalDescriptor>(this);
	public SymbolTable<ParamDescriptor> parameterSymbolTable = new SymbolTable<ParamDescriptor>(this);
	@Override
	public String toString() {
		String s = "method name: " + id + "  return type: " + returnType;
		return s;
	}
}
