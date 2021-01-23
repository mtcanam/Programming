package ShortestPathsNPComplete;

/*
Looking to find the shortest path that passes through all vertices. NP-Complete

Idea is to use a dynamic programming algorithm with a similar structure to Bellman-Ford.

Solutions will be stored in a float array with the set of usable vertices as one index, and the destination as the other
Three nested for loops:
1. m = 2:n;  Regulating the size of the set of vertices that we use for the subproblem
2. S = Set containing vertex 1 (by convention) of size m
3. j = Vertex in set S (not 1)

Array value to be saved for each subproblem is:
Minimum over all other (non-j) vertices in S of the subproblem that does not contain that vertex, plus the value of
that vertex to j.

In this case, we don't actually get the answer directly from this array. The answer will be the min value of the array
with a full set of vertices plus the hop back to 1.

Inputs: Filename to read
    Expecting first line to be number of vertices
    Subsequent lines to be floats of x, y
Outputs: Shortest TSP

 */

import ObjectLibrary.Bits;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashSet;
import java.util.List;

public class TravellingSalesmanSolver {

    private ArrayList<Point2D> vertArray;
    private int vertCount;
    private int setSize;
    private float[][] solArray;
    private float[][] distArray;

    public TravellingSalesmanSolver(String fName){
        //Read in data to array of vertices
        try {
            readData(fName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Pre calculate all teh distances from each vertex to each other, and store for later use
        generateDistanceArray();
        //Create array that will store all the subproblem solutions
        //Will be indexed by a bitset representing the set of vertices, and an int representing the dest vertex
        solArray = new float[setSize][vertCount];
    }




    
    public void populateSubProblems(){
        //Set the base case
        for (int s = 0; s < setSize; s++) {
            if (s == 1){
                solArray[s][0] = 0f;
            }else {
                solArray[s][0] = Float.MAX_VALUE;
            }
        }
        //1. m = 2:n;  Regulating the size of the set of vertices that we use for the subproblem
        for (int m = 2; m <= vertCount; m++){
            System.out.println("Iterating on set size " + m);
            //2. S = Set containing vertex 1 (by convention) of size m
            BitSet bs = new BitSet(0);
            bs = generateSubsets(m, bs);
            while (bs != null) {
                //3. j = Vertex in set S (not 1)
                int prevSetBitJ = 1;
                for (int i = 1; i < m; i++) {
                    float minVal = 100000;
                    int prevSetBitK = 0;
                    int j = bs.nextSetBit(prevSetBitJ);
                    prevSetBitJ = j + 1;
                    //j is the vertex that is our destination
                    for (int n = 0; n < m; n++){
                        //Iterate through all other vertices
                        int k = bs.nextSetBit(prevSetBitK);
                        prevSetBitK = k + 1;
                        if (k != j) {
                            //Create a new set without j
                            BitSet bs2 = (BitSet) bs.clone();
                            bs2.clear(j);
                            float dist = solArray[(int)Bits.convert(bs2)][k] + distArray[k][j];
                            if (dist < minVal){
                                minVal = dist;
                            }
                        }
                    }
                    solArray[(int)Bits.convert(bs)][j] = minVal;
                }
                bs = generateSubsets(m, bs);
            }
        }
    }

    public float getMinimumCostTourValue(){
        //Iterate over all full set distances to j
        float minVal = Float.MAX_VALUE;
        for (int j = 1; j < vertCount; j++) {
            float fullLoopDist = solArray[setSize - 1][j] + distArray[j][0];
            if (fullLoopDist < minVal){
                minVal = fullLoopDist;
            }
        }
        return minVal;
    }

    //Helper function to generate bitsets that represent all possible subsets of cardinality m that have vert 1
    private BitSet generateSubsets(int m, BitSet prevBS){
        long prevLong = Bits.convert(prevBS);
        for (long i = prevLong + 1; i < Math.pow(2, vertCount); i++) {
            if (Long.bitCount(i) != m) {
                continue;
            }
            BitSet bs = Bits.convert(i);
            if (bs.get(0) == true) {
                return bs;
            }
        }
        return null;
    }

    private void generateDistanceArray(){
        distArray = new float[vertCount][vertCount];
        //Fill the array with distances from each vertex to each other
        for (int i = 0; i < vertCount; i++) {
            for (int j = 0; j < vertCount; j++) {
                if(i == j){
                    distArray[i][j] = 0f;
                }else{
                    distArray[i][j] = getDistance(vertArray.get(i), vertArray.get(j));
                }
            }
        }
    }

    private float getDistance(Point2D p1, Point2D p2){
        return (float) Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2));
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
        setSize = (int) Math.pow(2, vertCount);
        //Iterate over remaining data
        while ((line = reader.readLine()) != null) {
            if (!line.equals("")) {
                //Take the line, split into x and y, then save to vertMap
                String[] parts = line.split("\\s");
                float xVal = Float.parseFloat(parts[0]);
                float yVal = Float.parseFloat(parts[1]);
                //Create point
                Point2D newPoint = new Point2D.Float(xVal, yVal);
                vertArray.add(newPoint);
            }
        }
        reader.close();
    }

}
