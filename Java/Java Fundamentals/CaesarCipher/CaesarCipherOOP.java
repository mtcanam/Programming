import edu.duke.*;
import java.io.*;
import org.apache.commons.csv.*;



/**
 * Write a description of CaesarCipher here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CaesarCipherOOP {
    private int mainKey;
    private String alphabet;
    private String shiftedAlphabet;
    
    public CaesarCipherOOP(int key){
        mainKey = key;
        alphabet = "abcdefghijklmnopqrstuvwxyz";
        shiftedAlphabet = alphabet.substring(key) + alphabet.substring(0,key);
    }
    
    public void testCaesarClass(){
        FileResource fr = new FileResource();
        String message = fr.asString();
        int key = 23;
        String encrypted = encrypt(message);
        System.out.println("key is " + key + "\n" + encrypted);
    }
    
    public String encrypt(String input){
        StringBuilder encrypted = new StringBuilder(input);
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
    
    public String decrypt(String message){
        CaesarCipherOOP cc = new CaesarCipherOOP(26-mainKey);
        String decrypted = cc.encrypt(message);
        return decrypted;
    }
    
        
    /*
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
    

    */
}
