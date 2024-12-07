/**
 * BST.java
 * @author Khiem Vu
 * CIS 22C Lab 12
 */
import java.util.ArrayList;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class BST<T> {
    private class Node {
        private T data;
        private Node left;
        private Node right;

        public Node(T data) {
            this.data = data;
            left = null;
            right = null;
        }
    }

    private Node root;

    /***CONSTRUCTORS***/

    /**
     * Default constructor for BST sets root to null.
     */
    public BST() {
        root = null;
    }

    /**
     * Copy constructor for BST.
     * @param bst the BST of which to make a copy.
     * @param cmp the way the tree is organized.
     */
    public BST(BST<T> bst, Comparator<T> cmp) {
        this();
        // attempt to adapt preOrder traversal
        if(bst != null) {
        	copyHelper(bst.root, cmp);
        }
    }

    /**
     * Helper method for copy constructor.
     * @param node the node containing data to copy.
     * @param cmp the way the tree is organized.
     */
    private void copyHelper(Node node, Comparator<T> cmp) {
    	if(node != null) {
    		this.insert(node.data, cmp);
    		copyHelper(node.left, cmp);
            copyHelper(node.right, cmp);
    	}
    }

    /**
     * Creates a BST of minimal height from an array of values.
     * @param array the list of values to insert.
     * @param cmp the way the tree is organized.
     * @precondition array must be sorted in ascending order.
     * @throws IllegalArgumentException when the array is unsorted.
     */
    public BST(T[] array, Comparator<T> cmp) throws IllegalArgumentException {
    	this();
    	if(array == null) {
    		root = null;
    	} else {
    		if(!isSorted(array, cmp)) {
        		throw new IllegalArgumentException("BST(T[] array) contsructor: array not sorted!");
        	} else {
        		int low = 0;
        		int high = array.length - 1;
        		int mid = low + (high - low)/2;
        		
        		// if tree is empty, root takes value of array middle element
        		if(root == null) {
    				root = new Node(array[mid]);
    			}
        		
        		// build left subtree
        		root.left = arrayHelper(low, mid - 1, array);
        		
        		// build right subtree
        		root.right = arrayHelper(mid + 1, high, array);
        	}
    	}
    }

    /**
     * Private helper method for array constructor
     * to check for a sorted array.
     * @param array the array to check.
     * @param cmp the way the tree is organized.
     * @return whether the array is sorted.
     */
    private boolean isSorted(T[] array, Comparator<T> cmp) {
    	if(array.length == 1) {
    		return true;
    	} else {
    		for(int i = 0; i < array.length - 1; i++) {
            	if(cmp.compare(array[i], array[i + 1]) > 0) {
            		return false;
            	}
            }
    	}
        return true;
    }

    /**
     * Recursive helper for the array constructor.
     * @param begin beginning array index.
     * @param end ending array index.
     * @param array array to search.
     * @return the newly created Node.
     */
    private Node arrayHelper(int begin, int end, T[] array) {
    	if(end < begin) {
    		return null;
    	}
    	
    	int mid = begin + (end - begin)/2;
    	Node newNode = new Node(array[mid]);
    	newNode.left = arrayHelper(begin, mid - 1, array);
    	newNode.right = arrayHelper(mid + 1, end, array);
    	return newNode;
    }

    /***ACCESSORS***/

    /**
     * Returns the data stored in the root.
     * @precondition !isEmpty()
     * @return the data stored in the root.
     * @throws NoSuchElementException when precondition is violated.
     */
    public T getRoot() throws NoSuchElementException {
        if(isEmpty()) {
        	throw new NoSuchElementException("getRoot: tree is empty!");
        } else {
        	return root.data;
        }
    }

    /**
     * Determines whether the tree is empty.
     * @return whether the tree is empty.
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Returns the current size of the tree (number of nodes).
     * @return the size of the tree.
     */
    public int getSize() {
    	if(root == null) {
    		return 0;
    	} else {
    		return 1 + getSize(root);
    	}
    }

    /**
     * Helper method for the getSize method.
     * @param node the current node to count.
     * @return the size of the tree.
     */
    private int getSize(Node node) {
        if(node.left == null && node.right == null) {
        	return 0;
        } else if(node.left == null && node.right != null) {
        	return 1 + getSize(node.right);
        } else if(node.left != null && node.right == null) {
        	return 1 + getSize(node.left);
        } else {
        	return 2 + getSize(node.left) + getSize(node.right);
        }
    }

    /**
     * Returns the height of tree by counting edges.
     * @return the height of the tree.
     */
    public int getHeight() {
        if(root == null) {
        	return -1;
        } else if(root.left == null && root.right == null) {
        	return 0;
        } else {
        	int height = 0;
        	if(1 + getHeight(root.left) > height) {
        		height = 1 + getHeight(root.left);
        	} 
        	if(1 + getHeight(root.right) > height) {
        		height = 1 + getHeight(root.right);
        	}
        	return height;
        }
    }

    /**
     * Helper method for getHeight method.
     * @param node the current node whose height to count.
     * @return the height of the tree.
     */
    private int getHeight(Node node) {
    	if(node.left == null && node.right == null) {
    		return 0;
    	} else if(node.left != null && node.right == null) {
    		return 1 + getHeight(node.left);
    	} else if(node.left == null && node.right != null) {
    		return 1 + getHeight(node.right);
    	} else {
    		return 2 + getHeight(node.right) + getHeight(node.left); 
    	}
    }

    /**
     * Returns the smallest value in the tree.
     * @precondition !isEmpty()
     * @return the smallest value in the tree.
     * @throws NoSuchElementException when the precondition is violated.
     */
    public T findMin() throws NoSuchElementException {
        if(isEmpty()) {
        	throw new NoSuchElementException("findMin: BST is empty!");
        } else {
        	return findMin(root);
        }
    }

    /**
     * Recursive helper method to findMin method.
     * @param node the current node to check if it is the smallest.
     * @return the smallest value in the tree.
     */
    private T findMin(Node node) {
        if(node.left != null) {
        	return findMin(node.left);
        } else {
        	return node.data;
        }
    }

    /**
     * Returns the largest value in the tree
     * @precondition !isEmpty()
     * @return the largest value in the tree.
     * @throws NoSuchElementException when the precondition is violated.
     */
    public T findMax() throws NoSuchElementException {
    	if(isEmpty()) {
        	throw new NoSuchElementException("findMax: BST is empty!");
        } else {
        	return findMax(root);
        }
    }

    /**
     * Recursive helper method to findMax method.
     * @param node the current node to check if it is the largest.
     * @return the largest value in the tree.
     */
    private T findMax(Node node) {
        if(node.right != null) {
        	return findMax(node.right);
        } else {
        	return node.data;
        }
    }

    /**
     * Searches for a specified value in the tree.
     * @param data the value to search for.
     * @param cmp the Comparator that indicates the way
     * the data in the tree was ordered.
     * @return the data stored in that Node of the tree, otherwise null.
     */
    public T search(T data, Comparator<T> cmp) {
        if(root == null) {
        	return null;
        } else {
        	return search(data, root, cmp);
        }
    }

    /**
     * Helper method for the search method.
     * @param data the data to search for.
     * @param node the current node to check.
     * @param cmp the Comparator that determines how the BST is organized.
     * @return the data stored in that Node of the tree, otherwise null.
     */
    private T search(T data, Node node, Comparator<T> cmp) {
        if(cmp.compare(data, node.data) == 0) {
        	return node.data;
        } else if(cmp.compare(data, node.data) < 0) {
        	if(node.left == null) {
        		return null;
        	} else {
        		return search(data, node.left, cmp);
        	}
        } else {
        	if(node.right == null) {
        		return null;
        	} else {
        		return search(data, node.right, cmp);
        	}
        }
    }

    /***MUTATORS***/

    /**
     * Inserts a new node in the tree.
     * @param data the data to insert.
     * @param cmp the Comparator indicating how data in the tree is ordered.
     */
    public void insert(T data, Comparator<T> cmp) {
        if(root == null) {
        	root = new Node(data);
        } else {
        	insert(data, root, cmp);
        }
    }

    /**
     * Helper method to insert.
     * Inserts a new value in the tree.
     * @param data the data to insert.
     * @param node the current node in the search for the correct insert
     *     location.
     * @param cmp the Comparator indicating how data in the tree is ordered.
     */
    private void insert(T data, Node node, Comparator<T> cmp) {
    	if(cmp.compare(data, node.data) <= 0) {  // data to insert smaller than node data
    		if(node.left == null) {
    			node.left = new Node(data);
    		} else {
    			insert(data, node.left, cmp);
    		}
    	} else {
    		if(node.right == null) {
    			node.right = new Node(data);
    		} else {
    			insert(data, node.right, cmp);
    		}
    	}
    }

    /**
     * Removes a value from the BST
     * @param data the value to remove
     * @param cmp the Comparator indicating how data in the tree is organized.
     * Note: updates nothing when the element is not in the tree.
     */
    public void remove(T data, Comparator<T> cmp) {
        root = remove(data, root, cmp);
    }

    /**
     * Helper method to the remove method.
     * @param data the data to remove.
     * @param node the current node.
     * @param cmp the Comparator indicating how data in the tree is organized.
     * @return an updated reference variable.
     */
    private Node remove(T data, Node node, Comparator<T> cmp) {
        if(node == null) {
        	return node;
        } else if(cmp.compare(data, node.data) < 0) {
        	node.left = remove(data, node.left, cmp);
        } else if(cmp.compare(data, node.data) > 0) {
        	node.right = remove(data, node.right, cmp);
        } else {
        	if(node.left == null && node.right == null) {
        		node = null;
        	} else if(node.left != null && node.right == null) {
        		node = node.left;
        	} else if(node.left == null && node.right != null) {
        		node = node.right;
        	} else {
        		T min = findMin(node.right);
        		node.data = min;
        		node.right = remove(min, node.right, cmp);
        	}
        }
        return node;
    }

    /***ADDITONAL OPERATIONS***/

    /**
     * Returns a String containing the data in pre order
     * followed by a new line.
     * @return a String of data in pre order.
     */
    public String preOrderString() {
        StringBuilder result = new StringBuilder();
        preOrderString(root, result);
        return result.toString() + "\n";
    }

    /**
     * Helper method to preOrderString method.
     * Prints the data in pre order to the console followed by a new line.
     * @param node the current Node
     * @param preOrder a StringBuilder containing the data
     */
    private void preOrderString(Node node, StringBuilder preOrder) {
        if(node == null) {
        	return;
        } else {
        	preOrder.append(node.data + " ");
        	preOrderString(node.left, preOrder);
        	preOrderString(node.right, preOrder);
        }
    }
    
    // to remove a User from BST (even if the BST has a duplicate)
    public void removeDuplicate(T itemToRemove, Comparator<T> cmp) {
    	root = removeDuplicate(itemToRemove, root, cmp);
    }
    
    private Node removeDuplicate(T itemToRemove, Node node, Comparator<T> cmp) {
        if(node == null) {
        	return node;
        } else if(cmp.compare(itemToRemove, node.data) < 0) {
        	node.left = remove(itemToRemove, node.left, cmp);
        } else if(cmp.compare(itemToRemove, node.data) > 0) {
        	node.right = remove(itemToRemove, node.right, cmp);
        } else {
        	if(node.data.equals(itemToRemove)) {
        		if(node.left == null && node.right == null) {
            		node = null;
            	} else if(node.left != null && node.right == null) {
            		node = node.left;
            	} else if(node.left == null && node.right != null) {
            		node = node.right;
            	} else {
            		T min = findMin(node.right);
            		node.data = min;
            		node.right = remove(min, node.right, cmp);
            	}
        	}
        }
        return node;
    }
    
    
    /**
     * Collect all nodes (users) that match search key
     *
     */
    public void searchAll(T data, Comparator<T> cmp, ArrayList<T> matches) {
    	searchAll(root, data, cmp, matches);
    }
    
    /**
     * Collect all nodes (users) that match search key
     *
     */
    private void searchAll(Node node, T data, Comparator<T> cmp, ArrayList<T> matches) {
    	if(node == null) {
    		return;
    	}
    	int comparisonValue = cmp.compare(data, node.data);
    	if(comparisonValue == 0) {
    		searchAll(node.left, data, cmp, matches);
    		matches.add(node.data);
    		searchAll(node.right, data, cmp, matches);
    	} else if (comparisonValue < 0) {
    		searchAll(node.left, data, cmp, matches);
    	} else {
    		searchAll(node.right, data, cmp, matches);
    	}
    }

    /**
     * Returns a String containing the data in order followed by a new line.
     * @return a String of data in order
     */
    public String inOrderString() {
        StringBuilder result = new StringBuilder();
        inOrderString(root, result);
        return result.toString() + "\n";
    }

    /**
     * Helper method to inOrderString.
     * Inserts the data in order into a String in order.
     * @param node the current Node
     * @param inOrder a String containing the data
     */
    private void inOrderString(Node node, StringBuilder inOrder) {
        if(node == null) {
        	return;
        } else {
        	inOrderString(node.left, inOrder);
        	inOrder.append(node.data + " ");
        	inOrderString(node.right, inOrder);
        }
    }
    
    /**
     * Returns the data with a new line in between each node's data
     * @return a String of data in order
     */
    public String inOrderStringPrint() {
        StringBuilder result = new StringBuilder();
        inOrderString(root, result);
        return result.toString() + "\n";
    }

    /**
     * Helper method to inOrderStringPrint
     * Inserts the data in order into a String in order.
     * @param node the current Node
     * @param inOrder a String containing the data
     */
    private void inOrderStringPrint(Node node, StringBuilder inOrder) {
        if(node == null) {
        	return;
        } else {
        	inOrderString(node.left, inOrder);
        	inOrder.append(node.data + "\n");
        	inOrderString(node.right, inOrder);
        }
    }
    
    /////Kai's edit
    /**
     * Inorder traversal to collect users for ArrayList
     */
    public void inOrderTraversal(ArrayList<T> list) {
    	inOrderTraversal(root, list);
    }
    
    /////Kai's edit
    /**
     * Inorder traversal to collect users for ArrayList
     */
    private void inOrderTraversal(Node node, ArrayList<T> list) {
    	if(node == null) {
    		return;
    	} else {
    		inOrderTraversal(node.left, list);
    		list.add(node.data);
    		inOrderTraversal(node.right, list);
    	}
    }
    
    // Khiem's edit
    /*
     * Reversed in order traversal to collect users for ArrayList (highest values to lowest values)
     * Wrapper
     */
    public void reversedInOrderTraversal(ArrayList<T> list) {
    	reversedInOrderTraversal(root, list);
    }
    
 // Khiem's edit
    /*
     * Reversed in order traversal to collect users for ArrayList (highest values to lowest values)
     * Helper
     */
    public void reversedInOrderTraversal(Node node, ArrayList<T> list) {
    	if(node == null) {
    		return;
    	} else {
    		reversedInOrderTraversal(node.right, list);
    		list.add(node.data);
    		reversedInOrderTraversal(node.left, list);
    	}
    }
    

    /**
     * Returns a String containing the data in post order.
     * @return a String of data in post order
     */
    public String postOrderString() {
        StringBuilder result = new StringBuilder();
        postOrderString(root, result);
        return result.toString() + "\n";
    }

    /**
     * Helper method to postOrderString
     * Inserts the data in post order into a String
     * @param node the current Node
     * @param postOrder a String containing the data
     */
    private void postOrderString(Node node, StringBuilder postOrder) {
        if(node == null) {
        	return;
        } else {
        	postOrderString(node.left, postOrder);
        	postOrderString(node.right, postOrder);
        	postOrder.append(node.data + " ");
        }
    }

    /**
     * Creates a String that is a height order
     * traversal of the data in the tree starting at
     * the Node with the largest height (the root)
     * down to Nodes of smallest height - with
     * Nodes of equal height added from left to right.
     * @return the level order traversal as a String
     */
    public String levelOrderString() {
        Queue<Node> que  = new Queue<>();
        StringBuilder sb = new StringBuilder();
        que.enqueue(root);
        levelOrderString(que, sb);
        return sb.toString() + "\n";
    }

    /**
     * Helper method to levelOrderString.
     * Inserts the data in level order into a String.
     * @param que the Queue in which to store the data.
     * @param heightTraverse a StringBuilder containing the data.
     */
    private void levelOrderString(Queue<Node> que, StringBuilder heightTraverse) {
        if(!que.isEmpty()) {
            Node nd = que.getFront();
            que.dequeue();
            if(nd != null) {
                que.enqueue(nd.left);
                que.enqueue(nd.right);
                heightTraverse.append(nd.data + " ");
            }
            levelOrderString(que, heightTraverse);
        }
    }
    

    /**Challenge Methods */

    /**
     * Returns the data of the Node who is the shared precursor to the two
     * Nodes containing the given data. If either data1 or data2 is a
     * duplicate value, the method will find the precursor of the duplicate
     * with greatest height.
     * @param data1 the data contained in one Node of the tree.
     * @param data2 the data contained in one Node of the tree.
     * @param cmp the Comparator indicating how data in the tree is organized.
     * @return the data stored by the shared precursor
     * @precondition data1 and data2 must exist in the BST.
     * @throws IllegalArgumentException when one or both values do not exist
     * in the BST.
     */
    public T sharedPrecursor(T data1, T data2, Comparator<T> cmp) throws IllegalArgumentException {
    	if(search(data1, cmp) == null) {
    		throw new IllegalArgumentException("sharedPrecursor: data1 not in BST!");
    	} else if(search(data2, cmp) == null) {
    		throw new IllegalArgumentException("sharedPrecursor: data2 not in BST!");
    	}
    	return sharedPrecursor(data1, data2, root, cmp);
    }

    /**
     * Private helper method to sharedPrecursor, which recursively locates
     * the shared precursor.
     * @param data1 the data contained in one Node of the tree.
     * @param data2 the data contained in one Node of the tree.
     * @param currLevel the current Node.
     * @param cmp the Comparator indicating how data in the tree is organized.
     * @return the data stored by the shared precursor.
     */
    private T sharedPrecursor(T data1, T data2, Node currLevel, Comparator<T> cmp) {
    	if(cmp.compare(data1, data2) == 0) { // duplicate case
    		if(cmp.compare(search(data1, currLevel, cmp), data1) == 0) {
    			return currLevel.data;
    		}
    	} else {
    		if((cmp.compare(data1, currLevel.data) <= 0 && cmp.compare(data2, currLevel.data) > 0) ||
    		   (cmp.compare(data2, currLevel.data) <= 0 && cmp.compare(data1, currLevel.data) > 0)) {
    			return currLevel.data;
    		} else if(cmp.compare(data1, currLevel.data) < 0 && cmp.compare(data2, currLevel.data) < 0) {
    			currLevel = currLevel.left;
    			return sharedPrecursor(data1, data2, currLevel, cmp);
    		} else if(cmp.compare(data1, currLevel.data) > 0 && cmp.compare(data2, currLevel.data) > 0 ){
    			currLevel = currLevel.right;
    			return sharedPrecursor(data1, data2, currLevel, cmp);
    		}
    	}
    	return currLevel.data;
    }
    
    /**
     * Searches through the BST for Users with the specified first and last name.
     * Creates and returns an ArrayList containing all matching Users.
     * 
     * @param firstName the first name to search for
     * @param lastName the last name to search for
     * @return ArrayList containing all Users with matching first and last names,
     *         returns empty ArrayList if no matches are found
     */
    public ArrayList<User> searchByName(String firstName, String lastName) {
        ArrayList<User> result = new ArrayList<>();
        searchByNameHelper(root, firstName, lastName, result);
        return result;
    }

    /**
     * Helper method for searchByName that recursively traverses the BST.
     * Adds any Users with matching names to the result ArrayList.
     * 
     * @param node the current node being examined
     * @param firstName the first name to search for
     * @param lastName the last name to search for
     * @param result ArrayList to store matching Users
     */
    private void searchByNameHelper(Node node, String firstName, String lastName, ArrayList<User> result) {
        if (node == null) {
            return;
        }
        User user = (User) node.data;  // Cast T to User since we know we're storing User objects
        if (user.getFirstName().equals(firstName) && user.getLastName().equals(lastName)) {
        result.add(user);
        }
        searchByNameHelper(node.left, firstName, lastName, result);
        searchByNameHelper(node.right, firstName, lastName, result);
    }
}
