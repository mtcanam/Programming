import java.util.*;
import edu.duke.*;
import java.io.*;

public class VigenereBreaker {
    public String sliceString(String message, int whichSlice, int totalSlices) {
        int totalChar = message.length();
        String slicedMsg = "";
        for(int i = whichSlice; i < totalChar; i += totalSlices){
            slicedMsg += message.charAt(i);
        }
        return slicedMsg;
    }

    public int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
        int[] key = new int[klength];
        //klength is key length
        //mostCommon is assumed most common letter
        for(int i = 0; i < klength; i++){
            CaesarCracker cc = new CaesarCracker(mostCommon);
            String slicedMsg = sliceString(encrypted, i, klength);
            key[i] = cc.getKey(slicedMsg);
        }
        return key;
    }

    public void breakVigenere () {
        FileResource messageFile = new FileResource();
        String encMsg = messageFile.asString();
        
        HashMap<String, HashSet<String>> hm = new HashMap<String, HashSet<String>>();
        DirectoryResource dr = new DirectoryResource();
        for(File f:dr.selectedFiles()){
            FileResource fr = new FileResource(f);
            HashSet<String> hs = readDictionary(fr);
            hm.put(f.getName(), hs);
            System.out.println(f.getName() + " reading is completed");
        }
        breakForAllLangs(encMsg, hm);
    }
    
    public HashSet<String> readDictionary(FileResource fr){
        HashSet<String> hs = new HashSet<String>();
        for(String line: fr.lines()){
            if(!hs.contains(line.toLowerCase())){
                hs.add(line.toLowerCase());
            }
        }
        return hs;
    }
    
    public int countWords(String message, HashSet<String> dictionary){
        String[] arr = message.toLowerCase().split("\\W+");
        int wordCount = 0;
        for(String s:arr){
            if(dictionary.contains(s)){
                wordCount += 1;
            }
        }
        return wordCount;
    }
    
    public String breakForLanguage(String encrypted, HashSet<String> dictionary){
        int maxCount = 0;
        int bestKeyLength = 0;
        char mostCommon = mostCommonCharIn(dictionary);
        for(int i = 1; i < 100; i++){
            //i is key length
            int[] key = tryKeyLength(encrypted, i, mostCommon);
            VigenereCipher vc = new VigenereCipher(key);
            String decMsg = vc.decrypt(encrypted);
            int currCount = countWords(decMsg, dictionary);
            if(currCount > maxCount){
                bestKeyLength = i;
                maxCount = currCount;
            }
        }
        System.out.println("The best key length was: " + bestKeyLength + " and the total word count was: " + maxCount);
        int[] key = tryKeyLength(encrypted, bestKeyLength, mostCommon);
        VigenereCipher vc = new VigenereCipher(key);
        String decMsg = vc.decrypt(encrypted);
        return decMsg;
    }
    
    public char mostCommonCharIn(HashSet<String> dictionary){
        int[] counts = new int[26];
        String alph = "abcdefghijklmnopqrstuvwxyz";
        for(String s:dictionary){
            counts = countLetters(s, counts);
        }
        int maxInd = maxIndex(counts);
        return alph.charAt(maxInd);
    }
    
    public void breakForAllLangs(String encrypted, HashMap<String, HashSet<String>> languages){
        int maxCount = 0;
        String maxLang = "NONE";
        String maxMsg = "NONE";
        String decMsg = "NONE";
        for(String s:languages.keySet()){
            decMsg = breakForLanguage(encrypted, languages.get(s));
            int wordCount = countWords(decMsg, languages.get(s));
            if(wordCount > maxCount){
                maxLang = s;
                maxCount = wordCount;
                maxMsg = decMsg;
            }
        }
        System.out.println("The language identified is " + maxLang + ", and the word count is " + maxCount);
        System.out.println(maxMsg);
    }
    
    public int[] countLetters(String message, int[] counts){
        String alph = "abcdefghijklmnopqrstuvwxyz";
        for(int k=0; k < message.length(); k++){
            int dex = alph.indexOf(Character.toLowerCase(message.charAt(k)));
            if (dex != -1){
                counts[dex] += 1;
            }
        }
        return counts;
    }
    
    public int maxIndex(int[] vals){
        int maxDex = 0;
        for(int k=0; k < vals.length; k++){
            if (vals[k] > vals[maxDex]){
                maxDex = k;
            }
        }
        return maxDex;
    }
    
}
