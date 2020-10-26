import edu.duke.*;
import java.io.File;

/**
 * Write a description of Part1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Part1 {

    public int findStopCodon(String dna, int startIndex, String stopCodon)
    {
        int currIndex = startIndex;
        while(dna.indexOf(stopCodon, currIndex+1)!=-1){
            currIndex = dna.indexOf(stopCodon, currIndex+1);
            //System.out.println(currIndex);
            if((currIndex - startIndex) % 3 == 0){
                return currIndex;
            }
        }
        return dna.length();
    }
    
    public String findGene(String dna){
        String startCodon = "ATG";
        //Find index of first ATG
        int startIndex = dna.indexOf(startCodon);
        if(startIndex == -1){
            return "";
        }
        //Find first TAA
        int stopTAA = findStopCodon(dna,startIndex,"TAA");
        //Find first TAA
        int stopTAG = findStopCodon(dna,startIndex,"TAG");
        //Find first TAA
        int stopTGA = findStopCodon(dna,startIndex,"TGA");
        int minLength = Math.min(stopTAA,Math.min(stopTAG,stopTGA));
        if (minLength < dna.length()){
            return dna.substring(startIndex,minLength+3);
        }else{
            return "";
        }
        
    }
    public void printAllGenes(String dna){
        String foundGene = "";
        int geneStopIndex = 0;
        while(geneStopIndex != dna.length() && geneStopIndex != -1){
            foundGene = findGene(dna.substring(geneStopIndex)); 
            geneStopIndex = dna.indexOf(foundGene) + foundGene.length();
            System.out.println(foundGene);
        }
    }
    
    public StorageResource getAllGenes(String dna){
        StorageResource sr = new StorageResource();
        
        String foundGene = "";
        int geneStopIndex = 0;
        while(geneStopIndex != dna.length() && geneStopIndex != -1){
            foundGene = findGene(dna.substring(geneStopIndex)); 
            if (foundGene.length()==0){
                break;
            }
            geneStopIndex = dna.indexOf(foundGene,geneStopIndex) + foundGene.length()+1;
            sr.add(foundGene);
        }
        
        return sr;
    }
    
    public float cgRatio(String dna){
        //loop through string and count cs and gs
        int cnt = 0;
        for (int i=0; i<dna.length();i++){
            if(dna.charAt(i)=='C' || dna.charAt(i)=='G'){
                cnt++;
            }
        }
        return ((float) cnt)/dna.length();
    }
    
    public int countCTG(String dna){
        int cnt = 0;
        int currInd = dna.indexOf("CTG");
        while (currInd != -1){
            currInd = dna.indexOf("CTG",currInd+3);
            cnt++;
        }   
        return cnt;
    }
    
    public void processGenes(StorageResource sr){
        int sLenCount = 0;
        int sCGCount = 0;
        int maxLength = 0;
        int ctgCnt = 0;
        String longestGene = "";
        StorageResource geneSR = new StorageResource();
        for (String s:sr.data()){
            geneSR = getAllGenes(s);
            for (String s2: geneSR.data()){
                //if string length is greater than 9, print
                if (s2.length() > 60){
                    System.out.println("Long String: " + s2);
                    sLenCount++;
                }
                //Print strings whose cg ratio is greater than 0.35    
                if (cgRatio(s2) > 0.35){
                    System.out.println("CG String: " + s2);
                    sCGCount++;
                }
                //Find the longest gene
                if (s2.length() > maxLength){
                    longestGene = s2;
                    maxLength = s2.length();
                }
            }
            ctgCnt = countCTG(s);
        }
        //print the total number of strings larger than 9 characters
        System.out.println("Total long string count: " + sLenCount);
        //print the total number of strings with cgratio > 0.35
        System.out.println("Total high CG Ratio string count: " + sCGCount);
        //print the longest gene
        System.out.println("Longest gene: " + longestGene + " at length: " + longestGene.length());
        //print number of ctgs
        System.out.println("CTG Count: " + ctgCnt);
    }
    
    public void testProcessGenes(){
        FileResource fr = new FileResource("GRch38dnapart.fa");
        String dna = fr.asString().toUpperCase();
        StorageResource sr = new StorageResource();
        sr.add(dna);
        processGenes(sr);
    }
    
    public String mystery(String dna) {
      int pos = dna.indexOf("T");
      int count = 0;
      int startPos = 0;
      String newDna = "";
      if (pos == -1) {
        return dna;
      }
      while (count < 3) {
        count += 1;
        newDna = newDna + dna.substring(startPos,pos);
        startPos = pos+1;
        pos = dna.indexOf("T", startPos);
        if (pos == -1) {
          break;
        }
      }
      newDna = newDna + dna.substring(startPos);
      return newDna;
    }
    
    public void testFindStopCodon(){
        //DNA with no “ATG”, 
        String dna1 = "ATATATATATATTTAAATTGAAT";
        System.out.println(dna1);
        System.out.println(findGene(dna1));
        //DNA with “ATG” and one valid stop codon, 
        String dna2 = "ATATATGATATATTTATAATTAAT";
        System.out.println(dna2);
        System.out.println(findGene(dna2));
        //DNA with “ATG” and multiple valid stop codons, 
        String dna3 = "ATATATGATATATTTATAATGAAT";
        System.out.println(dna3);
        System.out.println(findGene(dna3));
        //DNA with “ATG” and no valid stop codons. 
        String dna4 = "ATATATGATATATATATATATAGT";
        System.out.println(dna4);
        System.out.println(findGene(dna4)); 
        //Test out multiple genes
        
        String dna5 = "ATATGARDFRTGYHJUIKILTAAERTTGEATGWERFREWEDTAG";
        System.out.println(dna5);
        printAllGenes(dna5);
        
        StorageResource sr = getAllGenes(dna5);
        for (String s:sr.data()){
            System.out.println(s);
        }
        
    }
}
