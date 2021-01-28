package ShortestPathsNPComplete;

import ObjectLibrary.Graph;
import ObjectLibrary.Vertex;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

    /*
    This object reads in text data and returns a graph object
     */

public class GraphReader {

    private String fileName;
    private long vertCount;
    private long edgeCount;
    private Graph inputGraph;
    private Graph revGraph;

    public GraphReader(String fName){
        this.fileName = fName;
        this.inputGraph = new Graph();
        this.revGraph = new Graph();
    }

    public void readDataAndFormGraph() throws IOException {
        String filePath = "./data/" + fileName + ".txt";
        String line;
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        //Read first line
        line = reader.readLine();
        //Take the line, split into vertex count and edge count
        String[] parts = line.split("\\s");
        vertCount = Integer.parseInt(parts[0]);
        edgeCount = Integer.parseInt(parts[1]);
        //Iterate over remaining data
        while ((line = reader.readLine()) != null) {
            if (!line.equals("")) {
                //Take the line, split into node1, node2, weight
                parts = line.split("\\s");
                int vert1ID = Integer.parseInt(parts[0]);
                int vert2ID = Integer.parseInt(parts[1]);
                int weight = 0;
                if (parts.length == 3) {
                    weight = Integer.parseInt(parts[2]);
                }
                //Create vertices 1 and 2
                Vertex vert1 = new Vertex(vert1ID);
                HashMap<Integer,Integer> conn = new HashMap<>();
                conn.put(vert2ID, weight);
                vert1.setConnections(conn);
                inputGraph.setAndMergeNodeNoInboundVertices(vert1ID, vert1);
                Vertex vert2 = new Vertex(vert2ID);
                inputGraph.setAndMergeNodeNoInboundVertices(vert2ID, vert2);

                //Create vertices 1 and 2
                vert1 = new Vertex(vert1ID);
                conn = new HashMap<>();
                conn.put(vert1ID, weight);
                vert2 = new Vertex(vert2ID);
                vert2.setConnections(conn);
                revGraph.setAndMergeNodeNoInboundVertices(vert1ID, vert1);
                revGraph.setAndMergeNodeNoInboundVertices(vert2ID, vert2);
            }
        }
        reader.close();
    }

    public Graph getInputGraph() {
        return inputGraph;
    }

    public Graph getRevGraph() {
        return revGraph;
    }

    //This method outputs the stored as a parameter to a arraylist of arraylists for memory purposes
    public ArrayList<ArrayList<Integer>> outputLightGraphArray(Graph currGraph){
        ArrayList<ArrayList<Integer>> arr = new ArrayList<>();
        for (int i = 1; i <= currGraph.getNodeCount(); i++) {
            Vertex currVert = currGraph.getNode(i);
            ArrayList<Integer> currConn = currVert.getConnectionVertices();
            ArrayList<Integer> adjConn = new ArrayList<>();
            for (int j = 0; j < currConn.size(); j++) {
                adjConn.add(currConn.get(j) - 1);
            }
            arr.add(i - 1, adjConn);
        }
        return arr;
    }


}
