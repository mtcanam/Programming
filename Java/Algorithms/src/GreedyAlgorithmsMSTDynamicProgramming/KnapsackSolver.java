package GreedyAlgorithmsMSTDynamicProgramming;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
We have a set of items, with particular values and weights
Need to optimize the set of items that will fit in the backpack (capacity-wise) by max value
The general idea is to take the max of a certain set of subproblems:
  1. If we include this item, we take the value of the item, and the value of the recursive subproblem
     with the capacity of the sack reduced by the weight of the item in question
  2. If we don't include this item, we take the value from the subproblem of the same capacity, and the previous item
Generally, we would use an array of subproblems with i = [0, capacity] and j = [0, items]
This will not be possible, as the data set is too large, so create a subroutine that looks for subproblem answers
in a hashmap. If found, use that answer, if not found, recurse until capacity 0.
For this solution map, string with hyphenated item-capacity will be the key, and val will be the value
*/

public class KnapsackSolver {

    private static long sackCapacity;
    private static int itemCount;
    private static List<SackItem> sackItems;
    private static Map<String, Long> solMap;

    public static void main(String[] args) {
        //Read in the data and initialize the solution map
        try {
            readData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        solMap = new HashMap<>();
        //Iterate through the items, calling the solve function for each sackItem in sackItems
        for(int i = 0; i < itemCount; i++){
            SackItem currItem = sackItems.get(i);
            solveProblem(i, currItem, sackCapacity);
        }
        //Get the final solution from the solution array
        String finalKey = itemCount - 1 + "-" + sackCapacity;
        System.out.println(solMap.get(finalKey));
    }

    private static long solveProblem(int item, SackItem currItem, long capacity){
        //In this function, we need to compare the two subproblems noted above

        //First we create the degenerate solution
        if(item == 0){
            long degenSol = 0;
            if (currItem.getWeight() <= capacity){
                degenSol = currItem.getValue();
            }
            String newKey = item + "-" + capacity;
            solMap.put(newKey,degenSol);
            return degenSol;
        }

        SackItem prevItem = sackItems.get(item - 1);

        //If we are not at the degenerate solution, we need to check the two subproblems noted in the description
        //The precursor check for the first subproblem is to ensure we even have room for this new item at this capacity
        long sol1 = 0L;
        if(currItem.getWeight() <= capacity) {
            //If we have enough room, we use the current object, then recurse on the remaining capacity
            //First check if this solution already exists in the solution map. Otherwise, calculate it recursively.
            String residKey = item - 1 + "-" + String.valueOf(capacity - currItem.getWeight());
            long residSol = 0L;
            if (solMap.containsKey(residKey)) {
                residSol = solMap.get(residKey);
            } else {
                residSol = solveProblem(item - 1, prevItem, capacity - currItem.getWeight());
            }
            //After the calculation of the residual capacity value, we can add it to the current value to get the first option
            sol1 = currItem.getValue() + residSol;
        }
        //Number 2 is the solution without the current object. This means we just take the solution at the current capacity minus this item.
        //Again, calculate it recursively if not already calculated and saved in the map.
        String prevSolKey = (item - 1) + "-" + capacity;
        long sol2 = 0L;
        if (solMap.containsKey(prevSolKey)){
            sol2 = solMap.get(prevSolKey);
        }else {
            sol2 = solveProblem(item - 1, prevItem, capacity);
        }
        //Take the max value of the two options, add the solution to the map, and return the value.
        //The return is only relevant for the recursive calculations. Otherwise, we can use the solution map to get whichever we want.
        long maxSol = Math.max(sol1, sol2);
        String newKey = item + "-" + capacity;
        solMap.put(newKey,maxSol);
        return maxSol;
    }

    private static void readData() throws IOException {
        String filePath = "./data/knapsackData.txt";
        String line;
        //Create array of items
        sackItems = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        //Read first line, and assign to count
        line = reader.readLine();
        //Take the line, split into weight and length
        String[] parts = line.split("\\s");
        sackCapacity = Long.parseLong(parts[0]);
        itemCount = Integer.parseInt(parts[1]);
        while ((line = reader.readLine()) != null) {
            if (!line.equals("")) {
                //Take the line, split into weight and length
                parts = line.split("\\s");
                int value = Integer.parseInt(parts[0]);
                int weight = Integer.parseInt(parts[1]);
                //Create new job and add to job array
                sackItems.add(new SackItem(value, weight));
            }
        }
        reader.close();
    }

}

class SackItem {
    private long value;
    private long weight;

    public SackItem(long v, long w){
        value = v;
        weight = w;
    }

    public long getValue() {
        return value;
    }

    public long getWeight() {
        return weight;
    }

}