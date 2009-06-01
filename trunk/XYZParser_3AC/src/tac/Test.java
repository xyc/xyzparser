package tac;

public class Test {
	public static void main(String[] args){

		ExpTreeNode n7 = new ExpTreeNode();
		n7.value = "1";
		ExpTreeNode n8 = new ExpTreeNode();
		n8.value = "2";
		ExpTreeNode n9 = new ExpTreeNode(ExpTreeNode.SIGN, n8);
		ExpTreeNode n4 = new ExpTreeNode(ExpTreeNode.PLUS, n7, n9);

		ExpTreeNode n5 = new ExpTreeNode();
		n5.arrayID = "a";
		n5.arrayOff = n4;
		n5.value_type = ExpTreeNode.ARRAY;
		ExpTreeNode n6 = new ExpTreeNode();
		n6.value = "4";
		ExpTreeNode n3 = new ExpTreeNode(ExpTreeNode.MULTIPLE, n5, n6);

		ExpTreeNode n2 = new ExpTreeNode(ExpTreeNode.MULTIPLE, n3, n6);

		ExpTreeNode n1 = new ExpTreeNode();
		n1.value_type = ExpTreeNode.CALL;
		n1.params.add("a");
		n1.params.add("b");
		n1.params.add("c");
		n1.params.add("d");
		n1.method = "func";

		ExpTreeNode root = new ExpTreeNode(ExpTreeNode.PLUS, n1, n2);
		
		Statement stat = new Statement();
		stat.assignRight = root;
		stat.arrayID = "a";
		stat.arrayOff = root;
		stat.leftType = Statement.ARRAY;
		TacInfo s  = new TacInfo();
		stat.translate(s);
		
	}
}
