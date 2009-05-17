package semantic;

public class ClassDescriptor extends Descriptor{
	//class name
	//super class name
	//field symbol table
	//method symbol table
	//String id;
	String superClassName;
	
	SymbolTable<FieldDescriptor> fieldSymbolTable = new SymbolTable<FieldDescriptor>(this);
	SymbolTable<MethodDescriptor> methodSymbolTable = new SymbolTable<MethodDescriptor>(this);
	
	
	//new int[];
	//new id();
}
