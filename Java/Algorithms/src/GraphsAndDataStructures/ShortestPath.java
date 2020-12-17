package GraphsAndDataStructures;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class ShortestPath {

    //The hashmap to store all the graph nodes
    private HashMap<Integer, spNode> graph;
    private ArrayList<Integer> processedNodes;

    public ShortestPath(){
        graph = new HashMap<Integer, spNode>();
        processedNodes = new ArrayList<Integer>();
    }

    public static void main(String[] args) throws IOException {
        //Initialize a new shortest
        ShortestPath sp = new ShortestPath();
        //Read in all the data
        sp.readData();
        //Set the start distance to 0 and add to processed nodes
        spNode sink = sp.graph.get(1);
        sink.setSpDist(0);
        sp.graph.put(1, sink);
        sp.processedNodes.add(1);
        //Initialize the minDist and minNode variables
        int minDist = 1000000;
        int minNode = 1;
        while (sp.processedNodes.size() < sp.graph.size()){
            //Main Loop
            //Iterate through all nodes that have been processed
            minDist = 1000000;
            minNode = 0;
            for (int i = 0; i < sp.processedNodes.size(); i++){
                //set the processed node to be 'old'
                spNode old = sp.graph.get(sp.processedNodes.get(i));
                //Iterate through all of the edge connections of the node
                for (int key:old.getEdgeConn().keySet()){
                    //Check to make sure that the edge connection is to a node that has not been processed
                    if (!sp.processedNodes.contains(key)){
                        //calculate the cumulative distance and compare to current min
                        int nodeDist = old.getSpDist() + old.getEdgeConn().get(key);
                        if (nodeDist < minDist){
                            minNode = key;
                            minDist = nodeDist;
                        }
                    }
                }
            }
            //Based on the algorithm, we take the node that was the closest to node 1, set the distance, and note that it's been processed.
            spNode newNode = sp.graph.get(minNode);
            newNode.setSpDist(minDist);
            sp.graph.put(minNode, newNode);
            sp.processedNodes.add(minNode);
        }
        //For checking
        System.out.println(sp.graph.get(7));
        System.out.println(sp.graph.get(37));
        System.out.println(sp.graph.get(59));
        System.out.println(sp.graph.get(82));
        System.out.println(sp.graph.get(99));
        System.out.println(sp.graph.get(115));
        System.out.println(sp.graph.get(133));
        System.out.println(sp.graph.get(165));
        System.out.println(sp.graph.get(188));
        System.out.println(sp.graph.get(197));
    }


    private void readData() throws IOException {
        String filePath = "./data/DijkstraData.txt";
        String line;
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        while ((line = reader.readLine()) != null) {
            if (!line.equals("")) {
                String[] parts = line.split("\\s");

                int src = Integer.parseInt(parts[0]);

                HashMap<Integer, Integer> edges = new HashMap<Integer, Integer>();
                for (int i = 1; i < parts.length; i++){
                    String[] subParts = parts[i].split(",");
                    int outNode = Integer.parseInt(subParts[0]);
                    int weight = Integer.parseInt(subParts[1]);
                    edges.put(outNode, weight);
                }
                graph.put(src, new spNode(src,edges));
            }
        }
        reader.close();

    }

}

class spNode extends Node{
    private HashMap<Integer, Integer> edgeConn;
    private int spDist;

    public spNode(int nodeID, HashMap<Integer, Integer> edgeConn){
        super(nodeID);
        this.edgeConn = edgeConn;
        this.spDist = 1000000;
    }

    public void setEdgeConn(HashMap<Integer, Integer> edgeConn){
        this.edgeConn = edgeConn;
    }

    public HashMap<Integer,Integer> getEdgeConn(){
        return this.edgeConn;
    }

    public void setSpDist(int spDist){
        this.spDist = spDist;
    }

    public int getSpDist(){
        return this.spDist;
    }

    public String toString(){
        return "Node: " + getNodeID() + "\tShortest Distance: " + spDist + "\n";
    }
}
