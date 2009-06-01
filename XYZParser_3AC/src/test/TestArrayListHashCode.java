package test;

import java.util.ArrayList;

public class TestArrayListHashCode {
	public static void main(String args[]) {
	ArrayList<String> a = new ArrayList<String>();
	a.add("abc");
	a.add("eee");
	ArrayList<String> b = new ArrayList<String>();
	b.add("abc");
	b.add("eee");
	
	System.out.println(a.hashCode());
	System.out.println(b.hashCode());
	
	}
}
