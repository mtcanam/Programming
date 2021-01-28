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

import java.io.IOException;
import java.util.*;

public class SCCStackSolver {

    private ArrayList<ArrayList<Integer>> inputGraph;
    private ArrayList<ArrayList<Integer>> revGraph;
    private Deque<Integer> vertIDStack;
    private Deque<Integer> traversalStack;
    private HashSet<Integer> visitedVertices;
    private HashSet<Integer> addedVertices;
    private int[] leaderVertexArray;
    private HashSet<Integer> leaderVertex;
    private HashMap<Integer,Integer> SCCSize;

    //Takes a graph as input
    public SCCStackSolver(ArrayList<ArrayList<Integer>> input, ArrayList<ArrayList<Integer>> revInput){
        this.inputGraph = input;
        this.revGraph = revInput;
        //Initialize the stacks as an ArrayDeque
        this.vertIDStack = new ArrayDeque<Integer>();
        this.traversalStack = new ArrayDeque<Integer>();
        //Initialize the visited vertices as a hashset
        this.visitedVertices = new HashSet<>();
        this.addedVertices = new HashSet<>();
        //Initialize the leader array to be of length vertex count
        this.leaderVertex = new HashSet<>();
        this.leaderVertexArray = new int[inputGraph.size()];
        this.SCCSize = new HashMap<>();
    }

    public void countSCC(){
        System.out.println("Started forward pass.");

        //while the stack is not empty, strip off the first vertex id (ie the most recent added)
        //Check if we've already visited it. If not, add it's children to the stack
        //If there are no unvisited vertices next to this one, add this vertex to the vertIDStack for the main function
        for (int i = 0; i < inputGraph.size(); i++) {
            if (!visitedVertices.contains(i) && !addedVertices.contains(i)) {
                traversalStack.add(i);
                while (!traversalStack.isEmpty()){
                    int deepestVertex = DFS(inputGraph);
                    if (deepestVertex >= 0){
                        vertIDStack.push(deepestVertex);
                    }
                }
            }
        }
        visitedVertices.clear();
        addedVertices.clear();
        System.out.println("Started reverse pass.");
        while (!vertIDStack.isEmpty()) {
            int sourceID = vertIDStack.pop();
            if (!visitedVertices.contains(sourceID)) {
                traversalStack.push(sourceID);
                visitedVertices.add(sourceID);
                while (!traversalStack.isEmpty()) {
                    int vertID = DFS(revGraph);
                    if (vertID >= 0) {
                        leaderVertexArray[vertID] = sourceID;
                        leaderVertex.add(sourceID);
                    }
                }
            }
        }
        System.out.println("All SCCs found.");
    }

    private int DFS(ArrayList<ArrayList<Integer>> graph){
        //Set a boolean for leaf checking
        boolean noConn = true;
        //peek at the most recent vertex
        int currNodeID = traversalStack.peek();
        if (!addedVertices.contains(currNodeID)){
            visitedVertices.add(currNodeID);
            //look at all the adjacent nodes. If they're unvisited, add to stack
            ArrayList<Integer> currConn = graph.get(currNodeID);
            for (int adjNode : currConn) {
                if (!visitedVertices.contains(adjNode)) {
                    noConn = false;
                    traversalStack.push(adjNode);
                }
            }
            if (noConn) {
                traversalStack.pop();
                addedVertices.add(currNodeID);
                return currNodeID;
            }
        }else{
            traversalStack.pop();
        }
        return -1;
    }

    private void createSCCMap(){
        int len = leaderVertexArray.length;
        for (int i = 0; i < len; i++){
            int lNodeID = leaderVertexArray[i];
            if(!SCCSize.keySet().contains(lNodeID)) {
                SCCSize.put(lNodeID, 1);
            }else{
                int cnt = SCCSize.get(lNodeID) + 1;
                SCCSize.put(lNodeID, cnt);
            }
        }
    }

    public HashSet<Integer> getLeaderVertex(){
        return leaderVertex;
    }

    public void printTop5SCCSizes(){
        createSCCMap();
        List<Integer> list = new ArrayList<Integer>(SCCSize.values());
        Collections.sort(list,Collections.reverseOrder());
        for (int i = 0; i < 5; i++){
            System.out.println(list.get(i));
        }
    }

    public int[] getLeaderVertexArray() {
        return leaderVertexArray;
    }
}
