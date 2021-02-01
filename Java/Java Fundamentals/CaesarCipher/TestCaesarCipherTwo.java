import edu.duke.*;
import java.io.*;
import org.apache.commons.csv.*;

/**
 * Write a description of TestCaesarCipherTwo here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TestCaesarCipherTwo {

    public void simpleTests(){
        FileResource fr = new FileResource();
        String message = fr.asString();
        CaesarCipherTwo cc = new CaesarCipherTwo(17,3);
        String encrypted = cc.encrypt(message);
        String decrypted = cc.decrypt(encrypted); 
        System.out.println("Message is " + message);
        System.out.println("Encrypted is " + encrypted);
        System.out.println("Decrypted is " + decrypted);
        breakCaesarCipher(encrypted);
    }
    
    public void quizTests(){
        //FileResource fr = new FileResource();
        //String str = fr.asString();
        String str = "Aal uttx hm aal Qtct Fhljha pl Wbdl. Pvxvxlx!";
        CaesarCipherTwo cc = new CaesarCipherTwo(14,24);
        System.out.println(cc.decrypt(str));
        breakCaesarCipher(str);
    }
    
    public void breakCaesarCipher(String input){
        
        String firstStr = halfOfString(input,0);
        String secondStr = halfOfString(input,1);
        int key1 = getKey(firstStr);
        int key2 = getKey(secondStr);
        
        System.out.println("First key is: " + key1);
        System.out.println("Second key is: " + key2);
        
        CaesarCipherTwo cc = new CaesarCipherTwo(key1, key2);
        
        String decrypted = cc.decrypt(input);
        System.out.println("The decrypted string is: " + decrypted);
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
