
public class AITree<T> {
	Node root;
	
	public AITree(int depth) {
		
	}
	
	public void addChild(Node node, T data) {
		node.addChild(data);
	}
	
	private class Node {
		private T  data;
		private int point_weight;
		private Node next;
		private Node child;
		
		public Node(T data) {
			this.data = data;
			this.point_weight = 0;
			this.next = null;
			this.child = null;
		}

		public T getData() {
			return data;
		}

		public void setData(T data) {
			this.data = data;
		}

		public int getPoint_weight() {
			return point_weight;
		}

		public void setPoint_weight(int point_weight) {
			this.point_weight = point_weight;
		}

		public Node getNext() {
			return next;
		}

		public void setNext(Node next) {
			this.next = next;
		}

		public Node getChildren() {
			return child;
		}

		public void addChild(T data) {
			Node newChild = new Node(data);
			if (this.child == null) {
				this.child = newChild;
			}
			else {
				Node currNode = this.child;
				
				while (currNode.getNext() != null) {
					currNode = currNode.getNext();
				}
				
				currNode.setNext(newChild);
			}
		}
		
		
	}
}
