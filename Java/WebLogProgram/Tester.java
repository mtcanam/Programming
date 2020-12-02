
/**
 * Write a description of class Tester here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class Tester
{
    public void testLogEntry() {
        LogEntry le = new LogEntry("1.2.3.4", new Date(), "example request", 200, 500);
        System.out.println(le);
        LogEntry le2 = new LogEntry("1.2.100.4", new Date(), "example request 2", 300, 400);
        System.out.println(le2);
    }
    
    public void testLogAnalyzer() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("short-test_log");
        la.printAll();
    }
    
    public void testUniqueIp(){
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog2_log");
        System.out.println(la.countUniqueIps());
    }
    
    public void testPrintAllHigherThanNum(){
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog1_log");
        int num = 400;
        la.printAllHigherThanNum(num);
    }
    
    public void testUniqueIPVisitsOnDay(){
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog2_log");
        String someday = "Sep 24";
        ArrayList<String> arr = la.uniqueIPVisitsOnDay(someday);
        System.out.println(arr.size());
        for(String s:arr){
            System.out.println(s);
        }
    }
    
    public void testCountUniqueIPsInRange(){
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog2_log");
        int low = 200;
        int high = 299;
        System.out.println(la.countUniqueIPsInRange(low, high));
    }
    
    public void testMostNumberVisitsByIP(){
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog2_log");
        HashMap<String, Integer> hm = la.countVisitsPerIP();
        int maxVal = la.mostNumberVisitsByIP(hm);
        System.out.println(maxVal);
    }
    
    public void testIPsMostVisits(){
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog2_log");
        HashMap<String, Integer> hm = la.countVisitsPerIP();
        ArrayList<String> arr = la.iPsMostVisits(hm);
        for(String s:arr){
            System.out.println(s);
        }
    }
    
    public void testIPsForDays(){
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog3-short_log");
        HashMap<String, ArrayList<String>> hm = la.iPsForDays();
        ArrayList<String> arr = hm.get("Sep 30");
        for(String s:arr){
            System.out.println(s);
        }
    }
    
    public void testDaysWithMostIPVisits(){
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog2_log");
        HashMap<String, ArrayList<String>> hm = la.iPsForDays();
        String maxDay = la.daysWithMostIPVisits(hm);
        System.out.println(maxDay);
    }
    
    public void testIPsWithMostVisitsOnDay(){
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog2_log");
        HashMap<String, ArrayList<String>> hm = la.iPsForDays();
        ArrayList<String> arr = la.iPsWithMostVisitsOnDay(hm,"Sep 30");
        for(String s:arr){
            System.out.println(s);
        }
    }
}
