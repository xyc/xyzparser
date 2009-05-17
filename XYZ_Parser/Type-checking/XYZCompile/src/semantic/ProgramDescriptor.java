package semantic;

public class ProgramDescriptor extends Descriptor{
	public ProgramDescriptor(){
		id = "program";
	}
	
	SymbolTable<ClassDescriptor> classSymbolTable = new SymbolTable<ClassDescriptor>(this);
	
	public void addClassDescriptor(ClassDescriptor classDesc){
		classSymbolTable.addSymbol(Symbol.createSymbol(classDesc.id), classDesc);
	}
}
