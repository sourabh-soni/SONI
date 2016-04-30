package redblack;

public class RedBlckTree {
	private static class Node {
		private Node left;
		private Node right;
		private Node parent;
		private int data;
		private boolean isRed;

		Node() {
			left = null;
			right = null;
			parent = null;
			isRed = false;
		}

		Node(int data, Node left, Node right, Node parent) {
			this.data = data;
			this.left = left;
			this.right = right;
			this.parent = parent;
			this.isRed = true;
		}

		public void setData(int data) {
			this.data = data;
		}

		public void setColour(char ch) {
			if (ch == 'R')
				isRed = true;
			else
				isRed = false;
		}
	}

	public Node root;
	int size = 0;

	RedBlckTree() {
		root = null;
	}

	public void insertItem(int elem) {
		Node temp;
		Node parent;
		Node node;

		size++;

		if (root == null) {
			root = new Node();
			root.setData(elem);
			return;
		}

		temp = parent = root;

		while (temp != null) {
			parent = temp;
			if (elem > temp.data)
				temp = temp.right;
			else
				temp = temp.left;
		}

		if (elem > parent.data) {
			node = new Node(elem, null, null, parent);
			parent.right = node;
		} else {
			node = new Node(elem, null, null, parent);
			parent.left = node;
		}
		doubleRedFix(parent, node);

	}

	private void doubleRedFix(Node parent, Node node) {
		while (node != root && parent != null && parent.isRed) {
			Node grand = parent.parent;
			Node uncle = null;
			if (grand.left != null && grand.left == parent)
				uncle = grand.right;
			else if (grand.right != null)
				uncle = grand.left;

			if (isRed(uncle)) {
				parent.setColour('B');
				uncle.setColour('B');
				node = parent.parent;
				parent = node.parent;
				node.setColour('R');

			}

			else {
				grand.setColour('R');
				if (parent.left == node && grand.left == parent)
					grand = rotateRight(grand);
				if (parent.right == node && grand.right == parent)
					grand = rotateLeft(grand);
				if (parent.left == node && grand.right == parent) {
					grand.right = rotateRight(grand.right);
					grand = rotateLeft(grand);

				}
				if (parent.right == node && grand.left == parent) {
					grand.left = rotateLeft(grand.left);
					grand = rotateRight(grand);
				}
				node = grand;
				node.setColour('B');
				parent = node.parent;
				break;

			}
		}

		root.setColour('B');

	}

	public boolean isRed(Node temp) {
		if (temp == null)
			return false;
		else
			return temp.isRed;
	}

	private Node rotateRight(Node x) {
		Node y = x.left;
		x.left = y.right;
		if (x.left != null)
			x.left.parent = x;
		y.parent = x.parent;
		if (x.parent == null)
			root = y;
		else if (x.parent.left == x)
			x.parent.left = y;
		else
			x.parent.right = y;
		y.right = x;

		x.parent = y;

		return y;

	}

	private Node rotateLeft(Node x) {
		Node y = x.right;
		x.right = y.left;
		if (x.right != null)
			x.right.parent = x;
		y.parent = x.parent;
		if (x.parent == null)
			root = x;
		else if (x.parent.left == x)
			x.parent.left = y;
		else
			x.parent.right = y;

		y.left = x;
		x.parent = y;
		return y;
	}

	public void delete(int elem) {
		Node temp = search(elem);

		if (temp == null) {
			System.out.println("Element Not present");
			return;
		}

		if (temp.right != null && temp.left != null) {
			Node suc = inorderSuccessor(temp);
			temp.data = suc.data;
			temp = suc;
		}
		deleteNode(temp);
	}

