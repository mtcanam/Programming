
/**
 * Write a description of class LogAnalyzer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;
import edu.duke.*;

public class LogAnalyzer
{
     private ArrayList<LogEntry> records;
     
     public LogAnalyzer() {
         records = new ArrayList<LogEntry>();
     }
     
     public void readFile(String filename) {
         WebLogParser parser = new WebLogParser();
         FileResource fr = new FileResource(filename);
         for(String s:fr.lines()){
             LogEntry log = parser.parseEntry(s);
             records.add(log);
         }
     }
     
     public HashMap<String, Integer> countVisitsPerIP(){
         HashMap<String, Integer> hm  =new HashMap<String, Integer>();
         for(LogEntry le:records){
             String add = le.getIpAddress();
             if(hm.containsKey(add)){
                 hm.put(add,hm.get(add)+1);
             }else{
                 hm.put(add,1);
             }
         }
         return hm;
     }
     
     public int mostNumberVisitsByIP(HashMap<String, Integer> hm){
         int maxVal = 0;
         String maxAdd = "NONE";
         for(String s:hm.keySet()){
             int currVal = hm.get(s);
             if(currVal > maxVal){
                 maxVal = currVal;
                 maxAdd = s;
             }
         }
         return maxVal;
     }
     
     public ArrayList<String> iPsMostVisits(HashMap<String, Integer> hm){
         int maxVal = mostNumberVisitsByIP(hm);
         ArrayList<String> arr = new ArrayList<String>();
         for(String s:hm.keySet()){
             int currVal = hm.get(s);
             if(currVal == maxVal){
                 arr.add(s);
             }
         }
         return arr;
     }
     
     public HashMap<String, ArrayList<String>> iPsForDays(){
         HashMap<String, ArrayList<String>> hm = new HashMap<String, ArrayList<String>>(); 
         for(LogEntry le:records){
             String recDate = le.getAccessTime().toString();
             String str = recDate.substring(4,10);
             if(!hm.keySet().contains(str)){
                 ArrayList<String> arr = iPVisitsOnDay(str);
                 hm.put(str, arr);
             }
         }
         return hm;
     }
     
     public String daysWithMostIPVisits(HashMap<String, ArrayList<String>> hm){
         int maxVal = 0;
         String maxDay = "NONE";
         for(String s:hm.keySet()){
             int currVal = hm.get(s).size();
             if(currVal > maxVal){
                 maxVal = currVal;
                 maxDay = s;
             }
         }
         return maxDay;
     }
     
     public ArrayList<String> iPsWithMostVisitsOnDay(HashMap<String, ArrayList<String>> hm, String someday){
         //Array of dates and ip addresses for that date are inputs, as well as date itself
         ArrayList<String> arr = hm.get(someday);
         //Create a new hashmap of ips and counts for the days ip array
         HashMap<String, Integer> hm2  =new HashMap<String, Integer>();
         for(String s:arr){
             if(hm2.containsKey(s)){
                 hm2.put(s,hm2.get(s)+1);
             }else{
                 hm2.put(s,1);
             }
         }
         ArrayList<String> arr2 = iPsMostVisits(hm2);
         return arr2;
     }
     
     public void printAll() {
         for (LogEntry le : records) {
             System.out.println(le);
         }
     }
     
     public int countUniqueIps(){
         ArrayList<String> uniqueIP = new ArrayList<String>();
         for(LogEntry le:records){
             String add = le.getIpAddress();
             if(uniqueIP.isEmpty() || !uniqueIP.contains(add)){
                 uniqueIP.add(add);
             }
         }
         return uniqueIP.size();
     }
     
     public void printAllHigherThanNum(int num){
         for(LogEntry le:records){
             int code = le.getStatusCode();
             if(code > num){
                 System.out.println(le);
             }
         }
     }
     
     public ArrayList<String> uniqueIPVisitsOnDay(String someday){
         ArrayList<String> arr = new ArrayList<String>();
         for(LogEntry le:records){
             String add = le.getIpAddress();
             String str = le.getAccessTime().toString();
             if(str.indexOf(someday)!=-1 && !arr.contains(add)){
                 arr.add(add);
             }
         }
         return arr;
     }
     
     public ArrayList<String> iPVisitsOnDay(String someday){
         ArrayList<String> arr = new ArrayList<String>();
         for(LogEntry le:records){
             String add = le.getIpAddress();
             String str = le.getAccessTime().toString();
             if(str.indexOf(someday)!=-1){
                 arr.add(add);
             }
         }
         return arr;
     }
     
     public int countUniqueIPsInRange(int low, int high){
         ArrayList<String> arr = new ArrayList<String>();
         for(LogEntry le:records){
             String add = le.getIpAddress();
             int code = le.getStatusCode();
             if(code >= low && code <= high && !arr.contains(add)){
                 arr.add(add);
             }
         }
         return arr.size();
     }
}
