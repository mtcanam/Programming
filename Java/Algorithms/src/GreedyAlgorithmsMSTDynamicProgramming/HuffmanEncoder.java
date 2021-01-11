package GreedyAlgorithmsMSTDynamicProgramming;

import ObjectLibrary.HuffmanTree;
import ObjectLibrary.TreeNode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.PriorityQueue;

public class HuffmanEncoder {

    private static int symbolCount;
    private static PriorityQueue<HuffmanTree> treeHeap;


    //We want to repeatedly find the minimum two weights in the symbols that remain, and merge vertices
    //Every time we merge, add the two weights/symbols as siblings in a binary tree (irrelevant which is l/r)

    //Key ideas are to dump the trees in a heap (repeated min calls), removing individuals and adding combined as joined
    //Sort by total weight of tree

    public static void main(String[] args) {
        //read in the data
        try {
            readData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Start main loop, where the trees are merged
        for (int i = 0; i < symbolCount - 1; i++){
            huffmanEncode();
            System.out.println("Processing merger " + i + " of " + symbolCount);
        }

        HuffmanTree finalTree = treeHeap.poll();
        System.out.println(finalTree.getMinDepth() +  " is the shortest word. \n" + finalTree.getMaxDepth() + " is the longest word.");

    }

    private static void huffmanEncode(){
        //Peel off the two trees with the lowest weights
        HuffmanTree tree1 = treeHeap.poll();
        HuffmanTree tree2 = treeHeap.poll();

        //Create new merged tree
        HuffmanTree newTree = new HuffmanTree(tree1, tree2);

        //Re add this tree to heap
        treeHeap.add(newTree);
    }


    private static void readData() throws IOException {
        String filePath = "./data/huffmanData.txt";
        String line;
        //Create array of jobs
        treeHeap = new PriorityQueue<>(new SortBySymbolWeight());
        //Add a weight of 0 to sync the array indices
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        //Read first line, and assign to count
        line = reader.readLine();
        symbolCount = Integer.parseInt(line);
        while ((line = reader.readLine()) != null) {
            if (!line.equals("")) {
                //Take the line, parse to integer
                int weight = Integer.parseInt(line);
                //Create a new tree with this weight as a single leaf
                HuffmanTree currTree = new HuffmanTree(weight);
                //Create new job and add to job array
                treeHeap.add(currTree);
            }
        }
        reader.close();
    }

}

class SortBySymbolWeight implements Comparator<HuffmanTree> {
    @Override
    public int compare(HuffmanTree o1, HuffmanTree o2) {
        return (int) (o1.getTotalWeight() - o2.getTotalWeight());
    }
}