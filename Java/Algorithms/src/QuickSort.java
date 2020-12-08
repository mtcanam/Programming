import java.util.ArrayList;

public class QuickSort {

    private ArrayList<Integer> unsorted;
    private ArrayList<Integer> sorted;

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

    public static void main(String[] args) {
        ArrayList<Integer> arr = new ArrayList<Integer>();
        arr.add(2);
        arr.add(5);
        arr.add(1);
        arr.add(9);
        arr.add(3);
        arr.add(10);
        ArrayList<Integer> sorted = sortArray(arr, 0, 0, arr.size() - 1);
        System.out.println(sorted);
    }

    public static ArrayList<Integer> sortArray(ArrayList<Integer> arr, int pivotInd, int low, int high){
        //Need to make an algorithm that chooses a random pivot, then partitions the data to the left and right of that pivot

        //Get array length
        int arrLength = high-low;

        //Set the base case return
        if (arrLength < 2){
            return arr;
        }

        //Swap the pivot to the low position on the array
        arr = swap(arr, low, pivotInd);

        //Then partition the array
        PartitionArray pa = partitionArray(arr, low, high);

        //If we're not at the base case, we need to choose a random pivot
        //int pivot = choosePivot(arrLength);

        //Call the sortArray functionality to both sides of the partitioned array
        //Left
        arr = sortArray(pa.getArray(), low, low, pa.getPivotInd() - 1);
        //Right
        arr = sortArray(pa.getArray(), pa.getPivotInd()+1, pa.getPivotInd()+1, high);

        return arr;
    }

    private static PartitionArray partitionArray(ArrayList<Integer> arr, int low, int high){
        //Take in an array, as well as a pivot point
        //Get array length
        int arrLength = arr.size();
        //Read in the pivot value
        int pivotVal = arr.get(low);
        //Set the location of the partition
        int j = low + 1;
        //iterate through all the values in the array. If they are less than the pivot, then swap the left most of the values larger-than-pivot partition with current index
        for (int i = low + 1; i < high; i++){
            //As long as we're not looking at the pivot compared to itself, then we can do the basic operation
            if(arr.get(i) < pivotVal){
                arr = swap(arr, i, j);
                j++;
            }
        }
        //The index j has kept track of where the pivot needs to go
        arr = swap(arr, j - 1, low);

        //then return the partitioned array and teh pivot index
        return new PartitionArray(arr, j-1, low, high);
    }

    private static int choosePivot(int arrLength){
        //Chooses a random pivot
        return 0;
    }

    private static ArrayList<Integer> swap(ArrayList<Integer> arr, int ind1, int ind2){
        int arr1 = arr.get(ind1);
        int arr2 = arr.get(ind2);
        arr.set(ind1, arr2);
        arr.set(ind2, arr1);
        return arr;
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