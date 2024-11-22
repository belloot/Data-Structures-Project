/**
 * Defines a doubly-linked list class
 * @author Khiem Vu
 * CIS 22C, Applied Lab 4 (from Applied Lab 3.1)
 */
import java.util.NoSuchElementException;

public class LinkedList<T> {
    private class Node {
        private T data;
        private Node next;
        private Node prev;

        public Node(T data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }
    }

    private int length;
    private Node first;
    private Node last;
    private Node iterator;

    /**** CONSTRUCTORS ****/

    /**
     * Instantiates a new LinkedList with default values
     * @postcondition LinkedList is set up and empty
     */
    public LinkedList() {
         first = null;
         last = null;
         iterator = null;
         length = 0;
    }

    /**
     * Converts the given array into a LinkedList
     * @param array the array of values to insert into this LinkedList
     * @postcondition a new LinkedList object, which is an identical,
     * but separate, copy of the original array
     */
    public LinkedList(T[] array) {
    	this();
    	if(array != null && array.length != 0) {
    		for(int i = 0; i < array.length; i++) {
    			addLast(array[i]);
    		}
    		iterator = null;
    	}
    }

    /**
     * Instantiates a new LinkedList by copying another List
     * @param original the LinkedList to copy
     * @postcondition a new LinkedList object, which is an identical,
     * but separate, copy of the LinkedList original
     */
    public LinkedList(LinkedList<T> original) {
    	this();
    	if(original != null && original.length != 0) {
    		Node temp = original.first;
    		while(temp != null) {
    			addLast(temp.data);
    			temp = temp.next;
    		}
    		iterator = null;
    	}
    }

    /**** ACCESSORS ****/

    /**
     * Returns the value stored in the first node
     * @precondition LinkedList has at least one node
     * @return the value stored at node first
     * @throws NoSuchElementException indicates that LinkedList is empty
     */
    public T getFirst() throws NoSuchElementException {
        if(length == 0){
           throw new NoSuchElementException("List is empty");
        }
        return first.data;
    }

    /**
     * Returns the value stored in the last node
     * @precondition LinkedList has at least one node
     * @return the value stored in the node last
     * @throws NoSuchElementException indicates that LinkedList is empty
     */
    public T getLast() throws NoSuchElementException {
        if(length == 0){
           throw new NoSuchElementException("List is empty");
        }
        return last.data;
    }

    /**
     * Returns the data stored in the iterator node
     * @precondition iterator != null
     * @return the data stored in the iterator node
     * @throw NullPointerException indicates iterator is null
     */
    public T getIterator() throws NullPointerException {
        if(iterator == null) {
    		throw new NullPointerException("getIterator: iterator is null");
    	} else {
    		return iterator.data;
    	}
    }

    /**
     * Returns the current length of the LinkedList
     * @return the length of the LinkedList from 0 to n
     */
    public int getLength() {
        return length;
    }

    /**
     * Returns whether the LinkedList is currently empty
     * @return whether the LinkedList is empty
     */
    public boolean isEmpty() {
        return length == 0;
    }

   /**
     * Returns whether the iterator is offEnd, i.e. null
     * @return whether the iterator is null
     */
    public boolean offEnd() {
        return iterator == null;
    }

    /**** MUTATORS ****/

    /**
     * Creates a new first element
     * @param data the data to insert at the front of the LinkedList
     * @postcondition LinkedList has new node in front
     */
    public void addFirst(T data) {
        Node newNode = new Node(data);
        if(length == 0){
           first = newNode;
           last = newNode;
        }  
        else{
           newNode.next = first;
           first.prev = newNode;
           first = newNode;
        }
        length++;
    }

    /**
     * Creates a new last element
     * @param data the data to insert at the end of the LinkedList
     * @postcondition LinkedList has new node at end
     */
    public void addLast(T data) {
        Node newNode = new Node(data);
        if(length == 0){
           first = newNode;
           last = newNode;
        }
        else{
           last.next = newNode;
           newNode.prev = last;
           last = newNode;
        }
        length++;
    }

    /**
     * Inserts a new element after the iterator
     * @param data the data to insert
     * @precondition iterator != null
     * @postcondition new node added after iterator 
     * @throws NullPointerException when iterator is null
     */
    public void addIterator(T data) throws NullPointerException{
    	if(iterator == null) {
    		throw new NullPointerException("addIterator: iterator is null");
    	} else if (iterator == last) {
    		addLast(data);
    	} else {
    		Node newNode = new Node(data);
         newNode.next = iterator.next;
         newNode.prev = iterator;
         iterator.next.prev = newNode;
         iterator.next = newNode;
         length++;
    	}
    }

