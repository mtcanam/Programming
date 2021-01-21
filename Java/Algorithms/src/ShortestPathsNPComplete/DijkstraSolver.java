package ShortestPathsNPComplete;

/*
This is a solver for the single source shortest path algorithm. Only works with positive edge weights.
Greedy algorithm, so we look at out visited vertices, find the shortest edge that traverses from this set to the
unvisited vertices, and add that new vertex to the visited set.
Speeding up this algorithm requires the use of a heap for the unvisited vertices, where the edge weight is the key.
Continuously picking off the vertex with the shortest path that crosses the boundary will result in the overall shortest path.

Input:
Graph, Source Vertex
Output:
Array of Shortest Paths from Source
 */

import ObjectLibrary.Edge;
import ObjectLibrary.Graph;
import ObjectLibrary.Vertex;

import java.util.*;

public class DijkstraSolver {

    private Graph inputGraph;
    private int sourceVertexID;
    private Set<Integer> visitedVertices;
    private Set<Integer> unvisitedVertices;
    private PriorityQueue<DijkstraHeapVertex> vertHeap;
    private Map<Integer, DijkstraHeapVertex> vertMap;
    private long[] shortestPaths;

    public DijkstraSolver(Graph inputGraph, int sourceVertexID){
        //Initialize the heap and dictionary
        vertHeap = new PriorityQueue<>(new SortByPathLength());
        vertMap = new HashMap<>();
        //Double check that there are no negative lengths, throw error if so.
        //At the same time, create new DijkstraHeapVertices and add to heap/map
        for (int key : inputGraph.getGraphNodes().keySet()){
            Vertex currVert = inputGraph.getNode(key);
            HashMap<Integer, Integer> currConn = currVert.getConnections();
            for (int edgeWeight:currConn.values()) {
                if (edgeWeight < 0) {
                    throw new UnsupportedOperationException();
                }
            }
            DijkstraHeapVertex dv = new DijkstraHeapVertex(key);
            //If we're looking at the source vertex, set the distance to be 0
            if (key == sourceVertexID){
                dv.setDistance(0);
            }
            vertHeap.add(dv);
            vertMap.put(key, dv);
        }
        this.inputGraph = inputGraph;
        this.sourceVertexID = sourceVertexID;
        //Initialize the visited and unvisited vertices
        visitedVertices = new HashSet<>();

        unvisitedVertices = new HashSet<>();
        unvisitedVertices.addAll(inputGraph.getGraphNodes().keySet());
    }

    //Method to use Dijkstra's greedy algorithm to solve for shortest paths
    public void findShortestPaths(){
        //Iterate until all vertices are visited
        while (unvisitedVertices.size() > 0){
            //Extract the minimum distance
            DijkstraHeapVertex minVert = vertHeap.poll();
            int minID = minVert.getVertexID();
            long minDist = minVert.getDistance();

            //Move the source vertex to the visited vertex set
            visitedVertices.add(minID);
            unvisitedVertices.remove(minID);

            //Now need to update the distances for all vertices connected to source vertex
            //If the min distance here is already the max value, then no more nodes are connected, so no need to update

            if (minDist != Long.MAX_VALUE) {
                updateDistances(minID, minDist);
            }
        }
        shortestPaths = new long[vertMap.size()];
        for (int i = 0; i < vertMap.size(); i++){
            shortestPaths[i] = vertMap.get(i + 1).getDistance();
        }
    }

    //Return array of shortest paths
    public long[] getShortestPaths(){
        return shortestPaths;
    }

    //Helper method to update distances for all vertices connected to supplier vertex
    private void updateDistances(int vertID, long dist){
        //Get connections from the supplied vertex
        Vertex currVert = inputGraph.getNode(vertID);
        HashMap<Integer, Integer> currConn = currVert.getConnections();
        //Iterate over all connections, and take the distance to the supplied vertex, adding the path length to get the
        //potential new distance. If we already have a lower distance from a previous path, ignore this.
        for (int id: currConn.keySet()){
            if (unvisitedVertices.contains(id)) {
                long currDist = vertMap.get(id).getDistance();
                long altDist = dist + currConn.get(id);
                if (altDist < currDist) {
                    //If we need to update this vertex, get the original vert from the map, remove from heap, change, and
                    //re-add to both map and heap.
                    DijkstraHeapVertex changedVert = vertMap.get(id);
                    vertHeap.remove(changedVert);
                    changedVert.setDistance(altDist);
                    vertMap.put(id, changedVert);
                    vertHeap.add(changedVert);
                }
            }
        }
    }

}

class DijkstraHeapVertex{
    private int vertexID;
    private long distance;

    public DijkstraHeapVertex(int vertexID){
        this.vertexID = vertexID;
        this.distance = Long.MAX_VALUE;
    }

    public int getVertexID() {
        return vertexID;
    }

    public long getDistance() {
        return distance;
    }

    public void setDistance(long distance) {
        this.distance = distance;
    }
}

class SortByPathLength implements Comparator<DijkstraHeapVertex>{
    public int compare(DijkstraHeapVertex a, DijkstraHeapVertex b) {
        //Sorts small to large for distances
        long distA = a.getDistance();
        long distB = b.getDistance();
        if (distA < distB){
            return -1;
        }else{
            return 1;
        }
    }
}