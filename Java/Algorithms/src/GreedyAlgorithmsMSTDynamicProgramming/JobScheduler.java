package GreedyAlgorithmsMSTDynamicProgramming;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class JobScheduler {

    private static List<Job> dataArr;
    private static int count;

    public static void main(String[] args) throws IOException {
        //Read in the data and create array of jobs
        readData();
        for (int i = 0; i < 5; i++){
            System.out.println(dataArr.get(i));
        }
        long unsorted = CalculateWeightedCompletionTimes();
        System.out.println(unsorted);
        //Sort the data by weight-length, breaking ties with bigger weight first
        Collections.sort(dataArr,new SortByDifference());
        for (int i = 0; i < 5; i++){
            System.out.println(dataArr.get(i));
        }
        long diffSorted = CalculateWeightedCompletionTimes();
        System.out.println(diffSorted);
        //Sort the data by weight/length, ignoring ties, as they don't matter
        Collections.sort(dataArr,new SortByRatio());
        for (int i = 0; i < 5; i++){
            System.out.println(dataArr.get(i));
        }
        long ratioSorted = CalculateWeightedCompletionTimes();
        System.out.println(ratioSorted);
    }

    private static long CalculateWeightedCompletionTimes(){
        //Uses dataArr to calculate weighted completion times
        int len = dataArr.size() - 1;
        long runningTime = 0;
        long wCompTime = 0;
        for(int i = 0; i <= len; i++){
            Job currJob = dataArr.get(i);
            runningTime += currJob.getLength();
            wCompTime += runningTime * currJob.getWeight();
        }
        return wCompTime;
    }

    private static void readData() throws IOException {
        String filePath = "./data/JobSchedulingData.txt";
        String line;
        //Create array of jobs
        dataArr = new ArrayList<Job>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        //Read first line, and assign to count
        line = reader.readLine();
        count = Integer.parseInt(line);
        while ((line = reader.readLine()) != null) {
            if (!line.equals("")) {
                //Take the line, split into weight and length
                String[] parts = line.split("\\s");
                int weight = Integer.parseInt(parts[0]);
                int length = Integer.parseInt(parts[1]);
                //Create new job and add to job array
                Job newJob = new Job(weight, length);
                dataArr.add(newJob);
            }
        }
        reader.close();
    }
}

class Job {

    private int weight;
    private int length;

    public Job(int weight, int length){
        this.weight = weight;
        this.length = length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getLength() {
        return length;
    }

    public int getWeight() {
        return weight;
    }

    public String toString(){
        return weight + "\t" + length;
    }
}

class SortByDifference implements Comparator<Job>
{
    // Used for sorting in ascending order of the difference between weight and length
    //Ties are broken by whichever job has teh higher weight being sorted as lower
    public int compare(Job a, Job b)
    {
        int aDiff = a.getWeight()-a.getLength();
        int bDiff = b.getWeight()-b.getLength();
        if (aDiff == bDiff){
            return b.getWeight()-a.getWeight();
        }else {
            return bDiff - aDiff;
        }
    }
}

class SortByRatio implements Comparator<Job>
{
    // Used for sorting in ascending order of the ratio of weight and length
    public int compare(Job a, Job b)
    {
        double aRat = (double)a.getWeight()/a.getLength();
        double bRat = (double)b.getWeight()/b.getLength();
        if (aRat > bRat){
            return -1;
        }else if(aRat == bRat){
            return 0;
        }else{
            return 1;
        }

    }
}