package DivideAndConquer;

import java.util.ArrayList;

//Works to multiply square matrices faster than the normal method
//General method is to break the matrix into 4 submatrices (filling with rows/cols of zero if necessary)
//Then calculate 7 multiplications and multiply together
public class StrassenMultiplier {

    public static void main(String[] args) {
        //Create the test matrix
        int[][] arr = new int[][] {
                new int[] { 1, 2, 3, 4},
                new int[] { 1, 2, 3, 4},
                new int[] { 1, 2, 3, 4},
                new int[] { 1, 2, 3, 4},
        };
    }

    private static int[][]

}

class Matrix {
    private int rowCount;
    private int colCount;
    private int arr[][];

    public Matrix(int inputArr[][]){
        arr = inputArr;
        rowCount = inputArr[0].length;
        colCount = inputArr.length;
        bufferMatrix();
    }

    public int[][] getQuadrant(int row, int col){
        //Takes the matrix in arr[][], and returns the specified quadrant
        //0 is the left/top, 1 is the right/bottom
        int[][] quadArr = new int[rowCount/2][colCount/2];
        for (int i = row*rowCount/2; i < (1 + row) * rowCount / 2; i++){
            for (int j = col*colCount/2; j < (1 + col) * colCount / 2; j++){
                quadArr[i][j] = arr[i][j];
            }
        }
        return quadArr;
    }
    private void bufferMatrix(){
        if (rowCount % 2 != 0) {
            addRowOfZeroes();
        }
        if (colCount % 2 != 0){
            addColOfZeroes();
        }
    }
    private void addRowOfZeroes(){
        int[][] newArray = new int[rowCount+1][colCount];
        for (int i = 0; i <= rowCount; i++){
            for (int j = 0; j < colCount; j++){
                if(i < rowCount) {
                    newArray[i][j] = arr[i][j];
                }else{
                    newArray[i][j] = 0;
                }
            }
        }
        arr = newArray;
    }

    private void addColOfZeroes(){
        int[][] newArray = new int[rowCount][colCount+1];
        for (int i = 0; i <= rowCount; i++){
            for (int j = 0; j < colCount; j++){
                if(j < rowCount) {
                    newArray[i][j] = arr[i][j];
                }else{
                    newArray[i][j] = 0;
                }
            }
        }
        arr = newArray;
    }

}