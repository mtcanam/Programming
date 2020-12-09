import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class QuickSort {

    private ArrayList<Integer> unsorted;
    private ArrayList<Integer> sorted;
    private static long compCounter;

    public QuickSort(ArrayList<Integer> unsorted) {
        this.unsorted = unsorted;
        this.sorted = sorted;

    }

    public ArrayList<Integer> getSorted(){
        return sorted;
    }
    public ArrayList<Integer> getUnsorted(){
        return unsorted;
    }

    public static void main(String[] args) throws FileNotFoundException {
        compCounter = 0;
        ArrayList<Integer> arr = readData();
        //int pInd = 0;
        //int pInd = arr.size()-1;
        int pInd = getMedian(arr,0,arr.size()-1);
        ArrayList<Integer> sorted = sortArray(arr, pInd, 0, arr.size()-1);
        System.out.println(sorted);
        System.out.println(compCounter);
    }

    public static ArrayList<Integer> sortArray(ArrayList<Integer> arr, int pivotInd, int low, int high){
        //Need to make an algorithm that chooses a random pivot, then partitions the data to the left and right of that pivot
        //Counter for number of computations


        //Get array length
        int arrLength = high-low+1;

        //Set the base case return
        if (arrLength < 2){
            return arr;
        }

        compCounter += (arrLength - 1);

        //Swap the pivot to the low position on the array
        arr = swap(arr, low, pivotInd);

        //Then partition the array
        PartitionArray pa = partitionArray(arr, low, high);

        //If we're not at the base case, we need to choose a  pivot
        //for low pivots
        //int pLeft = low;
        //int pRight = pa.getPivotInd()+1;
        //for high pivots
        //int pLeft = pa.getPivotInd()-1;
        //int pRight = high;
        //for median pivots
        int pLeft = getMedian(pa.getArray(), low, pa.getPivotInd() - 1);
        int pRight = getMedian(pa.getArray(), pa.getPivotInd() + 1, high);
        //Call the sortArray functionality to both sides of the partitioned array
        //Left
        arr = sortArray(pa.getArray(), pLeft, low, pa.getPivotInd()-1);
        //Right
        arr = sortArray(pa.getArray(),  pRight, pa.getPivotInd()+1, high);

        return arr;
    }

    private static PartitionArray partitionArray(ArrayList<Integer> arr, int low, int high){
        //Take in an array, where the pivot has been moved to the left most value (low)
        //Read in the pivot value
        int pivotVal = arr.get(low);
        //Set the location of the partition
        int j = low + 1;
        //iterate through all the values in the array. If they are less than the pivot, then swap the left most of the values larger-than-pivot partition with current index
        for (int i = low + 1; i <= high; i++){
            //As long as we're not looking at the pivot compared to itself, then we can do the basic operation
            if(arr.get(i) < pivotVal){
                arr = swap(arr, i, j);
                j++;
            }
        }
        //The index j has kept track of where the pivot needs to go
        arr = swap(arr, low, j - 1);

        //then return the partitioned array and teh pivot index
        return new PartitionArray(arr, j - 1, low, high);
    }

    private static ArrayList<Integer> swap(ArrayList<Integer> arr, int ind1, int ind2){
        int arr1 = arr.get(ind1);
        int arr2 = arr.get(ind2);
        arr.set(ind1, arr2);
        arr.set(ind2, arr1);
        return arr;
    }

    private static ArrayList<Integer> readData() throws FileNotFoundException {
        Scanner s = new Scanner(new File("./data/QuickSortData.txt"));
        ArrayList<Integer> list = new ArrayList<Integer>();
        while (s.hasNext()){
            list.add(Integer.parseInt(s.next()));
        }
        s.close();
        return list;
    }

    private static int getMedian(ArrayList<Integer> arr, int low, int high){

        if(high - low < 2){
            return low;
        }

        //Just choose the median value of the lower, upper, and middle numbers
        int lowVal = arr.get(low);
        int medVal = arr.get((high+low)/2);
        int highVal = arr.get(high);

        int lowestVal = Math.min(Math.min(lowVal,medVal),highVal);
        int highestVal = Math.max(Math.max(lowVal,medVal),highVal);

        if (lowVal != lowestVal && lowVal != highestVal){
            return low;
        }else if(medVal != lowestVal && medVal != highestVal){
            return (high+low)/2;
        }else if (highVal != lowestVal && highVal != highestVal){
            return high;
        }else{
            System.out.println("Issues");
            return -1;
        }
    }
}

class PartitionArray{

    private ArrayList<Integer> arr;
    private int pivotInd;
    private int low;
    private int high;

    public PartitionArray(ArrayList<Integer> arr, int pivotInd, int low, int high){
        this.arr = arr;
        this.pivotInd = pivotInd;
        this.low = low;
        this.high = high;
    }

    public ArrayList<Integer> getArray(){
        return arr;
    }

    public int getPivotInd(){
        return pivotInd;
    }

    public int getLow(){
        return low;
    }

    public int getHigh(){
        return high;
    }
}