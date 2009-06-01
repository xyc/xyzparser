package semantic;

import java.util.HashMap;
import java.util.Iterator;


public class TestHashMap {
	public static void main(String args[]) {
		HashMap<String, Integer> hm = new HashMap<String, Integer>();
		hm.put("abc",1);
		Iterator<Integer> it = hm.values().iterator();
		System.out.println(hm.size());
		while (it.hasNext()) {
			System.out.println(it.next());
		}
		
		
		hm.put("abc",2);
		
		
		Iterator<Integer> it2 = hm.values().iterator();
		System.out.println(hm.size());
		while (it2.hasNext()) {
			System.out.println(it2.next());
		}
		
		
		new TestHashMap().test();
		
		
	}
	
	public void test() {
		ClassDescriptor c = new ClassDescriptor();
		MethodDescriptor m1 = new MethodDescriptor();
		MethodDescriptor m2 = new MethodDescriptor();
		m1.id = "m1";
		m2.id = "m2";
	
		//c.methodSymbolTable.addSymbol(new Symbol("abc"),m1);
		//c.methodSymbolTable.addSymbol(new Symbol("abc"), m2);
	}

}
