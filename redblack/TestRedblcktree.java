package redblack;

public class TestRedblcktree {
	public static void main(String args[]) {
		RedBlckTree tree = new RedBlckTree();
		tree.insertItem(8);
		tree.insertItem(3);
		tree.insertItem(5);
		tree.insertItem(2);
		tree.insertItem(1);
		tree.insertItem(9);
		tree.insertItem(10);
		tree.insertItem(7);
		tree.insertItem(6);
		tree.inorder();
		// tree.delete(10);
		// tree.inorder();
		// tree.delete(9);
		// tree.inorder();
		tree.delete(3);
		tree.inorder();
		tree.delete(2);
		tree.inorder();
		tree.delete(9);
		tree.inorder();
	}

}
