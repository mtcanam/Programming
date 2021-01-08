package DivideAndConquer;

//Works to multiply square matrices faster than the normal method
//General method is to break the matrix into 4 submatrices (filling with rows/cols of zero if necessary)
//Then calculate 7 multiplications and multiply together
public class StrassenMultiplier {

    public static void main(String[] args) {
        //Create the test matrix
        int[][] arr = new int[][] {
                new int[] { 1, 2, 3},
                new int[] { 1, 2, 3},
                new int[] { 1, 2, 3},
        };
        Matrix A = new Matrix(arr);
        Matrix B = new Matrix(arr);

        Matrix C = MultiplyMatrices(A, B);

        //By default, the matrix output will be the same size as the input, so no need to print the buffered rows or cols (if any)
        for (int i = 0; i < arr.length; i++){
            for (int j = 0; j < arr.length; j++){
                System.out.print(C.getEntry(i,j) + "\t");
            }
            System.out.print("\n");
        }
    }

    private static Matrix MultiplyMatrices(Matrix A, Matrix B){

        if (A.getRowCount() == 2 | A.getColCount() == 2){
            int[][] finalArray = new int[2][2];
            finalArray[0][0] = A.getEntry(0,0) * B.getEntry(0,0) + A.getEntry(0,1) * B.getEntry(1,0);
            finalArray[0][1] = A.getEntry(0,0) * B.getEntry(0,1) + A.getEntry(0,1) * B.getEntry(1,1);
            finalArray[1][0] = A.getEntry(1,0) * B.getEntry(0,0) + A.getEntry(1,1) * B.getEntry(1,0);
            finalArray[1][1] = A.getEntry(1,0) * B.getEntry(0,1) + A.getEntry(1,1) * B.getEntry(1,1);
            return new Matrix(finalArray);
        }

        Matrix A11 = A.getQuadrant(0,0);
        Matrix A12 = A.getQuadrant(0,1);
        Matrix A21 = A.getQuadrant(1,0);
        Matrix A22 = A.getQuadrant(1,1);

        Matrix B11 = A.getQuadrant(0,0);
        Matrix B12 = A.getQuadrant(0,1);
        Matrix B21 = A.getQuadrant(1,0);
        Matrix B22 = A.getQuadrant(1,1);



        Matrix M1 = MultiplyMatrices(A11.add(A22), B11.add(B22));
        Matrix M2 = MultiplyMatrices(A21.add(A22), B11);
        Matrix M3 = MultiplyMatrices(A11, B12.subtract(B22));
        Matrix M4 = MultiplyMatrices(A22, B21.subtract(B11));
        Matrix M5 = MultiplyMatrices(A11.add(A12), B22);
        Matrix M6 = MultiplyMatrices(A21.subtract(A11), B11.add(B12));
        Matrix M7 = MultiplyMatrices(A12.subtract(A22), B21.add(B22));

        Matrix C11 = M1.add(M4).subtract(M5).add(M7);
        Matrix C12 = M3.add(M5);
        Matrix C21 = M2.add(M4);
        Matrix C22 = M1.subtract(M2).add(M3).add(M6);

        return new Matrix(C11, C12, C21, C22);
    }

}

class Matrix {
    private int rowCount;
    private int colCount;
    private int bufferedCount;
    private int arr[][];

    public Matrix(int inputArr[][]){
        arr = inputArr;
        rowCount = inputArr[0].length;
        colCount = inputArr.length;
        bufferMatrix();
    }

    public Matrix(Matrix A11, Matrix A12, Matrix A21, Matrix A22){
        rowCount = A11.getRowCount() * 2;
        colCount = A11.getColCount() * 2;
        arr = new int[rowCount][colCount];
        for (int i = 0; i < rowCount; i++){
            for (int j = 0; j < colCount; j++){
                if (i < rowCount / 2) {
                    if(j < colCount / 2) {
                        arr[i][j] = A11.getEntry(i, j);
                    }else{
                        arr[i][j] = A12.getEntry(i, j - colCount / 2);
                    }
                }else{
                    if(j < colCount / 2) {
                        arr[i][j] = A21.getEntry(i - rowCount / 2, j);
                    }else{
                        arr[i][j] = A22.getEntry(i - rowCount / 2, j - colCount / 2);
                    }
                }
            }
        }
    }

    public Matrix getQuadrant(int row, int col){
        //Takes the matrix in arr[][], and returns the specified quadrant
        //0 is the left/top, 1 is the right/bottom
        int[][] quadArr = new int[rowCount/2][colCount/2];
        for (int i = row*rowCount/2; i < (1 + row) * rowCount / 2; i++){
            for (int j = col*colCount/2; j < (1 + col) * colCount / 2; j++){
                quadArr[i - row*rowCount/2][j - col*colCount/2] = arr[i][j];
            }
        }
        return new Matrix(quadArr);
    }

    public Matrix add(Matrix B){
        if (B.getColCount() != colCount | B.getRowCount() != rowCount){
            System.out.println("Error: Attempted to add differently sized matrices :(");
        }
        int[][] newArray = new int[rowCount][colCount];
        for (int i = 0; i < rowCount; i++){
            for (int j = 0; j < colCount; j++){
                newArray[i][j] = B.getEntry(i,j) + getEntry(i,j);
            }
        }
        return new Matrix(newArray);
    }

    public Matrix subtract(Matrix B){
        if (B.getColCount() != colCount | B.getRowCount() != rowCount){
            System.out.println("Error: Attempted to add differently sized matrices :(");
        }
        int[][] newArray = new int[rowCount][colCount];
        for (int i = 0; i < rowCount; i++){
            for (int j = 0; j < colCount; j++){
                newArray[i][j] = getEntry(i,j) - B.getEntry(i,j);
            }
        }
        return new Matrix(newArray);
    }

    public int getEntry(int i, int j){
        return arr[i][j];
    }

    public int getColCount() {
        return colCount;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int[][] getArr() {
        return arr;
    }

    private void bufferMatrix(){
        while (!isPowerOfTwo(rowCount)) {
            addRowOfZeroes();
        }
        while (!isPowerOfTwo(colCount)){
            addColOfZeroes();
        }
    }

    private boolean isPowerOfTwo(int i)
    {
        return (i & -i) == i;
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
        rowCount++;
    }

    private void addColOfZeroes(){
        int[][] newArray = new int[rowCount][colCount+1];
        for (int i = 0; i < rowCount; i++){
            for (int j = 0; j <= colCount; j++){
                if(j < colCount) {
                    newArray[i][j] = arr[i][j];
                }else{
                    newArray[i][j] = 0;
                }
            }
        }
        arr = newArray;
        colCount++;
    }

}