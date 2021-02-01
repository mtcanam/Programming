
/**
 * Write a description of Part3 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Part3 {

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
    public int countGenes(String dna){
        String foundGene = "";
        int geneStopIndex = 0;
        int cnt = 0;
        while(geneStopIndex != dna.length() && geneStopIndex != -1){
            foundGene = findGene(dna.substring(geneStopIndex)); 
            geneStopIndex = dna.indexOf(foundGene) + foundGene.length();
            cnt=cnt + 1;
        }
        return cnt;
    }

}
