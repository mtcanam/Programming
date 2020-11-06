import edu.duke.*;
import java.io.*;
import org.apache.commons.csv.*;

/**
 * Write a description of CaesarBreaker here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CaesarBreaker {
    public void testMethod(){
        String alph = "abcdefghijklmnopqrstuvwxyz";
        String message = "The quick brown fox jumped over the lazy dogs";
        int[] counts = countLetters(message);
        for(int i=0;i<counts.length;i++){
            System.out.println(alph.charAt(i) + ": " + counts[i]);   
        }
    }
    
    public void testDecrypt(){
        String message = "The quick brown fox jumped over the lazy dogs";
        int key = 18;
        CaesarCipher cc = new CaesarCipher();
        String encrypted = cc.encrypt(message, key);
        String decrypted = decrypt(encrypted, key);
        
        System.out.println("The original message was: " + message);
        System.out.println("The encrypted message was: " + encrypted);
        System.out.println("The decrytpted message was: " + decrypted);
    }
    
    public void testTwoKeys(){
        FileResource fr = new FileResource();
        
        String message = fr.asString();
        decryptTwoKeys(message);
    }
    
    public void decryptTwoKeys(String encrypted){
        String firstStr = halfOfString(encrypted,0);
        String secondStr = halfOfString(encrypted,1);
        int key1 = getKey(firstStr);
        int key2 = getKey(secondStr);
        System.out.println("First key is: " + key1);
        System.out.println("Second key is: " + key2);
        
        CaesarCipher cc = new CaesarCipher();
        String decrypted = cc.encryptTwoKeys(encrypted, 26-key1, 26-key2);
        System.out.println("The decrypted string is: " + decrypted);
    }
    
    public String decrypt(String message, int key){
        CaesarCipher cc = new CaesarCipher();
        String decrypted = cc.encrypt(message, 26 - key);
        return decrypted;
    }
    
    public int[] countLetters(String message){
        String alph = "abcdefghijklmnopqrstuvwxyz";
        int[] counts = new int[26];
        for(int i = 0; i < message.length(); i++){
            int letterIndex = alph.indexOf(Character.toLowerCase(message.charAt(i)));
            if(letterIndex != -1){
                counts[letterIndex]++; 
            }
        }
        return counts;
    }
    
    public int maxIndex(int[] values){
        int maxDex = 0;
            for(int i = 0; i < values.length; i++){
                if (values[i] > values[maxDex]){
                    maxDex = i;
                }
            }
        return maxDex;
    }
    
    public String halfOfString(String message, int start){
        StringBuilder halfMsg = new StringBuilder();
        int msgLength = message.length();
        for(int i = start; i < msgLength; i+=2){
            halfMsg.append(message.charAt(i));
        }
        return halfMsg.toString();
    }
    
    public int getKey(String s){
        int[] counts = countLetters(s);
        int key = maxIndex(counts) - 4;
        if(key < 0){
            key = key + 26;
        }
        return key;
    }
}
