import edu.duke.*;
import java.io.*;
import org.apache.commons.csv.*;

/**
 * Write a description of BabyBirths here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BabyBirths {
    public void testMethod(){
        //FileResource fr = new FileResource();
        //totalBirths(fr);
        //System.out.println(getRank(1971,"Frank","M"));
        //System.out.println(getName(1982,450,"M"));
        //whatIsNameInYear("Owen", 1974, 2014, "M");
        //System.out.println(yearOfHighestRank("Mich","M"));
        //System.out.println(getAverageRank("Robert","M"));
        System.out.println(getTotalBirthsRankedHigher(1990,"Drew","M"));
    }
    
    public void totalBirths(FileResource fr){
        int totalBirths = 0;
        int totalNames = 0;
        int totalBoys = 0;
        int totalBoyNames = 0; 
        int totalGirls = 0;
        int totalGirlNames = 0;
        for (CSVRecord r : fr.getCSVParser(false)){
            int numBorn = Integer.parseInt(r.get(2));
            totalBirths += numBorn;
            totalNames++;
            if(r.get(1).equals("M")){
                totalBoys += numBorn;
                totalBoyNames++;
            }else{
                totalGirls += numBorn;
                totalGirlNames++;
            }
        }
        System.out.println("Total Names: " + totalNames);
        System.out.println("Total Births: " + totalBirths);
        System.out.println("Total Boy Names: " + totalBoyNames);
        System.out.println("Total Boys: " + totalBoys);
        System.out.println("Total Girl Names: " + totalGirlNames);
        System.out.println("Total Girls: " + totalGirls);
    }
    
    public int getRank(int year, String name, String gender){
        int genderCnt = 0;
        File file = new File("./us_babynames_by_year/yob" + year + ".csv");
        FileResource fr = new FileResource(file);
        for (CSVRecord r : fr.getCSVParser(false)){
            if(r.get(1).equals(gender)){
                //If we're in the right gender, start counting
                genderCnt++;
                if(r.get(0).equals(name)){
                    //If we find the name, then the rank is the gender count
                    return genderCnt;
                }
            }
        }
        //If the loop finishes without returning, return -1
        return -1;
    }
    
    public String getName(int year, int rank, String gender){
        int genderCnt = 0; 
        File file = new File("./us_babynames_by_year/yob" + year + ".csv");
        FileResource fr = new FileResource(file);
        for (CSVRecord r : fr.getCSVParser(false)){
            if(r.get(1).equals(gender)){
                //If we're in the right gender, start counting
                genderCnt++;
                if(genderCnt == rank){
                    //If we find the name, then the rank is the gender count
                    return r.get(0);
                }
            }
        }
        //If the loop finishes without returning, return -1
        return "NO NAME";
    }
    
    public void whatIsNameInYear(String name, int year, int newYear, String gender){
        int nameRank = getRank(year, name, gender);
        String newName = getName(newYear, nameRank, gender);
        System.out.println(name + " born in " + year + " would be " + newName + " born in " + newYear);
    }
    
    public int yearOfHighestRank(String name, String gender){
        int minRank = 999;
        int minYear = -1;
        DirectoryResource dr = new DirectoryResource();
        for(File f:dr.selectedFiles()){
            FileResource fr = new FileResource(f);
            int fileYear = Integer.parseInt(f.getName().substring(3,7));
            int currRank = getRank(fileYear, name, gender);
            if(currRank < minRank && currRank != -1){
                minRank = currRank;
                minYear = fileYear;
            }
        }
        return minYear;
    }
    
    public double getAverageRank(String name, String gender){
        int sumRank = 0;
        int cntRank = 0;
        DirectoryResource dr = new DirectoryResource();
        for(File f:dr.selectedFiles()){
            FileResource fr = new FileResource(f);
            int fileYear = Integer.parseInt(f.getName().substring(3,7));
            int currRank = getRank(fileYear, name, gender);
            if (currRank > -1){
                //found the name
                sumRank += currRank;
                cntRank++;
            }
        }
        return ((double) sumRank)/cntRank;
    }
    
    public int getTotalBirthsRankedHigher(int year, String name, String gender){
        int genderCnt = 0; 
        int totalBirths = 0;
        int rank = getRank(year, name, gender);
        File file = new File("./us_babynames_by_year/yob" + year + ".csv");
        FileResource fr = new FileResource(file);
        for (CSVRecord r : fr.getCSVParser(false)){
            if(r.get(1).equals(gender)){
                genderCnt++;
                if(genderCnt < rank){
                    totalBirths += Integer.parseInt(r.get(2));
                }
            }
        }   
        return totalBirths;
    }
}
