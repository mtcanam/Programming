package ShortestPathsNPComplete;
/*
Solution:
An algorithm to solve the shortest path between one vertex and all other vertices in a graph.
This is of time complexity O(n^2), which is good considering it supports negative edge lengths (unlike Dijkstra's)
Interesting method is to impose a limit on the number of edges (i).
Steps are to loop through:
1. Edge budget (i) from 1 to n-1
2. Each vertex in the graph (v from 1 to n).
The two cases to assess a each step are:
1. The weight of the path from s to v with one fewer edge (i-1 iteration)
2. The weight of the path to w (which is a vertex that has an outbound connection to v) + the weight of w->v
Note: The above means that case 2 will have an in-degree number of possible values
We take the minimum of all cases above, and assign that to A[i,v].
If we have negative cycles, we will get a negative number in the diagonal of A at the completion time.

Reconstruction:

*/


/*
Input: Graph
Outputs: Matrix of solutions (main solver), path reconstruction (path solver)
*/

import ObjectLibrary.Graph;
import ObjectLibrary.Vertex;

import java.util.HashMap;
import java.util.HashSet;

public class BellmanFordSolver {

    private Graph inputGraph;
    private int sourceNodeID;
    private int vertexCount;
    private long[][] solArray;
    private long[] shortestPaths;
    private boolean negativeCycleDetected;


    //Initialize the solver with a graph and a source node id
    public BellmanFordSolver(Graph input, int sourceID){
        this.inputGraph = input;
        this.sourceNodeID = sourceID;
        this.vertexCount = input.getNodeCount();
    }

    //This is the main algorithm and solver.
    //Input: Graph
    //Output: Matrix of solutions
    public String generateSolutionMatrix(){
        solArray = new long[vertexCount][vertexCount];
        shortestPaths = new long[vertexCount];
        //Fill the entire array with max values, except the chosen source, which is distance 0.
        for(int j = 0; j < vertexCount; j++){
            if (j == this.sourceNodeID - 1){
                solArray[0][j] = 0;
            }else{
                solArray[0][j] = Long.MAX_VALUE;
            }
        }

        //Iterate over all length budgets (except the previously defined 0), and all vertices.
        for (int i = 1; i < vertexCount; i++){
            for (int j = 0; j < vertexCount; j++){
                //Case 1
                Long case1Value = solArray[i-1][j];
                //Case 2
                Long case2Value = getMinCase2Value(i, j + 1);
                solArray[i][j] = Math.min(case1Value, case2Value);
            }
        }

        //Check for negative values in last row (ie a negative cycle exists).
        for (int i = 0; i < vertexCount; i++){
            if (solArray[vertexCount - 1][i] < solArray[vertexCount - 2][i]){
                negativeCycleDetected = true;
            }
            shortestPaths[i] = solArray[vertexCount - 1][i];
        }

        //Return string of min path
        if (!negativeCycleDetected){
            return "Success";
        }else{
            return "Negative Cycle Detected";
        }

    }

    //Helper function to return the shortest paths
    public long[] getShortestPaths() {
        return shortestPaths;
    }

    //Helper function to determine the min case 2 value
    //Iterates over all inbound edges, and determines the shortest path to the nodeID
    private Long getMinCase2Value(int edgeBudget, int nodeID){
        //Get current vertex
        Vertex currVertex = inputGraph.getNode(nodeID);
        //Get list of inbound vertices
        HashSet<Integer> inList = currVertex.getInboundVertices();
        //Iterate over list, and keep the minimum distance
        long minPathLength = Long.MAX_VALUE;
        for (int inNode:inList){
            //Get the weight of the incoming edge
            Vertex inVertex = inputGraph.getNode(inNode);
            HashMap<Integer,Integer> outConn = inVertex.getConnections();
            int outWeight = outConn.get(nodeID);
            //Get the current best path length from s to this node
            long currBest = solArray[edgeBudget - 1][inNode - 1];
            //Ensure that the current path isn't already max_value (might overflow)
            if (currBest < Long.MAX_VALUE) {
                //Sum to get the best path length to v
                long currPathLength = currBest + outWeight;
                if (currPathLength < minPathLength) {
                    minPathLength = currPathLength;
                }
            }
        }
        return minPathLength;
    }

}
