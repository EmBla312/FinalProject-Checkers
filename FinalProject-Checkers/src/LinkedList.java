
public class LinkedList<T> {

	private Node firstNode;
	private int numberOfEntries;
	
	public LinkedList()
	{
		firstNode = null;
		numberOfEntries = 0;
	}
	
	public int size() 
	{
		return numberOfEntries;
	}
	
	public boolean isEmpty() {
		
		return firstNode == null;
	}

	public boolean add(T newEntry) 
	{
		Node newNode = new Node(newEntry);
		firstNode = newNode;
		numberOfEntries++;		
		
		return true;
	}

	public T remove() 
	{
		T result = null;
		
		if(!isEmpty())
		{
			result = firstNode.getData();
			firstNode = firstNode.getNext();
			numberOfEntries--;
		}
		
		return result;
	}
	
	public boolean remove(T anEntry) 
	{
		boolean found = false;
		Node currentNode = firstNode;
		
		while(!found && currentNode != null)
		{
			if(anEntry.equals(currentNode.getData()))
			{
				found = true;
				currentNode.setData(firstNode.getData());
				remove();
			}
			
			currentNode = currentNode.getNext();
		}
		
		return found;
	}

	public void clear() 
	{
		while(!isEmpty())
			remove();
		
	}

	public int getFrequencyOf(T anEntry) 
	{
		
		int count = 0;
		Node currentNode = firstNode;
		
		while(currentNode != null)
		{
			if(anEntry.equals(currentNode.getData()))
			{
				count++;
			}
			currentNode = currentNode.getNext();
		}
		
		return count;
	}
	
	public boolean contains(T anEntry) 
	{
		
		boolean found = false;
		Node currentNode = firstNode;
		
		while(!found && currentNode != null)
		{
			if(anEntry.equals(currentNode.getData()))
			{
				found = true;
			}
			
			currentNode = currentNode.getNext();
		}
		
		return found;
	}
	
	@SuppressWarnings("unchecked")
	public PieceMove[] toArray() 
	{
		PieceMove[] result = new PieceMove[numberOfEntries];
		
		Node currNode = firstNode;
		
		for(int i = 0; i < numberOfEntries; i++) {
			if(currNode != null) {
				result[i] = (PieceMove)currNode.getData();
			}
			currNode.getNext();
		}
		return result;
	}
	
	private class Node {
		private T data;
		private Node next;
		
		private Node(T data) {
			this.data = data;
		}
		private T getData() {
			return data;
		}
		private void setData(T data) {
			this.data = data;
		}
		private Node getNext() {
			return next;
		}
		private void setNext(Node next) {
			this.next = next;
		}
		
	}
}

