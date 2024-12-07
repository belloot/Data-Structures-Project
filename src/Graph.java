/**
 * Graph.java
 * @author Khiem Vu
 * CIS 22C, Lab 17
 */
import java.util.ArrayList;

public class Graph {
    private int vertices;
    private int edges;
    private ArrayList<LinkedList<Integer>> adj;
    private ArrayList<Character> color;
    private ArrayList<Integer> distance;
    private ArrayList<Integer> parent;
    private ArrayList<Integer> discoverTime;
    private ArrayList<Integer> finishTime;
    private static int time = 0;


    /** Constructors and Destructors */

    /**
     * initializes an empty graph, with n vertices and 0 edges
     *
     * @param numVtx the number of vertices in the graph
     * @precondition numVtx > 0
     * @throws IllegalArgumentException when numVtx <= 0
     */
    public Graph(int numVtx) throws IllegalArgumentException {
    	if(numVtx <= 0) {
    		throw new IllegalArgumentException("Graph constructor: numVtx <= 0!");
    	} else {
    		adj = new ArrayList<LinkedList<Integer>>(numVtx + 1);
    		color = new ArrayList<Character>(numVtx + 1);
    		distance = new ArrayList<Integer>(numVtx + 1);
    		parent = new ArrayList<Integer>(numVtx + 1);
    		discoverTime = new ArrayList<Integer>(numVtx + 1);
    		finishTime = new ArrayList<Integer>(numVtx + 1);
    		vertices = numVtx;
    		edges = 0;
    		for(int i = 0; i < numVtx + 1; i++) {
    			adj.add(new LinkedList<Integer>());
    			color.add('W');
    			distance.add(-1);
    			parent.add(0);
    			discoverTime.add(-1);
    			finishTime.add(-1);
    		}
    	}
    }

    /*** Accessors ***/

    /**
     * Returns the number of edges in the graph
     *
     * @return the number of edges
     */
    public int getNumEdges() {
        return edges;
    }

    /**
     * Returns the number of vertices in the graph
     *
     * @return the number of vertices
     */
    public int getNumVertices() {
        return vertices;
    }

    /**
     * returns whether the graph is empty (no edges)
     *
     * @return whether the graph is empty
     */
    public boolean isEmpty() {
        return edges == 0;
    }

    /**
     * Returns the value of the distance[v]
     *
     * @param v a vertex in the graph
     * @precondition 0 < v <= vertices
     * @return the distance of vertex v
     * @throws IndexOutOfBoundsException when v is out of bounds
     */
    public Integer getDistance(Integer v) throws IndexOutOfBoundsException {
        if(!(v > 0 && v <= vertices)) {
        	throw new IndexOutOfBoundsException("getDistance: v is out of bounds!");
        } else {
        	return distance.get(v);
        }
    }

    /**
     * Returns the value of the parent[v]
     *
     * @param v a vertex in the graph
     * @precondition 0 < v <= vertices
     * @return the parent of vertex v
     * @throws IndexOutOfBoundsException when v is out of bounds
     */
    public Integer getParent(Integer v) throws IndexOutOfBoundsException {
    	if(!(v > 0 && v <= vertices)) {
        	throw new IndexOutOfBoundsException("getParent: v is out of bounds!");
        } else {
        	return parent.get(v);
        }
    }

    /**
     * Returns the value of the color[v]
     *
     * @param v a vertex in the graph
     * @precondition 0 < v <= vertices
     * @return the color of vertex v
     * @throws IndexOutOfBoundsException when v is out of bounds
     */
    public Character getColor(Integer v) throws IndexOutOfBoundsException {
    	if(!(v > 0 && v <= vertices)) {
        	throw new IndexOutOfBoundsException("getColor: v is out of bounds!");
        } else {
        	return color.get(v);
        }
    }

    /**
     * Returns the value of the discoverTime[v]
     *
     * @param v a vertex in the graph
     * @precondition 0 < v <= vertices
     * @return the discover time of vertex v
     * @throws IndexOutOfBoundsException when v is out of bounds
     */
    public Integer getDiscoverTime(Integer v) throws IndexOutOfBoundsException {
    	if(!(v > 0 && v <= vertices)) {
        	throw new IndexOutOfBoundsException("getDiscoverTime: v is out of bounds!");
        } else {
        	return discoverTime.get(v);
        }
    }

    /**
     * Returns the value of the finishTime[v]
     *
     * @param v a vertex in the graph
     * @precondition 0 < v <= vertices
     * @return the finish time of vertex v
     * @throws IndexOutOfBoundsException when v is out of bounds
     */
    public Integer getFinishTime(Integer v) throws IndexOutOfBoundsException {
    	if(!(v > 0 && v <= vertices)) {
        	throw new IndexOutOfBoundsException("getFinishTime: v is out of bounds!");
        } else {
        	return finishTime.get(v);
        }
    }

    /**
     * Returns the LinkedList stored at index v
     *
     * @param v a vertex in the graph
     * @return the adjacency LinkedList at v
     * @precondition 0 < v <= vertices
     * @throws IndexOutOfBoundsException when v is out of bounds
     */
    public LinkedList<Integer> getAdjacencyList(Integer v)
        throws IndexOutOfBoundsException {
    	if(!(v > 0 && v <= vertices)) {
        	throw new IndexOutOfBoundsException("getAdjacencyList: v is out of bounds!");
        } else {
        	return adj.get(v);
        }
    }

    /*** Manipulation Procedures ***/

