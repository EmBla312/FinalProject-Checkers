import java.util.LinkedList;

public class AITree {
	Node root;
	
	public AITree(int depth, GameData data) {
		this.root = new Node(data);
		makeTree(root, 1, GameData.W_PAWN);
	}
	
	private void makeTree(Node node, int depth, int team) {
		if (depth < 5) {
			LinkedList<GameData> listChildren = node.getData().getFutureBoards(node.getData(), team);
			
			for (int i = 0; i < listChildren.length(); i++) {
				node.addChild(listChildren[i]);
				makeTree(listchildren[i], depth + 1, team); //how do we alternate between teams?
			}
			
		}
	}
	
	public void addChild(Node node, GameData data) {
		node.addChild(data);
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

		public void addChild(GameData data) {
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
