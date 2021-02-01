import edu.duke.*;
import java.util.*;

/**
 * Write a description of Tester here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Tester {
    public void testSliceString(){
        VigenereBreaker vb = new VigenereBreaker();
        System.out.println(vb.sliceString("abcdefghijklm", 4, 5));
    }
    
    public void testTryKeyLength(){
        FileResource fr = new FileResource();
        String str = fr.asString();
        VigenereBreaker vb = new VigenereBreaker();
        int[] key = new int[38];
        key = vb.tryKeyLength(str, 38, 'e');
        for(int j:key){
            System.out.println(j);
        }
        
    }
    
    public void testBreakVigenere(){
        VigenereBreaker vb = new VigenereBreaker();
        vb.breakVigenere();
    }
}
