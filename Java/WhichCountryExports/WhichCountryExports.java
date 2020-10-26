import edu.duke.*;
import java.io.File;
import org.apache.commons.csv.*;
/**
 * Write a description of WhichCountryExports here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */



public class WhichCountryExports {

    public void tester(){
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        System.out.println(countryInfo(parser, "Nauru"));
        CSVParser parser2 = fr.getCSVParser();
        listExportersTwoProducts(parser2, "fish", "nuts");
        CSVParser parser3 = fr.getCSVParser();
        System.out.println(numberOfExporters(parser3, "sugar"));
        CSVParser parser4 = fr.getCSVParser();
        bigExporters(parser4, "$999,999,999,999");
    }
    
    public String countryInfo(CSVParser parser, String country){
        String cInfo = "NOT FOUND";
        for(CSVRecord record : parser){
            if(record.get("Country").equals(country)){
                cInfo = country + ": " + record.get("Exports") + ": " + record.get("Value (dollars)");
            }
        }
        return cInfo;
    }
    
    public void listExportersTwoProducts(CSVParser parser, String exportItem1, String exportItem2){
        for(CSVRecord record : parser){
            if(record.get("Exports").contains(exportItem1) && record.get("Exports").contains(exportItem2)){
                System.out.println(record.get("Country"));
            }
        }
    }
    
    public int numberOfExporters(CSVParser parser, String exportItem){
        int cnt = 0;
        for(CSVRecord record : parser){
            if(record.get("Exports").contains(exportItem)){
                cnt++;
            }
        }
        return cnt;
    }
    
    public void bigExporters(CSVParser parser, String amount){
        int amtLength = amount.length();
        for(CSVRecord record : parser){
            if(record.get("Value (dollars)").length() > amtLength){
                System.out.println(record.get("Country") + ": " + record.get("Value (dollars)"));
            }
        }
    }
}
