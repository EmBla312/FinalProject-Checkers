import java.util.LinkedList; //
import java.util.Iterator;

public class AITree {
	Node root;
	
	public AITree(int depth, GameData data) {
		this.root = new Node(data);
		makeTree(root, 1, 1);
	}
	
	private int getTeam(int teamVariable) {
		if (teamVariable == -1 ) {
			return GameData.R_PAWN;
		}
		else {
			return GameData.W_PAWN;
		}
		
	}
	
	private void makeTree(Node node, int depth, int teamVariable) { //check this, then add evaluation of nodes when running
		if (depth < 5) {
			int team = getTeam(teamVariable);
			LinkedList<GameData> listChildren = node.getData().getFutureBoards(node.getData(), team);
			Iterator<GameData> iter = listChildren.iterator();
			
			while (iter.hasNext()) {
				Node newNode = new Node(iter.next());
				node.addChild(newNode);
				makeTree(newNode, depth + 1, teamVariable * -1); //how do we alternate between teams?
			}
		}
	}
	
	private void evaluateLeaves(Node node, int teamVariable) {
		if (node.isLeaf()) {
			node.setPoint_weight(evaluateBoard(node.getData(), getTeam(teamVariable)));
		}
		else {
			Node currNode = node.getChild();
			while (currNode != null) {
				evaluateLeaves(currNode, teamVariable*-1);
				currNode = currNode.getNext();
			}
		}
	}
	
	private void evaluateRestOfTree(Node node, int teamVariable) {
		if (node.getChild().isLeaf()) {
			node.setPoint_weight(node.minChildWeight()); //can assume that the leaves will always be playerMoves
			//node.setPoint_weight(node.maxChild());
		}
		else {
			Node currNode = node.getChild();
			while (currNode != null) {
				evaluateRestOfTree(currNode, teamVariable*-1);
				currNode = currNode.getNext();
			}
			if (getTeam(teamVariable) == GameData.R_PAWN) {
				node.setPoint_weight(node.minChildWeight());
			}
			else {
				node.setPoint_weight(node.maxChildWeight());
			}
		}
	}
	

	private int evaluateBoard(GameData board, int teamVariable) {
		int board_weight = 0;
		PieceMove[] boardPM = board.getLegalMoves(getTeam(teamVariable));
		
		for (int i = 0; i < boardPM.length; i++) {
			board_weight += board.evaluateMove(boardPM, i);
		}
		return board_weight;
	}
	
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
		

		public Node getChild() {
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
		
		public int maxChildWeight() {
			int max = -1001;
			
			Node currNode = this.child;
			
			while (currNode.getNext() != null) {
				if (currNode.getPoint_weight() > max) {
					max = currNode.getPoint_weight();
				}
				currNode = currNode.getNext();
			}
			return max;
		}

		public int minChildWeight() {
			int min = 1001;
			
			Node currNode = this.child;
			
			while (currNode.getNext() != null) {
				if (currNode.getPoint_weight() < min) {
					min = currNode.getPoint_weight();
				}
				currNode = currNode.getNext();
			}
			return min;
		}
	}
}
