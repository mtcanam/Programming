package GreedyAlgorithmsMSTDynamicProgramming;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MaxWISPathFinder {
    //Take in a path (series of vertices with weights)
    //Find the set of non-adjacent vertices that maximize the summed weight
    //General idea is to progress from left to right with two ideas in mind
    //1. Either the current vertex is part of the set, in which case, we have this node plus
    //   the set described by the path without the last two vertices
    //2. Otherwise, the current vertex is not part of the set, in which case, we have the
    //   the set described by the path without this node.
    //Keeping the WIS up to this point in an array of size n, we can just compare the value of 1. and 2.
    //and keep the larger of the two

    private static int vertCount;
    private static ArrayList<Integer> weightArr;
    private static ArrayList<Long> pathArr;

    public static void main(String[] args) {
        //Read in the path
        try {
            readData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        pathArr = new ArrayList<>();
        //Initialize the first element to 0
        pathArr.add(0L);
        //Add the first element to the array
        pathArr.add((long)weightArr.get(1));
        //Loop through from left to right, checking the WIS
        for(int i = 2; i <= vertCount; i++){
            assessWIS(i);
        }
        System.out.println(pathArr.get(vertCount));
        System.out.println(pathArr);
        System.out.println(weightArr);
        System.out.println(reconstructSet());
    }

    private static void assessWIS(int i){
        //Need to assess what the max weight of the WIS should be at this point
        //First, check the case where this vertex is not included
        long notIncluded = pathArr.get(i - 1);
        //Then check the case where the current vertex is included
        long isIncluded = pathArr.get(i - 2) + weightArr.get(i);
        //Compare, and add the larger as the ith element of the path array
        pathArr.add(Math.max(notIncluded,isIncluded));
    }

    private static Set<Integer> reconstructSet(){
        Set<Integer> WISNodes = new HashSet<>();
        int i = vertCount;
        while (i > 1){
            if (pathArr.get(i - 1) < pathArr.get(i - 2) + weightArr.get(i)) {
                //i is included, add to set
                WISNodes.add(i);
                i--;
            }
            i--;
        }
        //The above algorithm works for all but 1, so need to add that vertex if it is available
        if(!WISNodes.contains(2)){
            WISNodes.add(1);
        }
        return WISNodes;
    }

    private static void readData() throws IOException {
        String filePath = "./data/MaxWISData.txt";
        String line;
        //Create array of jobs
        weightArr = new ArrayList<>();
        //Add a weight of 0 to sync the array indices
        weightArr.add(0);
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        //Read first line, and assign to count
        line = reader.readLine();
        vertCount = Integer.parseInt(line);
        while ((line = reader.readLine()) != null) {
            if (!line.equals("")) {
                //Take the line, split into weight and length
                int weight = Integer.parseInt(line);
                //Create new job and add to job array
                weightArr.add(weight);
            }
        }
        reader.close();
    }

}