    /**
     * removes the element at the front of the LinkedList
     * @precondition LinkedList has at least one node
     * @postcondition First node in LinkedList is removed and second node is now first
     * @throws NoSuchElementException indicates the list is empty
     */
    public void removeFirst() throws NoSuchElementException {
        if(length == 0){
           throw new NoSuchElementException("List is empty");
        }
        else if(length == 1){
           first = null;
           last = null;
           iterator = null;
        }
        else{
           first = first.next;
           first.prev = null;
        }
        length--;
    }

    /**
     * removes the element at the end of the LinkedList
     * @precondition LinkedList has at least one node
     * @postcondition Last node in LinkedList is removed and second-to-last node is now last
     * @throws NoSuchElementException indicates the list is empty
     */
    public void removeLast() throws NoSuchElementException {
        if(length == 0){
           throw new NoSuchElementException("List is empty");
        }
        else if(length == 1){
           first = null;
           last = null;
           iterator = null;
        }
        else{
           last = last.prev;
           last.next = null;
        }
        length--;
    }

    /**
     * removes the element referenced by the iterator
     * @precondition iterator != null
     * @postcondition node at iterator is removed and iterator = null
     * @throws NullPointerException when iterator is null
     */
    public void removeIterator() throws NullPointerException {
         if(iterator == null) {
    		throw new NullPointerException("removeIterator: iterator is null");
    	} else if(iterator == first) {
    		removeFirst();
    		iterator = null;
    	} else if (iterator == last) {
    		removeLast();
    		iterator = null;
    	} else {
    		iterator.prev.next = iterator.next;
        	iterator.next.prev = iterator.prev;
        	iterator = null;
        	length--;
    	}
    }

    /**
     * places the iterator at the first node
     * @postcondition iterator holds first node's reference
     */
    public void positionIterator(){
      iterator = first;  
    }

    /**
     * Moves the iterator one node towards the last
     * @precondition iterator != null
     * @postcondition iterator is moved one node towards the last (may be off end)
     * @throws NullPointerException indicates iterator is null
     */
    public void advanceIterator() throws NullPointerException {
    	if(iterator == null) {
    		throw new NullPointerException("advanceIterator: iterator is null");
    	} else {
    		iterator = iterator.next;
    	}
    }

    /**
     * Moves the iterator one node towards the first
     * @precondition iterator != null
     * @postcondition iterator is moved one node towards the first (may be off end)
     * @throws NullPointerException indicates iterator is null
     */
    public void reverseIterator() throws NullPointerException {
    	if(iterator == null) {
    		throw new NullPointerException("reverseIterator: iterator is null");
    	} else {
    		iterator = iterator.prev;
    	}
    }

    /**** ADDITIONAL OPERATIONS ****/

    /**
     * Re-sets LinkedList to empty as if the
     * default constructor had just been called
     */
    public void clear() {
    	while(length != 0) {
    		positionIterator();
    		removeIterator();
    	}
    }

    /**
     * Converts the LinkedList to a String, with each value separated by a blank
     * line At the end of the String, place a new line character
     * @return the LinkedList as a String
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        Node temp = first;
        while(temp != null){
            result.append(temp.data + " ");
            temp = temp.next;
        }
        
        return result.toString() + "\n";
    }
    
    /**
     * Returns each element in the LinkedList along with its
     * numerical position from 1 to n, followed by a newline.
     * @return the numbered LinkedList elements as a String.
     */
    public String numberedListString() {
        StringBuilder result = new StringBuilder();
        int index = 1;
        positionIterator();
        while(!offEnd()) {
        	result.append("" + index + ". " + getIterator() + "\n");
        	index++;
        	advanceIterator();
        }
        return result.toString() + "\n";
    }

