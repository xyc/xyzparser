package semantic;

import java.util.HashMap;

public class Symbol {
	private String name;
	//private Descriptor descriptor;
	
	//private static HashMap<String, Symbol> symbolMap = new HashMap<String,Symbol>();
	
	//pre-defined symbol
	/*
	public static final Symbol INTEGER = createSymbol("int");
	public static final Symbol DOUBLE = createSymbol("double");
	public static final Symbol BOOLEAN = createSymbol("boolean");
	public static final Symbol INTEGERARRAY = createSymbol("int []");
	public static final Symbol DOUBLEARRAY = createSymbol("double []");
	public static final Symbol VOID = createSymbol("void");
	public static final Symbol TYPEERROR = createSymbol("type error");
	*/
	
	private Symbol(String n){
		name = n;
		//descriptor = desc;
	}
	
	public boolean equals(Object other){
		Symbol symbol = (Symbol) other;
		return name.equals(symbol.name);
	}
	
	public int hashCode(){
		return name.hashCode();
	}
	
	//TODO: should be SymbolTable.createSymbol && string should be unique
	public static Symbol createSymbol(String n){
		/*
		String u = n.intern();
		Symbol s = symbolMap.get(u);
		if(s==null){
			s = new Symbol(u);
			symbolMap.put(u, s);
		}
		return s;
		*/
		return new Symbol(n);
	}

	/*
	public Descriptor getDescriptor() {
		return descriptor;
	}

	public void setDescriptor(Descriptor descriptor) {
		this.descriptor = descriptor;
	}
	*/

	@Override
	public String toString(){
		return name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
