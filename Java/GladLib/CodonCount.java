import edu.duke.*;
import java.util.*;

/**
 * Write a description of CodonCount here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CodonCount {
    private HashMap<String, Integer> hm;
    
    public CodonCount(){
        hm = new HashMap<String, Integer>();
    }
    
    public void tester(){
        FileResource fr = new FileResource();
        String s = fr.asString().trim().toUpperCase();
        int start = 7;
        int end = 7;
        for(int i = 0; i < 3; i++){
            buildCodonMap(i,s);
            String mcc = getMostCommonCodon();
            System.out.println("Reading frame starting with " + i + " results in " + hm.size() + " unique codons");
            System.out.println("and most common codon is " + mcc + " with count " + hm.get(mcc));
            System.out.println("Counts of codons between " + start + " and " + end + " inclusive are:");
            printCodonCounts(start,end);
            
        }
    }
    
    public void buildCodonMap(int start, String dna){
        hm.clear();
        
        for(int i = start; i < (dna.length() - 2); i+=3){
            String codonString = dna.substring(i,i+3);
            //Check if codon already exists
            if(hm.keySet().contains(codonString)){
                //if the codon already exists, increment the count
                hm.put(codonString, hm.get(codonString) + 1);
            }else{
                //Simply add the codon and initialize at 1
                hm.put(codonString, 1);
            }
        }
    }
    
    public String getMostCommonCodon(){
        int maxVal = 0;
        String maxCod = "NONE";
        for(String s:hm.keySet()){
            if(hm.get(s) > maxVal){
                maxVal = hm.get(s);
                maxCod = s;
            }
        }
        return maxCod;
    }
    
    public void printCodonCounts(int start, int end){
        for(String s:hm.keySet()){
            if(hm.get(s) >= start && hm.get(s) <= end){
                System.out.println(s + "\t" + hm.get(s));
            }
        }
    }
}