    /**
     * Determines whether the given Object is
     * another LinkedList, containing
     * the same data in the same order
     * @param obj another Object
     * @return whether there is equality
     */
    @SuppressWarnings("unchecked") //good practice to remove warning here
    @Override public boolean equals(Object obj) {
        if(obj == this) {
        	return true;
        } else if (!(obj instanceof LinkedList)) {
        	return false;
        } else {
        	LinkedList<T> list = (LinkedList<T>) obj;
        	if(length != list.length) {
        		return false;
        	} else {
        		Node temp1 = this.first;
        		Node temp2 = list.first;
        		while(temp1 != null) {
        			if(temp1.data == null || temp2.data == null) {
        				if(temp1.data != temp2.data) {
        					return false;
        				}
        			} else if(!(temp1.data.equals(temp2.data))) {
        				return false;
        			}
        			temp1 = temp1.next;
        			temp2 = temp2.next;
        		}
        		return true;
        	}
        }
    }

    /**CHALLENGE METHODS*/

   /**
     * Moves all nodes in the list towards the end
     * of the list the number of times specified
     * Any node that falls off the end of the list as it
     * moves forward will be placed the front of the list
     * For example: [1, 2, 3, 4, 5], numMoves = 2 -> [4, 5, 1, 2 ,3]
     * For example: [1, 2, 3, 4, 5], numMoves = 4 -> [2, 3, 4, 5, 1]
     * For example: [1, 2, 3, 4, 5], numMoves = 7 -> [4, 5, 1, 2 ,3]
     * @param numMoves the number of times to move each node.
     * @precondition numMoves >= 0
     * @postcondition iterator position unchanged (i.e. still referencing
     * the same node in the list, regardless of new location of Node)
     * @throws IllegalArgumentException when numMoves < 0
     */
    public void spinList(int numMoves) throws IllegalArgumentException{
    	if(numMoves < 0) {
    		throw new IllegalArgumentException("spinList: numMoves is negative!");
    	} else if(length != 0){
    		for(int i = 0; i < numMoves; i++) {
    			addFirst(last.data);
    			removeLast();
    		}
    	}
    }


   /**
     * Splices together two LinkedLists to create a third List
     * which contains alternating values from this list
     * and the given parameter
     * For example: [1,2,3] and [4,5,6] -> [1,4,2,5,3,6]
     * For example: [1, 2, 3, 4] and [5, 6] -> [1, 5, 2, 6, 3, 4]
     * For example: [1, 2] and [3, 4, 5, 6] -> [1, 3, 2, 4, 5, 6]
     * @param list the second LinkedList
     * @return a new LinkedList, which is the result of
     * interlocking this and list
     * @postcondition this and list are unchanged
     */
    public LinkedList<T> altLists(LinkedList<T> list) {
    	
    	if(this == null && !(list == null)) {
    		return new LinkedList<T>(list);
    	} else if (!(this == null) && list == null) {
    		return new LinkedList<T>(this);
    	}
    	
    	LinkedList<T> newList = new LinkedList<>();
        this.positionIterator();
        list.positionIterator();
        
        while(this.iterator != null && list.iterator != null) {
        	newList.addLast(this.getIterator());
        	newList.addLast(list.getIterator());
        	this.advanceIterator();
        	list.advanceIterator();
        }
        
        if(list.iterator == null && this.iterator != null) {
        	while(this.iterator != null) {
        		newList.addLast(this.getIterator());
        		this.advanceIterator();
        	}
        	return newList;
        } else if(this.iterator == null) {
        	while(list.iterator != null) {
        		newList.addLast(list.getIterator());
        		list.advanceIterator();
        	}
        	return newList;
        }
        
        return newList;
    }
    
    /** MORE METHODS */

    /**
     * Searches the LinkedList for a given element's index.
     * @param data the data whose index to locate.
     * @return the index of the data or -1 if the data is not contained
     * in the LinkedList.
     */
    public int findIndex(T data) {
        int index = 0;
        Node currNode = first;
        while(currNode != null) {
        	if(currNode.data.equals(data)) {
        		return index;
        	}
        	currNode = currNode.next;
        	index++;
        }
        return -1;
    }

    /**
     * Advances the iterator to location within the LinkedList
     * specified by the given index.
     * @param index the index at which to place the iterator.
     * @precondition index >= 0, index < length
     * @throws IndexOutOfBoundsException when the index is out of bounds
     */
    public void advanceIteratorToIndex(int index) throws IndexOutOfBoundsException {
    	if(!(index >= 0)) {
    		throw new IndexOutOfBoundsException("advanceIteratorToIndex: index < 0!");
    	} else if(!(index < length)) {
    		throw new IndexOutOfBoundsException("advanceIteratorToIndex: index >= length!");
    	}
    	positionIterator();
    	for(int i = 0; i < index; i++) {
    		advanceIterator();
    	}
    }
}
