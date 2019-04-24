import java.util.LinkedList;
import java.util.Iterator;

public class AITree {
	Node root;
	
	public AITree(int depth, GameData data) {
		this.root = new Node(data);
		makeTree(root, 1, GameData.W_PAWN);
	}
	
	private void makeTree(Node node, int depth, int team) { //check this, then add evaluation of nodes when running
		if (depth < 5) {
			LinkedList<GameData> listChildren = node.getData().getFutureBoards(node.getData(), team);
			Iterator<GameData> iter = listChildren.iterator();
			
			while (iter.hasNext()) {
				Node newNode = new Node(iter.next());
				node.addChild(newNode);
				makeTree(newNode, depth + 1, team); //how do we alternate between teams?
			}
		}
	}
	
//	private void evaluateTree(Node node) {
//		while (!node.isLeaf()) {
//			
//		}
//	}
	
	public void addChild(Node node, Node newNode) {
		node.addChild(newNode);
	}
	
	private class Node {
		private GameData  data;
		private int point_weight;
		private Node next;
		private Node child;
		
		public Node(GameData data) {
			this.data = data;
			this.point_weight = 0;
			this.next = null;
			this.child = null;
		}

		public boolean isLeaf() {
			return child == null;
		}

		public GameData getData() {
			return data;
		}

		public void setData(GameData data) {
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

		public void addChild(Node newChild) {
			//Node newChild = new Node(data);
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
