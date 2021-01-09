package GreedyAlgorithmsMSTDynamicProgramming;

import GraphsAndDataStructures.Node;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class PrimsMST {
    private static int edgeCount;
    private static int nodeCount;
    private static Map<Integer, Vertex> graph;
    private static PriorityQueue<Vertex> vertexHeap;
    private static Set<Integer> unvisitedNodes;
    private static Set<Integer> visitedNodes;
    private static long treeSpan;

    public static void main(String[] args) throws IOException {
        treeSpan = 0;
        //Initialize the sets of visited and unvisited nodes
        unvisitedNodes = new HashSet<Integer>();
        visitedNodes = new HashSet<Integer>();
        //Read in data
        readData();
        //Test that the size of the hashset of unvisited nodes is correct
        System.out.println(unvisitedNodes.size() + " should be equal to " + nodeCount);
        //Now we need to pick a node (in this case just whatever the first is), and:
        //Add this node to the visited nodes (and remove from unvisited)
        //For all the edges of the node that was added, if they cross the boundary (ie they lead to unvisited nodes)
        //  We need to update the keys.

        vertexHeap = new PriorityQueue<Vertex>(new SortByWeight());

        //Select this first element of the graph as the starting point
        int firstNode = (int) graph.keySet().toArray()[0];
        unvisitedNodes.remove(firstNode);
        visitedNodes.add(firstNode);
        for (int node : graph.keySet()){
            //Iterate through all nodes and update keys, adding all to teh heap
            updateKey(node);
        }
        //Execute main loop
        while (unvisitedNodes.size() > 0){
            pickNewNode();
        }

        System.out.println("Total spanning tree weight is: " + treeSpan);
    }



    private static void pickNewNode(){
        //Pick the smallest element from teh heap, then add that to the visited, remove from unvisited
        Vertex newVertex = vertexHeap.poll();
        int node = newVertex.getNodeID();
        unvisitedNodes.remove(node);
        visitedNodes.add(node);
        treeSpan += graph.get(node).getKey();
        //Now need to update keys, and update heap
        HashMap<Integer, Integer> conn = newVertex.getConnections();
        for (int destNode : conn.keySet()){
            if(unvisitedNodes.contains(destNode)){
                while (vertexHeap.contains(graph.get(destNode))) {
                    vertexHeap.remove(graph.get(destNode));
                }
                updateKey(destNode);
            }
        }
    }

    private static void readData() throws IOException {
        String filePath = "./data/PrimsEdgeData.txt";
        String line;
        //Initialize the graph
        graph = new HashMap<Integer, Vertex>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        //Read first line, and assign to count of edges and nodes
        line = reader.readLine();
        String[] parts = line.split("\\s");
        nodeCount = Integer.parseInt(parts[0]);
        edgeCount = Integer.parseInt(parts[1]);
        while ((line = reader.readLine()) != null) {
            if (!line.equals("")) {
                //Take the line, split into three parts:
                //[one_node_of_edge_1] [other_node_of_edge_1] [edge_1_cost]
                parts = line.split("\\s");
                int node1 = Integer.parseInt(parts[0]);
                int node2 = Integer.parseInt(parts[1]);
                int weight = Integer.parseInt(parts[2]);
                //Look at both nodes. If we have seen them before, add this new connection. Otherwise, create and add the nodes
                if (graph.containsKey(node1)){
                    addConn(node1, node2, weight);
                }else{
                    addNode(node1, node2, weight);
                }
                if (graph.containsKey(node2)){
                    addConn(node2, node1, weight);
                }else{
                    addNode(node2, node1, weight);
                }
                //Add both nodes to the HashSet (if not already existing)
                unvisitedNodes.add(node1);
                unvisitedNodes.add(node2);
            }
        }
        reader.close();
    }

    private static void addConn(int node1, int node2, int weight){
        //Just add the connection
        Vertex currVertex = graph.get(node1);
        HashMap<Integer, Integer> currConn = currVertex.getConnections();
        currConn.put(node2, weight);
        currVertex.setConnections(currConn);
        graph.put(node1, currVertex);
    }

    private static void addNode(int node1, int node2, int weight){
        Vertex currVertex = new Vertex(node1);
        graph.put(node1, currVertex);
        addConn(node1, node2, weight);
    }

    private static void updateKey(int node){
        //Note, defaults to 100000 if there is no crossing
        //Get the list of connections for this node
        Vertex currVertex = graph.get(node);
        HashMap<Integer, Integer> currConn = currVertex.getConnections();
        boolean nodeCrosses = false;
        int minWeight = 100000;
        //Iterate through the connections, finding the smallest that leads to an unvisited node
        for(int destNode : currConn.keySet()){
            if (unvisitedNodes.contains(node) && visitedNodes.contains(destNode)){
                if(!nodeCrosses) {
                    //If this is the first edge that crosses the boundary, override the min weight by default
                    minWeight = currConn.get(destNode);
                    nodeCrosses = true;
                }else {
                    //We know this is an edge that crosses the boundary
                    if(currConn.get(destNode) < minWeight){
                        minWeight = currConn.get(destNode);
                    }
                }
            }
        }
        currVertex.setKey(minWeight);
        graph.put(node, currVertex);
        vertexHeap.add(currVertex);
    }

}

class Vertex extends ObjectLibrary.Vertex {
    private int key;

    public Vertex(int id) {
        super(id);
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }

}

class SortByWeight implements Comparator<Vertex>
{
    // Used for sorting in descending order of edge weight
    public int compare(Vertex a, Vertex b)
    {
        return a.getKey() - b.getKey();
    }
}
