import java.util.ArrayList;

public class MergeSort {

    private ArrayList<Integer> unsorted;
    private ArrayList<Integer> sorted;

    public MergeSort(ArrayList<Integer> unsorted) {
        this.unsorted = unsorted;
        this.sorted = sortArray(unsorted);
    }
    public ArrayList<Integer> getSorted(){
        return sorted;
    }

    public ArrayList<Integer> getUnsorted(){
        return unsorted;
    }

    public static void main(String[] args) {

    }

    //
    public static ArrayList<Integer> sortArray(ArrayList<Integer> unsorted) {
        //takes in the unsorted array, and sorts it, returning an array of the same length, with all values sorted
        //Once we get down to length 1, return that value to progress to merge
        int len = unsorted.size();
        if (len == 1) {
            return unsorted;
        }
        //Split the input list
        ArrayList<Integer> left = new ArrayList<Integer>();
        ArrayList<Integer> right = new ArrayList<Integer>();
        for (int i = 0; i < len; i++){
            if (i < len/2){
                left.add(unsorted.get(i));
            }else{
                right.add(unsorted.get(i));
            }
        }

        //Call sortArray on the two constituent lists
        ArrayList<Integer> lSort = sortArray(left);
        ArrayList<Integer> rSort = sortArray(right);

        //Merge the two lists
        ArrayList<Integer> mSort = mergeArray(lSort, rSort);
        //System.out.println(mSort);
        return mSort;
    }

    private static ArrayList<Integer> mergeArray(ArrayList<Integer> lArr, ArrayList<Integer> rArr){
        //Takes arrays in lArr and rArr and merges them
        //First create arrayList for the merger
        ArrayList<Integer> mergeArr = new ArrayList<Integer>();
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
                } else if (lArr.get(i) <= rArr.get(j)) {
                    mergeArr.add(lArr.get(i));
                    i++;
                }
            }
        }
        return mergeArr;
    }
}
