package semantic;

public class ClassDescriptor extends Descriptor{
	//class name
	//super class name
	//field symbol table
	//method symbol table
	//String id;
	String superClassName;
	
	public SymbolTable<FieldDescriptor> fieldSymbolTable = new SymbolTable<FieldDescriptor>(this);
	public SymbolTable<MethodDescriptor> methodSymbolTable = new SymbolTable<MethodDescriptor>(this);
	@Override
	public String toString() {
		String s = "class name: " + id;
		return s;
	}
	
	
	//new int[];
	//new id();
}