	public void deleteNode(Node temp) {

		Node parent = temp.parent;

		if (temp.left != null || temp.right != null) {
			Node child = null;
			if (temp.left != null) {
				child = temp.left;
			} else {
				child = temp.right;
			}
			if (parent.left == temp) {
				parent.left = child;
			} else {
				parent.right = child;
			}
			child.setColour('B');
			return;
		}

		Node sib = null;
		if (parent.left == temp) {
			sib = parent.right;
			parent.left = null;
		} else {
			sib = parent.left;
			parent.right = null;
		}

		if (isRed(parent)) {
			Node nephew;
			if (!isEmpty(sib.left))
				nephew = sib.left;
			else if (!isEmpty(sib.right))
				nephew = sib.right;
			else
				nephew = null;

			if (!isEmpty(nephew) && parent.left == sib) {
				if (nephew == sib.left) {
					nephew.setColour('B');
					parent.setColour('B');
					parent = rotateRight(parent);
					parent.setColour('R');
				} else {
					parent.setColour('B');
					parent.left = rotateLeft(parent.left);
					parent = rotateRight(parent);
					parent.setColour('R');
				}
			} else if (!isEmpty(nephew) && parent.right == sib) {
				if (nephew == sib.right) {
					nephew.setColour('B');
					parent.setColour('B');
					parent = rotateLeft(parent);
					parent.setColour('R');
				} else {
					parent.setColour('B');
					parent.right = rotateRight(parent.right);
					parent = rotateLeft(parent);
					parent.setColour('R');
				}
			} else {
				sib.setColour('R');
				parent.setColour('B');
			}
		} else {
			if (isRed(sib)) {
				if (!isEmpty(sib.right) && parent.left == sib) {
					Node nephew = sib.right;
					if (hasChild(nephew)) {
						parent.left = rotateLeft(parent.left);
						parent = rotateRight(parent);
						parent.setColour('B');
						parent.left.right.setColour('B');
					} else {
						nephew.setColour('R');
						parent = rotateRight(parent);
						parent.setColour('B');
					}
				} else if (!isEmpty(sib.left) && parent.right == sib) {
					Node nephew = sib.left;
					if (hasChild(nephew)) {
						parent.right = rotateRight(parent.right);
						parent = rotateLeft(parent);
						parent.setColour('B');
						parent.right.left.setColour('B');
					} else {
						nephew.setColour('R');
						parent = rotateLeft(parent);
						parent.setColour('B');

					}
				}

			} else {
				if (!isEmpty(sib) && hasChild(sib)) {
					if (parent.left == sib) {
						parent.right = null;
						if (!isEmpty(sib.left)) {
							parent = rotateRight(parent);
							parent.setColour('B');
						} else if (!isEmpty(sib.right)) {
							parent.left = rotateLeft(parent.left);
							parent = rotateRight(parent);
							parent.setColour('B');
						}
					} else if (!isEmpty(sib) && parent.right == sib) {
						parent.left = null;
						if (!isEmpty(sib.right)) {
							parent = rotateLeft(parent);
							parent.setColour('B');
						} else if (!isEmpty(sib.left)) {
							parent.right = rotateRight(parent.right);
							parent = rotateLeft(parent);
							parent.setColour('B');
						}
					}
				} else if (!isEmpty(sib)) {
					if (parent.left == sib) {
						parent.right = null;
						sib.setColour('R');
					} else {
						parent.left = null;
						sib.setColour('R');
					}

				}

			}
		}

	}

	public boolean hasChild(Node temp) {
		if (temp.right == null && temp.left == null)
			return false;
		else
			return true;

	}

	public boolean isEmpty(Node temp) {
		if (temp == null)
			return true;
		else
			return false;
	}

	private Node inorderSuccessor(Node temp) {
		Node parent;
		if (temp.right != null) {
			temp = temp.right;
			while (temp.left != null)
				temp = temp.left;
			return temp;
		} else {
			parent = temp.parent;
			while (parent != null) {
				if (parent.left == temp) {
					return parent;
				}
				temp = parent;
				parent = parent.parent;
			}
			return null;
		}

	}

	public Node search(int elem) {
		Node temp = root;
		while (temp != null) {
			if (temp.data == elem)
				return temp;
			else if (temp.data > elem)
				temp = temp.left;
			else
				temp = temp.right;
		}

		System.out.println("Element not found");
		return null;
	}

	public void inorder() {
		inorder(root);
		System.out.println();
	}

	public void inorder(Node temp) {
		if (temp != null) {
			inorder(temp.left);
			String ch = temp.isRed ? "R" : "B";
			// System.out.println(temp.data);
			System.out.print(temp.data + ch + " ");
			inorder(temp.right);
		}
	}
}
