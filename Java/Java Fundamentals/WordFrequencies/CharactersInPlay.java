import edu.duke.*;
import java.io.*;
import org.apache.commons.csv.*;
import java.util.*;

/**
 * Write a description of CharactersInPlay here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CharactersInPlay {
    private ArrayList<String> myWords;
    private ArrayList<Integer> myFreqs;
    
    public CharactersInPlay(){
        myWords = new ArrayList<String>();
        myFreqs = new ArrayList<Integer>();
    }
    
    public void tester(){
        findAllCharacters();
        for(int i = 0; i < myWords.size(); i++){
            if(myFreqs.get(i) > 100){
                System.out.println(myWords.get(i) + " " + myFreqs.get(i));
            }
        }
        characterWithNumParts(10,15);
    }
    
    public void update(String person){
        person = person.toLowerCase();
        int index = myWords.indexOf(person);
        if(index == -1){
            //Word does not exist; add word and set counter to 1
            myWords.add(person);
            myFreqs.add(1);
        }else{
            //Word already exists; increment the counter at that word
            int value = myFreqs.get(index);
            myFreqs.set(index, value+1);
        }
    }

    public void findAllCharacters(){
        myWords.clear();
        myFreqs.clear();
        FileResource fr = new FileResource();
        for(String line : fr.lines()){
            int dotIndex = line.indexOf(".");
            if(dotIndex != -1){
                //Period was found. Substring to that point and call update.
                String charName = line.substring(1,dotIndex);
                update(charName);
            }
        }
        
    }
    
    public void characterWithNumParts(int num1, int num2){
        //num1 <= num2
        for(int i = 0; i < myWords.size(); i++){
            if(myFreqs.get(i) >= num1 && myFreqs.get(i) <= num2){
                System.out.println(myWords.get(i) + " " + myFreqs.get(i));
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
