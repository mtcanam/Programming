import edu.duke.*;
import java.io.*;
import org.apache.commons.csv.*;

/**
 * Write a description of WordLengths here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WordLengths {
    public void testWordLengths(){
        FileResource fr = new FileResource();
        int[] counts = new int[31];
        countWordLengths(fr, counts);
        for(int i = 0; i < counts.length; i++){
            System.out.println("Words of size " + i + ": " + counts[i]);
        }
        int maxIndex = indexOfMax(counts);
        System.out.println("Index of max value is: " + maxIndex);
    }
    
    public void countWordLengths(FileResource resource, int[] counts){
        int maxLength = counts.length;
        int strLength = 0;
        for(String s:resource.words()){
            strLength = s.length();
            if(Character.isLetter(s.charAt(0)) == false){
                strLength--;
            }
            if(Character.isLetter(s.charAt(strLength-1)) == false){
                strLength--;
            }
            if(strLength < maxLength){
                counts[strLength]++;
            }else{
                counts[maxLength]++;
            }
        }
    }
    
    public int indexOfMax(int[] values){
        int maxDex = 0;
            for(int i = 0; i < values.length; i++){
                if (values[i] > values[maxDex]){
                    maxDex = i;
                }
            }
        return maxDex;
    }
    
}
