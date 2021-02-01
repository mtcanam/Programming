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
        
    }
}
