package game;

public class PriorityQueue <T> {
	private QNode firstNode;
	private int numberOfEntries;
	
	public PriorityQueue() {
		firstNode = null;
		numberOfEntries = 0;
	}
	
	public boolean add(T data, int pts) {
		QNode newQNode = new QNode(data, pts);
		boolean added = false;
		
		if(isEmpty()) {
			firstNode = newQNode;
			added = true;
		}
		else {
			QNode currNode = firstNode;
			
			while(!added && currNode != null) {
				if(currNode.getPts() < pts) {
					if(currNode == firstNode) {
						newQNode.setNext(firstNode);
						firstNode = newQNode;
						added = true;
					}
					else {
						newQNode.setNext(currNode);
						newQNode.setPrev(currNode.getPrev());
						currNode.setPrev(newQNode);
						currNode = newQNode;
						added = true;
					}
				}
				else {
					currNode = currNode.getNext();
				}
			}
		}
		if(added) {
			numberOfEntries++;
		}
		return added;
	}
	public T remove() {
		
		T data = null;
		
		if(!isEmpty()) {
			data = firstNode.getData();
			firstNode = firstNode.getNext();
			
			numberOfEntries--;
		}
		
		return data;
		
	}
	public boolean remove(T data) {
		QNode currNode = firstNode;
		boolean removed = false;
		
		if(isEmpty())
			return removed;
		else {		
			while(!removed && currNode != null) {
				if(data == currNode.getData()) {
					if(currNode == firstNode) {
						firstNode = currNode.getNext();
						removed = true;
					}
					else {
						currNode.getNext().setPrev(currNode.getPrev());
						currNode.getPrev().setNext(currNode.getNext());	
						removed = true;
					}
				}
				else {
					currNode = currNode.getNext();
				}
			}
		}
		if(removed) {
			numberOfEntries--;
		}
		return removed;
	}
	
	public void clear() {
		firstNode = null;
		numberOfEntries = 0;
	}
	public boolean isEmpty() {
		return numberOfEntries == 0;
	}
	
	public void mergePiece(int weight, T move) {
		
		
	}
	private class QNode {
		private T data;
		private int pt_weight;
		private QNode next;
		private QNode prev;
		
		
		public QNode getPrev() {
			return prev;
		}


		public void setPrev(QNode prev) {
			this.prev = prev;
		}


		public QNode(T data, int pt_weight) {
			this.data = data;
			this.pt_weight = pt_weight;
		}
	
	
		public T getData() {
			return data;
		}
	
	
		public void setMove(T data) {
			this.data = data;
		}
	
	
		public int getPts() {
			return pt_weight;
		}
	
	
		public void setPts(int pt_weight) {
			this.pt_weight = pt_weight;
		}
	
	
		public QNode getNext() {
			return next;
		}
	
	
		public void setNext(QNode next) {
			this.next = next;
		}
	}
}
