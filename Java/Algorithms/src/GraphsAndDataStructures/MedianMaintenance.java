package GraphsAndDataStructures;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class MedianMaintenance {

    private static ArrayList<Integer> dataArr;
    private static ArrayList<Integer> medianArr;
    private static PriorityQueue<Integer> lowHeap;
    private static PriorityQueue<Integer> highHeap;

    public static void main(String[] args) throws IOException {
        //Read in the numbers to an arraylist
        readData();
        //Create the two heaps (one min, and one max), and median array
        //High heap has the default ordering, ie the head is the lowest value
        //low heap has the reverse ordering, ie the head is the highest value
        highHeap = new PriorityQueue<Integer>();
        lowHeap = new PriorityQueue<Integer>(Comparator.reverseOrder());
        medianArr = new ArrayList<Integer>();
        //Loop through all the data in the array
        for (int i = 0; i < dataArr.size(); i++){
            medianArr.add(getMedian(i));
        }
        long medSum = 0;
        //sum all of the elements of the median array
        for (int i = 0; i < medianArr.size(); i++){
            medSum += medianArr.get(i);
        }
        System.out.println(medSum % 10000);
    }

    private static int getMedian(int index){
        int currVal = dataArr.get(index);
        //If we're at one of the first two elements, just add to each of the heaps (0th to min, first to max)
        //Otherwise, perform normal operations
        if (index < 2){
            lowHeap.add(currVal);
        }else{
            //Get the heads of the two heaps
            int highVal = lowHeap.peek();
            int lowVal = highHeap.peek();
            //If the current value is less than the highVal (ie the highest value of the low heap), then add the number to that heap
            if (currVal <= lowVal){
                lowHeap.add(currVal);
            }else{
                highHeap.add(currVal);
            }
        }
        //Check whether we need to rebalance
        int lowSize = lowHeap.size();
        int highSize = highHeap.size();
        if (Math.abs(lowSize - highSize) > 1){
            rebalanceHeaps();
        }

        //If the number of elements in the two heaps is even, take the low heap head, otherwise, take the larger heap head
        if((lowSize + highSize) % 2 == 0) {
            return lowHeap.peek();
        }else{
            if (lowSize > highSize){
                return lowHeap.peek();
            }else{
                return highHeap.peek();
            }
        }
    }

    private static void rebalanceHeaps(){
        //If the heaps are currently unbalanced, we need to remove one element from the larger heap, and add it to the smaller heap
        int lowSize = lowHeap.size();
        int highSize = highHeap.size();
        if (lowSize < highSize){
            int head = highHeap.poll();
            lowHeap.add(head);
        }else{
            int head = lowHeap.poll();
            highHeap.add(head);
        }
    }

    private static void readData() throws IOException {
        String filePath = "./data/MedianData.txt";
        String line;
        dataArr = new ArrayList<Integer>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        while ((line = reader.readLine()) != null) {
            if (!line.equals("")) {
                dataArr.add(Integer.parseInt(line));
            }
        }
        reader.close();
    }

}
