import edu.duke.*;
import java.util.*;
import java.io.*;
/**
 * Write a description of WordsInFiles here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WordsInFiles {
    private HashMap<String, ArrayList<String>> hm;
    
    public WordsInFiles(){
        hm = new HashMap<String, ArrayList<String>>();
        
    }
    
    public void tester(){
        buildWordFileMap();
        int maxNum = maxNumber();
        ArrayList<String> arr = wordsInNumFiles(4);
        
        System.out.println("The greatest number of files a word appears in is " + maxNum + ", and there are " + arr.size() + " such words.");
        /*for(int i = 0; i < arr.size(); i++){
            System.out.println(arr.get(i));
            printFilesIn(arr.get(i));
        }*/
    }
    
    private void addWordsFromFile(File f){
        String fName = f.getName();
        FileResource fr = new FileResource(f);
        for(String s:fr.words()){
            if(hm.keySet().contains(s)){
                //The word already exists, add filename to the arraylist
                ArrayList<String> fList = hm.get(s);
                if(!fList.contains(fName)){
                    //If the file name does not exist in teh array, add it
                    fList.add(fName);
                }
                hm.put(s,fList);
            }else{
                //if the word does not exist, add the key and an arraylist with the current filename
                ArrayList<String> fList = new ArrayList<String>();
                fList.add(fName);
                hm.put(s,fList);
            }
        }
    }
    
    public void buildWordFileMap(){
        hm.clear();
        DirectoryResource dr = new DirectoryResource();
        for(File f:dr.selectedFiles()){
            addWordsFromFile(f);
        }
    }
    
    public int maxNumber(){
        int maxSize = 0;
        for(String s:hm.keySet()){
            int currSize = hm.get(s).size();
            if(currSize > maxSize){
                maxSize = currSize;
            }
        }
        return maxSize;
    }
    
    public ArrayList<String> wordsInNumFiles(int number){
        ArrayList<String> arr = new ArrayList<String>();
        for(String s:hm.keySet()){
            int currSize = hm.get(s).size();
            if(currSize == number){
                arr.add(s);
            }
        }
        return arr;
    }
    
    public void printFilesIn(String word){
        ArrayList<String> arr = hm.get(word);
        for(int i = 0; i < arr.size(); i++){
            System.out.println(arr.get(i));
        }
    }
}
