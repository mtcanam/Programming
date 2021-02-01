import edu.duke.*;
import java.io.File;

public class PerimeterAssignmentRunner {
    public double getPerimeter (Shape s) {
        // Start with totalPerim = 0
        double totalPerim = 0.0;
        // Start wth prevPt = the last point 
        Point prevPt = s.getLastPoint();
        // For each point currPt in the shape,
        for (Point currPt : s.getPoints()) {
            // Find distance from prevPt point to currPt 
            double currDist = prevPt.distance(currPt);
            // Update totalPerim by currDist
            totalPerim = totalPerim + currDist;
            // Update prevPt to be currPt
            prevPt = currPt;
        }
        // totalPerim is the answer
        return totalPerim;
    }

    public int getNumPoints (Shape s) {
        // Put code here
        int numPoints = 0; 
        for (Point currPt : s.getPoints()) {
            numPoints = numPoints + 1;
        }
        return numPoints;
    }

    public double getAverageLength(Shape s) { 
        double avLength = getPerimeter(s)/getNumPoints(s);
        return avLength;
    }

    public double getLargestSide(Shape s) {
        // Put code here
        double maxLength = 0;
        Point prevPt = s.getLastPoint();
        for (Point currPt : s.getPoints()) {
            // Find distance from prevPt point to currPt 
            double currDist = prevPt.distance(currPt);
            // If this distance is higher than the previous, then take this distance
            if (currDist > maxLength){
                maxLength = currDist;
            }
            // Update prevPt to be currPt
            prevPt = currPt;
        }
        return maxLength;
    }

    public double getLargestX(Shape s) {
        double largestX = 0;
        Point prevPt = s.getLastPoint();
        for (Point currPt : s.getPoints()) {
            // If this distance is higher than the previous, then take this distance
            if (currPt.getX() > largestX){
                largestX = currPt.getX();
            }
            // Update prevPt to be currPt
            prevPt = currPt;
        }
        return largestX;
    }

    public double getLargestPerimeterMultipleFiles() {
        double maxPerimeter = 0;
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            Shape s = new Shape(fr);
            double currPerimeter = getPerimeter(s);
            if (currPerimeter > maxPerimeter){
                maxPerimeter = currPerimeter;
            }
        }
        
        return maxPerimeter;
    }

    public String getFileWithLargestPerimeter() {
        // Put code here
        double maxPerimeter = 0;
        File temp = null;
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            Shape s = new Shape(fr);
            double currPerimeter = getPerimeter(s);
            if (currPerimeter > maxPerimeter){
                maxPerimeter = currPerimeter;
                temp = f;
            }
        }
        return temp.getName();
    }

    public void testPerimeter () {
        FileResource fr = new FileResource();
        Shape s = new Shape(fr);
        //Print the number of points
        int numPoints = getNumPoints(s);
        System.out.println("Number of Points = " + numPoints);
        double avLength = getAverageLength(s);
        System.out.println("Average Side Length = " + avLength);
        double maxLength = getLargestSide(s);
        System.out.println("Max Side Length = " + maxLength);
        double largestX = getLargestX(s);
        System.out.println("Max X = " + largestX);
        double length = getPerimeter(s);
        System.out.println("perimeter = " + length);
    }
    
    public void testPerimeterMultipleFiles() {
        // Put code here
        double maxPerimeter = getLargestPerimeterMultipleFiles();
        System.out.println("Largest Perimeter = " + maxPerimeter);
    }

    public void testFileWithLargestPerimeter() {
        // Put code here
        String fName = getFileWithLargestPerimeter();
        System.out.println("Largest Perimeter File = " + fName);
    }

    // This method creates a triangle that you can use to test your other methods
    public void triangle(){
        Shape triangle = new Shape();
        triangle.addPoint(new Point(0,0));
        triangle.addPoint(new Point(6,0));
        triangle.addPoint(new Point(3,6));
        for (Point p : triangle.getPoints()){
            System.out.println(p);
        }
        double peri = getPerimeter(triangle);
        System.out.println("perimeter = "+peri);
    }

    // This method prints names of all files in a chosen folder that you can use to test your other methods
    public void printFileNames() {
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            System.out.println(f);
        }
    }

    public static void main (String[] args) {
        PerimeterAssignmentRunner pr = new PerimeterAssignmentRunner();
        pr.testPerimeter();
    }
}
