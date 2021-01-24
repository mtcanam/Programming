package ShortestPathsNPComplete;

/*
Alternative implementation of the SSCSolver using a stack (ArrayDeque)
General process is below:

1. Create an empty stack ‘S’ and do DFS traversal of a graph.
   In DFS traversal, after calling recursive DFS for adjacent vertices of a vertex,
   push the vertex to stack. In the above graph, if we start DFS from vertex 0,
   we get vertices in stack as 1, 2, 4, 3, 0.
2. Reverse directions of all arcs to obtain the transpose graph.
3. One by one pop a vertex from S while S is not empty.
   Let the popped vertex be ‘v’. Take v as source and do DFS (call DFSUtil(v)).
   The DFS starting from v prints strongly connected component of v.
   In the above example, we process vertices in order 0, 3, 4, 2, 1 (One by one popped from stack).

Input: Graph
Output: Map of leader vertex to set of vertices (size is number of SCCs)
 */

import ObjectLibrary.Graph;
import ObjectLibrary.Vertex;

import java.util.*;

public class SCCStackSolver {

    private Graph inputGraph;
    private Deque<Vertex> vertStack;
    private BitSet visitedVertices;
    private int[] leaderVertex;

    //Takes a graph as input
    public SCCStackSolver(Graph input){
        this.inputGraph = input;
        //Initialize the stack as an ArrayDeque
        this.vertStack = new ArrayDeque<Vertex>();
        //Initialize the visited vertices bitset to be array of falses
        this.visitedVertices = new BitSet(inputGraph.getNodeCount());
        //Initialize the leader array to be of length vertex count
        this.leaderVertex = new int[inputGraph.getNodeCount()];
    }

    public void countSCC(){
        //Perform depth first search on the graph until all vertices in teh graph are visited
        //Here, length is the last true bit + 1, and nextClearBit(0) will return the first
        // false from the start of the bitset. If they are the same, all bits are true
        int s = 0;
        while (visitedVertices.length() != visitedVertices.nextClearBit(0) || visitedVertices.length() == 0){
            DFS(inputGraph, s, s);
            s++;
        }
    }

    public Deque<Vertex> getVertStack() {
        return vertStack;
    }

    //Method to perform depth first search on a graph. When the bottom of the search is achieved,
    //the vertex is added to the stack.
    private void DFS(Graph currGraph, int vertID, int sourceID){
        //Get the vertex from the graph
        Vertex currVert = inputGraph.getNode(vertID);
        //Set this vertex to visited
        visitedVertices.set(vertID);
        //Set the leader node to be the current source vertex
        leaderVertex[vertID] = sourceID;
        HashMap<Integer, Integer> connMap = currVert.getConnections();
        for (int outVert : connMap.keySet()){
            if (!visitedVertices.get(outVert)){
                DFS(currGraph, outVert, sourceID);
            }
        }
        //If we've reached the bottom of the DFS, add the vertex to the stack
        vertStack.push(currVert);
    }


}
