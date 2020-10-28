import edu.duke.*;
import java.io.*;
import org.apache.commons.csv.*;

/**
 * Write a description of CSVMin here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CSVMin {
    public void testColdestHourInFile(){
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        System.out.println(coldestHourInFile(parser).get("TemperatureF"));
    }
    
    public void testFileWithColdestTemperature(){
        File f = fileWithColdestTemperature();
        FileResource fr = new FileResource(f);
        CSVParser parser = fr.getCSVParser();
        String coldestTemp = coldestHourInFile(parser).get("TemperatureF");
        System.out.println("Coldest day was in file " + f.getName());
        System.out.println("Coldest temperature on that day was " + coldestTemp);
        System.out.println("All the Temperatures on the coldest day were:");
        CSVParser parser2 = fr.getCSVParser();
        for (CSVRecord r : parser2){
            System.out.println(r.get("DateUTC")+"   "+r.get("TemperatureF"));
        }
    }
    
    public void testLowestHumidityInFile(){
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        CSVRecord csv = lowestHumidityInFile(parser);
        System.out.println("Lowest Humidity was " + csv.get("Humidity") + " at " + csv.get("DateUTC"));
    }
    
    public void testLowestHumidityInManyFiles (){
        CSVRecord csv = lowestHumidityInManyFiles();
        System.out.println("Lowest Humidity was " + csv.get("Humidity") + " at " + csv.get("DateUTC"));
    }
    
    public void testAverageTemperatureInFile(){
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        double avgTemp = averageTemperatureInFile(parser);
        System.out.println("Average Temperature in File is " + avgTemp);
    }
    
    public void testAverageTemperatureWithHighHumidityInFile(){
        int value = 80;
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        double avgTemp = averageTemperatureWithHighHumidityInFile(parser,value);
        System.out.println("Average Temperature when high Humidity is " + avgTemp);
    }
    
    public CSVRecord coldestHourInFile(CSVParser parser){
        CSVRecord coldestRow = null;
        double currTemp;
        double coldestTemp = -9999;
        for (CSVRecord r : parser){
            currTemp = Double.parseDouble(r.get("TemperatureF"));
            if (currTemp > -9999){
                if (coldestRow == null){
                    coldestRow = r;
                    coldestTemp = currTemp;
                } else if(currTemp < coldestTemp){
                    coldestRow = r;
                    coldestTemp = currTemp;
                }
            }    
        }
        return coldestRow;
    }
    
    public File fileWithColdestTemperature(){
        CSVRecord coldestRow = null;
        double currTemp;
        double coldestTemp = -9999;
        File coldestFile = null;
        DirectoryResource dr = new DirectoryResource();
        for(File f:dr.selectedFiles()){
            FileResource fr = new FileResource(f);
            CSVParser parser = fr.getCSVParser();
            CSVRecord r = coldestHourInFile(parser);
            currTemp = Double.parseDouble(r.get("TemperatureF"));
            if (coldestRow == null){
                coldestRow = r;
                coldestTemp = currTemp;
                coldestFile = f;
            } else if(currTemp < coldestTemp){
                coldestRow = r;
                coldestTemp = currTemp;
                coldestFile = f;
            }   
        }
        return coldestFile;
    }
    
    public CSVRecord lowestHumidityInFile(CSVParser parser){
        CSVRecord dryRow = null;
        double currHum;
        double dryHum = 100;
        String currHumString;
        for (CSVRecord r : parser){
            currHumString = r.get("Humidity");
            if (currHumString.equals("N/A")){
                break;
            }else{
                currHum = Double.parseDouble(currHumString);
            }
            if (dryRow == null){
                dryRow = r;
                dryHum = currHum;
            } else if(currHum < dryHum){
                dryRow = r;
                dryHum = currHum;
            }
        }
        return dryRow;
    }

    public CSVRecord lowestHumidityInManyFiles(){
        CSVRecord dryRow = null;
        double currHum;
        double dryHum = 100;
        DirectoryResource dr = new DirectoryResource();
        for(File f:dr.selectedFiles()){
            FileResource fr = new FileResource(f);
            CSVParser parser = fr.getCSVParser();
            CSVRecord r = lowestHumidityInFile(parser);
            currHum = Double.parseDouble(r.get("Humidity"));
            if (dryRow == null){
                dryRow = r;
                dryHum = currHum;
            } else if(currHum < dryHum){
                dryRow = r;
                dryHum = currHum;
            }   
        }
        return dryRow;
    }
    
    public double averageTemperatureInFile(CSVParser parser){
        double sumTemp = 0;
        double cntTemp = 0;
        for (CSVRecord r : parser){
            double currTemp = Double.parseDouble(r.get("TemperatureF"));
            if (currTemp > -9999){
                sumTemp = sumTemp + currTemp;
                cntTemp++;
            }    
        }
        return sumTemp/cntTemp;
    }
    
        public double averageTemperatureWithHighHumidityInFile(CSVParser parser, int value){
        double sumTemp = 0;
        double cntTemp = 0;
        double currHum = 0;
        for (CSVRecord r : parser){
            double currTemp = Double.parseDouble(r.get("TemperatureF"));
            String currHumString = r.get("Humidity");
            if (currHumString.equals("N/A")){
                break;
            }else{
                currHum = Double.parseDouble(currHumString);
            }
            if (currTemp > -9999 && currHum >= value){
                sumTemp = sumTemp + currTemp;
                cntTemp++;
            }    
        }
        return sumTemp/cntTemp;
    }
    
}
