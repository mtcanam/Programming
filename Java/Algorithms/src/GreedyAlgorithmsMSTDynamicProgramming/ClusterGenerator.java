package GreedyAlgorithmsMSTDynamicProgramming;

import ObjectLibrary.UnionFind;
import ObjectLibrary.Edge;
import ObjectLibrary.Graph;
import ObjectLibrary.Vertex;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ClusterGenerator {

    private static Set<Integer> vertexList;
    private static PriorityQueue<Edge> edgeHeap;
    private static UnionFind vertexClusters;
    private static int nodeCount;
    private static int clusterCount;

    public static void main(String[] args) {
        //Want to create a union-find structure comprised of the nodeids
        //Want to create a heap of the edges, with weight being the key, and a simple arraylist containing the nodes
        //Iteratively pick the edges from this heap, check that the two nodes are not in the same set (via find)
        //If they are, go to the next edge until they are not, and union the two sets
        //Need to keep track of cluster count along the way (cCount = # of Init nodes - # of Merges)
        clusterCount = 0;
        int desiredClusters = 4;
        //Fill union find structure with node ids, and fill the heap with edges
        try {
            readData();
            vertexClusters = new UnionFind();
            Integer[] vertexArr = new Integer[vertexList.size()];
            vertexArr = vertexList.toArray(vertexArr);
            vertexClusters.makeSet(vertexArr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Assess initial cluster count
        clusterCount = nodeCount;
        //Perform clustering, as above
        int lastEdge = 0;
        while (clusterCount > desiredClusters - 1){
            lastEdge = performClustering();
        }
        //Compress the structure of the union-find
        vertexClusters.structureCompression();
        //Output the next edge that woudl be combined
        System.out.println(lastEdge);
    }

    private static int performClustering(){
        //Takes the next lowest distance, unions (by default, the union operation checks whether the two are already in teh same set)
        Edge minEdge = edgeHeap.poll();
        ArrayList<Integer> connVert = (ArrayList<Integer>) minEdge.getConn();
        int set1 = vertexClusters.Find(connVert.get(0));
        int set2 = vertexClusters.Find(connVert.get(1));
        if (set1 != set2){
            //We know the edge crosses set boundaries
            vertexClusters.Union(set1, set2);
            //Update the cluster count
            clusterCount--;
        }
        return minEdge.getWeight();
    }



    private static void readData() throws IOException {
        String filePath = "./data/ClusteringData.txt";
        String line;
        //Create set for clusters and heap for edges
        vertexList = new HashSet<Integer>();
        edgeHeap = new PriorityQueue<Edge>(new SortByEdgeWeight());
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        //Read first line, and assign to count
        line = reader.readLine();
        nodeCount = Integer.parseInt(line);
        while ((line = reader.readLine()) != null) {
            if (!line.equals("")) {
                //Take the line, split into node1, node2, weight
                String[] parts = line.split("\\s");
                int node1 = Integer.parseInt(parts[0]);
                int node2 = Integer.parseInt(parts[1]);
                int weight = Integer.parseInt(parts[2]);
                //Add the two nodes to the vertexCluster
                vertexList.add(node1);
                vertexList.add(node2);
                //Add the edge to the heap
                ArrayList<Integer> arr = new ArrayList<Integer>();
                arr.add(node1);
                arr.add(node2);
                edgeHeap.add(new Edge(weight, arr));
            }
        }
        reader.close();
    }

}

class SortByEdgeWeight implements Comparator<Edge>{
    public int compare(Edge a, Edge b) {
        //Sorts small to large for edge weights
        return a.getWeight() - b.getWeight();
    }
}