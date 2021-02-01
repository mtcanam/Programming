import edu.duke.*;
import java.io.*;
import org.apache.commons.csv.*;

/**
 * Write a description of TestCaesarCipher here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TestCaesarCipher {
    
    private String alphabet;
    
    public TestCaesarCipher(){
        alphabet = "abcdefghijklmnopqrstuvwxyz";
    }
    
    public void simpleTests(){
        FileResource fr = new FileResource();
        String message = fr.asString();
        CaesarCipherOOP cc = new CaesarCipherOOP(18);
        String encrypted = cc.encrypt(message);
        String decrypted = cc.decrypt(encrypted);
        System.out.println("Message is " + message);
        System.out.println("Encrypted is " + encrypted);
        System.out.println("Decrypted is " + decrypted);
        breakCaesarCipher(encrypted);
    }
    
    public void quizTests(){
        String str = "Can you imagine life WITHOUT the internet AND computers in your pocket?";
        CaesarCipherOOP cc = new CaesarCipherOOP(15);
        System.out.println(cc.encrypt(str));
    }
    
    public void breakCaesarCipher(String input){
        int key = getKey(input);
        CaesarCipherOOP cc = new CaesarCipherOOP(key);
        String message = cc.decrypt(input);
        System.out.println("The decrypted message is: " + message);
    }
    
    public int[] countLetters(String decrypt){
        int[] counts = new int[26];
        for(int i = 0; i < decrypt.length(); i++){
            char ch = Character.toLowerCase(decrypt.charAt(i));
            int dex = alphabet.indexOf(ch);
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
    
    public int getKey(String s){
        int[] counts = countLetters(s);
        int key = maxIndex(counts) - 4;
        if(key < 0){
            key = key + 26;
        }
        return key;
    }
    
}
