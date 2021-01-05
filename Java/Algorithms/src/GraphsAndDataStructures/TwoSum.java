package GraphsAndDataStructures;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class TwoSum {

    private static List<Long> dataArr;
    private static Set<Long> dataSet;
    private static Set<Long> sumSet;
    private static int matchCount;

    //Have an array of numbers and a target sum T
    //Are there two numbers in the array that sum to T?
    //solution would be to iterate through all keys in hashmap, find the remainder, then see if it is in the hashtable

    public static void main(String[] args) throws IOException {
        //Hashset performs poorly in this situation (attempted, but needs n^2 operations)
        //First read in data
        readData();
        //Sort the array
        Collections.sort(dataArr);
        //Initialize the sum set
        sumSet = new HashSet<Long>();
        //For each number in the hashset, look for the numbers that are +-10000
        for (long key : dataSet){
            int lInd = findLowBound(key);
            int uInd = findHighBound(key);
            for (int i = lInd; i <= uInd; i++){
                if(uInd != lInd + 1 && key - dataArr.get(i) != 0) {
                    sumSet.add(dataArr.get(i) + key);
                }
            }
        }
        System.out.println("The total number of two sums is: " + sumSet.size());
    }

    private static int findLowBound(long key){
        //Have a sorted data array, need to find the index of the value that is within 10000-key
        int index = Collections.binarySearch(dataArr, 10000-key);
        if (index > 0){
            //Exact bound found
            return index;
        }else{
            //We located the place where the bound should have been. We want the next spot up from there
            return (index + 2) * -1;
        }
    }

    private static int findHighBound(long key){
        //Have a sorted data array, need to find the index of the value that is within -10000-key
        int index = Collections.binarySearch(dataArr, -10000-key);
        if (index > 0){
            //Exact bound found
            return index;
        }else{
            //We located the place where the bound should have been. We want the next spot down from there
            return (index + 1) * -1;
        }
    }

    private static void readData() throws IOException {
        String filePath = "./data/2SUMData.txt";
        String line;
        dataArr = new ArrayList<Long>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        while ((line = reader.readLine()) != null) {
            if (!line.equals("")) {
                dataArr.add(Long.parseLong(line));
            }
        }
        reader.close();
        dataSet = new HashSet<Long>(dataArr);
    }
}
