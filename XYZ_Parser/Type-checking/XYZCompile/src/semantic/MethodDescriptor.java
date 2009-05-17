package semantic;

import java.util.ArrayList;

public class MethodDescriptor extends Descriptor{
	String returnType;
	//String id;
	ArrayList<String> parameterTypes = new ArrayList<String>();
	
	SymbolTable<LocalDescriptor> localSymbolTable = new SymbolTable<LocalDescriptor>(this);
	SymbolTable<ParamDescriptor> parameterSymbolTable = new SymbolTable<ParamDescriptor>(this);
}