    /**
     * Inserts vertex v into the adjacency list of vertex u (i.e. inserts v into
     * the list at index u) 
     * @precondition, 0 < u, u <= vertices (same for v)
     * @param u a vertex in the graph
     * @param v a vertex in the graph
     * @throws IndexOutOfBounds exception when u or v is out of bounds
     */
    public void addDirectedEdge(Integer u, Integer v)
        throws IndexOutOfBoundsException {
    	if(u <= 0 || u > vertices) {
    		throw new IndexOutOfBoundsException("addUndirectedEge: u is out of bounds");
    	} else if(v <= 0 || v > vertices) {
    		throw new IndexOutOfBoundsException("addUndirectedEge: v is out of bounds");
    	} else {
    		// insert vertex v into adjacency list of vertex u
    		adj.get(u).addLast(v);
    		edges++;
    	}
    }

    /**
     * Inserts vertex v into the adjacency list of vertex u (i.e. inserts v into
     * the list at index u) and inserts u into the adjacent vertex list of v.
     *
     * @param u a vertex in the graph
     * @param v a vertex in the graph
     * @precondition, 0 < u, u <= vertices (same for v)
     * @throws IndexOutOfBoundsException when u or v is out of bounds
     */
    public void addUndirectedEdge(Integer u, Integer v)
        throws IndexOutOfBoundsException {
    	if(u <= 0 || u > vertices) {
    		throw new IndexOutOfBoundsException("addUndirectedEge: u is out of bounds");
    	} else if(v <= 0 || v > vertices) {
    		throw new IndexOutOfBoundsException("addUndirectedEge: v is out of bounds");
    	} else {
    		// insert vertex v into adjacency list of vertex u
    		adj.get(u).addLast(v);
    		// insert vertex u into adjacency list of vertex v
    		adj.get(v).addLast(u);
    		edges++;
    	}
    }
    
    public void deleteUndirectedEdge(int u, int v)throws IndexOutOfBoundsException {
    	
    	if(u <= 0 || u > vertices) {
    		throw new IndexOutOfBoundsException("addUndirectedEge: u is out of bounds");
    	} else if(v <= 0 || v > vertices) {
    		throw new IndexOutOfBoundsException("addUndirectedEge: v is out of bounds");
    	}else {
    		// delete v from u's LinkedList
    		int indexOfV = adj.get(u).findIndex(v);
    		adj.get(u).advanceIteratorToIndex(indexOfV);
    		adj.get(u).removeIterator();
    		
    		// delete u from v's LinkedList
    		int indexofU = adj.get(v).findIndex(u);
    		adj.get(v).advanceIteratorToIndex(indexofU);
    		adj.get(v).removeIterator();
    		edges--;
    	}
    	
    }

    /*** Additional Operations ***/

    /**
     * Creates a String representation of the Graph Prints the adjacency list of
     * each vertex in the graph, vertex: <space separated list of adjacent
     * vertices>
     * @return a space separated list of adjacent vertices
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for(int i = 1; i < adj.size(); i++) {
        	result.append(i + ": " + adj.get(i).toString());
        }
        return result.toString();
    }

    /**
     * Performs breath first search on this Graph give a source vertex
     *
     * @param source the starting vertex
     * @precondition source is a vertex in the graph
     * @throws IndexOutOfBoundsException when the source vertex is out of bounds
     *     of the graph
     */
    public void BFS(Integer source) throws IndexOutOfBoundsException {
    	if(source > vertices || source <= 0) {
    		throw new IndexOutOfBoundsException("BFS: source out of bounds!");
    	} else {
    		for(int i = 1; i <= vertices; i++) {
    			color.set(i, 'W');
    			distance.set(i, -1);
    			parent.set(i, 0);
    		}
    		color.set(source, 'G');
    		distance.set(source, 0);
    		// sets up a "queue" using LinkedList
    		LinkedList<Integer> listQueue = new LinkedList<Integer>();
    		listQueue.addLast(source);
    		while(!listQueue.isEmpty()) {
    			Integer current = listQueue.getFirst();
    			listQueue.removeFirst();
    			LinkedList<Integer> adj_list = adj.get(current);
    			for(int i = 0; i < adj_list.getLength(); i++) {
    				adj_list.advanceIteratorToIndex(i);
    				int vertexAtIndex = adj_list.getIterator();
    				if(color.get(vertexAtIndex) == 'W') {
    					color.set(vertexAtIndex, 'G');
    					distance.set(vertexAtIndex, distance.get(current) + 1);
    					parent.set(vertexAtIndex, current);
    					listQueue.addLast(vertexAtIndex);
    				}
    			}
    			color.set(current, 'B');
    		}
    		
    	}
    }

    /**
     * Performs depth first search on this Graph in order of vertex lists
     */
    public void DFS() {
    	for(int i = 1; i <= vertices; i++) {
			color.set(i, 'W');
			parent.set(i, 0);
			distance.set(i, -1);
			discoverTime.set(i, -1);
			finishTime.set(i, -1);
		}
    	time = 0;
    	for(int i = 1; i <= vertices; i++) {
    		if(color.get(i).equals('W')) {
    			visit(i);
    		}
    	}
    }

    /**
     * Private recursive helper method for DFS
     *
     * @param vertex the vertex to visit
     */
    private void visit(int vertex) {
    	color.set(vertex, 'G');
    	discoverTime.set(vertex, ++time);
    	LinkedList<Integer> adj_list = adj.get(vertex);
    	for(int y = 0; y < adj_list.getLength(); y++) {
    		adj_list.advanceIteratorToIndex(y);
    		int vertexAtIndex = adj_list.getIterator();
    		if(color.get(vertexAtIndex).equals('W')) {
    			parent.set(vertexAtIndex, vertex);
    			visit(vertexAtIndex);
    		}
    	}
    	color.set(vertex, 'B');
    	finishTime.set(vertex, ++time);
    }
}
