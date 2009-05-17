package semantic;

import java.util.HashMap;

public class SymbolTable<T extends Descriptor> {
	private HashMap<Symbol, T>  symbols = new HashMap<Symbol, T>();
	//parent descriptor
	private Descriptor parentDescriptor;
	
	public SymbolTable(Descriptor desc){
		parentDescriptor = desc;
	}
	
	public void addSymbol(Symbol s, T t){
		if(symbols.containsKey(s))
			System.err.println(s+" is already defined in " + parentDescriptor.id);
		else
			//s.setDescriptor(t);
			symbols.put(s, t);
	}
	
	public T getDescriptor(String s){
		return symbols.get(Symbol.createSymbol(s));
	}
	
	public T getDescriptor(Symbol s){
		return symbols.get(s);
	}

	public Descriptor getParentDescriptor() {
		return parentDescriptor;
	}
}
