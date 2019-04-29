package game;
import java.util.LinkedList; //
import java.util.Iterator;

public class AITree {
	Node root;

	//constructs root and uses it to call makeTree
	public AITree(int depth, GameData data) {
		this.root = new Node(data);
		makeTree(root, 1, -1);
	}


	//copies board of chosen node to GameData Board in runner
	public GameData makeMove() {
		evaluateLeaves(this.root, -1); //double-check team variable
		evaluateRestOfTree(this.root, -1);

		GameData newBoard = this.root.getMaxChild();
		
		return newBoard;
		
	}

	//gets the team that the teamVariable refers to
	private int getTeam(int teamVariable) {
		if (teamVariable == -1 ) {
			return GameData.R_PAWN;
		}
		else {
			return GameData.W_PAWN;
		}

	}

	//constructs tree to depth of 5
	private void makeTree(Node node, int depth, int teamVariable) { //check this, then add evaluation of nodes when running
		if (depth < 5) {
			int team = getTeam(teamVariable);
			LinkedList<GameData> listChildren = node.getData().getFutureBoards(node.getData(), team);
			Iterator<GameData> iter = listChildren.iterator();

			while (iter.hasNext()) {
				Node newNode = new Node(iter.next());
				node.addChild(newNode);
				makeTree(newNode, depth + 1, teamVariable * -1); //alternates between teams for each recursive call
			}
		}
	}

	//finds the point weight for the tree's leaves
	private void evaluateLeaves(Node node, int teamVariable) {
		if (node.isLeaf()) {
			node.setPoint_weight(node.getData().evaluateBoard(node.getData(), getTeam(teamVariable)));
		}
		else {
			Node currNode = node.getChild();
			while (currNode != null) {
				evaluateLeaves(currNode, teamVariable*-1);
				currNode = currNode.getNext();
			}
		}
	}

	//finds the point weight for all nodes based on the leaves' point weight
	private void evaluateRestOfTree(Node node, int teamVariable) {
		if (node.getChild().isLeaf()) {
			node.setPoint_weight(node.minChildWeight()); //can assume that the leaves will always be playerMoves
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

	//finds point weight for a single board
//	public int evaluateBoard(GameData board, int teamVariable) {
//		int board_weight = 0;
//		PieceMove[] boardAI = board.getLegalMoves(getTeam(teamVariable));
//		PieceMove[] boardPlayer = board.getLegalMoves(getTeam(teamVariable*-1));
//		if(boardPlayer.length == 0) {
//			board_weight += 1000;
//		}
//		if(boardAI.length == 0) {
//			board_weight -= 1000;
//		}
//		for (int i = 0; i < boardAI.length; i++) {
//			board_weight += board.evaluateMove(boardAI, i);
//		}
//		return board_weight;
//	}

	//adds node to tree
	private void addChild(Node node, Node newNode) {
		node.addChild(newNode);
	}

	//returns tree root
	private Node getRoot() {
		return this.root;
	}

	private class Node {
		private GameData  data;
		private int point_weight;
		private Node next;
		private Node child;

		private Node(GameData data) {
			this.data = data;
			this.point_weight = 0;
			this.next = null;
			this.child = null;
		}

		private boolean isLeaf() {
			return child == null;
		}

		private GameData getData() {
			return data;
		}

		private void setData(GameData data) {
			this.data = data;
		}

		private int getPoint_weight() {
			return point_weight;
		}

		private void setPoint_weight(int point_weight) {
			this.point_weight = point_weight;
		}

		private Node getNext() {
			return next;
		}

		private void setNext(Node next) {
			this.next = next;
		}


		private Node getChild() {
			return child;
		}

		private void addChild(Node newChild) {
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

		//returns the max point weight in children
		private int maxChildWeight() {
			int max = this.child.getPoint_weight();

			Node currNode = this.child;

			while (currNode.getNext() != null) {
				if (currNode.getPoint_weight() > max) {
					max = currNode.getPoint_weight();
				}
				currNode = currNode.getNext();
			}
			return max;
		}

		//returns the min point weight in children
		private int minChildWeight() {
			int min = this.child.getPoint_weight();

			Node currNode = this.child;

			while (currNode.getNext() != null) {
				if (currNode.getPoint_weight() < min) {
					min = currNode.getPoint_weight();
				}
				currNode = currNode.getNext();
			}
			return min;
		}

		//returns the data of the node that has the max point weight
		private GameData getMaxChild() {
			int max = this.child.getPoint_weight();

			Node currNode = this.child;
			GameData bestBoard = currNode.getData();

			while (currNode.getNext() != null) {
				if (currNode.getPoint_weight() > max) {
					max = currNode.getPoint_weight();
					bestBoard = currNode.getData();
				}
				currNode = currNode.getNext();
			}
			
			return bestBoard;
		}
	}
}
