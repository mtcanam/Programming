import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

public class InversionCounter {
    public InversionCounter(){

    }

    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<Integer> input = readData();
        RecursionCounter invCount = countInversions(input);
        System.out.println("The inversion count is: " + invCount.getRunningCount()); //Should be n(n-1)/2
    }

    public static RecursionCounter countInversions(ArrayList<Integer> arr){
        //Feeds in an array of integers, need to split in half, merge sort the two halves separately
        int arrSize = arr.size();

        //If we're down to an array of one, we have no inversions, so return array and counter at 0
        if(arrSize == 1) {
            return new RecursionCounter(arr, BigInteger.ZERO);
        }

        ArrayList<Integer> lArr = new ArrayList<Integer>();
        ArrayList<Integer> rArr = new ArrayList<Integer>();

        //First create and fill the left and right arrays
        for (int i = 0; i < arrSize; i++){
            if (i < arrSize / 2){
                lArr.add(arr.get(i));
            }else{
                rArr.add(arr.get(i));
            }
        }

        //Call the recursive algorithm
        RecursionCounter lSide = countInversions(lArr);
        RecursionCounter rSide = countInversions(rArr);

        //Take the sides of the array, and merger sort/count split inversions
        RecursionCounter mergedRecCounter = mergeAndCountSplitInversions(lSide.getSortedArray(), rSide.getSortedArray());

        return new RecursionCounter(mergedRecCounter.getSortedArray(), lSide.getRunningCount().add(rSide.getRunningCount()).add(mergedRecCounter.getRunningCount()));
    }

    private static RecursionCounter mergeAndCountSplitInversions(ArrayList<Integer> lArr, ArrayList<Integer> rArr){
        //Takes arrays in lArr and rArr and merges them
        //First create arrayList for the merger and counter for the inversions
        ArrayList<Integer> mergeArr = new ArrayList<Integer>();
        BigInteger invCounter = BigInteger.ZERO;
        //Get the size of the constituent arrays
        int lSize = lArr.size();
        int rSize = rArr.size();
        int mSize = lSize + rSize;
        //loop through all entries mergeArr
        int i = 0; //index for lArr
        int j = 0; //index for rArr
        boolean lDone = false;
        boolean rDone = false;
        for (int k = 0; k < mSize; k++) {
            //For each entry in the merged array, check what the lowest reamining value in the two original arrays is
            //i and j save the current position of the two arrays
            //First need to add a check to make sure that the array indices haven't been reached
            if (i == lSize){
                lDone = true;
            }
            if (j == rSize){
                rDone = true;
            }
            if(lDone){
                mergeArr.add(rArr.get(j));
                j++;
            }else if(rDone){
                mergeArr.add(lArr.get(i));
                i++;
            }else {
                if (lArr.get(i) > rArr.get(j)) {
                    mergeArr.add(rArr.get(j));
                    j++;
                    //The sole addition to the merge sort code is to add in a step here
                    //Every time that we have the right array getting added before the left is done, we need to increment the inv counter by how many are left in teh left array
                    invCounter = invCounter.add(BigInteger.valueOf(lSize - i));
                } else if (lArr.get(i) <= rArr.get(j)) {
                    mergeArr.add(lArr.get(i));
                    i++;
                }
            }
        }
        return new RecursionCounter(mergeArr, invCounter);
    }

    private static ArrayList<Integer> readData() throws FileNotFoundException {
        Scanner s = new Scanner(new File("./data/IntegerArray.txt"));
        ArrayList<Integer> list = new ArrayList<Integer>();
        while (s.hasNext()){
            list.add(Integer.parseInt(s.next()));
        }
        s.close();
        return list;
    }
}

class RecursionCounter{

    private ArrayList<Integer> sortedArray;
    private BigInteger runningCount;

    public RecursionCounter(ArrayList<Integer> arr, BigInteger counter){
        this.sortedArray = arr;
        this.runningCount = counter;
    }

    public ArrayList<Integer> getSortedArray(){
        return sortedArray;
    }

    public BigInteger getRunningCount(){
        return runningCount;
    }

}