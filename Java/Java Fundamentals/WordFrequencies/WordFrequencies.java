import edu.duke.*;
import java.io.*;
import org.apache.commons.csv.*;
import java.util.*;

/**
 * Write a description of WordFreqencies here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WordFrequencies {
    private ArrayList<String> myWords;
    private ArrayList<Integer> myFreqs;
    
    public WordFrequencies(){
        myWords = new ArrayList<String>();
        myFreqs = new ArrayList<Integer>();
    }
    
    public void tester(){
        findUnique();
        System.out.println("Number of unique words: " + myFreqs.size());
        /* for(int i = 0; i < myFreqs.size(); i++){
            System.out.println(myFreqs.get(i) + "\t" + myWords.get(i));
        } */
        int maxIndex = findIndexOfMax();
        System.out.println("The word that occurs most often and its count are: " + myWords.get(maxIndex) + " " + myFreqs.get(maxIndex));
    }
    
    public void findUnique(){
        myWords.clear();
        myFreqs.clear();
        FileResource fr = new FileResource();
        for(String s:fr.words()){
            s = s.toLowerCase();
            int index = myWords.indexOf(s);
            if(index == -1){
                //Word does not exist; add word and set counter to 1
                myWords.add(s);
                myFreqs.add(1);
            }else{
                //Word already exists; increment the counter at that word
                int value = myFreqs.get(index);
                myFreqs.set(index, value+1);
            }
        }
    }
    
    public int findIndexOfMax(){
        int maxVal = 0;
        int maxIndex = 0;
        for(int i = 0; i < myFreqs.size(); i++){
            int val = myFreqs.get(i);
            if(val > maxVal){
                maxIndex = i;
                maxVal = val;
            }
        }
        return maxIndex;
    }
}
