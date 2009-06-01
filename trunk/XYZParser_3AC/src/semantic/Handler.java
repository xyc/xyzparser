package semantic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class Handler {
	
	public static void main(String args[]) {
		
	}
	
	public String getClassInfo(ProgramDescriptor programDescriptor) {
		String s = "";
		Iterator<Symbol> ci = programDescriptor.classSymbolTable.symbols
				.keySet().iterator();
		ClassDescriptor cd;
		MethodDescriptor md;
		FieldDescriptor fd;
		LocalDescriptor ld;
		ParamDescriptor pd;
		Iterator<Symbol> mi;
		Iterator<Symbol> pi;
		Iterator<Symbol> li;
		Iterator<Symbol> fi;

		while (ci.hasNext()) {
			cd = (ClassDescriptor) programDescriptor.classSymbolTable.symbols
					.get((Symbol) ci.next());
			s += "\n";
			s += cd.toString() + "\n";

			fi = cd.fieldSymbolTable.symbols.keySet().iterator();
			while (fi.hasNext()) {
				fd = (FieldDescriptor) cd.fieldSymbolTable.symbols
						.get((Symbol) fi.next());
				s += "\t" + fd.toString() + "\n";
			}
			mi = cd.methodSymbolTable.symbols.keySet().iterator();
			while (mi.hasNext()) {
				md = (MethodDescriptor) cd.methodSymbolTable.symbols
						.get((Symbol) mi.next());

				s += "\t" + md.toString() + "\n";
				pi = md.parameterSymbolTable.symbols.keySet().iterator();
				while (pi.hasNext()) {
					pd = (ParamDescriptor) md.parameterSymbolTable.symbols
							.get((Symbol) pi.next());
					s += "\t\t" + pd.toString() + "\n";
				}
				li = md.localSymbolTable.symbols.keySet().iterator();
				while (li.hasNext()) {
					ld = (LocalDescriptor) md.localSymbolTable.symbols
							.get((Symbol) li.next());

					s += "\t\t" + ld.toString() + "\n";

				}
			}
		}
		return s;
	}

	public static boolean isTypeDuplicate(String type1, String type2) {
		if (type1.equals(type2))
			return true;
		if (type1.equals("int") && type2.equals("double"))
			return true;
		if (type1.equals("double") && type2.equals("int"))
			return true;
		if (type1.equals("int []") && type2.equals("double []"))
			return true;
		if (type1.equals("double []") && type2.equals("int []"))
			return true;
		return false;
	}
	// already asume with the same name
	public static boolean isDuplicate(MethodSymbol method1, MethodSymbol method2) {
		int length1 = method1.getParameterTypes().size();
		int length2 = method2.getParameterTypes().size();
		if (length1 != length2) // 参数数目不等，方法不重复
			return false;
		ArrayList<String> method1Types = method1.getParameterTypes();
		ArrayList<String> method2Types = method2.getParameterTypes();
		for (int i = 0; i < length1; i++) {
			if (!isTypeDuplicate(method1Types.get(i),method2Types.get(i))) //有一个参数不重复，则不重复
					return false;
		}
		return true; // 所有参数都重复，则方法重复。
	}
	
	public static void printMethods(ClassDescriptor c) {
		HashMap<Symbol, MethodDescriptor> methodSymbolTable = c.methodSymbolTable.getSymbols();
		Iterator<MethodDescriptor> methodIter = methodSymbolTable.values().iterator();
		//ArrayList<MethodDescriptor> methodWithSameName = new ArrayList<MethodDescriptor>();
		System.out.println("beginClass");
		while (methodIter.hasNext()) {
			MethodDescriptor method = methodIter.next();
			System.out.print("\n methodName: "+method.id+"\n       methodLists:");
			for (int i = 0; i < method.parameterTypes.size(); i++)
			System.out.print(method.parameterTypes.get(i)+"\t");	
		}
		System.out.println("\n endClass");
	}
	
	public static void printHashMap(HashMap<String,String> hm) {
		Iterator<String> iter = hm.values().iterator();
		while (iter.hasNext()) {
			System.out.println(iter.next());
		}
	}
	
	public static String getInfo(HashMap<String,String> hm) {
		StringBuffer sb = new StringBuffer("");
		Iterator<String> iter = hm.values().iterator();
		while (iter.hasNext()) {
			sb.append(iter.next());
		}
		return sb.toString();
	}

}
