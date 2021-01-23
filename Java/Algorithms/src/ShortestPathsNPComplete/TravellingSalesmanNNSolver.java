package ShortestPathsNPComplete;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TravellingSalesmanNNSolver {

    private List<Point2D> vertArray;
    private int vertCount;
    private Set<Integer> visited;
    private List<Integer> pathFollowed;
    private List<Double> distFollowed;

    public TravellingSalesmanNNSolver(String fName){
        visited = new HashSet<>();
        //Read data into array
        try {
            readData(fName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double calculateTotalDistance(){
        pathFollowed = new ArrayList<>();
        distFollowed = new ArrayList<>();
        //Pick vertex 1 as the starting point, and iteratively find nearest neighbour
        visited.add(0);
        pathFollowed.add(1);
        int currVertex = 0;
        int closestVertex = 0;
        double totalDist = 0d;
        while (visited.size() < vertCount){
            closestVertex = findClosestNewVertex(currVertex);
            totalDist += getDistance(vertArray.get(currVertex), vertArray.get(closestVertex));
            visited.add(closestVertex);
            currVertex = closestVertex;
            pathFollowed.add(currVertex+1);
            distFollowed.add(totalDist);
        }
        return totalDist + getDistance(vertArray.get(0), vertArray.get(currVertex));
    }

    private int findClosestNewVertex(int nodeID){
        double minDist = Double.MAX_VALUE;
        int minID = -1;
        double currDist = 0d;
        //Fill the array with distances from each vertex to each other
        for (int j = 0; j < vertCount; j++) {
            if(!visited.contains(j)){
                if(nodeID == j){
                    currDist = Double.MAX_VALUE;
                }else{
                    currDist = getSquaredDistance(vertArray.get(nodeID), vertArray.get(j));
                }
                if (currDist < minDist || (currDist == minDist && j < minID)){
                    minDist = currDist;
                    minID = j;
                }
            }
        }
        return minID;
    }

    private double getDistance(Point2D p1, Point2D p2){
        return Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2));
    }

    private double getSquaredDistance(Point2D p1, Point2D p2){
        return Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2);
    }

    public List<Integer> getPathFollowed() {
        return pathFollowed;
    }

    private void readData(String fName) throws IOException {
        vertArray = new ArrayList<>();
        String filePath = "./data/" + fName + ".txt";
        String line;
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        //Read first line
        line = reader.readLine();
        //Save to vertex count
        vertCount = Integer.parseInt(line);
        //Iterate over remaining data
        while ((line = reader.readLine()) != null) {
            if (!line.equals("")) {
                //Take the line, split into x and y, then save to vertMap
                String[] parts = line.split("\\s");
                int vertID = Integer.parseInt(parts[0]);
                double xVal = Double.parseDouble(parts[1]);
                double yVal = Double.parseDouble(parts[2]);
                //Create point
                Point2D newPoint = new Point2D.Double(xVal, yVal);
                vertArray.add(newPoint);
            }
        }
        reader.close();
    }

}
