package DivideAndConquer;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class KargerMinCut {

    private static Random randSrcNode;
    private static Random randDestNode;

    public KargerMinCut(){

    }

    public static void main(String[] args) throws IOException {
        randSrcNode = new Random();
        //Read in the connections as a hashmap of integers and arraylists
        HashMap<Integer, ArrayList<Integer>> connections = readData();
        //Determine size of graph to determine number of iterations
        int n = connections.size() ^ 2 * (int) Math.log((double) connections.size());
        //Set the min counter
        int minCut = connections.size();
        String connectionPts = "";
        for(int k = 0; k < n; k++) {
            //Read in the connections as a hashmap of integers and arraylists
            connections = readData();
            //Create list to store uncontracted nodes
            ArrayList<Integer> uncontractedList = new ArrayList<Integer>(connections.keySet());
            //Iterate over the main call until we are left with two nodes
            while (connections.size() > 2) {
                //Determine random edge to contract
                int nodeInd = randSrcNode.nextInt(connections.size());
                int srcNode = uncontractedList.get(nodeInd);
                //From the random node selected, pick a random edge
                int destNode = determineEdge(connections, srcNode);
                //Call contraction algorithm on randomly selected node
                connections = contractEdge(connections, srcNode, destNode);
                for (int i = 0; i < uncontractedList.size(); i++) {
                    if (uncontractedList.get(i) == Math.max(srcNode, destNode)) {
                        uncontractedList.remove(i);
                    }
                }
            }
            for(int key: connections.keySet()) {
                if (connections.get(key).size() < minCut) {
                    minCut = connections.get(key).size();
                    connectionPts = connections.toString();
                }
            }
        }
        System.out.println(minCut);
        System.out.println(connectionPts);
    }

    private static HashMap<Integer, ArrayList<Integer>> contractEdge(HashMap<Integer, ArrayList<Integer>> hm, int srcNode, int destNode){

        //Create new hashmap for the results
        HashMap<Integer, ArrayList<Integer>> hmOut = new HashMap<Integer, ArrayList<Integer>>();

        //First, we get the min and max nodes from node and edge
        int smVal = Math.min(srcNode, destNode);
        int lgVal = Math.max(srcNode, destNode);

        //Then, we copy over all the connections from the larger indexed array to teh smaller, and delete the larger map entry
        ArrayList<Integer> arr = hm.get(smVal);
        ArrayList<Integer> arr2 = hm.get(lgVal);
        for (int i = 0; i < arr2.size(); i++){
            arr.add(arr2.get(i));
        }
        hm.remove(lgVal);

        //Then, we iterate through all values in the map, and change all references to the larger node to teh smaller
        //At teh same time, we look for self references, and remove them
        for (int key:hm.keySet()){
            arr = hm.get(key);
            int len = arr.size();
            for (int i = 0; i < len; i++){
                if (arr.get(i) == lgVal){
                    arr.set(i, smVal);
                }
                if (arr.get(i) == key){
                    arr.remove(i);
                    i--;
                    len--;
                }
            }
            hmOut.put(key, arr);
        }
        //Now, we have the new hashmap with the corrected adjacency lists
        return hmOut;
    }

    private static int determineEdge(HashMap<Integer, ArrayList<Integer>> hm, int startNode){
        //Take the start node and hash map, find a rand edge to try to remove
        randDestNode = new Random();
        ArrayList<Integer> arr = hm.get(startNode);
        int len = arr.size();
        int edgeInd = randDestNode.nextInt(len);
        return arr.get(edgeInd);
    }

    private static HashMap<Integer, ArrayList<Integer>> readData() throws IOException {
        String filePath = "./data/MinCut.txt";
        HashMap<Integer, ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();

        String line;
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        while ((line = reader.readLine()) != null)
        {
            String[] parts = line.split("\\s");
            ArrayList<Integer> arr = new ArrayList<Integer>();
            int len = parts.length;
            for (int i = 1; i < len; i++){
                arr.add(Integer.parseInt(parts[i]));
            }
            map.put(Integer.parseInt(parts[0]),arr);
        }
        reader.close();
        return map;
    }

}
