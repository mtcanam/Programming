package ShortestPathsNPComplete;

import ObjectLibrary.Graph;
import ObjectLibrary.Vertex;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class TwoSATSolver {

    private Graph inputGraph;
    public Graph revGraph;
    private ArrayList<ArrayList<Integer>> inputArray;
    private ArrayList<ArrayList<Integer>> revArray;
    private int condCount;
    private int vertCount;

    public TwoSATSolver(String fName) throws IOException {
        readDataAndFormGraph(fName);
        inputArray = outputLightGraphArray(inputGraph);
        revArray = outputLightGraphArray(revGraph);
    }

    //This method reads in the conditions, and creates an implication graph
    private void readDataAndFormGraph(String fileName) throws IOException {
        inputGraph = new Graph();
        revGraph = new Graph();
        String filePath = "./data/" + fileName + ".txt";
        String line;
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        //Read first line
        line = reader.readLine();
        //Take the line, split into vertex count and edge count
        condCount = Integer.parseInt(line);
        vertCount = condCount;
        //Iterate over remaining data
        while ((line = reader.readLine()) != null) {
            if (!line.equals("")) {
                //Take the line, split into node1, node2, weight
                String[] parts = line.split("\\s");
                //Take the condition noted. If we have a negative, cast it to the positive value plus a constant (highestVert)
                //Then, take the vertices, and get their negation values
                int vert1ID = Integer.parseInt(parts[0]);
                int nVert1ID;
                if (vert1ID < 0){
                    nVert1ID = -1 * vert1ID;
                    vert1ID = nVert1ID + vertCount;
                }else{
                    nVert1ID = vert1ID + vertCount;
                }
                int vert2ID = Integer.parseInt(parts[1]);
                int nVert2ID;
                if (vert2ID < 0){
                    nVert2ID = -1 * vert2ID;
                    vert2ID = nVert2ID + vertCount;
                }else{
                    nVert2ID = vert2ID + vertCount;
                }
                int weight = 0;
                //Add two edges, one representing -a to b, one representing -b to a
                //Create vertices a, b, -a, -b
                Vertex vert1 = new Vertex(vert1ID);
                inputGraph.setAndMergeNodeNoInboundVertices(vert1ID, vert1);
                Vertex vert2 = new Vertex(vert2ID);
                inputGraph.setAndMergeNodeNoInboundVertices(vert2ID, vert2);
                Vertex vert3 = new Vertex(nVert1ID);
                HashMap<Integer,Integer> conn = new HashMap<>();
                conn.put(vert2ID, weight);
                vert3.setConnections(conn);
                inputGraph.setAndMergeNodeNoInboundVertices(nVert1ID, vert3);
                Vertex vert4 = new Vertex(nVert2ID);
                conn = new HashMap<>();
                conn.put(vert1ID, weight);
                vert4.setConnections(conn);
                inputGraph.setAndMergeNodeNoInboundVertices(nVert2ID, vert4);

                //Add two edges, one representing b to -a, one representing a to -b
                //Create vertices a, b, -a, -b
                vert1 = new Vertex(vert1ID);
                conn = new HashMap<>();
                conn.put(nVert2ID, weight);
                vert1.setConnections(conn);
                revGraph.setAndMergeNodeNoInboundVertices(vert1ID, vert1);
                vert2 = new Vertex(vert2ID);
                conn = new HashMap<>();
                conn.put(nVert1ID, weight);
                vert2.setConnections(conn);
                revGraph.setAndMergeNodeNoInboundVertices(vert2ID, vert2);
                vert3 = new Vertex(nVert1ID);
                revGraph.setAndMergeNodeNoInboundVertices(nVert1ID, vert3);
                vert4 = new Vertex(nVert2ID);
                revGraph.setAndMergeNodeNoInboundVertices(nVert2ID, vert4);
            }
        }
        reader.close();
    }

    //This method outputs the stored as a parameter to a arraylist of arraylists for memory purposes
    public ArrayList<ArrayList<Integer>> outputLightGraphArray(Graph currGraph){
        ArrayList<ArrayList<Integer>> arr = new ArrayList<>();
        for (int i = 1; i <= vertCount * 2; i++) {
            Vertex currVert = currGraph.getNode(i);
            if (currVert != null) {
                ArrayList<Integer> currConn = currVert.getConnectionVertices();
                ArrayList<Integer> adjConn = new ArrayList<>();
                for (int j = 0; j < currConn.size(); j++) {
                    adjConn.add(currConn.get(j) - 1);
                }
                arr.add(i - 1, adjConn);
            }else{
                arr.add(i - 1, new ArrayList<Integer>());
            }
        }
        return arr;
    }

    //This method feeds the graph to the SCCStackSolver, and prints the result of whether a var and
    // it's negation are in the same SCC
    public void isSatisfiable(){
        SCCStackSolver sss = new SCCStackSolver(inputArray, revArray);
        sss.countSCC();
        int[] lVert = sss.getLeaderVertexArray();
        for (int i = 0; i < vertCount; i++) {
            if(lVert[i] == lVert[i + vertCount]){
                System.out.println("Not Satisfiable");
                return;
            }
        }
        System.out.println("Satisfiable");
    }

}
