package semantic;

import java.util.HashSet;

public class Type {
	private String name;
	private static HashSet<String> typenames = new HashSet<String>();
	
	public Type(String name){
		this.name = name;
	}
	
	public static final Type INTEGER = createType("int");
	public static final Type DOUBLE = createType("double");
	public static final Type BOOLEAN = createType("boolean");
	public static final Type INTEGERARRAY = createType("int []");
	public static final Type DOUBLEARRAY = createType("double []");
	public static final Type VOID = createType("void");
	public static final Type TYPEERROR = createType("type error");
	
	public static Type createType(String name){
			if (typenames.contains(name)) {
			// error! Redefining class.
			System.err.println("Type" + name + " is already defined.");
			return null;
		} else {
			typenames.add(name);
			return new Type(name);
		}
	}

	public static Type findTypeByName(String name){
		if(typenames.contains(name)){
			return new Type(name);
		}
		else{
			return null;
		}
	}
	
	public String getName(){
		return name;
	}
}
