import edu.duke.*;
import java.util.*;

public class GladLibMap {
    private HashMap<String, ArrayList<String>> myMap;
    private ArrayList<String> usedWords;
    private ArrayList<String> usedLabels;
    
    private Random myRandom;
    
    private static String dataSourceURL = "http://dukelearntoprogram.com/course3/data";
    private static String dataSourceDirectory = "data";
    
    public GladLibMap(){
        initializeFromSource(dataSourceDirectory);
        myRandom = new Random();
    }
    
    public GladLibMap(String source){
        initializeFromSource(source);
        myRandom = new Random();
    }
    
    private void initializeFromSource(String source) {
        myMap = new HashMap<String,ArrayList<String>>();
        String[] labels = {"country","noun","animal","adjective","name","color","timeframe","verb","fruit"};
        for(String s:labels){
            ArrayList<String> arr = readIt(source + "/" + s + ".txt");
            myMap.put(s,arr);
        }
        usedWords = new ArrayList<String>();
        usedLabels = new ArrayList<String>();
    }
    
    private String randomFrom(ArrayList<String> source){
        int index = myRandom.nextInt(source.size());
        return source.get(index);
    }
    
    private String getSubstitute(String label) {
        if (label.equals("number")){
            return ""+myRandom.nextInt(50)+5;
        }else{
            if(!usedLabels.contains(label)){
                usedLabels.add(label);
            }
            return randomFrom(myMap.get(label));
        }
    }
    
    private String processWord(String w){
        int first = w.indexOf("<");
        int last = w.indexOf(">",first);
        if (first == -1 || last == -1){
            return w;
        }
        String prefix = w.substring(0,first);
        String suffix = w.substring(last+1);
        String sub = getSubstitute(w.substring(first+1,last));
        if(!usedWords.isEmpty()){
            while(usedWords.indexOf(sub)!=-1){
                sub = getSubstitute(w.substring(first+1,last));
            }
        }
        usedWords.add(sub);
        
        return prefix+sub+suffix;
    }
    
    private void printOut(String s, int lineWidth){
        int charsWritten = 0;
        for(String w : s.split("\\s+")){
            if (charsWritten + w.length() > lineWidth){
                System.out.println();
                charsWritten = 0;
            }
            System.out.print(w+" ");
            charsWritten += w.length() + 1;
        }
    }
    
    private String fromTemplate(String source){
        String story = "";
        if (source.startsWith("http")) {
            URLResource resource = new URLResource(source);
            for(String word : resource.words()){
                story = story + processWord(word) + " ";
            }
        }
        else {
            FileResource resource = new FileResource(source);
            for(String word : resource.words()){
                story = story + processWord(word) + " ";
            }
        }
        return story;
    }
    
    private ArrayList<String> readIt(String source){
        ArrayList<String> list = new ArrayList<String>();
        if (source.startsWith("http")) {
            URLResource resource = new URLResource(source);
            for(String line : resource.lines()){
                list.add(line);
            }
        }
        else {
            FileResource resource = new FileResource(source);
            for(String line : resource.lines()){
                list.add(line);
            }
        }
        return list;
    }
    
    public void makeStory(){
        System.out.println("\n");
        String story = fromTemplate("data/madtemplate2.txt");
        printOut(story, 60);
        System.out.println("\n" + "The total number of words replaced is: " + usedWords.size());
        System.out.println("\n" + "The total number of source words for replacement is: " + totalWordsInMap());
        System.out.println("\n" + "The total number of source words used for replacement is: " + totalWordsConsidered());
    }
    
    public int totalWordsInMap(){
        int totalNum = 0;
        for(String s:myMap.keySet()){
            totalNum += myMap.get(s).size();
        }
        return totalNum;
    }

    public int totalWordsConsidered(){
        int totalNum = 0;
        for(String s:myMap.keySet()){
            if(usedLabels.contains(s)){
                totalNum += myMap.get(s).size();
            }
        }
        return totalNum;
    }
}
