package semantic;

import java.util.HashMap;

public class SymbolTable<T extends Descriptor> {
	public HashMap<Symbol, T>  symbols = new HashMap<Symbol, T>();
	//parent descriptor
	private Descriptor parentDescriptor;
	
	public SymbolTable(Descriptor desc){
		parentDescriptor = desc;
	}
	
	public String addSymbol(Symbol s, T t){
		if(symbols.containsKey(s)){
			//if(!(t instanceof MethodDescriptor)){
				System.err.println(s+" is already defined in " + parentDescriptor.id);
				return s+" is already defined in " + parentDescriptor.id + "\n";
			/*}
			else{
				symbols.put(s, t);
				return "";
			}*/
		}
		else{
			//s.setDescriptor(t);
			symbols.put(s, t);
			return "";
		}
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

	public HashMap<Symbol, T> getSymbols() {
		return symbols;
	}

	public void setSymbols(HashMap<Symbol, T> symbols) {
		this.symbols = symbols;
	}
	
}
