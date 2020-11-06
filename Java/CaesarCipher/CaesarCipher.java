import edu.duke.*;
import java.io.*;
import org.apache.commons.csv.*;



/**
 * Write a description of CaesarCipher here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CaesarCipher {
    public void testMethod(){
        System.out.println(encryptTwoKeys("At noon be in the conference room with your hat on for a surprise party. YELL LOUD!", 8, 21));
    }
    
    public void testCaesar(){
        FileResource fr = new FileResource();
        String message = fr.asString();
        int key = 23;
        String encrypted = encrypt(message, key);
        System.out.println("key is " + key + "\n" + encrypted);
    }
    
    public String encrypt(String input, int key){
        StringBuilder encrypted = new StringBuilder(input);
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        String shiftedAlphabet = alphabet.substring(key) + alphabet.substring(0,key);
        for(int i = 0; i < encrypted.length(); i++){
            char currChar = encrypted.charAt(i);
            boolean lCase = Character.isLowerCase(currChar);
            if (lCase == true){
                int idx = alphabet.indexOf(currChar);
                if(idx!=-1){
                    char newChar = shiftedAlphabet.charAt(idx);
                    encrypted.setCharAt(i, newChar);
                }
            }else{
                int idx = alphabet.toUpperCase().indexOf(currChar);
                if(idx!=-1){
                    char newChar = shiftedAlphabet.toUpperCase().charAt(idx);
                    encrypted.setCharAt(i, newChar);
                }
            }
            
        }
        return encrypted.toString();
    }
    
    public String encryptTwoKeys(String input, int key1, int key2){
        StringBuilder encrypted = new StringBuilder(input);
        String enc1 = encrypt(input,key1);
        String enc2 = encrypt(input,key2);
        for(int i = 0; i < encrypted.length(); i++){
            if(i%2==0){
                encrypted.setCharAt(i, enc1.charAt(i));
            }else{
                encrypted.setCharAt(i, enc2.charAt(i));
            }
        }
        return encrypted.toString();
    }
    
    public int[] countLetters(String decrypt){
        String alph = "abcdefghijklmnopqrstuvwxyz";
        int[] counts = new int[26];
        for(int i = 0; i < decrypt.length(); i++){
            char ch = Character.toLowerCase(decrypt.charAt(i));
            int dex = alph.indexOf(ch);
            if(dex!=-1){
                counts[dex]++;
            }
        }
        return counts;
    }
    
    public int maxIndex(int[] vals){
        int maxDex = 0;
        for(int i = 0; i < vals.length; i++){
            if (vals[i] > vals[maxDex]){
                maxDex = i;
            }
        }
        return maxDex;
    }
}
