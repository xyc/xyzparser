package semantic;

public class ProgramDescriptor extends Descriptor{
	public ProgramDescriptor(){
		id = "program";
	}
	
	public SymbolTable<ClassDescriptor> classSymbolTable = new SymbolTable<ClassDescriptor>(this);
	
	public void addClassDescriptor(ClassDescriptor classDesc){
		classSymbolTable.addSymbol(Symbol.createSymbol(classDesc.id), classDesc);
	}

	@Override
	public String toString() {
		// TODO �Զ����ɷ������
		return null;
	}
}
