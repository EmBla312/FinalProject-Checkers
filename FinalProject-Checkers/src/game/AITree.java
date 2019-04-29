package game;
import java.util.LinkedList; //
import java.util.Iterator;

public class AITree {
	/**
	 * The root of the AI general tree.
	 */
	Node root;

	/**
	 * Constructs a GTNode containing a GameData object and passes it to makeTree()
	 * @param data This is the initial board to place in the root node.
	 */
	public AITree(int depth, GameData data) {
		this.root = new Node(data);
		makeTree(root, 1, -1);
	}

	/**
	 * calls evaluateLeaves and evaluateRestOfTree to assign a point weight to each node in tree, then chooses best move.
	 * @return the best board based on the point weight.
	 */
	public GameData makeMove() {
		evaluateLeaves(this.root, -1); //double-check team variable
		evaluateRestOfTree(this.root, -1);

		GameData newBoard = this.root.getMaxChild();
		
		return newBoard;
		
	}

	/**
	 * Takes the teamVariable and determines which player it represents.
	 * @param teamVariable This is either 1 or -1.
	 * @return R_PAWN or W_PAWN.
	 */
	private int getTeam(int teamVariable) {
		if (teamVariable == -1 ) {
			return GameData.R_PAWN;
		}
		else {
			return GameData.W_PAWN;
		}

	}

	/**
	 * Constructs a general tree of future board states up to 4 moves in the future.
	 * @param node
	 * @param depth This is the counter to keep track of the height of the tree through the recursive calls.
	 * @param teamVariable This alternates between 1 and -1 to make sure the correct team moves.
	 */
	private void makeTree(Node node, int depth, int teamVariable) { //check this, then add evaluation of nodes when running
		if (depth < 7) {
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

	/**
	 * Assigns a point weight to each leaf node based on the board it contains.
	 * @param node 
	 * @param teamVariable Represents the player whose turn it is.
	 */
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

	/**
	 * Assigns a point weight to each non-leaf node based on the point weight of its children.
	 * @param node
	 * @param teamVariable Represents the player whose turn it is.
	 */
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

//	//adds node to tree
//	private void addChild(Node node, Node newNode) {
//		node.addChild(newNode);
//	}

//	/**
//	 * Gets the root of the tree.
//	 * @return the tree's root.
//	 */
//	private Node getRoot() {
//		return this.root;
//	}

	/**
	 * The class for the nodes to construct the general tree.
	 *
	 */
	private class Node {
		/**
		 * Each node contains a GameData object.
		 */
		private GameData  data;
		/**
		 * Each node contains a point weight for evaluating moves.
		 */
		private int point_weight;
		/**
		 * Points to the node's next sibling.
		 */
		private Node next;
		/**
		 * Points to the first of the node's children.
		 */
		private Node child;

		/**
		 * Creates a node with given data, a point weight of zero, and next and child set to null.
		 * @param data This is the GameData object the node contains.
		 */
		private Node(GameData data) {
			this.data = data;
			this.point_weight = 0;
			this.next = null;
			this.child = null;
		}

		/**
		 * Checks whether node is leaf
		 * @return true if node has no children.
		 */
		private boolean isLeaf() {
			return child == null;
		}

		/**
		 * Gets the node's data.
		 * @return the node's data.
		 */
		private GameData getData() {
			return data;
		}
		/**
		 * Sets the node's data.
		 * @param data This is the data to set.
		 */
		private void setData(GameData data) {
			this.data = data;
		}
		/**
		 * Gets the node's point weight.
		 * @return this node's point weight.
		 */
		private int getPoint_weight() {
			return point_weight;
		}
		/**
		 * Sets the node's point weight.
		 * @param point_weight This is the int value to use as the point weight.
		 */
		private void setPoint_weight(int point_weight) {
			this.point_weight = point_weight;
		}
		
		/**
		 * Gets the node's next sibling.
		 * @return this node's next.
		 */
		private Node getNext() {
			return next;
		}
		/**
		 * Sets the node's next sibling.
		 * @param next This is the node to set as the next.
		 */
		private void setNext(Node next) {
			this.next = next;
		}
		/**
		 * Gets the node's child.
		 * @return the node's first child.
		 */
		private Node getChild() {
			return child;
		}
		/**
		 * Adds a child to the node. If the node already has children, the new child is added to the end of the list of children.
		 * @param newChild This is the child to add.
		 */
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
		/**
		 * Determines the maximum point weight in the node's children.
		 * @return the maximum point weight among the node's children.
		 */
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
		/**
		 * Determines the minimum point weight in the node's children.
		 * @return the minimum point weight among the node's children.
		 */
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

		/**
		 * Gets the child node with the maximum point weight.
		 * @return the child node with the maximum point weight.
		 */
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
