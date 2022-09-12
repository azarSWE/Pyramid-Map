
/**
 * This class represents an extended stack ADT implemented using a doubly linked list.
 * @author azararya
 * @param <T>
 */
public class DLStack<T> implements DLStackADT<T> {
	
	// instance variables
	DoubleLinkedNode<T> top;   // reference to the node at the top of the stack
	int numItems;			   // number of data items stored in the stack
	
	/**
	 * This constructor creates an empty stack
	 */
	public DLStack() {
		top = null;
		numItems = 0;
	}
	
	/**
	 * This method adds the given dataItem to the top of the stack
	 */
	public void push(T dataItem) {
		DoubleLinkedNode<T> temp = new DoubleLinkedNode<T>(dataItem);
		if (top != null) 
			top.setNext(temp);
		temp.setPrevious(top);
		top = temp;
		numItems = numItems + 1;
	}

	/**
	 * This method removes and returns the data item at the top of the stack
	 */
	public T pop() throws EmptyStackException {
		if (this.isEmpty())throw new EmptyStackException("Stack");
		T result = top.getElement();
		if (numItems == 1) {
			top = null;
			numItems --;
			} else {
				top = top.getPrevious();
				top.setNext(null);
				numItems --;
			}
		return result;
	}
	
	/**
	 * This method removes and returns the k-th data item from the top of the stack
	 */
	public T pop(int k) throws InvalidItemException {
		if (k > numItems || k <= 0) throw new InvalidItemException("k is larger than the number of data items");
		T kthItem = null;
		
		// to pop the top item in the stack
		if (k == 1) {
			kthItem = this.pop();
		}

		// to pop an item between bottom and top of the stack
		else if (1 < k && k < numItems && numItems > 1){
			DoubleLinkedNode<T> currentNode = new DoubleLinkedNode<T>();
			currentNode = top;
			int i = 1;
			while (i < k) {
				currentNode = currentNode.getPrevious();
				i++;
			}
			kthItem = currentNode.getElement();
			currentNode.getPrevious().setNext(currentNode.getNext());
			currentNode.getNext().setPrevious(currentNode.getPrevious());
			numItems --;
		}
		
		// to pop the last item in stack when there are more than one item in the stack
		else if (numItems > 1 && k == numItems) {
			DoubleLinkedNode<T> currentNode = new DoubleLinkedNode<T>();
			currentNode = top;
			int i = 1;
			while (i < k) {
				currentNode = currentNode.getPrevious();
				i++;
			}
			kthItem = currentNode.getElement();
			currentNode.getNext().setPrevious(null);
			currentNode.setNext(null);
			numItems --;
		}
		
		return kthItem;
	}
	
	/**
	 * This method returns the data item at the top of the stack without removing it.
	 */
	public T peek() throws EmptyStackException{
		if (isEmpty())throw new EmptyStackException("Stack");
		T result = getTop().getElement();
		return result;
	}

	
	/**
	 * This method returns true if the stack is empty and it returns false otherwise
	 */
	public boolean isEmpty() {
		if (numItems == 0)
			return true;
		else
			return false;
	}
	
	/**
	 * This method returns the number of data items in the stack
	 */
	public int size() {
		return numItems;
	}
	
	/**
	 * This method returns the top of the the stack
	 */
	public DoubleLinkedNode<T> getTop() {
		return top;
	}
	
	/**
	 * This method returns a string of data from the top to bottom of the stack
	 */
	public String toString() {
		DoubleLinkedNode<T> node = new DoubleLinkedNode<T>();
		node = this.getTop();
		String s = "[" + node.getElement().toString() + " ";
		for (int i = 0; i < (numItems - 2); i++) {
			node = node.getPrevious();
			s = s + node.getElement().toString() + " ";
		}
		node = node.getPrevious();
		s = s + node.getElement().toString() + "]";
		return s;
	}

}
