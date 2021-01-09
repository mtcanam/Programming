package GreedyAlgorithmsMSTDynamicProgramming;

import ObjectLibrary.Edge;
import ObjectLibrary.UnionFind;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class BitCluster {

    private static List<BitSet> nodes;
    private static UnionFind nodeClusters;
    private static int nodeCount;
    private static int bitLength;
    private static Map<BitSet, ArrayList<Integer>> map;

    public static void main(String[] args) {
        map = new HashMap<>();
        nodeClusters = new UnionFind();
        //Read in the data
        try {
            readData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Create arbitrary node labels for joining
        Integer[] nodeLabels = new Integer[nodeCount];
        for (int i = 0; i < nodeCount; i++){
            nodeLabels[i] = i;
        }
        nodeClusters.makeSet(nodeLabels);
        //Go through the all of bitsets, and create a map that describes which nodes are within 2 flips of that bitset
        for (int i = 0; i < nodeCount; i++) {
            addCloseNodes(i);
            System.out.println("Input node " + i + " of " + nodeCount);
        }
        //Now we cluster all the duplicates in the map, so just union the nodes when they belong to the same bitset array
        for (int i = 0; i < nodeCount; i++) {
            joinCloseNodes(i);
            System.out.println("Unioned node " + i + " of " + nodeCount);
        }
        System.out.println(nodeClusters.getNumSets());
    }

    private static void joinCloseNodes(int i){
        BitSet bs = nodes.get(i);
        if (map.containsKey(bs)){
            ArrayList<Integer> arr = map.get(bs);
            if (arr.size() == 0) {

            }
            for (int j = 0; j < arr.size();j++){
                nodeClusters.Union(i,arr.get(j));
            }
        }
    }

    private static void addCloseNodes(int i){
        //This looks at the current bitset, and calculates all the bitsets that are within 0,1,2 distance (bit flips)
        //Then adds them to the map
        //First, the addition of the base bitset
        addToMap(i, nodes.get(i));
        //Then all that are 1 flip away
        for (int j = 0; j < bitLength; j++){
            BitSet currBS = (BitSet) nodes.get(i).clone();
            currBS.flip(j);
            addToMap(i,currBS);
        }
        //Then all that are 1 flip away
        for (int j = 0; j < bitLength; j++){
            for (int k = j + 1; k < bitLength; k++) {
                BitSet currBS = (BitSet) nodes.get(i).clone();
                currBS.flip(j);
                currBS.flip(k);
                addToMap(i, currBS);
            }
        }

    }

    private static void addToMap(int i, BitSet bs){
        ArrayList<Integer> arr = new ArrayList<>();
        //Check if the bitset already exists
        if (map.containsKey(bs)){
            arr = map.get(bs);
        }
        //Add the new node to the arraylist (pre-existing or blank)
        arr.add(i);
        //Update the map
        map.put(bs,arr);
    }

    private static void readData() throws IOException {
        String filePath = "./data/ClusteringDataBig.txt";
        String line;
        //Create set for clusters and heap for edges
        nodes = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        //Read first line, and assign to count
        line = reader.readLine();
        String[] parts = line.split("\\s");
        nodeCount = Integer.parseInt(parts[0]);
        bitLength = Integer.parseInt(parts[1]);
        while ((line = reader.readLine()) != null) {
            if (!line.equals("")) {
                //Take the line, split into node1, node2, weight
                parts = line.split("\\s");
                BitSet bs = new BitSet(bitLength);
                for (int i = 0; i < bitLength; i++){
                    if (Integer.parseInt(parts[i]) == 1){
                        bs.set(i);
                    }
                }
                nodes.add(bs);
            }
        }
        reader.close();
    }
}
