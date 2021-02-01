
/**
 * Write a description of Part2 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Part2 {
    public int howMany(String stringA, String stringB){
        //how many times does stringA appear in stringB
        int startIndex = 0;
        int stopIndex = 0;
        int cnt = 0;
        while(stopIndex < stringB.length()){
            startIndex = stringB.indexOf(stringA, stopIndex);
            stopIndex = startIndex + stringA.length() + 1; 
            if (startIndex != -1){ 
                cnt = cnt + 1;
            }else{
                break;
            }
        }
        return cnt;
    }
    
    public void testHowMany(){
        System.out.println(howMany("GAA", "ATGAACGAATTGAATC")); 
    }
}
