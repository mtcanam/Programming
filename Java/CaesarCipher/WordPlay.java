import edu.duke.*;
import java.io.*;
import org.apache.commons.csv.*;


/**
 * Write a description of WordPlay here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WordPlay {
    
    public void testMethod(){
        //System.out.println(isVowel('f'));
        //System.out.println(replaceVowels("Hello World",'*'));
        System.out.println(emphasize("Mary Bella Abracadabra", 'a'));
    }
    
    public boolean isVowel(char ch){
     
        String vowelStr = "aeiou";
        if (vowelStr.indexOf(Character.toLowerCase(ch)) > -1){
            return true;
        }else{
            return false;
        }
        
    }
    
    public String replaceVowels(String phrase, char ch){
        StringBuilder repPhrase = new StringBuilder(phrase);
        
        for(int i = 0; i < repPhrase.length(); i++){
            if (isVowel(repPhrase.charAt(i))){
                repPhrase.setCharAt(i,ch);
            }
        }
        return repPhrase.toString();
    }
    
    public String emphasize(String phrase, char ch){
        StringBuilder repPhrase = new StringBuilder(phrase);
        
        for(int i = 0; i < repPhrase.length(); i++){
            if (Character.toLowerCase(repPhrase.charAt(i)) == Character.toLowerCase(ch)){
                if (i%2==0){
                    repPhrase.setCharAt(i,'*');
                }else{
                    repPhrase.setCharAt(i,'+');
                }
            }
        }
        return repPhrase.toString();
    }
    
}
