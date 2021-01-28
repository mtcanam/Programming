package ShortestPathsNPComplete;

import ObjectLibrary.Graph;
import ObjectLibrary.Vertex;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/*
Solution:
An algorithm to solve the shortest path between all pairs of vertices in a graph
#This is of time complexity O(n^2 log(n)), which is pretty decent compared to iterative versions of bellman ford or dikstra
Interesting method is to use vertex weights to transform any negative weight edges to positive weight edges while
preserving the correct shortest paths. Allows the use of Dijkstra's algo (mlog(n)).
Idea here is to pre-process by adding a vertex that has 0-weight edges to all other vertices, then running Bellman-Ford.
The shortest path to each vertex from this imaginary source is the node path weight.
We then use this weight to re-scale all the original edges according to c'e = ce + ps - pt.
This set of modified weights will always be >=0, and can be used in n implementations of Dijkstra's algo to solve.

Reconstruction:


Inputs: Graph
Outputs: A map with key source vertex, and value array of shortest paths to all vertices
*/
public class JohnsonsAlgorithmSolver {

    private Graph inputGraph;
    private int vertexCount;
    private Map<Integer, Long> vertWeights;
    private Map<Integer, long[]> solMap;
    private String status;


    //Initialize the solver with a graph
    public JohnsonsAlgorithmSolver(Graph input){
        this.inputGraph = input;
        this.vertexCount = input.getNodeCount();
        addSpanningVertex();
    }

    public static void main(String[] args) {
        GraphReader graphReader = new GraphReader("APSP1");
        GraphReader graphReader2 = new GraphReader("APSP2");
        GraphReader graphReader3 = new GraphReader("APSP3");
        Graph inputGraph1 = new Graph();
        Graph inputGraph2 = new Graph();
        Graph inputGraph3 = new Graph();
        try {
            graphReader.readDataAndFormGraph();
            inputGraph1 = graphReader.getInputGraph();
            graphReader2.readDataAndFormGraph();
            inputGraph2 = graphReader2.getInputGraph();
            graphReader3.readDataAndFormGraph();
            inputGraph3 = graphReader3.getInputGraph();

        } catch (IOException e) {
            e.printStackTrace();
        }

        boolean status = false;

        try {
            JohnsonsAlgorithmSolver js1 = new JohnsonsAlgorithmSolver(inputGraph1);
            js1.calculateVertexWeights();
            status = true;
        } catch (UnsupportedOperationException e){
        }

        if (status) {
            JohnsonsAlgorithmSolver js1 = new JohnsonsAlgorithmSolver(inputGraph1);
            js1.calculateVertexWeights();
            js1.allPointsDijkstra();
            System.out.println("The first APSP is " + js1.getMinShortestPath());
        } else {
            System.out.println("Negative cycle detected on graph 1.");
        }

        status = false;
        try {
            JohnsonsAlgorithmSolver js2 = new JohnsonsAlgorithmSolver(inputGraph2);
            js2.calculateVertexWeights();
            status = true;
        } catch (UnsupportedOperationException e){

        }
        if (status) {
            JohnsonsAlgorithmSolver js2 = new JohnsonsAlgorithmSolver(inputGraph2);
            js2.calculateVertexWeights();
            js2.allPointsDijkstra();
            System.out.println("The second APSP is " + js2.getMinShortestPath());
        } else {
            System.out.println("Negative cycle detected on graph 2.");
        }

        JohnsonsAlgorithmSolver js3 = new JohnsonsAlgorithmSolver(inputGraph3);
        js3.calculateVertexWeights();
        js3.allPointsDijkstra();
        System.out.println("The third APSP is " + js3.getMinShortestPath());
    }

    //Method to get the re-weighting parameters via Bellman-Ford
    public void calculateVertexWeights(){
        vertWeights = new HashMap<>();
        BellmanFordSolver bfs = new BellmanFordSolver(inputGraph, vertexCount + 1);
        status = bfs.generateSolutionMatrix();
        checkForNegativeCycles();
        long[] arr = bfs.getShortestPaths();
        for (int i = 0; i < arr.length; i++){
            vertWeights.put(i + 1, arr[i]);
        }
        reweighEdges();
    }

    //Helper method to re-weigh all the edges in the graph post Bellman-Ford
    private void reweighEdges(){
        for (int i = 0; i < vertexCount; i++) {
            Vertex currVert = inputGraph.getNode(i + 1);
            HashMap<Integer, Integer> currConn = currVert.getConnections();
            for (int key : currConn.keySet()){
                int weight = currConn.get(key);
                long pu = vertWeights.get(i + 1);
                long pv = vertWeights.get(key);
                long newWeight = weight + pu - pv;
                currConn.put(key, (int) newWeight);
            }
            currVert.setConnections(currConn);
            inputGraph.setNode(i + 1, currVert);
        }
    }

    //Helper function to add a single vertex to the graph that connects to all other vertices with an edge weight of 0
    private void addSpanningVertex(){
        Vertex sourceVert = new Vertex(vertexCount + 1);
        HashMap<Integer, Integer> sourceConn = new HashMap<>();
        //Need to add connections to every other vertex
        for (int vertID:inputGraph.getGraphNodes().keySet()){
            sourceConn.put(vertID,0);
        }
        sourceVert.setConnections(sourceConn);
        inputGraph.setNode(vertexCount + 1, sourceVert);
    }

    //This method will run Dijkstra's method on all vertices, populating a square matrix
    public void allPointsDijkstra(){
        solMap = new HashMap<Integer, long[]>();
        for (int i = 0; i < vertexCount; i++) {
            DijkstraSolver ds = new DijkstraSolver(inputGraph, i + 1);
            ds.findShortestPaths();
            System.out.println("Processing source vertex " + (i+1) + " of " + vertexCount);
            long[] singleSol = ds.getShortestPaths();
            long[] scaledSingleSol = singleSol;
            //Still need to rescale these values, so iterate over all values
            for (int j = 0; j < singleSol.length; j++) {
                //check to make sure that the shortest distance was not a max val (can overflow)
                if (singleSol[j] != Long.MAX_VALUE) {
                    scaledSingleSol[j] = singleSol[j] - vertWeights.get(i + 1) + vertWeights.get(j + 1);
                }else{
                    scaledSingleSol[j] = singleSol[j];
                }
            }
            solMap.put(i + 1, scaledSingleSol);
        }
    }

    //This method returns the smallest shortest path from the arrays
    public long getMinShortestPath(){
        long minLength = Long.MAX_VALUE;
        for (int key: solMap.keySet()){
            long[] singleArray = solMap.get(key);
            for (int i = 0; i < singleArray.length; i++) {
                if (singleArray[i] < minLength && i + 1 != key){
                    minLength = singleArray[i];
                }
            }
        }
        return minLength;
    }

    //Check for negative cycles
    private void checkForNegativeCycles(){
        if (status == "Negative Cycle Detected"){
            throw new UnsupportedOperationException(status);
        }
    }

}
